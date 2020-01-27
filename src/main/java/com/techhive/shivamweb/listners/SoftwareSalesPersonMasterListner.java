package com.techhive.shivamweb.listners;

import javax.persistence.PostPersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

import com.techhive.shivamweb.enums.EnumForAction;
import com.techhive.shivamweb.master.model.SoftwareSalePersonMaster;
import com.techhive.shivamweb.master.model.history.SoftwareSalePersonMasterHistory;
import com.techhive.shivamweb.service.GenericAuditServiceCustom;
import com.techhive.shivamweb.utils.BeanUtil;

public class SoftwareSalesPersonMasterListner {
	@PostPersist
	public void prePersist(SoftwareSalePersonMaster target) {
		perform(target, EnumForAction.INSERTED);
	}

	@PreUpdate
	public void preUpdate(SoftwareSalePersonMaster target) {
		perform(target, EnumForAction.UPDATED);
	}

	@PreRemove
	public void preRemove(SoftwareSalePersonMaster target) {
		perform(target, EnumForAction.DELETED);
	}

	private void perform(SoftwareSalePersonMaster target, EnumForAction action) {
		new Runnable() {
			public void run() {
				GenericAuditServiceCustom genericAuditServiceCustom = BeanUtil.getBean(GenericAuditServiceCustom.class);
				genericAuditServiceCustom.save(new SoftwareSalePersonMasterHistory(target, action));
			}
		}.run();
	}
}
