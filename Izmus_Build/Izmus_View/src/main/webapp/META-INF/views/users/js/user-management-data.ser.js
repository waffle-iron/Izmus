angular.module('userManagementApp').factory('loadUserManagementData',
		[ '$q', '$http', function($q, $http) {
			return function() {
				return $q(function(resolve, reject) {
					$http({
						method : 'GET',
						url : '/api/Users/UserManagementData'
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
angular.module('userManagementApp').factory('saveUserManagementData',
		[ '$q', '$http', '$httpParamSerializer', function($q, $http, $httpParamSerializer) {
			return function(userData) {
				return $q(function(resolve, reject) {
					$http({
						method : 'POST',
						url : '/api/Users/SaveUserData',
						headers: {
					        'Content-Type': "application/x-www-form-urlencoded; charset=UTF-8",
					        'Upgrade-Insecure-Requests': "1",
					        'X-CSRF-TOKEN': globalAttr.sessionToken
					    },
					    data : $httpParamSerializer({
					    	userData: userData
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
angular.module('userManagementApp').factory('loadAvailableRoles',
		[ '$q', '$http', function($q, $http) {
			return function() {
				return $q(function(resolve, reject) {
					$http({
						method : 'GET',
						url : '/api/Users/AvailableRoles'
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

angular.module('userManagementApp').factory('checkUserAndEmail',
		[ '$q', '$http', function($q, $http) {
			return function(userName, email) {
				return $q(function(resolve, reject) {
					$http({
						method : 'GET',
						url : '/api/Users/CheckUserAndEmail',
					    params : {
					    	userName: userName,
					    	email: email
					    }
					}).then(function successCallback(response) {
						resolve(response.data);
					}, function errorCallback(error) {
						window.location = "/";
					});
				})
			}
		} ]);
/*----------------------------------------------------------------------------------------------------*/

angular.module('userManagementApp').factory('createNewUser',
		[ '$q', '$http', '$httpParamSerializer',function($q, $http, $httpParamSerializer) {
			return function(userName, email, userType) {
				return $q(function(resolve, reject) {
					$http({
						method : 'POST',
						url : '/api/Users/CreateNewUser',
						headers: {
					        'Content-Type': "application/x-www-form-urlencoded; charset=UTF-8",
					        'Upgrade-Insecure-Requests': "1",
					        'X-CSRF-TOKEN': globalAttr.sessionToken
					    },
					    data : $httpParamSerializer({
					    	_csrf: globalAttr.sessionToken,
					    	userName: userName,
					    	email: email,
					    	userType: userType
					    })
					}).then(function successCallback(response) {
						resolve(response.data);
					}, function errorCallback(error) {
						
					});
				})
			}
		} ]);