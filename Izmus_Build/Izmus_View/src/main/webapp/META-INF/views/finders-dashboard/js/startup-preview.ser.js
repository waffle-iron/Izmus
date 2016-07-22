/*----------------------------------------------------------------------------------------------------*/
angular.module('findersDashboardApp').factory('startupPreviewDialog',
		[ '$mdMedia', '$mdDialog','addToCartConfirmationDialog','addToWishlistDialog',
		  function($mdMedia, $mdDialog, addToCartConfirmationDialog, addToWishlistDialog) {
			return function(ev, startup, iconSrc) {
				var customFullscreen = $mdMedia('xs') || $mdMedia('sm');
				var useFullScreen = ($mdMedia('sm') || $mdMedia('xs')) && customFullscreen;
			    /*----------------------------------------------------------------------------------------------------*/
				var startupPreviewCtrl = function($scope) {
				    $scope.globalAttr = globalAttr;
				    $scope.lang = lang;
				    $scope.iconSrc = iconSrc;
				    $scope.startup = startup;
				    $scope.progressMode = '';
					/*----------------------------------------------------------------------------------------------------*/
					$scope.ok = function() {
						$mdDialog.cancel();
					};
					/*----------------------------------------------------------------------------------------------------*/
					$scope.addToCart = function() {
						addToCartConfirmationDialog(ev, startup, iconSrc);
						$mdDialog.cancel();
					};
					/*----------------------------------------------------------------------------------------------------*/
					$scope.addToWishlist = function() {
						addToWishlistDialog(ev, startup, iconSrc);
						$mdDialog.cancel();
					};
				}
			    /*----------------------------------------------------------------------------------------------------*/
				$mdDialog.show({
				    controller: startupPreviewCtrl,
				    templateUrl: '/views/finders-dashboard/templates/startup-preview.dialog.html',
				    parent: angular.element(document.body),
				    targetEvent: ev,
				    clickOutsideToClose: true,
				    fullscreen: useFullScreen
				});
			}
		} ]);
