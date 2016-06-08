angular
		.module('izmusLandingPageApp')
		.directive(
				'languageMenu',
				[
						function() {
							return {
								restrict : 'E',
								templateUrl : '/views-public/landing-page/templates/language-menu.html',
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
