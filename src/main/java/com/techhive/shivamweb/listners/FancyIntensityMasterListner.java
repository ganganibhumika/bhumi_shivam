package com.techhive.shivamweb.listners;

import javax.persistence.PostPersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

import com.techhive.shivamweb.enums.EnumForAction;
import com.techhive.shivamweb.master.model.FancyIntensityMaster;
import com.techhive.shivamweb.master.model.history.FancyIntensityMasterHistory;
import com.techhive.shivamweb.service.GenericAuditServiceCustom;
import com.techhive.shivamweb.utils.BeanUtil;

public class FancyIntensityMasterListner {
	@PostPersist
	public void prePersist(FancyIntensityMaster target) {
		perform(target, EnumForAction.INSERTED);
	}

	@PreUpdate
	public void preUpdate(FancyIntensityMaster target) {
		perform(target, EnumForAction.UPDATED);
	}

	@PreRemove
	public void preRemove(FancyIntensityMaster target) {
		perform(target, EnumForAction.DELETED);
	}

	private void perform(FancyIntensityMaster target, EnumForAction action) {
		new Runnable() {
			public void run() {
				GenericAuditServiceCustom genericAuditServiceCustom = BeanUtil.getBean(GenericAuditServiceCustom.class);
				genericAuditServiceCustom.save(new FancyIntensityMasterHistory(target,action));
			}
		}.run();
	}
}
