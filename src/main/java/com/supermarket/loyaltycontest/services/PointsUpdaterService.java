package com.supermarket.loyaltycontest.services;

import java.sql.Timestamp;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supermarket.loyaltycontest.dao.CustomerDao;
import com.supermarket.loyaltycontest.model.Score;


@Service
public class PointsUpdaterService {
	@Autowired
	private CustomerDao cdao;
	
	public boolean updatePoint(int points,String userEmail){
		Score s=new Score();
		s.setCheckinDate(new Timestamp(new Date().getTime()));
		s.setPoints(points);
		cdao.updateScore(userEmail,s);
		
		return true;
	}
}
