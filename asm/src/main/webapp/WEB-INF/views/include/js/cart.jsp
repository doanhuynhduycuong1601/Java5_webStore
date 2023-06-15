<%@page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt"%>

<script>
  function addProductCart(id) {
	  var data = { 'id': id, 'quantity': 0 };
    
	  fetch('/store/product/addCart', {
		    method: 'POST', // Phương thức yêu cầu
		    headers: {
		        'Content-Type': 'application/json' // Loại dữ liệu gửi đi là JSON
		    },
		    body: JSON.stringify(data) // Chuyển đổi đối tượng JSON thành chuỗi JSON
		})
		.then(function(response) {
              return response.json(); // Nhận chuỗi phản hồi nếu trạng thái là "OK"
		})
		.then(function(dataResp) {
			if(dataResp.errors != null){
				alert(dataResp.errors[0].message)
				return
			}
			let divs = document.getElementsByClassName("addCart");
        	for (let i = 0; i < divs.length; i++) {
        	      let div = divs[i];
        	      	let dataId = div.getAttribute("data-id");
        	      	if(id == dataId){
        	      		let divBtn = div.querySelector(".btn");
        	      		divBtn.style.display = 'block'
        	      		
        	      		let divp2 = div.querySelector(".p-2");
        	      		divp2.style.display = 'none'
        	      		let label = div.querySelector("label");
        	            label.innerHTML = dataResp.quantity;
        	            let myCart = document.getElementById("myCart");
        	            myCart.innerHTML = dataResp.getCount
        	      	}
        	      }
          })
		.catch(function(error) {
		});
    

    }
  
  
  function updateCart(input,disting) {
	  let id = input.parentNode.parentNode
	  let label = id.querySelector("label");
	  let value = parseInt(label.innerHTML);
	  if(disting === '+'){
		  value +=   1
	  }else{
		  value -=   1
	  }
	  if(value <0 || value > 5){
		  return;
	  }
		var data = { 'id': id.getAttribute("data-id"), 'quantity': value };
    
	  fetch('/store/product/cart/disting', {
		    method: 'POST', // Phương thức yêu cầu
		    headers: {
		        'Content-Type': 'application/json' // Loại dữ liệu gửi đi là JSON
		    },
		    body: JSON.stringify(data) // Chuyển đổi đối tượng JSON thành chuỗi JSON
		})
		.then(function(response) {
              return response.json(); // Chuyển đổi phản hồi thành đối tượng JSON nếu trạng thái là "Bad Request"
		})
		.then(function(dataResp) {
			if(dataResp.errors != null){
				alert(dataResp.errors[0].message)
				return
			}
			let divs = document.getElementsByClassName("addCart");
        	for (let i = 0; i < divs.length; i++) {
        	      let div = divs[i];
        	      let dataId = div.getAttribute("data-id")
        	      	if(id.getAttribute("data-id") == dataId){
        	      		
        	      		if(dataResp.quantity <= 0){
        	      			let divBtn = div.querySelector(".btn");
            	      		divBtn.style.display = 'none'
        	      			let divp2 = div.querySelector(".p-2");
            	      		divp2.style.display = 'block'
        	      		}
        	      		
        	      		let label = div.querySelector("label");
        	      		console.log(dataResp)
        	            label.innerHTML = dataResp.quantity;
        	      	}
        	      }
        	if(dataResp.getCount <= 0){
        		let myCart = document.getElementById("myCart");
	            myCart.innerHTML = ''
        	}else{
        		let myCart = document.getElementById("myCart");
	            myCart.innerHTML = dataResp.getCount
        	}
          })
		.catch(function(error) {
		});
    

    }
</script>