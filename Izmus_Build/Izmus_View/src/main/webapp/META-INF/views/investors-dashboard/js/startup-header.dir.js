angular.module('investorsDashboardApp').directive('startupHeader', 
		['$mdMedia', function($mdMedia) {
	return {
		restrict : 'E',
		templateUrl : '/views/investors-dashboard/templates/startup-header.html',
		scope: {
			selectedStartup: '=',
			iconSrc: '@'
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