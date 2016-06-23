angular.module('contactsApp').directive('contactsDashboard',
		[ 'loadAllContacts','contactViewDialog','saveGeneralContact', '$mdToast',
		  function(loadAllContacts,contactViewDialog, saveGeneralContact, $mdToast) {
			return {
				restrict : 'E',
				templateUrl : '/views/contacts/templates/contacts.html',
				controller : [ '$scope', function($scope) {
					$scope.contacts = [];
					$scope.lang = lang;
					$scope.globalAttr = globalAttr;
					$scope.progressMode = '';
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
					loadAllContacts().then(function(data){
						$scope.contacts = data;
					},
					function(){
						
					});
					/*----------------------------------------------------------------------------------------------------*/
					$scope.viewGeneralContact = function(ev, contact){
						contactViewDialog(ev, contact, function(){
							$scope.progressMode = 'indeterminate';
							exportGeneralContact(contact).then(function(){
								$scope.progressMode = '';
							});
						},function(){
							$scope.progressMode = 'indeterminate';
							saveGeneralContact(contact).then(function(data){
								if (data.result == 'success'){
									$scope.progressMode = '';
									contact.contactId = data.contactId;
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
					$scope.viewStartupContact = function(ev, contact){
						contactViewDialog(ev, contact, function(){
							$scope.progressMode = 'indeterminate';
							exportGeneralContact(contact).then(function(){
								$scope.progressMode = '';
							});
						});
					}
					/*----------------------------------------------------------------------------------------------------*/
					$scope.exportPDF = function(contact){
						$scope.progressMode = 'indeterminate';
						exportGeneralContact(contact).then(function(){
							$scope.progressMode = '';
						});
					}
					/*----------------------------------------------------------------------------------------------------*/
					$scope.addGeneralContact = function(ev){
						if (!$scope.contacts.generalContacts){
							$scope.contacts.generalContacts = [];
						}
						var newContact = {
							contactDate: new Date()
						};
						$scope.viewGeneralContact(ev, newContact);
						$scope.contacts.generalContacts.push(newContact);
					}
					/*----------------------------------------------------------------------------------------------------*/
					$scope.searchContact = function(contact){
						if (!$scope.search 
								|| (contact.firstName.toLowerCase().indexOf($scope.search.toLowerCase()) != -1)
								|| (contact.lastName.toLowerCase().indexOf($scope.search.toLowerCase()) != -1)){
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