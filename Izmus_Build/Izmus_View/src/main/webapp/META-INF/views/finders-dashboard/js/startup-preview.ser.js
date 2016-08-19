/*----------------------------------------------------------------------------------------------------*/
angular.module('findersDashboardApp').factory('startupPreviewDialog',
		[ '$mdMedia', '$mdDialog','addToCartConfirmationDialog',
		  function($mdMedia, $mdDialog, addToCartConfirmationDialog) {
			return function(ev, startup, iconSrc, addToWishlist, wishlist, myRequests, addToMyRequests) {
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
				    if (wishlist) {
				    	$scope.isInWishlist = false;
				    	for (var i = 0; i < wishlist.length; i++){
							var item = wishlist[i];
							if (item.startupId == startup.startupId){
								$scope.isInWishlist = true;
								break;
							}
						}
				    } 
				    
				    /*----------------------------------------------------------------------------------------------------*/
				    if (myRequests) {
				    	$scope.isInMyRequests = false;
				    	for (var i = 0; i < myRequests.length; i++){
							var item = myRequests[i];
							if (item.startupId == startup.startupId){
								$scope.isInMyRequests = true;
								break;
							}
						}
				    }  
					/*----------------------------------------------------------------------------------------------------*/
					$scope.ok = function() {
						$mdDialog.cancel();
					};
					/*----------------------------------------------------------------------------------------------------*/
					$scope.addToCart = function() {
						addToCartConfirmationDialog(ev, startup, iconSrc, addToMyRequests);
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
				    templateUrl: '/views/finders-dashboard/templates/startup-preview.dialog.html',
				    parent: angular.element(document.body),
				    targetEvent: ev,
				    clickOutsideToClose: true,
				    fullscreen: useFullScreen
				});
			}
		} ]);
