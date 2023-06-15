<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt"%>
<%@ page pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<meta charset="utf-8">
<title>Insert title here</title>
<head>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD"
	crossorigin="anonymous">
<link rel="stylesheet" href="/store/css/all.min.css">
<link rel="stylesheet" href="/store/css/run.css">
<link rel="stylesheet"
	href="/store/fontawesome-free-6.2.1-web/css/all.min.css">
</head>
<body class="container">
	<div class="row bg-danger">
		<jsp:include page="include/fixed.jsp"></jsp:include>
	</div>

	<div class="row mb-3"></div>


	<div class="row mb-5">
		<jsp:include page="${page}"></jsp:include>
	</div>
	
	<div class="row">
		<jsp:include page="include/footer.jsp"></jsp:include>
	</div>

	<div>
		<jsp:include page="include/js/detail.jsp"></jsp:include>
	</div>
	<div>
		<jsp:include page="include/detail.jsp"></jsp:include>
	</div>

	<div>
		<jsp:include page="include/js/xemthem.jsp"></jsp:include>
	</div>
	
	<div>
		<jsp:include page="include/js/history.jsp"></jsp:include>
	</div>
	
	<div>
		<jsp:include page="include/js/cart.jsp"></jsp:include>
	</div>
	
	<div>
		<jsp:include page="include/js/tk.jsp"></jsp:include>
	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN"
		crossorigin="anonymous"></script>
</body>
</html>
