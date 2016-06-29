<%@ include file="fragments/frame.jsp"%>
<izmus-nav-bar ng-app="contactsApp"
	initial-selected-item=' <spring:message code="navBar.menu.assessorsMenu.contacts" />'>
	<contacts-dashboard
				flex
				layout="column">
	</contacts-dashboard>
</izmus-nav-bar>
<script
	src="<c:url value="/views/contacts/js/contacts.ctrl.js" />"></script>
<script
	src="<c:url value="/views/contacts/js/contacts.dir.js" />"></script>
<script
	src="<c:url value="/views/contacts/js/contacts.ser.js" />"></script>
<script
	src="<c:url value="/views/contacts/js/investor-contact.ser.js" />"></script>
<script
	src="<c:url value="/views/contacts/js/finder-contact.ser.js" />"></script>
<script
	src="<c:url value="/views/contacts/js/general-contact.ser.js" />"></script>
<script>
	var lang = {
			search:' <spring:message code="navBar.menu.startupAssessment.search" />',
			startupContacts: ' <spring:message code="navBar.menu.assessorsMenu.contacts.startupContacts" />',
			cancel:' <spring:message code="navBar.menu.startupAssessment.cancel" />',
			ok:' <spring:message code="navBar.menu.startupAssessment.ok" />',
			contactTypeRequired: ' <spring:message code="navBar.menu.assessorsMenu.contacts.contactTypeRequired" />',
			investorContacts: ' <spring:message code="navBar.menu.assessorsMenu.contacts.investorContacts" />',
			generalContacts: ' <spring:message code="navBar.menu.assessorsMenu.contacts.generalContacts" />',
			finderContacts: ' <spring:message code="navBar.menu.assessorsMenu.contacts.finderContacts" />',
			saveFail:' <spring:message code="navBar.menu.assessorsMenu.contacts.saveFail" />',
			saveSuccess:' <spring:message code="navBar.menu.assessorsMenu.contacts.saveSuccess" />',
			firstName:' <spring:message code="navBar.menu.startupAssessment.firstName" />',
			lastName:' <spring:message code="navBar.menu.startupAssessment.lastName" />',
			email:' <spring:message code="navBar.menu.startupAssessment.email" />',
			mobilePhone:' <spring:message code="navBar.menu.startupAssessment.mobilePhone" />',
			position:' <spring:message code="navBar.menu.startupAssessment.position" />',
			officePhone:' <spring:message code="navBar.menu.startupAssessment.officePhone" />',
			founded: ' <spring:message code="navBar.menu.assessorsMenu.contacts.founded" />',
			assetsUnderManagement: ' <spring:message code="navBar.menu.assessorsMenu.contacts.assetsUnderManagement" />',
			averageInvestmentSize:' <spring:message code="navBar.menu.assessorsMenu.contacts.averageInvestmentSize" />',
			noPastInvestments: ' <spring:message code="navBar.menu.assessorsMenu.contacts.noPastInvestments" />',
			noPastExits: ' <spring:message code="navBar.menu.assessorsMenu.contacts.noPastExits" />',
			howWeMet: ' <spring:message code="navBar.menu.assessorsMenu.contacts.howWeMet" />',
			generalInfo: ' <spring:message code="navBar.menu.assessorsMenu.contacts.generalInfo" />',
			save: ' <spring:message code="navBar.menu.assessorsMenu.contacts.save" />',
			company: ' <spring:message code="navBar.menu.assessorsMenu.contacts.company" />',
			companyName: ' <spring:message code="navBar.menu.assessorsMenu.contacts.companyName" />',
			focusAreas: ' <spring:message code="navBar.menu.assessorsMenu.contacts.focusAreas" />',
			additionalFocusAreas: ' <spring:message code="navBar.menu.assessorsMenu.contacts.additionalFocusAreas" />',
			additionalInvestmentStages: ' <spring:message code="navBar.menu.assessorsMenu.contacts.additionalInvestmentStages" />',
			investmentStages: ' <spring:message code="navBar.menu.assessorsMenu.contacts.investmentStages" />',
			assosiatedFinders: ' <spring:message code="navBar.menu.assessorsMenu.contacts.assosiatedFinders" />',
			notes: ' <spring:message code="navBar.menu.assessorsMenu.contacts.notes" />'
	};
</script>