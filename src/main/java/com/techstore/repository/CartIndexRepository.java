package com.techstore.repository;

import java.util.List;

import com.techstore.model.CartIndex;
import org.springframework.data.jpa.repository.JpaRepository;

import com.techstore.model.GioHang;
import com.techstore.model.SanPham;
import org.springframework.stereotype.Repository;

@Repository
public interface CartIndexRepository extends JpaRepository<CartIndex, Long>{
	
	CartIndex findBySanPhamAndGioHang(SanPham sp, GioHang g);
	
	List<CartIndex> findByGioHang(GioHang g);
}
