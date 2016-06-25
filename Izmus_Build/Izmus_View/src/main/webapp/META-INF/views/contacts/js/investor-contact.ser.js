/*----------------------------------------------------------------------------------------------------*/
angular.module('contactsApp').factory('viewInvestorContactDialog',
		[ '$mdMedia', '$mdDialog', 'loadInvestorContact', 'avatarDialog', function($mdMedia, $mdDialog, loadInvestorContact, avatarDialog) {
			return function(ev, investorContact, saveFunction, reloadAfterAvatar, reloaded) {
				var customFullscreen = $mdMedia('xs') || $mdMedia('sm');
				var useFullScreen = ($mdMedia('sm') || $mdMedia('xs')) && customFullscreen;
			    /*----------------------------------------------------------------------------------------------------*/
				var investorContactCtrl = function($scope) {
				    $scope.globalAttr = globalAttr;
				    $scope.lang = lang;
				    $scope.progressMode = 'indeterminate';
				    $scope.$mdMedia = $mdMedia;
				    /*----------------------------------------------------------------------------------------------------*/
					$scope.investorContactAttributes = {
							firstName: lang.firstName,
							lastName: lang.lastName,
							mobilePhone: lang.mobilePhone,
							officePhone: lang.officePhone,
							email: lang.email,
							position: lang.position,
							founded: lang.founded,
							assetsUnderManagement: lang.assetsUnderManagement,
							averageInvestmentSize: lang.averageInvestmentSize,
							numberOfPastInvestments: lang.noPastInvestments,
							numberOfPastExits: lang.noPastExits,
							howWeMet: lang.howWeMet
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
				    if (investorContact.contactId && !reloaded){
				    	loadInvestorContact(investorContact.contactId).then(function(data){
				    		if(data){
				    			$scope.investorContact = data;
				    			$scope.progressMode = '';
				    		}
				    	}, function(){
				    		$scope.investorContact = investorContact;
				    		$scope.progressMode = '';
				    	})
				    }
				    else {
				    	$scope.investorContact = investorContact;
				    	$scope.progressMode = '';
				    }
					/*----------------------------------------------------------------------------------------------------*/
					$scope.ok = function() {
						$mdDialog.cancel();
					};
					/*----------------------------------------------------------------------------------------------------*/
					$scope.save = function() {
						if (saveFunction){
							saveFunction($scope.investorContact);
						}
						$mdDialog.cancel();
					};
				}
			    /*----------------------------------------------------------------------------------------------------*/
				$mdDialog.show({
				    controller: investorContactCtrl,
				    templateUrl: '/views/contacts/templates/investor-contact.dialog.html',
				    parent: angular.element(document.body),
				    targetEvent: ev,
				    clickOutsideToClose: true,
				    fullscreen: useFullScreen
				});
			}
		} ]);
/*----------------------------------------------------------------------------------------------------*/
angular.module('contactsApp').factory('saveInvestorContact',
		[ '$q', '$http', '$httpParamSerializer', function($q, $http, $httpParamSerializer) {
			return function(investorContact) {
				return $q(function(resolve, reject) {
					$http({
						method : 'POST',
						url : '/api/Contacts/InvestorContacts',
						headers: {
					        'Content-Type': "application/x-www-form-urlencoded; charset=UTF-8",
					        'Upgrade-Insecure-Requests': "1",
					        'X-CSRF-TOKEN': globalAttr.sessionToken
					    },
					    data : $httpParamSerializer({
					    	investorContact: investorContact,
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
angular.module('contactsApp').factory('loadInvestorContact',
		[ '$q', '$http', function($q, $http) {
			return function(contactId) {
				return $q(function(resolve, reject) {
					$http({
						method : 'GET',
						url : '/api/Contacts/InvestorContacts/' + contactId,
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