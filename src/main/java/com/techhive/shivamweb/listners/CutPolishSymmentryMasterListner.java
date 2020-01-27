package com.techhive.shivamweb.listners;

import javax.persistence.PostPersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

import com.techhive.shivamweb.enums.EnumForAction;
import com.techhive.shivamweb.master.model.CutPolishSymmentryMaster;
import com.techhive.shivamweb.master.model.history.CutPolishSymmentryMasterHistory;
import com.techhive.shivamweb.service.GenericAuditServiceCustom;
import com.techhive.shivamweb.utils.BeanUtil;

public class CutPolishSymmentryMasterListner {
	@PostPersist
	public void prePersist(CutPolishSymmentryMaster target) {
		perform(target, EnumForAction.INSERTED);
	}

	@PreUpdate
	public void preUpdate(CutPolishSymmentryMaster target) {
		perform(target, EnumForAction.UPDATED);
	}

	@PreRemove
	public void preRemove(CutPolishSymmentryMaster target) {
		perform(target, EnumForAction.DELETED);
	}

	private void perform(CutPolishSymmentryMaster target, EnumForAction action) {
		new Runnable() {
			public void run() {
				GenericAuditServiceCustom genericAuditServiceCustom = BeanUtil.getBean(GenericAuditServiceCustom.class);
				genericAuditServiceCustom.save(new CutPolishSymmentryMasterHistory(target,action));
			}
		}.run();
	}
}
