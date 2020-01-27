package com.techhive.shivamweb.listners;

import javax.persistence.PostPersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

import com.techhive.shivamweb.enums.EnumForAction;
import com.techhive.shivamweb.master.model.history.NotificationHistory;
import com.techhive.shivamweb.master.model.history.UserHistory;
import com.techhive.shivamweb.model.Notification;
import com.techhive.shivamweb.service.GenericAuditServiceCustom;
import com.techhive.shivamweb.utils.BeanUtil;
import com.techhive.shivamweb.utils.ShivamWebMethodUtils;

public class NotificationListner {

	@PostPersist
	public void prePersist(Notification target) {
		perform(target, EnumForAction.INSERTED);
	}

	@PreUpdate
	public void preUpdate(Notification target) {
		perform(target, EnumForAction.UPDATED);
	}

	@PreRemove
	public void preRemove(Notification target) {
		perform(target, EnumForAction.DELETED);
	}

	private void perform(Notification target, EnumForAction action) {
		new Runnable() {
			public void run() {
				GenericAuditServiceCustom genericAuditServiceCustom = BeanUtil.getBean(GenericAuditServiceCustom.class);
				UserHistory user;
				if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(target.getUser().getId())) {
					user = genericAuditServiceCustom.getLastModifiedData(new UserHistory(), target.getUser().getId())
							.orElse(null);
				} else {
					user = null;
				}
				genericAuditServiceCustom.save(new NotificationHistory(target, user, action));
			}
		}.run();
	}

}
