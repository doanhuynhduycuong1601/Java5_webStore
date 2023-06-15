<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page pageEncoding="utf-8"%>
<div class="col-6 offset-3">
	<h1>Đăng nhập</h1>
	<div class="row mb-4">${param.error}</div>
	<form:form class="row" modelAttribute="accounts"
		action="/store/account/login">
		<div class="row mb-4">
			<div class="row">
				<label class="col-3">Email:</label>
				<form:input class="col-9" path="email" />
			</div>
			<form:errors class="row" style="direction: rtl;color: red;"
				path="email" />
		</div>
		<div class="row mb-4">
			<div class="row">
				<label class="col-3">Mật khẩu:</label>
				<form:password class="col-9" path="passwords" />
			</div>
			<form:errors class="row" style="direction: rtl;color: red;"
				path="passwords" />
		</div>

		<div class="row mb-4">
			<div class="row">
				<div class="col-9 offset-3">
					<label style="cursor: pointer;"><input type="checkbox"
						name="remember" ${remember}>Nhớ?</label>
				</div>
			</div>
		</div>

		<div class="row">
			<button class="btn btn-primary col-3 offset-3">Đăng nhập</button>
			<a class="btn btn-primary col-3 offset-1"
				href="/store/account/register">Đăng ký</a>
		</div>
	</form:form>
</div>