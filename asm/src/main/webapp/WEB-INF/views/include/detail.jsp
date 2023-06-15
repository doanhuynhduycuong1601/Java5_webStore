<%@page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt"%>


<div style="height: 80%;margin-top: 5%" class="offcanvas offcanvas-start" tabindex="-1"
	id="detail" aria-labelledby="offcanvasExampleLabel">
	<div class="offcanvas-header">
		<h5 class="offcanvas-title" id="offcanvasExampleLabel"></h5>
		<button type="button" class="btn-close" data-bs-dismiss="offcanvas"
			aria-label="Close"></button>
	</div>
	<div class="offcanvas-body">
		<div class="row">
			<img id="detailImg" alt="" src="/store/img/products/25.webp"
				style="height: 180px">
		</div>
		<h5 id="detailName"></h5>
		<h5 id="detailPrice"></h5>
		<h5 id="detailQuantity"></h5>
		<h5 id="detailCategory"></h5>
		<h5 id="detailStatus"></h5>
	</div>
</div>