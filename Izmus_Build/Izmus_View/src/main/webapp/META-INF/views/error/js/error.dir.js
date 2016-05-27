angular.module('errorApp').directive('investitError',
		[ function() {
			return {
				restrict : 'E',
				templateUrl : '/views/error/templates/error.html',
				scope :{
					errorMessage : '@',
					anErrorOccurred: '@'
				},
				controller : [ '$scope', function($scope) {

				} ],
				link : function(scope, elem, attr) {
					
				}
			}
		} ]);