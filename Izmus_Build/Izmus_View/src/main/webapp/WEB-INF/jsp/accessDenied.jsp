<%@ include file="fragments/frame.jsp"%>
<izmus-nav-bar ng-app="accessDeniedApp">
		<izmus-access-denied
				flex
				layout="column" 
				access-denied-message=' <spring:message code="accessDenied.message" />'
				access-denied=' <spring:message code="accessDenied" />'>
		</izmus-access-denied>
</izmus-nav-bar>
<script
	src="<c:url value="/views/access-denied/js/access-denied.ctrl.js" />"></script>
<script
	src="<c:url value="/views/access-denied/js/access-denied.dir.js" />"></script>
