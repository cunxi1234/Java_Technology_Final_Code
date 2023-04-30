package com.techstore.service;

import java.text.ParseException;

import org.springframework.data.domain.Page;

import com.techstore.dto.SearchLienHeObject;
import com.techstore.model.LienHe;

public interface ContactService {

	Page<LienHe> getLienHeByFilter(SearchLienHeObject object, int page) throws ParseException;

	LienHe findById(long id);
	
	LienHe save(LienHe lh);
	
	int countByTrangThai(String trangThai);

}
