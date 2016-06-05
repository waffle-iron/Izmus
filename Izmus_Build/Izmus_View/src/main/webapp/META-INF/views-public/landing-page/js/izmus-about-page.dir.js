angular
		.module('izmusLandingPageApp')
		.directive(
				'izmusAboutPage',
				['$timeout', '$mdMedia','contactUsDialog','$document',
						function($timeout, $mdMedia, contactUsDialog, $document) {
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
									scope.element = elem;
									/*----------------------------------------------------------------------------------------------------*/
									scope.contactDialog = function(ev){
										contactUsDialog(ev, elem);
									}
								}
							}
						} ]);
angular.module('izmusLandingPageApp').directive('aniView',['$document','$window', function($document, $window) {
    return {
        restrict: 'A',
        transclude: true,
        replace: true,
        template: '<div ng-transclude ng-show=\'show\'></div>',
        scope: {
            show: '@',
        },
        link: function(scope, element, attrs) {
        	var parent = angular.element($document[0].getElementById("about-scroll"));
        	scope.getScrollOffsets = function(w) {

                // Use the specified window or the current window if no argument
                w = w || window;

                // This works for all browsers except IE versions 8 and before
                if (w[0].scrollTop != null && w[0].scrollLeft != null) {
                    return {
                        x: w[0].scrollLeft,
                        y: w[0].scrollTop
                    };
                }

            };
            scope.getPosition = function(e) {
                return {
                    x: e[0].offsetLeft,
                    y: e[0].offsetTop
                };
            }
            scope.getViewPortSize = function(w) {

                return {
                    x: Math.max(document.documentElement.clientWidth, w.innerWidth || 0) -70,
                    y: Math.max(document.documentElement.clientHeight, w.innerHeight || 0) -70
                }


            }
            scope.show = false;

            updateElementVisiblityOnScroll();


            function updateElementVisiblityOnScroll(){
              angular.element(parent).bind('scroll', updateElementVisiblity);
            }

            //enables elements to be shown if they're already on viewport when page's loaded
            function updateElementOnPageLoad(){
              setTimeout(updateElementVisiblity, 0);
            }

            function updateElementVisiblity(){
              scope.show = elementIsOnViewport(scope, element, $window)
              scope.$apply();
            }

            function elementIsOnViewport(scope, element, $window){
              var position = scope.getPosition(element);
              var offset = scope.getScrollOffsets(parent);
              var viewport = scope.getViewPortSize($window);
              var coverage = {
                  x: parseInt(viewport.x + offset.x),
                  y: parseInt(viewport.y + offset.y)
              }
              if (coverage.y >= position.y && ((offset.y-viewport.y) <= position.y) 
            		  && coverage.x >= position.x &&((offset.x-viewport.x) <= position.x)) {
                return true;
              }
              return false;
            }
        }
    }
}]);
