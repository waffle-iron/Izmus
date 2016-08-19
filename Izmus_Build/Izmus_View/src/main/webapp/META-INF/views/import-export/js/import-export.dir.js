angular.module('importExportApp').directive('importExport',
		[ function() {
			return {
				restrict : 'E',
				templateUrl : '/views/import-export/templates/import-export.html',
				controller : [ '$scope', function($scope) {
					$scope.lang = lang;
					$scope.globalAttr = globalAttr;
				} ],
				link : function(scope, elem, attr) {
					
				}
			}
		} ]);