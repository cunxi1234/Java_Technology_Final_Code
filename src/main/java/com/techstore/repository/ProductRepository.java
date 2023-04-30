package com.techstore.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;


import com.techstore.model.SanPham;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<SanPham, Long>, QuerydslPredicateExecutor<SanPham>{

	
	List<SanPham> findFirst12ByDanhMucTenDanhMucContainingIgnoreCaseOrderByIdDesc(String dm);
	List<SanPham> findByIdIn(Set<Long> idList);
}
