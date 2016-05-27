angular.module('startupAssessmentApp').directive('izmusStartupDetail', ['saveStartupData','avatarDialog','$mdMedia', 
                        'exportScoreCardReport', '$mdDialog', '$mdConstant','emailScoreCardReport',
                        function(saveStartupData, avatarDialog, $mdMedia, exportScoreCardReport, $mdDialog, $mdConstant, emailScoreCardReport) {
	return {
		restrict : 'E',
		require: '^^izmusStartupAssessment',
		templateUrl : '/views/startup-assessment/templates/startup-detail.html',
		scope: {
			selectedStartup: '=',
			startupGeneralAttributes: '=',
			startupGeneralFinancials: '='
		},
		controller : ['$scope',function($scope) {
			/*----------------------------------------------------------------------------------------------------*/
			this.measurementProgress = function(measurement){
				var finalProgress = 0;
				if (measurement.measurementQuestions.length > 0){
					if (measurement.score){
						finalProgress = 30;
					}
					for (var i = 0; i < measurement.measurementQuestions.length; i++){
						if (measurement.measurementQuestions[i].score){
							finalProgress = finalProgress + (1 / measurement.measurementQuestions.length * 70);
						}
					}
				}
				else if (measurement.score){
					finalProgress = 100;
				}
				return finalProgress;
			}
			/*----------------------------------------------------------------------------------------------------*/
			$scope.measurementProgress = this.measurementProgress;
			var customFullscreen = $mdMedia('xs') || $mdMedia('sm');
			$scope.useFullScreen = ($mdMedia('sm') || $mdMedia('xs')) && customFullscreen;
			$scope.globalAttr = globalAttr;
			$scope.lang = lang;
			$scope.progressMode = '';
			$scope.tabs = {
					selectedTab: 0
			}
			/*----------------------------------------------------------------------------------------------------*/
			$scope.toggleMainFab = function($event, lostFocus){
				if (lostFocus){
					$scope.isMainFabOpen = false;
					return;
				}
				$scope.isMainFabOpen = !$scope.isMainFabOpen;
				if($scope.isMainFabOpen){
					if ($event){
						$event.currentTarget.focus();
					}
				}
			}
			/*----------------------------------------------------------------------------------------------------*/
			$scope.saveChanges = function(){
				$scope.progressMode = 'indeterminate';
				saveStartupData($scope.selectedStartup).then(function(result){
					$scope.isMainFabOpen = false;
					$scope.progressMode = '';
				}, function(error){
					$scope.progressMode = '';
				});
			}
			/*----------------------------------------------------------------------------------------------------*/
			$scope.changeLogo = function(ev){
				avatarDialog(ev, function(croppedImage){
					$scope.selectedStartup.logo = croppedImage;
				});
			}
			/*----------------------------------------------------------------------------------------------------*/
			$scope.addNewContact = function(){
				$scope.tabs.selectedTab = 1;
				if (!$scope.selectedStartup.contacts){
					$scope.selectedStartup.contacts = [];
				}
				$scope.selectedStartup.contacts.push({});
			}
			/*----------------------------------------------------------------------------------------------------*/
			$scope.addNewScoreCard = function(){
				if (!$scope.selectedStartup.scoreCards){
					$scope.selectedStartup.scoreCards = [];
				}
				$scope.selectedStartup.scoreCards.push({
					scoreCardDate: new Date(),
					measurements: []
				});
				$scope.tabs.selectedTab = $scope.selectedStartup.scoreCards.length + 1;
			}
			/*----------------------------------------------------------------------------------------------------*/
			$scope.isSmallDevice = function(){
				return (!$mdMedia('gt-md'));
			}
			/*----------------------------------------------------------------------------------------------------*/
			$scope.scoreCardProgress = function(scoreCard){
				if (scoreCard.measurements != null && scoreCard.measurements.length > 0){
					var progress = 0;
					var finalScore = 0;
					for (var i = 0; i < scoreCard.measurements.length; i++){
						progress = progress + ($scope.measurementProgress(scoreCard.measurements[i]) / scoreCard.measurements.length);
						if(scoreCard.measurements[i].finalScoreRatio) {
							finalScore = finalScore + (scoreCard.measurements[i].finalScore * scoreCard.measurements[i].finalScoreRatio);
						}
					}
					scoreCard.finalScore = Math.ceil(finalScore);
					return progress;
				}
				else {
					scoreCard.finalScore = 0;
					return 0;
				}
			}
			/*----------------------------------------------------------------------------------------------------*/
			$scope.exportScoreCard = function(ev, type){
				if ($scope.selectedStartup.scoreCards.length <= 0) return;
				/*----------------------------------------------------------------------------------------------------*/
				$mdDialog.show({
				    controller: $scope.exportScoreCardReportCtrl,
				    templateUrl: '/views/startup-assessment/templates/export-report-dialog.html',
				    parent: angular.element(document.body),
				    targetEvent: ev,
				    clickOutsideToClose: true,
				    fullscreen: $scope.useFullScreen,
				    locals: {
				           type: type,
				           ev: ev
				    }
				});
			}
		    
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
			scope.exportScoreCardReportCtrl = function($scope, $mdDialog, type, ev) {
				$scope.parentScope = scope;
				$scope.selectedScoreCard = scope.selectedStartup.scoreCards[0];
				/*----------------------------------------------------------------------------------------------------*/
				$scope.cancel = function() {
					$mdDialog.cancel();
				};
				/*----------------------------------------------------------------------------------------------------*/
				$scope.ok = function() {
					if (type == 'pdf'){
						scope.progressMode = 'indeterminate';
						exportScoreCardReport($scope.selectedScoreCard, scope.selectedStartup).then(function(result){
							scope.isMainFabOpen = false;
							scope.progressMode = '';
						}, function(error){
							scope.progressMode = '';
						});
					}
					else if (type == 'email'){
						$mdDialog.show({
						    controller: scope.emailScoreCardReportCtrl,
						    templateUrl: '/views/startup-assessment/templates/email-report-dialog.html',
						    parent: angular.element(document.body),
						    targetEvent: ev,
						    clickOutsideToClose: true,
						    fullscreen: scope.useFullScreen,
						    locals: {
						    	selectedScoreCard: $scope.selectedScoreCard
						    }
						});
					}
					$mdDialog.cancel();
				};
			}
			 /*----------------------------------------------------------------------------------------------------*/
			scope.emailScoreCardReportCtrl = function($scope, $mdDialog, selectedScoreCard) {
				$scope.parentScope = scope;
				$scope.customKeys = [$mdConstant.KEY_CODE.ENTER, $mdConstant.KEY_CODE.COMMA, $mdConstant.KEY_CODE.SPACE];
				$scope.emails = [];
				$scope.newChip = function($chip){
					var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
					if (re.test($chip)){
						$scope.emails.push($chip);
					}
					return null;
				}
				/*----------------------------------------------------------------------------------------------------*/
				$scope.cancel = function() {
					$mdDialog.cancel();
				};
				/*----------------------------------------------------------------------------------------------------*/
				$scope.ok = function() {
					scope.progressMode = 'indeterminate';
					emailScoreCardReport(selectedScoreCard, scope.selectedStartup, $scope.emails).then(function(result){
						scope.isMainFabOpen = false;
						scope.progressMode = '';
					}, function(error){
						scope.progressMode = '';
					});
					$mdDialog.cancel();
				};
			}
		}
	}
} ]);