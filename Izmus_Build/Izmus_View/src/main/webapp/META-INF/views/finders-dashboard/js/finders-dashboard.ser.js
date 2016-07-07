angular.module('findersDashboardApp').factory('loadAllAvailableStartups',
		[ '$q', '$http', function($q, $http) {
			return function(pageNumber, search, filterSector) {
				return $q(function(resolve, reject) {
					$http({
						method : 'GET',
						url : '/api/AvailableStartups',
						params: {
							pageNumber: pageNumber,
							searchName: search,
							filterSector: filterSector
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
/*----------------------------------------------------------------------------------------------------*/
angular.module('findersDashboardApp').factory('loadAllSectors',
		[ '$q', '$http', function($q, $http) {
			return function(pageNumber, search) {
				return $q(function(resolve, reject) {
					$http({
						method : 'GET',
						url : '/api/AvailableStartups/Sectors',
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