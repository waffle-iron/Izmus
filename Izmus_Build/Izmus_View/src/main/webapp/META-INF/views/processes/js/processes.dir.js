angular.module('processesApp').directive('izmusProcesses', ['loadProcesses', '$mdMedia', '$mdDialog', function(loadProcesses, $mdMedia, $mdDialog) {
	return {
		restrict : 'E',
		templateUrl : '/views/processes/templates/processes.html',
		scope: {
			
		},
		controller : ['$scope',function($scope) {
			
		}],
		link : function(scope, elem, attr) {
			scope.globalAttr = globalAttr;
			var customFullscreen = $mdMedia('xs') || $mdMedia('sm');
			var useFullScreen = ($mdMedia('sm') || $mdMedia('xs')) && customFullscreen;
			/*----------------------------------------------------------------------------------------------------*/
			loadProcesses().then(function(result){
				scope.processes = result;
			});
			/*----------------------------------------------------------------------------------------------------*/
			scope.loadPreview = function(ev, process){
				/*----------------------------------------------------------------------------------------------------*/
				$mdDialog.show({
				    controller: scope.loadPreviewCtrl,
				    templateUrl: '/views/processes/templates/process-preview.html',
				    parent: angular.element(document.body),
				    targetEvent: ev,
				    clickOutsideToClose: true,
				    fullscreen: useFullScreen,
				    locals: {
				           process: process,
				    }
				});
			}
		    /*----------------------------------------------------------------------------------------------------*/
			scope.loadPreviewCtrl = function($scope, $mdDialog, process) {
				$scope.parentScope = scope;
				$scope.process = process;
				/*----------------------------------------------------------------------------------------------------*/
				$scope.cancel = function() {
					$mdDialog.cancel();
				};
			}
		}
	}
} ]);