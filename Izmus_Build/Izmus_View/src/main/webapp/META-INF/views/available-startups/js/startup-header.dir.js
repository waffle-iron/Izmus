angular.module('availableStartupsApp').directive('startupHeader', 
		['$mdMedia','avatarDialog', function($mdMedia, avatarDialog) {
	return {
		restrict : 'E',
		require: '^^startupDetails',
		templateUrl : '/views/available-startups/templates/startup-header.html',
		scope: {
			selectedStartup: '='
		},
		controller : ['$scope',function($scope) {
			$scope.globalAttr = globalAttr;
			$scope.lang = lang;
			$scope.progressMode = '';
			/*----------------------------------------------------------------------------------------------------*/
			$scope.isSmallDevice = function(){
				return (!$mdMedia('gt-md'));
			}
			/*----------------------------------------------------------------------------------------------------*/
			$scope.changeLogo = function(ev){
				avatarDialog(ev, function(croppedImage){
					$scope.selectedStartup.logo = croppedImage;
				});
			}
		}],
		link : function(scope, elem, attr, parentCtrl) {

		}
	}
} ]);