package com.techstore;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.techstore.repository.OrderRepository;
import com.techstore.repository.UserRepository;
import com.techstore.service.CategoryService;
import com.techstore.service.UserService;
import com.techstore.service.ProductService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TechStoreApplicationTests {

	@Autowired
	private CategoryService dmService;

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository ndRepo;

	@Autowired
	private OrderRepository dhRepo;
	
	@Autowired
	private ProductService spService;


//	@Test
//	public void getALlDanhMucTest() {
//		System.out.println(dhRepo.test().size());
//	}

	@Test
	public void getALlDanhMucTest() {
		System.out.println(spService.getSanPhamByTenSanPhamForAdmin("asus",0,10).getContent().size());
	}

}
