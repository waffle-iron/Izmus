angular.module('proposalEngineApp').directive('investitProposalEngine', [function() {
	return {
		restrict : 'E',
		templateUrl : '/views/proposal-engine/templates/proposal-engine.html',
		scope: {
			
		},
		controller : ['$scope',function($scope) {
			
		}],
		link : function(scope, elem, attr) {
			scope.obj = {};
			scope.onDragComplete=function(data,evt){
				console.log("drag success, data:", data);
			}
			scope.onDropComplete=function(data,evt){
				console.log("drop success, data:", data);
			}
			scope.test = function(data, text){
				console.log('test ' + text);
			}
		}
	}
} ]);