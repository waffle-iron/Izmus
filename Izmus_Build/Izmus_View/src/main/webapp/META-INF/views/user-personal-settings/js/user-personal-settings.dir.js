angular.module('userPersonalSettingsApp').directive('izmusUserPersonalSettings', ['getOwnUser', function(getOwnUser) {
	return {
		restrict : 'E',
		templateUrl : '/views/user-personal-settings/templates/user-personal-settings.html',
		scope: {
			
		},
		controller : ['$scope',function($scope) {
			var avatars = ['/views/users/images/male-avatar.svg', '/views/users/images/female-avatar.svg'];
			$scope.lang = lang;
			$scope.globalAttr = globalAttr;
			$scope.isMainFabOpen = false;
			/*----------------------------------------------------------------------------------------------------*/
			$scope.toggleMainFab = function($event, lostFocus){
				if (lostFocus){
					$scope.isMainFabOpen = false;
					return;
				}
				$scope.isMainFabOpen = !$scope.isMainFabOpen;
				if($scope.isMainFabOpen){
					if ($event){
						$event.currentTarget.focus();
					}
				}
			}
			/*----------------------------------------------------------------------------------------------------*/
			$scope.changePassword = function(){
				window.location = "/ChangePassword";
			}
			/*----------------------------------------------------------------------------------------------------*/
			getOwnUser().then(function(result){
				$scope.ownUser = result;
			});
			/*----------------------------------------------------------------------------------------------------*/
			$scope.getUserAvatar = function(){
				if (!$scope.ownUser) return;
				if ($scope.ownUser.userAvatar != null && $scope.ownUser.userAvatar != ''){
					return $scope.ownUser.userAvatar;
				}
				if ($scope.ownUser.isUserMale != null){
					if ($scope.ownUser.isUserMale){
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
		}],
		link : function(scope, elem, attr) {
			
		}
	}
} ]);