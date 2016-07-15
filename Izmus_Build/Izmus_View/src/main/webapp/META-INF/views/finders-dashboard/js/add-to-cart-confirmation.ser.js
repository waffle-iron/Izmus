/*----------------------------------------------------------------------------------------------------*/
angular.module('findersDashboardApp').factory('addToCartConfirmationDialog',
		[ '$mdMedia', '$mdDialog', function($mdMedia, $mdDialog) {
			return function(ev, startup, iconSrc) {
				var customFullscreen = $mdMedia('xs') || $mdMedia('sm');
				var useFullScreen = ($mdMedia('sm') || $mdMedia('xs')) && customFullscreen;
			    /*----------------------------------------------------------------------------------------------------*/
				var addToCartConfirmationCtrl = function($scope) {
				    $scope.globalAttr = globalAttr;
				    $scope.lang = lang;
				    $scope.iconSrc = iconSrc;
				    $scope.startup = startup;
					/*----------------------------------------------------------------------------------------------------*/
					$scope.cancel = function() {
						$mdDialog.cancel();
					};
					/*----------------------------------------------------------------------------------------------------*/
					$scope.addToCart = function() {
						$mdDialog.cancel();
					};
				}
			    /*----------------------------------------------------------------------------------------------------*/
				$mdDialog.show({
				    controller: addToCartConfirmationCtrl,
				    templateUrl: '/views/finders-dashboard/templates/add-to-cart-confirmation.dialog.html',
				    parent: angular.element(document.body),
				    targetEvent: ev,
				    clickOutsideToClose: true,
				    fullscreen: useFullScreen
				});
			}
		} ]);