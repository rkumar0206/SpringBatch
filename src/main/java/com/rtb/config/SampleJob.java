package com.rtb.config;

import java.io.File;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import com.rtb.model.StudentCsv;
import com.rtb.processor.FirstItemProcessor;
import com.rtb.reader.FirstItemReader;
import com.rtb.writer.FirstItemWriter;

@Configuration
public class SampleJob {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

//	@Autowired
//	private SecondTasklet secondTasklet;

	@Autowired
	private FirstItemReader firstItemReader;

	@Autowired
	private FirstItemProcessor firstItemProcessor;

	@Autowired
	private FirstItemWriter firstItemWriter;

	@Bean
	public Job chunkJob() {

		return jobBuilderFactory.get("Chunk Job").incrementer(new RunIdIncrementer()).start(firstChunkStep()).build();

	}

	private Step firstChunkStep() {

		return stepBuilderFactory.get("First Chink Step")
				.<StudentCsv, StudentCsv>chunk(3)
				.reader(flatFileItemReader())
				//.processor(firstItemProcessor)
				.writer(firstItemWriter)
				.build();
	}


	/**
	 * For making FlatFileItemReader (i.e. csv file reader), 
	 * we need to configure our flatFileItemReader.
	 * 
	 * Steps : 
	 * 
	 * 1 -> set the source or the path where the csv file exists.
	 * 2 -> Configure the line mapper : 
	 * 		LineMapper has two componenents :- LineTokenizer and Bean Mapper
	 * 		3 -> configure Line Tokenier using DelimitedLineTokenizer(String delimiter)
	 * 		setNames() method is used to define the headers
	 * 		setDelimiter() method is used to pass the delimiter string like, , | 
	 * 4 -> configure the Bean Mapper using the setFieldSetMapper(new BeanWrapperFieldSetMapper<StudentCsv>()
	 * 		here basically we need to configure our target Object class
	 * 5 -> set the number of lines to skip
	 */
	
	public FlatFileItemReader<StudentCsv> flatFileItemReader() {

		
		FlatFileItemReader<StudentCsv> flatFileItemReader = new FlatFileItemReader<>();

		flatFileItemReader.setResource(new FileSystemResource(new File(
				"R:\\spring_workspaces\\spring_boot_spring_batch\\spring_batch_gradle\\inputfiles\\students.csv")));

		flatFileItemReader.setLineMapper(new DefaultLineMapper<StudentCsv>() {

			{
				// by default the delimated line tokenizer will be comma, 
				// we don't need to explicitly set it
				setLineTokenizer(new DelimitedLineTokenizer() {

					{
						setNames("ID", "First Name", "Last Name", "Email");
					}
				});
				
				setFieldSetMapper(new BeanWrapperFieldSetMapper<>() {
					
					{
						
						setTargetType(StudentCsv.class);
					}
				});
			}

		});

		// skipping the first line as that is our column header
		flatFileItemReader.setLinesToSkip(1);
		
		return flatFileItemReader;
	}

}
