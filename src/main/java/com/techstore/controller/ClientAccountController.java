package com.techstore.controller;



import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import com.google.common.collect.Lists;

import com.techstore.dto.PasswordDTO;
import com.techstore.model.DonHang;
import com.techstore.model.NguoiDung;
import com.techstore.model.ResponseObject;
import com.techstore.service.OrderService;
import com.techstore.service.UserService;
import com.techstore.service.ProductService;

import java.util.List;

@Controller
@SessionAttributes("loggedInUser")
@RequestMapping("/")

public class ClientAccountController {
	
	@Autowired
	private ProductService productService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private OrderService orderService;
	
	
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@ModelAttribute("loggedInUser")
	public NguoiDung loggedInUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return userService.findByEmail(auth.getName());
	}

	public NguoiDung getSessionUser(HttpServletRequest request) {
		return (NguoiDung) request.getSession().getAttribute("loggedInUser");
	}
	
	@GetMapping("/account")
	public String accountPage(HttpServletRequest res, Model model) {
		NguoiDung currentUser = getSessionUser(res);
		model.addAttribute("user", currentUser);
		List<DonHang> list = Lists.reverse(orderService.getDonHangByNguoiDung(currentUser));
		model.addAttribute("list",list);
		return "client/account";
	}
	
	@GetMapping("/changeInformation")
	public String clientChangeInformationPage(HttpServletRequest res,Model model) {
		NguoiDung currentUser = getSessionUser(res);
		model.addAttribute("user", currentUser);
		return "client/information";
	}

	@GetMapping("/changePassword")
	public String clientChangePasswordPage() {
		return "client/passwordChange";
	}
	
	@PostMapping("/updateInfo")
	@ResponseBody
	public ResponseObject commitChange(HttpServletRequest res,@RequestBody NguoiDung ng) {
		NguoiDung currentUser = getSessionUser(res);
		currentUser.setHoTen(ng.getHoTen());
		currentUser.setSoDienThoai(ng.getSoDienThoai());
		currentUser.setDiaChi(ng.getDiaChi());
		userService.updateUser(currentUser);
		return new ResponseObject();
	}
	
	@PostMapping("/updatePassword")
	@ResponseBody
	public ResponseObject passwordChange(HttpServletRequest res,@RequestBody PasswordDTO dto) {
		NguoiDung currentUser = getSessionUser(res);
		if (!passwordEncoder.matches( dto.getOldPassword(), currentUser.getPassword())) {
			ResponseObject re = new ResponseObject();
			re.setStatus("old");
			return re;
		}
		userService.changePass(currentUser, dto.getNewPassword());
		return new ResponseObject();
	}

}
