package com.lovelyday.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.lovelyday.dto.GetOrdersDto;
import com.lovelyday.dto.HopeMessageDto;
import com.lovelyday.dto.ChangeTypeDto;
import com.lovelyday.dto.DahboardDto;
import com.lovelyday.dto.DahboardDtoDate;
import com.lovelyday.dto.OrdersDto;
import com.lovelyday.dto.QuoteDto;
import com.lovelyday.dto.ResponseDto;
import com.lovelyday.dto.ResponseDtoGetWebsiteName;
import com.lovelyday.dto.ResponseDtoGetWebsiteNames;
import com.lovelyday.dto.ThumbnailDto;
import com.lovelyday.model.HopeMessage;
import com.lovelyday.model.UploadFile;
import com.lovelyday.model.User;
import com.lovelyday.model.UserOrders;
import com.lovelyday.repository.HopeMessageRepository;
import com.lovelyday.repository.UserOrdersRepository;
import com.lovelyday.repository.UserRepository;
import com.lovelyday.util.ValidationUntil;


@Service
public class UserOrdersService {
	
	private Log log = LogFactory.getLog(getClass());
	
	@Resource
	private UserOrdersRepository userOrdersRepository;
	
	@Autowired
	private UploadFileService uploadFileService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private HopeMessageRepository hopeMessageRepository;
	
	@Autowired
	ValidationUntil validationUntil;
	
	public void saveUserOrders(UserOrders userOrders) {
		try {
			this.userOrdersRepository.save(userOrders);
		} catch (Exception e) { log.debug(e.getMessage(), e); }
	}
	
	public List<UserOrders> findAllUser(){
		return this.userOrdersRepository.findAll();
	}
	
	public UserOrders findUserOrdersByUserAndWebsiteName(User user, String websiteName) {
		return this.userOrdersRepository.findByUserAndWebsiteName(user, websiteName);
	}
	
	public UserOrders findUserOrderByWebsiteName(String websiteName) {
		return this.userOrdersRepository.findByWebsiteName(websiteName);
	}

	public List<UserOrders> findListByUser(User user){
		return this.userOrdersRepository.findListByUser(user);
	}

	public User findUserByUserName(String userName) {
		return this.userRepository.findByUserName(userName);
	}
	
	public UserOrders findBynamMempelaiPria(String namaMempelaiPria) {
		return this.userOrdersRepository.findByWebsiteName(namaMempelaiPria);
	}
	
	   // enum for updateBychangeType 
	  private static final String namaMempelaiPria = "namaMempelaiPria";
      private static final String namaMempelaiWanita = "namaMempelaiWanita";
      private static final String namaPanggilanPria = "namaPanggilanPria";
      private static final String namaPanggilanWanita = "namaPanggilanWanita";
      private static final String namaOrangTuaPria = "namaOrangTuaPria";
      private static final String namaOrangTuaWanita = "namaOrangTuaWanita";
      private static final String akadDate = "akadDate";
      private static final String akadTime = "akadTime";
      private static final String resepsiDate = "resepsiDate";
      private static final String resepsiTime = "resepsiTime";
      private static final String koordinatLokasi = "koordinatLokasi";
      private static final String alamat = "alamat";
      
     // enum for order type
      private static final String Name = "Name";
      private static final String Quote = "Quote";
      
