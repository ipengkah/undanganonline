package com.lovelyday.service;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.lovelyday.dto.ChangePassDto;
import com.lovelyday.dto.ResendEmailValidationDto;
import com.lovelyday.dto.ResponseDto;
import com.lovelyday.dto.ResponseDtoIsActive;
import com.lovelyday.dto.UserDto;
import com.lovelyday.model.User;
import com.lovelyday.model.UserOrders;
import com.lovelyday.other.AccountType;
import com.lovelyday.repository.UserOrdersRepository;
import com.lovelyday.repository.UserRepository;
import com.lovelyday.util.EmailSenderUtil;
import com.lovelyday.util.SecurityUtil;

@Service
public class UserService {
	
	private Log log = LogFactory.getLog(getClass());
	
	@Resource
	private UserRepository userRepository;
	
	@Resource
	private UserOrdersRepository userOrdersRepository;
	
	@Autowired
	SecurityUtil securityUtil;
	
	@Autowired
	EmailSenderUtil emailSenderService;
	
	
	public void saveUser(User user) {
		this.userRepository.save(user);
	}
	
	public List<User> findAllUser(){
		return this.userRepository.findAll();
	}
	
	public User findUserByUserName(String userName) {
		return this.userRepository.findByUserName(userName);
	}
	
	
	public User findUserByUserNameAndUserPass(String userName, String userPass) {
		return this.userRepository.findByUserNameAndUserPass(userName, userPass);
	}

	public User findUserByUserNameAndToken(String userName, String token) {
		return this.userRepository.findByUserNameAndToken(userName, token);
	}
	
	public void updateToken(String token, String username) {
		this.userRepository.updateToken(token, username);
	}
	
	public void updateActiveUser(String username) {
		this.userRepository.updateActiveUser(username);
	}
	
