package com.rtb.listeners;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class FirstStepListener implements StepExecutionListener{

	@Override
	public void beforeStep(StepExecution stepExecution) {
	
		System.out.println("\nBefore Step " + stepExecution.getStepName());
		System.out.println("Job execution context " + stepExecution.getJobExecution().getExecutionContext());
		System.out.println("Step execution context " + stepExecution.getExecutionContext());
		System.out.println();
		stepExecution.getExecutionContext().put("step_sec", "sec value");
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		
		System.out.println("\nAfter Step " + stepExecution.getStepName());
		System.out.println("Job execution context " + stepExecution.getJobExecution().getExecutionContext());
		System.out.println("Step execution context " + stepExecution.getExecutionContext());
		System.out.println();
		return null;
	}

	
}
