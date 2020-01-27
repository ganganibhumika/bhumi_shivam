package com.techhive.shivamweb.listners;

import javax.persistence.PostPersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

import com.techhive.shivamweb.enums.EnumForAction;
import com.techhive.shivamweb.master.model.SalesPersonMaster;
import com.techhive.shivamweb.master.model.history.SalesPersonMasterHistory;
import com.techhive.shivamweb.master.model.history.SoftwareSalePersonMasterHistory;
import com.techhive.shivamweb.service.GenericAuditServiceCustom;
import com.techhive.shivamweb.utils.BeanUtil;
import com.techhive.shivamweb.utils.ShivamWebMethodUtils;

public class SalesPersonMasterListner {
	@PostPersist
	public void prePersist(SalesPersonMaster target) {
		perform(target, EnumForAction.INSERTED);
	}

	@PreUpdate
	public void preUpdate(SalesPersonMaster target) {
		perform(target, EnumForAction.UPDATED);
	}

	@PreRemove
	public void preRemove(SalesPersonMaster target) {
		perform(target, EnumForAction.DELETED);
	}

	private void perform(SalesPersonMaster salesPerson, EnumForAction action) {
		new Runnable() {
			public void run() {
				GenericAuditServiceCustom genericAuditServiceCustom = BeanUtil.getBean(GenericAuditServiceCustom.class);
				SoftwareSalePersonMasterHistory softwareSalePersonMasterHistory;
				if(!ShivamWebMethodUtils.isObjectisNullOrEmpty(salesPerson.getSoftwareSalePersonMaster())) {
					softwareSalePersonMasterHistory= genericAuditServiceCustom.getLastModifiedData(new SoftwareSalePersonMasterHistory(),salesPerson.getSoftwareUserIdNew()).orElse(null);
				}else {
					softwareSalePersonMasterHistory=null;
				}
				genericAuditServiceCustom.save(new SalesPersonMasterHistory(salesPerson,softwareSalePersonMasterHistory,action));
			}
		}.run();
	}
}
