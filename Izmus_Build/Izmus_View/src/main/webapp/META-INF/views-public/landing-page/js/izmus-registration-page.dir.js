angular
		.module('izmusLandingPageApp')
		.directive(
				'izmusRegistrationPage',
				[
						'izmusRegistration',
						'checkUserAndEmail',
						'$mdToast',
						function(izmusRegistration, checkUserAndEmail, $mdToast) {
							return {
								restrict : 'E',
								templateUrl : '/views-public/landing-page/templates/registration-page.html',
								scope : {

								},
								controller : [
										'$scope',
										function($scope) {
											$scope.userName = '';
											$scope.email = '';
											$scope.globalAttr = globalAttr;
											$scope.lang = lang;
											/*----------------------------------------------------------------------------------------------------*/
											$scope.register = function() {
												checkUserAndEmail($scope.userName,
														$scope.email).then(function(response){
															if (response.isEmailExists == true && response.isUserExists == true){
																$scope.showRegistrationFailure($scope.lang.userAndEmailExists);
															}
															else if (response.isEmailExists == true && response.isUserExists == false){
																$scope.showRegistrationFailure($scope.lang.emailExists);
															}
															else if (response.isEmailExists == false && response.isUserExists == true){
																$scope.showRegistrationFailure($scope.lang.userNameExists);
															}
															else {
																izmusRegistration($scope.userName,
																		$scope.email);
															}
														});
											}
										} ],
								link : function(scope, elem, attr) {
									scope.showRegistrationFailure = function(message){
										$mdToast.show({
										      controller: 'registrationToastCtrl',
										      templateUrl: '/views-public/landing-page/templates/registration-toast.html',
										      parent : angular.element(elem),
										      hideDelay: 3000,
										      position: 'top right',
										      locals: {
										    	  message: message
										      }
										    });
									}
								}
							}
						} ]);
/*----------------------------------------------------------------------------------------------------*/

angular.module('izmusLandingPageApp').controller('registrationToastCtrl', ['$scope','message', function($scope, message){
	$scope.message = message;
}]);