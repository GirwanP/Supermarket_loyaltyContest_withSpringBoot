package com.supermarket.loyaltycontest.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.supermarket.loyaltycontest.dao.CustomerDao;
import com.supermarket.loyaltycontest.model.Score;
import com.supermarket.loyaltycontest.services.MyConstants;
import com.supermarket.loyaltycontest.services.MyServices;

@Controller
public class CustomerPortalController {
	private static final Logger logger = LoggerFactory.getLogger(CustomerLoginController.class);

	@Autowired
	private CustomerDao cdao;
	@Autowired
	private MyServices mservice;

	// use return "redirect:getCustomerPortal" ; to send this page after
	// login/updates
	@RequestMapping(value = "/getCustomerPortal", method = RequestMethod.GET)
	public String getCusPortal(HttpSession session, Model model) {
		Object activeuserEmail = session.getAttribute("activeuserEmail");
		if (org.springframework.util.StringUtils.isEmpty(activeuserEmail)) {
			return "customerLogin";
		}

		boolean daNClaimed = true, waNClaimed = true, maNClaimed = true;

		List<Score> scores = mservice.getSortedList(activeuserEmail.toString());
		// List<Score> scores =
		// cdao.getByEmail(session.getAttribute("activeuserEmail").toString()).getScores();
		// List<Score> scores = (new
		// MyServices()).getSortedList(session.getAttribute("activeuserEmail").toString());
		// test and remove

		// Collections.sort(scores, new Comparator<Score>() {
		// //@Override
		// public int compare(Score s1, Score s2) {
		// return s2.getCheckinDate().compareTo(s1.getCheckinDate());
		// }
		// });
		long tPeriod = MyConstants.dayLength;
		
		// try{
		if (!scores.isEmpty()) {
//			long diff = (new java.util.Date().getTime() - ((Score) scores.toArray()[0]).getCheckinDate().getTime());
//			logger.info(Long.toString(diff * (1)));
//			System.out.println(tPeriod);
//
//			if (diff <= tPeriod) {
//				System.out.println("already claimed point condition check:passed");
//				daNClaimed = false;
//			}
			boolean[] claimStatus=mservice.anClaimedStatus(scores);
			daNClaimed=claimStatus[0];
			waNClaimed=claimStatus[1];
			maNClaimed=claimStatus[2];
			System.out.println(claimStatus[0]+" "+claimStatus[1]+" "+claimStatus[2]);
		}
		// }finally{}
		model.addAttribute("customer", cdao.getByEmail(session.getAttribute("activeuserEmail").toString()));
		model.addAttribute("daNClaimed", daNClaimed);
		model.addAttribute("waNClaimed", waNClaimed);
		model.addAttribute("maNClaimed", maNClaimed);
		// scores.add(arg0)
		// model.addAttribute("scoreList", new
		// MyServices().getSortedList(activeuserEmail.toString()));
		model.addAttribute("scoreList", scores);

		return "customerPortal";
	}
}
