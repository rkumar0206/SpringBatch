package com.rtb.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.rtb.request.JobParamRequest;

@Service
public class JobService {

	@Autowired
	JobLauncher jobLauncher;

	@Qualifier("firstJob")
	@Autowired
	Job firstJob;

	@Qualifier("secondJob")
	@Autowired
	Job secondJob;

	// using @Aync to make this function as asynchronous
	@Async
	public void startJob(String jobName, List<JobParamRequest> jobParamRequestList) {

		// we need to create job parameters here, as, when starting
		// a job from here the run.id or the custom parameters using the run
		// configuration will no longer work, so for uniquely differentiating
		// the job we need to pass some parameters everytime we run the job

		Map<String, JobParameter> params = new HashMap<>();
		params.put("currentTime", new JobParameter(System.currentTimeMillis()));

		jobParamRequestList.forEach(jobParamReq -> {

			params.put(jobParamReq.getParamKey(),
					new JobParameter(jobParamReq.getParamValue()));

		});

		JobParameters jobParameters = new JobParameters(params);

		try {

			JobExecution jobExecution = null;

			if (jobName.equals("First Job")) {

				jobExecution = jobLauncher.run(firstJob, jobParameters);

			} else if (jobName.equals("Second Job")) {

				jobExecution = jobLauncher.run(secondJob, jobParameters);

			}

			System.out.println("jobExecution ID : " + jobExecution.getId());

		} catch (Exception e) {

			System.out.println("Exception while starting job...");
		}

	}
}
