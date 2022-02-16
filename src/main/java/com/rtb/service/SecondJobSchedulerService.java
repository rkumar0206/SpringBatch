package com.rtb.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class SecondJobSchedulerService {

	@Autowired
	JobLauncher jobLauncher;

	@Qualifier("secondJob")
	@Autowired
	Job secondJob;
	
	/*
	 * @Schedule is used to schedule a job.
	 * This job will run according to the cron we pass to
	 * the scheduled params.
	 */
	@Scheduled(cron = "0 0/1 * 1/1 * ?")  // every 1 minute - get from (http://www.cronmaker.com/?1)
	public void secondJobScheduler() {
		
		// we need to create job parameters here, as, when starting
		// a job from here the run.id or the custom parameters using the run
		// configuration will no longer work, so for uniquely differentiating
		// the job we need to pass some parameters everytime we run the job

		Map<String, JobParameter> params = new HashMap<>();
		params.put("currentTime", new JobParameter(System.currentTimeMillis()));

		JobParameters jobParameters = new JobParameters(params);

		try {

			JobExecution jobExecution = null;

			jobExecution = jobLauncher.run(secondJob, jobParameters);

			System.out.println("jobExecution ID : " + jobExecution.getId());

		} catch (Exception e) {

			System.out.println("Exception while starting job...");
		}

	}
}
