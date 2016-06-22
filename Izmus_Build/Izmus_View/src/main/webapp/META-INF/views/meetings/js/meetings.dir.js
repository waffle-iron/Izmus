angular.module('meetingsApp').directive('meetingsDashboard',
		[ 'loadAllMeetings','meetingViewDialog',function(loadAllMeetings,meetingViewDialog) {
			return {
				restrict : 'E',
				templateUrl : '/views/meetings/templates/meetings.html',
				controller : [ '$scope', function($scope) {
					$scope.meetings = [];
					$scope.lang = lang;
					$scope.globalAttr = globalAttr;
					/*----------------------------------------------------------------------------------------------------*/
					loadAllMeetings().then(function(data){
						$scope.meetings = $scope.meetings.concat(data.generalMeetings);
						$scope.meetings = $scope.meetings.concat(data.startupMeetings);
					},
					function(){
						
					});
					/*----------------------------------------------------------------------------------------------------*/
					$scope.parseDate = function(meeting){
						meeting.meetingDate = new Date(meeting.meetingDate);
					}
					/*----------------------------------------------------------------------------------------------------*/
					$scope.$watch('meetings', function(){
						for (var i = 0; i < $scope.meetings.length; i++){
							var meeting = $scope.meetings[i];
							$scope.parseDate(meeting);
						}
					});
					/*----------------------------------------------------------------------------------------------------*/
					$scope.viewMeeting = function(ev, meeting){
						meetingViewDialog(ev, meeting);
					}
				} ],
				link : function(scope, elem, attr) {
					
				}
			}
		} ]);