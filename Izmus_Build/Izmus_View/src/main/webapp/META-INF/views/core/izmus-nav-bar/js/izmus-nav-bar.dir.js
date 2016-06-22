var idle = 5 * 60;
var timeout = 15 * 60;
var heartbeat = 5 * 60;
var popupTimeout = 200;
angular.module('izmusNavBarApp', [ 'ngMaterial', 'ngImgCrop', 'ngIdle' ]);
angular.module('izmusNavBarApp').controller('toastCtrl', ['$scope','message', function($scope, message){
	$scope.message = message;
}]);
/*----------------------------------------------------------------------------------------------------*/
/**
 * Directive
 */
angular
		.module('izmusNavBarApp')
		.directive(
				'izmusNavBar',
				[
						'loadNavbar',
						'Idle',
						'timeoutCountdownDialog',
						'heartbeat',
						function(loadNavbar, Idle, timeoutCountdownDialog, heartbeat) {
							return {
								restrict : 'E',
								templateUrl : '/views/core/izmus-nav-bar/templates/izmus-nav-bar.html',
								transclude : true,
								scope : {
									initialSelectedItem : '@',
								},
								controller : [
										'$scope',
										'$mdSidenav',
										function($scope, $mdSidenav) {
											$scope.globalAttr = globalAttr;
											/*----------------------------------------------------------------------------------------------------*/
											$scope.toggleSidenav = function() {
												return $mdSidenav(
														'izmusSidenav')
														.toggle();
											};
											/*----------------------------------------------------------------------------------------------------*/
											this.itemClicked = function(menuItem){
												if ($scope.selectedItem === menuItem){
													$scope.selectedItem = null;
												}
												else {
													$scope.selectedItem = menuItem;
												}
											};
											/*----------------------------------------------------------------------------------------------------*/
											this.isOpen = function(menuItem){
												if ($scope.selectedItem === menuItem){
													return true;
												}
												else {
													return false;
												}
											};
											/*----------------------------------------------------------------------------------------------------*/
											$scope.topBarMinimized = false;
											/*----------------------------------------------------------------------------------------------------*/
											$scope.toggleTopBar = function(){
												$scope.topBarMinimized = !$scope.topBarMinimized;
											}
											/*----------------------------------------------------------------------------------------------------*/
											$scope.isTopBarMinimized = function(){
												return $scope.topBarMinimized;
											}
											/*----------------------------------------------------------------------------------------------------*/

										    $scope.$on('IdleWarn', function(e, countdown) {
										    	if (countdown == popupTimeout){
										    		timeoutCountdownDialog();
										    	}
										    });
											/*----------------------------------------------------------------------------------------------------*/

										    $scope.$on('IdleTimeout', function() {
										        window.location = "/";
										    });

											/*----------------------------------------------------------------------------------------------------*/

										    $scope.$on('Keepalive', function() {
										        heartbeat();
										    });
										} ],
								link : function(scope, elem, attr) {
									scope.progressMode = 'indeterminate';
									/*----------------------------------------------------------------------------------------------------*/
									loadNavbar().then(function(result) {
										scope.list = result;
										scope.progressMode = '';
										scope.setSelectedItem(scope.list, scope.initialSelectedItem);
									}, function(error) {
										scope.progressMode = '';
									});
									/*----------------------------------------------------------------------------------------------------*/
									scope.setSelectedItem = function(list, initialSelectedItem){
										if (!initialSelectedItem) return;
										if (!angular.isArray(list));
										loop: for (var i = 0; i < list.length; i++){
											var item = list[i];
											if (item.label == initialSelectedItem){
												scope.selectedItem = item;
												item.selected = true;
												break;
											}
											if (item.subItems){
												var subItems = item.subItems;
												for (var j = 0; j < subItems.length; j++){
													var subItem = subItems[j];
													if (subItem.label == initialSelectedItem){
														scope.selectedItem = item;
														subItem.selected = true;
														break loop;
													}
												}
											}
										}
									}
								}
							}
						} ]);
/*----------------------------------------------------------------------------------------------------*/
/**
 * Configuration
 */
