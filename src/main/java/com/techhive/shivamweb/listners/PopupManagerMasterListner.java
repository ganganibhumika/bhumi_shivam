package com.techhive.shivamweb.listners;

import javax.persistence.PostPersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

import com.techhive.shivamweb.enums.EnumForAction;
import com.techhive.shivamweb.master.model.PopupManagerMaster;
import com.techhive.shivamweb.master.model.history.PopupManagerMasterHistory;
import com.techhive.shivamweb.service.GenericAuditServiceCustom;
import com.techhive.shivamweb.utils.BeanUtil;

public class PopupManagerMasterListner {
	@PostPersist
	public void prePersist(PopupManagerMaster target) {
		perform(target, EnumForAction.INSERTED);
	}

	@PreUpdate
	public void preUpdate(PopupManagerMaster target) {
		perform(target, EnumForAction.UPDATED);
	}

	@PreRemove
	public void preRemove(PopupManagerMaster target) {
		perform(target, EnumForAction.DELETED);
	}

	private void perform(PopupManagerMaster target, EnumForAction action) {
		new Runnable() {
			public void run() {
				GenericAuditServiceCustom genericAuditServiceCustom = BeanUtil.getBean(GenericAuditServiceCustom.class);
				genericAuditServiceCustom.save(new PopupManagerMasterHistory(target,action));
			}
		}.run();
	}
}
