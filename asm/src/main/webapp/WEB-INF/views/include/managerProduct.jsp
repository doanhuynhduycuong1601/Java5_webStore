<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="mb-4 d-flex flex-row-reverse">
	<a href="/store/manager/product/view" class="btn btn-primary">View</a>
</div>
<form:form class="row mb-3" modelAttribute="product"
	enctype="multipart/form-data">
	<div class="col-4">
		<label class="row" for="managerProductInput"> <img
			src="/store/img/products/${product.img == null ? 'chonanh.png' : product.img}"
			class="row" style="height: 300px;" id="managerProductImg">
		</label> <input hidden="true" id="managerProductInput" type="file"
			name="paramImg">
		<div class="row" style="color: red;">${managerProductImgGet}</div>

	</div>
	<div class="col-8">
		<div class="row mb-4" hidden="true">
			<label class="col-3">ID:</label>
			<form:input readonly="true" class="col-9" path="id" />
		</div>
		<div class="row mb-4">
			<div class="row">
				<label class="col-3">Names:</label>
				<form:input class="col-9" path="names" />
			</div>
			<div class="row">
				<form:errors class="row" style="direction: rtl;color: red;" path="names" />
			</div>
		</div>

		<div class="row mb-4">
			<div class="row">
				<label class="col-3">Price:</label>
				<form:input class="col-9" path="price" />
			</div>
			<div class="row">
				<form:errors class="row" style="direction: rtl;color: red;" path="price" />
			</div>
		</div>

		<div class="row mb-4">
			<div class="row">
				<label class="col-3">Quantity:</label>
				<form:input class="col-9" path="quantity" />
			</div>
			<div class="row">
				<form:errors class="row" style="direction: rtl;color: red;" path="quantity" />
			</div>
		</div>

		<div class="row mb-4">
			<div class="row">
				<label class="col-3">CreateAt:</label> <input class="col-9"
					name="create" value="${managerProductEdit.dateAt}" readonly>
			</div>
		</div>

		<div class="row mb-4">
			<label class="col-3">Exists:</label>

			<div class="col-9">
				<form:radiobuttons path="existss" items="${exists}" />
			</div>
		</div>

		<div class="row mb-4">
			<div class="row">
				<label class="col-3">Category:</label>
				<form:select path="category.id" class="col-9">
					<form:options items="${category}" itemValue="id" itemLabel="name" />
				</form:select>
			</div>
		</div>

		<div class="row mb-4">
			<div class="row">
				<label class="col-3">Descript:</label>
				<form:textarea class="col-9" path="descript" />
			</div>
			<div class="row">
				<form:errors class="row" style="direction: rtl;color: red;" path="descript" />
			</div>
		</div>



		<div class="col-12">
			<c:choose>
				<c:when test="${view != null}">
					<button class="btn btn-primary" formaction="/product/create">Create</button>
				</c:when>
				<c:otherwise>
					<button class="btn btn-primary"
						formaction="/store/manager/product/update">Update</button>
				</c:otherwise>
			</c:choose>
			<a class="btn btn-primary" href="/store/manager/product/reset">Reset</a>
		</div>
	</div>
</form:form>


<script>
	document.getElementById('managerProductInput').addEventListener('change', (event) => {
		let selectedFile = event.target.files[0];
		if (selectedFile) {
            let fileType = selectedFile.type;

            if (fileType.startsWith("image/")) {
                let filePath = URL.createObjectURL(selectedFile);
                document.getElementById("managerProductImg").setAttribute("src", filePath);
            } else {
                alert("Chỉ cho phép chọn tệp tin ảnh!");
                this.value = ""; // Xóa giá trị đầu vào của phần tử input
            }
        }
	});
	
</script>