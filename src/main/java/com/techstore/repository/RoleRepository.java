package com.techstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techstore.model.VaiTro;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<VaiTro, Long> {

	VaiTro findByTenVaiTro(String tenVaiTro);
}
