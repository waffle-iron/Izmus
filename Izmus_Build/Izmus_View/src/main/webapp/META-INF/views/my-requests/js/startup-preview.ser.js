/*----------------------------------------------------------------------------------------------------*/
angular.module('myRequestsApp').factory('startupPreviewDialog',
		[ '$mdMedia', '$mdDialog','cancelRequestDialog',
		  function($mdMedia, $mdDialog, cancelRequestDialog) {
			return function(ev, startup, iconSrc, cancelRequest) {
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
					$scope.cancelRequest = function(){
						cancelRequestDialog(ev, startup, iconSrc, cancelRequest);
						$mdDialog.cancel();
					}
				}
			    /*----------------------------------------------------------------------------------------------------*/
				$mdDialog.show({
				    controller: startupPreviewCtrl,
				    templateUrl: '/views/my-requests/templates/startup-preview.dialog.html',
				    parent: angular.element(document.body),
				    targetEvent: ev,
				    clickOutsideToClose: true,
				    fullscreen: useFullScreen
				});
			}
		} ]);
