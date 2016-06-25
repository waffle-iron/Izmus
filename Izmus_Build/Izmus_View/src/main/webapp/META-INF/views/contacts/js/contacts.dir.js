angular.module('contactsApp').directive('contactsDashboard',
		[ 'loadAllContacts','addContactDialog', '$mdToast', 'viewInvestorContactDialog', 'saveInvestorContact','viewStartupContactDialog',
		  function(loadAllContacts,addContactDialog, $mdToast, viewInvestorContactDialog, saveInvestorContact, viewStartupContactDialog) {
			return {
				restrict : 'E',
				templateUrl : '/views/contacts/templates/contacts.html',
				controller : [ '$scope', function($scope) {
					$scope.contacts = [];
					$scope.lang = lang;
					$scope.globalAttr = globalAttr;
					$scope.progressMode = 'indeterminate';
					/*----------------------------------------------------------------------------------------------------*/
					loadAllContacts().then(function(data){
						$scope.contacts = data;
						$scope.progressMode = '';
					},
					function(){
						$scope.progressMode = '';
					});
					/*----------------------------------------------------------------------------------------------------*/
					$scope.addContact = function(ev){
						addContactDialog(ev, function(contactType){
							if (contactType){
								switch(contactType){
								case 'Izmus Finder':
									break;
								case 'Izmus Investor':
									$scope.addInvestorContact(ev);
									break;
								default:
									console.log(contactType);
									break;
								}
							}
						});
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
									$scope.progressMode = '';
									investorContact.contactId = data.contactId;
									$scope.showMessage($scope.lang.saveSuccess);
								}
								else {
									$scope.showMessage($scope.lang.saveFail);
								}
							}, function(){
								$scope.showMessage($scope.lang.saveFail);
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
								|| (contact.firstName.toLowerCase().indexOf($scope.search.toLowerCase()) != -1)
								|| (contact.lastName.toLowerCase().indexOf($scope.search.toLowerCase()) != -1)
								|| (contact.companyName.toLowerCase().indexOf($scope.search.toLowerCase()) != -1)){
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