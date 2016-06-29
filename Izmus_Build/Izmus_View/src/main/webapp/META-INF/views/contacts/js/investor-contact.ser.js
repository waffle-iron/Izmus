/*----------------------------------------------------------------------------------------------------*/
angular.module('contactsApp').factory('viewInvestorContactDialog',
		[ '$mdMedia', '$mdDialog', 'loadInvestorContact', 'avatarDialog','$mdConstant', 
		  function($mdMedia, $mdDialog, loadInvestorContact, avatarDialog, $mdConstant) {
			return function(ev, investorContact, saveFunction, reloadAfterAvatar, reloaded, allFinders) {
				var customFullscreen = $mdMedia('xs') || $mdMedia('sm');
				var useFullScreen = ($mdMedia('sm') || $mdMedia('xs')) && customFullscreen;
			    /*----------------------------------------------------------------------------------------------------*/
				var investorContactCtrl = function($scope) {
					
				    $scope.globalAttr = globalAttr;
				    $scope.lang = lang;
				    $scope.progressMode = 'indeterminate';
				    $scope.$mdMedia = $mdMedia;
				    $scope.allFinders = allFinders;
				    $scope.finderSearch = '';
				    $scope.customKeys = [$mdConstant.KEY_CODE.ENTER, $mdConstant.KEY_CODE.COMMA];
				    $scope.additionalFocusAreas = [];
				    $scope.additionalInvestmentStages = [];
				    /*----------------------------------------------------------------------------------------------------*/
					$scope.searchFinder = function(contact){
						if (!$scope.finderSearch 
								|| (contact.firstName && contact.firstName.toLowerCase().indexOf($scope.finderSearch.toLowerCase()) != -1)
								|| (contact.lastName && contact.lastName.toLowerCase().indexOf($scope.finderSearch.toLowerCase()) != -1)
								|| (contact.companyName && contact.companyName.toLowerCase().indexOf($scope.finderSearch.toLowerCase()) != -1)){
							return true;
						}
						return false;
					};
				    /*----------------------------------------------------------------------------------------------------*/
				    $scope.investmentStages = [
				                             'Idea Stage', 
				                             'Seed Stage', 
				                             'A stage', 
				                             'Post A Stage', 
				                             'Pre IPO'
				                         ];
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
				    $scope.removeAdditionalIndicator = function(removedChip, contactList, cachedList){
				    	for (var i = 0; i < contactList.length; i++){
				    		var indicator = contactList[i];
				    		if (indicator == removedChip){
				    			contactList.splice(i, 1);
				    			break;
				    		}
				    	}
				    	for (var i = 0; i < cachedList.length; i++){
				    		var indicator = cachedList[i];
				    		if (indicator == removedChip){
				    			cachedList.splice(i, 1);
				    			break;
				    		}
				    	}
				    }
				    /*----------------------------------------------------------------------------------------------------*/
				    $scope.newIndicatorChip = function(newChip, contactList, cachedList){
				    	if (!$scope.contains(cachedList, newChip)){
				    		cachedList.push(newChip);
				    		contactList.push(newChip);
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
				    $scope.indicatorToggle = function (item, list) {
				      var idx = list.indexOf(item);
				      if (idx > -1) {
				        list.splice(idx, 1);
				      }
				      else {
				        list.push(item);
				      }
				    };
				    /*----------------------------------------------------------------------------------------------------*/
				    $scope.indicatorExists = function (item, list) {
				      return list.indexOf(item) > -1;
				    };
				    /*----------------------------------------------------------------------------------------------------*/
				    $scope.isIndicatorIndeterminate = function(contactList, cachedList) {
				      return (contactList.length !== 0 &&
				    		  contactList.length !== cachedList.length);
				    };
				    /*----------------------------------------------------------------------------------------------------*/
				    $scope.isIndicatorChecked = function(contactList, cachedList) {
				      return contactList.length === cachedList.length;
				    };
				    /*----------------------------------------------------------------------------------------------------*/
				    $scope.indicatorToggleAll = function(contactList, cachedList) {
				      if (contactList.length === cachedList.length) {
				    	  while(contactList.length > 0) {
				    		  contactList.pop();
				    	  }
				      } else if (contactList.length === 0 || contactList.length > 0) {
				    	  while(contactList.length > 0) {
				    		  contactList.pop();
				    	  }
				    	  for (var i = 0; i < cachedList.length; i++) {
				    		  contactList.push(cachedList[i]);
				    	  }
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
				    			$scope.checkIndicator($scope.investorContact.focusAreas, $scope.focusAreas);
				    			$scope.checkIndicator($scope.investorContact.investmentStage, $scope.investmentStages);
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
				    	$scope.investorContact.investmentStage = [];
				    	$scope.investorContact.notes = [];
				    	$scope.progressMode = '';
				    }
				    /*----------------------------------------------------------------------------------------------------*/
				    $scope.checkIndicator = function(contactList, cachedList){
				    	for (var i = 0; i < contactList.length; i++){
				    		var indicator = contactList[i];
				    		if (!$scope.contains(cachedList, indicator)){
				    			cachedList.push(indicator);
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
					/*----------------------------------------------------------------------------------------------------*/
					$scope.addNote = function(){
						if (!$scope.investorContact.notes){
							$scope.investorContact.notes = [];
						}
						$scope.investorContact.notes.push({});
					}
					/*----------------------------------------------------------------------------------------------------*/
					$scope.deleteNote = function(index){
						$scope.investorContact.notes.splice(index, 1);
					}
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