angular.module('izmusNavBarApp', [ 'ngMaterial', 'ngImgCrop' ]);
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
						'$timeout',
						function(loadNavbar, $timeout) {
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
											$timeout(function() {
												window.location = "/";
										    },(15 * 60 * 1000));
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