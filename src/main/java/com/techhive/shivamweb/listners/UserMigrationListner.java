package com.techhive.shivamweb.listners;

import javax.persistence.PostPersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

import com.techhive.shivamweb.enums.EnumForAction;
import com.techhive.shivamweb.master.model.UserMigration;
import com.techhive.shivamweb.master.model.history.UserMigrationHistory;
import com.techhive.shivamweb.service.GenericAuditServiceCustom;
import com.techhive.shivamweb.utils.BeanUtil;

public class UserMigrationListner {
	@PostPersist
	public void prePersist(UserMigration target) {
		perform(target, EnumForAction.INSERTED);
	}

	@PreUpdate
	public void preUpdate(UserMigration target) {
		perform(target, EnumForAction.UPDATED);
	}

	@PreRemove
	public void preRemove(UserMigration target) {
		perform(target, EnumForAction.DELETED);
	}

	private void perform(UserMigration target, EnumForAction action) {
		new Runnable() {
			public void run() {
				GenericAuditServiceCustom genericAuditServiceCustom = BeanUtil.getBean(GenericAuditServiceCustom.class);
				genericAuditServiceCustom.save(new UserMigrationHistory(target,action));
			}
		}.run();
	}
}
