package com.techhive.shivamweb.listners;

import javax.persistence.PostPersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

import com.techhive.shivamweb.enums.EnumForAction;
import com.techhive.shivamweb.master.model.history.ConfirmOrderHistory;
import com.techhive.shivamweb.master.model.history.PktMasterHistory;
import com.techhive.shivamweb.master.model.history.UserHistory;
import com.techhive.shivamweb.model.ConfirmOrder;
import com.techhive.shivamweb.service.GenericAuditServiceCustom;
import com.techhive.shivamweb.utils.BeanUtil;

public class ConfirmOrderListner {
	@PostPersist
	public void prePersist(ConfirmOrder target) {
		perform(target, EnumForAction.INSERTED);
	}

	@PreUpdate
	public void preUpdate(ConfirmOrder target) {
		perform(target, EnumForAction.UPDATED);
	}

	@PreRemove
	public void preRemove(ConfirmOrder target) {
		perform(target, EnumForAction.DELETED);
	}

	private void perform(ConfirmOrder target, EnumForAction action) {
		new Runnable() {
			public void run() {
				GenericAuditServiceCustom genericAuditServiceCustom = BeanUtil.getBean(GenericAuditServiceCustom.class);
				UserHistory userHistory=genericAuditServiceCustom.getLastModifiedData(new UserHistory(), target.getUser().getId()).orElse(null);
				PktMasterHistory pktMasterHistory=genericAuditServiceCustom.getLastModifiedData(new PktMasterHistory(), target.getPktMaster().getId()).orElse(null);
				genericAuditServiceCustom.save(new ConfirmOrderHistory(target,action,userHistory,pktMasterHistory));
			}
		}.run();
	}
}
