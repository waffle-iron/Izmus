angular.module('startupAssessmentApp', ['izmusNavBarApp', 'izmusMainWindowApp', 'ngAnimate', 'ngFileUpload', 'ngMessages', 'md.data.table']);
/*----------------------------------------------------------------------------------------------------*/

angular.module('startupAssessmentApp').controller('toastCtrl', ['$scope','message', function($scope, message){
	$scope.message = message;
}]);