/*----------------------------------------------------------------------------------------------------*/
angular.module('contactsApp').factory('contactViewDialog',
		[ '$mdMedia', '$mdDialog', function($mdMedia, $mdDialog) {
			return function(ev, contact, exportFunction, saveContact) {
				var customFullscreen = $mdMedia('xs') || $mdMedia('sm');
				var useFullScreen = ($mdMedia('sm') || $mdMedia('xs')) && customFullscreen;
			    /*----------------------------------------------------------------------------------------------------*/
				var contactViewCtrl = function($scope) {
				    $scope.globalAttr = globalAttr;
				    $scope.lang = lang;
				    $scope.contact = contact;
				    $scope.saveContact = saveContact;
				    /*----------------------------------------------------------------------------------------------------*/
					$scope.export = function() {
						if (exportFunction){
							$mdDialog.cancel();
							exportFunction();
						}
					};
					/*----------------------------------------------------------------------------------------------------*/
					$scope.ok = function() {
						$mdDialog.cancel();
					};
				    /*----------------------------------------------------------------------------------------------------*/
					$scope.save = function(){
						if (saveContact){
							$mdDialog.cancel();
							saveContact();
						}
					}
				}
			    /*----------------------------------------------------------------------------------------------------*/
				$mdDialog.show({
				    controller: contactViewCtrl,
				    templateUrl: '/views/startup-assessment/templates/contact-view.dialog.html',
				    parent: angular.element(document.body),
				    targetEvent: ev,
				    clickOutsideToClose: true,
				    fullscreen: useFullScreen
				});
			}
		} ]);
/*----------------------------------------------------------------------------------------------------*/
angular.module('contactsApp').factory('loadAllContacts',
		[ '$q', '$http', function($q, $http) {
			return function() {
				return $q(function(resolve, reject) {
					$http({
						method : 'GET',
						url : '/api/Contacts',
					}).then(function successCallback(response) {
						if (response.data) {
							resolve(response.data);
						} else {
							reject();
						}
					}, function errorCallback(response) {
						reject();
					});
				})
			}
		} ]);

/*----------------------------------------------------------------------------------------------------*/
angular.module('contactsApp').factory('saveGeneralContact',
		[ '$q', '$http', '$httpParamSerializer', function($q, $http, $httpParamSerializer) {
			return function(contact) {
				return $q(function(resolve, reject) {
					$http({
						method : 'POST',
						url : '/api/Contacts',
						headers: {
					        'Content-Type': "application/x-www-form-urlencoded; charset=UTF-8",
					        'Upgrade-Insecure-Requests': "1",
					        'X-CSRF-TOKEN': globalAttr.sessionToken
					    },
					    data : $httpParamSerializer({
					    	contact: contact,
					    })
					}).then(function successCallback(response) {
						resolve(response.data);
					}, function errorCallback(response) {
						reject();
					});
				})
			}
		} ]);