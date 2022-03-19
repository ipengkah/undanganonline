package com.lovelyday.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.lovelyday.model.UploadFile;
import com.lovelyday.service.UploadFileService;

@CrossOrigin
@RestController
@RequestMapping("lld_api/")
public class ContentController {
	
	private Log log = LogFactory.getLog(getClass());
	
	@Autowired
	private UploadFileService UploadFileService;

	@Autowired
	ResourceLoader resourceLoader;  
	
	@GetMapping(value="image/{id}", 
			produces= {
					MediaType.IMAGE_JPEG_VALUE, 
					MediaType.IMAGE_PNG_VALUE})
	public @ResponseBody byte[] getImage(@PathVariable("id") String id) throws IOException{
		try {
			id = id.trim();
			log.info("id-> withLD? "+id.substring(0, 2));
			if(id.substring(0, 2).equals("LD")) {
				
				Resource resource = resourceLoader.getResource("classpath:/static/images/"+id+".png");
				if (resource.exists()) {
					InputStream in = getClass().getResourceAsStream("/static/images/"+id+".png");
					return IOUtils.toByteArray(in);
				} else {
					resource = resourceLoader.getResource("classpath:/static/images/"+id+".jpg");
					if (resource.exists()) {
					InputStream in = getClass().getResourceAsStream("/static/images/"+id+".jpg");
					return IOUtils.toByteArray(in);
					} else return null;
				}
			}else {
				UploadFile uploadFile = this.UploadFileService.findByUploadFileId(id!=null&&id.length()>0?Long.valueOf(id):0);
				if(uploadFile!=null) {
					InputStream in = new ByteArrayInputStream(uploadFile.getFileConyenByte());
					return IOUtils.toByteArray(in);
				}else return null;
			}
		}catch (Exception e) {
			log.debug(e.getMessage(), e);
			return null;
		}
		
	}
	
}

/*
 * public byte[] getImage() throws IOException { InputStream in =
 * getClass().getResourceAsStream("/com/baeldung/produceimage/image.jpg");
 * return IOUtils.toByteArray(in); }
 */
