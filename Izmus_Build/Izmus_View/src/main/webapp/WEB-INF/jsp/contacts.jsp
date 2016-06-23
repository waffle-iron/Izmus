<%@ include file="fragments/frame.jsp"%>
<izmus-nav-bar ng-app="contactsApp"
	initial-selected-item=' <spring:message code="navBar.menu.assessorsMenu.contacts" />'>
	<contacts-dashboard
				flex
				layout="column">
	</contacts-dashboard>
</izmus-nav-bar>
<script
	src="<c:url value="/views/contacts/js/contacts.ctrl.js" />"></script>
<script
	src="<c:url value="/views/contacts/js/contacts.dir.js" />"></script>
<script
	src="<c:url value="/views/contacts/js/contacts.ser.js" />"></script>
<script>
	var lang = {
			search:' <spring:message code="navBar.menu.startupAssessment.search" />',
	};
</script>