angular.module('userManagementApp')
.directive('izmusUserPreview', ['saveUserManagementData','$mdDialog','$mdMedia','loadAvailableRoles', 'avatarDialog',
                                   function(saveUserManagementData, $mdDialog, $mdMedia, loadAvailableRoles, avatarDialog) {
	return {
		restrict : 'E',
		require: '^^izmusUserManagement',
		templateUrl : '/views/users/templates/user-preview.html',
		scope: {
			selectedUser : '=',
			userRoles: '@',
			userId: '@',
			userInfo: '@',
			userEmail: '@',
			userName: '@',
			userEnabled: '@',
			female: '@',
			male: '@',
			gender: '@',
			created: '@',
			cancel: '@',
			ok: '@',
			lastSeen: '@',
			availableRoles: '@',
		},
		controller : ['$scope',function($scope) {
			$scope.changeAvatar = function(ev){
				avatarDialog(ev, function(croppedImage){
					$scope.selectedUser.userAvatar = croppedImage;
				});
			}
		}],
		link : function(scope, elem, attr, parentCtrl) {
			/*----------------------------------------------------------------------------------------------------*/
			scope.globalAttr = globalAttr;
			scope.isMainFabOpen = false;
			scope.progressMode = '';
			var avatars = ['/views/users/images/male-avatar.svg', '/views/users/images/female-avatar.svg'];
			scope.customFullscreen = $mdMedia('xs') || $mdMedia('sm');
			/*----------------------------------------------------------------------------------------------------*/
			scope.getUserAvatar = function(){
				if (scope.selectedUser.userAvatar != null && scope.selectedUser.userAvatar != ''){
					return scope.selectedUser.userAvatar;
				}
				if (scope.selectedUser.isUserMale != null){
					if (scope.selectedUser.isUserMale){
						return avatars[0];
					}
					else {
						return avatars[1];
					}
				}
				else {
					return avatars[0];
				}
			}
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
			scope.saveChanges = function(){
				scope.progressMode = 'indeterminate';
				saveUserManagementData(scope.selectedUser).then(function(result){
					scope.isMainFabOpen = false;
					scope.progressMode = '';
				}, function(error){
					scope.progressMode = '';
				});;
			}
			/*----------------------------------------------------------------------------------------------------*/
			scope.deleteRole = function (index){
				scope.selectedUser.userRoles.splice(index, 1);
			}
			/*----------------------------------------------------------------------------------------------------*/
			scope.showRoleDialog = function(ev) {
				var useFullScreen = ($mdMedia('sm') || $mdMedia('xs')) && scope.customFullscreen;
			    $mdDialog.show({
			      controller: scope.roleDialogController,
			      templateUrl: '/views/users/templates/role-dialog.html',
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
				/*----------------------------------------------------------------------------------------------------*/
				loadAvailableRoles().then(function(result) {
					$scope.availableRoles = [];
					loop: for (var i = 0; i < result.length; i++){
						var rule = result[i];
						for (var j = 0; j < scope.selectedUser.userRoles.length; j++){
							var userRule = scope.selectedUser.userRoles[j];
							if (userRule.roleName == rule.roleName){
								continue loop;
							}
						}
						$scope.availableRoles.push(rule);
					}
				}, function(error) {
					
				});
				/*----------------------------------------------------------------------------------------------------*/
				$scope.cancel = function() {
					$mdDialog.cancel();
				};
				/*----------------------------------------------------------------------------------------------------*/
				$scope.ok = function() {
					for (var i = 0; i < $scope.availableRoles.length; i++){
						var role = $scope.availableRoles[i];
						if (role.selected == true){
							delete role.selected;
							scope.selectedUser.userRoles.push(role);
						}
					}
					$mdDialog.cancel();
				};
			}
		}
	}
} ]);