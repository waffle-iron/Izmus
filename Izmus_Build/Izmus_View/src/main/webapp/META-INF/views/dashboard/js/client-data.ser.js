var clientDataService = angular.module('clientDataService', []);
clientDataService.factory('getPortfolioData', ['$q', '$http', '$timeout',
		function($q, $http, $timeout) {
			return function() {
				return $q(function(resolve, reject) {
					$http({
						method : 'GET',
						url : '/api/Portfolios/ClientPortfolio'
					}).then(function successCallback(response) {
						resolve(response.data);
					}, function errorCallback(response){
						reject(response.data);
					});
				})
			}
		} ]);

