angular.module('changePasswordApp').directive('izmusChangePassword', ['saveUserPassword','$mdToast','$timeout', function(saveUserPassword, $mdToast, $timeout) {
	return {
		restrict : 'E',
		templateUrl : '/views/change-password/templates/change-password.html',
		scope: {
			
		},
		controller : ['$scope',function($scope) {
			$scope.lang = lang;
			$scope.globalAttr = globalAttr;
			$scope.currentPassword = '';
			$scope.newPassword = '';
			$scope.confirmPassword = '';
		}],
		link : function(scope, elem, attr) {
			/*----------------------------------------------------------------------------------------------------*/

			scope.changePassword = function(){
				if (scope.currentPassword && scope.newPassword && (scope.newPassword == scope.confirmPassword)){
					saveUserPassword(scope.currentPassword, scope.newPassword).then(function(response){
						if (response.result == 'fail'){
							scope.showMessage(scope.lang.failMessage);
						}
						else {
							scope.showMessage(scope.lang.successMessage);
							$timeout(function() {
								window.location = "/";
						    }, 3500);
						}
					});
				}
				else {
					scope.showMessage(scope.lang.failMessage);
				}
			}
			/*----------------------------------------------------------------------------------------------------*/

			scope.showMessage = function(message){
				$mdToast.show({
				      controller: 'changePasswordToastCtrl',
				      templateUrl: '/views/change-password/templates/change-password-toast.html',
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
/*----------------------------------------------------------------------------------------------------*/

angular.module('changePasswordApp').directive('validPasswordC', function () {
    return {
        require: 'ngModel',
        link: function (scope, elm, attrs, ctrl) {
        	scope.$watch('$parent.$parent.newPassword', function(newValue, oldValue){
        		if (newValue == scope.$parent.$parent.confirmPassword){
        			ctrl.$setValidity('noMatch', true);
        		}
        		else {
        			ctrl.$setValidity('noMatch', false);
        		}
        	});
            ctrl.$parsers.unshift(function (viewValue, $scope) {
                var noMatch = viewValue != scope.changePasswordForm.newPassword.$viewValue;
                if (!noMatch){
                	scope.$parent.$parent.confirmPassword = viewValue;
                }
                ctrl.$setValidity('noMatch', !noMatch)
            })
        }
    }
});
/*----------------------------------------------------------------------------------------------------*/

angular.module('changePasswordApp').controller('changePasswordToastCtrl', ['$scope','message', function($scope, message){
	$scope.message = message;
}]);