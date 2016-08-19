<%@ include file="fragments/frame.jsp"%>
<izmus-nav-bar ng-app="errorApp">
	<izmus-error
				flex
				layout="column" 
				error-message='${errorMessage}'
				an-error-occurred=' <spring:message code="error.anErrorOccurred" />'>
	</izmus-error>
</izmus-nav-bar>
<script src="<c:url value="/views/error/js/error.ctrl.js" />"></script>
<script src="<c:url value="/views/error/js/error.dir.js" />"></script>