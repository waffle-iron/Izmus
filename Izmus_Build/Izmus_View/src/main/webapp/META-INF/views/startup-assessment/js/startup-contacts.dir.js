angular.module('startupAssessmentApp').directive('izmusStartupContacts', ['avatarDialog',
                        function(avatarDialog) {
	return {
		restrict : 'E',
		require: '^^izmusStartupAssessment',
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
			/*----------------------------------------------------------------------------------------------------*/
			$scope.addNewContact = function(){
				if (!$scope.selectedStartup.contacts){
					$scope.selectedStartup.contacts = [];
				}
				$scope.selectedStartup.contacts.push({});
			}
		}],
		link : function(scope, elem, attr, parentCtrl) {
			
		}
	}
} ]);