package com.techhive.shivamweb.listners;

import javax.persistence.PostPersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

import com.techhive.shivamweb.enums.EnumForAction;
import com.techhive.shivamweb.master.model.SuggestionFeedback;
import com.techhive.shivamweb.master.model.history.SuggestionFeedbackHistory;
import com.techhive.shivamweb.service.GenericAuditServiceCustom;
import com.techhive.shivamweb.utils.BeanUtil;

public class SuggestionFeedbackListner {
	@PostPersist
	public void prePersist(SuggestionFeedback target) {
		perform(target, EnumForAction.INSERTED);
	}

	@PreUpdate
	public void preUpdate(SuggestionFeedback target) {
		perform(target, EnumForAction.UPDATED);
	}

	@PreRemove
	public void preRemove(SuggestionFeedback target) {
		perform(target, EnumForAction.DELETED);
	}

	private void perform(SuggestionFeedback target, EnumForAction action) {
		new Runnable() {
			public void run() {
				GenericAuditServiceCustom genericAuditServiceCustom = BeanUtil.getBean(GenericAuditServiceCustom.class);
				genericAuditServiceCustom.save(new SuggestionFeedbackHistory(target,action));
			}
		}.run();
	}
}
