package com.techhive.shivamweb.listners;

import javax.persistence.PostPersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

import com.techhive.shivamweb.enums.EnumForAction;
import com.techhive.shivamweb.master.model.history.CreateDemandHistory;
import com.techhive.shivamweb.master.model.history.UserHistory;
import com.techhive.shivamweb.model.CreateDemand;
import com.techhive.shivamweb.service.GenericAuditServiceCustom;
import com.techhive.shivamweb.utils.BeanUtil;

public class CreateDemandListner {
	@PostPersist
	public void prePersist(CreateDemand target) {
		perform(target, EnumForAction.INSERTED);
	}

	@PreUpdate
	public void preUpdate(CreateDemand target) {
		perform(target, EnumForAction.UPDATED);
	}

	@PreRemove
	public void preRemove(CreateDemand target) {
		perform(target, EnumForAction.DELETED);
	}

	private void perform(CreateDemand target, EnumForAction action) {
		new Runnable() {
			public void run() {
				GenericAuditServiceCustom genericAuditServiceCustom = BeanUtil.getBean(GenericAuditServiceCustom.class);
				UserHistory userHistory = genericAuditServiceCustom
						.getLastModifiedData(new UserHistory(), target.getUser().getId()).orElse(null);
				genericAuditServiceCustom.save(new CreateDemandHistory(target, action, userHistory));
			}
		}.run();
	}
}
