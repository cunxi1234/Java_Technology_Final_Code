package com.techstore.service;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;

import com.techstore.dto.AccountDTO;
import com.techstore.model.NguoiDung;
import com.techstore.model.VaiTro;

public interface UserService {

	NguoiDung findByEmail(String email);

	NguoiDung findByConfirmationToken(String confirmationToken);

	NguoiDung saveUserForMember(NguoiDung nd);

	NguoiDung findById(long id);

	NguoiDung updateUser(NguoiDung nd);

	void changePass(NguoiDung nd, String newPass);

	Page<NguoiDung> getNguoiDungByVaiTro(Set<VaiTro> vaiTro, int page);

	List<NguoiDung> getNguoiDungByVaiTro(Set<VaiTro> vaiTro);
	
	NguoiDung saveUserForAdmin(AccountDTO dto);

	void deleteById(long id);

}
