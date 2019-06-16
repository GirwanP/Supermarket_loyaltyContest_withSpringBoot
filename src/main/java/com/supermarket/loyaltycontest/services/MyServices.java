package com.supermarket.loyaltycontest.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;



//import javax.inject.Inject;

import org.hibernate.service.spi.InjectService;
import org.mockito.InjectMocks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supermarket.loyaltycontest.dao.CustomerDao;
import com.supermarket.loyaltycontest.model.Customer;
import com.supermarket.loyaltycontest.model.Score;

@Service
public class MyServices {
	private static final Logger logger=LoggerFactory.getLogger(MyServices.class);
	
	@Autowired
	//@Inject
	private CustomerDao cdao; //= new CustomerDaoImpl();
	
	//@Transactional
	public List<Score> getSortedList(String cEmail){
		
		
		System.out.println(cEmail+"  --from getsortedList method-Myservices class");
		Customer cus=cdao.getByEmail(cEmail);
		
		List<Score> sList=cus.getScores();
		for(Score ss:sList){
			System.out.println(ss.getCheckinDate());
		}
		Collections.sort(sList, new Comparator<Score>() {
			@Override
			public int compare(Score arg0, Score arg1) {
				
				return arg1.getCheckinDate().compareTo(arg0.getCheckinDate());
			}
		});
		return sList;
	}

	
	
	
	public boolean[] anClaimedStatus(List<Score> claimList){
		
		System.out.println("myservice anclaimed status method*************");
		return new boolean[]{
				danClaimed((claimList))
				,wanClaimed((claimList))
				,manClaimed((claimList))};
	}
	
	public boolean danClaimed(List<Score> ClaimList){
		long tPeriod = MyConstants.dayLength;
		
		 return isEligible(tPeriod,filterDailyScores(ClaimList));
		
//		return true;
	}
	
	public boolean wanClaimed(List<Score> ClaimList){
		long tPeriod = MyConstants.dayLength*7;
		List<Score> wList=filterWeeklyScores(ClaimList);
		System.out.println("inside wancliamed method");
		
		if(isEligible(tPeriod,wList)){
			System.out.println("iseligible-condition======");
			List<Score> dClaimList=filterDailyScores(ClaimList);
			
			if(dClaimList.isEmpty()){
				System.out.println("the day claim list is empty");
				return false;
			}
			if(dClaimList.toArray().length<7){
				System.out.println("the day claim less than 7");
				return false;
			}
			long diff = 0;
//			try{
				
			diff=(((Score)dClaimList.toArray()[0]).getCheckinDate().getTime()- ((Score)dClaimList.toArray()[6]).getCheckinDate().getTime()+MyConstants.dayLength);
//			}
//			finally{}
			
			for(int i=0;i<7;i++){
				Score s=(Score)dClaimList.toArray()[i];
				logger.info(s.getCheckinDate().toString()+"  "+s.getPoints());
			}
			System.out.println("^^^^^^^^^^^^^^^^^^^^");
			System.out.println("diff is="+diff);
			System.out.println("tperiod="+tPeriod);
			if(diff==tPeriod){
				return true;
			}
		}
		System.out.println("exiting wanclaimed method /n");
		return false;
	}
	
	
	
	
	
	public boolean manClaimed(List<Score> ClaimList){
		System.out.println("inside ma nClaimed method");
		long tPeriod = MyConstants.dayLength*30;
		if(isEligible(tPeriod,filterMonthlyScores(ClaimList))){
			List<Score> dClaimList=filterDailyScores(ClaimList);
			
			if(dClaimList.isEmpty()){
				return false;
			}
			if(dClaimList.toArray().length<30){
				return false;
			}			
			Score s1=(Score)dClaimList.toArray()[0];
			Score s2=(Score)dClaimList.toArray()[29];
			System.out.println("latest cliam is "+s1.getPoints()+" date:"+s1.getCheckinDate());
			System.out.println("latest cliam is "+s2.getPoints()+" date:"+s2.getCheckinDate());
			long diff = (((Score)dClaimList.toArray()[0]).getCheckinDate().getTime()- ((Score)dClaimList.toArray()[29]).getCheckinDate().getTime())+MyConstants.dayLength;
			System.out.println("tperiod="+tPeriod);
			System.out.println("diff="+diff);
			
			if(diff==tPeriod){
				return true;
			}
		}
			
		return false;
	}
	
	public boolean isEligible(long tPeriod,List<Score> ClaimList){
		System.out.println("inside iseligible");
		if(ClaimList.isEmpty()){
			System.out.println("list is empty");
			return true;
		}
		long diff = (new java.util.Date().getTime() - ((Score)ClaimList.toArray()[0]).getCheckinDate().getTime());
//		logger.info(Long.toString(diff*(1)));
		System.out.println("last claim is:"+((Score)ClaimList.toArray()[0]).getCheckinDate());
		System.out.println("tperiod="+tPeriod);
		System.out.println("diff="+diff);
		if (diff <= tPeriod) {
			System.out.println("already claimed point condition check:passed op=false");
			return false;
		}
		System.out.println("--------------leaving iseligible  op:true");
		return true;
	}
	
	
	
	
	
	
	
	public List<Score> filterDailyScores(List<Score> slist){
		List<Score> dlist=new ArrayList<Score>();
		
		for(Score s: slist){
			if(s.getPoints()==MyConstants.dailyPoint){
				dlist.add(s);
			}
		}
		return dlist;
	}
	public List<Score> filterWeeklyScores(List<Score> slist){
List<Score> wlist=new ArrayList<Score>();
		
		for(Score s: slist){
			if(s.getPoints()==MyConstants.weeklyPoint){
				wlist.add(s);
			}
		}
		return wlist;
	}
	public List<Score> filterMonthlyScores(List<Score> slist){
List<Score> mlist=new ArrayList<Score>();
		
		for(Score s: slist){
			if(s.getPoints()==MyConstants.monthlyPoint){
				mlist.add(s);
			}
		}
		return mlist;
	}
	

}
