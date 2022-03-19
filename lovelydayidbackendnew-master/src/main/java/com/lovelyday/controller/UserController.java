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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lovelyday.dto.ChangePassDto;
import com.lovelyday.dto.ResponseDto;
import com.lovelyday.dto.UserDto;
import com.lovelyday.model.User;
import com.lovelyday.service.UserService;

@CrossOrigin
@RestController
@RequestMapping("lld_api/")
public class UserController {
	private Log log = LogFactory.getLog(getClass());
	
	@Autowired
	private UserService userService;
	
	@PostMapping("user/emailVerification")
	public ResponseEntity<ResponseDto> emailVerification(@RequestBody User user){
		ResponseEntity<ResponseDto> result = null;
		try {
			result = userService.checkEmailVerification(user);
		}catch (Exception e) {
			log.debug(e.getMessage(), e);
			result = new ResponseEntity<ResponseDto>(new ResponseDto("ERROR","Error system."),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return result;
	}
	
	
	@PostMapping("user/singlelogin")
	public ResponseEntity<Object> singleLogin(@RequestBody UserDto userDto){
		ResponseEntity<Object> result = null;
		
		try {
			result = userService.checkSingleLogin(userDto);
		}catch (Exception e) {
			log.debug(e.getMessage(), e);
			result = new ResponseEntity<Object>(new ResponseDto("ERROR","Error system."),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return result;
	}
	
	
	
	@PostMapping("user/logIn")
	public ResponseEntity<ResponseDto> login(@RequestParam("user") String username, @RequestParam("password") String pwd) {
		ResponseEntity<ResponseDto> result = null;
		
		try {
			result = userService.login(username, pwd);
		}catch (Exception e) {
			log.debug(e.getMessage(), e);
			result = new ResponseEntity<ResponseDto>(new ResponseDto("ERROR","Error system."),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return result;
		
	}
	
	@PostMapping("user/logIn2")
	public ResponseEntity<Object> login2(@RequestBody UserDto userDto) {
		ResponseEntity<Object> result = null;
		
		try {
			result = userService.login2(userDto);
		}catch (Exception e) {
			log.debug(e.getMessage(), e);
			result = new ResponseEntity<Object>(new ResponseDto("ERROR","Error system."),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		return result;
	}
	
	@PostMapping("user/register")
	public ResponseEntity<Object> registerUser(/*@RequestParam String keyId*/ @RequestBody User user) throws Exception {
		ResponseEntity<Object> result = null;
		
		try {
			result = userService.register(user);
		}catch (Exception e) {
			log.debug(e.getMessage(), e);
			result = new ResponseEntity<Object>(new ResponseDto("ERROR","Error system."),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return result;
	}
	
	@PostMapping("user/changePassLogin")
	public ResponseEntity<ResponseDto> changePass(/*@RequestParam String keyId*/ @RequestBody ChangePassDto changePassDto) throws Exception {
		ResponseEntity<ResponseDto> result = null;
		try {
			result = userService.changeUserPassLogin(changePassDto);
		}catch (Exception e) {
			log.debug(e.getMessage(), e);
			result = new ResponseEntity<ResponseDto>(new ResponseDto("ERROR","Error system."),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return result;
	}
}
