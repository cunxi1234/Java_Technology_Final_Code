<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
<link rel="stylesheet" href="Frontend/css/detailsp.css">
</head>
	<script type="text/javascript">	  
	$(document).ready(function() { 
	  	var priceConvert = accounting.formatMoney(${sp.getDonGia()})+' VND';
		document.getElementById("priceConvert").innerHTML= priceConvert;
		  
	  });


	</script>
<body>
	<div class="container">

			<!-- Setup outer frame container -->
			<div class="container-card">
				<!-- Align product card in the center of the frame -->
				<div class="row align-items-center justify-content-center">
					<!-- Product card starts -->
					<div class="col card product-card" style="width: 100%;" >
						<!-- Row 2 -->
						<h1 class="product-card-title" style="font-size: 20px">
							${sp.getTenSanPham()}
						</h1>
						<!-- Row 4 -->
						<div class="container-card">
							<div class="row">

								<!-- Col 4.1 -->
								<div class="col-sm-6">
									<div>
										<!-- Product card detail image row-->
										<div class="container-card product-card-carousel">
											<div id="carouselIndicators" class="carousel slide vertical" >

												<div class="carousel-inner">
													<div class="carousel-item active">
														<div class="tab-pane active" id="pic-1"><img src="/img/${sp.getId()}.png" /></div>
													</div>
												</div>
											</div>
										</div>

									</div>
								</div>

								<!--Col 4.2-->
								<div class="col-sm-6" style="margin-top: 40px">
									<h4 class="price" id ="blabla" style="font-size: 24px">Giá bán: <span id="priceConvert"></span></h4>

									<div class="action">
										<button class="add-to-cart btn btn-warning" type="button"><span class="glyphicon glyphicon-shopping-cart pull-center"></span> Giỏ hàng</button>
									</div>


									<!--Product card description row-->
									<div id="product-detail-container">
										<ul class="nav nav-tabs" id="myTab" role="tablist">
											<li class="nav-item" role="presentation">
												<a class="nav-link product-detail-selection-nav " id="description-tab" data-toggle="tab" href="#description" role="tab" aria-controls="description" aria-selected="true">DESCRIPTION</a>
											</li>
											<li class="nav-item" role="presentation">
												<a class="nav-link product-detail-selection-nav" id="detail-tab" data-toggle="tab" href="#detail" role="tab" aria-controls="detail" aria-selected="false">DETAILS</a>
											</li>
										</ul>
										<div class="tab-content" id="myTabContent">
											<div class="tab-pane fade in active product-detail-content" id="description"  role="tabpanel" aria-labelledby="description-tab">
												<div class="product-description" style="font-size: 16px">
													<p ><span class="important">Hãng sản xuất: </span>${sp.hangSanXuat.tenHangSanXuat}</p>
													<p ><span class="important">THÔNG TIN CHUNG: </span> ${sp.getThongTinChung()}</p>
													<p ><span class="important">BẢO HÀNH: </span> ${sp.getThongTinBaoHanh()}</p>
												</div>
											</div>
											<div class="tab-pane fade product-detail-content" id="detail" role="tabpanel" aria-labelledby="detail-tab">
												<div class="product-description" style="font-size: 16px;">
													<p style="display:none" id="spid">${sp.getId()}</p>
													<h4 class="important" style="font-size: 20px; text-align: center; margin-top: 10px" >Mô tả sản phẩm</h4>
													<c:if test = "${sp.getCpu().length() > 0}">
														<p ><span class="important">CPU:  </span> ${sp.getCpu()}</p>
													</c:if>
													<c:if test = "${sp.getRam().length() > 0}">
														<p ><span class="important">RAM:  </span> ${sp.getRam()}</p>
													</c:if>
													<c:if test = "${sp.getThietKe().length() > 0}">
														<p ><span class="important">Thiết kế:  </span> ${sp.getThietKe()}</p>
													</c:if>
													<c:if test = "${sp.getHeDieuHanh().length() > 0}">
														<p ><span class="important">Hệ điều hành:  </span> ${sp.getHeDieuHanh()}</p>
													</c:if>
													<c:if test = "${sp.getManHinh().length() > 0}">
														<p ><span class="important">Màn hình:  </span> ${sp.getManHinh()}</p>
													</c:if>
													<c:if test = "${sp.getDungLuongPin().length() > 0}">
														<p ><span class="important">Dung lượng:  </span> ${sp.getDungLuongPin()}</p>
													</c:if>
												</div>
											</div>

										</div>
									</div>
									<!--Product card share row-->
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>

	</div>
</body>

<script src="<c:url value='/js/client/detailspAjax.js'/>" ></script>	 