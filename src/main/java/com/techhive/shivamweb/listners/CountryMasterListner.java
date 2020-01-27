package com.techhive.shivamweb.listners;

import javax.persistence.PostPersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

import com.techhive.shivamweb.enums.EnumForAction;
import com.techhive.shivamweb.master.model.CountryMaster;
import com.techhive.shivamweb.master.model.history.CountryMasterHistory;
import com.techhive.shivamweb.service.GenericAuditServiceCustom;
import com.techhive.shivamweb.utils.BeanUtil;

public class CountryMasterListner {
	@PostPersist
	public void prePersist(CountryMaster target) {
		perform(target, EnumForAction.INSERTED);
	}

	@PreUpdate
	public void preUpdate(CountryMaster target) {
		perform(target, EnumForAction.UPDATED);
	}

	@PreRemove
	public void preRemove(CountryMaster target) {
		perform(target, EnumForAction.DELETED);
	}

	private void perform(CountryMaster target, EnumForAction action) {
		new Runnable() {
			public void run() {
				GenericAuditServiceCustom genericAuditServiceCustom = BeanUtil.getBean(GenericAuditServiceCustom.class);
				genericAuditServiceCustom.save(new CountryMasterHistory(target,action));
			}
		}.run();
	}
}
