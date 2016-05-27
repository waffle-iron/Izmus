angular.module('investitLandingPageApp', [ 'ngMaterial', 'ngAnimate', 'ngMessages']);
/*----------------------------------------------------------------------------------------------------*/
/**
 * Directive
 */
angular
		.module('investitLandingPageApp')
		.directive(
				'investitLandingPage',
				[function() {
							return {
								restrict : 'E',
								templateUrl : '/views-public/landing-page/templates/investit-landing-page.html',
								scope : {
									
								},
								controller : ['$scope',function($scope) {
									$scope.globalAttr = globalAttr;
									$scope.lang = lang;
									$scope.screenShowing = {
											login: true,
											register: false,
											about: false,
											contactUs: false
									};
									/*----------------------------------------------------------------------------------------------------*/
									$scope.login = function(){
										$scope.flipTo("login");
									}
									/*----------------------------------------------------------------------------------------------------*/
									$scope.register = function(){
										$scope.flipTo("register");
									}
									/*----------------------------------------------------------------------------------------------------*/
									$scope.flipTo = function(valueShowing){
										for (var screen in $scope.screenShowing) {
										    if ($scope.screenShowing.hasOwnProperty(screen)) {
										        if (screen == valueShowing){
										        	$scope.screenShowing[screen] = true;
										        }
										        else {
										        	$scope.screenShowing[screen] = false;
										        }
										    }
										}
									}
								}],
								link : function(scope, elem, attr) {

								}
							}
						} ]);
/*----------------------------------------------------------------------------------------------------*/
/**
 * Factories
 */
angular.module('investitLandingPageApp').factory('investitLogin',
		[ '$q', '$http', '$httpParamSerializer',function($q, $http, $httpParamSerializer) {
			return function(userName, password) {
				return $q(function(resolve, reject) {
					$http({
						method : 'POST',
						url : '/Login',
						headers: {
					        'Content-Type': "application/x-www-form-urlencoded; charset=UTF-8",
					        'Upgrade-Insecure-Requests': "1",
					        'X-CSRF-TOKEN': globalAttr.sessionToken
					    },
					    data : $httpParamSerializer({
					    	_csrf: globalAttr.sessionToken,
					    	userName: userName,
					    	password: password
					    })
					}).then(function successCallback(response) {
						window.location = "/";
					}, function errorCallback(error) {
						window.location = "/";
					});
				})
			}
		} ]);
angular.module('investitLandingPageApp').factory('checkUserCredentials',
		[ '$q', '$http', function($q, $http) {
			return function(userName, password) {
				return $q(function(resolve, reject) {
					$http({
						method : 'GET',
						url : '/api/Users/CheckUserCredentials',
					    params : {
					    	userName: userName,
					    	password: password
					    }
					}).then(function successCallback(response) {
						resolve(response.data);
					}, function errorCallback(error) {
						window.location = "/";
					});
				})
			}
		} ]);
angular.module('investitLandingPageApp').factory('checkUserName',
		[ '$q', '$http', function($q, $http) {
			return function(userName, password) {
				return $q(function(resolve, reject) {
					$http({
						method : 'GET',
						url : '/api/Users/CheckUserCredentials',
					    params : {
					    	userName: userName,
					    	password: password
					    }
					}).then(function successCallback(response) {
						resolve(response.data);
					}, function errorCallback(error) {
						window.location = "/";
					});
				})
			}
		} ]);
angular.module('investitLandingPageApp').factory('checkUserAndEmail',
		[ '$q', '$http', function($q, $http) {
			return function(userName, email) {
				return $q(function(resolve, reject) {
					$http({
						method : 'GET',
						url : '/api/Users/CheckUserAndEmail',
					    params : {
					    	userName: userName,
					    	email: email
					    }
					}).then(function successCallback(response) {
						resolve(response.data);
					}, function errorCallback(error) {
						window.location = "/";
					});
				})
			}
		} ]);
angular.module('investitLandingPageApp').factory('investitRegistration',
		[ '$q', '$http', '$httpParamSerializer',function($q, $http, $httpParamSerializer) {
			return function(userName, email) {
				return $q(function(resolve, reject) {
					$http({
						method : 'POST',
						url : '/api/Clients/Register',
						headers: {
					        'Content-Type': "application/x-www-form-urlencoded; charset=UTF-8",
					        'Upgrade-Insecure-Requests': "1",
					        'X-CSRF-TOKEN': globalAttr.sessionToken
					    },
					    data : $httpParamSerializer({
					    	_csrf: globalAttr.sessionToken,
					    	userName: userName,
					    	email: email
					    })
					}).then(function successCallback(response) {
						window.location = "/";
					}, function errorCallback(error) {
						window.location = "/";
					});
				})
			}
		} ]);
/*----------------------------------------------------------------------------------------------------*/
/**
 * Configuration
 */
angular.module('investitLandingPageApp').config(
		[
				'$mdThemingProvider',
				'$mdIconProvider',
				function($mdThemingProvider, $mdIconProvider) {
					var customPrimary = {
					        '50': '#b9cdd8',
					        '100': '#a9c1cf',
					        '200': '#98b5c6',
					        '300': '#88aabd',
					        '400': '#779eb4',
					        '500': '#6792AB',
					        '600': '#5886a0',
					        '700': '#4f7890',
					        '800': '#466a7f',
					        '900': '#3d5d6f',
					        'A100': '#cad9e1',
					        'A200': '#dae4eb',
					        'A400': '#eaf0f4',
					        'A700': '#344f5e'
					    };
					    $mdThemingProvider
					        .definePalette('customPrimary', 
					                        customPrimary);

					    var customAccent = {
					        '50': '#b2cedf',
					        '100': '#a0c3d7',
					        '200': '#8eb8d0',
					        '300': '#7cadc8',
					        '400': '#6aa1c1',
					        '500': '#5896B9',
					        '600': '#498aae',
					        '700': '#427c9c',
					        '800': '#3a6d8a',
					        '900': '#325f78',
					        'A100': '#c4dae6',
					        'A200': '#d6e5ee',
					        'A400': '#e8f0f5',
					        'A700': '#2b5166'
					    };
					    $mdThemingProvider
					        .definePalette('customAccent', 
					                        customAccent);

					    var customWarn = {
					        '50': '#ffb280',
					        '100': '#ffa266',
					        '200': '#ff934d',
					        '300': '#ff8333',
					        '400': '#ff741a',
					        '500': '#ff6400',
					        '600': '#e65a00',
					        '700': '#cc5000',
					        '800': '#b34600',
					        '900': '#993c00',
					        'A100': '#ffc199',
					        'A200': '#ffd1b3',
					        'A400': '#ffe0cc',
					        'A700': '#803200'
					    };
					    $mdThemingProvider
					        .definePalette('customWarn', 
					                        customWarn);

					    var customBackground = {
					        '50': '#ffffff',
					        '100': '#fefefe',
					        '200': '#ecf3f6',
					        '300': '#dbe7ee',
					        '400': '#c9dce6',
					        '500': '#B8D0DE',
					        '600': '#a7c4d6',
					        '700': '#95b9ce',
					        '800': '#84adc6',
					        '900': '#72a2be',
					        'A100': '#ffffff',
					        'A200': '#ffffff',
					        'A400': '#ffffff',
					        'A700': '#6196b6'
					    };
					    $mdThemingProvider
					        .definePalette('customBackground', 
					                        customBackground);

					   $mdThemingProvider.theme('default')
					       .primaryPalette('customPrimary')
					       .accentPalette('customAccent')
					       .warnPalette('customWarn')
					       .backgroundPalette('customBackground')
				} ]);