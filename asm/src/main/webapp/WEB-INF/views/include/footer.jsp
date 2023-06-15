<%@page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt"%>

<div class="container">
	<div class="row">
        <div class="col-4">
            <h2>Introduce</h2>
            <h6>Name : Đoàn Huỳnh Duy Cương</h6>
            <h6>Trường : cao đẳng FPT</h6>
            <h6>Chuyên nghành BACK-END(Java)</h6>
            <h6>Năm học 09/10/2021 - 12/12/2023</h6>
            <h6></h6>
        </div>

        <div class="col-4">
            <h2>Skill</h2>
            <h6>Spring boot(5/5)</h6>
            <h6>Servlet/JSP(4.8/5)</h6>
            <h6>Java swing(4/5)</h6>
            <h6>SQL server(5/5)</h6>
            <h6>BootStrap(5/5)</h6>
            <h6>Java core(4.7/5)</h6>
        </div>
        
        <div class="col-4">
        	<h2>Contact</h2>
        	<div class="row mb-4">
        		<i onclick="git()" style="font-size: 30px;" class="fa-brands fa-github col-2"></i>
        		<i onclick="face()" style="font-size: 30px;" class="fa-brands fa-facebook col-2"></i>
        		<a href="/asm/cv.pdf" class="col-2">CV</a>
        	</div>
        	<div class="mb-4">
        		<i style="font-size: 20px" class="fa-solid fa-phone"></i>  : 0357846328
        	</div>
        	
        	<div class="mb-4">
        		<i class="fa-solid fa-envelope"></i>  : doanhuynhduycuong1601@gmail.com
        	</div>
        	
        </div>

        
    </div>
</div>
<script type="text/javascript">
	function face() {
		window.open("https://www.facebook.com/profile.php?id=100026289963110", "FaceBook");
	}
	
	function git() {
		window.open("https://github.com/doanhuynhduycuong1601", "Github");
	}
</script>