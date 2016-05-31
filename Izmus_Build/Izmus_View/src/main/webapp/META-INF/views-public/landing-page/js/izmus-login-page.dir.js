angular
		.module('izmusLandingPageApp')
		.directive(
				'izmusLoginPage',
				[
						'izmusLogin',
						'checkUserCredentials',
						'$mdToast',
						function(izmusLogin, checkUserCredentials, $mdToast) {
							return {
								restrict : 'E',
								templateUrl : '/views-public/landing-page/templates/login-page.html',
								scope : {

								},
								controller : [
										'$scope',
										function($scope) {
											$scope.userName = '';
											$scope.password = '';
											$scope.globalAttr = globalAttr;
											$scope.lang = lang;
											/*----------------------------------------------------------------------------------------------------*/
											$scope.login = function() {
												if (!$scope.userName || !$scope.password){
													$scope.showLoginFailure($scope.lang.badCredentials);
												}
												else {
													checkUserCredentials($scope.userName,
															$scope.password).then(function(response){
																if (response == false){
																	$scope.showLoginFailure($scope.lang.badCredentials);
																}
																else {
																	izmusLogin($scope.userName,
																			$scope.password);
																}
															});
												}
											}
										} ],
								link : function(scope, elem, attr) {
									scope.showLoginFailure = function(message){
										$mdToast.show({
										      controller: 'loginFailToastCtrl',
										      templateUrl: '/views-public/landing-page/templates/login-fail-toast.html',
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

angular.module('izmusLandingPageApp').controller('loginFailToastCtrl', ['$scope','message', function($scope, message){
	$scope.message = message;
}]);