<%@page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt"%>




<div class="col-3">
	<c:if test="${prices.size()>0}">
		<div>
			<span>Mức giá</span>
		</div>
		<div class="row">
			<c:forEach var="list" items="${prices}">
				<div>
					<label style="cursor: pointer;"> <input type="checkbox"
						name="price" value="${list.value}">${list.label}
					</label>
				</div>
			</c:forEach>
		</div>
	</c:if>
</div>


<div class="col-9">
	<c:choose>
		<c:when test="${searchByCategory.content.size()>0}">
			<div class="row" id="searchByCategory">
				<c:forEach var="list" items="${searchByCategory.content}">
					<div class="col-4 mb-4">
						<a class="row" data-bs-toggle="offcanvas" data-bs-target="#detail"
							onclick="detail('${list.id}')"> <img alt="" class="row"
							style="height: 150px" src="/store/img/products/${list.img}">
						</a>
						<div>${list.names}</div>
						<div>${list.price}</div>
						<div class="d-flex justify-content-end addCart"
							data-id="${list.id}">
							<div class="btn"
								style="display: ${list.quantityCart>0 ? 'block' : 'none'}">
								<button class="btn btn-primary" onclick="updateCart(this,'-')">
									<i class="fa-solid fa-minus"></i>
								</button>
								<label>${list.quantityCart}</label>
								<button class="btn btn-primary" onclick="updateCart(this,'+')">
									<i class="fa-solid fa-plus"></i>
								</button>
							</div>
							<div class="p-2"
								style="display: ${list.quantityCart>0 ? 'none' : 'block'};cursor: pointer;">
								<i onclick="addProductCart(${list.id})"
									class="fa-sharp fa-solid fa-cart-plus"></i>
							</div>
						</div>
					</div>
				</c:forEach>
			</div>

			<div class="row">
				<div class="col-6 offset-4">
					<button id="view" class="btn btn-primary"
						onclick="xem(1,this,'/store/product/search/by/categoryPage','searchByCategory',4)">Xem
						thêm</button>
				</div>
			</div>
		</c:when>
		
		<c:otherwise>
		<div class="row" style="text-align: center;">
			<h2>Không có sản phẩm nào.</h2>
		</div>
		<div class="col-6 offset-3 row">
			<img src="/store/img/default/notfound.jfif" style="height: 300px">
		</div>
	</c:otherwise>
	</c:choose>
</div>


<script>
	var event = document.querySelectorAll('input[type="checkbox"][name="price"]');

	for (var i = 0; i < event.length; i++) {
		event[i].addEventListener('click', select);
	}

	function select() {
		var checkboxes = document.querySelectorAll('input[type="checkbox"][name="price"]');
		var selectedValues = [];
		for (let i = 0; i < checkboxes.length; i++) {
			  let checkbox = checkboxes[i];
			  
			  if (checkbox.checked) {
			    selectedValues.push(checkbox.value);
			  }
		}
		console.log(selectedValues)
		var data = { 'priceBetWeen': selectedValues};
		fetch('/store/product/search/by/categoryValue', {
		    method: 'POST',
		    headers: {
		    	'Content-Type': 'application/json' // Loại dữ liệu gửi đi là JSON
		    },
		    body: JSON.stringify(data) // Chuyển đổi đối tượng JSON thành chuỗi JSON
		})
		.then(function(response) {
		     return response.json();
		})
		.then(function(datas) {
			var result = ""
			for (let data of datas.content) {
				let htmlString = '<div class="col-4 mb-4">' +
				'<a class="row" data-bs-toggle="offcanvas" data-bs-target="#detail" onclick="detail('+data.id+')"> <img alt="" class="row" style="height: 150px"' +
				'src="/store/img/products/' + data.img + '">' +
				'</a>' +
				'<div>' + data.names + '</div>' +
				'<div>' + data.price + '</div>' +
				'<div class="d-flex justify-content-end addCart"' +
				'data-id="' + data.id + '">' +
				'<div class="btn"' +
				'style="display: ' + (data.quantityCart > 0 ? 'block' : 'none') + '">' +
				'<button class="btn btn-primary" onclick="updateCart(this,\'-\')">' +
				'<i class="fa-solid fa-minus"></i>' +
				'</button>' +
				'<label>' + data.quantityCart + '</label>' +
				'<button class="btn btn-primary" onclick="updateCart(this,\'+\')">' +
				'<i class="fa-solid fa-plus"></i>' +
				'</button>' +
				'</div>' +
				'<div class="p-2"' +
				'style="display: ' + (data.quantityCart > 0 ? 'none' : 'block') + '">' +
				'<i onclick="addProductCart(' + data.id + ')"' +
				'class="fa-sharp fa-solid fa-cart-plus"></i>' +
				'</div>' +
				'</div>' +
				'</div>';
		        result += htmlString
			}
			let divElement = document.getElementById('searchByCategory');
		    divElement.innerHTML = result;
		    
		    let btn = document.getElementById('view');
		    btn.style.display = "block"
		    btn.setAttribute('onclick', "xem(1,this,'/store/product/search/by/categoryPage','searchByCategory',4)");
		})
		.catch(function(error) {
		    
		});
	}
</script>
