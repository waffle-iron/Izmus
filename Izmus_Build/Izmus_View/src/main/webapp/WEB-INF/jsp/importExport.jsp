<%@ include file="fragments/frame.jsp"%>
<izmus-nav-bar ng-app="importExportApp"
	initial-selected-item=' <spring:message code="navBar.menu.adminMenu.importExport" />'>
	<import-export
				flex
				layout="column">
	</import-export>
</izmus-nav-bar>
<script
	src="<c:url value="/views/import-export/js/import-export.ctrl.js" />"></script>
<script
	src="<c:url value="/views/import-export/js/import-export.dir.js" />"></script>
<script
	src="<c:url value="/views/import-export/js/import-export.ser.js" />"></script>
<script
	src="<c:url value="/views/import-export/js/import-startup-csv.dir.js" />"></script>
<!--File Upload-->
<script
	src="<c:url value="/views/core/file-upload/js/ng-file-upload-shim.js" />"></script>
<script
	src="<c:url value="/views/core/file-upload/js/ng-file-upload.js" />"></script>
<script>
	var lang = {
			dropFile:' <spring:message code="navBar.menu.adminMenu.importExport.dropFile" />',
			importStartupCsv: ' <spring:message code="navBar.menu.adminMenu.importExport.importStartupCsv" />',
			processedSuccessfully: ' <spring:message code="navBar.menu.adminMenu.importExport.processedSuccessfully" />',
			processFailed: ' <spring:message code="navBar.menu.adminMenu.importExport.processFailed" />'
	};
</script>