package com.techhive.shivamweb.listners;

import javax.persistence.PostPersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

import com.techhive.shivamweb.enums.EnumForAction;
import com.techhive.shivamweb.master.model.FancyOvertoneMaster;
import com.techhive.shivamweb.master.model.history.FancyOvertoneMasterHistory;
import com.techhive.shivamweb.service.GenericAuditServiceCustom;
import com.techhive.shivamweb.utils.BeanUtil;

public class FancyOvertoneMasterListner {
	@PostPersist
	public void prePersist(FancyOvertoneMaster target) {
		perform(target, EnumForAction.INSERTED);
	}

	@PreUpdate
	public void preUpdate(FancyOvertoneMaster target) {
		perform(target, EnumForAction.UPDATED);
	}

	@PreRemove
	public void preRemove(FancyOvertoneMaster target) {
		perform(target, EnumForAction.DELETED);
	}

	private void perform(FancyOvertoneMaster target, EnumForAction action) {
		new Runnable() {
			public void run() {
				GenericAuditServiceCustom genericAuditServiceCustom = BeanUtil.getBean(GenericAuditServiceCustom.class);
				genericAuditServiceCustom.save(new FancyOvertoneMasterHistory(target,action));
			}
		}.run();
	}
}
