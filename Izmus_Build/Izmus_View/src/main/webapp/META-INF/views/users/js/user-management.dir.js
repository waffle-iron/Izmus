angular.module('userManagementApp').directive('investitUserManagement',
		[ 'loadUserManagementData', '$mdSidenav', '$mdMedia', '$mdDialog',function(loadUserManagementData, $mdSidenav, $mdMedia, $mdDialog) {
			return {
				restrict : 'E',
				templateUrl : '/views/users/templates/user-management.html',
				scope :{
					userName : '@',
					userId : '@',
					userRoles : '@',
					lastSeen : '@',
					userEmail : '@',
					userInfo : '@',
					userEnabled : '@',
					globalSearch : '@',
					female : '@',
					male : '@',
					gender : '@',
					created : '@',
					userManagementText: '@',
					userListText: '@',
					availableRoles: '@',
					cancel: '@',
					ok: '@',
				},
				controller : [ '$scope', function($scope) {
					$scope.customFullscreen = $mdMedia('xs') || $mdMedia('sm');
					/*----------------------------------------------------------------------------------------------------*/
					this.setSelectedUser = function(user){
						$scope.selectedUser = user;
					}
				} ],
				link : function(scope, elem, attr) {
					scope.progressMode = 'indeterminate';
					/*----------------------------------------------------------------------------------------------------*/
					loadUserManagementData().then(function(result) {
						scope.userTypeTabs = result;
						scope.progressMode = '';
					}, function(error) {
						scope.progressMode = ''
					});
					/*----------------------------------------------------------------------------------------------------*/
					scope.toggleSidenav = function() {
						return $mdSidenav(
								'userListSidenav')
								.toggle();
					};
					/*----------------------------------------------------------------------------------------------------*/
					scope.addNewUser = function(ev) {
						var useFullScreen = ($mdMedia('sm') || $mdMedia('xs')) && scope.customFullscreen;
					    $mdDialog.show({
					      controller: 'addUserDialogCtrl',
					      templateUrl: '/views/users/templates/user-dialog.html',
					      parent: angular.element(document.body),
					      targetEvent: ev,
					      clickOutsideToClose: true,
					      fullscreen: useFullScreen,
					      locals:{
					    	  parentScope: scope,
					    	  parentElem: elem
					      }
					    });
					    scope.$watch(function() {
					      return $mdMedia('xs') || $mdMedia('sm');
					    }, function(wantsFullScreen) {
					      scope.customFullscreen = (wantsFullScreen === true);
					    });
					};
				}
			}
		} ]);