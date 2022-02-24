package com.rtb.config;

import java.io.IOException;
import java.io.Writer;
import java.util.Date;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileHeaderCallback;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.rtb.model.StudentJdbc;
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
	
	@Autowired
	private DataSource dataSource;

//	@Autowired
//	private ExpenseCategoryService expenseCategoryService;
	
	@Bean
	public Job chunkJob() {

		return jobBuilderFactory.get("Chunk Job").incrementer(new RunIdIncrementer()).start(firstChunkStep()).build();

	}

	private Step firstChunkStep() {

		return stepBuilderFactory.get("First Chink Step")
				.<StudentJdbc, StudentJdbc>chunk(3)
				.reader(jdbcCursorItemReader())
				.writer(flatFileItemWriter(null))
				.build();
	}

	/**
	 * 
	 * For reading contents from sql database
	 */
	public JdbcCursorItemReader<StudentJdbc> jdbcCursorItemReader() {
		
		JdbcCursorItemReader<StudentJdbc> jdbcCursorItemReader = 
				new JdbcCursorItemReader<>();
		
		jdbcCursorItemReader.setDataSource(dataSource);
		
		jdbcCursorItemReader.setSql("select id, first_name as firstName, last_name as lastName, email from student");
		
		jdbcCursorItemReader.setRowMapper(new BeanPropertyRowMapper<>() {
			
			{
				setMappedClass(StudentJdbc.class);
			}
		});
				
		return jdbcCursorItemReader;
	}
	
	
//	/**
//	 * 
//	 * Reading from REST API call (rest template)
//	 */
//	private ItemReaderAdapter<ExpenseCategory> itemReaderAdapter() {
//		
//		ItemReaderAdapter<ExpenseCategory> itemReaderAdapter = 
//				new ItemReaderAdapter<>();
//		
//		itemReaderAdapter.setTargetObject(expenseCategoryService);
//		
//		// the method name passing in this method should always return 
//		// only one element and not the list of element at a time
//		itemReaderAdapter.setTargetMethod("getExpenseCategory");
//		
//		
//		// Note : for passing any argument to the method we can 
//		// use -  itemReaderAdapter.setArguments(new Object[] {1L,"test"});
//		
//		return itemReaderAdapter;
//	}
	
	@StepScope
	@Bean
	public FlatFileItemWriter<StudentJdbc> flatFileItemWriter(
			@Value("#{jobParameters['outputFile']}") FileSystemResource fileSystemResource
			){
		
		FlatFileItemWriter<StudentJdbc> flatFileItemWriter = 
				new FlatFileItemWriter<>();
		
		flatFileItemWriter.setResource(fileSystemResource);
		
		flatFileItemWriter.setHeaderCallback((writer) -> {
			
			writer.write("Id,First Name,Last Name,Email");
		});
		
		flatFileItemWriter.setLineAggregator(new DelimitedLineAggregator<>() {
			
			{
				
				//setDelimiter("|")  // by default ","
				setFieldExtractor(new BeanWrapperFieldExtractor<>() {
					
					{
						setNames(new String[] {"id", "firstName", "lastName", "email"});
					}
				});
			}
		});
		
		flatFileItemWriter.setFooterCallback((writer) -> {
			
			writer.write("created @ " + new Date());
			
		});
		
		return flatFileItemWriter;
		
	}
	
	
}
