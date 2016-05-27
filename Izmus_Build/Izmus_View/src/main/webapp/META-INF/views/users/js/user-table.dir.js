angular.module('userManagementApp').directive('izmusUserTable', ['$mdSidenav', function($mdSidenav) {
	return {
		restrict : 'E',
		require: '^^izmusUserManagement',
		templateUrl : '/views/users/templates/user-table.html',
		scope: {
			userTypeTabs : '=',
			userName : '@',
			userId : '@',
			male: '@',
			globalSearch : '@'
		},
		controller : ['$scope',function($scope) {
			
		}],
		link : function(scope, elem, attr, parentCtrl) {
			scope.globalAttr = globalAttr;
			var avatars = ['/views/users/images/male-avatar.svg', '/views/users/images/female-avatar.svg'];
			/*----------------------------------------------------------------------------------------------------*/
			scope.selectUser = function(user){
				parentCtrl.setSelectedUser(user);
				$mdSidenav('userListSidenav').close();
			}
			/*----------------------------------------------------------------------------------------------------*/
			scope.getUserAvatar = function(user){
				if (user.userAvatar != null && user.userAvatar != ''){
					return user.userAvatar;
				}
				if (user.isUserMale != null){
					if (user.isUserMale){
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
			$mdSidenav('userListSidenav').toggle();
		}
	}
} ]);