package com.techstore.service;

import java.util.List;

import com.techstore.model.CartIndex;
import com.techstore.model.GioHang;
import com.techstore.model.SanPham;

public interface CardIndexService {
	
	List<CartIndex> getChiMucGioHangByGioHang(GioHang g);
	
	CartIndex getChiMucGioHangBySanPhamAndGioHang(SanPham sp, GioHang g);
	
	CartIndex saveChiMucGiohang(CartIndex c);
	
	void deleteChiMucGiohang(CartIndex c);
	
	void deleteAllChiMucGiohang(List<CartIndex> c);
	
}
