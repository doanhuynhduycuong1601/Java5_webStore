<%@page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt"%>

<script>
  function detailOrder(id,div) {
	  var params = new URLSearchParams();
	  params.append('id', id);
	  fetch('/store/api/product/order/detail', {
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
			var result = ""
			
			result += '<div class="row mb-5">' +
			  '<div class="col-3 row">Ảnh</div>' +
			  '<div class="col-3">Tên sản phẩm</div>' +
			  '<div class="col-3">Giá</div>' +
			  '<div class="col-3">Số lượng mua</div>'+
			  '</div>';
			
				for (let data of dataResp) {
					let htmlString = '<div class="row mb-5" style="align-items: center;">' +
					  '<div class="col-3 row">' +
					  '<a class="row" data-bs-toggle="offcanvas" data-bs-target="#detail" '+
					  'onclick="detail('+data.idProduct+')"> <img alt="" class="row" '+
					  'style="height: 150px" src="/store/img/products/'+data.img+'"></a> ' +
					  '</div>' +
					  '<div class="col-3">' + data.name + '</div>' +
					  '<div class="col-3">' + data.price + '</div>' +
					  '<div class="col-2">' + data.quantity + '</div>' +
					  '<div class="col-1">'+(data.itemReturn == 0 ? 'Bị hủy': '')+'</div>'+
					  '</div>';
			        result += htmlString
				}
				
			let parentDiv = document.getElementById(div);
			parentDiv.innerHTML = result
			
          })
		.catch(function(error) {
		});
    

    }
  
  
  function detailOrderSuccess(id,div) {
	  var params = new URLSearchParams();
	  params.append('id', id);
	  fetch('/store/api/product/order/detail', {
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
			var result = ""
			
			result += '<div class="row mb-5">' +
			  '<div class="col-3 row">Ảnh</div>' +
			  '<div class="col-3">Tên sản phẩm</div>' +
			  '<div class="col-2">Giá</div>' +
			  '<div class="col-2">Số lượng mua</div>'+
			  '<div class="col-1"></div>'+
			  '<div class="col-1">Hoàn</div>'+
			  '</div>';
			
				for (let data of dataResp) {
					console.log(data.hoan)
					let text= ""
					if(data.itemReturn == 1 && data.hoan){
						text = '<div class="col-1"><button class="btn btn-primary" onclick="requestHoan('+data.id+')" data-bs-toggle="modal" data-bs-target="#hoan">Hoàn</button></div>';
					}else if(data.itemReturn == 2){
						text = '<div class="col-1">Chờ xl</div>'
					}else if(data.itemReturn == 3){
						text = '<div class="col-1">Đã hoàn</div>'
					}
					
					let htmlString = '<div class="row mb-5" style="align-items: center;">' +
					  '<div class="col-3 row">' +
					  '<a class="row" data-bs-toggle="offcanvas" data-bs-target="#detail" '+
					  'onclick="detail('+data.idProduct+')"> <img alt="" class="row" '+
					  'style="height: 150px" src="/store/img/products/'+data.img+'"></a> ' +
					  '</div>' +
					  '<div class="col-3">' + data.name + '</div>' +
					  '<div class="col-2">' + data.price + '</div>' +
					  '<div class="col-2">' + data.quantity + '</div>' +
					  '<div class="col-1">'+(data.itemReturn == 0 ? 'Bị hủy': '')+'</div>'+
					  	text
					  +
					  '</div>';
			        result += htmlString
				}
				
			let parentDiv = document.getElementById(div);
			parentDiv.innerHTML = result
			
          })
		.catch(function(error) {
		});
    

    }
  
  function detailOrderSuccessHoan(id,div) {
	  var params = new URLSearchParams();
	  params.append('id', id);
	  fetch('/store/api/product/order/detail', {
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
			var result = ""
			
			result += '<div class="row mb-5">' +
			  '<div class="col-3 row">Ảnh</div>' +
			  '<div class="col-3">Tên sản phẩm</div>' +
			  '<div class="col-2">Giá</div>' +
			  '<div class="col-2">Số lượng mua</div>'+
			  '<div class="col-1"></div>'+
			  '<div class="col-1">Hoàn</div>'+
			  '</div>';
			
				for (let data of dataResp) {
					console.log(data.hoan)
					let text= ""
					if(data.itemReturn == 2){
						text = '<div class="col-1">Chờ xl</div>'
					}else if(data.itemReturn == 3){
						text = '<div class="col-1">Đã hoàn</div>'
					}
					
					let htmlString = '<div class="row mb-5" style="align-items: center;">' +
					  '<div class="col-3 row">' +
					  '<a class="row" data-bs-toggle="offcanvas" data-bs-target="#detail" '+
					  'onclick="detail('+data.idProduct+')"> <img alt="" class="row" '+
					  'style="height: 150px" src="/store/img/products/'+data.img+'"></a> ' +
					  '</div>' +
					  '<div class="col-3">' + data.name + '</div>' +
					  '<div class="col-2">' + data.price + '</div>' +
					  '<div class="col-2">' + data.quantity + '</div>' +
					  '<div class="col-1">'+(data.itemReturn == 0 ? 'Bị hủy': '')+'</div>'+
					  	text
					  +
					  '</div>';
			        result += htmlString
				}
				
			let parentDiv = document.getElementById(div);
			parentDiv.innerHTML = result
			
          })
		.catch(function(error) {
		});
    

    }
  
  
  function detailOrderHoan(id,div) {
	  var params = new URLSearchParams();
	  params.append('id', id);
	  fetch('/store/manager/history/hoan/order/detail', {
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
			var result = ""
			
			result += '<div class="row mb-5">' +
			  '<div class="col-3 row">Ảnh</div>' +
			  '<div class="col-3">Tên sản phẩm</div>' +
			  '<div class="col-2">Giá</div>' +
			  '<div class="col-2">Số lượng mua</div>'+
			  '<div class="col-1">Xử lý</div>'+
			  '</div>';
			
				for (let data of dataResp) {
					let htmlString = '<div class="row mb-5" style="align-items: center;">' +
					  '<div class="col-3 row">' +
					  '<a class="row" data-bs-toggle="offcanvas" data-bs-target="#detail" '+
					  'onclick="detail('+data.idProduct+')"> <img alt="" class="row" '+
					  'style="height: 150px" src="/store/img/products/'+data.img+'"></a> ' +
					  '</div>' +
					  '<div class="col-3">' + data.name + '</div>' +
					  '<div class="col-2">' + data.price + '</div>' +
					  '<div class="col-2">' + data.quantity + '</div>' +
					  '<div class="col-1"><a href="/store/manager/history/hoan/no/'+data.id+'/'+id+'">No</a></div>'+
					  '<div class="col-1"><a href="/store/manager/history/hoan/yes/'+data.id+'/'+id+'">Yes</a></div>'+
					  '</div>';
			        result += htmlString
				}
				
			let parentDiv = document.getElementById(div);
			parentDiv.innerHTML = result
			
          })
		.catch(function(error) {
		});
    

    }
  
  
  function status(id,div,url) {
	  var params = new URLSearchParams();
	  params.append('id', id);
	  fetch(url, {
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
			var result = ""
			
			result += '<div class="row mb-2">' +
			  '<div class="row mb-1">' +
			  '<div class="col-1">ID</div>' +
			  '<div class="col-3">Email</div>' +
			  '<div class="col-4">Time</div>' +
			  '<div class="col-4">Mô tả</div>' +
			  '</div>';
			  
			  for (let data of dataResp) {
					let htmlString = '<hr/>'+
					  '<div class="row mb-1">' +
					  '<div class="col-1">'+data.id+'</div>' +
					  '<div class="col-3">'+(data.gmail == null? '':data.gmail)+'</div>' +
					  '<div class="col-4">'+data.date+'</div>' +
					  '<div class="col-4">'+(data.description == null? '':data.description)+'</div>' +
					  '</div>';
					  
			        result += htmlString
				}
			  result +=  '</div>';
			  
			let parentDiv = document.getElementById(div);
			parentDiv.innerHTML = result
			
          })
		.catch(function(error) {
		});
    

    }
  
  
  function detailOrderManager(id,div) {
	  var params = new URLSearchParams();
	  params.append('id', id);
	  fetch('/store/api/product/order/detail', {
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
			var result = ""
			
			result += '<div class="row mb-5">' +
			  '<div class="col-3 row">Ảnh</div>' +
			  '<div class="col-3">Tên sản phẩm</div>' +
			  '<div class="col-3">Giá</div>' +
			  '<div class="col-2">Số lượng mua</div>'+
			  '</div>';
			
				for (let data of dataResp) {
					if(data.itemReturn == 0){
						continue;
					}
					let htmlString = '<div class="row mb-5" style="align-items: center;">' +
					  '<div class="col-3 row">' +
					  '<a class="row" data-bs-toggle="offcanvas" data-bs-target="#detail" '+
					  'onclick="detail('+data.idProduct+')"> <img alt="" class="row" '+
					  'style="height: 150px" src="/store/img/products/'+data.img+'"></a> ' +
					  '</div>' +
					  '<div class="col-3">' + data.name + '</div>' +
					  '<div class="col-3">' + data.price + '</div>' +
					  '<div class="col-2" style="cursor: pointer;color:red;" onclick="giam(this,'+data.id+','+id+')">' + data.quantity + '</div>' +
					  '<div class="col-1"><label onclick="deleteOneItem(this,'+data.id+','+id+')" class="btn btn-primary">Xóa</label></div>' +
					  '</div>';
			        result += htmlString
				}
				
			let parentDiv = document.getElementById(div);
			parentDiv.innerHTML = result
			
          })
		.catch(function(error) {
		});
    }
  
  function deleteOneItem(label,value,idOrder) {
	  var params = new URLSearchParams();
	  params.append('id', value);
	  params.append('idOrder', idOrder);
	  fetch('/store/manager/history/remove/one/item', {
		    method: 'POST', // Phương thức yêu cầu
		    headers: {
		    	'Content-Type': 'application/x-www-form-urlencoded'
		    },
		    body: params.toString() // Chuyển đổi đối tượng URLSearchParams thành chuỗi URL-encoded
		})
		.then(function(response) {
			if (response.ok) {
                return response.text(); // Nhận chuỗi phản hồi nếu trạng thái là "OK"
            } else {
                return response.json(); // Chuyển đổi phản hồi thành đối tượng JSON nếu trạng thái là "Bad Request"
            }
		})
		.then(function(dataResp) {
			if(dataResp.errors != null){
				return
			}
			let divConCon = label.parentNode.parentNode;
			
			let divCon = divConCon.parentNode;
			divConCon.remove();
			if (divCon.childElementCount <= 1) {
				let divCha = divCon.parentNode
				divCha.remove();
			}
			
          })
		.catch(function(error) {
		});
	}
  
  function giam(label,value,idOrder) {
	  let quantity = window.prompt("Nhập số lượng muốn thay đổi:");
	  var params = new URLSearchParams();
	  params.append('id', value);
	  params.append('idOrder', idOrder);
	  params.append('quantity', quantity);
	  fetch('/store/manager/history/giam/one/item', {
		    method: 'POST', // Phương thức yêu cầu
		    headers: {
		    	'Content-Type': 'application/x-www-form-urlencoded'
		    },
		    body: params.toString() // Chuyển đổi đối tượng URLSearchParams thành chuỗi URL-encoded
		})
		.then(function(response) {
			if (response.ok) {
                return response.text(); // Nhận chuỗi phản hồi nếu trạng thái là "OK"
            } else {
                return response.json(); // Chuyển đổi phản hồi thành đối tượng JSON nếu trạng thái là "Bad Request"
            }
		})
		.then(function(dataResp) {
			if(dataResp.errors != null){
				alert(dataResp.errors[0].message)
				return
			}
			label.innerHTML = quantity
			
          })
		.catch(function(error) {
		});
	}
  
</script>