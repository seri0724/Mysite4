<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="${pageContext.request.contextPath }/assets/css/guestbook.css" rel="stylesheet" type="text/css">
	<link href="${pageContext.request.contextPath }/assets/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">
	
	<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.12.4.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/assets/bootstrap/js/bootstrap.min.js"></script>

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
				<div id="guestbook">
					
					<%-- <form action="${pageContext.request.contextPath }/gb/add" method="post"> --%>
						
						<table>
							<tr>
								<td>이름</td><td><input type="text" name="name" /></td>
								<td>비밀번호</td><td><input type="password" name="password" /></td>
							</tr>
							<tr>
								<td colspan=4><textarea name="content" id="content"></textarea></td>
							</tr>
							<tr>
								<td colspan=4 align=right><input id="btnInsert" type="submit" VALUE="확인 " /></td>
							</tr>
						</table>

					<!-- </form> -->
					
					<!-- <input id="btnDel" type="button" VALUE="삭제예제버튼 " /> -->
					
					<ul id="listArea"></ul>
					
					<input id="btnNext" type="button" VALUE="다음글 5개 가져오기 " />
					
				</div><!-- /guestbook -->
			</div><!-- /content -->
		</div><!-- /wrapper -->
		
		<c:import url="/WEB-INF/views/includes/footer.jsp"></c:import>
		
		<!-- footer -->
		
	</div> <!-- /container -->

<!-- 삭제팝업(모달)창 -->
	<div class="modal fade" id="del-pop">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					<h4 class="modal-title">방명록삭제</h4>
				</div>
				<div class="modal-body">
					<label>비밀번호</label>
					<input type="password" name="modalPassword" id="modalPassword"><br>	
					<input type="hidden" name="modalPassword" value="" id="modalNo"> <br>	
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal" id="btn_cancel">취소</button>
					<button type="button" class="btn btn-danger" id="btn_del">삭제</button>
				</div>
			</div><!-- /.modal-content -->
		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->

</body>

<script type="text/javascript">

var page = 1;

	$(document).ready(function() { //시작했을 때 5개 뿌림
		fetchList();
	});
	
	$("#btnNext").on("click", function() { //버튼 누를 때 5개 뿌림
		page += 1;
		fetchList();
	});
	
	/* $("#btnDel").on("click", function() { //modal창에서 삭제버튼 눌렀을때
		$("#del-pop").modal();
	}); */
	
	$("#btn_cancel").on("click",function() { //취소
		$("#modalPassword").val("");
	});
	
	$("#listArea").on("click", ".btn_del_content", function() {
		$("#login_process").text("");
		var no = $(this).data("no");		//data-no 를 여기서 쓰는 것임!
		$("#modalNo").val(no);
		$("#del-pop").modal();
	});
	$("#btn_del").on("click", function() {

		var no = $("#modalNo").val();
		var password = $("#modalPassword").val();
		
		$("#modalPassword").val("");
		
		var modalGuestbookVo = {
				no: no,
				password: password
		}
		
		$.ajax({
			//보낼 때 데이터 타입
			url : "${pageContext.request.contextPath }/gb/api/delete",		
			type : "post",
			contentType : "application/json",
			data : JSON.stringify(modalGuestbookVo),
			
			//받을 때 데이터 타입
			dataType : "json",
			success : function(no) {
				
				if(GuestbookVo.fail == 1){ //맞는 비밀번호
					$("#del-pop").modal("hide");
					$("[id="+GuestbookVo.no+"]").remove();
				}else if(GuestbookVo.fail == 0){ //틀린비번
					$("#login_process").text("비밀번호가 다릅니다.").css("color","red");
				}
				
			},
			error : function(XHR, status, error) {
				console.error(status + " : " + error);
			}
		});
	});
	
	$("#btnInsert").on("click", function() { 
		var name = $("[name=name]").val();
		var password = $("[name=password]").val();
		var content = $("[name=content]").val();
		
		$("[name=name]").val("");
		$("[name=password]").val("");
		$("[name=content]").val("");
		
		var guestbookVo = {
			name: name,
			password: password,
			content: content
		}

		$.ajax({
			url : "${pageContext.request.contextPath }/gb/api/insert",		
			type : "post",
			contentType : "application/json",
			data : JSON.stringify(guestbookVo),

			dataType : "json",
			success : function(guestbookVo) {
				render(guestbookVo,"up");
			},
			error : function(XHR, status, error) {
				console.error(status + " : " + error);
			}
		});
	});	

function fetchList() {
	
	$.ajax({
		url : "${pageContext.request.contextPath }/gb/api/list",		
		type : "post",
		data : {page : page},
		
		dataType : "json",		 
		success : function(list) {
				
			for(var i = 0; i < list.length; i++) {
				render(list[i], "down");
			}
		},
		error : function(XHR, status, error) {
			console.error(status + " : " + error);
		}
	});
}	

function render(guestbookVo, updown) { //그리다
	
	var str = "";
	str += "<li>";
	str += "	<table>";
	str += "		<tr>";
	str += "			<td>" + guestbookVo.no + "</td>";
	str += "			<td>" + guestbookVo.name + "</td>";
	str += "			<td>" + guestbookVo.regDate + "</td>";
	str += "			<td><button class='btn_del_content' type='button' data-no="+guestbookVo.no+">삭제</button></td>";
	str += "		</tr>";
	str += "		<tr>";
	str += "			<td colspan=4>" + guestbookVo.content + "</td>";
	str += "		</tr>";
	str += "	</table>";
	str += "	<br>";
	str += "</li>";
	
	if(updown == "up") {
		$("#listArea").prepend(str);
	} else if(updown == "down"){
		$("#listArea").append(str);
	} else {
		console.log("updown 오류");
	}
}
</script>

</html>