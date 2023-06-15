<%@page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt"%>

<h1>History</h1>
<div>
	<a class="btn btn-primary" href="/store/history/order">Đơn hàng đã
		đặt</a> <a class="btn btn-primary" href="/store/history/order/cancel">Đơn
		hàng bị hủy</a> <a class="btn btn-primary"
		href="/store/history/order/confirm">Đơn hàng đã xác nhận</a> <a
		class="btn btn-primary" href="/store/history/order/ship">Đơn hàng
		đang giao</a> <a class="btn btn-primary"
		href="/store/history/order/success">Đơn hàng hoàn thành</a>
</div>

<c:choose>
	<c:when test="${history.size()>0}">

		<div class="row">
			<div class="col-8 row mb-3">
				<label class="col-2">Mã</label> <label class="col-5">Ngày
					đặt</label> <label class="col-5">Tổng tiền</label>
			</div>
			<c:forEach var="list" items="${history}">
				<div class="row mb-4" id="order${list.orderId}">
					<div class="col-8 row">
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
					</div>
					<div class="col-2">
						<button class="btn btn-primary" type="button"
							data-bs-toggle="collapse" data-bs-target="#show${list.orderId}"
							aria-expanded="false" aria-controls="show${list.orderId}"
							onclick="detailOrderSuccess('${list.orderId}','show${list.orderId}')">
							Chi tiết</button>
					</div>
					<div class="collapse row" id="showStatus${list.orderId}"></div>
					<div class="collapse row" id="show${list.orderId}"></div>
				</div>
				<hr>
			</c:forEach>
		</div>
	</c:when>
	<c:otherwise>
		<div class="row">
			<c:if test="${order != null}">
				<h2 class="col-8">Bạn chưa đặt đơn hàng nào.</h2>
				<div class="col-4">
					<a class="btn btn-primary" href="/store/">Mua ngay?</a>
				</div>
			</c:if>
			<c:if test="${orderCancel != null}">
				<h2>Bạn không có đơn hàng nào bị hủy cả</h2>
			</c:if>

			<c:if test="${orderCancel == null && order == null}">
				<h2>Bạn chưa có đơn hàng nào hoàn thành cả</h2>
			</c:if>
		</div>
		<div class="col-8 offset-2 row">
			<img src="/store/img/default/order.jfif" style="height: 300px">
		</div>
	</c:otherwise>
</c:choose>
<!-- Modal -->
<div class="modal fade" id="hoan" data-bs-backdrop="static"
	data-bs-keyboard="false" tabindex="-1"
	aria-labelledby="staticBackdropLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h1 class="modal-title fs-5" id="staticBackdropLabel">Lý do
					hoàn đơn hàng</h1>
				<button type="button" class="btn-close" data-bs-dismiss="modal"
					aria-label="Close"></button>
			</div>
			<div class="modal-body">
				<form id="formCancel" action="/store/manager/history/cancel/"
					class="row">
					<div class="row mb-3">
						<label class="col-3">Lý do</label>
						<textarea name="reason" rows="6" class="col-9"></textarea>
					</div>
					<div class="d-flex flex-row-reverse">
						<button class="p-2 btn btn-primary">Gửi</button>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>
<script>
	function requestHoan(id) {
		let img = document.getElementById("formCancel");
		img.setAttribute('action', '/store/history/hoan/' + id);
	}
</script>

