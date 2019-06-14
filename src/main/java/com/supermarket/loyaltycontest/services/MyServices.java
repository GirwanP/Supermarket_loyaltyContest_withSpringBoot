package com.supermarket.loyaltycontest.services;

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
	
	//private TestDaoImpl cdao;
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

}
