package com.techstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techstore.model.DanhMuc;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<DanhMuc, Long>{

}
