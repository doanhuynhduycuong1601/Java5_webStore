<%@page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<h1>History</h1>

<div>
	<a class="btn btn-primary" href="/store/manager/history/order">Đơn
		hàng đã đặt</a> <a class="btn btn-primary"
		href="/store/manager/history/order/confirm">Đơn hàng đã xác nhận</a> <a
		class="btn btn-primary" href="/store/manager/history/order/ship">Đơn
		hàng đang giao</a> <a class="btn btn-primary"
		href="/store/manager/history/order/cancel">Đơn hàng bị hủy</a> <a
		class="btn btn-primary" href="/store/manager/history/order/success">Thống
		kê đơn hàng</a> <a class="btn btn-primary"
		href="/store/manager/history/order/tk">Thống kê SL bán</a>
		<a class="btn btn-primary"
		href="/store/manager/history/order/hoan">Yêu cầu hoàn</a>
</div>

<div class="row">
	<form class="col-8 row mb-5" id="select"
		action="/store/manager/history/order/tk">
		<div class="row mb-4">
			<label class="col-3">Theo tháng</label> <select class="col-9"
				onchange="submitForm()" name="selectMonthYear">
				<c:forEach var="list" items="${MonthYear}">
					<option value="${list }" ${list ==  selected? 'selected' : '' }>${list }</option>
				</c:forEach>
			</select>
		</div>
		<div class="row">
			<label class="col-3">Tên sp</label> <input class="col-9"
				name="searchManager" value="${searchManager}">
		</div>
	</form>
	
	<div class="col-3 offset-1">
		<label>Lợi nhuận : ${money}</label>
	</div>
</div>



<c:choose>
	<c:when test="${history.content.size()>0}">
		<div class="row">
			<div class="row mb-3">
				<div class="col-1">Mã SP</div>
				<div class="col-2">Ảnh</div>
				<div class="col-3">Tên SP</div>
				<div class="col-3">Tổng tiền</div>
				<div class="col-2">Tổng SL bán</div>
			</div>
			<c:forEach var="list" items="${history.content}">
				<div class="row mb-3" style="align-items: center;">
					<div class="col-1">${list.id}</div>
					<div class="col-2 row">
						<a class="row" data-bs-toggle="offcanvas" data-bs-target="#detail"
							onclick="detail('${list.id}')"> <img alt="" class="row"
							style="height: 150px" src="/store/img/products/${list.img}">
						</a>
					</div>
					<div class="col-3">${list.names}</div>
					<div class="col-3">${list.getTotalPrice()}</div>
					<div class="col-2" style="text-align: center;">${list.quantity}</div>
					<div class="col-1">
						<button class="btn btn-primary" type="button"
							data-bs-toggle="collapse" data-bs-target="#show${list.id}"
							aria-expanded="false" aria-controls="show${list.id}"
							onclick="detailOrderTK('${list.id}','show${list.id}')"
							>
							Chi</button>
					</div>
				</div>
				<div class="collapse container" id="show${list.id}">asdf</div>
				<hr>
			</c:forEach>

			<div class="col-9 offset-3">
				<c:if test="${history.number>1}">
					<a href="/store/manager/history/order/tk/page?page=1"
						class="btn btn-primary">1</a>
				</c:if>
				<c:if test="${history.number>0}">
					<a
						href="/store/manager/history/order/tk/page?page=${history.number <= 0 ? 1 : history.number}"
						class="btn btn-primary">${history.number <= 0 ? 1 : history.number}</a>
				</c:if>
				<label class="btn btn-secondary">${history.number+1}</label>
				<c:if test="${(history.totalPages - history.number-1) > 0}">
					<a
						href="/store/manager/history/order/tk/page?page=${history.number + 2 >= history.totalPages ? history.totalPages :  history.number + 2}"
						class="btn btn-primary">${history.number + 2 >= history.totalPages ? history.totalPages :  history.number + 2}</a>
				</c:if>
				<c:if test="${(history.totalPages - history.number-1) > 1}">
					<a
						href="/store/manager/history/order/tk/page?page=${history.totalPages}"
						class="btn btn-primary">${history.totalPages}</a>
				</c:if>
			</div>
		</div>
	</c:when>
	<c:otherwise>
		<div class="row">
			<h2 class="col-8">Tháng ${selected == 'All'? 'tất cả' : selected} không có sản phẩm nào được bán ra theo tên ${searchNameSpTK}</h2>
		</div>
		<div class="col-8 offset-2 row">
			<img src="/store/img/default/order.jfif" style="height: 300px">
		</div>
	</c:otherwise>
</c:choose>


<script>
	function cancel(id) {
		let img = document.getElementById("formCancel");
		img.setAttribute('action', '/store/manager/history/cancel/' + id);
	}

	function submitForm() {
		document.getElementById("select").submit();
	}
</script>
