/*----------------------------------------------------------------------------------------------------*/
angular.module('contactsApp').factory('viewFinderContactDialog',
		[ '$mdMedia', '$mdDialog', 'loadFinderContact', 'avatarDialog', 
		  function($mdMedia, $mdDialog, loadFinderContact, avatarDialog) {
			return function(ev, finderContact, saveFunction, reloadAfterAvatar, reloaded, createNewUser) {
				var customFullscreen = $mdMedia('xs') || $mdMedia('sm');
				var useFullScreen = ($mdMedia('sm') || $mdMedia('xs')) && customFullscreen;
			    /*----------------------------------------------------------------------------------------------------*/
				var finderContactCtrl = function($scope) {
				    $scope.globalAttr = globalAttr;
				    $scope.lang = lang;
				    $scope.progressMode = 'indeterminate';
				    $scope.$mdMedia = $mdMedia;
				    /*----------------------------------------------------------------------------------------------------*/
					$scope.finderContactAttributes = {
							firstName: lang.firstName,
							lastName: lang.lastName,
							mobilePhone: lang.mobilePhone,
							officePhone: lang.officePhone,
							email: lang.email,
							position: lang.position
					};
					/*----------------------------------------------------------------------------------------------------*/
					$scope.finderCompanyAttributes = {
							companyName: lang.companyName,
					}
					 /*----------------------------------------------------------------------------------------------------*/
					$scope.changeContactAvatar = function(ev){
						avatarDialog(ev, function(croppedImage){
							$scope.finderContact.contactAvatar = croppedImage;
							if (reloadAfterAvatar){
								reloadAfterAvatar($scope.finderContact);
							}
						}, function(){
							reloadAfterAvatar($scope.finderContact);
						});
					}
					/*----------------------------------------------------------------------------------------------------*/
					$scope.changeCompanyAvatar = function(ev){
						avatarDialog(ev, function(croppedImage){
							$scope.finderContact.companyAvatar = croppedImage;
							if (reloadAfterAvatar){
								reloadAfterAvatar($scope.finderContact);
							}
						}, function(){
							reloadAfterAvatar($scope.finderContact);
						});
					}
				    /*----------------------------------------------------------------------------------------------------*/
				    if (finderContact.contactId && !reloaded){
				    	loadFinderContact(finderContact.contactId).then(function(data){
				    		if(data){
				    			$scope.finderContact = data;
				    			$scope.progressMode = '';
				    		}
				    	}, function(){
				    		$scope.finderContact = finderContact;
				    		$scope.progressMode = '';
				    	})
				    }
				    else {
				    	$scope.finderContact = finderContact;
				    	$scope.progressMode = '';
				    }
					/*----------------------------------------------------------------------------------------------------*/
					$scope.ok = function() {
						$mdDialog.cancel();
					};
					/*----------------------------------------------------------------------------------------------------*/
					$scope.checkCreateUserValidation = function(){
						if (finderContact.contactId && 
								!finderContact.entityId &&
								finderContact.firstName &&
								finderContact.lastName &&
								finderContact.email) return false;
						return true;
					}
					/*----------------------------------------------------------------------------------------------------*/
					$scope.createContactUser = function() {
						if (createNewUser){
							createNewUser(finderContact.contactId, "Izmus Finder");
						}
						$mdDialog.cancel();
					};
					/*----------------------------------------------------------------------------------------------------*/
					$scope.save = function() {
						if (saveFunction){
							saveFunction($scope.finderContact);
						}
						$mdDialog.cancel();
					};
				}
			    /*----------------------------------------------------------------------------------------------------*/
				$mdDialog.show({
				    controller: finderContactCtrl,
				    templateUrl: '/views/contacts/templates/finder-contact.dialog.html',
				    parent: angular.element(document.body),
				    targetEvent: ev,
				    clickOutsideToClose: true,
				    fullscreen: useFullScreen
				});
			}
		} ]);
/*----------------------------------------------------------------------------------------------------*/
angular.module('contactsApp').factory('saveFinderContact',
		[ '$q', '$http', '$httpParamSerializer', function($q, $http, $httpParamSerializer) {
			return function(finderContact) {
				return $q(function(resolve, reject) {
					$http({
						method : 'POST',
						url : '/api/Contacts/FinderContacts',
						headers: {
					        'Content-Type': "application/x-www-form-urlencoded; charset=UTF-8",
					        'Upgrade-Insecure-Requests': "1",
					        'X-CSRF-TOKEN': globalAttr.sessionToken
					    },
					    data : $httpParamSerializer({
					    	finderContact: finderContact,
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
angular.module('contactsApp').factory('loadFinderContact',
		[ '$q', '$http', function($q, $http) {
			return function(contactId) {
				return $q(function(resolve, reject) {
					$http({
						method : 'GET',
						url : '/api/Contacts/FinderContacts/' + contactId,
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