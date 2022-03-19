package com.lovelyday.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import com.lovelyday.model.UserOrders;

public class GetOrdersDto {

	/*{
		responseStatus: “SUCCESS“, 
		responseMessage: ““, 
		data:{
			template: “LDS01”,
			paket: “p1“,
			title:'Wedding Mario & Jennifer',
			photoMale:21,
			photoFemale:22,
			fullnameMale: 'Mario Fettucini',
			fullnameFemale: 'Jennifer Vespucci',
			shortnameMale: 'Mario',
			shortnameFemale: 'Jennie',
			parentsMale: 'Alberto Fettucini & Juliet Fettucini',
			parentsFemale: 'Ezio Vespucci & Amanda Vespucci',
			akadDate: 1623486898000,
			akadTime: "09:00 - 10:00",
			resepsiDate: 1623486898000,
			resepsiTime: "11:00 - 13:00",
			lokasi: "Gedung Pernikahan serba guna Hj.Imron, Jl. Rumput Soang, Bekasi",
			lokasiMap: "-6.090351923308629, 106.74392683470818",
			galleryPhoto: [21,22,21,22,21],
			quote: null,
			quoteGiver: null,
			song:"",
        }
	}*/
	
	private String template;
	private String paket;
	private String title;
	private Long photoMale;
	private Long photoFemale;
	private String fullnameMale;
	private String fullnameFemale;
	private String shortnameMale;
	private String shortnameFemale;
	private String parentsMale;
	private String parentsFemale;
	private Long akadDate;
	private String akadTime;
	private Long resepsiDate;
	private String resepsiTime;
	private String lokasi;
	private String lokasiMap;
	private List<Long> galleryPhoto = new ArrayList<Long>();
	private String quote;
	private String quoteGiver;
	private Long song;
	private List<Object> ceritaCinta = new ArrayList<Object>();
	private String tamu;
	private List<String> videoGallery = new ArrayList<String>();
	
	public GetOrdersDto(UserOrders order, String tamu) {
		super();
		this.template = order.getTemplateCode();
		this.paket = order.getProduct();
		this.title = order.getWebsiteName();
		this.photoMale = order.getGroomPictId();
		this.photoFemale = order.getBridePictId();
		this.fullnameMale = order.getGroomName();
		this.fullnameFemale = order.getBrideName();
		this.shortnameMale = order.getGroomShortName();
		this.shortnameFemale = order.getBrideShortName();
		this.parentsMale = order.getGroomsParent();
		this.parentsFemale = order.getBridesParent();
		
		Calendar calendar = Calendar.getInstance();
		if(order.getAkadDate()!=null) {
			calendar.setTime(order.getAkadDate());
			this.akadDate = calendar.getTimeInMillis();
		}else this.akadDate = new Long(0);
		this.akadTime = order.getAkadTime()!=null?order.getAkadTime():"";
		
		if(order.getReceptionDate()!=null) {
			calendar.setTime(order.getReceptionDate());
			this.resepsiDate = calendar.getTimeInMillis();
		}else this.resepsiDate = new Long(0);
		this.resepsiTime = order.getReceptionTime()!=null?order.getReceptionTime():"";
		
		this.lokasi = order.getAddress();
		this.lokasiMap = order.getLocCoordinate();
		if(order.getGalleryPhoto()!=null && order.getGalleryPhoto().trim().length()>0) {
			String[] list = order.getGalleryPhoto().split(",");
			for(int i=0;i<list.length;i++) {
				this.galleryPhoto.add(new Long(list[i].trim()));
			}
		}
		this.quote = order.getQuote()!=null?order.getQuote():"";
		this.quoteGiver = order.getQuoteGiver()!=null?order.getQuoteGiver():"";
		this.song = order.getSong()!=null?order.getSong():null;
		this.tamu = tamu;
	}
	
