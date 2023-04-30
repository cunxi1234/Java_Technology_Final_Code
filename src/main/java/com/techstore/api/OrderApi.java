package com.techstore.api;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.techstore.model.OrderDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techstore.dto.SearchDonHangObject;
import com.techstore.model.DonHang;
import com.techstore.model.SanPham;
import com.techstore.service.OrderService;
import com.techstore.service.UserService;

@RestController
@RequestMapping("/api/don-hang")
public class OrderApi {

	@Autowired
	private OrderService orderService;

	@Autowired
	private UserService userService;

	// lấy danh sách đơn hàng theo search object
	@GetMapping("/all")
	public Page<DonHang> getDonHangByFilter(@RequestParam(defaultValue = "1") int page, @RequestParam String trangThai,
			@RequestParam String tuNgay, @RequestParam String denNgay) throws ParseException {

		SearchDonHangObject object = new SearchDonHangObject();
		object.setDenNgay(denNgay);
		object.setTrangThaiDon(trangThai);
		object.setTuNgay(tuNgay);
		Page<DonHang> listDonHang = orderService.getAllDonHangByFilter(object, page);
		return listDonHang;
	}

	@GetMapping("/{id}")
	public DonHang getDonHangById(@PathVariable long id) {
		return orderService.findById(id);
	}

	// phân công đơn hàng
	@PostMapping("/assign")
	public void phanCongDonHang(@RequestParam("shipper") String emailShipper,
			@RequestParam("donHangId") long donHangId) {
		DonHang dh = orderService.findById(donHangId);
		dh.setTrangThaiDonHang("Đang giao");
		dh.setShipper(userService.findByEmail(emailShipper));

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {

			String dateStr = format.format(new Date());
			Date date = format.parse(dateStr);
			dh.setNgayGiaoHang(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		orderService.save(dh);
	}

	// xác nhận hoàn thành đơn hàng
	@PostMapping("/update")
	public void xacNhanHoanThanhDon(@RequestParam("donHangId") long donHangId,
			@RequestParam("ghiChu") String ghiChuAdmin) {
		DonHang dh = orderService.findById(donHangId);
		
		for(OrderDetail ct : dh.getDanhSachChiTiet()) {
			SanPham sp = ct.getSanPham();
			sp.setDonViBan(sp.getDonViBan() + ct.getSoLuongNhanHang());
			sp.setDonViKho(sp.getDonViKho() - ct.getSoLuongNhanHang() );
		}
		dh.setTrangThaiDonHang("Hoàn thành");
		String ghiChu = dh.getGhiChu();
		if (!ghiChuAdmin.equals("")) {
			ghiChu += "<br> Ghi chú admin:\n" + ghiChuAdmin;
		}
		dh.setGhiChu(ghiChu);
		orderService.save(dh);
	}

	// xác nhận hoàn thành đơn hàng
	@PostMapping("/cancel")
	public void huyDonHangAdmin(@RequestParam("donHangId") long donHangId) {
		DonHang dh = orderService.findById(donHangId);
		dh.setTrangThaiDonHang("Đã bị hủy");
		orderService.save(dh);
	}

	// lấy dữ liệu làm báo cáo thống kê
	@GetMapping("/report")
	public List<Object> test() {
		return orderService.layDonHangTheoThangVaNam();
	}
}
