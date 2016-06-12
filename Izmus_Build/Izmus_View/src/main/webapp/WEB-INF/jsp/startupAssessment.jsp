<%@ include file="fragments/frame.jsp"%>
<izmus-nav-bar ng-app="startupAssessmentApp"
		initial-selected-item=' <spring:message code="navBar.menu.startupAssessment" />'>
		<izmus-startup-assessment
				flex
				layout="column">
		</izmus-startup-assessment>
</izmus-nav-bar>
<script
	src="<c:url value="/views/startup-assessment/js/startup-assessment.ctrl.js" />"></script>
<script
	src="<c:url value="/views/startup-assessment/js/startup-assessment.dir.js" />"></script>
<script
	src="<c:url value="/views/startup-assessment/js/startup-data.ser.js" />"></script>
<script
	src="<c:url value="/views/startup-assessment/js/startup-list.dir.js" />"></script>
<script
	src="<c:url value="/views/startup-assessment/js/startup-detail.dir.js" />"></script>
<script
	src="<c:url value="/views/startup-assessment/js/startup-contacts.dir.js" />"></script>
<script
	src="<c:url value="/views/startup-assessment/js/startup-scorecard.dir.js" />"></script>
<script
	src="<c:url value="/views/startup-assessment/js/startup-additional-documents.dir.js" />"></script>
<script
	src="<c:url value="/views/startup-assessment/js/startup-meetings.dir.js" />"></script>
<script
	src="<c:url value="/views/core/file-upload/js/ng-file-upload-shim.js" />"></script>
<script
	src="<c:url value="/views/core/file-upload/js/ng-file-upload.js" />"></script>
<script>
	var lang = {
			startupAssessmentProcess: ' <spring:message code="navBar.menu.startupAssessment.startupAssessmnetProcess" />',
			startups:' <spring:message code="navBar.menu.startupAssessment.startups" />',
			cancel:' <spring:message code="navBar.menu.startupAssessment.cancel" />',
			ok:' <spring:message code="navBar.menu.startupAssessment.ok" />',
			startupName:' <spring:message code="navBar.menu.startupAssessment.startupName" />',
			newStartup:' <spring:message code="navBar.menu.startupAssessment.newStartup" />',
			address:' <spring:message code="navBar.menu.startupAssessment.address" />',
			officePhone:' <spring:message code="navBar.menu.startupAssessment.officePhone" />',
			generalInfo:' <spring:message code="navBar.menu.startupAssessment.generalInfo" />',
			generalFinancials:' <spring:message code="navBar.menu.startupAssessment.generalFinancials" />',
			requestedFunds:' <spring:message code="navBar.menu.startupAssessment.requestedFunds" />',
			achivedFunds:' <spring:message code="navBar.menu.startupAssessment.achivedFunds" />',
			startupOwnValuation:' <spring:message code="navBar.menu.startupAssessment.startupOwnValuation" />',
			izmusValuation:' <spring:message code="navBar.menu.startupAssessment.izmusValuation" />',
			sector:' <spring:message code="navBar.menu.startupAssessment.sector" />',
			mainInfo:' <spring:message code="navBar.menu.startupAssessment.mainInfo" />',
			contacts:' <spring:message code="navBar.menu.startupAssessment.contacts" />',
			search:' <spring:message code="navBar.menu.startupAssessment.search" />',
			miscellaneous:' <spring:message code="navBar.menu.startupAssessment.miscellaneous" />',
			site:' <spring:message code="navBar.menu.startupAssessment.site" />',
			firstName:' <spring:message code="navBar.menu.startupAssessment.firstName" />',
			lastName:' <spring:message code="navBar.menu.startupAssessment.lastName" />',
			email:' <spring:message code="navBar.menu.startupAssessment.email" />',
			mobilePhone:' <spring:message code="navBar.menu.startupAssessment.mobilePhone" />',
			position:' <spring:message code="navBar.menu.startupAssessment.position" />',
			scoreCard:' <spring:message code="navBar.menu.startupAssessment.scoreCard" />',
			start:' <spring:message code="navBar.menu.startupAssessment.start" />',
			measurementProgress:' <spring:message code="navBar.menu.startupAssessment.measurementProgress" />',
			assessment:' <spring:message code="navBar.menu.startupAssessment.assessment" />',
			score:' <spring:message code="navBar.menu.startupAssessment.score" />',
			giveScore:' <spring:message code="navBar.menu.startupAssessment.giveScore" />',
			scorePattern:' <spring:message code="navBar.menu.startupAssessment.scorePattern" />',
			inadequate:' <spring:message code="navBar.menu.startupAssessment.measurements.answers.inadequate" />',
			belowAverage:' <spring:message code="navBar.menu.startupAssessment.measurements.answers.belowAverage" />',
			average:' <spring:message code="navBar.menu.startupAssessment.measurements.answers.average" />',
			aboveAverage:' <spring:message code="navBar.menu.startupAssessment.measurements.answers.aboveAverage" />',
			outstanding:' <spring:message code="navBar.menu.startupAssessment.measurements.answers.outstanding" />',
			saveStartup:' <spring:message code="navBar.menu.startupAssessment.saveStartup" />',
			addContact:' <spring:message code="navBar.menu.startupAssessment.addContact" />',
			addScoreCard:' <spring:message code="navBar.menu.startupAssessment.addScoreCard" />',
			selectAScoreCard:' <spring:message code="navBar.menu.startupAssessment.selectAScoreCard" />',
			exportScoreCardReport:' <spring:message code="navBar.menu.startupAssessment.exportScoreCardReport" />',
			finalScore:' <spring:message code="navBar.menu.startupAssessment.finalScore" />',
			date:' <spring:message code="navBar.menu.startupAssessment.date" />',
			emailScoreCardReport:' <spring:message code="navBar.menu.startupAssessment.emailScoreCardReport" />',
			enterEmails:' <spring:message code="navBar.menu.startupAssessment.enterEmails" />',
			onlyEmails:' <spring:message code="navBar.menu.startupAssessment.onlyEmails" />',
			additionalDocuments:' <spring:message code="navBar.menu.startupAssessment.additionalDocuments" />',
			uploadFile:' <spring:message code="navBar.menu.startupAssessment.uploadFile" />',
			meetings:' <spring:message code="navBar.menu.startupAssessment.meetings" />',
			addMeeting:' <spring:message code="navBar.menu.startupAssessment.addMeeting" />',
			enterDate:' <spring:message code="navBar.menu.startupAssessment.enterDate" />',
			dropPdf:' <spring:message code="navBar.menu.startupAssessment.dropPdf" />'
	};
</script>