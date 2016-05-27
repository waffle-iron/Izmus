angular.module('rolesManagementApp')
.directive('izmusRolesPreview', ['saveRoleManagementData','$mdDialog', '$mdMedia','loadAvailablePermissions', 'deleteRole',
                  function(saveRoleManagementData, $mdDialog, $mdMedia, loadAvailablePermissions, deleteRole) {
	return {
		restrict : 'E',
		require: '^^izmusRolesManagement',
		templateUrl : '/views/roles/templates/roles-preview.html',
		scope: {
			selectedRole : '=',
			cancel: '@',
			ok: '@',
			availablePermissions: '@',
			userRoles: '@'			
		},
		controller : ['$scope',function($scope) {
			
		}],
		link : function(scope, elem, attr, parentCtrl) {
			/*----------------------------------------------------------------------------------------------------*/
			scope.globalAttr = globalAttr;
			scope.isMainFabOpen = false;
			scope.progressMode = '';
			scope.customFullscreen = $mdMedia('xs') || $mdMedia('sm');
			/*----------------------------------------------------------------------------------------------------*/
			scope.toggleMainFab = function($event, lostFocus){
				if (lostFocus){
					scope.isMainFabOpen = false;
					return;
				}
				scope.isMainFabOpen = !scope.isMainFabOpen;
				if(scope.isMainFabOpen){
					if ($event){
						$event.currentTarget.focus();
					}
				}
			}
			/*----------------------------------------------------------------------------------------------------*/
			scope.deletePermission = function (index){
				scope.selectedRole.permissions.splice(index, 1);
			}
			/*----------------------------------------------------------------------------------------------------*/
			scope.showPermissionsDialog = function(ev) {
				var useFullScreen = ($mdMedia('sm') || $mdMedia('xs')) && scope.customFullscreen;
			    $mdDialog.show({
			      controller: scope.permissionsDialogController,
			      templateUrl: '/views/roles/templates/permissions-dialog.html',
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
			scope.saveChanges = function(){
				scope.progressMode = 'indeterminate';
				saveRoleManagementData(scope.selectedRole).then(function(result){
					scope.isMainFabOpen = false;
					scope.progressMode = '';
				}, function(error){
					scope.progressMode = '';
				});;
			}
			/*----------------------------------------------------------------------------------------------------*/
			scope.deleteRole = function(){
				scope.progressMode = 'indeterminate';
				deleteRole(scope.selectedRole).then(function(result){
					parentCtrl.deleteRole(scope.selectedRole);
					scope.selectedRole = null;
					scope.isMainFabOpen = false;
					scope.progressMode = '';
				}, function(error){
					scope.progressMode = '';
				});;
			}
			/*----------------------------------------------------------------------------------------------------*/
			scope.permissionsDialogController = function($scope, $mdDialog) {
				$scope.parentScope = scope;
				/*----------------------------------------------------------------------------------------------------*/
				loadAvailablePermissions().then(function(result) {
					$scope.availablePermissions = [];
					loop: for (var i = 0; i < result.length; i++){
						var permission = result[i];
						for (var j = 0; j < scope.selectedRole.permissions.length; j++){
							var rolePermission = scope.selectedRole.permissions[j];
							if (rolePermission.permissionName == permission.permissionName){
								continue loop;
							}
						}
						$scope.availablePermissions.push(permission);
					}
				}, function(error) {
					
				});
				/*----------------------------------------------------------------------------------------------------*/
				$scope.cancel = function() {
					$mdDialog.cancel();
				};
				/*----------------------------------------------------------------------------------------------------*/
				$scope.ok = function() {
					for (var i = 0; i < $scope.availablePermissions.length; i++){
						var permission = $scope.availablePermissions[i];
						if (permission.selected == true){
							delete permission.selected;
							scope.selectedRole.permissions.push(permission);
						}
					}
					$mdDialog.cancel();
				};
			}
		}
	}
} ]);