angular.module('availableStartupsApp').directive('availableStartups',
		[ 'loadAllAvailableStartups','$timeout','$mdSidenav', function(loadAllAvailableStartups, $timeout, $mdSidenav) {
			return {
				restrict : 'E',
				templateUrl : '/views/available-startups/templates/available-startups.html',
				controller : [ '$scope', function($scope) {
					$scope.lang = lang;
					$scope.globalAttr = globalAttr;
					/*----------------------------------------------------------------------------------------------------*/
					$scope.sidenavCtrl = {
							/*----------------------------------------------------------------------------------------------------*/
							progressMode: 'indeterminate',
							/*----------------------------------------------------------------------------------------------------*/
							toggleSidenav: function(){
								$mdSidenav(
								'mainWidowSidenav')
								.toggle();
							},
					};
				} ],
				link : function(scope, elem, attr) {
					
				}
			}
		} ]);