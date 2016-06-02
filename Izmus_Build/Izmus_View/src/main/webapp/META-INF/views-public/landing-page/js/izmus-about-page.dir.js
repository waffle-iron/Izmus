angular
		.module('izmusLandingPageApp')
		.directive(
				'izmusAboutPage',
				['$timeout', '$mdMedia',
						function($timeout, $mdMedia) {
							return {
								restrict : 'E',
								templateUrl : '/views-public/landing-page/templates/about-page.html',
								scope : {

								},
								controller : [
										'$scope',
										function($scope) {
											$scope.globalAttr = globalAttr;
											$scope.lang = lang;
											$scope.backgroundSwitch = true;
											/*----------------------------------------------------------------------------------------------------*/
											$scope.getSectionBackgroundImage = function(section){
												if ($mdMedia('sm') || $mdMedia('xs')){
													return $scope.getMobileBackground(section);
												}
												else {
													return $scope.getWebBackground(section);
												}
											}
											/*----------------------------------------------------------------------------------------------------*/
											$scope.getMobileBackground = function(section){
												if ($scope.backgroundSwitch){
													if (section == 'one'){
														return "url('/views-public/landing-page/images/about/mobile/about-background.jpg')";
													}
													else if (section == 'two'){
														return "url('/views-public/landing-page/images/about/mobile/about-background-2.jpg')";
													}	
												}
												else {
													if (section == 'two'){
														return "url('/views-public/landing-page/images/about/mobile/about-background.jpg')";
													}
													else if (section == 'one'){
														return "url('/views-public/landing-page/images/about/mobile/about-background-2.jpg')";
													}
												}
											}
											/*----------------------------------------------------------------------------------------------------*/
											$scope.getWebBackground = function(section){
												if ($scope.backgroundSwitch){
													if (section == 'one'){
														return "url('/views-public/landing-page/images/about/about-background.jpg')";
													}
													else if (section == 'two'){
														return "url('/views-public/landing-page/images/about/about-background-2.jpg')";
													}	
												}
												else {
													if (section == 'two'){
														return "url('/views-public/landing-page/images/about/about-background.jpg')";
													}
													else if (section == 'one'){
														return "url('/views-public/landing-page/images/about/about-background-2.jpg')";
													}
												}
											}
											/*----------------------------------------------------------------------------------------------------*/
//											$scope.backgroundLoop = function(){
//												$timeout(function(){
//													$scope.backgroundSwitch = !$scope.backgroundSwitch;
//													$scope.backgroundLoop();
//												}, 13000);
//											}
//											$scope.backgroundLoop();
										} ],
								link : function(scope, elem, attr) {
									
								}
							}
						} ]);
