package com.techhive.shivamweb.listners;

import javax.persistence.PostPersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

import com.techhive.shivamweb.enums.EnumForAction;
import com.techhive.shivamweb.master.model.User;
import com.techhive.shivamweb.master.model.history.SalesPersonMasterHistory;
import com.techhive.shivamweb.master.model.history.SoftwarePartyMasterHistory;
import com.techhive.shivamweb.master.model.history.UserHistory;
import com.techhive.shivamweb.service.GenericAuditServiceCustom;
import com.techhive.shivamweb.utils.BeanUtil;
import com.techhive.shivamweb.utils.ShivamWebMethodUtils;

public class UserListner {
	@PostPersist
	public void prePersist(User target) {
		perform(target, EnumForAction.INSERTED);
	}

	@PreUpdate
	public void preUpdate(User target) {
		perform(target, EnumForAction.UPDATED);
	}

	@PreRemove
	public void preRemove(User target) {
		perform(target, EnumForAction.DELETED);
	}

	private void perform(User target, EnumForAction action) {
		new Runnable() {
			public void run() {
				GenericAuditServiceCustom genericAuditServiceCustom = BeanUtil.getBean(GenericAuditServiceCustom.class);
				SalesPersonMasterHistory salesPMHistory = new SalesPersonMasterHistory();
				SoftwarePartyMasterHistory softwarePartyMaster = new SoftwarePartyMasterHistory();
				if (!ShivamWebMethodUtils.isObjectNullOrEmpty(target.getSalesPersonMaster())) {
					salesPMHistory = genericAuditServiceCustom
							.getLastModifiedData(new SalesPersonMasterHistory(), target.getSalesPersonMaster().getId())
							.orElse(new SalesPersonMasterHistory());
				}
				if (!ShivamWebMethodUtils.isObjectNullOrEmpty(target.getSoftwarePartyMaster())) {
					softwarePartyMaster = genericAuditServiceCustom
							.getLastModifiedData(new SoftwarePartyMasterHistory(),
									target.getSoftwarePartyMaster().getId())
							.orElse(new SoftwarePartyMasterHistory());
				}
				genericAuditServiceCustom.save(new UserHistory(target, salesPMHistory, softwarePartyMaster, action));
			}
		}.run();
	}
}
