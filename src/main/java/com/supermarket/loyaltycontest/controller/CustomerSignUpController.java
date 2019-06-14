package com.supermarket.loyaltycontest.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.supermarket.loyaltycontest.dao.CustomerDao;
import com.supermarket.loyaltycontest.model.Customer;


@Controller
//@RequestMapping("/customer")
public class CustomerSignUpController {
	@Autowired
	CustomerDao cdao;
	
	@RequestMapping(value="/customerSignUp",method=RequestMethod.GET)
	public String getSignUp(){
		
		return "customerSignUp";
	}
	
	@RequestMapping(value="/customerSignUp",method=RequestMethod.POST)
	public String signUp(@ModelAttribute Customer c){
		//c.settPoints(1);
		cdao.signup(c);
		return "customerLogin";
	}
}
