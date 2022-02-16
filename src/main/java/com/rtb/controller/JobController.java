package com.rtb.controller;

import java.util.List;

import org.springframework.batch.core.launch.JobExecutionNotRunningException;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.NoSuchJobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rtb.request.JobParamRequest;
import com.rtb.service.JobService;

@RestController
@RequestMapping("/api/job")
public class JobController {

	@Autowired
	JobService jobService;
	
	@Autowired
	JobOperator jobOperator;

	/**
	 * 
	 * @param jobName
	 * @return String
	 * 
	 * This is the example of starting any job using the rest API
	 * 
	 */

	@GetMapping("/start/{jobName}")
	public String startJob(@PathVariable String jobName, @RequestBody List<JobParamRequest> jobParamRequestList) {

		// this function is made asynchronous so that our api did not have
		// to wait for the job to be completed
		// the job will run in different thread
		jobService.startJob(jobName, jobParamRequestList);

		return "Job Started...";
	}
	
	
	@GetMapping("/stop/{jobExecutionId}")
	public String stopJob(@PathVariable Long jobExecutionId) {
		
		try {
			/*
			 * This will stop the job. But
			 * Note : If the job is chuck oriented and we are stopping the job
			 * while it's running the chunk then it will not be stopped for that 
			 * particular chunk, it will first finish that chunk and then it will
			 * be stopped.
			 */
			jobOperator.stop(jobExecutionId);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return "Job Stopped...";

	}

}
