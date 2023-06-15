<%@page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt"%>

<script>
  function detailOrderTK(id,div) {
	  var params = new URLSearchParams();
	  params.append('idProduct', id);
	  fetch('/store/manager/history/order/tk/detail', {
		    method: 'POST', // Phương thức yêu cầu
		    headers: {
		    	'Content-Type': 'application/x-www-form-urlencoded'
		    },
		    body: params.toString() // Chuyển đổi đối tượng URLSearchParams thành chuỗi URL-encoded
		})
		.then(function(response) {
              return response.json(); // Nhận chuỗi phản hồi nếu trạng thái là "OK"
		})
		.then(function(dataResp) {
			console.log(dataResp)
			var result = ""
			
			result += '<table class="row">'+
						'<tr class="row mb-2">'+
						'<th class="col-3">Email</th>'+
						'<th class="col-2">Điện thoại</th>'+
						'<th class="col-2">Địa chỉ</th>'+
						'<th class="col-2">Ngày đặt</th>'+
						'<th class="col-1">SL mua</th>'+
						'<th class="col-2">Giá sp</th>'+
						'</tr>';
			
				for (let data of dataResp) {
					let htmlString = '<tr class="row mb-2">'+
							'<td class="col-3">'+data.order.email+'</td>'+
							'<td class="col-2">'+data.order.phone+'</td>'+
							'<td class="col-2">'+data.order.address+'</td>'+
							'<td class="col-2">'+data.order.dateAt+'</td>'+
							'<td class="col-1">'+data.quantity+'</td>'+
							'<td class="col-2">'+data.price+'</td>'+
						'</tr>'
			        result += htmlString
				}
				result += '</table>';
				
			let parentDiv = document.getElementById(div);
			parentDiv.innerHTML = result
			
          })
		.catch(function(error) {
		});
    

    }
  
  
  
</script>
