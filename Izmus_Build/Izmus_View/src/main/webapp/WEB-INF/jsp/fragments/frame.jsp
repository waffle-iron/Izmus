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
<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui">
<meta http-equiv="x-ua-compatible" content="IE=Edge" />
<meta name="theme-color" content="#1f6086">
<!-- Angular Material style sheet -->
<link
	href="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/angular-material/1.0.9/angular-material.min.css" />"
	rel="stylesheet" />
<link
	href="<c:url value="https://fonts.googleapis.com/icon?family=Material+Icons" />"
	rel="stylesheet" />
<!-- icon in the highest resolution we need it for -->
<link rel="icon" sizes="192x192" href="/views-public/core/logo/logo192x192.png">
<!-- reuse same icon for Safari -->
<link rel="apple-touch-icon" href="/views-public/core/logo/logo192x192.png">
<!-- multiple icons for IE -->
<meta name="msapplication-square310x310logo" content="/views-public/core/logo/logo192x192.png">
</head>
<body>
	<!-- Angular Material requires Angular.js Libraries -->
	<script
		src="<c:url value="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.5/angular.min.js" />"></script>
	<script
		src="<c:url value="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.5/angular-animate.min.js" />"></script>
	<script
		src="<c:url value="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.5/angular-aria.min.js" />"></script>
	<script
		src="<c:url value="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.5/angular-messages.min.js" />"></script>
	<script
		src="<c:url value="/views/core/image-crop/js/image-crop.dir.js" />"></script>
	<script
		src="<c:url value="/views/core/izmus-nav-bar/js/izmus-nav-bar.dir.js" />"></script>
	<script
		src="<c:url value="/views/core/izmus-main-window/js/izmus-main-window.dir.js" />"></script>
	<script
		src="<c:url value="/views/core/drag/izmus-drag.js" />"></script>
	<script
		src="<c:url value="/views-public/core/idle/js/angular-idle.min.js" />"></script>
	<script
		src="<c:url value="/views-public/core/full-screen/js/full-screen.js" />"></script>

	<!-- Angular Material Library -->
	<script
		src="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/angular-material/1.0.9/angular-material.min.js" />"></script>
	<script type="text/javascript">
		var globalAttr = {
			direction: '${dir}',
			user: angular.fromJson('${user}'),
			sessionToken:'${_csrf.token}',
			english: ' <spring:message code="navBar.menu.user.language.english" />',
			hebrew:' <spring:message code="navBar.menu.user.language.hebrew" />',
			logout:' <spring:message code="navBar.menu.user.logout" />',
			menu:' <spring:message code="navBar.menu" />',
			changeAvatar: ' <spring:message code="navBar.menu.user.changeAvatar" />',
			ok: ' <spring:message code="navBar.menu.user.ok" />',
			cancel: ' <spring:message code="navBar.menu.user.cancel" />',
			selectAnImage: ' <spring:message code="navBar.menu.user.selectAnImage" />',
			croppedImage: ' <spring:message code="navBar.menu.user.croppedImage" />',
			userSettings: ' <spring:message code="navBar.menu.user.settings" />',
			timeout: ' <spring:message code="navBar.timeout" />',
			fullScreen: ' <spring:message code="navBar.fullScreen" />'
		};
		globalAttr.user.userAvatar = '${user.userAvatar}';
		globalAttr.user.userRoles = '${user.userRoles}';
		var docElm = document.documentElement;
		if (docElm.requestFullscreen) {
		    docElm.requestFullscreen();
		}
		else if (docElm.mozRequestFullScreen) {
		    docElm.mozRequestFullScreen();
		}
		else if (docElm.webkitRequestFullScreen) {
		    docElm.webkitRequestFullScreen();
		}
	</script>
</body>
</html>