package com.techhive.shivamweb.listners;

import javax.persistence.PostPersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

import com.techhive.shivamweb.enums.EnumForAction;
import com.techhive.shivamweb.master.model.FluorescenceMaster;
import com.techhive.shivamweb.master.model.history.FluorescenceMasterHistory;
import com.techhive.shivamweb.service.GenericAuditServiceCustom;
import com.techhive.shivamweb.utils.BeanUtil;

public class FluorescenceMasterListner {
	@PostPersist
	public void prePersist(FluorescenceMaster target) {
		perform(target, EnumForAction.INSERTED);
	}

	@PreUpdate
	public void preUpdate(FluorescenceMaster target) {
		perform(target, EnumForAction.UPDATED);
	}

	@PreRemove
	public void preRemove(FluorescenceMaster target) {
		perform(target, EnumForAction.DELETED);
	}

	private void perform(FluorescenceMaster target, EnumForAction action) {
		new Runnable() {
			public void run() {
				GenericAuditServiceCustom genericAuditServiceCustom = BeanUtil.getBean(GenericAuditServiceCustom.class);
				genericAuditServiceCustom.save(new FluorescenceMasterHistory(target,action));
			}
		}.run();
	}
}
