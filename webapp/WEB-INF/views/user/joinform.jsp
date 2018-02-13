<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.12.4.js"></script>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="${pageContext.request.contextPath }/assets/css/user.css" rel="stylesheet" type="text/css">
	<title>Insert title here</title>
</head>
<body>

	<div id="container">
		
		<c:import url="/WEB-INF/views/includes/header.jsp"></c:import>
		
		<!-- header -->
				
		<c:import url="/WEB-INF/views/includes/navigation.jsp"></c:import>
		
		<!-- navigation -->
			
		<div id="wrapper">
			<div id="content">
				<div id="user">
	
					<form id="join-form" name="joinForm" method="post" action="${pageContext.request.contextPath }/user/join">
						
						<label class="block-label" for="name">이름</label>
						<input id="name" name="name" type="text" value="">
	
						<label class="block-label" for="email">이메일</label>
						<input id="email" name="email" type="text" value="">
						<input type="button" value="id 중복체크" id="btnEmailCheck">
						<div id="checkMsg"> </div>
						
						<label class="block-label">패스워드</label>
						<input name="password" type="password" value="">
						
						<fieldset>
							<legend>성별</legend>
							<label>여</label> <input type="radio" name="gender" value="female" checked="checked">
							<label>남</label> <input type="radio" name="gender" value="male">
						</fieldset>
						
						<fieldset>
							<legend>약관동의</legend>
							<input id="agree-prov" type="checkbox" name="agreeProv" value="y">
							<label>서비스 약관에 동의합니다.</label>
						</fieldset>
						
						<input type="submit" value="가입하기">
						
					</form>
					
				</div><!-- /user -->
			</div><!-- /content -->
		</div><!-- /wrapper -->
		
		<c:import url="/WEB-INF/views/includes/footer.jsp"></c:import>
		
		<!-- footer -->
		
	</div> <!-- /container -->
	
</body>
<script type="text/javascript"> //email중복체크
	$("#btnEmailCheck").on("click", function() {
		var email = $("#email").val();
		var userVo = {
				email : email
		}
		$.ajax({
			url : "${pageContext.request.contextPath }/users/api/emailcheck",		
			type : "post",
			contentType : "application/json",
			data : JSON.stringify(userVo), //여기까지가 데이터를 보낼때
			
			dataType : "json", //여기부터는 데이터를 받을때
			success : function(result) {
				console.log(result);
				if(result == true) {
					$("#checkMsg").html("<font color = blue>사용하실 수 있는 Email입니다</font>");
				} else {
					$("#checkMsg").html("<font color = red>사용하실 수 없는 Email입니다</font>");
				}
			},
			error : function(XHR, status, error) {
				console.error(status + " : " + error);
			}
		});
	});
</script>
</html>