package com.techstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techstore.model.HangSanXuat;
import org.springframework.stereotype.Repository;

@Repository
public interface ManufacturerRepository extends JpaRepository<HangSanXuat, Long>{

}
