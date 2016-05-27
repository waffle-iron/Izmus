angular.module('startupAssessmentApp').directive('izmusStartupScorecard', ['loadDefaultScoreCard', 
                        function(loadDefaultScoreCard) {
	return {
		restrict : 'E',
		require: '^^izmusStartupDetail',
		templateUrl : '/views/startup-assessment/templates/startup-scorecard.html',
		scope: {
			scoreCard: '=',
			selectedStartup: '='
		},
		controller : ['$scope',function($scope) {
			$scope.globalAttr = globalAttr;
			$scope.lang = lang;
			$scope.progressMode = '';
			/*----------------------------------------------------------------------------------------------------*/
			$scope.measurementAttr = {
				firstName: lang.firstName,
				lastName: lang.lastName,
				mobilePhone: lang.mobilePhone,
				officePhone: lang.officePhone,
				email: lang.email,
				position: lang.position
			};
			/*----------------------------------------------------------------------------------------------------*/
			$scope.startScorecard = function(){
				$scope.progressMode = 'indeterminate';
				loadDefaultScoreCard($scope.selectedStartup.startupId).then(function(result) {
					var index = $scope.selectedStartup.scoreCards.indexOf($scope.scoreCard);
					$scope.selectedStartup.scoreCards.splice(index, 1);
					$scope.selectedStartup.scoreCards.push(result);
					$scope.scoreCard = result;
					$scope.progressMode = '';
				}, function(error) {
					$scope.progressMode = ''
				});
			};
			/*----------------------------------------------------------------------------------------------------*/
			$scope.getMeasurementColor = function(measurement){
				var score = $scope.getFinalScore(measurement);
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
			$scope.getFinalScore = function(measurement){
				var ratio = 0.7;
				var finalScore = 0;
				var evaluationScore = 0;
				var questionScore = 0;
				if (measurement.score){
					evaluationScore = measurement.score / 9 * 100;
				}
				if (measurement.measurementQuestions.length > 0){
					for (var i = 0; i < measurement.measurementQuestions.length; i++){
						if (measurement.measurementQuestions[i].score){
							if (questionScore == 0){
								questionScore = measurement.measurementQuestions[i].score / 5 * 100;
							}
							else {
								questionScore = (questionScore + measurement.measurementQuestions[i].score / 5 * 100)/2;
							}
						}
					}
				}
				if (evaluationScore != 0 && questionScore != 0){
					finalScore = (evaluationScore * ratio) + (questionScore * (1 - ratio));
				}
				else {
					finalScore = evaluationScore + questionScore;
				}
				finalScore = Math.ceil(finalScore);
				measurement.finalScore = finalScore;
				return finalScore;
			}
		}],
		link : function(scope, elem, attr, parentCtrl) {
			/*----------------------------------------------------------------------------------------------------*/
			scope.measurementProgress = function(measurement){
				return parentCtrl.measurementProgress(measurement);
			}
		}
	}
} ]);