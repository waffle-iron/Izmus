angular.module('proposalEngine').controller('recheckDeleteCtrl', ['$scope','$uibModalInstance','assetName', function($scope, $uibModalInstance, assetName){
	$scope.name = $scope.$parent.models.selected.name;
	if (assetName){
		$scope.name = assetName;
	}
	$scope.dir = dir;
	$scope.deleteButton = function (){
		$uibModalInstance.close();
	};
	$scope.cancelButton = function () {
	    $uibModalInstance.dismiss();
	};
}]);