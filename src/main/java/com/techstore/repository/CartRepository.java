package com.techstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techstore.model.GioHang;
import com.techstore.model.NguoiDung;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<GioHang, Long>{
	
	GioHang findByNguoiDung(NguoiDung n);
	
}
