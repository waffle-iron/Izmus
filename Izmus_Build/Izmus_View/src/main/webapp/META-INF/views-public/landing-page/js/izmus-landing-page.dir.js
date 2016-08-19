var idle = 5 * 60;
var timeout = 15 * 60;
var heartbeat = 5 * 60;
angular.module('izmusLandingPageApp', [ 'ngMaterial', 'ngAnimate', 'ngMessages', 'ngIdle','angular-timeline' ]);
/*----------------------------------------------------------------------------------------------------*/
/**
 * Directive
 */
angular
		.module('izmusLandingPageApp')
		.directive(
				'izmusLandingPage',
				['contactUsDialog','Idle','heartbeat', function(contactUsDialog, Idle, heartbeat) {
							return {
								restrict : 'E',
								templateUrl : '/views-public/landing-page/templates/izmus-landing-page.html',
								scope : {
									
								},
								controller : ['$scope',function($scope) {
									$scope.globalAttr = globalAttr;
									$scope.lang = lang;
									$scope.screenShowing = "about";
									/*----------------------------------------------------------------------------------------------------*/
									$scope.moveToPage = function(newPage){
										$scope.screenShowing = newPage;
									}
									/*----------------------------------------------------------------------------------------------------*/
								    $scope.$on('IdleTimeout', function() {
								        window.location = "/";
								    });
								    /*----------------------------------------------------------------------------------------------------*/
								    $scope.$on('Keepalive', function() {
								        heartbeat();
								    });
								}],
								link : function(scope, elem, attr) {
									/*----------------------------------------------------------------------------------------------------*/
									scope.contactDialog = function(ev){
										contactUsDialog(ev, elem);
									}
								}
							}
						} ]);
/*----------------------------------------------------------------------------------------------------*/
/**
 * Factories
 */
angular.module('izmusLandingPageApp').factory('izmusLogin',
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
angular.module('izmusLandingPageApp').factory('checkUserCredentials',
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
angular.module('izmusLandingPageApp').factory('checkUserName',
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
angular.module('izmusLandingPageApp').factory('checkUserAndEmail',
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
angular.module('izmusLandingPageApp').factory('izmusRegistration',
		[ '$q', '$http', '$httpParamSerializer',function($q, $http, $httpParamSerializer) {
			return function(userName, email) {
				return $q(function(resolve, reject) {
					$http({
						method : 'POST',
						url : '/api/Users/Register',
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
angular.module('izmusLandingPageApp').factory('heartbeat',
		[ '$q', '$http', function($q, $http) {
			return function(userAvatar) {
				return $q(function(resolve, reject) {
					$http({
						method : 'POST',
						url : '/api/Heartbeat',
						headers: {
					        'Content-Type': "application/x-www-form-urlencoded; charset=UTF-8",
					        'Upgrade-Insecure-Requests': "1",
					        'X-CSRF-TOKEN': globalAttr.sessionToken
					    },
					}).then(function successCallback(response) {
						if (!response.data.result){
							window.location = "/";
						}
					}, function errorCallback(response) {
						window.location = "/";
					});
				})
			}
		} ]);
/*----------------------------------------------------------------------------------------------------*/
/**
 * Configuration
 */
angular.module('izmusLandingPageApp').config(
		[
				'$mdThemingProvider',
				'$mdIconProvider',
				function($mdThemingProvider, $mdIconProvider) {
					var customPrimary = {
							'50': '#ccd8e3',
					        '100': '#bccbda',
					        '200': '#abbfd1',
					        '300': '#9bb2c8',
					        '400': '#246f9b',
					        '500': '#1f6086',
					        '600': '#1a5171',
					        '700': '#15425d',
					        '800': '#113348',
					        '900': '#0c2533',
					        'A100': '#63afdb',
					        'A200': '#78b9e0',
					        'A400': '#8cc4e5',
					        'A700': '#07161e'
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
angular.module('izmusLandingPageApp').config(['IdleProvider','KeepaliveProvider','TitleProvider', function(IdleProvider, KeepaliveProvider, TitleProvider) {
    // configure Idle settings
    IdleProvider.idle(idle); // in seconds
    IdleProvider.timeout(timeout); // in seconds
    KeepaliveProvider.interval(heartbeat); // in seconds
    TitleProvider.enabled(false);
}])
.run(['Idle', function(Idle){
    // start watching when the app runs. also starts the Keepalive service by default.
    Idle.watch();
}]);