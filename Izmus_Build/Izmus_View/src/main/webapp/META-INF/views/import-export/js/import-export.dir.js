angular.module('importExportApp').directive('importExport',
		[ 'Upload', function(Upload) {
			return {
				restrict : 'E',
				templateUrl : '/views/import-export/templates/import-export.html',
				controller : [ '$scope', function($scope) {
					$scope.lang = lang;
					$scope.globalAttr = globalAttr;
					/*----------------------------------------------------------------------------------------------------*/
					$scope.uploadFile = function(file) {
						if (file) {
							file.upload = Upload.upload(
								{
									url : '/api/ImportExport/StartupCSV',
									headers : {
										'Content-Type' : "application/x-www-form-urlencoded; charset=UTF-8",
										'Upgrade-Insecure-Requests' : "1",
										'X-CSRF-TOKEN' : globalAttr.sessionToken
									},
									fields : {
										
									},
									file : file
								});
							file.upload.then(function(response) {
									file.result = response.data;
								},function(response) {
									
								},function(evt) {
									file.progress = Math.min(100,parseInt(100.0	* evt.loaded / evt.total));
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