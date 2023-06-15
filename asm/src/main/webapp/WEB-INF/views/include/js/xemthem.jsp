<%@page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt"%>


<script>
	function xem(value,input,url,id,col) {
		var params = new URLSearchParams();
		params.append('page', value);
		fetch(url, {
		    method: 'POST',
		    headers: {
		        'Content-Type': 'application/x-www-form-urlencoded'
		    },
		    body: params.toString() // Chuyển đổi đối tượng URLSearchParams thành chuỗi URL-encoded
		})
		.then(function(response) {
		    return response.json();
		})
		.then(function(datas) {
			var result = ""
			for (let data of datas.content) {
				let htmlString = '<div class="col-'+col+' mb-4">' +
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
				'style="display: ' + (data.quantityCart > 0 ? 'none' : 'block') + ';cursor: pointer;">' +
				'<i onclick="addProductCart(' + data.id + ')"' +
				'class="fa-sharp fa-solid fa-cart-plus"></i>' +
				'</div>' +
				'</div>' +
				'</div>';
		        result += htmlString
			}
			var divElement = document.getElementById(id);
		    divElement.insertAdjacentHTML('beforeend', result);
		    let num = parseInt(value) + 1
		    if(num >= datas.totalPages){
		    	input.style.display = 'none'
		    }
			
			console.log(num)
			input.setAttribute('onclick', "xem("+num+",this,'"+url+"','"+id+"','"+col+"')");
		})
		.catch(function(error) {
		    
		});
	}
</script>