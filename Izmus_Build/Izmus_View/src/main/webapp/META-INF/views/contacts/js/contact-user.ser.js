/*----------------------------------------------------------------------------------------------------*/
angular.module('contactsApp').factory('viewCreateContactUserDialog',
		[ '$mdMedia', '$mdDialog','checkUserAndEmail', function($mdMedia, $mdDialog, checkUserAndEmail) {
			return function(ev, saveFunction) {
				var customFullscreen = $mdMedia('xs') || $mdMedia('sm');
				var useFullScreen = ($mdMedia('sm') || $mdMedia('xs')) && customFullscreen;
			    /*----------------------------------------------------------------------------------------------------*/
				var createContactUserCtrl = function($scope) {
				    $scope.globalAttr = globalAttr;
				    $scope.lang = lang;
				    $scope.$mdMedia = $mdMedia;
					/*----------------------------------------------------------------------------------------------------*/
					$scope.ok = function() {
						$mdDialog.cancel();
					};
					/*----------------------------------------------------------------------------------------------------*/
					$scope.save = function() {
						if (saveFunction){
							saveFunction($scope.userName);
						}
						$mdDialog.cancel();
					};
				}
			    /*----------------------------------------------------------------------------------------------------*/
				$mdDialog.show({
				    controller: createContactUserCtrl,
				    templateUrl: '/views/contacts/templates/create-contact-user.dialog.html',
				    parent: angular.element(document.body),
				    targetEvent: ev,
				    clickOutsideToClose: true,
				    fullscreen: useFullScreen
				});
			}
		} ]);
