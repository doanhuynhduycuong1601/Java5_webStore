<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page pageEncoding="utf-8"%>
<div class="container">
	<div class="col-10 offset-1 row">
		<div class="row mb-4">
			<div class="mb-4 d-flex flex-row-reverse">
				<a href="/store/manager/product/page/create" class="btn btn-primary">Tạo</a>
			</div>
			
			<form class="row" action="/store/manager/product/search">
				<label class="col-2">
					Tìm kiếm
				</label>
				<input placeholder="Tìm kiếm theo tên sản phẩm" class="col-7" name="searchManagerProduct" value="${searchManagerProduct}">
				<button class="col-1"><i class="fa-solid fa-magnifying-glass"></i></button>
			</form>
		</div>

		<c:forEach var="list" items="${managerProduct.content}">
			<div class="row mb-5">
				<div class="col-3 row">
					<img alt="" style="height: 100px"
						src="/store/img/products/${list.img}">
				</div>
				<div class="col-7 row">
					<div class="col-6">
						<h6 class="mb-2">ID : ${list.id}</h6>
						<h6 class="mb-2">Name : ${list.names}</h6>
						<h6>Loại hàng : ${list.nameCategory}</h6>
					</div>
					<div class="col-6">
						<h6 class="mb-3">Price : ${list.price}</h6>
						<h6>Quantity ${list.quantity}</h6>
					</div>
				</div>
				<div class="col-2">
					<a href="/store/manager/product/edit/${list.id}" class="btn btn-primary">Chỉnh sửa</a>
				</div>
			</div>
		</c:forEach>
		
		<div class="col-9 offset-3">
	<c:if test="${managerProduct.number>1}">
		<a href="/store/manager/product/search/page?page=1" class="btn btn-primary">1</a>
	</c:if>
	<c:if test="${managerProduct.number>0}">
		<a href="/store/manager/product/search/page?page=${managerProduct.number <= 0 ? 1 : managerProduct.number}" class="btn btn-primary">${managerProduct.number <= 0 ? 1 : managerProduct.number}</a>
	</c:if>
	<label class="btn btn-secondary">${managerProduct.number+1}</label>
	<c:if test="${(managerProduct.totalPages - managerProduct.number-1) > 0}">
		<a href="/store/manager/product/search/page?page=${managerProduct.number + 2 >= managerProduct.totalPages ? managerProduct.totalPages :  managerProduct.number + 2}" class="btn btn-primary">${managerProduct.number + 2 >= managerProduct.totalPages ? managerProduct.totalPages :  managerProduct.number + 2}</a>
	</c:if>
	<c:if test="${(managerProduct.totalPages - managerProduct.number-1) > 1}">
		<a href="/store/manager/product/search/page?page=${managerProduct.totalPages}" class="btn btn-primary">${managerProduct.totalPages}</a>
	</c:if>
</div>
	</div>
</div>