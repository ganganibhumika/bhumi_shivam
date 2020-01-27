package com.techhive.shivamweb.master.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.techhive.shivamweb.listners.PartyMasterListner;
import com.techhive.shivamweb.utils.ShivamWebMethodUtils;
import com.techhive.shivamweb.utils.ShivamWebVariableUtils;

@Entity
@Table(name = ShivamWebVariableUtils.TABLE_NAME_FOR_PARTY)
@EntityListeners(PartyMasterListner.class)
@JsonInclude(Include.NON_EMPTY)
public class PartyMaster {

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "partyId", nullable = false, insertable = false, updatable = false)
	private String id;

	@Column(name = "partyname", columnDefinition = "varchar(30)")
	private String partyname;

	@Column(name = "password", columnDefinition = "varchar(100)")
	private String password;

	private Boolean isActive;
	
	private Boolean isParty;
	
	private String email;

	@OneToMany(mappedBy = "partyMaster")
	@JsonIgnore
	private Set<ThirdPartyDiscountMaster> setOfThirdPartyDiscount;

	@SuppressWarnings("unchecked")
	@PreRemove
	protected void onRemove() {
		Set<ThirdPartyDiscountMaster> setOfThirdPartyDiscount = this.setOfThirdPartyDiscount;
		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(setOfThirdPartyDiscount)) {
			setOfThirdPartyDiscount.forEach(discount -> {
				discount.setPartyMaster(null);
			});
		}
	}

	public PartyMaster() {
		super();
	}

	/**
	 * heena
	 * 
	 * @param id
	 */
	public PartyMaster(String id) {
		super();
		this.id = id;
	}

	public PartyMaster(String partyname, String password,String email,Boolean isParty, Boolean isActive) {
		super();
		this.partyname = partyname;
		this.password = password;
		this.isActive = isActive;
		this.email=email;
		this.isParty=isParty;
	}

	@PrePersist
	public void prePersist() {
		this.isActive = true;
		this.isParty=true;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPartyname() {
		return partyname;
	}

	public void setPartyname(String partyname) {
		this.partyname = partyname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Set<ThirdPartyDiscountMaster> getSetOfThirdPartyDiscount() {
		return setOfThirdPartyDiscount;
	}

	public void setSetOfThirdPartyDiscount(Set<ThirdPartyDiscountMaster> setOfThirdPartyDiscount) {
		this.setOfThirdPartyDiscount = setOfThirdPartyDiscount;
	}

	public Boolean getIsParty() {
		return isParty;
	}

	public void setIsParty(Boolean isParty) {
		this.isParty = isParty;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
