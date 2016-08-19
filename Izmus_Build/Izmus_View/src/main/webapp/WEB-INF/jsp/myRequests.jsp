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
<script
	src="<c:url value="/views/my-requests/js/startup-grid-list.dir.js" />"></script>
<script
	src="<c:url value="/views/my-requests/js/startup-preview.ser.js" />"></script>
<script
	src="<c:url value="/views/my-requests/js/startup-header.dir.js" />"></script>
<script
	src="<c:url value="/views/my-requests/js/startup-details.dir.js" />"></script>
<script
	src="<c:url value="/views/my-requests/js/cancel-request.ser.js" />"></script>
<script>
	var lang = {
			viewDetails: ' <spring:message code="navBar.menu.partnerMenu.partnerDashboard.viewDetails" />',
			cancel:' <spring:message code="navBar.menu.startupAssessment.cancel" />',
			cancelRequest:' <spring:message code="navBar.menu.cartMenu.myRequests.cancelRequest" />',
			requestCancelled:' <spring:message code="navBar.menu.cartMenu.myRequests.requestCancelled" />',
			anErrorOccurred:' <spring:message code="navBar.menu.cartMenu.myRequests.anErrorOccurred" />',
			site:' <spring:message code="navBar.menu.startupAssessment.site" />',
			sector:' <spring:message code="navBar.menu.startupAssessment.sector" />',
			productStage:' <spring:message code="navBar.menu.partnerMenu.partnerDashboard.productStage" />',
			fundingStage:' <spring:message code="navBar.menu.partnerMenu.partnerDashboard.fundingStage" />',
			ok:' <spring:message code="navBar.menu.startupAssessment.ok" />',
			founded:' <spring:message code="navBar.menu.partnerMenu.partnerDashboard.founded" />',
			numberOfEmployees:' <spring:message code="navBar.menu.partnerMenu.partnerDashboard.numberOfEmployees" />',
	};
</script>