angular.module('izmusNavBarApp').config(
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
					    		'50': '#ccd8e3',
					            '100': '#bccbda',
					            '200': '#abbfd1',
					            '300': '#9bb2c8',
					            '400': '#8aa6bf',
					            '500': '#7A99B6',
					            '600': '#6a8cad',
					            '700': '#5a80a3',
					            '800': '#517393',
					            '900': '#486682',
					            'A100': '#dde5ec',
					            'A200': '#edf1f5',
					            'A400': '#fefefe',
					            'A700': '#3f5972'
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
angular.module('izmusNavBarApp').config(['IdleProvider','KeepaliveProvider','TitleProvider', function(IdleProvider, KeepaliveProvider, TitleProvider) {
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
/*----------------------------------------------------------------------------------------------------*/
/**
 * Services
 */
angular.module('izmusNavBarApp').factory('loadNavbar',
		[ '$q', '$http', function($q, $http) {
			return function() {
				return $q(function(resolve, reject) {
					$http({
						method : 'GET',
						url : '/api/Navbar/GetNavbar',
					}).then(function successCallback(response) {
						if (response.data) {
							resolve(response.data);
						} else {
							reject();
						}
					}, function errorCallback(response) {
						reject();
					});
				})
			}
		} ]);
angular.module('izmusNavBarApp').factory('heartbeat',
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
angular.module('izmusNavBarApp').factory('saveUserAvatar',
		[ '$q', '$http', '$httpParamSerializer', function($q, $http, $httpParamSerializer) {
			return function(userAvatar) {
				return $q(function(resolve, reject) {
					$http({
						method : 'POST',
						url : '/api/Users/SaveUserAvatar',
						headers: {
					        'Content-Type': "application/x-www-form-urlencoded; charset=UTF-8",
					        'Upgrade-Insecure-Requests': "1",
					        'X-CSRF-TOKEN': globalAttr.sessionToken
					    },
					    data : $httpParamSerializer({
					    	userAvatar: userAvatar
					    })
					}).then(function successCallback() {
						resolve();
					}, function errorCallback(response) {
						reject();
					});
				})
			}
		} ]);
angular.module('izmusNavBarApp').factory('izmusLogout',
		[ '$q', '$http', '$httpParamSerializer','$location', function($q, $http, $httpParamSerializer, $location) {
			return function() {
				return $q(function(resolve, reject) {
					$http({
						method : 'POST',
						url : '/logout',
						headers: {
					        'Content-Type': "application/x-www-form-urlencoded; charset=UTF-8",
					        'Upgrade-Insecure-Requests': "1",
					        'X-CSRF-TOKEN': globalAttr.sessionToken
					    },
					    data : $httpParamSerializer({
					    	_csrf: globalAttr.sessionToken
					    })
					}).then(function successCallback() {
						window.location = "/Login?logout";
					}, function errorCallback() {
						window.location = "/Login?logout";
					});
				})
			}
		} ]);
angular.module('izmusNavBarApp').factory('avatarDialog',
		[ '$mdMedia', '$mdDialog',function($mdMedia, $mdDialog) {
			return function(ev, okFunction) {
				var customFullscreen = $mdMedia('xs') || $mdMedia('sm');
				var useFullScreen = ($mdMedia('sm') || $mdMedia('xs')) && customFullscreen;
			    /*----------------------------------------------------------------------------------------------------*/
				var changeAvatarCtrl = function($scope, $mdDialog) {
					$scope.originalImage='';
				    $scope.croppedImage='';
				    $scope.globalAttr = globalAttr;
				    $scope.handleFileSelect = function(evt) {
						var file = evt.currentTarget.files[0];
						var reader = new FileReader();
						reader.onload = function (evt) {
							$scope.$apply(function($scope){
								$scope.originalImage = evt.target.result;
							});
						};
						reader.readAsDataURL(file);
				    };
					/*----------------------------------------------------------------------------------------------------*/
					$scope.cancel = function() {
						$mdDialog.cancel();
					};
					/*----------------------------------------------------------------------------------------------------*/
					$scope.ok = function() {
						okFunction($scope.croppedImage);
						$mdDialog.cancel();
					};
				}
			    /*----------------------------------------------------------------------------------------------------*/
				$mdDialog.show({
				    controller: changeAvatarCtrl,
				    templateUrl: '/views/core/change-avatar/templates/change-avatar.html',
				    parent: angular.element(document.body),
				    targetEvent: ev,
				    clickOutsideToClose: true,
				    fullscreen: useFullScreen
				});
			}
		} ]);
angular.module('izmusNavBarApp').factory('timeoutCountdownDialog',
		[ '$mdMedia', '$mdDialog',function($mdMedia, $mdDialog) {
			return function(ev) {
				var customFullscreen = $mdMedia('xs') || $mdMedia('sm');
				var useFullScreen = ($mdMedia('sm') || $mdMedia('xs')) && customFullscreen;
			    /*----------------------------------------------------------------------------------------------------*/
				var timeoutCountdownCtrl = function($scope, $mdDialog) {
				    $scope.globalAttr = globalAttr;
				    $scope.timeout = popupTimeout;
					/*----------------------------------------------------------------------------------------------------*/
					$scope.ok = function() {
						$mdDialog.cancel();
					};
					/*----------------------------------------------------------------------------------------------------*/
					$scope.$on('IdleEnd', function() {
						$mdDialog.cancel();
				    });
				}
			    /*----------------------------------------------------------------------------------------------------*/
				$mdDialog.show({
				    controller: timeoutCountdownCtrl,
				    template: 
				    '<style>' +
				    	'h1 {' +
				    	'color: red;' +
				    '}' +
				    '</style>' +
				    '<section layout="column"  dir="{{globalAttr.direction}}">' +
				    	'<md-content layout-padding idle-countdown="countdown" ng-init="countdown = timeout">' +
				    		'<md-whiteframe class="md-whiteframe-1dp"' +
				    				'layout-padding layout="column" layout-align="center center">' +
				    				'<h3>{{globalAttr.timeout}}</h3>' +
				    				'<h1>{{countdown}}</h1>' +
				    				'<md-progress-linear md-mode="determinate" value="{{(countdown / timeout) * 100}}"></md-progress-linear>' +
				    		'</md-whiteframe>' +
				    	'</md-content>' +
				    	'<md-dialog-actions flex="none" layout="row">' +
				    	      '<span flex></span>' +
				    	      '<md-button class="md-primary md-button md-default-theme md-ink-ripple" ng-click="ok()">' +
				    	        '{{globalAttr.ok}}' +
				    	      '</md-button>' +
				    	'</md-dialog-actions>' +
				    '</section>',
				    parent: angular.element(document.body),
				    targetEvent: ev,
				    clickOutsideToClose: false,
				    fullscreen: useFullScreen
				});
			}
		} ]);
/*----------------------------------------------------------------------------------------------------*/
/**
 * Menu toggle
 */
angular.module('izmusNavBarApp').directive('izmusNavbarMenuToggle', [ function() {
	return {
		scope : {
			item : '=',
		},
		require : '^^izmusNavBar',
		templateUrl : '/views/core/izmus-nav-bar/templates/menu-toggle.html',
		link : function(scope, element, attr, parentController) {
			scope.globalAttr = globalAttr;
			scope.isOpen = function(){
				return parentController.isOpen(scope.item);
			};
			scope.click = function(){
				parentController.itemClicked(scope.item);
			};
		}
	};
} ]);
/*----------------------------------------------------------------------------------------------------*/
/**
 * Menu link
 */
angular
		.module('izmusNavBarApp')
		.directive(
				'izmusNavbarMenuLink',
				[ function() {
					return {
						scope : {
							item : '=',
						},
						require : '^^izmusNavBar',
						templateUrl : '/views/core/izmus-nav-bar/templates/menu-link.html',
						link : function(scope, element, attr, parentController) {
							scope.globalAttr = globalAttr;
						}
					};
				} ]);
/*----------------------------------------------------------------------------------------------------*/
/**
 * Toolbar Menu
 */
angular
		.module('izmusNavBarApp')
		.directive(
				'izmusToolbarMenu',
				[ 'izmusLogout', 'saveUserAvatar', 'avatarDialog', function(izmusLogout, saveUserAvatar, avatarDialog) {
					return {
						scope : {
							
						},
						templateUrl : '/views/core/izmus-nav-bar/templates/toolbar-menu.html',
						link : function(scope, element, attr) {
							scope.globalAttr = globalAttr;
							var avatars = ['/views/core/izmus-nav-bar/images/male-avatar.svg', '/views/core/izmus-nav-bar/images/female-avatar.svg'];
							/*----------------------------------------------------------------------------------------------------*/
							scope.getUserAvatar = function(){
								if(globalAttr.user.userAvatar && globalAttr.user.userAvatar != 'null'){
									return globalAttr.user.userAvatar;
								}
								if (globalAttr.user.isUserMale != null){
									if (globalAttr.user.isUserMale){
										return avatars[0];
									}
									else {
										return avatars[1];
									}
								}
								else {
									return avatars[0];
								}
							}
							/*----------------------------------------------------------------------------------------------------*/
							scope.izmusLogout = function(){
								izmusLogout(scope.sessionToken);
							}
							/*----------------------------------------------------------------------------------------------------*/
							scope.changeAvatar = function(ev){
								avatarDialog(ev, function(croppedImage){
									globalAttr.user.userAvatar = croppedImage;
									saveUserAvatar(globalAttr.user.userAvatar);
								});
							}
							/*----------------------------------------------------------------------------------------------------*/
							scope.userSettings = function(){
								window.location = "/UserPersonalSettings";
							}
						}
					};
				} ]);