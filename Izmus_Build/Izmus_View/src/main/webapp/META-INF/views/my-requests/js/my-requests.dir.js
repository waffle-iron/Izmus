angular.module('myRequestsApp').directive('myRequestsWindow', 
		[function() {
	return {
		restrict : 'E',
		templateUrl : '/views/my-requests/templates/my-requests.html',
		controller : ['$scope',function($scope) {
			$scope.globalAttr = globalAttr;
			$scope.lang = lang;
		}],
		link : function(scope, elem, attr) {
			
		}
	}
} ]);