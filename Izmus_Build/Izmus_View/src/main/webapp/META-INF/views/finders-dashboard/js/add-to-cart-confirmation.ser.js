/*----------------------------------------------------------------------------------------------------*/
angular.module('findersDashboardApp').factory('addToCartConfirmationDialog',
		[ '$mdMedia', '$mdDialog','triggerAnalysis', function($mdMedia, $mdDialog, triggerAnalysis) {
			return function(ev, startup, iconSrc) {
				var customFullscreen = $mdMedia('xs') || $mdMedia('sm');
				var useFullScreen = ($mdMedia('sm') || $mdMedia('xs')) && customFullscreen;
			    /*----------------------------------------------------------------------------------------------------*/
				var addToCartConfirmationCtrl = function($scope) {
				    $scope.globalAttr = globalAttr;
				    $scope.lang = lang;
				    $scope.iconSrc = iconSrc;
				    $scope.startup = startup;
					/*----------------------------------------------------------------------------------------------------*/
					$scope.cancel = function() {
						$mdDialog.cancel();
					};
					/*----------------------------------------------------------------------------------------------------*/
					$scope.addToCart = function() {
						triggerAnalysis(startup.startupId);
						$mdDialog.cancel();
					};
				}
			    /*----------------------------------------------------------------------------------------------------*/
				$mdDialog.show({
				    controller: addToCartConfirmationCtrl,
				    templateUrl: '/views/finders-dashboard/templates/add-to-cart-confirmation.dialog.html',
				    parent: angular.element(document.body),
				    targetEvent: ev,
				    clickOutsideToClose: true,
				    fullscreen: useFullScreen
				});
			}
		} ]);
/*----------------------------------------------------------------------------------------------------*/
angular.module('findersDashboardApp').factory('triggerAnalysis',
		[ '$q', '$http', '$httpParamSerializer', function($q, $http, $httpParamSerializer) {
			return function(startupId) {
				return $q(function(resolve, reject) {
					$http({
						method : 'POST',
						url : '/api/AvailableStartups/AnalysisRequest',
						headers: {
					        'Content-Type': "application/x-www-form-urlencoded; charset=UTF-8",
					        'Upgrade-Insecure-Requests': "1",
					        'X-CSRF-TOKEN': globalAttr.sessionToken
					    },
					    data : $httpParamSerializer({
					    	startupId: startupId,
					    })
					}).then(function successCallback(response) {
						resolve(response.data);
					}, function errorCallback(response) {
						reject();
					});
				})
			}
		} ]);