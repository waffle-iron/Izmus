angular.module('investorsDashboardApp').factory('loadAllAvailableStartups',
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
angular.module('investorsDashboardApp').factory('loadAllSectors',
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
angular.module('investorsDashboardApp').factory('loadAllFundingStages',
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
angular.module('investorsDashboardApp').factory('loadAllProductStages',
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
/*----------------------------------------------------------------------------------------------------*/
angular.module('investorsDashboardApp').factory('triggerWishlist',
		[ '$q', '$http', '$httpParamSerializer', function($q, $http, $httpParamSerializer) {
			return function(startupId) {
				return $q(function(resolve, reject) {
					$http({
						method : 'POST',
						url : '/api/AvailableStartups/Wishlist',
						headers: {
					        'Content-Type': "application/x-www-form-urlencoded; charset=UTF-8",
					        'Upgrade-Insecure-Requests': "1",
					        'X-CSRF-TOKEN': globalAttr.sessionToken
					    },
					    data : $httpParamSerializer({
					    	startupId: startupId,
					    })
					}).then(function successCallback(response) {
						resolve(response.data);
					}, function errorCallback(response) {
						reject();
					});
				})
			}
		} ]);
/*----------------------------------------------------------------------------------------------------*/
angular.module('investorsDashboardApp').factory('loadAllWishlist',
		[ '$q', '$http', function($q, $http) {
			return function(pageNumber, search) {
				return $q(function(resolve, reject) {
					$http({
						method : 'GET',
						url : '/api/Cart/Wishlist',
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
angular.module('investorsDashboardApp').factory('loadAllMyRequests',
		[ '$q', '$http', function($q, $http) {
			return function(pageNumber, search) {
				return $q(function(resolve, reject) {
					$http({
						method : 'GET',
						url : '/api/Cart/MyRequests',
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