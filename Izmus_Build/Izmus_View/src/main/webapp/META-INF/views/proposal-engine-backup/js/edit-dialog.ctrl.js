angular.module('proposalEngine').controller('editDialogCtrl', ['$scope','$uibModalInstance', function($scope, $uibModalInstance){
	$scope.dir = dir;
	$scope.item = JSON.parse(JSON.stringify($scope.$parent.models.selected));
	$scope.options = {
			chart : {
				type : 'pieChart',
				x : function(asset) {
					return asset.name;
				},
				y : function(asset) {
					return asset.value;
				},
				legend: {
					margin: {   
						top: 1,
						right: 0,
						bottom: 0,
						left: 0
					},
					dispatch :{
						legendClick : function (element, u){
							$scope.$parent.openDeleteDialog(function(){
								var index = $scope.data.indexOf(element);
								if (index > -1) {
									$scope.data.splice(index, 1);
								}
							}, element.name);
						}
					},
					padding: 17,
					rightAlign: false,
					updateState: false
				},
				showLabels : true,
				duration : 500,
				labelThreshold : 0.01,
				labelSunbeamLayout : true,
				height : 350,
				width: 300,
				donut : true,
				showLabels : false,
				noData : ''
			}
		};
	$scope.data = $scope.item.children;
	$scope.handleAssetDrop = function(asset){
		$scope.data.push(asset);
	}
	$scope.saveButton = function (){
		$scope.$parent.models.selected.name = $scope.item.name;
		$scope.$parent.models.selected.children.splice(0, $scope.$parent.models.selected.children.length);
		for (var i = 0; i < $scope.item.children.length; i++){
			$scope.$parent.models.selected.children.push($scope.item.children[i]);
		}
		$uibModalInstance.close();
	};
	$scope.cancelButton = function () {
	    $uibModalInstance.dismiss();
	};
}]);