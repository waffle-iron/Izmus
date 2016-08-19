angular.module('findersDashboardApp').directive('startupDetails', 
		['$mdMedia', function($mdMedia) {
	return {
		restrict : 'E',
		templateUrl : '/views/finders-dashboard/templates/startup-details.html',
		scope: {
			startup: '=',
		},
		controller : ['$scope',function($scope) {
			$scope.globalAttr = globalAttr;
			$scope.lang = lang;
			/*----------------------------------------------------------------------------------------------------*/
			$scope.isSmallDevice = function(){
				return (!$mdMedia('gt-md'));
			}
		}],
		link : function(scope, elem, attr, parentCtrl) {

		}
	}
} ]);