	public ResponseEntity<ResponseDto> checkEmailVerification(User user){
		
		user = userRepository.findByUserName(user.getUserName());
		UserOrders userOrders = null;
		try {
			userOrders = userOrdersRepository.findByUser(user);
		}catch(Exception e) {
			userOrders = null;
		}
		
		try {
			if(user == null) 
			return new ResponseEntity<ResponseDto>(new ResponseDto("USERNAME_NOT_FOUND", "Email tidak ditemukan"),HttpStatus.BAD_REQUEST);
			
			Date date = user.getResendEmailDate(); 
			long resendTime = 0;
			if (date == null)
				resendTime = 0;
			else
			resendTime = date.getTime();         
			
			String accType;
			  if(user.getAccType()==null || (user.getAccType()!=null && user.getAccType().length()==0)) {
				  accType = "Personal";
			  }else {
				  accType = user.getAccType().equalsIgnoreCase("VENDOR")?"Vendor":"Personal";
			  }
				if (user != null && user.isActive() == true) {
					/*//////////////////////////////////////////////// MOVED TO OrderController.getWebsiteName
					 * if(userOrders == null || userOrders.getWebsiteName() == null ||
					 * (userOrders.getWebsiteName() != null &&
					 * userOrders.getWebsiteName().length()==0)) { return new
					 * ResponseEntity<ResponseDto>(new
					 * ResponseDto("SUCCESS","Email anda sudah terverifikasi",new
					 * ResponseDtoIsActive(user.isActive(),resendTime,null,accType)),HttpStatus.OK);
					 * }else { return new ResponseEntity<ResponseDto>(new
					 * ResponseDto("SUCCESS","Email anda sudah terverifikasi",new
					 * ResponseDtoIsActive(user.isActive(),resendTime,userOrders.getWebsiteName(),
					 * accType)),HttpStatus.OK); }
					 */
					return new ResponseEntity<ResponseDto>(new ResponseDto("SUCCESS","Email anda sudah terverifikasi",new ResponseDtoIsActive(user.isActive(),resendTime,accType)),HttpStatus.OK);
				} else 
					return new ResponseEntity<ResponseDto>(new ResponseDto("SUCCESS","Email anda belum terverifikasi",new ResponseDtoIsActive(user.isActive(),resendTime,accType)),HttpStatus.OK);
			
		}catch (Exception e) {
			log.debug(e.getMessage(), e);
			return new ResponseEntity<ResponseDto>(new ResponseDto("ERROR","Error system."),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	public ResponseEntity<Object> checkSingleLogin(UserDto userDto){
		
		User user = userRepository.findByUserName(userDto.getUserName());
		
		if(user == null) return new ResponseEntity<Object>(new ResponseDto("USERNAME_NOT_FOUND", "Email tidak ditemukan"),HttpStatus.BAD_REQUEST);
		
		user = userRepository.findByUserNameAndToken(userDto.getUserName(), (userDto.getToken()));
		
		if (user != null)
			return new ResponseEntity<Object>(new ResponseDto("SUCCESS", "Berhasil"),HttpStatus.OK);
		else 
			return new ResponseEntity<Object>(new ResponseDto("FORCE_SIGN_IN", "Anda Telah Login di Tempat lain"),HttpStatus.BAD_REQUEST);
	}

	public ResponseEntity<ResponseDto> login(String username, String pwd) {
		
		User user = userRepository.findByUserNameAndUserPass(username, securityUtil.encrypt(pwd));
		String token = securityUtil.getJWTToken(username);
		if(user!=null)
			return new ResponseEntity<ResponseDto>(new ResponseDto("Token LogIn User : "+username, token),HttpStatus.OK);
		else 
			return new ResponseEntity<ResponseDto>(new ResponseDto("Error", "Email tidak terdaftar"),HttpStatus.BAD_REQUEST);
	}

	public ResponseEntity<Object> login2(UserDto userDto) {
		
		User user = userRepository.findByUserName(userDto.getUserName());
		if(user==null) 
			return new ResponseEntity<Object>(new ResponseDto("USERNAME_NOT_FOUND", "Email tidak ditemukan"),HttpStatus.BAD_REQUEST);
		
		user = userRepository.findByUserNameAndUserPass(userDto.getUserName(), securityUtil.encrypt(userDto.getUserPass()));
		String token = securityUtil.getJWTToken(userDto.getUserName());
		if(user!=null)
			return new ResponseEntity<Object>(new ResponseDto("SUCCESS","", new UserDto(user.getName(), userDto.getUserName(), token)),HttpStatus.OK);
		else 
			return new ResponseEntity<Object>(new ResponseDto("INCORRECT_PASSWORD", "Password salah"),HttpStatus.BAD_REQUEST);
	}

	public ResponseEntity<Object> register(User user) {
		
		String regexUserName = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$"; 
		Pattern patternUserName = Pattern.compile(regexUserName); 
		
		String regexUserPass = "((?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,20})";
		Pattern patternUserPass = Pattern.compile(regexUserPass); 
		
		Matcher matcherUserName = patternUserName.matcher(user.getUserName());
		if(!Boolean.parseBoolean(String.valueOf(matcherUserName.matches()))) 
			return new ResponseEntity<Object>(new ResponseDto("INVALID_USERNAME","Format Email tidak sesuai"),HttpStatus.BAD_REQUEST);
		
		/*if(keyId!=null && keyId.trim().length()>0 && user.getUserPass()!=null) 
			user.setUserPass(this.securityUtil.decode(keyId, user.getUserPass()));*/
		
		if(user.getUserPass()==null || user.getUserPass().length()<8) 
			return new ResponseEntity<Object>(new ResponseDto("INVALID_PASSWORD","Password tidak boleh kosong (min 8 dan max 20)"),HttpStatus.BAD_REQUEST);
		
		Matcher matcherUserPass = patternUserPass.matcher(user.getUserPass());
		if(!Boolean.parseBoolean(String.valueOf(matcherUserPass.matches()))) 
			return new ResponseEntity<Object>(new ResponseDto("INVALID_PASSWORD","Password harus terdiri dari huruf kecil, huruf besar dan angka"),HttpStatus.BAD_REQUEST);
		
		if(user.getCreateBy()==null || !(user.getCreateBy().equals("WEB") || user.getCreateBy().equals("MOBILE")))
			return new ResponseEntity<Object>(new ResponseDto("INVALID","createBy is required(WEB / MOBILE)."),HttpStatus.BAD_REQUEST);
		
		if(user.getName()==null || user.getName().trim().length()==0)
			return new ResponseEntity<Object>(new ResponseDto("INVALID_NAMED","Nama tidak boleh kosong"),HttpStatus.BAD_REQUEST);
		
		if(user.getAccType()!=null && user.getAccType().trim().length()>0) {
			AccountType check = AccountType.valueOf(user.getAccType());
			if(check==null) 
				return new ResponseEntity<Object>(new ResponseDto("INVALID_TYPE","Tipe Akun tidak dapat digunakan"),HttpStatus.BAD_REQUEST);
		}else
			return new ResponseEntity<Object>(new ResponseDto("INVALID_TYPE","Tipe Akun tidak boleh kosong"),HttpStatus.BAD_REQUEST);
		
		String regexMobile = "(?=.*\\d).{11,15}";
		Pattern patternMobile = Pattern.compile(regexMobile);
		if(user.getMobile()==null || user.getMobile().length()>15 || !user.getMobile().startsWith("08"))
			return new ResponseEntity<Object>(new ResponseDto("INVALID_MOBILE","Nomor handphone tidak boleh kosong, (min 11 dan max 15, dan dimulai dengan 08XXXXXXXXXX)"),HttpStatus.BAD_REQUEST);
		Matcher matcherMobile = patternMobile.matcher(user.getMobile());
		if(!Boolean.parseBoolean(String.valueOf(matcherMobile.matches()))) 
			return new ResponseEntity<Object>(new ResponseDto("INVALID_MOBILE","Format nomor handphone tidak sesuai"),HttpStatus.BAD_REQUEST);
		
		User users = userRepository.findByUserName(user.getUserName());
		if(users==null) {
			user.setUserPass(securityUtil.encrypt(user.getUserPass()));
			user.setActive(Boolean.FALSE);
			user.setAdmin(Boolean.FALSE);
			user.setCreateOn(new Date());
			user.setEmail(user.getUserName());
			user.setToken(securityUtil.getJWTToken(user.getUserName()));
			userRepository.save(user);
			//result = new ResponseEntity<Object>(new ResponseDto("SUCCESS","Created User."),HttpStatus.CREATED);
			
			try {
				emailSenderService.sendValidationEmail(user.getUserName(), user.getName());
			} catch (Exception e) {
				e.printStackTrace();
			} 
			
			return new ResponseEntity<Object>(new ResponseDto("SUCCESS","", new UserDto(user.getName(), user.getUserName(), user.getToken())),HttpStatus.OK);
		}else 
			return new ResponseEntity<Object>(new ResponseDto("INVALID_USERNAME","Email sudah terdaftar"),HttpStatus.BAD_REQUEST);
		
	}

	public void validationUser(String encUser, HttpServletResponse response) {
		userRepository.updateActiveUser(this.emailSenderService.getUserName((securityUtil.decrypt(encUser))));
		try {
			response.sendRedirect("https://lovelyday-e15e2.firebaseapp.com/user");
		} catch (IOException e) {
			log.debug(e.getMessage(), e);
			e.printStackTrace();
		}
		
	}

	public ResponseEntity<ResponseDto> resendEmailValidation2(User user) {
		
		user = userRepository.findByUserName(user.getUserName());

		try {
			if(user != null && emailSenderService.sendValidationEmail(user.getUserName(), user.getName())) {
				Calendar calendar = Calendar.getInstance(); 
				LocalDateTime now = LocalDateTime.now();
				Date dateNow = convertToDateViaSqlTimestamp(now);
				Date resendDate = user.getResendEmailDate();
				log.info(dateNow+" "+resendDate);
				if (resendDate != null) {
					if(dateNow.compareTo(resendDate) < 0) {
						return new ResponseEntity<ResponseDto>(new ResponseDto("RESEND_FAILED", "Anda belum bisa kirim ulang email verifikasi"),HttpStatus.OK);
					}
				}
				dateNow = convertToDateViaSqlTimestamp(now.plus(Duration.of(5, ChronoUnit.MINUTES))); //add 5 minutes
				user.setResendEmailDate(dateNow);
				userRepository.save(user);
				
				//Setting the Calendar date and time to the given date and time
				calendar.setTime(dateNow);
				long resendTime = calendar.getTimeInMillis();
				//log.info("resendTime : "+resendTime);
				return new ResponseEntity<ResponseDto>(new ResponseDto("SUCCESS", "Email telah terkirim.",new ResendEmailValidationDto(resendTime)),HttpStatus.OK);
			}else 
				return new ResponseEntity<ResponseDto>(new ResponseDto("ERROR", "Email tidak terkirim"),HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			log.debug(e.getMessage(), e);
			return new ResponseEntity<ResponseDto>(new ResponseDto("ERROR","Error system."),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<ResponseDto> resendEmailValidation() {
		
		User user = userRepository.findByUserName(securityUtil.getUserName());
		
		try {
			if(user != null && emailSenderService.sendValidationEmail(user.getUserName(), user.getName())) {
				
				return new ResponseEntity<ResponseDto>(new ResponseDto("SUCCESS", "Email telah terkirim."),HttpStatus.OK);
			}else 
				return new ResponseEntity<ResponseDto>(new ResponseDto("ERROR", "Email tidak terkirim"),HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			log.debug(e.getMessage(), e);
			return new ResponseEntity<ResponseDto>(new ResponseDto("ERROR","Error system."),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	public Date convertToDateViaSqlTimestamp(LocalDateTime dateToConvert) {
	    return java.sql.Timestamp.valueOf(dateToConvert);
	}
	
	public ResponseEntity<ResponseDto> sentEmailChangePass(String to){
		try {
			if(to != null && emailSenderService.isSentEmailForgetPassword(to)) 
				return new ResponseEntity<ResponseDto>(new ResponseDto("SUCCESS", "Email telah terkirim."),HttpStatus.OK);
			else 
				return new ResponseEntity<ResponseDto>(new ResponseDto("ERROR", "Email tidak terkirim"),HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			log.debug(e.getMessage(), e);
			return new ResponseEntity<ResponseDto>(new ResponseDto("ERROR","Error system."),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	public ResponseEntity<ResponseDto> changeUserPassLogin(ChangePassDto changePassDto){
		try {
			if(changePassDto!=null && 
					(changePassDto.getUserName()!=null || changePassDto.getUserName().trim().length()>0) && 
					(changePassDto.getOldPassword()!=null || changePassDto.getOldPassword().trim().length()>0) && 
					(changePassDto.getNewPassword()!=null || changePassDto.getNewPassword().trim().length()>0)) {
				
				User user = userRepository.findByUserName(changePassDto.getUserName());
				if(user==null) 
					return new ResponseEntity<ResponseDto>(new ResponseDto("USERNAME_NOT_FOUND", "Email tidak ditemukan."),HttpStatus.BAD_REQUEST);
				
				user = userRepository.findByUserNameAndUserPass(changePassDto.getUserName(), securityUtil.encrypt(changePassDto.getOldPassword()));
				if(user!=null) {
					//validation UserPass
					if(changePassDto.getNewPassword().equals(changePassDto.getOldPassword())) 
						return new ResponseEntity<ResponseDto>(new ResponseDto("INVALID_PASSWORD","Password baru harus berbeda dengan password sebelumnya."),HttpStatus.BAD_REQUEST);
					
					String regexUserPass = "((?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,20})";
					Pattern patternUserPass = Pattern.compile(regexUserPass); 
					
					if(changePassDto.getNewPassword()==null || changePassDto.getNewPassword().trim().length()<8) 
						return new ResponseEntity<ResponseDto>(new ResponseDto("INVALID_PASSWORD","Password tidak boleh kosong (min 8 dan max 20)."),HttpStatus.BAD_REQUEST);
					
					Matcher matcherUserPass = patternUserPass.matcher(changePassDto.getNewPassword());
					if(!Boolean.parseBoolean(String.valueOf(matcherUserPass.matches()))) 
						return new ResponseEntity<ResponseDto>(new ResponseDto("INVALID_PASSWORD","Password harus terdiri dari huruf kecil, huruf besar dan angka."),HttpStatus.BAD_REQUEST);
					//
					user.setUserPass(this.securityUtil.encrypt(changePassDto.getNewPassword()));
					userRepository.save(user);
					return new ResponseEntity<ResponseDto>(new ResponseDto("SUCCESS","Password anda berhasil di ubah."),HttpStatus.OK);
					
				}else 
					return new ResponseEntity<ResponseDto>(new ResponseDto("INCORRECT_PASSWORD", "Password lama salah."),HttpStatus.BAD_REQUEST);
				
			}else return new ResponseEntity<ResponseDto>(new ResponseDto("REQUEST_ERROR","Mohon semua kolom diisi."),HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			log.debug(e.getMessage(), e);
			return new ResponseEntity<ResponseDto>(new ResponseDto("ERROR","Error system."),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	public ResponseEntity<ResponseDto> changeUserPass(UserDto userDto){
		try {
			String[] userData = this.emailSenderService.getuserNameAndUserPass(this.securityUtil.decrypt(userDto.getUserName()));
			if(userData!=null && userData.length==2) {
				User user = this.userRepository.findByUserName(userData[1]);
				if(user==null) 
					return new ResponseEntity<ResponseDto>(new ResponseDto("USERNAME_NOT_FOUND", userData[1]),HttpStatus.INTERNAL_SERVER_ERROR);
				user = this.userRepository.findByUserNameAndUserPass(userData[1], userData[0]);
				if(user!=null) {
					//validation UserPass
					
					if(userDto.getUserPass().equals(this.securityUtil.decrypt(userData[0]))) 
						return new ResponseEntity<ResponseDto>(new ResponseDto("INVALID_PASSWORD","Password baru harus berbeda dengan password sebelumnya."),HttpStatus.BAD_REQUEST);
					
					String regexUserPass = "((?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,20})";
					Pattern patternUserPass = Pattern.compile(regexUserPass); 
					
					if(userDto.getUserPass()==null || userDto.getUserPass().length()<8) 
						return new ResponseEntity<ResponseDto>(new ResponseDto("INVALID_PASSWORD","Password tidak boleh kosong (min 8 dan max 20)"),HttpStatus.BAD_REQUEST);
					
					Matcher matcherUserPass = patternUserPass.matcher(userDto.getUserPass());
					if(!Boolean.parseBoolean(String.valueOf(matcherUserPass.matches()))) 
						return new ResponseEntity<ResponseDto>(new ResponseDto("INVALID_PASSWORD","Password harus terdiri dari huruf kecil, huruf besar dan angka"),HttpStatus.BAD_REQUEST);
					//
					user.setUserPass(this.securityUtil.encrypt(userDto.getUserPass()));
					userRepository.save(user);
					return new ResponseEntity<ResponseDto>(new ResponseDto("SUCCESS", "Ubah Password Berhasil.", new UserDto(user.getName(), user.getUserName(), null)),HttpStatus.OK);
				} else 
					return new ResponseEntity<ResponseDto>(new ResponseDto("HAS_BEEN_USED", "Request has been used."),HttpStatus.OK);
			} else 
				return new ResponseEntity<ResponseDto>(new ResponseDto("ERROR","Error system."),HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			log.debug(e.getMessage(), e);
			return new ResponseEntity<ResponseDto>(new ResponseDto("ERROR","Error system."),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
