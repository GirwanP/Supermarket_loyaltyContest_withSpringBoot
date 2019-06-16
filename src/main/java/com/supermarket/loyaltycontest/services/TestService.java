package com.supermarket.loyaltycontest.services;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supermarket.loyaltycontest.dao.CustomerDao;
import com.supermarket.loyaltycontest.model.Score;

@Service
public class TestService {
	
	@Autowired
	private CustomerDao cdao;
	
//	public static void main(String[] args) {
//		MyServices ms=new MyServices();
//		List<Score> list=ms.getSortedList("a@gmail.com");
//		
//		for(Score s:list){
//			System.out.println(s.getCheckinDate()+"  points: "+s.getPoints());
//		}
//	}
	
	
	public void addweekpoints(String email){
		long d=new Date().getTime();
		
		for(char i=0;i<7;i++){
			System.out.println("################ d is="+d);
			Score s=new Score();
			s.setPoints(MyConstants.dailyPoint);
			s.setCheckinDate(new Timestamp(d));
			cdao.updateScore(email, s);
			
			d=d+MyConstants.dayLength ;
			System.out.println(d);
		}
	}
	
	
		public void addmonthpoints(String email){
			long d=new Date().getTime();
			
			for(char i=0;i<30;i++){
				Score s=new Score();
				s.setPoints(MyConstants.dailyPoint);
				s.setCheckinDate(new Timestamp(d));
				cdao.updateScore(email, s);
				
				d=d+MyConstants.dayLength;
			}
		
		
		}
}
