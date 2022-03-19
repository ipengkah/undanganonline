package com.lovelyday.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name="ld_user_orders")
@Table(name="ld_user_orders",uniqueConstraints= {@UniqueConstraint(columnNames={"website_name"})})
@BatchSize(size = 100)
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class UserOrders implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(allocationSize=1,initialValue=1,name="order_seq",sequenceName="order_seq")
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="order_seq")
	@Column(name="order_id",nullable=false)
	private Long orderId;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="user_id", nullable=false)
	private User user;
	
	@Column(length=300,name="website_name")
	private String websiteName;
	
	@Column(name="groom_picture_id")
	private Long groomPictId;
	
	@Column(name="bride_picture_id")
	private Long bridePictId;
	
	@Column(length=60,name="groom_name")
	private String groomName;
	
	@Column(length=60,name="brides_name")
	private String brideName;
	
	@Column(length=20,name="groom_short_name")
	private String groomShortName;
	
	@Column(length=20,name="brides_short_name")
	private String brideShortName;
	
	@Column(length=300,name="grooms_parent")
	private String groomsParent;
	
	@Column(length=300,name="brides_parest")
	private String bridesParent;
	
	@Temporal(TemporalType.DATE)
	@Column(name="akad_date")
	private Date akadDate;
	
	@Temporal(TemporalType.DATE)
	@Column(name="reception_date")
	private Date receptionDate;
	
	@Column(length=15,name="akad_time")
	private String akadTime;
	
	@Column(length=15,name="reception_time")
	private String receptionTime;
	
	@Column(length=500)
	private String address;
	
	@Column(length=500,name="location_coordinate")
	private String locCoordinate;
	
	@Column(length=10)
	private String product;
	
	@Column(length=10,name="template_code")
	private String templateCode;
	
	@Column(name = "gallery_photo", length=100)
	private String galleryPhoto;
	
	@Column(length = 300)
	private String quote;
	
	@Column(length=300, name="quote_giver")
	private String quoteGiver;
	
	@Column
	private Long song;

	/*getter setter*/
	
	public Long getOrderId() {
		return orderId;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getWebsiteName() {
		return websiteName;
	}

	public void setWebsiteName(String websiteName) {
		this.websiteName = websiteName;
	}

	public Long getGroomPictId() {
		return groomPictId;
	}

	public void setGroomPictId(Long groomPictId) {
		this.groomPictId = groomPictId;
	}

	public Long getBridePictId() {
		return bridePictId;
	}

	public void setBridePictId(Long bridePictId) {
		this.bridePictId = bridePictId;
	}

	public String getGroomName() {
		return groomName;
	}

	public void setGroomName(String groomName) {
		this.groomName = groomName;
	}

	public String getBrideName() {
		return brideName;
	}

	public void setBrideName(String brideName) {
		this.brideName = brideName;
	}

	public String getGroomShortName() {
		return groomShortName;
	}

	public void setGroomShortName(String groomShortName) {
		this.groomShortName = groomShortName;
	}

	public String getBrideShortName() {
		return brideShortName;
	}

	public void setBrideShortName(String brideShortName) {
		this.brideShortName = brideShortName;
	}

	public String getGroomsParent() {
		return groomsParent;
	}

	public void setGroomsParent(String groomsParent) {
		this.groomsParent = groomsParent;
	}

	public String getBridesParent() {
		return bridesParent;
	}

	public void setBridesParent(String bridesParent) {
		this.bridesParent = bridesParent;
	}

	public Date getAkadDate() {
		return akadDate;
	}

	public void setAkadDate(Date akadDate) {
		this.akadDate = akadDate;
	}

	public Date getReceptionDate() {
		return receptionDate;
	}

	public void setReceptionDate(Date receptionDate) {
		this.receptionDate = receptionDate;
	}

	public String getAkadTime() {
		return akadTime;
	}

	public void setAkadTime(String akadTime) {
		this.akadTime = akadTime;
	}

	public String getReceptionTime() {
		return receptionTime;
	}

	public void setReceptionTime(String receptionTime) {
		this.receptionTime = receptionTime;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLocCoordinate() {
		return locCoordinate;
	}

	public void setLocCoordinate(String locCoordinate) {
		this.locCoordinate = locCoordinate;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getTemplateCode() {
		return templateCode;
	}

	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getGalleryPhoto() {
		return galleryPhoto;
	}

	public void setGalleryPhoto(String galleryPhoto) {
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
}
