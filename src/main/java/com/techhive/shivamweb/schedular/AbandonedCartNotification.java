package com.techhive.shivamweb.schedular;

import java.util.Set;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.techhive.shivamweb.enums.EnumForNotificationDescription;
import com.techhive.shivamweb.enums.EnumForNotificationType;
import com.techhive.shivamweb.master.model.User;
import com.techhive.shivamweb.model.Notification;
import com.techhive.shivamweb.repository.CartRepository;
import com.techhive.shivamweb.repository.UserRepository;
import com.techhive.shivamweb.service.NotificationService;
import com.techhive.shivamweb.utils.BeanUtil;

/***
 * @author neel send notification if no action perform at the end of the day
 */
public class AbandonedCartNotification implements org.quartz.Job {

	@Autowired
	CartRepository cartRepository = BeanUtil.getBean(CartRepository.class);

	@Autowired
	UserRepository userRepository = BeanUtil.getBean(UserRepository.class);

	@Autowired
	NotificationService notificationService = BeanUtil.getBean(NotificationService.class);;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		Set<User> users = cartRepository.findAllUserWithAbandondStone();
		Notification notification = new Notification();
		notification.setDescription(EnumForNotificationDescription.ABANDONDCART.toString());
		notification.setSetOfUserObject(users);
		notification.setCategory(EnumForNotificationType.CART.toString());
		notification.setIsAdmin(false);
		notificationService.sendNotification(notification);
		
	}

}
