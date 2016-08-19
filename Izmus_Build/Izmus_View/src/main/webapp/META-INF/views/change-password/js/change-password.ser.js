angular.module('changePasswordApp').factory('saveUserPassword',
		[ '$q', '$http', '$httpParamSerializer', function($q, $http, $httpParamSerializer) {
			return function(currentPassword, newPassword) {
				return $q(function(resolve, reject) {
					$http({
						method : 'POST',
						url : '/api/Users/SaveUserPassword',
						headers: {
					        'Content-Type': "application/x-www-form-urlencoded; charset=UTF-8",
					        'Upgrade-Insecure-Requests': "1",
					        'X-CSRF-TOKEN': globalAttr.sessionToken
					    },
					    data : $httpParamSerializer({
					    	currentPassword: currentPassword,
					    	newPassword: newPassword
					    })
					}).then(function successCallback(response) {
						resolve(response.data);
					}, function errorCallback(response) {
						reject();
					});
				})
			}
		} ]);