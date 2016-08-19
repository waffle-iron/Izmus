angular.module('meetingsApp').directive('meetingsDashboard',
		[ 'loadAllMeetings','meetingViewDialog','exportGeneralMeeting','saveGeneralMeeting', '$mdToast',
		  function(loadAllMeetings,meetingViewDialog,exportGeneralMeeting, saveGeneralMeeting, $mdToast) {
			return {
				restrict : 'E',
				templateUrl : '/views/meetings/templates/meetings.html',
				controller : [ '$scope', function($scope) {
					$scope.meetings = [];
					$scope.lang = lang;
					$scope.globalAttr = globalAttr;
					$scope.progressMode = 'indeterminate';
					/*----------------------------------------------------------------------------------------------------*/
					loadAllMeetings().then(function(data){
						$scope.meetings = data;
						$scope.progressMode = '';
					},
					function(){
						$scope.progressMode = '';
					});
					/*----------------------------------------------------------------------------------------------------*/
					$scope.parseDate = function(meeting){
						meeting.meetingDate = new Date(meeting.meetingDate);
					}
					/*----------------------------------------------------------------------------------------------------*/
					$scope.$watch('meetings', function(){
						if ($scope.meetings.generalMeetings) for (var i = 0; i < $scope.meetings.generalMeetings.length; i++){
							var meeting = $scope.meetings.generalMeetings[i];
							$scope.parseDate(meeting);
						}
						if ($scope.meetings.startupMeetings) for (var i = 0; i < $scope.meetings.startupMeetings.length; i++){
							var meeting = $scope.meetings.startupMeetings[i];
							$scope.parseDate(meeting);
						}
					});
					/*----------------------------------------------------------------------------------------------------*/
					$scope.viewGeneralMeeting = function(ev, meeting){
						meetingViewDialog(ev, meeting, function(){
							$scope.progressMode = 'indeterminate';
							exportGeneralMeeting(meeting).then(function(){
								$scope.progressMode = '';
							});
						},function(){
							$scope.progressMode = 'indeterminate';
							saveGeneralMeeting(meeting).then(function(data){
								if (data.result == 'success'){
									$scope.progressMode = '';
									meeting.meetingId = data.meetingId;
									$scope.showMessage($scope.lang.saveSuccess);
								}
								else {
									$scope.showMessage($scope.lang.saveFail);
								}
							}, function(){
								$scope.showMessage($scope.lang.saveFail);
							});
						});
					}
					/*----------------------------------------------------------------------------------------------------*/
					$scope.viewStartupMeeting = function(ev, meeting){
						meetingViewDialog(ev, meeting, function(){
							$scope.progressMode = 'indeterminate';
							exportGeneralMeeting(meeting).then(function(){
								$scope.progressMode = '';
							});
						});
					}
					/*----------------------------------------------------------------------------------------------------*/
					$scope.exportPDF = function(meeting){
						$scope.progressMode = 'indeterminate';
						exportGeneralMeeting(meeting).then(function(){
							$scope.progressMode = '';
						});
					}
					/*----------------------------------------------------------------------------------------------------*/
					$scope.addGeneralMeeting = function(ev){
						if (!$scope.meetings.generalMeetings){
							$scope.meetings.generalMeetings = [];
						}
						var newMeeting = {
							meetingDate: new Date()
						};
						$scope.viewGeneralMeeting(ev, newMeeting);
						$scope.meetings.generalMeetings.push(newMeeting);
					}
				} ],
				link : function(scope, elem, attr) {
					/*----------------------------------------------------------------------------------------------------*/
					scope.showMessage = function(message){
						$mdToast.show({
						      controller: 'toastCtrl',
						      templateUrl: '/views/core/toast/templates/toast.html',
						      parent : angular.element(elem),
						      hideDelay: 2600,
						      position: 'top right',
						      locals: {
						    	  message: message
						      }
						    });
					}
				}
			}
		} ]);