angular.module('findersDashboardApp').directive('findersDashboardWindow', 
		[function() {
	return {
		restrict : 'E',
		templateUrl : '/views/finders-dashboard/templates/finders-dashboard.html',
		controller : ['$scope',function($scope) {
			$scope.globalAttr = globalAttr;
			$scope.lang = lang;
		}],
		link : function(scope, elem, attr) {
			
		}
	}
} ]);