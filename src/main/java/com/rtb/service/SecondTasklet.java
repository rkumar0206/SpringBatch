package com.rtb.service;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Service;

@Service
public class SecondTasklet implements Tasklet{

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		
		System.out.println("This is second tasklet step.");
		
		// we can use the job execution context value in every step as job execution value is
		// always in job level
		System.out.println(chunkContext.getStepContext().getJobExecutionContext());
		
		return RepeatStatus.FINISHED;
		
	}

}
