angular.module('izmusMainWindowApp', [ 'ngMaterial', 'ngMessages' ]);
/*----------------------------------------------------------------------------------------------------*/
/**
 * Directive
 */
angular
		.module('izmusMainWindowApp')
		.directive(
				'izmusMainWindow',
				[function() {
							return {
								restrict : 'E',
								templateUrl : '/views/core/izmus-main-window/templates/izmus-main-window.html',
								transclude : {
									 'sidenavContent': '?sidenavContent',
									 'mainContent': '?mainContent'
								},
								scope : {
									mainWindowTitle: '@',
									sidenavCtrl: '='
								},
								controller : ['$scope',function($scope) {
									/*----------------------------------------------------------------------------------------------------*/
									$scope.windowTopBarMinimized = false;
									/*----------------------------------------------------------------------------------------------------*/
									$scope.toggleWindowTopBar = function(){
										$scope.windowTopBarMinimized = !$scope.windowTopBarMinimized;
									}
									/*----------------------------------------------------------------------------------------------------*/
									$scope.isWindowTopBarMinimized = function(){
										return $scope.windowTopBarMinimized;
									}
								}],
								link : function(scope, elem, attr) {
									
								}
							}
						} ]);