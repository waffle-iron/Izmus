angular.module('wishListApp').directive('wishListWindow', 
		[function() {
	return {
		restrict : 'E',
		templateUrl : '/views/wish-list/templates/wish-list.html',
		controller : ['$scope',function($scope) {
			$scope.globalAttr = globalAttr;
			$scope.lang = lang;
		}],
		link : function(scope, elem, attr) {
			
		}
	}
} ]);