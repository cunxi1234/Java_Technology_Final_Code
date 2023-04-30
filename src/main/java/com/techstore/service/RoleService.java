package com.techstore.service;

import java.util.List;

import com.techstore.model.VaiTro;

public interface RoleService {

	VaiTro findByTenVaiTro(String tenVaiTro);
	List<VaiTro> findAllVaiTro();
}
