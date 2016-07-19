<%@ include file="fragments/frame.jsp"%>
<izmus-nav-bar ng-app="myRequestsApp"
	initial-selected-item=' <spring:message code="navBar.menu.cartMenu.myRequests" />'>
	<my-requests-window
				flex
				layout="column">
	</my-requests-window>
</izmus-nav-bar>
<script
	src="<c:url value="/views/my-requests/js/my-requests.ctrl.js" />"></script>
<script
	src="<c:url value="/views/my-requests/js/my-requests.dir.js" />"></script>
<script
	src="<c:url value="/views/my-requests/js/my-requests.ser.js" />"></script>
<script>
	var lang = {
			
	};
</script>