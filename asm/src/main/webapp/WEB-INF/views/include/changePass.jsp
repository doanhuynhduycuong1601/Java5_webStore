<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page pageEncoding="utf-8"%>
<div class="col-6 offset-3">
	<h1>Thay đổi mật khẩu</h1>
	<form:form class="row" modelAttribute="accounts"
		action="/store/account/changepass">
		<div class="row mb-4">
			<div class="row">
				<label class="col-5">Mật khẩu hiện tại:</label>
				<form:password class="col-7" path="passCurrent" />
			</div>
			<div class="row">
				<form:errors class="row" style="direction: rtl;color: red;" path="passCurrent" />
			</div>
		</div>
		<div class="row mb-4">
			<div class="row">
				<label class="col-5">Mật khẩu mới:</label>
				<form:password class="col-7" path="passNew" />
			</div>
			<div class="row">
				<form:errors class="row" style="direction: rtl;color: red;" path="passNew" />
			</div>
		</div>

		<div class="row mb-4">
			<div class="row">
				<label class="col-5">Xác nhận mật khẩu:</label>
				<form:password class="col-7" path="passConfirm" />
			</div>
			<div class="row">
				<form:errors class="row" style="direction: rtl;color: red;" path="passConfirm" />
			</div>
		</div>

		<div class="row">
			<button class="btn btn-primary col-4 offset-3">Thay đổi</button>
		</div>
	</form:form>
</div>