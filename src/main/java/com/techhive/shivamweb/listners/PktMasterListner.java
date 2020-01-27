package com.techhive.shivamweb.listners;

import javax.persistence.PostPersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

import com.techhive.shivamweb.enums.EnumForAction;
import com.techhive.shivamweb.master.model.PktMaster;
import com.techhive.shivamweb.master.model.history.PktMasterHistory;
import com.techhive.shivamweb.service.GenericAuditServiceCustom;
import com.techhive.shivamweb.utils.BeanUtil;
import com.techhive.shivamweb.utils.ShivamWebMethodUtils;

public class PktMasterListner {
	@PostPersist
	public void prePersist(PktMaster target) {
		perform(target, EnumForAction.INSERTED);
	}

	@PreUpdate
	public void preUpdate(PktMaster target) {
		perform(target, EnumForAction.UPDATED);
	}

	@PreRemove
	public void preRemove(PktMaster target) {
		perform(target, EnumForAction.DELETED);
	}

	private void perform(PktMaster target, EnumForAction action) {
		new Runnable() {
			public void run() {
				GenericAuditServiceCustom genericAuditServiceCustom = BeanUtil.getBean(GenericAuditServiceCustom.class);
				genericAuditServiceCustom.save(new PktMasterHistory(target, action));
			}
		}.run();
	}
}
