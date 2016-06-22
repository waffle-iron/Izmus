/*----------------------------------------------------------------------------------------------------*/
angular.module('meetingsApp').factory('meetingViewDialog',
		[ '$mdMedia', '$mdDialog', function($mdMedia, $mdDialog) {
			return function(ev, meeting, exportFunction, saveMeeting) {
				var customFullscreen = $mdMedia('xs') || $mdMedia('sm');
				var useFullScreen = ($mdMedia('sm') || $mdMedia('xs')) && customFullscreen;
			    /*----------------------------------------------------------------------------------------------------*/
				var meetingViewCtrl = function($scope) {
				    $scope.globalAttr = globalAttr;
				    $scope.lang = lang;
				    $scope.meeting = meeting;
				    $scope.saveMeeting = saveMeeting;
				    /*----------------------------------------------------------------------------------------------------*/
					$scope.export = function() {
						if (exportFunction){
							$mdDialog.cancel();
							exportFunction();
						}
					};
					/*----------------------------------------------------------------------------------------------------*/
					$scope.ok = function() {
						$mdDialog.cancel();
					};
				    /*----------------------------------------------------------------------------------------------------*/
					$scope.save = function(){
						if (saveMeeting){
							$mdDialog.cancel();
							saveMeeting();
						}
					}
				}
			    /*----------------------------------------------------------------------------------------------------*/
				$mdDialog.show({
				    controller: meetingViewCtrl,
				    templateUrl: '/views/startup-assessment/templates/meeting-view.dialog.html',
				    parent: angular.element(document.body),
				    targetEvent: ev,
				    clickOutsideToClose: true,
				    fullscreen: useFullScreen
				});
			}
		} ]);
/*----------------------------------------------------------------------------------------------------*/
angular.module('meetingsApp').factory('loadAllMeetings',
		[ '$q', '$http', function($q, $http) {
			return function() {
				return $q(function(resolve, reject) {
					$http({
						method : 'GET',
						url : '/api/Meetings',
					}).then(function successCallback(response) {
						if (response.data) {
							resolve(response.data);
						} else {
							reject();
						}
					}, function errorCallback(response) {
						reject();
					});
				})
			}
		} ]);
/*----------------------------------------------------------------------------------------------------*/
angular.module('meetingsApp').factory('exportGeneralMeeting',
		[ '$q', '$http', '$httpParamSerializer', function($q, $http, $httpParamSerializer) {
			return function(meeting) {
				return $q(function(resolve, reject) {
					$http({
						method : 'POST',
						url : '/api/Meetings/MeetingReport',
						headers: {
					        'Content-Type': "application/x-www-form-urlencoded; charset=UTF-8",
					        'Upgrade-Insecure-Requests': "1",
					        'X-CSRF-TOKEN': globalAttr.sessionToken
					        
					    },
					    data : $httpParamSerializer({
					    	meeting: meeting,
					    })
					}).then(function successCallback(response) {
						window.location = "/Export/MeetingReport/" + response.data.reportId;
						resolve();
					}, function errorCallback(response) {
						reject();
					});
				})
			}
		} ]);
/*----------------------------------------------------------------------------------------------------*/
angular.module('meetingsApp').factory('saveGeneralMeeting',
		[ '$q', '$http', '$httpParamSerializer', function($q, $http, $httpParamSerializer) {
			return function(meeting) {
				return $q(function(resolve, reject) {
					$http({
						method : 'POST',
						url : '/api/Meetings',
						headers: {
					        'Content-Type': "application/x-www-form-urlencoded; charset=UTF-8",
					        'Upgrade-Insecure-Requests': "1",
					        'X-CSRF-TOKEN': globalAttr.sessionToken
					    },
					    data : $httpParamSerializer({
					    	meeting: meeting,
					    })
					}).then(function successCallback(response) {
						resolve(response.data);
					}, function errorCallback(response) {
						reject();
					});
				})
			}
		} ]);