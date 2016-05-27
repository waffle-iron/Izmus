<%@ include file="fragments/frame.jsp"%>
<investit-nav-bar ng-app="errorApp">
	<investit-error
				flex
				layout="column" 
				error-message='${errorMessage}'
				an-error-occurred=' <spring:message code="error.anErrorOccurred" />'>
	</investit-error>
</investit-nav-bar>
<script src="<c:url value="/views/error/js/error.ctrl.js" />"></script>
<script src="<c:url value="/views/error/js/error.dir.js" />"></script>