package com.rtb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rtb.service.JobService;

@RestController
@RequestMapping("/api/job")
public class JobController {

	@Autowired
	JobService jobService;
	
	/**
	 * 
	 * @param jobName
	 * @return String
	 * 
	 * This is the example of starting any job using the rest API
	 * 
	 */

	@GetMapping("/start/{jobName}")
	public String startJob(@PathVariable String jobName) {

		// this function is made asynchronous so that our api did not have 
		// to wait for the job to be completed
		// the job will run in different thread
		jobService.startJob(jobName);  
		
		return "Job Started...";
	}

}
