angular.module('izmusLandingPageApp').factory('contactUsDialog',
		[ '$mdMedia', '$mdDialog',function($mdMedia, $mdDialog) {
			return function(ev) {
				var customFullscreen = $mdMedia('xs') || $mdMedia('sm');
				var useFullScreen = ($mdMedia('sm') || $mdMedia('xs')) && customFullscreen;
			    /*----------------------------------------------------------------------------------------------------*/
				var contactUsCtrl = function($scope, $mdDialog) {
				    $scope.globalAttr = globalAttr;
				    $scope.lang = lang;
					/*----------------------------------------------------------------------------------------------------*/
					$scope.cancel = function() {
						$mdDialog.cancel();
					};
					/*----------------------------------------------------------------------------------------------------*/
					$scope.ok = function() {
						$mdDialog.cancel();
					};
				}
			    /*----------------------------------------------------------------------------------------------------*/
				$mdDialog.show({
				    controller: contactUsCtrl,
				    templateUrl: '/views-public/landing-page/templates/contact-us.dialog.html',
				    parent: angular.element(document.body),
				    targetEvent: ev,
				    clickOutsideToClose: true,
				    fullscreen: useFullScreen
				});
			}
		} ]);