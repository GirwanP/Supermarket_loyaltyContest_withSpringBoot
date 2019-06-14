package com.supermarket.loyaltycontest.dao;

import java.util.List;

import com.supermarket.loyaltycontest.model.Customer;
import com.supermarket.loyaltycontest.model.Score;


public interface CustomerDao {
	void signup(Customer c);
	boolean login(String un,String pw);
	List<Customer> getAll();
	Customer getById(int id);
	Customer getByEmail(String email);
	void removeCustomer(int id);
	void updateCustomer(Customer c);
	void updateScore(String email,Score score);
	int getScore(int id);
	
	boolean isUserNameValid(String un);
}
