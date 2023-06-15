<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page pageEncoding="utf-8"%>
<div class="row">
	<h1>Đăng ký</h1>
	<div class="col-6 offset-3">
		<form:form class="row" modelAttribute="accounts"
			action="/store/account/register">
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

			<div class="row mb-4" ${code == null ? 'hidden="true"' : ''}>
				<div class="row">
					<label class="col-3">Code:</label> <input class="col-9" name="code" />
				</div>
				<div class="row" style="direction: rtl; color: red;">${errorCode}</div>
			</div>

			<div class="row">
				<button class="btn btn-primary col-3 offset-3">Đăng ký</button>
				<a class="btn btn-primary col-3 offset-1"
					href="/store/account/login">Đăng nhập</a>
			</div>
		</form:form>
	</div>
</div>