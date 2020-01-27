package com.techhive.shivamweb.listners;

import javax.persistence.PostPersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

import com.techhive.shivamweb.enums.EnumForAction;
import com.techhive.shivamweb.master.model.DiscountMaster;
import com.techhive.shivamweb.master.model.history.DiscountMasterHistory;
import com.techhive.shivamweb.master.model.history.UserHistory;
import com.techhive.shivamweb.service.GenericAuditServiceCustom;
import com.techhive.shivamweb.utils.BeanUtil;
import com.techhive.shivamweb.utils.ShivamWebMethodUtils;

/**
 * 
 * @author Heena
 *
 */
public class DiscountMasterListner {

	@PostPersist
	public void postPersist(DiscountMaster discount) {
		perform(discount, EnumForAction.INSERTED);
	}

	@PreUpdate
	public void preUpdate(DiscountMaster target) {
		perform(target, EnumForAction.UPDATED);
	}

	@PreRemove
	public void preRemove(DiscountMaster target) {
		perform(target, EnumForAction.DELETED);
	}

	public void perform(DiscountMaster discount, EnumForAction action) {
		new Runnable() {
			public void run() {
				GenericAuditServiceCustom auditService = BeanUtil.getBean(GenericAuditServiceCustom.class);
				UserHistory user;
				if(!ShivamWebMethodUtils.isObjectisNullOrEmpty(discount.getIdOfUser())) {
					 user= auditService.getLastModifiedData(new UserHistory(),discount.getUser().getId()).orElse(null);
				}else {
					 user=null;
				}
				auditService.save(new DiscountMasterHistory(discount, user, action));
			}
		}.run();
	}

}
