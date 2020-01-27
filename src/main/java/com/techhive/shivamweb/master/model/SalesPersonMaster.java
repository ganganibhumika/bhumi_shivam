package com.techhive.shivamweb.master.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.techhive.shivamweb.audit.Auditable;
import com.techhive.shivamweb.listners.SalesPersonMasterListner;
import com.techhive.shivamweb.utils.ShivamWebMethodUtils;
import com.techhive.shivamweb.utils.ShivamWebVariableUtils;

@Entity
@Table(name = ShivamWebVariableUtils.TABLE_NAME_FOR_SALESPERSONMASTER)
@JsonInclude(Include.NON_EMPTY)
@EntityListeners(SalesPersonMasterListner.class)
public class SalesPersonMaster extends Auditable<String> {

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "salesPersonId", nullable = false, insertable = false, updatable = false)
	private String id;

	@Column(name = "name", columnDefinition = "varchar(30)")
	private String name;

	@Column(name = "mobileNo", columnDefinition = "varchar(30)")
	private String mobileNo;

	@Column(name = "email", columnDefinition = "varchar(50)")
	private String email;

	@Column(name = "skype", columnDefinition = "varchar(50)")
	private String skype;

	@OneToOne
	private SoftwareSalePersonMaster softwareSalePersonMaster;

	@Transient
	private String softwareUserIdNew;

	@Column(name = "qQaddress", columnDefinition = "varchar(300)")
	private String qQaddress;

	private Boolean isActive;

	private Boolean isPrimary;

	@OneToMany(mappedBy = "salesPersonMaster")
	private Set<User> setOfUser;

	public SalesPersonMaster(String salesPersonId) {
		super();
		this.id = salesPersonId;
	}

	public SalesPersonMaster() {
		super();
	}

	public SalesPersonMaster(String id, String name, String mobileNo, String email, String skype, String qQaddress,
			Boolean isActive, Boolean isPrimary) {
		super();
		this.id = id;
		this.name = name;
		this.mobileNo = mobileNo;
		this.email = email;
		this.skype = skype;
		this.qQaddress = qQaddress;
		this.isActive = isActive;
		this.isPrimary = isPrimary;
	}

	public SalesPersonMaster(String salesPersonId, String salesPersonName) {
		this.id = salesPersonId;
		this.name = salesPersonName;
	}

	@PrePersist
	public void onCreate() {
		this.isActive = true;
	}

	@PreRemove
	protected void onRemove() {
		Set<User> setOfUser = this.setOfUser;
		if (!ShivamWebMethodUtils.isSetNullOrEmpty(setOfUser)) {
			setOfUser.forEach(user -> {
				user.setSalesPersonMaster(null);
			});
		}

		SoftwareSalePersonMaster softwareSalePersonMaster = this.softwareSalePersonMaster;
		if (!ShivamWebMethodUtils.isObjectNullOrEmpty(softwareSalePersonMaster)) {
			softwareSalePersonMaster.setSalesPersonMaster(null);

		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSkype() {
		return skype;
	}

	public void setSkype(String skype) {
		this.skype = skype;
	}

	public String getqQaddress() {
		return qQaddress;
	}

	public void setqQaddress(String qQaddress) {
		this.qQaddress = qQaddress;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Boolean getIsPrimary() {
		return isPrimary;
	}

	public void setIsPrimary(Boolean isPrimary) {
		this.isPrimary = isPrimary;
	}

	public Set<User> getSetOfUser() {
		return setOfUser;
	}

	public void setSetOfUser(Set<User> setOfUser) {
		this.setOfUser = setOfUser;
	}

	// public String getSoftwareUserId() {
	// return softwareUserId;
	// }
	//
	// public void setSoftwareUserId(String softwareUserId) {
	// this.softwareUserId = softwareUserId;
	// }

	public SoftwareSalePersonMaster getSoftwareSalePersonMaster() {
		return softwareSalePersonMaster;
	}

	public void setSoftwareSalePersonMaster(SoftwareSalePersonMaster softwareSalePersonMaster) {
		this.softwareSalePersonMaster = softwareSalePersonMaster;
	}

	public String getSoftwareUserIdNew() {
		return softwareUserIdNew;
	}

	public void setSoftwareUserIdNew(String softwareUserIdNew) {
		if (!ShivamWebMethodUtils.isObjectNullOrEmpty(softwareUserIdNew)) {
			this.softwareSalePersonMaster = new SoftwareSalePersonMaster(softwareUserIdNew);
		}
		this.softwareUserIdNew = softwareUserIdNew;
	}

}
