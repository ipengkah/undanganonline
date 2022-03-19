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
import com.lovelyday.dto.OrdersDto;
import com.lovelyday.dto.ChangeTypeDto;
import com.lovelyday.dto.HopeMessageDto;
import com.lovelyday.service.UserOrdersService;

@CrossOrigin
@RestController
@RequestMapping("lld_api/")
public class OrderController {
	
	private Log log = LogFactory.getLog(getClass());
	
	@Autowired
	private UserOrdersService userOrdersService;
	
	@PostMapping("checkWebsiteName")
	public ResponseEntity<ResponseDto> checkWebsiteName(@RequestBody OrdersDto ordersDto) throws Exception{
		try {
			return this.userOrdersService.checkWebsiteName(ordersDto);
		}catch (Exception e) {
			log.debug(e.getMessage(), e);
			return new ResponseEntity<ResponseDto>(new ResponseDto("ERROR","Error system."),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("addOrder")
	public ResponseEntity<ResponseDto> addOrder(@RequestBody OrdersDto ordersDto) throws Exception{
		try {
			return this.userOrdersService.saveOrder(ordersDto);
		}catch (Exception e) {
			log.debug(e.getMessage(), e);
			return new ResponseEntity<ResponseDto>(new ResponseDto("ERROR","Error controller system."),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(value = {"getOrder","getOrder/","getOrder/{tamu}"})
	public ResponseEntity<ResponseDto> getOrder(@RequestBody OrdersDto ordersDto, @PathVariable(value = "tamu", required = false) String tamu) throws Exception{
		try {
			return this.userOrdersService.getOrdersDetailByWebsiteName(ordersDto.getWebsiteName(), (tamu!=null?tamu.replace("+", " "):""), null, Boolean.FALSE);
		}catch (Exception e) {
			log.debug(e.getMessage(), e);
			return new ResponseEntity<ResponseDto>(new ResponseDto("ERROR","Error controller system."),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("getOrderCheck")
	public ResponseEntity<ResponseDto> getOrderCheck(@RequestBody OrdersDto ordersDto) throws Exception{
		try {
			if(ordersDto.getWebsiteName()!=null&&ordersDto.getWebsiteName().trim().length()>0 
					&& ordersDto.getUserName()!=null&&ordersDto.getUserName().trim().length()>0)
				return this.userOrdersService.getOrdersDetailByWebsiteName(ordersDto.getWebsiteName(), "", ordersDto.getUserName(), Boolean.TRUE);
			else return new ResponseEntity<ResponseDto>(new ResponseDto("ERROR","Username dan Website Name tidak boleh kosong"),HttpStatus.OK);
		}catch (Exception e) {
			log.debug(e.getMessage(), e);
			return new ResponseEntity<ResponseDto>(new ResponseDto("ERROR","Error controller system."),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("checkUsersOrder")
	public ResponseEntity<ResponseDto> checkUsersOrder(@RequestBody OrdersDto ordersDto) throws Exception{
		try {
			return this.userOrdersService.checkUsersOrder(ordersDto);
		}catch (Exception e) {
			log.debug(e.getMessage(), e);
			return new ResponseEntity<ResponseDto>(new ResponseDto("ERROR","Error controller system."),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("updateBychangeType")
	public ResponseEntity<ResponseDto> updateBychangeType(@RequestBody ChangeTypeDto changeTypeDto)throws Exception{
		try {
			return this.userOrdersService.updateBychangeType(changeTypeDto);
		}catch (Exception e) {
			log.debug(e.getMessage(), e);
			return new ResponseEntity<ResponseDto>(new ResponseDto("ERROR","Error controller system."),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("getWebsiteName")
	public ResponseEntity<ResponseDto> getWebsiteName(@RequestBody OrdersDto ordersDto)throws Exception{
		try {
			return this.userOrdersService.getWebsiteNameByUsername(ordersDto);
		}catch (Exception e) {
			log.debug(e.getMessage(), e);
			return new ResponseEntity<ResponseDto>(new ResponseDto("ERROR","Error controller system."),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("saveHopeMessage")
	public ResponseEntity<ResponseDto> saveHopeMessage(@RequestBody HopeMessageDto hopeMessageDto)throws Exception{
		try {
			return this.userOrdersService.hopeMessageAdd(hopeMessageDto);
		}catch (Exception e) {
			log.debug(e.getMessage(), e);
			return new ResponseEntity<ResponseDto>(new ResponseDto("ERROR","Error controller system."),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("getListHopeMessage")
	public ResponseEntity<ResponseDto> getListHopeMessage(@RequestBody HopeMessageDto hopeMessageDto)throws Exception{
		try {
			return this.userOrdersService.getHopeMessageList(hopeMessageDto.getWebsiteName());
		}catch (Exception e) {
			log.debug(e.getMessage(), e);
			return new ResponseEntity<ResponseDto>(new ResponseDto("ERROR","Error controller system."),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("getThumbnail/{param}")
	public ResponseEntity<ResponseDto> getThumbnail(@PathVariable("param") String param) throws IOException{
		try {
			return this.userOrdersService.getThumbnail(param);
		} catch (Exception e) {
			log.debug(e.getMessage(), e);
			return new ResponseEntity<ResponseDto>(new ResponseDto("ERROR","Error controller system."),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
