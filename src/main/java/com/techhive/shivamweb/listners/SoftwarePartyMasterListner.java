package com.techhive.shivamweb.listners;

import javax.persistence.PostPersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

import com.techhive.shivamweb.enums.EnumForAction;
import com.techhive.shivamweb.master.model.SoftwarePartyMaster;
import com.techhive.shivamweb.master.model.history.SoftwarePartyMasterHistory;
import com.techhive.shivamweb.service.GenericAuditServiceCustom;
import com.techhive.shivamweb.utils.BeanUtil;

public class SoftwarePartyMasterListner {
	@PostPersist
	public void prePersist(SoftwarePartyMaster target) {
		perform(target, EnumForAction.INSERTED);
	}

	@PreUpdate
	public void preUpdate(SoftwarePartyMaster target) {
		perform(target, EnumForAction.UPDATED);
	}

	@PreRemove
	public void preRemove(SoftwarePartyMaster target) {
		perform(target, EnumForAction.DELETED);
	}

	private void perform(SoftwarePartyMaster target, EnumForAction action) {
		new Runnable() {
			public void run() {
				GenericAuditServiceCustom genericAuditServiceCustom = BeanUtil.getBean(GenericAuditServiceCustom.class);
				genericAuditServiceCustom.save(new SoftwarePartyMasterHistory(target, action));
			}
		}.run();
	}
}
