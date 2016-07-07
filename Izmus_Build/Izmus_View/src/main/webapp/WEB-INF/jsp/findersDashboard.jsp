<%@ include file="fragments/frame.jsp"%>
<izmus-nav-bar ng-app="findersDashboardApp"
	initial-selected-item=' <spring:message code="navBar.menu.findersMenu.findersDashboard" />'>
	<finders-dashboard-window
				flex
				layout="column">
	</finders-dashboard-window>
</izmus-nav-bar>
<script
	src="<c:url value="/views/finders-dashboard/js/finders-dashboard.ctrl.js" />"></script>
<script
	src="<c:url value="/views/finders-dashboard/js/finders-dashboard.dir.js" />"></script>
<script
	src="<c:url value="/views/finders-dashboard/js/startup-grid-list.dir.js" />"></script>
<script
	src="<c:url value="/views/finders-dashboard/js/finders-dashboard.ser.js" />"></script>
<script>
	var lang = {
			searchName:' <spring:message code="navBar.menu.partnerMenu.partnerDashboard.searchName" />',
			filterSector:' <spring:message code="navBar.menu.partnerMenu.partnerDashboard.filterSector" />',
			site:' <spring:message code="navBar.menu.startupAssessment.site" />',
			sector:' <spring:message code="navBar.menu.startupAssessment.sector" />'
	};
</script>