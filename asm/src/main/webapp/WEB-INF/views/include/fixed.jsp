<%@page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt"%>

<div class="below row">
	<nav class="navbar navbar-expand-lg" style="padding-bottom: 0px">
		<div class="collapse navbar-collapse" id="navbarSupportedContent">
			<ul class="navbar-nav mb-2">
				<li style="margin-left: 15px;" class="nav-item">
					<div class="dropdown" style="margin-left: 20px">
						<button class="btn btn-outline-light dropdown-toggle"
							type="button" data-bs-toggle="dropdown" aria-expanded="false"
							style="font-size: 20px">
							<i class="fa-solid fa-list"></i>
						</button>
						<ul class="dropdown-menu">
							<c:forEach var="list" items="${category}">
								<li><a class="dropdown-item"
									href="/store/product/search/by/category/${list.id}">${list.name}</a></li>
							</c:forEach>
						</ul>
					</div>
				</li>

				<li style="margin-left: 35px;" class="nav-item"><a id="home"
					class="nav-link text-light" style="font-size: 20px"
					aria-current="page" href="/store"> <i
						class="fa-solid fa-bag-shopping"></i> Cuongee
				</a></li>


				<li class="nav-item" style="margin-left: 40px">
					<div class="input-group">
						<form class="form-control" method="get">
							<input id="searchByName" onkeyup="searchKeyUp(event)"
								style="width: 270px; border: none; outline: none;" type="text"
								placeholder="Tìm kiếm theo tên sản phẩm" name="search" value="${param.search}">
						</form>
						<span class="input-group-text">
							<button style="border: none; outline: none; outline-color: none"
								onclick="searchVideoByName()">
								<i class="fa-solid fa-magnifying-glass"></i>
							</button>
						</span>
					</div>
				</li>


				<li class="nav-item" style="margin-left: 40px"><a
					href="/store/cart" class="nav-link position-relative"><i
						style="font-size: 25px; color: white;"
						class="fa-sharp fa-solid fa-cart-plus"> </i> <span
						class="position-absolute top-0 start-100 translate-middle badge rounded-pill"
						id="myCart"> <c:if test="${myCart != 0}">${myCart}</c:if>
					</span> </a></li>

				<c:if test="${account!=null && account.admins}">
					<li class="nav-item">
					<div class="dropdown" style="margin-left: 20px">
						<button class="btn btn-outline-light dropdown-toggle" style="border: none;"
							type="button" data-bs-toggle="dropdown" aria-expanded="false">
							Quản lý</button>
						<ul class="dropdown-menu">
							<li><a class="dropdown-item" href="/store/manager/category/view">Loại sản phẩm</a></li>
							<li><a class="dropdown-item"
								href="/store/manager/product/view">Sản phẩm</a></li>
							<li><a class="dropdown-item"
								href="/store/manager/history/order">Lịch sử đặt hàng</a></li>
						</ul>
					</div>
				</li>
				</c:if>



				<c:if test="${account==null}">
					<li class="nav-item">
						<!-- Button trigger modal --> <a style="border: none;margin-left: 50px"
						type="button" href="/store/account/login"
						class="btn btn-outline-light"> <i
							style="margin-right: 8px;border: none;" class="fa-solid fa-user"></i>Đăng nhập
					</a>
					</li>
				</c:if>

				<c:if test="${account!=null}">
					<li class="nav-item">
						<div class="dropdown" style="margin-left: 20px">
							<button class="btn btn-outline-light dropdown-toggle" style="border: none;"
								type="button" data-bs-toggle="dropdown" aria-expanded="false">
								Tài khoản</button>
							<ul class="dropdown-menu">
								<li><a class="dropdown-item"
									href="/store/account/changepass">Thay đổi mật khẩu</a></li>
								<li><a class="dropdown-item"
									href="/store/account/update/infor">Cập nhập thông tin</a></li>
								<li><a class="dropdown-item" href="/store/account/logout">Đăng xuất</a></li>
								<li><a class="dropdown-item" href="/store/history/order">Lịch sử mua hàng</a></li>
							</ul>
						</div>
					</li>
				</c:if>
			</ul>
		</div>
	</nav>
</div>
<script>
	var input = document.getElementById("searchByName");
	var formSearch = input.parentNode
	formSearch.addEventListener("submit", function(event) {
		event.preventDefault(); // Ngăn chặn việc submit form mặc định
	});
	function searchVideoByName() {
		value = input.value
		formSearch.setAttribute('action', '/store/product/search')
		formSearch.submit()
	}

	function searchKeyUp(event) {
		if (event.keyCode === 13) {
			searchVideoByName()
		}
	}
</script>