      // enum for quote
      private static final String quote = "quote";
      private static final String quoteGiver = "quoteGiver";
	   
	
	public ResponseEntity<ResponseDto> checkWebsiteName(OrdersDto ordersDto){
		try {
			if(ordersDto.getWebsiteName()==null 
					|| ordersDto.getWebsiteName().trim().length()==0) return new ResponseEntity<ResponseDto>(new ResponseDto("INVALID_USERNAME","Nama Website harus diisi."),HttpStatus.BAD_REQUEST);
			if(ordersDto.getWebsiteName().contains(" ")) return new ResponseEntity<ResponseDto>(new ResponseDto("INVALID_USERNAME","Nama Website tidak boleh mengandung spasi."),HttpStatus.OK);
			
			UserOrders userOrders = this.findUserOrderByWebsiteName(ordersDto.getWebsiteName());
			
			if(userOrders!=null) return new ResponseEntity<ResponseDto>(new ResponseDto("WEBSITE_NAME_USED","Nama Website sudah dipakai."),HttpStatus.OK);
			else return new ResponseEntity<ResponseDto>(new ResponseDto("SUCCESS","Nama Website bisa dipakai."),HttpStatus.OK);
		} catch (Exception e) {
			log.debug(e.getMessage(), e);
			return new ResponseEntity<ResponseDto>(new ResponseDto("ERROR","Error system."),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	public ResponseEntity<ResponseDto> saveOrder(OrdersDto ordersDto){
		try {
			/*if(ordersDto.getNamaMempelaiPria()!=null && ordersDto.getNamaMempelaiWanita()!=null 
					&& ordersDto.getNamaPanggilanPria()!=null && ordersDto.getNamaPanggilanWanita()!=null 
					&& ordersDto.getFotoPria()!=null && ordersDto.getFotoWanita()!=null 
					&& ordersDto.getOrangtuaPria()!=null && ordersDto.getOrangtuaWanita()!=null 
					&& (ordersDto.getTanggalAkad()!=null || ordersDto.getTanggalResepsi()!=null) 
					&& (ordersDto.getWaktuAkad()!=null || ordersDto.getWaktuResepsi()!=null)
					&& ordersDto.getPaket()!=null && ordersDto.getKodeTemplate()!=null 
					&& ordersDto.getAlamatLokasi()!=null && ordersDto.getKoordinatLokasi()!=null 
					&& ordersDto.getWebsiteName()!=null) {*/
				User user = userService.findUserByUserName(ordersDto.getUserName());
				if(user==null)
					return new ResponseEntity<ResponseDto>(new ResponseDto("USER_NOT_FOUND","Terdapat masalah pada akun."),HttpStatus.BAD_REQUEST);
				
				Integer orders = this.findListByUser(user).size();
				System.out.println("ADD_ORDERS || this user ("+user.getUserName()+") total order before "+orders);
				
				if(user.getAccType()!=null && user.getAccType().equals("PERSONAL") && orders!=null && orders>0)
					return new ResponseEntity<ResponseDto>(new ResponseDto("ORDER_FAILED","Akun anda hanya dapat membuat 1 undangan."),HttpStatus.BAD_REQUEST);
				
				if((this.findUserOrderByWebsiteName(ordersDto.getWebsiteName()))!=null) 
					return new ResponseEntity<ResponseDto>(new ResponseDto("WEBSITENAME_HAS_BEEN_USED","Nama website telah terpakai."),HttpStatus.BAD_REQUEST);
			
				if(!(ordersDto.getWebsiteName()!=null && ordersDto.getWebsiteName().trim().length()>0)) 
					return new ResponseEntity<ResponseDto>(new ResponseDto("WEBSITENAME_EMPTY","Namawebsite harus diisi."),HttpStatus.BAD_REQUEST);
			
				if(!(ordersDto.getNamaMempelaiPria()!=null && ordersDto.getNamaMempelaiPria().trim().length()>0 && ordersDto.getNamaMempelaiWanita()!=null && ordersDto.getNamaMempelaiWanita().trim().length()>0 
						&& ordersDto.getNamaPanggilanPria()!=null && ordersDto.getNamaPanggilanPria().trim().length()>0 && ordersDto.getNamaPanggilanWanita()!=null && ordersDto.getNamaPanggilanWanita().trim().length()>0)) 
					return new ResponseEntity<ResponseDto>(new ResponseDto("ONE_OF_THE_NAMES_EMPTY","Nama dan nama panggilan masing masing mempelai tidak boleh kosong."),HttpStatus.BAD_REQUEST);
				
				if(!(ordersDto.getFotoPria()!=null && ordersDto.getFotoPria().trim().length()>0 && ordersDto.getFotoWanita()!=null && ordersDto.getFotoWanita().trim().length()>0))
					return new ResponseEntity<ResponseDto>(new ResponseDto("PHOTOS_ARE_REQUIRED","Kedua foto mempelai harus dimasukkan."),HttpStatus.BAD_REQUEST);
				else if(!((ordersDto.getFotoPria().toLowerCase()).replace("data:", "").startsWith("image") && (ordersDto.getFotoWanita().toLowerCase()).replace("data:", "").startsWith("image")))
					return new ResponseEntity<ResponseDto>(new ResponseDto("MUST_PHOTO","File harus berupa foto."),HttpStatus.BAD_REQUEST);
				
				if(!(ordersDto.getOrangtuaPria()!=null && ordersDto.getOrangtuaPria().trim().length()>0 && ordersDto.getOrangtuaWanita()!=null && ordersDto.getOrangtuaWanita().trim().length()>0)) 
					return new ResponseEntity<ResponseDto>(new ResponseDto("PARENTS_NAME_IS_REQUIRED","Kedua nama orang tua mempelai harus diisi."),HttpStatus.BAD_REQUEST);
				
				if(!((ordersDto.getTanggalAkad()!=null && ordersDto.getTanggalAkad()>0 && ordersDto.getWaktuAkad()!=null && ordersDto.getWaktuAkad().trim().length()>0) || 
						(ordersDto.getTanggalResepsi()!=null && ordersDto.getTanggalResepsi()>0 && ordersDto.getWaktuResepsi()!=null && ordersDto.getWaktuResepsi().trim().length()>0)))
					return new ResponseEntity<ResponseDto>(new ResponseDto("ONE_OF_DATES_IS_REQUIRED","Salah satu tanggal dan waktu harus diisi, bisa diisi hanya tanggal dan waktu akad atau tanggal dan waktu resepsi."),HttpStatus.BAD_REQUEST);
				
				if(!(ordersDto.getPaket()!=null && ordersDto.getPaket().trim().length()>0 && ordersDto.getKodeTemplate()!=null && ordersDto.getKodeTemplate().trim().length()>0)) 
					return new ResponseEntity<ResponseDto>(new ResponseDto("PRODUCT_EMPTY","Paket dan Template undangan harus diisi."),HttpStatus.BAD_REQUEST);
				
				if(!(ordersDto.getAlamatLokasi()!=null && ordersDto.getAlamatLokasi().trim().length()>0 && ordersDto.getKoordinatLokasi()!=null && ordersDto.getKoordinatLokasi().trim().length()>0)) 
					return new ResponseEntity<ResponseDto>(new ResponseDto("ADRRESS_EMPTY","Alamat dan koordinasi acara harus dimasukkan."),HttpStatus.BAD_REQUEST);
				
				//validasi nama mempelai
				String validation = validationUntil.validationName(ordersDto.getNamaMempelaiPria());
				if(validation!=null) 
					return new ResponseEntity<ResponseDto>(new ResponseDto("INVALID_NAME","(Nama lengkap mempelai pria) "+validation),HttpStatus.BAD_REQUEST);
				validation = validationUntil.validationName(ordersDto.getNamaMempelaiWanita());
				if(validation!=null) 
					return new ResponseEntity<ResponseDto>(new ResponseDto("INVALID_NAME","(Nama lengkap mempelai wanita) "+validation),HttpStatus.BAD_REQUEST);
				validation = validationUntil.validationName(ordersDto.getNamaPanggilanPria());
				if(validation!=null) 
					return new ResponseEntity<ResponseDto>(new ResponseDto("INVALID_NAME","(Nama panggil mempelai pria) "+validation),HttpStatus.BAD_REQUEST);
				validation = validationUntil.validationName(ordersDto.getNamaPanggilanWanita());
				if(validation!=null) 
					return new ResponseEntity<ResponseDto>(new ResponseDto("INVALID_NAME","(Nama panggil mempelai wanita) "+validation),HttpStatus.BAD_REQUEST);
				
				//validasi nama orang tua mempelai
				String[] familyNames = ordersDto.getOrangtuaPria().split(" & ");
				if(familyNames.length==2) {
					for(String name:familyNames) {
						validation = validationUntil.validationName(name);
						if(validation!=null) 
							return new ResponseEntity<ResponseDto>(new ResponseDto("INVALID_NAME","(Nama orang tua mempelai pria) "+validation),HttpStatus.BAD_REQUEST);
					}
				}else 
					return new ResponseEntity<ResponseDto>(new ResponseDto("INVALID_NAME","(Nama orang tua mempelai pria) Kedua orang tua harus dicantumkan, dengan format \"Nama Bapak & Nama Ibu\""),HttpStatus.BAD_REQUEST);
				
				familyNames = ordersDto.getOrangtuaWanita().split(" & ");
				if(familyNames.length==2) {
					for(String name:familyNames) {
						validation = validationUntil.validationName(name);
						if(validation!=null) 
							return new ResponseEntity<ResponseDto>(new ResponseDto("INVALID_NAME","(Nama orang tua mempelai wanita) "+validation),HttpStatus.BAD_REQUEST);
					}
				}else 
					return new ResponseEntity<ResponseDto>(new ResponseDto("INVALID_NAME","(Nama orang tua mempelai wanita) Kedua orang tua harus dicantumkan, dengan format \"Nama Bapak & Nama Ibu\""),HttpStatus.BAD_REQUEST);
				
				//save
				UserOrders userOrders = new UserOrders();
				userOrders.setUser(user);
				userOrders.setWebsiteName(ordersDto.getWebsiteName());
				
				userOrders.setGroomName(ordersDto.getNamaMempelaiPria());
				userOrders.setBrideName(ordersDto.getNamaMempelaiWanita());
				userOrders.setGroomShortName(ordersDto.getNamaPanggilanPria());
				userOrders.setBrideShortName(ordersDto.getNamaPanggilanWanita());
				
				userOrders.setGroomsParent(ordersDto.getOrangtuaPria());
				userOrders.setBridesParent(ordersDto.getOrangtuaWanita());
				
				userOrders.setAddress(ordersDto.getAlamatLokasi());
				userOrders.setLocCoordinate(ordersDto.getKoordinatLokasi());
				userOrders.setProduct(ordersDto.getPaket());
				userOrders.setTemplateCode(ordersDto.getKodeTemplate());
				
				if(ordersDto.getWaktuAkad()!=null && ordersDto.getWaktuAkad().trim().length()>0) {
					validation = validationUntil.validationTimeFromTo(ordersDto.getWaktuAkad()); 
					if(validation!=null) return new ResponseEntity<ResponseDto>(new ResponseDto("INVALID_TIME","(Waktu akad) "+validation),HttpStatus.BAD_REQUEST);
					userOrders.setAkadTime(ordersDto.getWaktuAkad());
				}
				
				if(ordersDto.getWaktuResepsi()!=null && ordersDto.getWaktuResepsi().trim().length()>0) {
					validation = validationUntil.validationTimeFromTo(ordersDto.getWaktuResepsi()); 
					if(validation!=null) return new ResponseEntity<ResponseDto>(new ResponseDto("INVALID_TIME","(Waktu resepsi) "+validation),HttpStatus.BAD_REQUEST);
					userOrders.setReceptionTime(ordersDto.getWaktuResepsi());
				}
				
				Calendar calendar = Calendar.getInstance();
				if(ordersDto.getTanggalAkad()!=null && ordersDto.getTanggalAkad()>0) {
					calendar.setTimeInMillis(ordersDto.getTanggalAkad());
					userOrders.setAkadDate(calendar.getTime());
				}
				if(ordersDto.getTanggalResepsi()!=null && ordersDto.getTanggalResepsi()>0) {
					calendar.setTimeInMillis(ordersDto.getTanggalResepsi());
					userOrders.setReceptionDate(calendar.getTime());
				}
				
				//save foto pria
				String ct = ordersDto.getFotoPria().substring(0, ordersDto.getFotoPria().indexOf(";base64,")+8);
				UploadFile uploadFile = new UploadFile();
				uploadFile.setFileConyenByte(Base64.decodeBase64((ordersDto.getFotoPria().replaceAll(ct, "")).getBytes()));
				ct = ct.replaceAll("data:", "");
				ct = ct.replaceAll(";base64,", "");
				uploadFile.setFileType(ct);
				uploadFile.setCreateOn(new Date());
				uploadFileService.saveUploadFile(uploadFile);
				userOrders.setGroomPictId(uploadFile.getUploadFileId());
				
				//save foto wanita
				ct = ordersDto.getFotoWanita().substring(0, ordersDto.getFotoWanita().indexOf(";base64,")+8);
				uploadFile = new UploadFile();
				uploadFile.setFileConyenByte(Base64.decodeBase64((ordersDto.getFotoWanita().replaceAll(ct, "")).getBytes()));
				ct = ct.replaceAll("data:", "");
				ct = ct.replaceAll(";base64,", "");
				uploadFile.setFileType(ct);
				uploadFile.setCreateOn(new Date());
				uploadFileService.saveUploadFile(uploadFile);
				userOrders.setBridePictId(uploadFile.getUploadFileId());
				
				user.getUserOrders().add(userOrders);
				userService.saveUser(user);
				
				return new ResponseEntity<ResponseDto>(new ResponseDto("SUCCESS","Undangan berhasil dibuat.",userOrders),HttpStatus.OK);
			/*} else 
				return new ResponseEntity<ResponseDto>(new ResponseDto("FAILED","Semua data harus diisi."),HttpStatus.BAD_REQUEST);*/
		} catch (Exception e) {
			log.debug(e.getMessage(), e);
			return new ResponseEntity<ResponseDto>(new ResponseDto("ERROR","Error service system."),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<ResponseDto> getOrdersDetailByWebsiteName(String websiteName, String tamu, String userName, boolean check){
		try {
			UserOrders userOrders = this.findUserOrderByWebsiteName(websiteName);
			if(check) {
				User user = userService.findUserByUserName(userName);
				if(user!=null) {
					System.out.println(" ------------------------------------- "+user.getUserId()+" / "+websiteName);
					
					userOrders = this.findUserOrdersByUserAndWebsiteName(user, websiteName);
					if(userOrders!=null) return new ResponseEntity<ResponseDto>(new ResponseDto("SUCCESS","",new GetOrdersDto(userOrders, tamu)),HttpStatus.OK);
					else return new ResponseEntity<ResponseDto>(new ResponseDto("WEBSITE_NOT_EXIST","Nama Undangan tidak ditemukan"),HttpStatus.OK);
				}else 
					return new ResponseEntity<ResponseDto>(new ResponseDto("WEBSITENAME_NOT_FOUND","User tidak ditemukan."),HttpStatus.OK);
			}else {
				if(userOrders!=null) return new ResponseEntity<ResponseDto>(new ResponseDto("SUCCESS","",new GetOrdersDto(userOrders, tamu)),HttpStatus.OK);
				else return new ResponseEntity<ResponseDto>(new ResponseDto("WEBSITENAME_NOT_FOUND","Nama Website tidak ditemukan."),HttpStatus.OK);
			}
		} catch (Exception e) {
			log.debug(e.getMessage(), e);
			return new ResponseEntity<ResponseDto>(new ResponseDto("ERROR","Error service system."),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<ResponseDto> checkUsersOrder(@RequestBody OrdersDto ordersDto){
		
		try {
			User user = userService.findUserByUserName(ordersDto.getUserName());
			UserOrders userOrders = this.findUserOrderByWebsiteName(ordersDto.getWebsiteName());
			
			//handle reception date null
			Long receptionDate = userOrders.getReceptionDate() == null ? 0  : userOrders.getReceptionDate().getTime();
		
				  if (user != null && user.isActive() == true) {
					  if(this.findUserOrderByWebsiteName(ordersDto.getWebsiteName()) !=null) {
						  if (ordersDto.getOrderType().equalsIgnoreCase(Name)) {
							  return new ResponseEntity<ResponseDto>(new ResponseDto("SUCCESS", "Berhasil.",
									 new DahboardDto(userOrders.getGroomName(), userOrders.getBrideName(), 
										  		 userOrders.getGroomShortName(),userOrders.getBrideShortName(),
										  		 userOrders.getGroomsParent(), userOrders.getBridesParent())),HttpStatus.OK);
						  } else if (ordersDto.getOrderType().equalsIgnoreCase(Quote)){
							String quote = "";
							String quotegiver = "";
							
							if (userOrders.getQuote() != null && userOrders.getQuoteGiver() != null) {
								quote = userOrders.getQuote();
								quotegiver = userOrders.getQuoteGiver();
							}
							  return new ResponseEntity<ResponseDto>(new ResponseDto("SUCCESS", "Berhasil.",
										 new QuoteDto(quote,quotegiver)),HttpStatus.OK);
						  }else 
							  return new ResponseEntity<ResponseDto>(new ResponseDto("SUCCESS", "Berhasil.",
										 new DahboardDtoDate(userOrders.getAkadDate().getTime(), userOrders.getAkadTime(), 
												 receptionDate,userOrders.getReceptionTime(),
											  		 userOrders.getLocCoordinate(), userOrders.getAddress())),HttpStatus.OK);

					  } else {
						  return new ResponseEntity<ResponseDto>(new ResponseDto("WEBSITE_NOT_EXIST","Nama Undangan tidak ditemukan",null),HttpStatus.BAD_REQUEST);
					  }
				  } else 
					  return new ResponseEntity<ResponseDto>(new ResponseDto("NOT_VERIFIED","Email anda belum terverifikasi.",null),HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			log.debug(e.getMessage(), e);
			return new ResponseEntity<ResponseDto>(new ResponseDto("ERROR","Error system."),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<ResponseDto> updateBychangeType(ChangeTypeDto changeTypeDto) {
		try {
			
			User user = userService.findUserByUserName(changeTypeDto.getUserName());
		    UserOrders userOrders = this.findUserOrderByWebsiteName(changeTypeDto.getWebsiteName());
			Calendar calendar = Calendar.getInstance();
			if(user == null) 
				return new ResponseEntity<ResponseDto>(new ResponseDto("USERNAME_NOT_FOUND", "Username tidak ditemukan"),HttpStatus.BAD_REQUEST);
			
		    if(userOrders ==null) 
				return new ResponseEntity<ResponseDto>(new ResponseDto("WEBSITE_NOT_EXIST","Nama Undangan tidak ditemukan",null),HttpStatus.BAD_REQUEST);
		    
		    if (changeTypeDto.getchangeType().equalsIgnoreCase(namaMempelaiPria)) {
		    	System.out.println(" masuk sini");
		        userOrders.setGroomName(changeTypeDto.getchangeTo());
		        userOrdersRepository.save(userOrders);
		        return new ResponseEntity<ResponseDto>(new ResponseDto("SUCCESS", "Berhasil di ubah"),HttpStatus.OK);
			} else if (changeTypeDto.getchangeType().equalsIgnoreCase(namaMempelaiWanita)){
				 userOrders.setBrideName(changeTypeDto.getchangeTo());
			     userOrdersRepository.save(userOrders);
			     return new ResponseEntity<ResponseDto>(new ResponseDto("SUCCESS", "Berhasil di ubah"),HttpStatus.OK);
			} else if (changeTypeDto.getchangeType().equalsIgnoreCase(namaPanggilanPria)){
				 userOrders.setGroomShortName(changeTypeDto.getchangeTo());
			     userOrdersRepository.save(userOrders);
			     return new ResponseEntity<ResponseDto>(new ResponseDto("SUCCESS", "Berhasil di ubah"),HttpStatus.OK);
			} else if (changeTypeDto.getchangeType().equalsIgnoreCase(namaPanggilanWanita)) {
				 userOrders.setBrideShortName(changeTypeDto.getchangeTo());
			     userOrdersRepository.save(userOrders);
			     return new ResponseEntity<ResponseDto>(new ResponseDto("SUCCESS", "Berhasil di ubah"),HttpStatus.OK);
			} else if (changeTypeDto.getchangeType().equalsIgnoreCase(namaOrangTuaPria)) {
				 userOrders.setGroomsParent(changeTypeDto.getchangeTo());
			     userOrdersRepository.save(userOrders);
			     return new ResponseEntity<ResponseDto>(new ResponseDto("SUCCESS", "Berhasil di ubah"),HttpStatus.OK);
			} else if (changeTypeDto.getchangeType().equalsIgnoreCase(namaOrangTuaWanita)){
				 userOrders.setBridesParent(changeTypeDto.getchangeTo());
			     userOrdersRepository.save(userOrders);
			     return new ResponseEntity<ResponseDto>(new ResponseDto("SUCCESS", "Berhasil di ubah"),HttpStatus.OK);
			} else if (changeTypeDto.getchangeType().equalsIgnoreCase(akadDate)) {
				String akadDate = changeTypeDto.getchangeTo();
				long setMilisToDate = Long.valueOf(akadDate);
				calendar.setTimeInMillis(setMilisToDate);
				userOrders.setAkadDate(calendar.getTime());
				userOrdersRepository.save(userOrders);
			    return new ResponseEntity<ResponseDto>(new ResponseDto("SUCCESS", "Berhasil di ubah"),HttpStatus.OK);
			} else if (changeTypeDto.getchangeType().equalsIgnoreCase(akadTime)) {
				String validation = validationUntil.validationTimeFromTo(changeTypeDto.getchangeTo()); 
				     if(validation!=null) 
					    return new ResponseEntity<ResponseDto>(new ResponseDto("INVALID_TIME","(Waktu akad) "+validation),HttpStatus.BAD_REQUEST);
				     else
				        userOrders.setAkadTime(changeTypeDto.getchangeTo());
				      	userOrdersRepository.save(userOrders);
			    return new ResponseEntity<ResponseDto>(new ResponseDto("SUCCESS", "Berhasil di ubah"),HttpStatus.OK);
			} else if (changeTypeDto.getchangeType().equalsIgnoreCase(resepsiDate)) {
				String resepsiDate = changeTypeDto.getchangeTo();
				long setMilisToDate = Long.valueOf(resepsiDate);
				calendar.setTimeInMillis(Long.valueOf(setMilisToDate));
				userOrders.setReceptionDate(calendar.getTime());
				userOrdersRepository.save(userOrders);
			    return new ResponseEntity<ResponseDto>(new ResponseDto("SUCCESS", "Berhasil di ubah"),HttpStatus.OK);
			}  else if (changeTypeDto.getchangeType().equalsIgnoreCase(resepsiTime)) {
				String validation = validationUntil.validationTimeFromTo(changeTypeDto.getchangeTo()); 
				    if(validation!=null)
					  return new ResponseEntity<ResponseDto>(new ResponseDto("INVALID_TIME","(Waktu resepsi) "+validation),HttpStatus.BAD_REQUEST);
				   else
				    userOrders.setReceptionTime(changeTypeDto.getchangeTo());
				    userOrdersRepository.save(userOrders);
			    return new ResponseEntity<ResponseDto>(new ResponseDto("SUCCESS", "Berhasil di ubah"),HttpStatus.OK);
			} else if (changeTypeDto.getchangeType().equalsIgnoreCase(koordinatLokasi)){
				userOrders.setLocCoordinate(changeTypeDto.getchangeTo());
				userOrdersRepository.save(userOrders);
			    return new ResponseEntity<ResponseDto>(new ResponseDto("SUCCESS", "Berhasil di ubah"),HttpStatus.OK);
			} else if (changeTypeDto.getchangeType().equalsIgnoreCase(alamat)) {
				userOrders.setAddress(changeTypeDto.getchangeTo());
				userOrdersRepository.save(userOrders);
			    return new ResponseEntity<ResponseDto>(new ResponseDto("SUCCESS", "Berhasil di ubah"),HttpStatus.OK);
			} else if (changeTypeDto.getchangeType().equalsIgnoreCase(quote)) {
				userOrders.setQuote(changeTypeDto.getchangeTo());
				userOrdersRepository.save(userOrders);
			    return new ResponseEntity<ResponseDto>(new ResponseDto("SUCCESS", "Berhasil di ubah"),HttpStatus.OK);
			}else if (changeTypeDto.getchangeType().equalsIgnoreCase(quoteGiver)){
				userOrders.setQuoteGiver(changeTypeDto.getchangeTo());
				userOrdersRepository.save(userOrders);
			    return new ResponseEntity<ResponseDto>(new ResponseDto("SUCCESS", "Berhasil di ubah"),HttpStatus.OK);
			}
		} catch (Exception e) {
			log.debug(e.getMessage(), e);
			return new ResponseEntity<ResponseDto>(new ResponseDto("ERROR","Error system."),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return null;
	}
	
	public ResponseEntity<ResponseDto> getWebsiteNameByUsername(OrdersDto ordersDto){
		try {
			User user = this.findUserByUserName(ordersDto.getUserName());
			int itemPerPage = 5;
			if (user != null) {
				if(user.getAccType()==null) {
					String websiteName = userOrdersRepository.findFirstOrderByUserId(user.getUserId());
					if (websiteName==null || websiteName.length()==0) websiteName="";
					return new ResponseEntity<ResponseDto>(new ResponseDto("SUCCESS","Berhasil mengambil nama undangan",new ResponseDtoGetWebsiteName(websiteName)),HttpStatus.OK);
				}
				if(user.getAccType().equalsIgnoreCase("VENDOR")) {
					Pageable pageable = PageRequest.of(ordersDto.getPaging()-1, itemPerPage); // 5 item per page //-1 //pageable starts from 0
					if(ordersDto.getSearchName()==null || ordersDto.getSearchName().length()==0) {
						Double countListByUser = userOrdersRepository.countListByUser(user.getUserId());
						int maxPage = (int) Math.ceil(countListByUser/itemPerPage);
						List<String> websiteName = userOrdersRepository.findListByUserPageable(user.getUserId(),pageable); 
						return new ResponseEntity<ResponseDto>(new ResponseDto("SUCCESS","Berhasil mengambil nama undangan",new ResponseDtoGetWebsiteNames(websiteName,maxPage)),HttpStatus.OK);
					}else {
						Double countListByUser = userOrdersRepository.countListByUser2(user.getUserId(),ordersDto.getSearchName());
						int maxPage = (int) Math.ceil(countListByUser/itemPerPage);
						List<String> websiteName = userOrdersRepository.findListByUserPageable2(user.getUserId(),ordersDto.getSearchName(),pageable); 
						if(websiteName.isEmpty()) return new ResponseEntity<ResponseDto>(new ResponseDto("UNDANGAN_NOT_FOUND","Nama website undangan tidak ditemukan", null),HttpStatus.INTERNAL_SERVER_ERROR);
						else return new ResponseEntity<ResponseDto>(new ResponseDto("SUCCESS","Berhasil mengambil nama undangan",new ResponseDtoGetWebsiteNames(websiteName,maxPage)),HttpStatus.OK);
					}
				}else if(user.getAccType().equalsIgnoreCase("PERSONAL")){//personal or else
					String websiteName = userOrdersRepository.findFirstOrderByUserId(user.getUserId());
					if (websiteName==null || websiteName.length()==0) websiteName="";
					return new ResponseEntity<ResponseDto>(new ResponseDto("SUCCESS","Berhasil mengambil nama undangan",new ResponseDtoGetWebsiteName(websiteName)),HttpStatus.OK);
				}else return new ResponseEntity<ResponseDto>(new ResponseDto("ERROR","Error system."),HttpStatus.INTERNAL_SERVER_ERROR);
			}else return new ResponseEntity<ResponseDto>(new ResponseDto("FAILED","User tidak ditemukan."),HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			log.debug(e.getMessage(), e);
			return new ResponseEntity<ResponseDto>(new ResponseDto("ERROR","Error system."),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//------------------------------------------------HOPEMESSAGE----------------------------------------------------------------------	
	
	public ResponseEntity<ResponseDto> hopeMessageAdd(HopeMessageDto hopeMessageDto){
		try {
			if(hopeMessageDto.getWebsiteName()==null 
					|| hopeMessageDto.getWebsiteName().trim().length()==0) return new ResponseEntity<ResponseDto>(new ResponseDto("FAILED","Nama Website harus diisi."),HttpStatus.BAD_REQUEST);
			if(hopeMessageDto.getUcapanSender()==null 
					|| hopeMessageDto.getUcapanSender().trim().length()==0) return new ResponseEntity<ResponseDto>(new ResponseDto("FAILED","Nama Pengirim harus diisi."),HttpStatus.BAD_REQUEST);
			if(hopeMessageDto.getUcapanText()==null 
					|| hopeMessageDto.getUcapanText().trim().length()==0) return new ResponseEntity<ResponseDto>(new ResponseDto("FAILED","Ucapan harus diisi."),HttpStatus.BAD_REQUEST);
			
			HopeMessage hopeMessage = new HopeMessage();
			hopeMessage.setAttend(hopeMessageDto.getWillAttend()!=null?hopeMessageDto.getWillAttend():false);
			hopeMessage.setCreateOn(new Date());
			hopeMessage.setSender(hopeMessageDto.getUcapanSender());
			hopeMessage.setText(hopeMessageDto.getUcapanText());
			hopeMessage.setWebsiteName(hopeMessageDto.getWebsiteName());
			Thread.sleep(1000);
			hopeMessageRepository.save(hopeMessage);
			
			return new ResponseEntity<ResponseDto>(new ResponseDto("SUCCESS","Berhasil Dikirim"),HttpStatus.OK);
		} catch (Exception e) {
			log.debug(e.getMessage(), e);
			return new ResponseEntity<ResponseDto>(new ResponseDto("ERROR","Error system."),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	public ResponseEntity<ResponseDto> getHopeMessageList(String websiteName){
		try {
			if(websiteName==null 
					|| websiteName.trim().length()==0) return new ResponseEntity<ResponseDto>(new ResponseDto("FAILED","Nama Website harus diisi."),HttpStatus.BAD_REQUEST);
			return new ResponseEntity<ResponseDto>(new ResponseDto("SUCCESS","Berhasil mengambil ucapan", hopeMessageRepository.findByWebsiteNameOrderByHopeMessageIdDesc(websiteName)),HttpStatus.OK);
		} catch (Exception e) {
			log.debug(e.getMessage(), e);
			return new ResponseEntity<ResponseDto>(new ResponseDto("ERROR","Error system."),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	public ResponseEntity<ResponseDto> getThumbnail(String websiteName){
		try {
			if(websiteName==null 
					|| websiteName.trim().length()==0) return new ResponseEntity<ResponseDto>(new ResponseDto("FAILED","Nama Website harus diisi."),HttpStatus.BAD_REQUEST);
			else {
				UserOrders userOrders = userOrdersRepository.findByWebsiteName(websiteName);
				if(userOrders!=null) 
					return new ResponseEntity<ResponseDto>(new ResponseDto("SUCCESS","", new ThumbnailDto(userOrders)),HttpStatus.OK);
				else 
					return new ResponseEntity<ResponseDto>(new ResponseDto("WEBSITE_NOT_EXIST","Website does not exist"),HttpStatus.OK);
			}
		} catch (Exception e) {
			log.debug(e.getMessage(), e);
			return new ResponseEntity<ResponseDto>(new ResponseDto("ERROR","Error system."),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
