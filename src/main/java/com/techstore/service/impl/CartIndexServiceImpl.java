package com.techstore.service.impl;

import java.util.List;

import com.techstore.model.CartIndex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techstore.model.GioHang;
import com.techstore.model.SanPham;
import com.techstore.repository.CartIndexRepository;
import com.techstore.service.CardIndexService;

@Service
public class CartIndexServiceImpl implements CardIndexService {
	
	@Autowired
	private CartIndexRepository repo;
	
	@Override
	public CartIndex getChiMucGioHangBySanPhamAndGioHang(SanPham sp, GioHang g)
	{
		return repo.findBySanPhamAndGioHang(sp,g);
	}
	
	@Override
	public CartIndex saveChiMucGiohang(CartIndex c)
	{
		return repo.save(c);
	}
	
	@Override
	public void deleteChiMucGiohang(CartIndex c)
	{
		repo.delete(c);
	}
	
	@Override
	public List<CartIndex> getChiMucGioHangByGioHang(GioHang g)
	{
		return repo.findByGioHang(g);
	}
	
	@Override
	public void deleteAllChiMucGiohang(List<CartIndex> c)
	{
		repo.deleteAll(c);
	}

}
