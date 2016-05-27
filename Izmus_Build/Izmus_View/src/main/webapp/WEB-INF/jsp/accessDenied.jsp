<%@ include file="fragments/frame.jsp"%>
<investit-nav-bar ng-app="accessDeniedApp">
		<investit-access-denied
				flex
				layout="column" 
				access-denied-message=' <spring:message code="accessDenied.message" />'
				access-denied=' <spring:message code="accessDenied" />'>
		</investit-access-denied>
</investit-nav-bar>
<script
	src="<c:url value="/views/access-denied/js/access-denied.ctrl.js" />"></script>
<script
	src="<c:url value="/views/access-denied/js/access-denied.dir.js" />"></script>
