angular.module('rolesManagementApp').directive('investitRolesManagement',
		[ 'loadRolesManagementData', '$mdSidenav', '$mdMedia','$mdDialog', function(loadRolesManagementData, $mdSidenav, $mdMedia, $mdDialog) {
			return {
				restrict : 'E',
				templateUrl : '/views/roles/templates/roles-management.html',
				scope :{
					rolesManagement: '@',
					rolesList: '@',
					cancel: '@',
					ok: '@',
					availablePermissions: '@',
					userRoles: '@',
					newRole: '@',
					roleName: '@'
				},
				controller : [ '$scope', function($scope) {
					this.setSelectedRole = function(role){
						$scope.selectedRole = role;
					}
					/*----------------------------------------------------------------------------------------------------*/
					this.deleteRole = function(role){
						var index = $scope.rolesDataList.indexOf(role);
						$scope.rolesDataList.splice(index, 1);
						$scope.toggleSidenav();
					}
				} ],
				link : function(scope, elem, attr) {
					scope.globalAttr = globalAttr;
					scope.progressMode = 'indeterminate';
					scope.customFullscreen = $mdMedia('xs') || $mdMedia('sm');
					/*----------------------------------------------------------------------------------------------------*/
					loadRolesManagementData().then(function(result) {
						scope.rolesDataList = result;
						scope.progressMode = '';
					}, function(error) {
						scope.progressMode = ''
					});
					/*----------------------------------------------------------------------------------------------------*/
					scope.toggleSidenav = function() {
						return $mdSidenav(
								'rolesListSidenav')
								.toggle();
					};
					/*----------------------------------------------------------------------------------------------------*/
					scope.showRoleDialog = function(ev) {
						var useFullScreen = ($mdMedia('sm') || $mdMedia('xs')) && scope.customFullscreen;
					    $mdDialog.show({
					      controller: scope.roleDialogController,
					      templateUrl: '/views/roles/templates/new-role-dialog.html',
					      parent: angular.element(document.body),
					      targetEvent: ev,
					      clickOutsideToClose: true,
					      fullscreen: useFullScreen
					    });
					    scope.$watch(function() {
					      return $mdMedia('xs') || $mdMedia('sm');
					    }, function(wantsFullScreen) {
					      scope.customFullscreen = (wantsFullScreen === true);
					    });
					};
					/*----------------------------------------------------------------------------------------------------*/
					scope.roleDialogController = function($scope, $mdDialog) {
						$scope.parentScope = scope;
						$scope.roleName = "ROLE_";
						/*----------------------------------------------------------------------------------------------------*/
						$scope.cancel = function() {
							$mdDialog.cancel();
						};
						/*----------------------------------------------------------------------------------------------------*/
						$scope.ok = function() {
							if ($scope.roleName != 'ROLE_'){
								scope.rolesDataList.push({
									roleName: $scope.roleName,
									permissions: []
								});
							}
							$mdDialog.cancel();
						};
					}
				}
			}
		} ]);