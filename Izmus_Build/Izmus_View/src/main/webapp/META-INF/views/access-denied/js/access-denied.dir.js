angular.module('accessDeniedApp').directive('investitAccessDenied',
		[ function() {
			return {
				restrict : 'E',
				templateUrl : '/views/access-denied/templates/access-denied.html',
				scope :{
					accessDeniedMessage : '@',
					accessDenied : '@',
				},
				controller : [ '$scope', function($scope) {

				} ],
				link : function(scope, elem, attr) {
					
				}
			}
		} ]);