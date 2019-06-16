package com.supermarket.loyaltycontest.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.supermarket.loyaltycontest.services.MyServices;
import com.supermarket.loyaltycontest.services.TestService;


@Controller
public class TestController {
	
	@Autowired
	TestService ts;
	
	@RequestMapping(value="/testCw",method=RequestMethod.GET)
	public String getCusPortal(HttpSession session,Model model){
		Object activeuserEmail=session.getAttribute("activeuserEmail");
		if(org.springframework.util.StringUtils.isEmpty(activeuserEmail)){
			return "customerLogin";
		}
		
		ts.addweekpoints(activeuserEmail.toString());
		
		return "redirect:getCustomerPortal";
	}
	
	
	@RequestMapping(value="/testCm",method=RequestMethod.GET)
	public String getCusPortalm(HttpSession session,Model model){
		Object activeuserEmail=session.getAttribute("activeuserEmail");
		if(org.springframework.util.StringUtils.isEmpty(activeuserEmail)){
			return "customerLogin";
		}
		
		ts.addmonthpoints(activeuserEmail.toString());
		
		return "redirect:getCustomerPortal";
	}
}
