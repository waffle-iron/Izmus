angular.module('izmusLandingPageApp').factory('contactUsDialog',
		[ '$mdMedia', '$mdDialog', 'sendContactUs','$mdToast','$timeout', function($mdMedia, $mdDialog, sendContactUs, $mdToast, $timeout) {
			return function(ev, element) {
			    /*----------------------------------------------------------------------------------------------------*/
				var contactUsCtrl = function($scope, $mdDialog) {
				    $scope.globalAttr = globalAttr;
				    $scope.lang = lang;
					/*----------------------------------------------------------------------------------------------------*/
					$scope.cancel = function() {
						$mdDialog.cancel();
					};
					$scope.dialogClass = function(){
						if ($mdMedia('xs') || $mdMedia('sm')){
							return 'mobile-dialog';
						}
						return 'desktop-dialog';
					}
					/*----------------------------------------------------------------------------------------------------*/
					$scope.send = function($event) {
						if (!$scope.name || !$scope.email){
							$scope.showFailureToast();
						}
						else {
							sendContactUs($scope.name, $scope.email, $scope.subject, $scope.message);
							$mdDialog.cancel();
						}
					};
					$scope.showFailureToast = function(){
						$mdToast.show({
						      controller: 'contactUsFailCtrl',
						      templateUrl: '/views-public/landing-page/templates/contact-us-fail.toast.html',
						      parent : angular.element(document.body),
						      hideDelay: 2000,
						      position: 'top right',
						      locals: {
						    	  message: $scope.lang.badContactInfo
						      }
						    });
						$timeout(function(){
							$mdToast.show({
							      controller: 'contactUsFailCtrl',
							      templateUrl: '/views-public/landing-page/templates/contact-us-fail.toast.html',
							      parent : angular.element(document.body),
							      hideDelay: 2000,
							      position: 'top right',
							      locals: {
							    	  message: $scope.lang.messageNotSent
							      }
							    });
						}, 2000);
					}
				}
			    /*----------------------------------------------------------------------------------------------------*/
				$mdDialog.show({
				    controller: contactUsCtrl,
				    templateUrl: '/views-public/landing-page/templates/contact-us.dialog.html',
				    parent: angular.element(document.body),
				    targetEvent: ev,
				    clickOutsideToClose: true,
				    fullscreen: false
				});
			}
		} ]);
/*----------------------------------------------------------------------------------------------------*/
angular.module('izmusLandingPageApp').factory('sendContactUs',
		[ '$q', '$http', '$httpParamSerializer', function($q, $http, $httpParamSerializer) {
			return function(name, email, subject, message) {
				return $q(function(resolve, reject) {
					$http({
						method : 'POST',
						url : '/api/ContactUs',
						headers: {
					        'Content-Type': "application/x-www-form-urlencoded; charset=UTF-8",
					        'Upgrade-Insecure-Requests': "1",
					        'X-CSRF-TOKEN': globalAttr.sessionToken
					    },
					    data : $httpParamSerializer({
					    	name: name,
					    	email: email,
					    	subject: subject,
					    	message: message
					    })
					}).then(function successCallback() {
						resolve();
					}, function errorCallback(response) {
						reject();
					});
				})
			}
		} ]);
/*----------------------------------------------------------------------------------------------------*/

angular.module('izmusLandingPageApp').controller('contactUsFailCtrl', ['$scope','message', function($scope, message){
	$scope.message = message;
}]);