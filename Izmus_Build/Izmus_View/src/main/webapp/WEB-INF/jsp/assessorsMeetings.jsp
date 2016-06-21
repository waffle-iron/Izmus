<%@ include file="fragments/frame.jsp"%>
<izmus-nav-bar ng-app="assessorsMeetingsApp"
	initial-selected-item=' <spring:message code="navBar.menu.assessorsMenu.assessorsMeetings" />'>
	<assessors-meetings
				flex
				layout="column">
	</assessors-meetings>
</izmus-nav-bar>
<script
	src="<c:url value="/views/assessors-meetings/js/assessors-meetings.ctrl.js" />"></script>
<script
	src="<c:url value="/views/assessors-meetings/js/assessors-meetings.dir.js" />"></script>
<script
	src="<c:url value="/views/assessors-meetings/js/assessors-meetings.ser.js" />"></script>
<script>
	var lang = {
			meetingSummary: ' <spring:message code="navBar.menu.startupAssessment.meetingSummary" />',
			meetingDate: ' <spring:message code="navBar.menu.startupAssessment.meetingDate" />',
			meetingTitleText: ' <spring:message code="navBar.menu.startupAssessment.meetingTitleText" />',
			meetingPurpose: ' <spring:message code="navBar.menu.startupAssessment.meetingPurpose" />',
			investment: ' <spring:message code="navBar.menu.startupAssessment.investment" />',
			busDev: ' <spring:message code="navBar.menu.startupAssessment.busDev" />',
			both: ' <spring:message code="navBar.menu.startupAssessment.both" />',
			fieldRequired: ' <spring:message code="navBar.menu.startupAssessment.fieldRequired" />',
			parties: ' <spring:message code="navBar.menu.startupAssessment.parties" />',
			startupParty: ' <spring:message code="navBar.menu.startupAssessment.startupParty" />',
			companyParty: ' <spring:message code="navBar.menu.startupAssessment.companyParty" />',
			companyIntro: ' <spring:message code="navBar.menu.startupAssessment.companyIntro" />',
			meetingFlow: ' <spring:message code="navBar.menu.startupAssessment.meetingFlow" />',
			oneSentenceSummary: ' <spring:message code="navBar.menu.startupAssessment.oneSentenceSummary" />',
			followUp: ' <spring:message code="navBar.menu.startupAssessment.followUp" />'
	};
</script>