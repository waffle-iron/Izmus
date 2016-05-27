angular.module('rolesManagementApp').directive('investitRolesTable', ['$mdSidenav', function($mdSidenav) {
	return {
		restrict : 'E',
		require: '^^investitRolesManagement',
		templateUrl : '/views/roles/templates/roles-table.html',
		scope: {
			rolesDataList : '=',
		},
		controller : ['$scope',function($scope) {
			
		}],
		link : function(scope, elem, attr, parentCtrl) {
			scope.globalAttr = globalAttr;
			/*----------------------------------------------------------------------------------------------------*/
			scope.selectRole = function(role){
				parentCtrl.setSelectedRole(role);
				$mdSidenav('rolesListSidenav').close();
			}
			/*----------------------------------------------------------------------------------------------------*/
			$mdSidenav('rolesListSidenav').toggle();
		}
	}
} ]);