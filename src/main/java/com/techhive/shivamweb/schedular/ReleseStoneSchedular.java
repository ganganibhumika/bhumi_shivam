package com.techhive.shivamweb.schedular;

import java.util.Optional;

import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.techhive.shivamweb.master.model.PktMaster;
import com.techhive.shivamweb.model.ViewRequest;
import com.techhive.shivamweb.repository.PktMasterRepository;
import com.techhive.shivamweb.repository.ViewRequestRepository;
import com.techhive.shivamweb.service.NotificationService;
import com.techhive.shivamweb.utils.BeanUtil;

/***
 * @author neel delete schedular and relese pkt stones by setting is holt to
 *         false and viewrequest.inprogress false
 */
public class ReleseStoneSchedular implements org.quartz.Job {
	@Autowired
	ViewRequestRepository viewRequestRepository = BeanUtil.getBean(ViewRequestRepository.class);

	@Autowired
	PktMasterRepository pktMasterRepository = BeanUtil.getBean(PktMasterRepository.class);

	@Autowired
	NotificationService notificationService = BeanUtil.getBean(NotificationService.class);;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobDetail jobDetail = context.getJobDetail();

		JobKey reportIdToRun = jobDetail.getKey();
		String viewRequestId = reportIdToRun.getName();
		Optional<ViewRequest> viewRequest = viewRequestRepository.findById(viewRequestId);
		if (viewRequest.isPresent()) {
			if (viewRequest.get().getInProgress() == true) {
				PktMaster pkt = viewRequest.get().getPktMaster();
				pkt.setIsHold(false);
				pktMasterRepository.saveAndFlush(pkt);
				viewRequest.get().setInProgress(false);
				viewRequestRepository.saveAndFlush(viewRequest.get());
				notificationService.setCriteriaForNotification(pkt.getId(), pkt.getStoneId(),"stoneRelease");
				deleteSchedular(viewRequestId);
			}
		}
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

}
