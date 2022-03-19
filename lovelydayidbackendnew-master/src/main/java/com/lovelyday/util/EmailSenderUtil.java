package com.lovelyday.util;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.lovelyday.model.User;
import com.lovelyday.service.UserService;

@Service
public class EmailSenderUtil {
	
	@Autowired
    private JavaMailSender emailSender;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SecurityUtil securityUtil;
	
	private String headUser = "lovelyDays.s89g77dsf89g.";
	
	public String getUserName(String userName) {return userName.replace(headUser, "");}
	
	public String[] getuserNameAndUserPass(String userName) {return userName.split(headUser);}
	
	public Boolean sendValidationEmail(String to, String name) throws Exception {
		try {
			
			User user = null;
			if(to!=null && to.trim().length()>0) user = this.userService.findUserByUserName(to);
			if(user!=null) {
				String messageText = ""
						+ "<html>\n<body>\n<table>\n<tr>\n"
						+ "<th align=\"center\">\n"
						+ "<img src=\"https://i.ibb.co/W2pnjM5/Logo.png\" alt=\"\" width=\"207\" height=\"55\" border=\"0\">\n"
						+ "</th></tr>\n<tr><td>"
						+ "<font face=\"Courier New\"><pre>\n"
						+ "<br>Yang terhormat Bapak/Ibu. "+name+", \n\n"
						+ "Selamat datang di LovelyDay.id. \n"
						+ "Terima kasih telah mendaftar sebagai anggota LovelyDay.id.\n"
						+ "Untuk melanjutkan registrasi, klik link berikut ini : \n\n"
						+ "Link: <a href=\"https://lovelydayidbackendnew.herokuapp.com/lld_api/validationUser/"+securityUtil.encrypt(headUser+to)+"\">Validasi Email</a>\n\n"
						+ "Jika ada pertanyaan silahkan kontak kami \n"
						+ "di email lovelyday.undangan.online@gmail.com.<br><br>\n"
						+ "</pre></font></td></tr>\n</table>\n</body>\n</html>";

				return this.serdEmailMimeMessage(
						user.getNewEmail()!=null&&user.getNewEmail().trim().length()>0?user.getNewEmail():to, messageText, "Konfirmasi Email LovelyDay.id");
			} else return Boolean.FALSE;
		} catch (Exception e) {
			e.printStackTrace();
			return Boolean.FALSE;
		}
	}
	
	public Boolean isSentEmailForgetPassword(String to) throws Exception{
		try {
			User user = null;
			if(to!=null && to.trim().length()>0) user = this.userService.findUserByUserName(to);
			if(user!=null) {
				
				String messageText = this.securityUtil.encrypt(user.getUserPass()+headUser+to);
				
				messageText = ""
						+ "<html>\n<body>\n<table>\n<tr>\n"
						+ "<th align=\"center\">\n"
						+ "<img src=\"https://i.ibb.co/W2pnjM5/Logo.png\" alt=\"\" width=\"207\" height=\"55\" border=\"0\">\n"
						+ "</th></tr>\n<tr><td>"
						+ "<font face=\"Courier New\"><pre>\n"
						+ "<br>Kepada Bapak/Ibu. "+user.getName()+", \n\n"
						+ "Sepertinya Anda ingin mengubah password? \n\n"
						+ "Jika anda merasa tidak ingin mengubah password \n"
						+ "abaikan email ini. \n"
						+ "Untuk dapat mengubah password, klik link berikut ini : \n\n"
						+ "Link: <a href=\"https://lovelyday-e15e2.firebaseapp.com/resetpassword/"+messageText+"\">Ubah Password</a>\n\n"
						+ "Jika ada pertanyaan silahkan kontak kami \n"
						+ "di email lovelyday.undangan.online@gmail.com.<br><br>\n"
						+ "</pre></font></td></tr>\n</table>\n</body>\n</html>";
				
				return this.serdEmailMimeMessage(
						user.getNewEmail()!=null&&user.getNewEmail().trim().length()>0?user.getNewEmail():to, messageText, "Lupa Password LovelyDay.id");
			} else return Boolean.FALSE;
		} catch (Exception e) {
			e.printStackTrace();
			return Boolean.FALSE;
		}
	}
	
	private Boolean serdEmailMimeMessage(String to, String body, String subject) throws Exception{
		try {
			MimeMessage messages = emailSender.createMimeMessage();
		    MimeMessageHelper helper = new MimeMessageHelper(messages, true);
		    
		    helper.setFrom("lovelyDay@admin.com");
		    helper.setTo(to);
		    helper.setSubject(subject);
		    helper.setText(body, true);
		    //messages.setContent(messageText, "text/html");

		    emailSender.send(messages);
		    
		    return Boolean.TRUE;
		}catch (Exception e) {
			e.printStackTrace();
			return Boolean.FALSE;
		}
	}

}
