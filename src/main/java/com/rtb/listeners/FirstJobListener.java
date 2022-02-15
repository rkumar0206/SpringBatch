package com.rtb.listeners;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class FirstJobListener implements JobExecutionListener {

	@Override
	public void beforeJob(JobExecution jobExecution) {

		System.out.println("\nBefore job : " + jobExecution.getJobInstance().getJobName());
		System.out.println("Job Params : " + jobExecution.getJobParameters());
		System.out.println("Job Exec context : " + jobExecution.getExecutionContext());
		System.out.println();
		jobExecution.getExecutionContext().put("jec", "jec value");
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		System.out.println("\nAfter job : " + jobExecution.getJobInstance().getJobName());
		System.out.println("Job Params : " + jobExecution.getJobParameters());
		System.out.println("Job Exec context : " + jobExecution.getExecutionContext());
		System.out.println();
	}

}
