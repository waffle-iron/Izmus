/*----------------------------------------------------------------------------------------------------*/
angular.module('contactsApp').factory('viewGeneralContactDialog',
		[ '$mdMedia', '$mdDialog', 'loadGeneralContact', 'avatarDialog',function($mdMedia, $mdDialog, loadGeneralContact, avatarDialog) {
			return function(ev, generalContact, saveFunction, reloadAfterAvatar, reloaded) {
				var customFullscreen = $mdMedia('xs') || $mdMedia('sm');
				var useFullScreen = ($mdMedia('sm') || $mdMedia('xs')) && customFullscreen;
			    /*----------------------------------------------------------------------------------------------------*/
				var generalContactCtrl = function($scope) {
				    $scope.globalAttr = globalAttr;
				    $scope.lang = lang;
				    $scope.progressMode = 'indeterminate';
				    $scope.$mdMedia = $mdMedia;
				    /*----------------------------------------------------------------------------------------------------*/
					$scope.generalContactAttributes = {
							firstName: lang.firstName,
							lastName: lang.lastName,
							mobilePhone: lang.mobilePhone,
							officePhone: lang.officePhone,
							email: lang.email,
							position: lang.position
					};
					 /*----------------------------------------------------------------------------------------------------*/
					$scope.changeContactAvatar = function(ev){
						avatarDialog(ev, function(croppedImage){
							$scope.investorContact.contactAvatar = croppedImage;
							if (reloadAfterAvatar){
								reloadAfterAvatar($scope.investorContact);
							}
						}, function(){
							reloadAfterAvatar($scope.investorContact);
						});
					}
				    /*----------------------------------------------------------------------------------------------------*/
				    if (generalContact.contactId && !reloaded){
				    	loadGeneralContact(generalContact.contactId).then(function(data){
				    		if(data){
				    			$scope.generalContact = data;
				    			$scope.progressMode = '';
				    		}
				    	}, function(){
				    		$scope.generalContact = generalContact;
				    		$scope.progressMode = '';
				    	})
				    }
				    else {
				    	$scope.generalContact = generalContact;
				    	$scope.progressMode = '';
				    }
					/*----------------------------------------------------------------------------------------------------*/
					$scope.ok = function() {
						$mdDialog.cancel();
					};
					/*----------------------------------------------------------------------------------------------------*/
					$scope.save = function() {
						if (saveFunction){
							saveFunction($scope.generalContact);
						}
						$mdDialog.cancel();
					};
				}
			    /*----------------------------------------------------------------------------------------------------*/
				$mdDialog.show({
				    controller: generalContactCtrl,
				    templateUrl: '/views/contacts/templates/general-contact.dialog.html',
				    parent: angular.element(document.body),
				    targetEvent: ev,
				    clickOutsideToClose: true,
				    fullscreen: useFullScreen
				});
			}
		} ]);
/*----------------------------------------------------------------------------------------------------*/
angular.module('contactsApp').factory('saveGeneralContact',
		[ '$q', '$http', '$httpParamSerializer', function($q, $http, $httpParamSerializer) {
			return function(generalContact) {
				return $q(function(resolve, reject) {
					$http({
						method : 'POST',
						url : '/api/Contacts/GeneralContacts',
						headers: {
					        'Content-Type': "application/x-www-form-urlencoded; charset=UTF-8",
					        'Upgrade-Insecure-Requests': "1",
					        'X-CSRF-TOKEN': globalAttr.sessionToken
					    },
					    data : $httpParamSerializer({
					    	generalContact: generalContact,
					    })
					}).then(function successCallback(response) {
						resolve(response.data);
					}, function errorCallback(response) {
						reject();
					});
				})
			}
		} ]);
/*----------------------------------------------------------------------------------------------------*/
angular.module('contactsApp').factory('loadGeneralContact',
		[ '$q', '$http', function($q, $http) {
			return function(contactId) {
				return $q(function(resolve, reject) {
					$http({
						method : 'GET',
						url : '/api/Contacts/GeneralContacts/' + contactId,
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