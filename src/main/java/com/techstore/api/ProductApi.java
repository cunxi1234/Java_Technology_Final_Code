package com.techstore.api;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.techstore.dto.ProductDTO;
import com.techstore.dto.SearchSanPhamObject;
import com.techstore.model.ResponseObject;
import com.techstore.model.SanPham;
import com.techstore.service.ProductService;
import com.techstore.validator.ProductDTOValidator;

@RestController
@RequestMapping("api/san-pham")
public class ProductApi {

	@Autowired
	private ProductDTOValidator validator;

	@Autowired
	private ProductService productService;

	@InitBinder("sanPhamDto")
	protected void initialiseBinder(WebDataBinder binder) {
		binder.setValidator(validator);
	}

	// lấy tất cả san phẩm theo tiêu chí, mặc địch lấy tất cả 
	@GetMapping("/all")
	public Page<SanPham> getAllSanPhamByFilter(@RequestParam(defaultValue = "1") int page, 
			 @RequestParam String danhMucId, @RequestParam String hangSXId, @RequestParam String donGia, @RequestParam String sapXepTheoGia) {
		SearchSanPhamObject searchObject = new SearchSanPhamObject();
		searchObject.setDanhMucId(danhMucId);
		searchObject.setHangSXId(hangSXId);
		searchObject.setDonGia(donGia);
		searchObject.setSapXepTheoGia(sapXepTheoGia);
		
		Page<SanPham> listSanPham = productService.getAllSanPhamByFilter(searchObject, page-1, 10);
		return listSanPham;
	}
	
	@GetMapping("/latest")
	public List<SanPham> getLatestSanPham(){
		return productService.getLatestSanPham();
	}

	// lấy sản phẩm theo id
	@GetMapping("/{id}")
	public SanPham getSanPhamById(@PathVariable long id) {
		return productService.getSanPhamById(id);
	}
	
	
	// lấy sản phẩm theo tên
	@GetMapping("/")
	public Page<SanPham> getSanPhamById(@RequestParam String tenSanPham, @RequestParam(defaultValue = "1") int page) {
		return productService.getSanPhamByTenSanPhamForAdmin(tenSanPham, page-1, 10 );
	}

	// lưu sản phẩm vào db
	@PostMapping(value = "/save")
	public ResponseObject addSanPham(@ModelAttribute @Valid ProductDTO newProductDTO, BindingResult result,
									 HttpServletRequest request) {

		ResponseObject ro = new ResponseObject();

		// nếu có lỗi xảy ra ( validate)
		if (result.hasErrors()) {
			Map<String, String> errors = result.getFieldErrors().stream()
					.collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
			errors.forEach((k, v) -> System.out.println(" test: Key : " + k + " Value : " + v));
			ro.setErrorMessages(errors);
			ro.setStatus("fail");
			errors = null;
		} else {
			// lưu sản phẩm
			SanPham sp = productService.save(newProductDTO);
			ro.setData(sp);
			saveImageForProduct(sp, newProductDTO, request);
			ro.setStatus("success");
		}
		return ro;
	}
	
	
	@DeleteMapping("/delete/{id}")
	public String deleteSanPham(@PathVariable long id) {
		productService.deleteById(id);
		return "OK !";
	}

	
	// lưu ảnh của sản phẩm vào thư mục
	public void saveImageForProduct(SanPham sp, ProductDTO dto, HttpServletRequest request) {

		MultipartFile productImage = dto.getHinhAnh();
		String rootDirectory = request.getSession().getServletContext().getRealPath("/");
		Path path = Paths.get(rootDirectory + "/resources/images/" + sp.getId() + ".png");
		System.out.println(productImage != null && !productImage.isEmpty());
		if (productImage != null && !productImage.isEmpty()) {

			try {
				productImage.transferTo(new File(path.toString()));
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new RuntimeException("Product image saving failed", ex);
			}
		}
	}
}
