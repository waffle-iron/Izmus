angular.module('contactsApp').directive('contactsDashboard',
		[ 'loadAllContacts','addContactDialog', '$mdToast', 'viewInvestorContactDialog', 'saveInvestorContact','viewStartupContactDialog',
		  'viewFinderContactDialog', 'saveFinderContact','viewGeneralContactDialog', 'saveGeneralContact',
		  function(loadAllContacts,addContactDialog, $mdToast, viewInvestorContactDialog, saveInvestorContact, viewStartupContactDialog,
				  viewFinderContactDialog, saveFinderContact,viewGeneralContactDialog, saveGeneralContact) {
			return {
				restrict : 'E',
				templateUrl : '/views/contacts/templates/contacts.html',
				controller : [ '$scope', function($scope) {
					$scope.contacts = [];
					$scope.lang = lang;
					$scope.globalAttr = globalAttr;
					$scope.progressMode = 'indeterminate';
					/*----------------------------------------------------------------------------------------------------*/
					$scope.loadContacts = function(){
						loadAllContacts().then(function(data){
							$scope.contacts = data;
							$scope.progressMode = '';
						},
						function(){
							$scope.progressMode = '';
						});
					}
					$scope.loadContacts();
					/*----------------------------------------------------------------------------------------------------*/
					$scope.addContact = function(ev){
						addContactDialog(ev, function(contactType){
							if (contactType){
								switch(contactType){
								case 'Izmus Finder':
									$scope.addFinderContact(ev);
									break;
								case 'Izmus Investor':
									$scope.addInvestorContact(ev);
									break;
								default:
									$scope.addGeneralContact(ev);
									break;
								}
							}
						});
					}
					/*----------------------------------------------------------------------------------------------------*/
					$scope.addFinderContact = function(ev){
						var finderContact = {
							contactId: null
						};
						$scope.contacts.finderContacts.push(finderContact);
						$scope.viewFinderContact(ev, finderContact);
					}
					/*----------------------------------------------------------------------------------------------------*/
					$scope.addGeneralContact = function(ev){
						var generalContact = {
							contactId: null
						};
						$scope.contacts.generalContacts.push(generalContact);
						$scope.viewGeneralContact(ev, generalContact);
					}
					/*----------------------------------------------------------------------------------------------------*/
					$scope.addInvestorContact = function(ev){
						var investorContact = {
							contactId: null
						};
						$scope.contacts.investorContacts.push(investorContact);
						$scope.viewInvestorContact(ev, investorContact);
					}
					/*----------------------------------------------------------------------------------------------------*/
					$scope.viewInvestorContact = function(ev, investorContact){
						viewInvestorContactDialog(ev, investorContact, function(investorContact){
							$scope.progressMode = 'indeterminate';
							saveInvestorContact(investorContact).then(function(data){
								if (data.result == 'success'){
									investorContact.contactId = data.contactId;
									$scope.showMessage($scope.lang.saveSuccess);
									$scope.loadContacts();
								}
								else {
									$scope.showMessage($scope.lang.saveFail);
									$scope.progressMode = '';
								}
							}, function(){
								$scope.showMessage($scope.lang.saveFail);
								$scope.progressMode = '';
							});
						});
					}
					/*----------------------------------------------------------------------------------------------------*/
					$scope.viewFinderContact = function(ev, finderContact){
						viewFinderContactDialog(ev, finderContact, function(finderContact){
							$scope.progressMode = 'indeterminate';
							saveFinderContact(finderContact).then(function(data){
								if (data.result == 'success'){
									finderContact.contactId = data.contactId;
									$scope.showMessage($scope.lang.saveSuccess);
									$scope.loadContacts();
								}
								else {
									$scope.showMessage($scope.lang.saveFail);
									$scope.progressMode = '';
								}
							}, function(){
								$scope.showMessage($scope.lang.saveFail);
								$scope.progressMode = '';
							});
						});
					}
					/*----------------------------------------------------------------------------------------------------*/
					$scope.viewGeneralContact = function(ev, generalContact){
						viewGeneralContactDialog(ev, generalContact, function(generalContact){
							$scope.progressMode = 'indeterminate';
							saveGeneralContact(generalContact).then(function(data){
								if (data.result == 'success'){
									generalContact.contactId = data.contactId;
									$scope.showMessage($scope.lang.saveSuccess);
									$scope.loadContacts();
								}
								else {
									$scope.showMessage($scope.lang.saveFail);
									$scope.progressMode = '';
								}
							}, function(){
								$scope.showMessage($scope.lang.saveFail);
								$scope.progressMode = '';
							});
						});
					}
					/*----------------------------------------------------------------------------------------------------*/
					$scope.viewStartupContact = function(ev, startupContact){
						viewStartupContactDialog(ev, startupContact);
					}
					/*----------------------------------------------------------------------------------------------------*/
					$scope.searchContact = function(contact){
						if (!$scope.search 
								|| (contact.firstName && contact.firstName.toLowerCase().indexOf($scope.search.toLowerCase()) != -1)
								|| (contact.lastName && contact.lastName.toLowerCase().indexOf($scope.search.toLowerCase()) != -1)
								|| (contact.companyName && contact.companyName.toLowerCase().indexOf($scope.search.toLowerCase()) != -1)){
							return true;
						}
						return false;
					};
				} ],
				link : function(scope, elem, attr) {
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