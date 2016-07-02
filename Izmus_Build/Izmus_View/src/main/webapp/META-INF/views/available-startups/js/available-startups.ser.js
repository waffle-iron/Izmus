/*----------------------------------------------------------------------------------------------------*/
angular.module('availableStartupsApp').factory('loadAllAvailableStartups',
		[ '$q', '$http', function($q, $http) {
			return function(pageNumber) {
				return $q(function(resolve, reject) {
					$http({
						method : 'GET',
						url : '/api/AvailableStartups',
						params: {pageNumber: pageNumber}
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