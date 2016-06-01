angular
		.module('startupAssessmentApp')
		.directive(
				'izmusStartupAdditionalDocuments',
				[ 'Upload','$timeout', function(Upload, $timeout) {
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
									$scope.uploadFiles = function(file, errFiles) {
								        $scope.f = file;
								        $scope.errFile = errFiles && errFiles[0];
								        if (file) {
								            file.upload = Upload.upload({
								                url: '/api/StartupAssessment/UploadAdditionalDocument',
								                headers: {
											        'Content-Type': "application/x-www-form-urlencoded; charset=UTF-8",
											        'Upgrade-Insecure-Requests': "1",
											        'X-CSRF-TOKEN': globalAttr.sessionToken
											    },
											    fields: {'startupId': '1234'}, 
											    file: file
								            }).progress(function (evt) {
								                var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
								                console.log('progress: ' + progressPercentage + '% ' + evt.config.file.name);
								            }).success(function (data, status, headers, config) {
								                console.log('file ' + config.file.name + 'uploaded. Response: ' + data);
								            });

								            file.upload.then(function (response) {
								                $timeout(function () {
								                    file.result = response.data;
								                });
								            }, function (response) {
								                if (response.status > 0)
								                    $scope.errorMsg = response.status + ': ' + response.data;
								            }, function (evt) {
								                file.progress = Math.min(100, parseInt(100.0 * 
								                                         evt.loaded / evt.total));
								            });
								        }   
								    }
								} ],
						link : function(scope, elem, attr, parentCtrl) {

						}
					}
				} ]);