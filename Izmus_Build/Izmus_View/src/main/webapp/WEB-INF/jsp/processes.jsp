<%@ include file="fragments/frame.jsp"%>
<izmus-nav-bar ng-app="processesApp"
		initial-selected-item=' <spring:message code="navBar.menu.adminMenu.processes" />'>
		<izmus-processes
			flex
			layout="column">
		</izmus-processes>
</izmus-nav-bar>
<script
	src="<c:url value="/views/processes/js/processes.ctrl.js" />"></script>
<script
	src="<c:url value="/views/processes/js/processes.dir.js" />"></script>
<script
	src="<c:url value="/views/processes/js/processes.ser.js" />"></script>
<script>
	var lang = {
			
	};
</script>