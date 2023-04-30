package com.techstore.service;

import java.util.List;

import com.techstore.model.OrderDetail;

public interface OrderDetailService {
	List<OrderDetail> save(List<OrderDetail> list);
}
