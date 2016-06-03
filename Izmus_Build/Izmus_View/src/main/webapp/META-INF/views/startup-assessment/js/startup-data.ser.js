angular.module('startupAssessmentApp').factory('loadStartupData',
		[ '$q', '$http', function($q, $http) {
			return function() {
				return $q(function(resolve, reject) {
					$http({
						method : 'GET',
						url : '/api/StartupAssessment/StartupAssessmentData'
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
angular.module('startupAssessmentApp').factory('saveStartupData',
		[ '$q', '$http', '$httpParamSerializer', function($q, $http, $httpParamSerializer) {
			return function(startupData) {
				return $q(function(resolve, reject) {
					$http({
						method : 'POST',
						url : '/api/StartupAssessment/SaveStartupData',
						headers: {
					        'Content-Type': "application/x-www-form-urlencoded; charset=UTF-8",
					        'Upgrade-Insecure-Requests': "1",
					        'X-CSRF-TOKEN': globalAttr.sessionToken
					    },
					    data : $httpParamSerializer({
					    	startupData: startupData
					    })
					}).then(function successCallback() {
						resolve();
					}, function errorCallback(response) {
						reject();
					});
				})
			}
		} ]);
/*----------------------------------------------------------------------------------------------------*/
angular.module('startupAssessmentApp').factory('deleteAdditionalDocument',
		[ '$q', '$http', '$httpParamSerializer', function($q, $http, $httpParamSerializer) {
			return function(documentId, startupId) {
				return $q(function(resolve, reject) {
					$http({
						method : 'DELETE',
						url : '/api/StartupAssessment/AdditionalDocument',
						headers: {
					        'Content-Type': "application/x-www-form-urlencoded; charset=UTF-8",
					        'Upgrade-Insecure-Requests': "1",
					        'X-CSRF-TOKEN': globalAttr.sessionToken
					    },
					    params: {
					    	documentId: documentId,
					    	startupId: startupId}
					}).then(function successCallback() {
						resolve();
					}, function errorCallback(response) {
						reject();
					});
				})
			}
		} ]);
/*----------------------------------------------------------------------------------------------------*/
angular.module('startupAssessmentApp').factory('loadDefaultScoreCard',
		[ '$q', '$http', function($q, $http) {
			return function(startupId) {
				return $q(function(resolve, reject) {
					$http({
						method : 'GET',
						url : '/api/StartupAssessment/DefaultScoreCard',
						params: {startupId: startupId}
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
angular.module('startupAssessmentApp').factory('exportScoreCardReport',
		[ '$q', '$http', '$httpParamSerializer', function($q, $http, $httpParamSerializer) {
			return function(scoreCard, startup, additionalDocuments) {
				return $q(function(resolve, reject) {
					$http({
						method : 'POST',
						url : '/api/StartupAssessment/ScoreCardReport',
						headers: {
					        'Content-Type': "application/x-www-form-urlencoded; charset=UTF-8",
					        'Upgrade-Insecure-Requests': "1",
					        'X-CSRF-TOKEN': globalAttr.sessionToken
					    },
					    data : $httpParamSerializer({
					    	scoreCard: scoreCard,
					    	startup: startup,
					    	additionalDocuments: additionalDocuments
					    })
					}).then(function successCallback(response) {
						window.location = "/Export/StartupScoreCardReport/" + response.data.parameters;
						resolve();
					}, function errorCallback(response) {
						reject();
					});
				})
			}
		} ]);
/*----------------------------------------------------------------------------------------------------*/
angular.module('startupAssessmentApp').factory('emailScoreCardReport',
		[ '$q', '$http', '$httpParamSerializer', function($q, $http, $httpParamSerializer) {
			return function(scoreCard, startup, emails, additionalDocuments) {
				return $q(function(resolve, reject) {
					$http({
						method : 'POST',
						url : '/api/StartupAssessment/EmailScoreCardReport',
						headers: {
					        'Content-Type': "application/x-www-form-urlencoded; charset=UTF-8",
					        'Upgrade-Insecure-Requests': "1",
					        'X-CSRF-TOKEN': globalAttr.sessionToken
					    },
					    data : $httpParamSerializer({
					    	scoreCard: scoreCard,
					    	startup: startup,
					    	emails: emails,
					    	additionalDocuments: additionalDocuments
					    })
					}).then(function successCallback(response) {
						resolve();
					}, function errorCallback(response) {
						reject();
					});
				})
			}
		} ]);