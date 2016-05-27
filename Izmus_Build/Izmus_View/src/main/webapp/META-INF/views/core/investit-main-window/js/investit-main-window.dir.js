angular.module('investitMainWindowApp', [ 'ngMaterial', 'ngMessages' ]);
/*----------------------------------------------------------------------------------------------------*/
/**
 * Directive
 */
angular
		.module('investitMainWindowApp')
		.directive(
				'investitMainWindow',
				[function() {
							return {
								restrict : 'E',
								templateUrl : '/views/core/investit-main-window/templates/investit-main-window.html',
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