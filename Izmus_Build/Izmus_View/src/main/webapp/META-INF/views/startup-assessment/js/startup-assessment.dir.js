angular.module('startupAssessmentApp').directive('investitStartupAssessment', [ 
        '$mdSidenav', '$mdMedia','$mdDialog', 'loadStartupData', 'saveStartupData',
        function($mdSidenav, $mdMedia, $mdDialog, loadStartupData, saveStartupData) {
	return {
		restrict : 'E',
		templateUrl : '/views/startup-assessment/templates/startup-assessment.html',
		scope :{
			
		},
		controller : ['$scope',function($scope) {
			this.setSelectedStartup = function(selectedStartup){
				$scope.selectedStartup = selectedStartup;
				$mdSidenav('mainWidowSidenav').toggle();
			}
			$scope.globalAttr = globalAttr;
			$scope.lang = lang;
			$scope.customFullscreen = $mdMedia('xs') || $mdMedia('sm');
			/*----------------------------------------------------------------------------------------------------*/
			$scope.startupGeneralAttributes = {
					startupName: lang.startupName,
					sector: lang.sector,
					address: lang.address,
					officePhone: lang.officePhone,
					site: lang.site
			};
			/*----------------------------------------------------------------------------------------------------*/
			$scope.startupGeneralFinancials = {
					requestedFunds: lang.requestedFunds,
					achivedFunds: lang.achivedFunds,
					startupOwnValuation: lang.startupOwnValuation,
					izmusValuation: lang.izmusValuation
			};
			/*----------------------------------------------------------------------------------------------------*/
			$scope.sidenavCtrl = {
					/*----------------------------------------------------------------------------------------------------*/
					progressMode: 'indeterminate',
					sidenavTitle: lang.startups,
					/*----------------------------------------------------------------------------------------------------*/
					toggleSidenav: function(){
						$mdSidenav(
						'mainWidowSidenav')
						.toggle();
					},
					/*----------------------------------------------------------------------------------------------------*/
					showAddDialog: function(ev) {
						var useFullScreen = ($mdMedia('sm') || $mdMedia('xs')) && $scope.customFullscreen;
					    $mdDialog.show({
					      controller: $scope.addStartupController,
					      templateUrl: '/views/startup-assessment/templates/add-startup-dialog.html',
					      parent: angular.element(document.body),
					      targetEvent: ev,
					      clickOutsideToClose: true,
					      fullscreen: useFullScreen
					    });
					    $scope.$watch(function() {
					      return $mdMedia('xs') || $mdMedia('sm');
					    }, function(wantsFullScreen) {
					      $scope.customFullscreen = (wantsFullScreen === true);
					    });
					}
			};
			/*----------------------------------------------------------------------------------------------------*/
			loadStartupData().then(function(result) {
				$mdSidenav('mainWidowSidenav').toggle();
				$scope.sidenavCtrl.startupDataList = result;
				$scope.sidenavCtrl.progressMode = '';
			}, function(error) {
				$scope.sidenavCtrl.progressMode = ''
			});
		}],
		link : function(scope, elem, attr) {
			/*----------------------------------------------------------------------------------------------------*/
			scope.addStartupController = function($scope, $mdDialog) {
				$scope.parentScope = scope;
				/*----------------------------------------------------------------------------------------------------*/
				$scope.cancel = function() {
					$mdDialog.cancel();
				};
				/*----------------------------------------------------------------------------------------------------*/
				$scope.ok = function() {
					var newStartup = {
							startupName: $scope.startupName	
					};
					saveStartupData(newStartup);
					scope.sidenavCtrl.startupDataList.push(newStartup);
					$mdDialog.cancel();
				};
			}
		}
	}
} ]);