<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="${pageContext.request.contextPath }/assets/css/guestbook.css" rel="stylesheet" type="text/css">
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
				<div id="guestbook" class="delete-form">
					
					<form method="post" action="${pageContext.request.contextPath }/gb/delete">
						
						<label>비밀번호</label>
						<input type="password" name="password">
						<input type="submit" value="확인">
						<input type="hidden" value="${param.no }" name="no">
						<input type="hidden" value="delete" name="a">
					</form>
					<a href="${pageContext.request.contextPath }/gb/list">방명록 리스트</a>
					
				</div>
			</div><!-- /content -->
		</div><!-- /wrapper -->
		
		<c:import url="/WEB-INF/views/includes/footer.jsp"></c:import>
		
		<!-- footer -->
		
	</div> <!-- /container -->

</body>
</html>
