<%@ include file="fragments/frame.jsp"%>
<izmus-nav-bar ng-app="changePasswordApp">
		<izmus-change-password
			flex
			layout="column">
		</izmus-change-password>
</izmus-nav-bar>
<script
	src="<c:url value="/views/change-password/js/change-password.ctrl.js" />"></script>
<script
	src="<c:url value="/views/change-password/js/change-password.dir.js" />"></script>
<script
	src="<c:url value="/views/change-password/js/change-password.ser.js" />"></script>
<script>
	var lang = {
		changePassword: ' <spring:message code="navBar.menu.changePassword" />',
		currentPassword: ' <spring:message code="navBar.menu.changePassword.currentPassword" />',
		newPassword: ' <spring:message code="navBar.menu.changePassword.newPassword" />',
		confirmPassword: ' <spring:message code="navBar.menu.changePassword.confirmPassword" />',
		go: ' <spring:message code="landingPage.login.go" />',
		enterCurrentPassword: ' <spring:message code="navBar.menu.changePassword.enterCurrentPassword" />',
		enterNewPassword: ' <spring:message code="navBar.menu.changePassword.enterNewPassword" />',
		confirmNewPassword: ' <spring:message code="navBar.menu.changePassword.confirmNewPassword" />',
		passwordPattern: ' <spring:message code="navBar.menu.changePassword.passwordPattern" />',
		passwordLength: ' <spring:message code="navBar.menu.changePassword.passwordLength" />',
		passwordMatch: ' <spring:message code="navBar.menu.changePassword.passwordMatch" />',
		failMessage: ' <spring:message code="navBar.menu.changePassword.failMessage" />',
		successMessage: ' <spring:message code="navBar.menu.changePassword.successMessage" />'
	};
</script>