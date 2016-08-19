angular.module('investorsDashboardApp').directive('investorsDashboardWindow', 
		[function() {
	return {
		restrict : 'E',
		templateUrl : '/views/investors-dashboard/templates/investors-dashboard.html',
		controller : ['$scope',function($scope) {
			$scope.globalAttr = globalAttr;
			$scope.lang = lang;
		}],
		link : function(scope, elem, attr) {
			
		}
	}
} ]);