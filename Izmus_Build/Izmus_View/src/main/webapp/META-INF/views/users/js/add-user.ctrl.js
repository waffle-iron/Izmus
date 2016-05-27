angular.module('userManagementApp').controller('addUserDialogCtrl', ['$timeout', '$scope', 'parentScope', '$mdDialog', 'checkUserAndEmail', 'createNewUser', '$mdToast', 'parentElem', 
        function($timeout, $scope, parentScope, $mdDialog, checkUserAndEmail, createNewUser, $mdToast, parentElem){
	$scope.parentScope = parentScope;
	$scope.lang = lang;
	$scope.globalAttr = globalAttr;
	$scope.userName = "";
	$scope.email = "";
	$scope.userType = "";
	/*----------------------------------------------------------------------------------------------------*/
	$scope.cancel = function() {
		$mdDialog.cancel();
	};
	/*----------------------------------------------------------------------------------------------------*/
	$scope.ok = function() {
		if (!($scope.userName && $scope.email && $scope.userType)){
			$scope.showRegistrationFailure($scope.lang.userAndEmailAndTypeRequired);
			return;
		}
		checkUserAndEmail($scope.userName,
				$scope.email).then(function(response){
					if (response.isEmailExists == true && response.isUserExists == true){
						$scope.showRegistrationFailure($scope.lang.userAndEmailExists);
					}
					else if (response.isEmailExists == true && response.isUserExists == false){
						$scope.showRegistrationFailure($scope.lang.emailExists);
					}
					else if (response.isEmailExists == false && response.isUserExists == true){
						$scope.showRegistrationFailure($scope.lang.userNameExists);
					}
					else {
						createNewUser($scope.userName,
								$scope.email, $scope.userType).then(function(result){
									if (result.result == 'success'){
										$scope.showRegistrationFailure($scope.lang.userCreatedSuccessfully);
										$timeout(function() {
											window.location = "/Users";
									    }, 3500);
									}
									else {
										$scope.showRegistrationFailure($scope.lang.userCreationFailed);
									}
								});
					}
				});
	}
	/*----------------------------------------------------------------------------------------------------*/
	$scope.showRegistrationFailure = function(message){
		$mdToast.show({
		      controller: 'registrationToastCtrl',
		      templateUrl: '/views/users/templates/registration-toast.html',
		      parent : angular.element(parentElem),
		      hideDelay: 2700,
		      position: 'top right',
		      locals: {
		    	  message: message
		      }
		    });
	}
}]);
/*----------------------------------------------------------------------------------------------------*/

angular.module('userManagementApp').controller('registrationToastCtrl', ['$scope','message', function($scope, message){
	$scope.message = message;
}]);