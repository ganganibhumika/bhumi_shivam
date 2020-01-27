package com.techhive.shivamweb.listners;

import javax.persistence.PostPersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

import com.techhive.shivamweb.enums.EnumForAction;
import com.techhive.shivamweb.master.model.history.SaveSearchCriteriaHistory;
import com.techhive.shivamweb.master.model.history.UserHistory;
import com.techhive.shivamweb.model.SaveSearchCriteria;
import com.techhive.shivamweb.service.GenericAuditServiceCustom;
import com.techhive.shivamweb.utils.BeanUtil;

public class SaveSearchCriteriaListner {
	@PostPersist
	public void prePersist(SaveSearchCriteria target) {
		perform(target, EnumForAction.INSERTED);
	}

	@PreUpdate
	public void preUpdate(SaveSearchCriteria target) {
		perform(target, EnumForAction.UPDATED);
	}

	@PreRemove
	public void preRemove(SaveSearchCriteria target) {
		perform(target, EnumForAction.DELETED);
	}

	private void perform(SaveSearchCriteria target, EnumForAction action) {
		new Runnable() {
			public void run() {
				GenericAuditServiceCustom genericAuditServiceCustom = BeanUtil.getBean(GenericAuditServiceCustom.class);
				UserHistory userHistory=genericAuditServiceCustom.getLastModifiedData(new UserHistory(), target.getUser().getId()).orElse(null);
				genericAuditServiceCustom.save(new SaveSearchCriteriaHistory(target,action,userHistory));
			}
		}.run();
	}
}
