angular
		.module('startupAssessmentApp')
		.directive(
				'izmusStartupAdditionalDocuments',
				[
						'Upload',
						'$timeout',
						'deleteAdditionalDocument',
						function(Upload, $timeout, deleteAdditionalDocument) {
							return {
								restrict : 'E',
								require : '^^izmusStartupAssessment',
								templateUrl : '/views/startup-assessment/templates/startup-additional-documents.html',
								scope : {
									selectedStartup : '='
								},
								controller : [
										'$scope',
										function($scope) {
											$scope.globalAttr = globalAttr;
											$scope.lang = lang;
											/*----------------------------------------------------------------------------------------------------*/
											$scope.uploadFile = function(file) {
												if (file) {
													file.upload = Upload
															.upload(
																	{
																		url : '/api/StartupAssessment/AdditionalDocument',
																		headers : {
																			'Content-Type' : "application/x-www-form-urlencoded; charset=UTF-8",
																			'Upgrade-Insecure-Requests' : "1",
																			'X-CSRF-TOKEN' : globalAttr.sessionToken
																		},
																		fields : {
																			'startupId' : $scope.selectedStartup.startupId
																		},
																		file : file
																	})
															.progress(
																	function(
																			evt) {

																	})
															.success(
																	function(
																			data,
																			status,
																			headers,
																			config) {
																		$scope.selectedStartup.additionalDocuments
																				.push({
																					documentName : config.file.name,
																					documentId : data.documentId
																				});
																		file.progress = "";
																	});

													file.upload
															.then(
																	function(
																			response) {
																		$timeout(function() {
																			file.result = response.data;
																		});
																	},
																	function(
																			response) {
																		if (response.status > 0)
																			$scope.errorMsg = response.status
																					+ ': '
																					+ response.data;
																	},
																	function(
																			evt) {
																		file.progress = Math
																				.min(
																						100,
																						parseInt(100.0
																								* evt.loaded
																								/ evt.total));
																	});
												}
											}
											/*----------------------------------------------------------------------------------------------------*/
											$scope.deleteDocument = function(
													index, ev, doc) {
												doc.progressMode = 'indeterminate';
												ev.stopImmediatePropagation();
												deleteAdditionalDocument(
														$scope.selectedStartup.additionalDocuments[index].documentId,
														$scope.selectedStartup.startupId)
														.then(
																function(result) {
																	doc.progressMode = '';
																	$scope.selectedStartup.additionalDocuments
																			.splice(
																					index,
																					1);
																},
																function(error) {
																	doc.progressMode = ''
																});
											}
											/*----------------------------------------------------------------------------------------------------*/
											$scope.downloadDocument = function(
													index) {
												window.location = "/Export/StartupAdditionalDocuments/"
														+ $scope.selectedStartup.additionalDocuments[index].documentId
														+ "?startupId="
														+ $scope.selectedStartup.startupId;
											}
										} ],
								link : function(scope, elem, attr, parentCtrl) {

								}
							}
						} ]);