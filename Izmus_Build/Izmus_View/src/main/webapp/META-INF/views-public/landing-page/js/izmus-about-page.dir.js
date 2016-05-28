angular
		.module('izmusLandingPageApp')
		.directive(
				'izmusAboutPage',
				[
						function() {
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
										} ],
								link : function(scope, elem, attr) {
									
								}
							}
						} ]);
