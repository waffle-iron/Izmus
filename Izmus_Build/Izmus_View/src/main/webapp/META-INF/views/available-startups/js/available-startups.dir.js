angular.module('availableStartupsApp').directive('availableStartups',
		[ function() {
			return {
				restrict : 'E',
				templateUrl : '/views/available-startups/templates/available-startups.html',
				controller : [ '$scope', function($scope) {
					$scope.lang = lang;
					$scope.globalAttr = globalAttr;
				} ],
				link : function(scope, elem, attr) {
					
				}
			}
		} ]);