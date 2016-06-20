angular
		.module('startupAssessmentApp')
		.directive(
				'izmusStartupMeetings',
				[ 'meetingViewDialog',
				  'exportMeetingSummary',
						function(meetingViewDialog, exportMeetingSummary) {
							return {
								restrict : 'E',
								require : '^^izmusStartupAssessment',
								templateUrl : '/views/startup-assessment/templates/startup-meetings.html',
								scope : {
									selectedStartup : '='
								},
								controller : [
										'$scope',
										function($scope) {
											$scope.globalAttr = globalAttr;
											$scope.lang = lang;
											/*----------------------------------------------------------------------------------------------------*/
											$scope.parseDate = function(meeting){
												meeting.meetingDate = new Date(meeting.meetingDate);
											}
											/*----------------------------------------------------------------------------------------------------*/
											$scope.$watch('selectedStartup', function(){
												for (var i = 0; i < $scope.selectedStartup.meetings.length; i++){
													var meeting = $scope.selectedStartup.meetings[i];
													$scope.parseDate(meeting);
												}
											});
											/*----------------------------------------------------------------------------------------------------*/
											$scope.viewMeeting = function(ev, meeting){
												meetingViewDialog(ev, meeting);
											}
											/*----------------------------------------------------------------------------------------------------*/
											$scope.addNewMeeting = function(){
												if (!$scope.selectedStartup.meetings){
													$scope.selectedStartup.meetings = [];
												}
												$scope.selectedStartup.meetings.push({
													meetingDate: new Date()
												});
											}
										} ],
								link : function(scope, elem, attr, parentCtrl) {

								}
							}
						} ]);