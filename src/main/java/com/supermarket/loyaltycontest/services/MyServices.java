package com.supermarket.loyaltycontest.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;



//import javax.inject.Inject;

import org.hibernate.service.spi.InjectService;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supermarket.loyaltycontest.dao.CustomerDao;
import com.supermarket.loyaltycontest.model.Customer;
import com.supermarket.loyaltycontest.model.Score;

@Service
public class MyServices {
	
	
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
		if(isEligible(tPeriod,filterWeeklyScores(ClaimList))){
			List<Score> dClaimList=filterDailyScores(ClaimList);
			if(dClaimList.isEmpty()){
				return false;
			}
			if(dClaimList.toArray().length<7){
				return false;
			}
			long diff = 0;
			try{
				
			diff=(((Score)dClaimList.toArray()[0]).getCheckinDate().getTime()- ((Score)dClaimList.toArray()[6]).getCheckinDate().getTime());
			}
			finally{}
			if(diff==tPeriod){
				return true;
			}
		}
		return false;
	}
	public boolean manClaimed(List<Score> ClaimList){
		long tPeriod = MyConstants.dayLength*30;
		if(isEligible(tPeriod,filterMonthlyScores(ClaimList))){
			List<Score> dClaimList=filterDailyScores(ClaimList);
			
			if(dClaimList.isEmpty()){
				return false;
			}
			if(dClaimList.toArray().length<30){
				return false;
			}
			
			
			long diff = (((Score)dClaimList.toArray()[0]).getCheckinDate().getTime()- ((Score)dClaimList.toArray()[6]).getCheckinDate().getTime());
			
			if(diff==tPeriod){
				return true;
			}
		}
		
		
		return false;
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
			if(s.getPoints()==MyConstants.dailyPoint){
				wlist.add(s);
			}
		}
		return wlist;
	}
	public List<Score> filterMonthlyScores(List<Score> slist){
List<Score> mlist=new ArrayList<Score>();
		
		for(Score s: slist){
			if(s.getPoints()==MyConstants.dailyPoint){
				mlist.add(s);
			}
		}
		return mlist;
	}
	
	public boolean isEligible(long tPeriod,List<Score> ClaimList){
		long diff = (new java.util.Date().getTime() - ((Score)ClaimList.toArray()[0]).getCheckinDate().getTime());
//		logger.info(Long.toString(diff * (1)));
		System.out.println(tPeriod);

		if (diff <= tPeriod) {
			System.out.println("already claimed point condition check:passed");
			return false;
		}

		return true;
	}
}
