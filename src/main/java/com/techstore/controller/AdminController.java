package com.techstore.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.techstore.dto.ListTodoDTO;
import com.techstore.model.NguoiDung;
import com.techstore.model.VaiTro;
import com.techstore.service.CategoryService;
import com.techstore.service.OrderService;
import com.techstore.service.HangSanXuatService;
import com.techstore.service.ContactService;
import com.techstore.service.UserService;
import com.techstore.service.RoleService;

@Controller
@RequestMapping("/admin")
@SessionAttributes("loggedInUser")
public class AdminController {

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private HangSanXuatService hangSXService;

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;
	
	@Autowired
	private ContactService contactService;

	@Autowired
	private OrderService orderService;

	@ModelAttribute("loggedInUser")
	public NguoiDung loggedInUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return userService.findByEmail(auth.getName());
	}

	@GetMapping
	public String adminPage(Model model) {
		ListTodoDTO listCongViec = new ListTodoDTO();
		listCongViec.setSoDonHangMoi(orderService.countByTrangThaiDonHang("Đang chờ giao"));
		listCongViec.setSoDonhangChoDuyet(orderService.countByTrangThaiDonHang("Chờ duyệt"));
		listCongViec.setSoLienHeMoi(contactService.countByTrangThai("Đang chờ trả lời"));
		
		model.addAttribute("listCongViec", listCongViec);
		return "admin/trangAdmin";
	}

	@GetMapping("/danh-muc")
	public String quanLyDanhMucPage() {
		return "admin/quanLyDanhMuc";
	}

	@GetMapping("/nhan-hieu")
	public String quanLyNhanHieuPage() {
		return "admin/quanLyNhanHieu";
	}

	@GetMapping("/lien-he")
	public String quanLyLienHePage() {
		return "admin/quanLyLienHe";
	}
	
	@GetMapping("/san-pham")
	public String quanLySanPhamPage(Model model) {
		model.addAttribute("listNhanHieu", hangSXService.getALlHangSX());
		model.addAttribute("listDanhMuc", categoryService.getAllDanhMuc());
		return "admin/quanLySanPham";
	}

	@GetMapping("/profile")
	public String profilePage(Model model, HttpServletRequest request) {
		model.addAttribute("user", getSessionUser(request));
		return "admin/profile";
	}

	@PostMapping("/profile/update")
	public String updateNguoiDung(@ModelAttribute NguoiDung nd, HttpServletRequest request) {
		NguoiDung currentUser = getSessionUser(request);
		currentUser.setDiaChi(nd.getDiaChi());
		currentUser.setHoTen(nd.getHoTen());
		currentUser.setSoDienThoai(nd.getSoDienThoai());
		userService.updateUser(currentUser);
		return "redirect:/admin/profile";
	}

	@GetMapping("/don-hang")
	public String quanLyDonHangPage(Model model) {
		Set<VaiTro> vaiTro = new HashSet<>();
		// lấy danh sách shipper
		vaiTro.add(roleService.findByTenVaiTro("ROLE_SHIPPER"));
		List<NguoiDung> shippers = userService.getNguoiDungByVaiTro(vaiTro);
		for (NguoiDung shipper : shippers) {
			shipper.setListDonHang(orderService.findByTrangThaiDonHangAndShipper("Đang giao", shipper));
		}
		model.addAttribute("allShipper", shippers);
		return "admin/quanLyDonHang";
	}

	@GetMapping("/tai-khoan")
	public String quanLyTaiKhoanPage(Model model) {
	    model.addAttribute("listVaiTro", roleService.findAllVaiTro());
		return "admin/quanLyTaiKhoan";
	}
	
	@GetMapping("/thong-ke")
	public String thongKePage(Model model) {
		return "admin/thongKe";
	}
	
	public NguoiDung getSessionUser(HttpServletRequest request) {
		return (NguoiDung) request.getSession().getAttribute("loggedInUser");
	}
	
	

}
