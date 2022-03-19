package com.lovelyday.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lovelyday.dto.ResponseDto;
import com.lovelyday.model.User;
import com.lovelyday.service.UserService;

@CrossOrigin
@RestController
@RequestMapping("lld_api/")
public class ValidationEmailController {
	private Log log = LogFactory.getLog(getClass());
	
	@Autowired
	private UserService userService;
	
	@GetMapping("validationUser/{encUser}")
	public void validateUser(@PathVariable("encUser") String encUser, HttpServletResponse response) throws IOException{
		try {
			userService.validationUser(encUser, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@PostMapping("validationUser/resendMail")
	public ResponseEntity<ResponseDto> resendEmailValidatin2(@RequestBody User user) throws Exception {
		ResponseEntity<ResponseDto> result = null;
		try {
			result = userService.resendEmailValidation2(user);
		} catch (Exception e) {
			log.debug(e.getMessage(), e);
			result = new ResponseEntity<ResponseDto>(new ResponseDto("ERROR","Error system."),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return result;
		
	}
	
	@PostMapping("resendValidation")
	public ResponseEntity<ResponseDto> resendEmailValidatin() throws Exception {
		ResponseEntity<ResponseDto> result = null;
		try {
			result = userService.resendEmailValidation();
		} catch (Exception e) {
			log.debug(e.getMessage(), e);
			result = new ResponseEntity<ResponseDto>(new ResponseDto("ERROR","Error system."),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return result;
		
	}
}
