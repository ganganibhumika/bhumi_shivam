package com.techhive.shivamweb.listners;

import javax.persistence.PostPersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

import com.techhive.shivamweb.enums.EnumForAction;
import com.techhive.shivamweb.master.model.UpcomingFairMaster;
import com.techhive.shivamweb.master.model.history.UpcomingFairHistory;
import com.techhive.shivamweb.service.GenericAuditServiceCustom;
import com.techhive.shivamweb.utils.BeanUtil;

public class UpcomingFairListner {
	@PostPersist
	public void prePersist(UpcomingFairMaster target) {
		perform(target, EnumForAction.INSERTED);
	}

	@PreUpdate
	public void preUpdate(UpcomingFairMaster target) {
		perform(target, EnumForAction.UPDATED);
	}

	@PreRemove
	public void preRemove(UpcomingFairMaster target) {
		perform(target, EnumForAction.DELETED);
	}

	private void perform(UpcomingFairMaster target, EnumForAction action) {
		new Runnable() {
			public void run() {
				GenericAuditServiceCustom genericAuditServiceCustom = BeanUtil.getBean(GenericAuditServiceCustom.class);
				genericAuditServiceCustom.save(new UpcomingFairHistory(target,action));
			}
		}.run();
	}
}
