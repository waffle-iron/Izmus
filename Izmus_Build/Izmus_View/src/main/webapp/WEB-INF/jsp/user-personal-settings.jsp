<%@ include file="fragments/frame.jsp"%>
<izmus-nav-bar ng-app="userPersonalSettingsApp">
		<izmus-user-personal-settings
			flex
			layout="column">
		</izmus-user-personal-settings>
</izmus-nav-bar>
<script
	src="<c:url value="/views/user-personal-settings/js/user-personal-settings.ctrl.js" />"></script>
<script
	src="<c:url value="/views/user-personal-settings/js/user-personal-settings.dir.js" />"></script>
<script
	src="<c:url value="/views/user-personal-settings/js/user-personal-settings.ser.js" />"></script>
<script>
	var lang = {
		created: ' <spring:message code="navBar.menu.adminMenu.users.created" />',
		lastSeen:' <spring:message code="navBar.menu.adminMenu.users.lastSeen" />',
		userId:' <spring:message code="navBar.menu.adminMenu.users.userId" />',
		userInfo:' <spring:message code="navBar.menu.adminMenu.users.userInfo" />',
		gender:' <spring:message code="navBar.menu.adminMenu.users.gender" />',
		male:' <spring:message code="navBar.menu.adminMenu.users.male" />',
		female:' <spring:message code="navBar.menu.adminMenu.users.female" />',
		userName:' <spring:message code="navBar.menu.adminMenu.users.userName" />',
		userEmail:' <spring:message code="navBar.menu.adminMenu.users.userEmail" />',
		userRoles:' <spring:message code="navBar.menu.adminMenu.users.userRoles" />',
		saveUserInfo: ' <spring:message code="navBar.menu.adminMenu.users.saveUserInfo" />',
		changePassword: ' <spring:message code="navBar.menu.changePassword" />'
	};
</script>