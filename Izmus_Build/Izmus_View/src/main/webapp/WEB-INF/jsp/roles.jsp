<%@ include file="fragments/frame.jsp"%>
<investit-nav-bar ng-app="rolesManagementApp"
	initial-selected-item=' <spring:message code="navBar.menu.adminMenu.roles" />'>
	<investit-roles-management
				flex
				layout="column" 
				roles-management=' <spring:message code="navBar.menu.adminMenu.roles.rolesManagement" />'
				roles-list=' <spring:message code="navBar.menu.adminMenu.roles.rolesList" />'
				cancel=' <spring:message code="navBar.menu.adminMenu.roles.cancel" />'
				ok=' <spring:message code="navBar.menu.adminMenu.roles.ok" />'
				available-permissions=' <spring:message code="navBar.menu.adminMenu.roles.availablePermissions" />'
				user-roles=' <spring:message code="navBar.menu.adminMenu.roles.userRoles" />'
				new-role=' <spring:message code="navBar.menu.adminMenu.roles.newRole" />'
				role-name=' <spring:message code="navBar.menu.adminMenu.roles.roleName" />'	>
	</investit-roles-management>
</investit-nav-bar>
<script
	src="<c:url value="/views/roles/js/roles.ctrl.js" />"></script>
<script
	src="<c:url value="/views/roles/js/roles-management.dir.js" />"></script>
<script
	src="<c:url value="/views/roles/js/roles-management-data.ser.js" />"></script>
<script
	src="<c:url value="/views/roles/js/roles-table.dir.js" />"></script>
<script
	src="<c:url value="/views/roles/js/roles-preview.dir.js" />"></script>
