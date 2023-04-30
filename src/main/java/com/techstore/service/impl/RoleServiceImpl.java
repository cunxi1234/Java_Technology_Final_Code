package com.techstore.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techstore.model.VaiTro;
import com.techstore.repository.RoleRepository;
import com.techstore.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {
	

	@Autowired
	private RoleRepository repo;

	@Override
	public VaiTro findByTenVaiTro(String tenVaiTro) {
		return repo.findByTenVaiTro(tenVaiTro);
	}

	@Override
	public List<VaiTro> findAllVaiTro() {
		return repo.findAll();
	}


}
