<%@page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt"%>

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
	<form class="col-8 row" id="select"
		action="/store/manager/history/order/success">
		<label class="col-3">Theo tháng</label> <select class="col-9"
			onchange="submitForm()" name="selectMonthYear">
			<c:forEach var="list" items="${MonthYear}">
				<option value="${list }" ${list ==  selected? 'selected' : '' }>${list }</option>
			</c:forEach>
		</select>
	</form>
	<div class="col-2 offset-1">
		<label class="col-10">Số lượng đơn : ${history.totalElements}</label>
	</div>
</div>
<c:choose>
	<c:when test="${history.content.size()>0}">
		<div class="row">
			<div class="col-7 row mb-3">
				<label class="col-2">Mã</label> <label class="col-5">Ngày
					đặt</label> <label class="col-5">Tổng tiền</label>
			</div>
			<c:forEach var="list" items="${history.content}">
				<div class="row mb-4" id="order${list.orderId}">
					<div class="col-7 row">
						<label class="col-2">${list.orderId}</label> <label class="col-5">${list.dateAt}</label>
						<label class="col-5">${list.totalAmount}</label> <label
							class="row mb-2">Email : ${list.email}</label> <label
							class="row mb-2">Điện thoại : ${list.phone}</label> <label
							class="row mb-1">Địa chỉ : ${list.address}</label>
					</div>
					<div class="col-2">
						<button class="btn btn-primary" type="button"
							data-bs-toggle="collapse"
							data-bs-target="#showStatus${list.orderId}" aria-expanded="false"
							aria-controls="showStatus${list.orderId}"
							onclick="status('${list.orderId}','showStatus${list.orderId}','/store/history/order/status')">
							Tình trạng</button>
							<button class="btn btn-primary" type="button"
							data-bs-toggle="collapse" data-bs-target="#show${list.orderId}"
							aria-expanded="false" aria-controls="show${list.orderId}"
							onclick="detailOrderSuccessHoan('${list.orderId}','show${list.orderId}')">
							<i class="fa-sharp fa-solid fa-down-long"></i>
						</button>
					</div>

					<div class="collapse row" id="showStatus${list.orderId}"></div>
					<div class="collapse row" id="show${list.orderId}"></div>
				</div>
				<hr>
			</c:forEach>
			
			<div class="col-9 offset-3">
				<c:if test="${history.number>1}">
					<a href="/store/manager/history/order/success/page?page=1"
						class="btn btn-primary">1</a>
				</c:if>
				<c:if test="${history.number>0}">
					<a
						href="/store/manager/history/order/success/page?page=${history.number <= 0 ? 1 : history.number}"
						class="btn btn-primary">${history.number <= 0 ? 1 : history.number}</a>
				</c:if>
				<label class="btn btn-secondary">${history.number+1}</label>
				<c:if test="${(history.totalPages - history.number-1) > 0}">
					<a
						href="/store/manager/history/order/success/page?page=${history.number + 2 >= history.totalPages ? history.totalPages :  history.number + 2}"
						class="btn btn-primary">${history.number + 2 >= history.totalPages ? history.totalPages :  history.number + 2}</a>
				</c:if>
				<c:if test="${(history.totalPages - history.number-1) > 1}">
					<a
						href="/store/manager/history/order/success/page?page=${history.totalPages}"
						class="btn btn-primary">${history.totalPages}</a>
				</c:if>
			</div>
		</div>
	</c:when>
	<c:otherwise>
		<div class="row">
			<h2 class="col-8">Không có đơn hàng nào bị hủy</h2>
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
