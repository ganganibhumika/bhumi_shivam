package com.techhive.shivamweb.appconfig;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.techhive.shivamweb.master.model.PktMaster;
import com.techhive.shivamweb.model.ViewRequest;
import com.techhive.shivamweb.repository.PktMasterRepository;
import com.techhive.shivamweb.repository.ViewRequestRepository;
import com.techhive.shivamweb.schedular.AbandonedCartNotification;
import com.techhive.shivamweb.schedular.ReleseStoneSchedular;
import com.techhive.shivamweb.service.NotificationService;

/***
 * @author neel if application stop then on startup check if any stone is on
 *         hold i.e. view request in progress than if two hours is completed
 *         then relese stone or two hours is left then reschedule task to relese
 *         after remaning time
 */
@Component
public class ApplicationStartup implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	ViewRequestRepository viewRequestRepository;

	@Autowired
	PktMasterRepository pktMasterRepository;
	
	@Autowired
	NotificationService notificationService;

	// ContextStartedEvent
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		List<ViewRequest> viewRequestList = viewRequestRepository.findByInProgress(true);
		for (ViewRequest viewRequest : viewRequestList) {
			if (viewRequest.getEndDate().after(new Date())) {
				try {
					ReSchedular(viewRequest);
				} catch (SchedulerException | ParseException e) {
					e.printStackTrace();
				}
			} else {
				PktMaster pkt = viewRequest.getPktMaster();
				pkt.setIsHold(false);
				pktMasterRepository.saveAndFlush(pkt);
				viewRequest.setInProgress(false);
				viewRequestRepository.saveAndFlush(viewRequest);
				notificationService.setCriteriaForNotification(pkt.getId(), pkt.getStoneId(),"stoneRelease");
				deleteSchedular(viewRequest.getId());
			}
		}
		abandonedCartNotification();
	}

	public void deleteSchedular(String viewRequestId) {
		try {
			JobKey jobKey = JobKey.jobKey(viewRequestId, "ReleseStoneSchedular");
			SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();
			Scheduler sched = schedFact.getScheduler();
			sched.deleteJob(jobKey);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}

	}

	public void ReSchedular(ViewRequest viewRequest) throws SchedulerException, ParseException {

		String expression = null;

		try {

			JobKey jobKey = JobKey.jobKey(viewRequest.getId(), "ReleseStoneSchedular");

			JobDetail job = JobBuilder.newJob(ReleseStoneSchedular.class).withIdentity(jobKey).build();
			//
			// Trigger trigger = TriggerBuilder.newTrigger()
			// .withIdentity(viewRequest.getId(), "ReleseStoneSchedular").startNow()
			// .withSchedule(CronScheduleBuilder.cronSchedule(expression)
			// .withMisfireHandlingInstructionDoNothing())
			// .build();
			//
			Calendar cal = Calendar.getInstance(); // creates calendar
			cal.setTime(viewRequest.getEndDate()); // sets calendar time/date
			expression = "* " + cal.get(Calendar.MINUTE) + " " + cal.get(Calendar.HOUR_OF_DAY) + " * * ?";
			Trigger trigger = TriggerBuilder.newTrigger().withIdentity(viewRequest.getId(), "ReleseStoneSchedular")
					.startNow()
					.withSchedule(
							CronScheduleBuilder.cronSchedule(expression).withMisfireHandlingInstructionFireAndProceed())
					.build();
			viewRequest.setEndDate(cal.getTime());
			viewRequestRepository.saveAndFlush(viewRequest);
			SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();
			Scheduler sched = schedFact.getScheduler();

			sched.deleteJob(jobKey);
			sched.start();
			sched.scheduleJob(job, trigger);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}

	}

	public void abandonedCartNotification() {
		//daily 6 pm
		String expression = "	0 0 18 1/1 * ? *";
		final String abandondCart = "abandondCart";
		final String abandondCartGroup = "abandondCartGroup";

		try {

			JobKey jobKey = JobKey.jobKey(abandondCart, abandondCartGroup);

			JobDetail job = JobBuilder.newJob(AbandonedCartNotification.class).withIdentity(jobKey).build();

			Trigger trigger = TriggerBuilder.newTrigger().withIdentity(abandondCart, abandondCartGroup).startNow()
					.withSchedule(
							CronScheduleBuilder.cronSchedule(expression).withMisfireHandlingInstructionFireAndProceed())
					.build();

			SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();
			Scheduler sched = schedFact.getScheduler();

			sched.deleteJob(jobKey);
			sched.start();
			sched.scheduleJob(job, trigger);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}

	}

}
