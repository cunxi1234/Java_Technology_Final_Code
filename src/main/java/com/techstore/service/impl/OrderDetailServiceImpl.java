package com.techstore.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techstore.model.OrderDetail;
import com.techstore.repository.OrderDetailRepository;
import com.techstore.service.OrderDetailService;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {
	
	@Autowired
	private OrderDetailRepository repo;
	
	@Override
	public List<OrderDetail> save(List<OrderDetail> list)
	{	
		return repo.saveAll(list);
	}
}
