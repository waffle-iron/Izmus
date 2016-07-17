<%@ include file="fragments/frame.jsp"%>
<izmus-nav-bar ng-app="investorsDashboardApp"
	initial-selected-item=' <spring:message code="navBar.menu.investorsMenu.investorsDashboard" />'>
	<investors-dashboard-window
				flex
				layout="column">
	</investors-dashboard-window>
</izmus-nav-bar>
<script
	src="<c:url value="/views/investors-dashboard/js/investors-dashboard.ctrl.js" />"></script>
<script
	src="<c:url value="/views/investors-dashboard/js/investors-dashboard.dir.js" />"></script>
<script
	src="<c:url value="/views/investors-dashboard/js/startup-grid-list.dir.js" />"></script>
<script
	src="<c:url value="/views/investors-dashboard/js/investors-dashboard.ser.js" />"></script>
<script
	src="<c:url value="/views/investors-dashboard/js/startup-preview.ser.js" />"></script>
<script
	src="<c:url value="/views/investors-dashboard/js/startup-header.dir.js" />"></script>
<script
	src="<c:url value="/views/investors-dashboard/js/startup-details.dir.js" />"></script>
<script
	src="<c:url value="/views/investors-dashboard/js/add-to-cart-confirmation.ser.js" />"></script>
<script>
	var lang = {
			searchName:' <spring:message code="navBar.menu.partnerMenu.partnerDashboard.searchName" />',
			cancel:' <spring:message code="navBar.menu.startupAssessment.cancel" />',
			filterSector:' <spring:message code="navBar.menu.partnerMenu.partnerDashboard.filterSector" />',
			site:' <spring:message code="navBar.menu.startupAssessment.site" />',
			sector:' <spring:message code="navBar.menu.startupAssessment.sector" />',
			productStage:' <spring:message code="navBar.menu.partnerMenu.partnerDashboard.productStage" />',
			fundingStage:' <spring:message code="navBar.menu.partnerMenu.partnerDashboard.fundingStage" />',
			welcomeText:' <spring:message code="navBar.menu.partnerMenu.partnerDashboard.welcomeText" />',
			filterText:' <spring:message code="navBar.menu.partnerMenu.partnerDashboard.filterText" />',
			ok:' <spring:message code="navBar.menu.startupAssessment.ok" />',
			founded:' <spring:message code="navBar.menu.partnerMenu.partnerDashboard.founded" />',
			numberOfEmployees:' <spring:message code="navBar.menu.partnerMenu.partnerDashboard.numberOfEmployees" />',
			thankYouForAdding: ' <spring:message code="navBar.menu.partnerMenu.partnerDashboard.thankYouForAdding" />'
	};
</script>