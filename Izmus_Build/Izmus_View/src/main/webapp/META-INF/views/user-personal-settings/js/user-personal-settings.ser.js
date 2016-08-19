angular.module('userPersonalSettingsApp').factory('getOwnUser',
		[ '$q', '$http', function($q, $http) {
			return function() {
				return $q(function(resolve, reject) {
					$http({
						method : 'GET',
						url : '/api/Users/GetOwnUser'
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
angular.module('userPersonalSettingsApp').factory('saveUserPersonalData',
		[ '$q', '$http', '$httpParamSerializer', function($q, $http, $httpParamSerializer) {
			return function(userData) {
				return $q(function(resolve, reject) {
					$http({
						method : 'POST',
						url : '/api/Users/SaveUserPersonalData',
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