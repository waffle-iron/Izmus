angular.module('startupAssessmentApp').directive('izmusStartupList', [function() {
	return {
		restrict : 'E',
		require: '^^izmusStartupAssessment',
		templateUrl : '/views/startup-assessment/templates/startup-list.html',
		scope: {
			sidenavCtrl: '='
		},
		controller : ['$scope',function($scope) {
			$scope.globalAttr = globalAttr;
			$scope.lang = lang;
			/*----------------------------------------------------------------------------------------------------*/
			$scope.getMeasurementColor = function(startup){
				var score = $scope.getFinalScore(startup);
				if (score) {
					var returnColor = '';
					if (score < 50){
						 var customWarn = {
							        10:'#f7bebd',
							        9: '#f4a8a6',
							        8: '#f19290',
							        7: '#ee7b79',
							        6: '#eb6562',
							        5: '#e84f4c',
							        4: '#eb6562',
							        3: '#e84f4c',
							        2: '#E53935',
							        1: '#e2231e'
							    };
						 returnColor = customWarn[Math.ceil(score / 5)];
					}
					else {
						var customAccent = {
								1: '#b1d2c5',
						        2: '#a1c8ba',
						        3: '#90bfae',
						        4: '#80b6a2',
						        5: '#70ac96',
						        6: '#60a38a',
						        7: '#56947d',
						        8: '#00b361',
						        9: '#00cc6e',
						        10:'#00e67c'
						    };
					 returnColor = customAccent[Math.ceil((score - 50) / 5)];
					}
					return returnColor;
				}
			}
			/*----------------------------------------------------------------------------------------------------*/
			$scope.getFinalScore = function(startup){
				var returnScore = '';
				if (!startup.scoreCards) return returnScore;
				for (var i = 0; i < startup.scoreCards.length; i++){
					if (startup.scoreCards[i].finalScore){
						if (returnScore != ''){
							returnScore = (returnScore + (startup.scoreCards[i].finalScore)) / 2;
						}
						else {
							returnScore = (startup.scoreCards[i].finalScore);
						}
					}
				}
				return returnScore;
			}
		}],
		link : function(scope, elem, attr, parentCtrl) {
			/*----------------------------------------------------------------------------------------------------*/
			scope.selectStartup = function(startup){
				parentCtrl.setSelectedStartup(startup);
			}
		}
	}
} ]);