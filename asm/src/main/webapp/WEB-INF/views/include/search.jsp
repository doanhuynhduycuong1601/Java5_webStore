<%@page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt"%>

<c:choose>
	<c:when test="${searchProducts.content.size()>0}">
		<h1>Search ${param.search}</h1>
		<div class="row" id="pageSearch">
			<c:forEach var="list" items="${searchProducts.content}">
				<div class="col-3 mb-4">
					<a class="row" data-bs-toggle="offcanvas" data-bs-target="#detail"
						onclick="detail('${list.id}')"> <img alt="" class="row"
						style="height: 150px" src="/store/img/products/${list.img}">
					</a>
					<div>${list.names}</div>
					<div>${list.price}</div>
					<div class="d-flex justify-content-end addCart"
						data-id="${list.id}">
						<div class="btn"
							style="display: ${list.quantityCart>0 ? 'block' : 'none'}">
							<button class="btn btn-primary" onclick="updateCart(this,'-')">
								<i class="fa-solid fa-minus"></i>
							</button>
							<label>${list.quantityCart}</label>
							<button class="btn btn-primary" onclick="updateCart(this,'+')">
								<i class="fa-solid fa-plus"></i>
							</button>
						</div>
						<div class="p-2"
							style="display: ${list.quantityCart>0 ? 'none' : 'block'};cursor: pointer;">
							<i onclick="addProductCart(${list.id})"
								class="fa-sharp fa-solid fa-cart-plus" cursor:pointer;></i>
						</div>
					</div>
				</div>
			</c:forEach>
		</div>

		<div class="row">
			<div class="col-6 offset-4">
				<c:if test="${searchProducts.totalPages > 1 }">
					<button class="btn btn-primary"
						onclick="xem(1,this,'/store/api/product/search','pageSearch',3)">Xem
						thêm</button>
				</c:if>
			</div>
		</div>


	</c:when>

	<c:otherwise>
		<div class="row" style="text-align: center;">
			<h2>Không tìm thấy sản phẩm</h2>
		</div>
		<div class="col-6 offset-3 row">
			<img src="/store/img/default/notfound.jfif" style="height: 300px">
		</div>
	</c:otherwise>
</c:choose>

