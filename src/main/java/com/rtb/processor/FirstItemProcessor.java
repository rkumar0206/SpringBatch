package com.rtb.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

/*
 * ItemProcesor is used to process the data coming from the item reader.
 * We have to define two datatypes one for input and one for output,
 * input is the datatype coming from the Item reader and output is of
 * datatype which will be passed to the Item Writer.
 * 
 * here input type is integer and output type is long
 */

@Component
public class FirstItemProcessor implements ItemProcessor<Integer, Long>{

	@Override
	public Long process(Integer item) throws Exception {
		
		System.out.println("Inside item processor");
		
		return Long.valueOf(item + 20);
	}

}
