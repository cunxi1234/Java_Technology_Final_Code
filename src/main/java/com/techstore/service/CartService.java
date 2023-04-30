package com.techstore.service;

import com.techstore.model.GioHang;
import com.techstore.model.NguoiDung;

public interface CartService {
	
	GioHang getGioHangByNguoiDung(NguoiDung n);
	
	GioHang save(GioHang g);
}
