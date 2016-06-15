angular
		.module('startupAssessmentApp')
		.directive(
				'izmusStartupFinancials',
				[
						'loadFinancialIndicators',
						'$mdEditDialog',
						'addPeriodDialog',
						function(loadFinancialIndicators, $mdEditDialog, addPeriodDialog) {
							return {
								restrict : 'E',
								require : '^^izmusStartupAssessment',
								templateUrl : '/views/startup-assessment/templates/startup-financials.html',
								scope : {
									selectedStartup : '=',
									financialIndicators : '=',
									financialIndicatorTypes : '='
								},
								controller : [
										'$scope',
										function($scope) {
											$scope.globalAttr = globalAttr;
											$scope.lang = lang;
											$scope.selectedFinancialIndicators = [];
											$scope.filteredFinancialIndicators = [];
											/*----------------------------------------------------------------------------------------------------*/
											$scope
													.$watch(
															'selectedStartup',
															function() {
																for (var i = 0; i < $scope.financialIndicators.length; i++) {
																	var indicatorSet = $scope.financialIndicators[i];
																	if (indicatorSet.startupId == $scope.selectedStartup.startupId) {
																		$scope.selectedFinancialIndicators = indicatorSet.indicators;
																		$scope.foundIndicators = true;
																		break;
																	}
																}
																if (!$scope.foundIndicators) {
																	loadFinancialIndicators(
																			$scope.selectedStartup.startupId)
																			.then(
																					function(
																							response) {
																						$scope.financialIndicators
																								.push({
																									startupId : $scope.selectedStartup.startupId,
																									indicators : response
																								});
																						$scope.selectedFinancialIndicators = response;
																						$scope
																								.createFilteredFinancialIndicators();
																					},
																					function() {
																						$scope.financialIndicators
																								.push({
																									startupId : $scope.selectedStartup.startupId,
																									indicators : []
																								});
																						$scope.selectedFinancialIndicators = [];
																						$scope
																								.createFilteredFinancialIndicators();
																					});
																} else {
																	$scope
																			.createFilteredFinancialIndicators();
																}
															});
											/*----------------------------------------------------------------------------------------------------*/
											$scope.getFinancialIndicator = function(
													typeId) {
												for (var i = 0; i < $scope.selectedFinancialIndicators.length; i++) {
													if ($scope.selectedFinancialIndicators[i].typeId == typeId)
														return $scope.selectedFinancialIndicators[i];
												}
											}
											/*----------------------------------------------------------------------------------------------------*/
											$scope.createFilteredFinancialIndicators = function() {
												$scope.filteredFinancialIndicators = {
													statementOfIncome: [],
													assetsAndLiabilities: []
												};
												$scope.filteredPeriods = {
													statementOfIncome: [],
													assetsAndLiabilities: []
												};
												for (var i = 0; i < $scope.financialIndicatorTypes.length; i++) {
													var indicatorType = $scope.financialIndicatorTypes[i];
													var indicator = $scope
															.getFinancialIndicator(indicatorType.typeId);
													if (indicatorType.reportName == "Statement of Income"){
														$scope.filteredFinancialIndicators.statementOfIncome
														.push({
															type : indicatorType,
															indicator : indicator
														});
													}
													else if (indicatorType.reportName == "Assets and Liabilities"){
														$scope.filteredFinancialIndicators.assetsAndLiabilities
														.push({
															type : indicatorType,
															indicator : indicator
														});
													}
												}
												if ($scope.filteredFinancialIndicators.statementOfIncome[0] != null && $scope.filteredFinancialIndicators.statementOfIncome[0].indicator != null){
													for (var i = 0; i < $scope.filteredFinancialIndicators.statementOfIncome[0].indicator.points.length; i++){
														$scope.filteredPeriods.statementOfIncome.push($scope.filteredFinancialIndicators.statementOfIncome[0].indicator.points[i].period);
													}
												}
												if ($scope.filteredFinancialIndicators.assetsAndLiabilities[0] != null && $scope.filteredFinancialIndicators.assetsAndLiabilities[0].indicator != null){
													for (var i = 0; i < $scope.filteredFinancialIndicators.assetsAndLiabilities[0].indicator.points.length; i++){
														$scope.filteredPeriods.assetsAndLiabilities.push($scope.filteredFinancialIndicators.assetsAndLiabilities[0].indicator.points[i].period);
													}
												}
											}
											/*----------------------------------------------------------------------------------------------------*/
											$scope.editCell = function(
													event, point) {
												event.stopPropagation(); // in
																			// case
																			// autoselect
																			// is
																			// enabled

												var editDialog = {
													modelValue : point.value,
													placeholder : $scope.lang.addValue,
													save : function(input) {
														point.value = input.$modelValue;
													},
													targetEvent : event,
													title : $scope.lang.addValue,
													validators : {
														'md-maxlength' : 30
													}
												};

												var promise;

												promise = $mdEditDialog
													.small(editDialog);

												promise.then();
											};
											/*----------------------------------------------------------------------------------------------------*/
											$scope.addPeriod = function(ev, reportName){
												addPeriodDialog(ev, function(period){
													$scope.addPeriodToIndicators(period, reportName);
													$scope.filteredPeriods[reportName].push(period);
												});
											}
											/*----------------------------------------------------------------------------------------------------*/
											$scope.addPeriodToIndicators = function(period, reportName){
												for (var i = 0; i < $scope.filteredFinancialIndicators[reportName].length; i++){
													var indicator = $scope.filteredFinancialIndicators[reportName][i].indicator;
													if (!indicator.points){
														indicator.points = [];
													}
													indicator.points.push({
														period: period,
														value: '',
													});
												}
											}
											
											/*----------------------------------------------------------------------------------------------------*/
										} ],
								link : function(scope, elem, attr, parentCtrl) {

								}
							}
						} ]);