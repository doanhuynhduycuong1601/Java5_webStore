<%@page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt"%>


<c:if test="${msg!=null}">
	<h2>${msg}</h2>
</c:if>
<c:choose>
	<c:when test="${cart.size()>0}">
		<table class="row">
			<tr class="row mb-4">
				<th class="col-2">Ảnh</th>
				<th class="col-3">Tên</th>
				<th class="col-2">Số lượng mua</th>
				<th class="col-2">Giá tiền</th>
				<th class="col-2" style="text-align: center;">Số lượng tồn</th>
				<th class="col-1">Xóa</th>
			</tr>

			<c:forEach var="list" items="${cart}">
				<tr class="row mb-4" style="align-items: center;">
					<th class="col-2"><a class="row" data-bs-toggle="offcanvas"
						data-bs-target="#detail" onclick="detail('${list.id}')"> <img
							alt="" class="row" style="height: 150px"
							src="/store/img/products/${list.img}">
					</a></th>
					<th class="col-3">${list.names}</th>
					<th class="col-2">
						<form class="row" action="/store/cart/update/${list.id}"
							method="post">
							<input name="qty" value="${list.quantityCart}" type="number"
								onblur="this.form.submit()" class="row" min="0" max="5">
						</form>
					</th>
					<th class="col-2">${list.priceFormat()}</th>
					<th class="col-2" style="text-align: center;">${list.quantity}</th>
					<th class="col-1"><a href="/store/cart/remove/${list.id}"
						style="text-decoration: none;" class="btn btn-primary">Xóa</a></th>
				</tr>
			</c:forEach>
		</table>

		<div class="d-flex">
			<div class="p-2 flex-grow-1">
				<div>Tổng số lượng mua : ${count}</div>
				<div>Tổng hóa đơn : ${amount}</div>
			</div>
			<div class="p-2">
				<a href="/store/cart/buy" class="btn btn-primary">Mua</a> <a
					href="/store/cart/clear" class="btn btn-primary">làm sạch giỏ
					hàng</a>
			</div>
		</div>
	</c:when>
	<c:otherwise>
		<div class="row" style="text-align: center;">
			<h2>Không có sản phẩm nào trong giỏ hàng</h2>
		</div>
		<div class="row">
			<img src="/store/img/default/cart.png" style="height: 400px">
		</div>
	</c:otherwise>
</c:choose>
