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
<meta name="theme-color" content="#1f6086">
<!-- Angular Material style sheet -->
<link
	href="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/angular-material/1.0.6/angular-material.min.css" />"
	rel="stylesheet" />
<link
	href="<c:url value="https://fonts.googleapis.com/icon?family=Material+Icons" />"
	rel="stylesheet" />
<link
	href="<c:url value="/views-public/core/leaflet/css/leaflet.css" />"
	rel="stylesheet" />
<link
	href="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/animate.css/3.5.1/animate.min.css" />"
	rel="stylesheet" />
<!-- icon in the highest resolution we need it for -->
<link rel="icon" sizes="192x192" href="/views-public/core/logo/logo192x192.png">
<!-- reuse same icon for Safari -->
<link rel="apple-touch-icon" href="/views-public/core/logo/logo192x192.png">
<!-- multiple icons for IE -->
<meta name="msapplication-square310x310logo" content="/views-public/core/logo/logo192x192.png">
</head>
<body layout="colum">
	<izmus-landing-page 
		ng-app="izmusLandingPageApp"
		flex
		layout="column">
	</izmus-landing-page>
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
		src="<c:url value="/views-public/landing-page/js/izmus-landing-page.dir.js" />"></script>
	<script
		src="<c:url value="/views-public/landing-page/js/izmus-login-page.dir.js" />"></script>
	<script
		src="<c:url value="/views-public/landing-page/js/izmus-registration-page.dir.js" />"></script>
	<script
		src="<c:url value="/views-public/landing-page/js/izmus-about-page.dir.js" />"></script>
	<script
		src="<c:url value="/views-public/landing-page/js/language-menu.dir.js" />"></script>
	<script
		src="<c:url value="/views-public/landing-page/js/contact-us.ser.js" />"></script>
	<script
		src="<c:url value="/views-public/core/leaflet/leaflet.js" />"></script>
	<script
		src="<c:url value="/views-public/landing-page/js/location-map.dir.js" />"></script>
	<script
		src="<c:url value="/views-public/core/idle/js/angular-idle.min.js" />"></script>
	<script type="text/javascript">
		var globalAttr = {
			direction: '${dir}',
			sessionToken:'${_csrf.token}',
			csrfParameterName: '${_csrf.parameterName}'
		};
		var lang = {
			loginSectionHeader: ' <spring:message code="landingPage.login.loginSectionHeader" />',
			loginSectionContent: ' <spring:message code="landingPage.login.loginSectionContent" />',
			about: ' <spring:message code="landingPage.about" />',
			loginTitle: ' <spring:message code="landingPage.login.title" />',
			contactUs: ' <spring:message code="landingPage.contactUs" />',
			homePage: ' <spring:message code="landingPage.homePage" />',
			register: ' <spring:message code="landingPage.register.title" />',
			userName: ' <spring:message code="landingPage.login.userName" />',
			password: ' <spring:message code="landingPage.login.password" />',
			go: ' <spring:message code="landingPage.login.go" />',
			ok: ' <spring:message code="navBar.menu.user.ok" />',
			send: ' <spring:message code="landingPage.send" />',
			cancel: ' <spring:message code="navBar.menu.user.cancel" />',
			registration: ' <spring:message code="landingPage.register" />',
			email: ' <spring:message code="landingPage.register.eMail" />',
			badCredentials: ' <spring:message code="landingPage.login.badCredentials" />',
			emailExists: ' <spring:message code="landingPage.register.emailExists" />',
			userNameExists: ' <spring:message code="landingPage.register.userNameExists" />',
			userAndEmailExists: ' <spring:message code="landingPage.register.userAndEmailExists" />',
			enterValidEmailAddress: ' <spring:message code="landingPage.register.enterValidEmailAddress" />',
			userNameRequired: ' <spring:message code="landingPage.register.userNameRequired" />',
			emailRequired: ' <spring:message code="landingPage.register.emailRequired" />',
			aboutSectionHeaderText: ' <spring:message code="landingPage.about.aboutSectionHeaderText" />',
			izmusSectionHeader: ' <spring:message code="landingPage.about.izmusSectionHeader" />',
			izmusSectionContent: ' <spring:message code="landingPage.about.izmusSectionContent" />',
			visionSectionHeader: ' <spring:message code="landingPage.about.visionSectionHeader" />',
			visionSectionContent: ' <spring:message code="landingPage.about.visionSectionContent" />',
			missionSectionHeader: ' <spring:message code="landingPage.about.missionSectionHeader" />',
			missionSectionContentOne: ' <spring:message code="landingPage.about.missionSectionContentOne" />',
			missionSectionContentTwo: ' <spring:message code="landingPage.about.missionSectionContentTwo" />',
			missionSectionContentThree: ' <spring:message code="landingPage.about.missionSectionContentThree" />',
			aboutSectionTwoHeaderText: ' <spring:message code="landingPage.about.aboutSectionTwoHeaderText" />',
			aboutSectionTwoHeaderContent: ' <spring:message code="landingPage.about.aboutSectionTwoHeaderContent" />',
			singaporeLocation: ' <spring:message code="landingPage.about.singaporeLocation" />',
			singaporeLocationContent: ' <spring:message code="landingPage.about.singaporeLocationContent" />',
			singaporeBusiness: ' <spring:message code="landingPage.about.singaporeBusiness" />',
			singaporeBusinessContent: ' <spring:message code="landingPage.about.singaporeBusinessContent" />',
			singaporePopulation: ' <spring:message code="landingPage.about.singaporePopulation" />',
			singaporePopulationContent: ' <spring:message code="landingPage.about.singaporePopulationContent" />',
			smartNation: ' <spring:message code="landingPage.about.smartNation" />',
			smartNationContent: ' <spring:message code="landingPage.about.smartNationContent" />',
			name: ' <spring:message code="landingPage.name" />',
			nameRequired: ' <spring:message code="landingPage.nameRequired" />',
			subject: ' <spring:message code="landingPage.subject" />',
			message: ' <spring:message code="landingPage.message" />',
			address: ' <spring:message code="landingPage.address" />',
			messageNotSent: ' <spring:message code="landingPage.messageNotSent" />',
			sendSuccess: ' <spring:message code="landingPage.sendSuccess" />',
			english: ' <spring:message code="navBar.menu.user.language.english" />',
			hebrew:' <spring:message code="navBar.menu.user.language.hebrew" />',
			aboutBottom: ' <spring:message code="landingPage.about.bottomAbout" />',
			news: ' <spring:message code="landingPage.news" />',
			badContactInfo: ' <spring:message code="landingPage.badContactInfo" />',
			theTeam: ' <spring:message code="landingPage.theTeam" />',
			liorNevo: ' <spring:message code="landingPage.liorNevo" />',
			liorTitle: ' <spring:message code="landingPage.liorTitle" />',
			joyPhua: " <spring:message code='landingPage.joyPhua' />",
			joyTitle: ' <spring:message code="landingPage.joyTitle" />',
			rabeaBader: ' <spring:message code="landingPage.rabeaBader" />',
			rabeaTitle: ' <spring:message code="landingPage.rabeaTitle" />',
			israeliStartupsHeader: ' <spring:message code="landingPage.about.israeliStartupsHeader" />',
			israeliStartupsContentOne: ' <spring:message code="landingPage.about.israeliStartupsContentOne" />',
			israeliStartupsContentTwo: ' <spring:message code="landingPage.about.israeliStartupsContentTwo" />',
			israeliStartupsContentThree: ' <spring:message code="landingPage.about.israeliStartupsContentThree" />',
			israeliStartupsContentFour: ' <spring:message code="landingPage.about.israeliStartupsContentFour" />',
			investorBusinessHeader: ' <spring:message code="landingPage.about.investorBusinessHeader" />',
			investorBusinessContentOne: ' <spring:message code="landingPage.about.investorBusinessContentOne" />',
			investorBusinessContentTwo: ' <spring:message code="landingPage.about.investorBusinessContentTwo" />',
			investorBusinessContentThree: ' <spring:message code="landingPage.about.investorBusinessContentThree" />',
			establishedSectionHeader: ' <spring:message code="landingPage.about.establishedSectionHeader" />',
			establishedSectionContent: ' <spring:message code="landingPage.about.establishedSectionContent" />',
			resourcefulSectionHeader: ' <spring:message code="landingPage.about.resourcefulSectionHeader" />',
			resourcefulSectionContent: ' <spring:message code="landingPage.about.resourcefulSectionContent" />',
			strategicSectionHeader: ' <spring:message code="landingPage.about.strategicSectionHeader" />',
			strategicSectionContent: ' <spring:message code="landingPage.about.strategicSectionContent" />'
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