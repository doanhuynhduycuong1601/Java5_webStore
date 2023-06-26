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

					<div class="col-4">
						<c:if test="${order != null}">
							<a class="btn btn-primary"
								href="/store/history/cancel/${list.orderId}">Hủy</a>
						</c:if>
						
						<button class="btn btn-primary" type="button"
							data-bs-toggle="collapse"
							data-bs-target="#showStatus${list.orderId}" aria-expanded="false"
							aria-controls="showStatus${list.orderId}"
							onclick="status('${list.orderId}','showStatus${list.orderId}','/store/api/product/order/status')">
							Tình trạng</button>
						<button class="btn btn-primary" type="button"
							data-bs-toggle="collapse" data-bs-target="#show${list.orderId}"
							aria-expanded="false" aria-controls="show${list.orderId}"
							onclick="detailOrder('${list.orderId}','show${list.orderId}')">
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

