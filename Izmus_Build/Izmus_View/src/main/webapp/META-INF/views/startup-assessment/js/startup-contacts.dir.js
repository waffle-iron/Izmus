angular.module('startupAssessmentApp').directive('investitStartupContacts', ['avatarDialog',
                        function(avatarDialog) {
	return {
		restrict : 'E',
		require: '^^investitStartupAssessment',
		templateUrl : '/views/startup-assessment/templates/startup-contacts.html',
		scope: {
			selectedStartup: '='
		},
		controller : ['$scope',function($scope) {
			$scope.globalAttr = globalAttr;
			$scope.lang = lang;
			/*----------------------------------------------------------------------------------------------------*/
			$scope.contactAttributes = {
					firstName: lang.firstName,
					lastName: lang.lastName,
					mobilePhone: lang.mobilePhone,
					officePhone: lang.officePhone,
					email: lang.email,
					position: lang.position
			};
			/*----------------------------------------------------------------------------------------------------*/
			$scope.deleteContact = function (index){
				$scope.selectedStartup.contacts.splice(index, 1);
			};
			/*----------------------------------------------------------------------------------------------------*/
			$scope.changeContactAvatar = function($event, contact){
				avatarDialog($event, function(croppedImage){
					contact.contactAvatar = croppedImage;
				});
			};
		}],
		link : function(scope, elem, attr, parentCtrl) {
			
		}
	}
} ]);