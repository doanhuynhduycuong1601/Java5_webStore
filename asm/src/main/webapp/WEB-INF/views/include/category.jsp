<%@page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt"%>

<div class="d-flex justify-content-end">
	<div>
		<button class="btn btn-primary" type="button"
			data-bs-toggle="collapse" data-bs-target="#collapseWidthExample"
			aria-expanded="false" aria-controls="collapseWidthExample">
			Tạo loại sản phẩm</button>
	</div>

	<div>
		<div class="collapse collapse-horizontal" id="collapseWidthExample">
			<div class="card card-body" style="width: 300px;">
					<div class="row mb-4">
						<label class="col-4">Tên loại</label>
						<input class="col-8" id="inputCategoryNames">
					</div>
					<div class="col-9">
						<button class="btn btn-primary" onclick="create()">Tạo</button>
					</div>
			</div>
		</div>
	</div>
</div>
<div class="accordion accordion-flush" id="accordionFlushExample">
	<div class="row mb-4 col-10 offset-1">
		<div class="col-1">ID</div>
		<div class="col-4">Tên loại sản phẩm</div>
		<div class="col-3">Số lượng sản phẩm</div>
		<div class="col-2">Xóa</div>
	</div>
	<div class="row col-10 offset-1" id="listCategoryManager">
		<c:forEach var="list" items="${managerCategory}">
			<div class="accordion-item row mb-4">
				<div class="accordion-header row">
					<div class="col-1">${list.id}</div>
					<div class="col-4">
						<input value="${list.name}"
							onblur="updateCategory('${list.id}',this)">
					</div>
					<div class="col-3" style="text-align: center;">
						<label>${list.quantity}</label>
					</div>
					<c:if test="${list.quantity <= 0}">
						<div class="col-2" onclick="remove(${list.id},this)" style="text-align: center;">
							<label class="btn btn-primary">Xóa</label>
						</div>
					</c:if>
				</div>
			</div>

		</c:forEach>
	</div>
</div>

<script>
	function updateCategory(idCategory, input) {
		var data = { 'id': idCategory, 'names': input.value };

		fetch('/store/manager/category/update', {
		    method: 'PUT', // Phương thức yêu cầu
		    headers: {
		        'Content-Type': 'application/json' // Loại dữ liệu gửi đi là JSON
		    },
		    body: JSON.stringify(data) // Chuyển đổi đối tượng JSON thành chuỗi JSON
		})
		.then(function(response) {
			if (response.ok) {
                return response.text(); // Nhận chuỗi phản hồi nếu trạng thái là "OK"
            } else {
                return response.json(); // Chuyển đổi phản hồi thành đối tượng JSON nếu trạng thái là "Bad Request"
            }
		})
		.then(function(data) {
			if (typeof data === 'string') {
				alert(data)
            } else {
                // Xử lý đối tượng JSON khi trạng thái là "Bad Request"
                alert(data.errors[0].message)
            }
            })
		.catch(function(error) {
		});
	}
	
	//add
	function create() {
		let name = document.getElementById("inputCategoryNames")
		var data = { 'names': name.value };

		fetch('/store/manager/category/create', {
		    method: 'POST', // Phương thức yêu cầu
		    headers: {
		        'Content-Type': 'application/json' // Loại dữ liệu gửi đi là JSON
		    },
		    body: JSON.stringify(data) // Chuyển đổi đối tượng JSON thành chuỗi JSON
		})
		.then(function(response) {
			if (response.ok) {
                return response.json(); // Nhận chuỗi phản hồi nếu trạng thái là "OK"
            } else {
                return response.json(); // Chuyển đổi phản hồi thành đối tượng JSON nếu trạng thái là "Bad Request"
            }
		})
		.then(function(data) {
			console.log(data)
			if(data.errors != null){
				alert(data.errors[0].message)
			}else{
				var divElement = document.getElementById("listCategoryManager");
				
				var text = '<div class="accordion-item row mb-4">\n' +
				  '  <div class="accordion-header row">\n' +
				  '    <div class="col-1">' + data.id + '</div>\n' +
				  '    <div class="col-4">\n' +
				  '      <input value="' + data.name + '" onblur="updateCategory(' + data.id + ',this)">\n' +
				  '    </div>\n' +
				  '    <div class="col-3" style="text-align: center;">\n' +
				  '      <label>' + data.quantity + '</label>\n' +
				  '    </div>\n'+
				  '    <div class="col-2" onclick="remove(' + data.id + ',this)" style="text-align: center;">\n' +
				    '      <label class="btn btn-primary">Xóa</label>\n' +
				    '    </div>\n'
				  '    </div>\n';
                
                
                `
				
                `
					
				divElement.insertAdjacentHTML('afterbegin', text);
			}
			
            })
		.catch(function(error) {
		});
	}
	
	function remove(id, input) {
		let name = document.getElementById("inputCategoryNames")
		var data = { 'id': id };

		fetch('/store/manager/category/delete', {
		    method: 'DELETE', // Phương thức yêu cầu
		    headers: {
		        'Content-Type': 'application/json' // Loại dữ liệu gửi đi là JSON
		    },
		    body: JSON.stringify(data) // Chuyển đổi đối tượng JSON thành chuỗi JSON
		})
		.then(function(response) {
			if (response.ok) {
				 return response.text(); // Nhận chuỗi phản hồi nếu trạng thái là "OK"
            } else {
                return response.json(); // Chuyển đổi phản hồi thành đối tượng JSON nếu trạng thái là "Bad Request"
            }
		})
		.then(function(data) {
			if(data.errors != null){
				alert(data.errors[0].message)
			}else{
				alert(data)
				input.parentNode.parentNode.remove()	
			}
        })
		.catch(function(error) {
		});
	}
</script>