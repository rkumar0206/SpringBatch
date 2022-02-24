//package com.rtb.service;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//import com.rtb.model.ExpenseCategory;
//import com.rtb.model.ExpenseCategoryResponse;
//
//@Service
//public class ExpenseCategoryService {
//
//	@Autowired
//	private RestTemplate restTemplate;
//	
//	private List<ExpenseCategory> expenseCategories;
//	
//	public List<ExpenseCategory> restCallToGetExpenseCategory() {
//		
//		ExpenseCategoryResponse expenseCategoryResponse = 
//				restTemplate.getForObject("https://serene-crag-89401.herokuapp.com/api/rrrrr/expenseCategories/uid",
//						ExpenseCategoryResponse.class);
//		
//		expenseCategories = expenseCategoryResponse.getExpenseCategories();
//		
//		return expenseCategories;
//	}
//	
//	public ExpenseCategory getExpenseCategory() {
//		
//		if (expenseCategories == null) {
//			
//			restCallToGetExpenseCategory();
//			
//		}
//		
//		if (expenseCategories != null && !expenseCategories.isEmpty()) {
//			
//			
//			return expenseCategories.remove(0);
//		}
//		
//		return null;
//	}
//	
//}
