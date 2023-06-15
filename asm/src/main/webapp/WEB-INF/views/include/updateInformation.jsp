<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page pageEncoding="utf-8"%>
<h2>${msg}</h2>
<div class="col-12">
	<h1>Cập nhập thông tin</h1>
	<h2>${param.error}</h2>
	<form:form class="row" modelAttribute="accounts"
		action="/store/account/update/infor">
		<div class="row mb-4">
			<div class="col-6 row">
				<div class="row">
					<label class="col-3">Email</label>
					<form:input readonly="true" class="col-9" path="email" />
				</div>
				<form:errors class="row" style="direction: rtl;color: red;"
					path="email" />
			</div>
			<div class="col-6 row">
				<div class="row">
					<label class="col-3">Điện thoại</label>
					<form:input class="col-9" path="phone" />
				</div>
				<form:errors class="row" style="direction: rtl;color: red;"
					path="phone" />
			</div>
		</div>

		<div class="row mb-4">
			<div class="col-6 row">
				<div class="row">
					<label class="col-3">Họ tên</label>
					<form:input class="col-9" path="name" />
				</div>
				<form:errors class="row" style="direction: rtl;color: red;"
					path="name" />
			</div>
			<div class="col-6 row">
				<div class="row">
					<label class="col-3">Tuổi:</label>
					<form:input class="col-9" path="age" />
				</div>
				<form:errors class="row" style="direction: rtl;color: red;"
					path="age" />
			</div>
		</div>

		<div class="row mb-4">
			<div class="col-6 row">
				<div class="row">
					<label class="col-3">Giới tính:</label>
					<div class="col-9">
						<form:radiobuttons path="gender" items="${gender}" />
					</div>
				</div>
			</div>
		
			<div class="col-6 row">
				<div class="row">
					<label class="col-3">Địa chỉ</label>
					<form:textarea class="col-9" path="address" />
				</div>
				<form:errors class="row" style="direction: rtl;color: red;"
					path="address" />
			</div>
		</div>

		<div class="row">
			<div class="col-4 offset-3">
				<button class="btn btn-primary">Cập nhập</button>
			</div>
		</div>
	</form:form>
</div>