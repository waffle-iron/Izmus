/*----------------------------------------------------------------------------------------------------*/
angular.module('myRequestsApp').factory('loadAllMyRequests',
		[ '$q', '$http', function($q, $http) {
			return function(pageNumber, pageSize) {
				return $q(function(resolve, reject) {
					$http({
						method : 'GET',
						url : '/api/Cart/PagedMyRequests',
						params: {
							pageNumber: pageNumber,
							pageSize: pageSize
							},
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