angular.module('proposalAllocation').directive('investitAccount', [ function() {
	return {
		restrict : 'E',
		require : '^investitProposalAllocation',
		scope : {
			item : '='
		},
		templateUrl : '/views/proposal-engine/templates/account.html',
		controller : ['$scope',function($scope) {
			$scope.dir = dir;
			$scope.options = {
				chart : {
					type : 'pieChart',
					height : 500,
					x : function(asset) {
						return asset.name;
					},
					y : function(asset) {
						return asset.value;
					},
					showLabels : true,
					duration : 500,
					labelThreshold : 0.01,
					legend: {
						margin: {   
							top: 1,
							right: 0,
							bottom: 0,
							left: 0
						},
						dispatch :{
							legendClick : function (element, u){
								$scope.deleteAsset(element);
							}
						},
						padding: 17,
						rightAlign: false,
						updateState: false
					},
					height : 300,
					donut : true,
					showLabels : false,
					noData : ''
				}
			};
			$scope.data = $scope.item.children;
			$scope.handleAssetDrop = function(asset){
				$scope.data.push(asset);
			}
		}],
		link : function(scope, elem, attr, parentCtrl) {
			scope.deleteAsset = function (element){
				parentCtrl.deleteAsset(function(){
					var index = scope.data.indexOf(element);
					if (index > -1) {
						scope.data.splice(index, 1);
					}
				}, element);
			}
		}
	}
} ]);