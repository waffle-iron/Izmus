/*----------------------------------------------------------------------------------------------------*/
angular.module('contactsApp').factory('viewInvestorContactDialog',
		[ '$mdMedia', '$mdDialog', 'loadInvestorContact', 'avatarDialog','$mdConstant', 
		  function($mdMedia, $mdDialog, loadInvestorContact, avatarDialog, $mdConstant) {
			return function(ev, investorContact, saveFunction, reloadAfterAvatar, reloaded) {
				var customFullscreen = $mdMedia('xs') || $mdMedia('sm');
				var useFullScreen = ($mdMedia('sm') || $mdMedia('xs')) && customFullscreen;
			    /*----------------------------------------------------------------------------------------------------*/
				var investorContactCtrl = function($scope) {
					
				    $scope.globalAttr = globalAttr;
				    $scope.lang = lang;
				    $scope.progressMode = 'indeterminate';
				    $scope.$mdMedia = $mdMedia;
				    $scope.customKeys = [$mdConstant.KEY_CODE.ENTER, $mdConstant.KEY_CODE.COMMA, $mdConstant.KEY_CODE.SPACE];
				    $scope.additionalFocusAreas = [];
				    /*----------------------------------------------------------------------------------------------------*/
				    $scope.contains = function (a, obj) {
				        for (var i = 0; i < a.length; i++) {
				            if (a[i] === obj) {
				                return true;
				            }
				        }
				        return false;
				    };
				    /*----------------------------------------------------------------------------------------------------*/
				    $scope.removeAdditionalFocusArea = function(removedChip){
				    	for (var i = 0; i < $scope.investorContact.focusAreas.length; i++){
				    		var focusArea = $scope.investorContact.focusAreas[i];
				    		if (focusArea == removedChip){
				    			$scope.investorContact.focusAreas.splice(i, 1);
				    			break;
				    		}
				    	}
				    	for (var i = 0; i < $scope.focusAreas.length; i++){
				    		var focusArea = $scope.focusAreas[i];
				    		if (focusArea == removedChip){
				    			$scope.focusAreas.splice(i, 1);
				    			break;
				    		}
				    	}
				    }
				    /*----------------------------------------------------------------------------------------------------*/
				    $scope.newFocusAreaChip = function(newChip){
				    	if (!$scope.contains($scope.focusAreas, newChip)){
				    		$scope.focusAreas.push(newChip);
				    		$scope.investorContact.focusAreas.push(newChip);
				    	}
				    	else {
				    		return null;
				    	}
				    };
				    /*----------------------------------------------------------------------------------------------------*/
				    $scope.focusAreas = [
				                         'IoT', 
				                         'MedTech', 
				                         'Digital Health', 
				                         'Fintech', 
				                         'AI', 
				                         'AR', 
				                         'VR', 
				                         'E-commerce', 
				                         'Mobile', 
				                         'Data', 
				                         'Infocomm'
				                         ];
				    /*----------------------------------------------------------------------------------------------------*/
				    $scope.focusAreaToggle = function (item, list) {
				      var idx = list.indexOf(item);
				      if (idx > -1) {
				        list.splice(idx, 1);
				      }
				      else {
				        list.push(item);
				      }
				    };
				    /*----------------------------------------------------------------------------------------------------*/
				    $scope.focusAreaExists = function (item, list) {
				      return list.indexOf(item) > -1;
				    };
				    /*----------------------------------------------------------------------------------------------------*/
				    $scope.isFocusAreaIndeterminate = function() {
				      return ($scope.investorContact.focusAreas.length !== 0 &&
				          $scope.investorContact.focusAreas.length !== $scope.focusAreas.length);
				    };
				    /*----------------------------------------------------------------------------------------------------*/
				    $scope.isFocusAreaChecked = function() {
				      return $scope.investorContact.focusAreas.length === $scope.focusAreas.length;
				    };
				    /*----------------------------------------------------------------------------------------------------*/
				    $scope.focusAreaToggleAll = function() {
				      if ($scope.investorContact.focusAreas.length === $scope.focusAreas.length) {
				        $scope.investorContact.focusAreas = [];
				      } else if ($scope.investorContact.focusAreas.length === 0 || $scope.investorContact.focusAreas.length > 0) {
				        $scope.investorContact.focusAreas = $scope.focusAreas.slice(0);
				      }
				    };
				    /*----------------------------------------------------------------------------------------------------*/
					$scope.investorContactAttributes = {
							firstName: lang.firstName,
							lastName: lang.lastName,
							mobilePhone: lang.mobilePhone,
							officePhone: lang.officePhone,
							email: lang.email,
							position: lang.position,
							howWeMet: lang.howWeMet
					};
				    /*----------------------------------------------------------------------------------------------------*/
					$scope.investorCompanyAttributes = {
							companyName: lang.companyName,
							founded: lang.founded,
							assetsUnderManagement: lang.assetsUnderManagement,
							averageInvestmentSize: lang.averageInvestmentSize,
							numberOfPastInvestments: lang.noPastInvestments,
							numberOfPastExits: lang.noPastExits
					}
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
					$scope.changeCompanyAvatar = function(ev){
						avatarDialog(ev, function(croppedImage){
							$scope.investorContact.companyAvatar = croppedImage;
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
				    			$scope.checkFocusAreas($scope.investorContact.focusAreas);
				    			$scope.progressMode = '';
				    		}
				    	}, function(){
				    		$scope.investorContact = investorContact;
				    		$scope.progressMode = '';
				    	})
				    }
				    else {
				    	$scope.investorContact = investorContact;
				    	$scope.investorContact.focusAreas = [];
				    	$scope.progressMode = '';
				    }
				    /*----------------------------------------------------------------------------------------------------*/
				    $scope.checkFocusAreas = function(investorFocusAreas){
				    	for (var i = 0; i < investorFocusAreas.length; i++){
				    		var focusArea = investorFocusAreas[i];
				    		if (!$scope.contains($scope.focusAreas, focusArea)){
				    			$scope.focusAreas.push(focusArea);
				    		}
				    	}
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