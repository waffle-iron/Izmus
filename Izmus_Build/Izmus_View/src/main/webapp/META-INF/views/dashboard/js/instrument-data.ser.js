var instrumentDataService = angular.module('instrumentDataService', []);
instrumentDataService.factory('getInstrumentData', [ '$q', '$http', '$timeout',
		function($q, $http, $timeout) {
			return function(instrumentNumber) {
				return $q(function(resolve, reject) {
					$http({
						method : 'GET',
						url : '/api/Instruments/' + instrumentNumber
					}).then(function successCallback(response) {
						resolve(response.data);
					}, function errorCallback(response) {
						reject(response.data);
					});
				})
			}
		} ]);
instrumentDataService.factory('getProposalAseetClassData', [ '$q', '$http', '$timeout',
		function($q, $http, $timeout) {
			return function() {
				return $q(function(resolve, reject) {
					$http({
						method : 'GET',
						url : '/api/Instruments/ProposalAssetClasses'
					}).then(function successCallback(response) {
						resolve(response.data);
					}, function errorCallback(response) {
						reject(response.data);
					});
				})
			}
		} ]);