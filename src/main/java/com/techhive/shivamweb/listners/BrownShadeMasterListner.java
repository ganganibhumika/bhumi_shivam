package com.techhive.shivamweb.listners;

import javax.persistence.PostPersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

import com.techhive.shivamweb.enums.EnumForAction;
import com.techhive.shivamweb.master.model.BrownShadeMaster;
import com.techhive.shivamweb.master.model.history.BrownShadeMasterHistory;
import com.techhive.shivamweb.service.GenericAuditServiceCustom;
import com.techhive.shivamweb.utils.BeanUtil;

public class BrownShadeMasterListner {
	@PostPersist
	public void prePersist(BrownShadeMaster target) {
		perform(target, EnumForAction.INSERTED);
	}

	@PreUpdate
	public void preUpdate(BrownShadeMaster target) {
		perform(target, EnumForAction.UPDATED);
	}

	@PreRemove
	public void preRemove(BrownShadeMaster target) {
		perform(target, EnumForAction.DELETED);
	}

	private void perform(BrownShadeMaster target, EnumForAction action) {
		new Runnable() {
			public void run() {
				GenericAuditServiceCustom genericAuditServiceCustom = BeanUtil.getBean(GenericAuditServiceCustom.class);
				genericAuditServiceCustom.save(new BrownShadeMasterHistory(target,action));
			}
		}.run();
	}
}
