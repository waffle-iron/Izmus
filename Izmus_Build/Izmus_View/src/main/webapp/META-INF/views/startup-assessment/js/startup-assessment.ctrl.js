angular.module('startupAssessmentApp', ['izmusNavBarApp', 'izmusMainWindowApp', 'ngAnimate', 'ngFileUpload', 'ngMessages']);
/*----------------------------------------------------------------------------------------------------*/

angular.module('startupAssessmentApp').controller('toastCtrl', ['$scope','message', function($scope, message){
	$scope.message = message;
}]);