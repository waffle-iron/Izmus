angular.module('izmusLandingPageApp').directive('izmusNewsPage', 
		['$mdMedia', function($mdMedia) {
	return {
		restrict : 'E',
		templateUrl : '/views-public/landing-page/templates/news-page.html',
		controller : ['$scope',function($scope) {
			$scope.globalAttr = globalAttr;
			$scope.lang = lang;
			/*----------------------------------------------------------------------------------------------------*/
			$scope.isSmallDevice = function(){
				return (!$mdMedia('gt-md'));
			}
			/*----------------------------------------------------------------------------------------------------*/
			$scope.getVideoStyle = function(){
				return "{'height':'360px', 'width' : '640px'}"
			}
		}],
		link : function(scope, elem, attr) {

		}
	}
} ]);