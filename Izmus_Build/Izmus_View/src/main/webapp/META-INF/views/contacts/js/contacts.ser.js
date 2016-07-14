/*----------------------------------------------------------------------------------------------------*/
angular.module('contactsApp').factory('addContactDialog',
		[ '$mdMedia', '$mdDialog', function($mdMedia, $mdDialog) {
			return function(ev, okFunction) {
				var customFullscreen = $mdMedia('xs') || $mdMedia('sm');
				var useFullScreen = ($mdMedia('sm') || $mdMedia('xs')) && customFullscreen;
			    /*----------------------------------------------------------------------------------------------------*/
				var addContactCtrl = function($scope) {
				    $scope.globalAttr = globalAttr;
				    $scope.lang = lang;
					/*----------------------------------------------------------------------------------------------------*/
					$scope.ok = function() {
						if (okFunction){
							okFunction($scope.contactType);
						}
						$mdDialog.cancel();
					};
					/*----------------------------------------------------------------------------------------------------*/
					$scope.cancel = function() {
						$mdDialog.cancel();
					};
				}
			    /*----------------------------------------------------------------------------------------------------*/
				$mdDialog.show({
				    controller: addContactCtrl,
				    templateUrl: '/views/contacts/templates/add-contact.dialog.html',
				    parent: angular.element(document.body),
				    targetEvent: ev,
				    clickOutsideToClose: true,
				    fullscreen: useFullScreen
				});
			}
		} ]);

/*----------------------------------------------------------------------------------------------------*/
angular.module('contactsApp').factory('viewStartupContactDialog',
		[ '$mdMedia', '$mdDialog', 'loadStartupContact', function($mdMedia, $mdDialog, loadStartupContact) {
			return function(ev, startupContact) {
				var customFullscreen = $mdMedia('xs') || $mdMedia('sm');
				var useFullScreen = ($mdMedia('sm') || $mdMedia('xs')) && customFullscreen;
			    /*----------------------------------------------------------------------------------------------------*/
				var startupContactCtrl = function($scope) {
				    $scope.globalAttr = globalAttr;
				    $scope.lang = lang;
				    $scope.progressMode = 'indeterminate';
				    $scope.$mdMedia = $mdMedia;
				    /*----------------------------------------------------------------------------------------------------*/
					$scope.contactAttributes = {
							firstName: lang.firstName,
							lastName: lang.lastName,
							mobilePhone: lang.mobilePhone,
							officePhone: lang.officePhone,
							email: lang.email,
							position: lang.position
					};
				    /*----------------------------------------------------------------------------------------------------*/
				    if (startupContact.contactId){
				    	loadStartupContact(startupContact.contactId).then(function(data){
				    		if(data){
				    			$scope.startupContact = data;
				    			$scope.progressMode = '';
				    		}
				    	}, function(){
				    		$scope.startupContact = startupContact;
				    		$scope.progressMode = '';
				    	})
				    }
				    else {
				    	$scope.startupContact = startupContact;
				    	$scope.progressMode = '';
				    }
					/*----------------------------------------------------------------------------------------------------*/
					$scope.ok = function() {
						$mdDialog.cancel();
					};
				}
			    /*----------------------------------------------------------------------------------------------------*/
				$mdDialog.show({
				    controller: startupContactCtrl,
				    templateUrl: '/views/contacts/templates/startup-contact.dialog.html',
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
angular.module('contactsApp').factory('loadStartupContact',
		[ '$q', '$http', function($q, $http) {
			return function(contactId) {
				return $q(function(resolve, reject) {
					$http({
						method : 'GET',
						url : '/api/Contacts/StartupContacts/' + contactId,
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

angular.module('contactsApp').factory('createNewUser',
		[ '$q', '$http', '$httpParamSerializer',function($q, $http, $httpParamSerializer) {
			return function(contactId, userType) {
				return $q(function(resolve, reject) {
					$http({
						method : 'POST',
						url : '/api/Contacts/CreateNewUser',
						headers: {
					        'Content-Type': "application/x-www-form-urlencoded; charset=UTF-8",
					        'Upgrade-Insecure-Requests': "1",
					        'X-CSRF-TOKEN': globalAttr.sessionToken
					    },
					    data : $httpParamSerializer({
					    	_csrf: globalAttr.sessionToken,
					    	contactId: contactId,
					    	userType: userType
					    })
					}).then(function successCallback(response) {
						resolve(response.data);
					}, function errorCallback(error) {
						
					});
				})
			}
		} ]);