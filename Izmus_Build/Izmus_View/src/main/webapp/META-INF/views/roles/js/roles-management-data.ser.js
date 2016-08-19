angular.module('rolesManagementApp').factory('loadRolesManagementData',
		[ '$q', '$http', function($q, $http) {
			return function() {
				return $q(function(resolve, reject) {
					$http({
						method : 'GET',
						url : '/api/Roles/RolesManagementData'
					}).then(function successCallback(response) {
						if (response.data) {
							resolve(response.data);
						} else {
							reject();
						}
					}, function errorCallback(response) {
						reject();
					});
				})
			}
		} ]);
/*----------------------------------------------------------------------------------------------------*/
angular.module('rolesManagementApp').factory('saveRoleManagementData',
		[ '$q', '$http', '$httpParamSerializer', function($q, $http, $httpParamSerializer) {
			return function(roleData) {
				return $q(function(resolve, reject) {
					$http({
						method : 'POST',
						url : '/api/Roles/SaveRoleData',
						headers: {
					        'Content-Type': "application/x-www-form-urlencoded; charset=UTF-8",
					        'Upgrade-Insecure-Requests': "1",
					        'X-CSRF-TOKEN': globalAttr.sessionToken
					    },
					    data : $httpParamSerializer({
					    	roleData: roleData
					    })
					}).then(function successCallback() {
						resolve();
					}, function errorCallback(response) {
						reject();
					});
				})
			}
		} ]);
/*----------------------------------------------------------------------------------------------------*/
angular.module('rolesManagementApp').factory('loadAvailablePermissions',
		[ '$q', '$http', function($q, $http) {
			return function() {
				return $q(function(resolve, reject) {
					$http({
						method : 'GET',
						url : '/api/Roles/AvailablePermissions'
					}).then(function successCallback(response) {
						if (response.data) {
							resolve(response.data);
						} else {
							reject();
						}
					}, function errorCallback(response) {
						reject();
					});
				})
			}
		} ]);
/*----------------------------------------------------------------------------------------------------*/
angular.module('rolesManagementApp').factory('deleteRole',
		[ '$q', '$http', '$httpParamSerializer', function($q, $http, $httpParamSerializer) {
			return function(roleData) {
				return $q(function(resolve, reject) {
					$http({
						method : 'POST',
						url : '/api/Roles/DeleteRoleData',
						headers: {
					        'Content-Type': "application/x-www-form-urlencoded; charset=UTF-8",
					        'Upgrade-Insecure-Requests': "1",
					        'X-CSRF-TOKEN': globalAttr.sessionToken
					    },
					    data : $httpParamSerializer({
					    	roleData: roleData
					    })
					}).then(function successCallback() {
						resolve();
					}, function errorCallback(response) {
						reject();
					});
				})
			}
		} ]);