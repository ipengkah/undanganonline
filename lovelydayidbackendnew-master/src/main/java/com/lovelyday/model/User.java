package com.lovelyday.model;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

@Entity(name="ld_users")
@Table(name="ld_users",uniqueConstraints= {@UniqueConstraint(columnNames={"user_name"})})
@BatchSize(size = 100)
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(allocationSize=1,initialValue=1,name="user_seq",sequenceName="user_seq")
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="user_seq")
	@Column(name="user_id",nullable=false)
	private Long userId;
	
	@Column(length=128)
	private String name;
	
	@Column(length=128,name="user_name",nullable=false)
	private String userName;
	
	@Column(length=128,name="user_pass",nullable=false)
	private String userPass;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_on",insertable=true,updatable=false)
	private Date createOn;
	
	@Column(length=128,name="create_by",insertable=true,updatable=false)
	private String createBy;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="change_on",insertable=false,updatable=true)
	private Date changeOn;
	
	@Column(length=128,name="change_by",insertable=false,updatable=true)
	private String changeBy;
	
	@Type(type="true_false")
	@Column(length=1,name="is_active")
	private boolean active = false;
	
	@Type(type="true_false")
	@Column(length=1,name="is_admin")
	private boolean admin = false;
	
	@Column(length=128,name="old_user_pass")
	private String oldUserPass;
	
	@Column(length=128)
	private String email;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="last_login_date")
	private Date lastLoginDate;
	
	//if value >= 3, blockInvalidPass filled
	@Column(name="invalid_login_counter",length=2)
	private int invalidLoginCouter = 0;
	
	//if unblock set null blockInvalidPass && set 0 invalidLoginCouter
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="block_invalid_pass")
	private Date blockInvalidPass;
	
	@Column(length=1200)
	private String token;
	
	@Column(length=20)
	private String mobile;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="resend_email_date")
	private Date resendEmailDate;
	
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER,mappedBy="user",orphanRemoval=true)
	@BatchSize(size=10)
	private List<UserOrders> userOrders = new LinkedList<>();
	
	@Column(length=128,name="new_email")
	private String newEmail;
	
	@Column(length=50,name="account_type")
	private String accType;
	
	public User() {
		super();
	}
	
	public User(String userName, String userPass, String createBy, String name) {
		super();
		this.userName = userName;
		this.userPass = userPass;
		this.createBy = createBy;
		this.name = name;
	}
	
	//Getter and Setter
	public Long getUserId() {
		return userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPass() {
		return userPass;
	}

	public void setUserPass(String userPass) {
		this.userPass = userPass;
	}

	public Date getCreateOn() {
		return createOn;
	}

	public void setCreateOn(Date createOn) {
		this.createOn = createOn;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getChangeOn() {
		return changeOn;
	}

	public void setChangeOn(Date changeOn) {
		this.changeOn = changeOn;
	}

	public String getChangeBy() {
		return changeBy;
	}

	public void setChangeBy(String changeBy) {
		this.changeBy = changeBy;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public String getOldUserPass() {
		return oldUserPass;
	}

	public void setOldUserPass(String oldUserPass) {
		this.oldUserPass = oldUserPass;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public int getInvalidLoginCouter() {
		return invalidLoginCouter;
	}

	public void setInvalidLoginCouter(int invalidLoginCouter) {
		this.invalidLoginCouter = invalidLoginCouter;
	}

	public Date getBlockInvalidPass() {
		return blockInvalidPass;
	}

	public void setBlockInvalidPass(Date blockInvalidPass) {
		this.blockInvalidPass = blockInvalidPass;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Date getResendEmailDate() {
		return resendEmailDate;
	}

	public void setResendEmailDate(Date resendEmailDate) {
		this.resendEmailDate = resendEmailDate;
	}

	public List<UserOrders> getUserOrders() {
		return userOrders;
	}

	public void setUserOrders(List<UserOrders> userOrders) {
		this.userOrders = userOrders;
	}

	public String getNewEmail() {
		return newEmail;
	}

	public void setNewEmail(String newEmail) {
		this.newEmail = newEmail;
	}

	public String getAccType() {
		return accType;
	}

	public void setAccType(String accType) {
		this.accType = accType;
	}
	
}
