package com.techhive.shivamweb.master.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.techhive.shivamweb.audit.Auditable;
import com.techhive.shivamweb.utils.ShivamWebVariableUtils;

@Entity
@Table(name = ShivamWebVariableUtils.TABLE_NAME_FOR_APP_VERSION_MASTER)
@JsonInclude(Include.NON_EMPTY)
// @EntityListeners(NewArrivalSettingsListner.class)
public class AppVersionMaster extends Auditable<String> {
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "appVersionMasterId", nullable = false, insertable = false, updatable = false)
	private String id;

	private String androidVersion;

	private String iosVersion;

	public AppVersionMaster() {
		super();
	}

	public AppVersionMaster(String id, String androidVersion, String iosVersion) {
		super();
		this.id = id;
		this.androidVersion = androidVersion;
		this.iosVersion = iosVersion;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAndroidVersion() {
		return androidVersion;
	}

	public void setAndroidVersion(String androidVersion) {
		this.androidVersion = androidVersion;
	}

	public String getIosVersion() {
		return iosVersion;
	}

	public void setIosVersion(String iosVersion) {
		this.iosVersion = iosVersion;
	}

}
