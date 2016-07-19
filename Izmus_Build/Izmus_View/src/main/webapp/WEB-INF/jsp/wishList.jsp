<%@ include file="fragments/frame.jsp"%>
<izmus-nav-bar ng-app="wishListApp"
	initial-selected-item=' <spring:message code="navBar.menu.cartMenu.wishList" />'>
	<wish-list-window
				flex
				layout="column">
	</wish-list-window>
</izmus-nav-bar>
<script
	src="<c:url value="/views/wish-list/js/wish-list.ctrl.js" />"></script>
<script
	src="<c:url value="/views/wish-list/js/wish-list.dir.js" />"></script>
<script
	src="<c:url value="/views/wish-list/js/wish-list.ser.js" />"></script>
<script>
	var lang = {
			
	};
</script>