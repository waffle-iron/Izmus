angular.module('findersDashboardApp').factory('loadAllAvailableStartups',
		[ '$q', '$http', function($q, $http) {
			return function(pageNumber, 
					search, 
					filterSector,
					fundingStage,
					productStage,
					pageSize) {
				return $q(function(resolve, reject) {
					$http({
						method : 'GET',
						url : '/api/AvailableStartups',
						params: {
							pageNumber: pageNumber,
							searchName: search,
							filterSector: filterSector,
							fundingStage: fundingStage,
							productStage: productStage,
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
/*----------------------------------------------------------------------------------------------------*/
angular.module('findersDashboardApp').factory('loadAllFundingStages',
		[ '$q', '$http', function($q, $http) {
			return function(pageNumber, search) {
				return $q(function(resolve, reject) {
					$http({
						method : 'GET',
						url : '/api/AvailableStartups/FundingStages',
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
angular.module('findersDashboardApp').factory('loadAllProductStages',
		[ '$q', '$http', function($q, $http) {
			return function(pageNumber, search) {
				return $q(function(resolve, reject) {
					$http({
						method : 'GET',
						url : '/api/AvailableStartups/ProductStages',
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