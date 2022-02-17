package com.rtb.writer;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import com.rtb.model.StudentCsv;
import com.rtb.model.StudentJSON;

/*
 * Item Writer is the last stage of a chunk oriented step and is used to
 * do final implementation of the value passed by the item processor.
 */
@Component
public class FirstItemWriter implements ItemWriter<StudentJSON>{

	/**
	 * In this method we will get the list of item.
	 * The size of the list is basically the size of the chunk.
	 * If the size of the chunk is 3 the list size is also 3.
	 */
	@Override
	public void write(List<? extends StudentJSON> items) throws Exception {
		
		System.out.println("Inside Item Writer");
		
		items.stream().forEach(System.out::println);
		
	}

}
