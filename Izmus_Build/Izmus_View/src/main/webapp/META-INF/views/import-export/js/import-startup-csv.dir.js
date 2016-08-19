angular.module('importExportApp').directive('importStartupCsv',
		[ 'Upload','$mdToast', function(Upload, $mdToast) {
			return {
				restrict : 'E',
				templateUrl : '/views/import-export/templates/import-startup-csv.html',
				controller : [ '$scope', function($scope) {
					$scope.lang = lang;
					$scope.globalAttr = globalAttr;
					$scope.progressMode = '';
					/*----------------------------------------------------------------------------------------------------*/
					$scope.uploadFile = function(file) {
						if (file) {
							file.upload = Upload.upload(
								{
									url : '/api/ImportExport/StartupCSV',
									headers : {
										'Upgrade-Insecure-Requests' : "1",
										'X-CSRF-TOKEN' : globalAttr.sessionToken
									},
									fields : {
										
									},
									file : file
								});
							file.upload.then(function(response) {
									file.result = response.data;
									if (response.data && response.data.result == 'success'){
										$scope.showMessage($scope.lang.processedSuccessfully);
									}
									else{
										$scope.showMessage($scope.lang.processFailed);
									}
									$scope.progressMode = '';
								},function(response) {
									$scope.showMessage($scope.lang.processFailed);
									$scope.progressMode = '';
								},function(evt) {
									file.progress = Math.min(100,parseInt(100.0	* evt.loaded / evt.total));
									if (file.progress == 100){
										$scope.progressMode = 'indeterminate';
									}
								});
						}
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