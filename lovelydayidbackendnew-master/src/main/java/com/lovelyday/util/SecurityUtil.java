package com.lovelyday.util;

import java.nio.charset.StandardCharsets;
import java.security.DigestException;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import com.lovelyday.service.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Controller
public class SecurityUtil {
	
	private final String encryptKey = "a2346rt2468976yut2teu";
	private final String SECRET = "LovelyDaySecretKey";

	@Autowired
	private UserService userService;
	
	public String encrypt(String str) {
		try {
			String tempEncrypt = "";
			char[] stringArray = str.toCharArray();
			char[] stringPwd = this.encryptKey.toCharArray();
			int index = 0;
			while (index < stringArray.length) {
				int c = stringArray[index];
				int modresult = (index + 1) % this.encryptKey.length();
				char tobeasciied = stringPwd[modresult];
				int nominal = ((c += tobeasciied) + 256) % 256;
				if (tobeasciied < 5) {
					nominal = nominal << 2;
				} else {
					nominal = nominal << 4;
				}
				int vh = (nominal >> (index * 4 + 4)) & 0x0f;
				String hex = Integer.toHexString(nominal);
				tempEncrypt = String.valueOf(tempEncrypt) + hex + stringPwd[vh];
				++index;
			}
			return String.valueOf(tempEncrypt);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public String decrypt(String str) {
		try {
			String tempDecrypt = "";
			char[] stringPwd = this.encryptKey.toCharArray();
			int index = 0, start = 0, end = 3;
			String hex = "";
			while (index < str.length() / 4) {
				hex = str.substring(start, end);
				int modresult = (index + 1) % this.encryptKey.length();
				char tobeasciied = stringPwd[modresult];
				int dec = Integer.parseInt(hex, 16);
				if (tobeasciied < 5) {
					dec = dec >> 2;
				} else {
					dec = dec >> 4;
				}
				int decStr = ((dec - tobeasciied) + 256) % 256;
				char charStr = (char) decStr;
				tempDecrypt = String.valueOf(tempDecrypt) + charStr;
				start = end + 1;
				end += 4;
				++index;
			}
			return String.valueOf(tempDecrypt);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "";
	}

	public String getkey() {
		String keyId = "";
		try {
			keyId = (UUID.randomUUID()).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return keyId;
	}

	public String decode(String key, String cipherText) {
		String data = "";
		try {
			byte[] ciperData = java.util.Base64.getDecoder().decode(cipherText);
			byte[] saltData = Arrays.copyOfRange(ciperData, 8, 16);

			MessageDigest mds = MessageDigest.getInstance("MD5");
			final byte[][] keyAndIV = GenerateKeyAndIV(32, 16, 1, saltData, key.getBytes(StandardCharsets.UTF_8), mds);
			SecretKeySpec keyS = new SecretKeySpec(keyAndIV[0], "AES");
			IvParameterSpec iv = new IvParameterSpec(keyAndIV[1]);

			byte[] encrypted = Arrays.copyOfRange(ciperData, 16, ciperData.length);
			Cipher aesCDC = Cipher.getInstance("AES/CBC/PKCS5Padding");
			aesCDC.init(Cipher.DECRYPT_MODE, keyS, iv);
			byte[] descryptedData = aesCDC.doFinal(encrypted);
			data = new String(descryptedData, StandardCharsets.UTF_8);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

	public static byte[][] GenerateKeyAndIV(int keyLength, int ivLength, int iterations, byte[] salt, byte[] password,
			MessageDigest md) {

		int digestLength = md.getDigestLength();
		int requiredLength = (keyLength + ivLength + digestLength - 1) / digestLength * digestLength;
		byte[] generatedData = new byte[requiredLength];
		int generatedLength = 0;

		try {
			md.reset();

			// Repeat process until sufficient data has been generated
			while (generatedLength < keyLength + ivLength) {

				// Digest data (last digest if available, password data, salt if available)
				if (generatedLength > 0)
					md.update(generatedData, generatedLength - digestLength, digestLength);
				md.update(password);
				if (salt != null)
					md.update(salt, 0, 8);
				md.digest(generatedData, generatedLength, digestLength);

				// additional rounds
				for (int i = 1; i < iterations; i++) {
					md.update(generatedData, generatedLength, digestLength);
					md.digest(generatedData, generatedLength, digestLength);
				}

				generatedLength += digestLength;
			}

			// Copy key and IV into separate byte arrays
			byte[][] result = new byte[2][];
			result[0] = Arrays.copyOfRange(generatedData, 0, keyLength);
			if (ivLength > 0)
				result[1] = Arrays.copyOfRange(generatedData, keyLength, keyLength + ivLength);

			return result;

		} catch (DigestException e) {
			throw new RuntimeException(e);

		} finally {
			// Clean out temporary data
			Arrays.fill(generatedData, (byte) 0);
		}
	}

	public String getJWTToken(String username) {
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");

		String token = Jwts.builder().setId("softtekJWT").setSubject(username)
				.claim("authorities",
						grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 600000))
				.signWith(SignatureAlgorithm.HS512, SECRET.getBytes()).compact();
		token = "LovelyDay." + token; 
		
		this.userService.updateToken(token, username);
		
		return token;
	}
	
	
	public String getUserName() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return auth.getName();
	}

}
