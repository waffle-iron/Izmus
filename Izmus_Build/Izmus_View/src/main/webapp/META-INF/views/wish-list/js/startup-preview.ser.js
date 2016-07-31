/*----------------------------------------------------------------------------------------------------*/
angular.module('wishListApp').factory('startupPreviewDialog',
		[ '$mdMedia', '$mdDialog','addToCartConfirmationDialog',
		  function($mdMedia, $mdDialog, addToCartConfirmationDialog) {
			return function(ev, startup, iconSrc, addToMyRequests) {
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
						addToCartConfirmationDialog(ev, startup, iconSrc, addToMyRequests);
						$mdDialog.cancel();
					};
				}
			    /*----------------------------------------------------------------------------------------------------*/
				$mdDialog.show({
				    controller: startupPreviewCtrl,
				    templateUrl: '/views/wish-list/templates/startup-preview.dialog.html',
				    parent: angular.element(document.body),
				    targetEvent: ev,
				    clickOutsideToClose: true,
				    fullscreen: useFullScreen
				});
			}
		} ]);
