<%@ include file="fragments/frame.jsp"%>
<izmus-nav-bar ng-app="userManagementApp"
	initial-selected-item=' <spring:message code="navBar.menu.adminMenu.users" />'>
	<izmus-user-management
				flex
				layout="column" 
				user-id=' <spring:message code="navBar.menu.adminMenu.users.userId" />'
				user-name=' <spring:message code="navBar.menu.adminMenu.users.userName" />'
				user-roles=' <spring:message code="navBar.menu.adminMenu.users.userRoles" />'
				last-seen=' <spring:message code="navBar.menu.adminMenu.users.lastSeen" />'
				user-info=' <spring:message code="navBar.menu.adminMenu.users.userInfo" />'
				user-email=' <spring:message code="navBar.menu.adminMenu.users.userEmail" />'
				user-enabled=' <spring:message code="navBar.menu.adminMenu.users.userEnabled" />'
				global-search=' <spring:message code="navBar.menu.adminMenu.users.globalSearch" />'
				male=' <spring:message code="navBar.menu.adminMenu.users.male" />'
				female=' <spring:message code="navBar.menu.adminMenu.users.female" />'
				gender=' <spring:message code="navBar.menu.adminMenu.users.gender" />'
				created=' <spring:message code="navBar.menu.adminMenu.users.created" />'
				user-management-text=' <spring:message code="navBar.menu.adminMenu.users.userManagementText" />'
				user-list-text=' <spring:message code="navBar.menu.adminMenu.users.userListText" />'
				available-roles=' <spring:message code="navBar.menu.adminMenu.users.availableRoles" />'
				cancel=' <spring:message code="navBar.menu.adminMenu.users.cancel" />'
				ok=' <spring:message code="navBar.menu.adminMenu.users.ok" />'>
	</izmus-user-management>
</izmus-nav-bar>
<script src="<c:url value="/views/users/js/users.ctrl.js" />"></script>
<script src="<c:url value="/views/users/js/user-management.dir.js" />"></script>
<script
	src="<c:url value="/views/users/js/user-management-data.ser.js" />"></script>
<script src="<c:url value="/views/users/js/user-table.dir.js" />"></script>
<script src="<c:url value="/views/users/js/user-preview.dir.js" />"></script>
<script src="<c:url value="/views/users/js/add-user.ctrl.js" />"></script>
<script type="text/javascript">
		var lang = {
			register: ' <spring:message code="landingPage.register.title" />',
			registration: ' <spring:message code="landingPage.register" />',
			userName: ' <spring:message code="landingPage.login.userName" />',
			email: ' <spring:message code="landingPage.register.eMail" />',
			emailExists: ' <spring:message code="landingPage.register.emailExists" />',
			userNameExists: ' <spring:message code="landingPage.register.userNameExists" />',
			userAndEmailExists: ' <spring:message code="landingPage.register.userAndEmailExists" />',
			enterValidEmailAddress: ' <spring:message code="landingPage.register.enterValidEmailAddress" />',
			userNameRequired: ' <spring:message code="landingPage.register.userNameRequired" />',
			emailRequired: ' <spring:message code="landingPage.register.emailRequired" />',
			userAndEmailAndTypeRequired: ' <spring:message code="landingPage.register.userAndEmailAndTypeRequired" />',
			userCreatedSuccessfully: ' <spring:message code="landingPage.register.userCreatedSuccessfully" />',
			userCreationFailed: ' <spring:message code="landingPage.register.userCreationFailed" />',
			userTypeRequired: ' <spring:message code="landingPage.register.userTypeRequired" />'
		};
</script>
