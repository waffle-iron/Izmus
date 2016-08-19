angular.module('availableStartupsApp').directive('startupDetails', 
		[function() {
	return {
		restrict : 'E',
		require: '^^availableStartups',
		templateUrl : '/views/available-startups/templates/startup-details.html',
		scope: {
			selectedStartup: '='
		},
		controller : ['$scope',function($scope) {
			$scope.globalAttr = globalAttr;
			$scope.lang = lang;
		}],
		link : function(scope, elem, attr, parentCtrl) {

		}
	}
} ]);