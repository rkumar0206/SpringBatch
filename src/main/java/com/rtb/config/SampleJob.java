package com.rtb.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.rtb.listeners.FirstJobListener;
import com.rtb.listeners.FirstStepListener;
import com.rtb.processor.FirstItemProcessor;
import com.rtb.reader.FirstItemReader;
import com.rtb.service.SecondTasklet;
import com.rtb.writer.FirstItemWriter;

@Configuration
public class SampleJob {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Autowired
	private SecondTasklet secondTasklet;
	
	@Autowired
	private FirstJobListener firstJobListener;
	
	@Autowired
	private FirstStepListener firstStepListener;
	
	@Autowired
	private FirstItemReader firstItemReader;
	
	@Autowired
	private FirstItemProcessor firstItemProcessor;
	
	@Autowired
	private FirstItemWriter firstItemWriter;
	
	// ---------------Tasklet Step---------------
	@Bean
	public Job firstJob() {

		// create a job by using JobBuilderFactpry and giving it a name, here, First Job
		// in start we need to pass the first step
		// for adding more steps we need to use next() method
		
		// incrementer : in spring batch, when a job is run it has an instance id
		// and the job with same instance cannot be run again after the job is completed
		// successfully, therefore for running the job again and again, we can use
		// an incrementer which will add a key value pair i.e. run.id = LONG value, 
		// and will incrementing the value with each job execution
		
		// we can attach listeners using the listener method
		
		return jobBuilderFactory.get("First Job")
				.incrementer(new RunIdIncrementer())
				.start(firstStep())
				.next(secondStep()) // for subsequent steps we need to use next method
				.listener(firstJobListener)
				.build();

	}

	private Step firstStep() {

		// using StepBuilderFactory we create a step and we also provide it a name, here, First Step
		// .tasklet is used to create a tasklet step
		
		return stepBuilderFactory.get("First Step")
				.tasklet(firstTask())
				.listener(firstStepListener)
				.build();
	}
	
	private Step secondStep() {
		
		// using StepBuilderFactory we create another step 
		// instead of proving the instance of interface we can
		// provide the instance of class which implements Tasklet interface
		
		return stepBuilderFactory.get("Second Step")
				.tasklet(secondTasklet).build();
	}

	private Tasklet firstTask() {

		// here we create our task, execute it and 
		// giving the status as finished
		
		return new Tasklet() {

			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

				System.out.println("This is first tasklet step.");
				
				// we can use the step execution context only at the step level
				System.out.println("STEP_SEC = " +chunkContext.getStepContext().getStepExecutionContext());
				
				return RepeatStatus.FINISHED;
			}
		};
	}
	
	/*
	 * private Tasklet secondTask() {
	 * 
	 * // here we create our another task, execute it and // giving the status as
	 * finished
	 * 
	 * return new Tasklet() {
	 * 
	 * @Override public RepeatStatus execute(StepContribution contribution,
	 * ChunkContext chunkContext) throws Exception {
	 * 
	 * System.out.println("This is second tasklet step."); return
	 * RepeatStatus.FINISHED; } }; }
	 */
	
	// ---------------------------------------
	
	
	// --------------Chunk Oriented Step----------------

	/**
	 * 
	 * In this job both types of steps is being executed
	 * 
	 */

		@Bean
		public Job secondJob() {
			
			return jobBuilderFactory.get("Second Job")
					.incrementer(new RunIdIncrementer())
					.start(firstChunkStep()) // chunk oriented step
					.next(secondStep()) // tasklet step
					.build();
			
		}
		
		private Step firstChunkStep() {
			
			return stepBuilderFactory.get("First Chunk Step")
					.<Integer, Long>chunk(3)   // we have to specify the input and output datatype with chunk size
					.reader(firstItemReader)
					.processor(firstItemProcessor)  // this optional, but, if it is not used then the input and output datatypes have to be same
					.writer(firstItemWriter)
					.build();
					
		}
	
	
	// -------------------------------------------

	
}
