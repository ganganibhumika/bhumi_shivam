package com.techhive.shivamweb.listners;

import javax.persistence.PostPersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

import com.techhive.shivamweb.enums.EnumForAction;
import com.techhive.shivamweb.master.model.NewArrivalSettings;
import com.techhive.shivamweb.master.model.history.NewArrivalSettingsHistory;
import com.techhive.shivamweb.service.GenericAuditServiceCustom;
import com.techhive.shivamweb.utils.BeanUtil;

public class NewArrivalSettingsListner {
	@PostPersist
	public void prePersist(NewArrivalSettings target) {
		perform(target, EnumForAction.INSERTED);
	}

	@PreUpdate
	public void preUpdate(NewArrivalSettings target) {
		perform(target, EnumForAction.UPDATED);
	}

	@PreRemove
	public void preRemove(NewArrivalSettings target) {
		perform(target, EnumForAction.DELETED);
	}

	private void perform(NewArrivalSettings target, EnumForAction action) {
		new Runnable() {
			public void run() {
				GenericAuditServiceCustom genericAuditServiceCustom = BeanUtil.getBean(GenericAuditServiceCustom.class);
				genericAuditServiceCustom.save(new NewArrivalSettingsHistory(target,action));
			}
		}.run();
	}
}