	@Override
	public String toString() {
		return "GetOrdersDto [template=" + template + ", paket=" + paket + ", title=" + title + ", photoMale="
				+ photoMale + ", photoFemale=" + photoFemale + ", fullnameMale=" + fullnameMale + ", fullnameFemale="
				+ fullnameFemale + ", shortnameMale=" + shortnameMale + ", shortnameFemale=" + shortnameFemale
				+ ", parentsMale=" + parentsMale + ", parentsFemale=" + parentsFemale + ", akadDate=" + akadDate
				+ ", akadTime=" + akadTime + ", resepsiDate=" + resepsiDate + ", resepsiTime=" + resepsiTime
				+ ", lokasi=" + lokasi + ", lokasiMap=" + lokasiMap + ", galleryPhoto=" + galleryPhoto + ", quote=" + quote 
				+ ", quoteGiver=" + quoteGiver + ", song=" + song + ", tamu=" + tamu + ", ceritaCinta=" + ceritaCinta 
				+ ", videoGallery=" + videoGallery + "]";
	}
	
	//getter setter
	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getPaket() {
		return paket;
	}

	public void setPaket(String paket) {
		this.paket = paket;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getPhotoMale() {
		return photoMale;
	}

	public void setPhotoMale(Long photoMale) {
		this.photoMale = photoMale;
	}

	public Long getPhotoFemale() {
		return photoFemale;
	}

	public void setPhotoFemale(Long photoFemale) {
		this.photoFemale = photoFemale;
	}

	public String getFullnameMale() {
		return fullnameMale;
	}

	public void setFullnameMale(String fullnameMale) {
		this.fullnameMale = fullnameMale;
	}

	public String getFullnameFemale() {
		return fullnameFemale;
	}

	public void setFullnameFemale(String fullnameFemale) {
		this.fullnameFemale = fullnameFemale;
	}

	public String getShortnameMale() {
		return shortnameMale;
	}

	public void setShortnameMale(String shortnameMale) {
		this.shortnameMale = shortnameMale;
	}

	public String getShortnameFemale() {
		return shortnameFemale;
	}

	public void setShortnameFemale(String shortnameFemale) {
		this.shortnameFemale = shortnameFemale;
	}

	public String getParentsMale() {
		return parentsMale;
	}

	public void setParentsMale(String parentsMale) {
		this.parentsMale = parentsMale;
	}

	public String getParentsFemale() {
		return parentsFemale;
	}

	public void setParentsFemale(String parentsFemale) {
		this.parentsFemale = parentsFemale;
	}

	public Long getAkadDate() {
		return akadDate;
	}

	public void setAkadDate(Long akadDate) {
		this.akadDate = akadDate;
	}

	public String getAkadTime() {
		return akadTime;
	}

	public void setAkadTime(String akadTime) {
		this.akadTime = akadTime;
	}

	public Long getResepsiDate() {
		return resepsiDate;
	}

	public void setResepsiDate(Long resepsiDate) {
		this.resepsiDate = resepsiDate;
	}

	public String getResepsiTime() {
		return resepsiTime;
	}

	public void setResepsiTime(String resepsiTime) {
		this.resepsiTime = resepsiTime;
	}

	public String getLokasi() {
		return lokasi;
	}

	public void setLokasi(String lokasi) {
		this.lokasi = lokasi;
	}

	public String getLokasiMap() {
		return lokasiMap;
	}

	public void setLokasiMap(String lokasiMap) {
		this.lokasiMap = lokasiMap;
	}

	public List<Long> getGalleryPhoto() {
		return galleryPhoto;
	}

	public void setGalleryPhoto(List<Long> galleryPhoto) {
		this.galleryPhoto = galleryPhoto;
	}

	public String getQuote() {
		return quote;
	}

	public void setQuote(String quote) {
		this.quote = quote;
	}

	public String getQuoteGiver() {
		return quoteGiver;
	}

	public void setQuoteGiver(String quoteGiver) {
		this.quoteGiver = quoteGiver;
	}

	public Long getSong() {
		return song;
	}

	public void setSong(Long song) {
		this.song = song;
	}

	public List<Object> getCeritaCinta() {
		return ceritaCinta;
	}

	public void setCeritaCinta(List<Object> ceritaCinta) {
		this.ceritaCinta = ceritaCinta;
	}

	public String getTamu() {
		return tamu;
	}

	public void setTamu(String tamu) {
		this.tamu = tamu;
	}

	public List<String> getVideoGallery() {
		return videoGallery;
	}

	public void setVideoGallery(List<String> videoGallery) {
		this.videoGallery = videoGallery;
	}
}
