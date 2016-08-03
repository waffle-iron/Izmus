/*----------------------------------------------------------------------------------------------------*/
angular.module('availableStartupsApp').factory('loadAllAvailableStartups',
		[ '$q', '$http', function($q, $http) {
			return function(pageNumber, search, pageSize) {
				return $q(function(resolve, reject) {
					$http({
						method : 'GET',
						url : '/api/StartupAssessment/PagedStartupBasicData',
						params: {
							pageNumber: pageNumber,
							searchName: search,
							pageSize: pageSize
							}
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