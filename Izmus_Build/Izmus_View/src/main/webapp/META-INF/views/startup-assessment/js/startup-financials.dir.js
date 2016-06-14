angular
		.module('startupAssessmentApp')
		.directive(
				'izmusStartupFinancials',
				[
						'loadFinancialIndicators',
						'$mdEditDialog',
						function(loadFinancialIndicators, $mdEditDialog) {
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
												$scope.filteredFinancialIndicators = [];
												$scope.filteredPeriods = [];
												for (var i = 0; i < $scope.financialIndicatorTypes.length; i++) {
													var indicatorType = $scope.financialIndicatorTypes[i];
													var indicator = $scope
															.getFinancialIndicator(indicatorType.typeId);
													$scope.addPeriodsToFilteredPeriods($scope.filteredPeriods, indicator);
													$scope.filteredFinancialIndicators
															.push({
																type : indicatorType,
																indicator : indicator
															});
												}
											}
											/*----------------------------------------------------------------------------------------------------*/
											$scope.addPeriodsToFilteredPeriods = function(periods, indicator){
												if (indicator.points) for (var i = 0; i < indicator.points.length; i++){
													var found = false;
													var point = indicator.points[i];
													for (var j = 0; j < periods.length; j++){
														var period = periods[j];
														if (period == point.period){
															found = true;
															break;
														}
													}
													if (!found){
														periods.push(point.period);
													}
												}
											};
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
											$scope.addPeriod = function(){
												$scope.addPeriodToIndicators('2017');
												$scope.filteredPeriods.push('2017');
											}
											/*----------------------------------------------------------------------------------------------------*/
											$scope.addPeriodToIndicators = function(period){
												for (var i = 0; i < $scope.selectedFinancialIndicators.length; i++){
													var indicator = $scope.selectedFinancialIndicators[i];
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