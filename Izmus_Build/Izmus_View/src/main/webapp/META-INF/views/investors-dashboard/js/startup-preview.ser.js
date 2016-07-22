/*----------------------------------------------------------------------------------------------------*/
angular.module('investorsDashboardApp').factory('startupPreviewDialog',
		[ '$mdMedia', '$mdDialog','addToCartConfirmationDialog', function($mdMedia, $mdDialog, addToCartConfirmationDialog) {
			return function(ev, startup, iconSrc, addToWishlist) {
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
						if (addToWishlist){
							addToWishlist(startup);
						}
						$mdDialog.cancel();
					};
				}
			    /*----------------------------------------------------------------------------------------------------*/
				$mdDialog.show({
				    controller: startupPreviewCtrl,
				    templateUrl: '/views/investors-dashboard/templates/startup-preview.dialog.html',
				    parent: angular.element(document.body),
				    targetEvent: ev,
				    clickOutsideToClose: true,
				    fullscreen: useFullScreen
				});
			}
		} ]);
