<%@ include file="fragments/frame.jsp"%>
<izmus-nav-bar ng-app="availableStartupsApp"
	initial-selected-item=' <spring:message code="navBar.menu.assessorsMenu.availableStartups" />'>
	<available-startups
				flex
				layout="column">
	</available-startups>
</izmus-nav-bar>
<script
	src="<c:url value="/views/available-startups/js/available-startups.ctrl.js" />"></script>
<script
	src="<c:url value="/views/available-startups/js/available-startups.dir.js" />"></script>
<script
	src="<c:url value="/views/available-startups/js/available-startups.ser.js" />"></script>
<script
	src="<c:url value="/views/available-startups/js/available-startups-list.dir.js" />"></script>
<script
	src="<c:url value="/views/available-startups/js/startup-details.dir.js" />"></script>
<script
	src="<c:url value="/views/available-startups/js/startup-header.dir.js" />"></script>
<script>
	var lang = {
			search:' <spring:message code="navBar.menu.startupAssessment.search" />',
			availableStartups: ' <spring:message code="navBar.menu.assessorsMenu.availableStartups" />',
			site:' <spring:message code="navBar.menu.startupAssessment.site" />',
			sector:' <spring:message code="navBar.menu.startupAssessment.sector" />',
	};
</script>