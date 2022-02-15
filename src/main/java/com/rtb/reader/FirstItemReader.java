package com.rtb.reader;

import java.util.Arrays;
import java.util.List;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;

/*
 * Item Reader is responsible for reading content from the data source
 */

@Component
public class FirstItemReader implements ItemReader<Integer> {

	// our source data
	List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

	int index = 0;

	/*
	 * In this method we have to return only one value at a time.
	 * This method will be called for each value of the datasource and 
	 * will be called until a null value is passed 
	 */
	@Override
	public Integer read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		
		System.out.println("Inside item reader");

		Integer item;

		if (index < list.size()) {

			item = list.get(index);
			index++;
			return item;
		}

		// when there are no more item the resetting the index to 0, as if we want to
		// re-run the item reader
		index = 0;
		// when returning null value we tell item reader that there are no more records
		// to read in the data-source;
		return null;
	}

}
