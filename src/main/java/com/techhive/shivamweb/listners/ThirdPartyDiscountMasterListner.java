package com.techhive.shivamweb.listners;

import javax.persistence.PostPersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

import com.techhive.shivamweb.enums.EnumForAction;
import com.techhive.shivamweb.master.model.ThirdPartyDiscountMaster;
import com.techhive.shivamweb.master.model.history.PartyMasterHistory;
import com.techhive.shivamweb.master.model.history.ThirdPartyDiscountMasterHistory;
import com.techhive.shivamweb.service.GenericAuditServiceCustom;
import com.techhive.shivamweb.utils.BeanUtil;

public class ThirdPartyDiscountMasterListner {
	
	@PostPersist
	public void prePersist(ThirdPartyDiscountMaster target) {
		perform(target, EnumForAction.INSERTED);
	}

	@PreUpdate
	public void preUpdate(ThirdPartyDiscountMaster target) {
		perform(target, EnumForAction.UPDATED);
	}

	@PreRemove
	public void preRemove(ThirdPartyDiscountMaster target) {
		perform(target, EnumForAction.DELETED);
	}

	private void perform(ThirdPartyDiscountMaster target, EnumForAction action) {
		new Runnable() {
			public void run() {
				GenericAuditServiceCustom genericAuditServiceCustom = BeanUtil.getBean(GenericAuditServiceCustom.class);
				PartyMasterHistory	partyMasterHistory = genericAuditServiceCustom.getLastModifiedData(new PartyMasterHistory(),
							target.getPartyMaster().getId()).orElse(null);
				genericAuditServiceCustom.save(new ThirdPartyDiscountMasterHistory(target, partyMasterHistory, action));
			}
		}.run();
	}
}
