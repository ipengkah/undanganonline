package com.lovelyday.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lovelyday.dto.ResponseDto;
import com.lovelyday.dto.UserDto;
import com.lovelyday.model.User;
import com.lovelyday.service.UserService;

@CrossOrigin
@RestController
@RequestMapping("lld_api/")
public class ForgetEmailController {
	private Log log = LogFactory.getLog(getClass());
	
	@Autowired
	private UserService userService;
	
	@PostMapping("validationUser/requestChangePass")
	public ResponseEntity<ResponseDto> emailChangeUSerPass(@RequestBody User user) throws Exception {
		ResponseEntity<ResponseDto> result = null;
		try {
			result = userService.sentEmailChangePass(user.getUserName());
		} catch (Exception e) {
			log.debug(e.getMessage(), e);
			result = new ResponseEntity<ResponseDto>(new ResponseDto("ERROR","Error system."),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return result;
	}
	
	@PostMapping("validationUser/changePass")
	public ResponseEntity<ResponseDto> changeUserPass(@RequestBody UserDto userDto) throws Exception{
		ResponseEntity<ResponseDto> result = null;
		try {
			result = userService.changeUserPass(userDto);
		} catch (Exception e) {
			log.debug(e.getMessage(), e);
			result = new ResponseEntity<ResponseDto>(new ResponseDto("ERROR","Error system."),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return result;
	}
}
