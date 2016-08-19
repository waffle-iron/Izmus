<%@ include file="fragments/frame.jsp"%>
<izmus-nav-bar ng-app="wishListApp"
	initial-selected-item=' <spring:message code="navBar.menu.cartMenu.wishList" />'>
	<wish-list-window
				flex
				layout="column">
	</wish-list-window>
</izmus-nav-bar>
<script
	src="<c:url value="/views/wish-list/js/wish-list.ctrl.js" />"></script>
<script
	src="<c:url value="/views/wish-list/js/wish-list.dir.js" />"></script>
<script
	src="<c:url value="/views/wish-list/js/wish-list.ser.js" />"></script>
<script
	src="<c:url value="/views/wish-list/js/startup-grid-list.dir.js" />"></script>
<script
	src="<c:url value="/views/wish-list/js/startup-preview.ser.js" />"></script>
<script
	src="<c:url value="/views/wish-list/js/startup-header.dir.js" />"></script>
<script
	src="<c:url value="/views/wish-list/js/startup-details.dir.js" />"></script>
<script
	src="<c:url value="/views/wish-list/js/add-to-cart-confirmation.ser.js" />"></script>
<script>
	var lang = {
			viewDetails: ' <spring:message code="navBar.menu.partnerMenu.partnerDashboard.viewDetails" />',
			addToMyRequests: ' <spring:message code="navBar.menu.partnerMenu.partnerDashboard.addToMyRequests" />',
			cancel:' <spring:message code="navBar.menu.startupAssessment.cancel" />',
			site:' <spring:message code="navBar.menu.startupAssessment.site" />',
			sector:' <spring:message code="navBar.menu.startupAssessment.sector" />',
			productStage:' <spring:message code="navBar.menu.partnerMenu.partnerDashboard.productStage" />',
			fundingStage:' <spring:message code="navBar.menu.partnerMenu.partnerDashboard.fundingStage" />',
			ok:' <spring:message code="navBar.menu.startupAssessment.ok" />',
			founded:' <spring:message code="navBar.menu.partnerMenu.partnerDashboard.founded" />',
			numberOfEmployees:' <spring:message code="navBar.menu.partnerMenu.partnerDashboard.numberOfEmployees" />',
			thankYouForAdding: ' <spring:message code="navBar.menu.partnerMenu.partnerDashboard.thankYouForAdding" />',
			addedToMyRequests: ' <spring:message code="navBar.menu.partnerMenu.partnerDashboard.addedToMyRequests" />',
			notAddedRequests: ' <spring:message code="navBar.menu.partnerMenu.partnerDashboard.notAddedRequests" />',
	};
</script>