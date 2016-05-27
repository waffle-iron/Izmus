<%@ include file="fragments/frame.jsp"%>
<investit-nav-bar ng-app="proposalEngineApp"
		initial-selected-item=' <spring:message code="navBar.menu.proposalEngine" />'>
		<investit-proposal-engine
			flex
			layout="column">
		</investit-proposal-engine>
</investit-nav-bar>
<script
	src="<c:url value="/views/proposal-engine/js/proposal-engine.ctrl.js" />"></script>
<script
	src="<c:url value="/views/proposal-engine/js/proposal-engine.dir.js" />"></script>
<script>
	var lang = {
			
	};
</script>