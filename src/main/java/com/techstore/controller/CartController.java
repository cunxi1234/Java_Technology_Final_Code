package com.techstore.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.techstore.model.CartIndex;
import com.techstore.model.GioHang;
import com.techstore.model.NguoiDung;
import com.techstore.model.SanPham;
import com.techstore.service.CardIndexService;
import com.techstore.service.CartService;
import com.techstore.service.UserService;
import com.techstore.service.ProductService;

@Controller
@SessionAttributes("loggedInUser")
public class CartController {
	
	@Autowired
	private ProductService productService;
	@Autowired
	private UserService userService;
	@Autowired
	private CartService cartService;
	@Autowired
	private CardIndexService cardIndexService;
	
	@ModelAttribute("loggedInUser")
	public NguoiDung loggedInUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return userService.findByEmail(auth.getName());
	}
	
	public NguoiDung getSessionUser(HttpServletRequest request) {
		return (NguoiDung) request.getSession().getAttribute("loggedInUser");
	}
	
	@GetMapping("/cart")
	public String cartPage(HttpServletRequest res,Model model) {
		NguoiDung currentUser = getSessionUser(res);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Map<Long,String> quanity = new HashMap<Long,String>();
		List<SanPham> listsp = new ArrayList<SanPham>();
		if(auth == null || auth.getPrincipal() == "anonymousUser")     //Lay tu cookie
		{
			Cookie cl[] = res.getCookies();		
			Set<Long> idList = new HashSet<Long>();
			for(int i=0; i< cl.length; i++)
			{
				if(cl[i].getName().matches("[0-9]+"))
				{
					idList.add(Long.parseLong(cl[i].getName()));
					quanity.put(Long.parseLong(cl[i].getName()), cl[i].getValue());  
				}
				
			}
			listsp = productService.getAllSanPhamByList(idList);
		}else     //Lay tu database
		{
			GioHang g = cartService.getGioHangByNguoiDung(currentUser);
			if(g != null)
			{
				List<CartIndex> listchimuc = cardIndexService.getChiMucGioHangByGioHang(g);
				for(CartIndex c: listchimuc)
				{
					listsp.add(c.getSanPham());
					quanity.put(c.getSanPham().getId(), Integer.toString(c.getSo_luong()));
				}
			}
		}
		model.addAttribute("checkEmpty",listsp.size());
		model.addAttribute("cart",listsp);
		model.addAttribute("quanity",quanity);
		
		
		return "client/cart";
	}

}
