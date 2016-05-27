<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<c:set var="localeCode" value="${pageContext.response.locale}"></c:set>
<sec:authentication var="user" property="principal" />
<c:if test="${localeCode eq 'iw' || localeCode eq 'iw_IL'}">
	<c:set var="dir" value="rtl"></c:set>
</c:if>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
<meta http-equiv="x-ua-compatible" content="IE=Edge" />
<!-- Angular Material style sheet -->
<link
	href="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/angular-material/1.0.6/angular-material.min.css" />"
	rel="stylesheet" />
<link
	href="<c:url value="https://fonts.googleapis.com/icon?family=Material+Icons" />"
	rel="stylesheet" />
</head>
<body layout="colum">
	<investit-landing-page 
		ng-app="investitLandingPageApp"
		flex
		layout="column">
	</investit-landing-page>
	<!-- Angular Material requires Angular.js Libraries -->
	<script
		src="<c:url value="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.5/angular.min.js" />"></script>
	<script
		src="<c:url value="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.5/angular-animate.min.js" />"></script>
	<script
		src="<c:url value="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.5/angular-aria.min.js" />"></script>
	<script
		src="<c:url value="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.5/angular-messages.min.js" />"></script>

	<!-- Angular Material Library -->
	<script
		src="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/angular-material/1.0.6/angular-material.min.js" />"></script>
	<!-- Landing Page -->
	<script
		src="<c:url value="/views-public/landing-page/js/investit-landing-page.dir.js" />"></script>
	<script
		src="<c:url value="/views-public/landing-page/js/investit-login-page.dir.js" />"></script>
	<script
		src="<c:url value="/views-public/landing-page/js/investit-registration-page.dir.js" />"></script>
	<script type="text/javascript">
		var globalAttr = {
			direction: '${dir}',
			sessionToken:'${_csrf.token}',
			csrfParameterName: '${_csrf.parameterName}'
		};
		var lang = {
			loginSectionHeader: ' <spring:message code="landingPage.loginSectionHeader" />',
			loginSectionContent: ' <spring:message code="landingPage.loginSectionContent" />',
			about: ' <spring:message code="landingPage.about" />',
			loginTitle: ' <spring:message code="landingPage.login.title" />',
			contactUs: ' <spring:message code="landingPage.contactUs" />',
			homePage: ' <spring:message code="landingPage.homePage" />',
			register: ' <spring:message code="landingPage.register.title" />',
			userName: ' <spring:message code="landingPage.login.userName" />',
			password: ' <spring:message code="landingPage.login.password" />',
			go: ' <spring:message code="landingPage.login.go" />',
			ok: ' <spring:message code="navBar.menu.user.ok" />',
			cancel: ' <spring:message code="navBar.menu.user.cancel" />',
			registration: ' <spring:message code="landingPage.register" />',
			email: ' <spring:message code="landingPage.register.eMail" />',
			badCredentials: ' <spring:message code="landingPage.login.badCredentials" />',
			emailExists: ' <spring:message code="landingPage.register.emailExists" />',
			userNameExists: ' <spring:message code="landingPage.register.userNameExists" />',
			userAndEmailExists: ' <spring:message code="landingPage.register.userAndEmailExists" />',
			enterValidEmailAddress: ' <spring:message code="landingPage.register.enterValidEmailAddress" />',
			userNameRequired: ' <spring:message code="landingPage.register.userNameRequired" />',
			emailRequired: ' <spring:message code="landingPage.register.emailRequired" />'
		};
		<% if (request.getUserPrincipal()!= null && !request.getUserPrincipal().equals("")){
			%>
			globalAttr.user = angular.fromJson('${user}');
			globalAttr.user.userAvatar = '${user.userAvatar}';
			<%
		}%>
	</script>
</body>
</html>