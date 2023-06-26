<%@page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt"%>

<script>
function detail(id) {
	var params = new URLSearchParams();
	params.append('id', id);
	fetch('/store/api/product/detail', {
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
		document.getElementById("detailName").innerHTML = "Tên : "+datas.names;
		document.getElementById("detailPrice").innerHTML = "Giá : "+datas.price;
		document.getElementById("detailQuantity").innerHTML = "Số lượng : "+datas.quantity;
		document.getElementById("detailCategory").innerHTML = "Loại sản phẩm : "+datas.nameCategory;
		document.getElementById("detailStatus").innerHTML = "Tình trạng : "+datas.status;
		let img = document.getElementById("detailImg");
		img.setAttribute('src', '/store/img/products/'+datas.img);
	})
	.catch(function(error) {
	    
	});
}
  
</script>