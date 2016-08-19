angular.module('startupAssessmentApp').directive('izmusStartupAssessment', [ 
        '$mdSidenav', '$mdMedia','$mdDialog', 'loadStartupData', 'saveStartupData','$mdToast','loadFinancialIndicatorTypes',
        function($mdSidenav, $mdMedia, $mdDialog, loadStartupData, saveStartupData, $mdToast, loadFinancialIndicatorTypes) {
	return {
		restrict : 'E',
		templateUrl : '/views/startup-assessment/templates/startup-assessment.html',
		scope :{
			
		},
		controller : ['$scope',function($scope) {
			$scope.detailsProgress = {
					progressMode: ''
			};
			/*----------------------------------------------------------------------------------------------------*/
			this.setSelectedStartup = function(selectedStartup){
				$scope.detailsProgress.progressMode = 'indeterminate';
				loadStartupData(selectedStartup.startupId).then(function(data){
					$scope.selectedStartup = data[0];
					$scope.detailsProgress.progressMode = '';
				})
				$mdSidenav('mainWidowSidenav').toggle();
			}
			/*----------------------------------------------------------------------------------------------------*/
			$scope.globalAttr = globalAttr;
			$scope.lang = lang;
			$scope.customFullscreen = $mdMedia('xs') || $mdMedia('sm');
			$scope.financialIndicators = [];
			/*----------------------------------------------------------------------------------------------------*/
			loadFinancialIndicatorTypes().then(function(response){
				$scope.financialIndicatorTypes = response;
			},function(response){
				$scope.financialIndicatorTypes = [];
			});
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
					saveStartupData(newStartup).then(function(response){
						if (response.result != 'success'){
							scope.showMessage(scope.lang.saveFail);
						}
						else {
							scope.showMessage(scope.lang.saveSuccess);
						}
					},
					function(){
						scope.showMessage(scope.lang.saveFail);
					});
					scope.sidenavCtrl.startupDataList.push(newStartup);
					$mdDialog.cancel();
				};
			}
			/*----------------------------------------------------------------------------------------------------*/
			scope.showMessage = function(message){
				$mdToast.show({
				      controller: 'toastCtrl',
				      templateUrl: '/views/core/toast/templates/toast.html',
				      parent : angular.element(elem),
				      hideDelay: 2600,
				      position: 'top right',
				      locals: {
				    	  message: message
				      }
				    });
			}
		}
	}
} ]);