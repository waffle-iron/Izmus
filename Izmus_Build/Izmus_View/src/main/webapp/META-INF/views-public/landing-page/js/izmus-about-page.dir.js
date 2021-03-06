angular
		.module('izmusLandingPageApp')
		.directive(
				'izmusAboutPage',
				['$timeout', '$mdMedia','contactUsDialog','$document','$interval',
						function($timeout, $mdMedia, contactUsDialog, $document, $interval) {
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
											$scope.section = {
													1: {add: false, show: false},
													2: {add: false, show: false},
													3: {add: false, show: false},
													4: {add: false, show: false},
													5: {add: false, show: false},
													6: {add: false, show: false},
													7: {add: false, show: false},
													8: {add: false, show: false},
													9: {add: false, show: false},
													10: {add: false, show: false}
											};
											$scope.counter = 2;
//											$scope.backgroundSwitch = true;
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
											$timeout(function(){
												$scope.section[1]['show'] = true;
												$scope.section[1]['add'] = true;
											}, 2000);
											/*----------------------------------------------------------------------------------------------------*/
											$interval(function(){
												if ($scope.counter == 1){
													$scope.section[10]['show'] = false;
													$scope.section[9]['show'] = false;
													$scope.section[8]['show'] = false;
													$scope.section[7]['show'] = false;
													$timeout(function(){
														$scope.section[10]['add'] = false;
														$scope.section[9]['add'] = false;
														$scope.section[8]['add'] = false;
														$scope.section[7]['add'] = false;
													}, 2000);
												}
												else if ($scope.counter == 7){
													$scope.section[6]['show'] = false;
													$scope.section[5]['show'] = false;
													$scope.section[4]['show'] = false;
													$scope.section[3]['show'] = false;
													$scope.section[2]['show'] = false;
													$timeout(function(){
														$scope.section[6]['add'] = false;
														$scope.section[5]['add'] = false;
														$scope.section[4]['add'] = false;
														$scope.section[3]['add'] = false;
														$scope.section[2]['add'] = false;
													}, 2000);
												}
												else if ($scope.counter - 1 != 2 && $scope.counter - 1 != 7){
													$scope.section[$scope.counter-1]['show'] = false;
													$timeout(function(){
														$scope.section[$scope.counter-1]['add'] = false;
													}, 2000);
												}
												$timeout(function(){
													$scope.section[$scope.counter]['show'] = true;
													$scope.section[$scope.counter]['add'] = true;
													if ($scope.counter == 2){
														$scope.counter = 3;
														$scope.section[$scope.counter]['show'] = true;
														$scope.section[$scope.counter]['add'] = true;
														$timeout(function(){
															$scope.counter = 4;
															$scope.section[$scope.counter]['show'] = true;
															$scope.section[$scope.counter]['add'] = true;
															$timeout(function(){
																$scope.counter = 5;
																$scope.section[$scope.counter]['show'] = true;
																$scope.section[$scope.counter]['add'] = true;
																$timeout(function(){
																	$scope.counter = 6;
																	$scope.section[$scope.counter]['show'] = true;
																	$scope.section[$scope.counter]['add'] = true;
																	$scope.counter = 7;
																}, 100);
															}, 100);
														}, 100);
													}
													else if ($scope.counter == 7){
														$scope.counter = 8;
														$scope.section[$scope.counter]['show'] = true;
														$scope.section[$scope.counter]['add'] = true;
														$timeout(function(){
															$scope.counter = 9;
															$scope.section[$scope.counter]['show'] = true;
															$scope.section[$scope.counter]['add'] = true;
															$timeout(function(){
																$scope.counter = 10;
																$scope.section[$scope.counter]['show'] = true;
																$scope.section[$scope.counter]['add'] = true;
																$scope.counter = 1;
															}, 100);
														}, 100);
													}
													else {
														$scope.counter = (($scope.counter + 1) % 11) == 0 ? 1 : ($scope.counter + 1) % 11;
													}
												}, 2000);
											},7000);
											/*----------------------------------------------------------------------------------------------------*/
											$scope.showSection = function(sectionNumber){
												return $scope.section[sectionNumber]['show'];
											}
											/*----------------------------------------------------------------------------------------------------*/
											$scope.addSection = function(sectionNumber){
												return $scope.section[sectionNumber]['add'];
											}
											/*----------------------------------------------------------------------------------------------------*/
											$scope.getMobileBackground = function(section){
//												if ($scope.backgroundSwitch){
													if (section == 'one'){
														return "url('/views-public/landing-page/images/about/mobile/about-background.jpg')";
													}
													else if (section == 'three'){
														return "url('/views-public/landing-page/images/about/mobile/about-background-2.jpg')";
													}
													else if (section == 'four'){
														return "url('/views-public/landing-page/images/the-team/mobile/the-team-background.jpg')";
													}
//												}
//												else {
//													if (section == 'two'){
//														return "url('/views-public/landing-page/images/about/mobile/about-background.jpg')";
//													}
//													else if (section == 'one'){
//														return "url('/views-public/landing-page/images/about/mobile/about-background-2.jpg')";
//													}
//												}
											}
											/*----------------------------------------------------------------------------------------------------*/
											$scope.getWebBackground = function(section){
//												if ($scope.backgroundSwitch){
													if (section == 'one'){
														return "url('/views-public/landing-page/images/about/about-background.jpg')";
													}
													else if (section == 'three'){
														return "url('/views-public/landing-page/images/about/about-background-2.jpg')";
													}
													else if (section == 'four'){
														return "url('/views-public/landing-page/images/the-team/the-team-background.jpg')";
													}	
//												}
//												else {
//													if (section == 'two'){
//														return "url('/views-public/landing-page/images/about/about-background.jpg')";
//													}
//													else if (section == 'one'){
//														return "url('/views-public/landing-page/images/about/about-background-2.jpg')";
//													}
//												}
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
angular.module('izmusLandingPageApp').directive('aniView',['$document','$window','$timeout', function($document, $window, $timeout) {
    return {
        restrict: 'A',
        transclude: true,
        replace: true,
        template: '<div ng-transclude ng-show=\'show\'></div>',
        scope: {
            show: '@',
        },
        link: function(scope, element, attrs) {
        	scope.sensitivity = 0;
        	scope.parent = angular.element($document[0].getElementById("about-scroll"));
        	scope.show = false;
			/*----------------------------------------------------------------------------------------------------*/
        	scope.getScrollOffsets = function(w) {

                // Use the specified window or the current window if no argument
                w = w || window;
                var scrollTop = w[0].scrollTop;
                var scrollLeft = w[0].scrollLeft;
                // This works for all browsers except IE versions 8 and before
                if (scrollTop != null && scrollLeft != null) {
                	if (scrollLeft == 0 && scrollTop == 0){
                    	return null;
                    }
                    return {
                        x: w[0].scrollLeft,
                        y: w[0].scrollTop
                    };
                }

            };
			/*----------------------------------------------------------------------------------------------------*/
            scope.getPosition = function(e) {
                return {
                    x: e[0].offsetLeft,
                    y: e[0].offsetTop
                };
            };
			/*----------------------------------------------------------------------------------------------------*/
            scope.getViewPortSize = function(w) {
                return {
                    x: Math.max(document.documentElement.clientWidth, w.innerWidth || 0) - scope.sensitivity,
                    y: Math.max(document.documentElement.clientHeight, w.innerHeight || 0) - scope.sensitivity
                }
            }
			/*----------------------------------------------------------------------------------------------------*/
            scope.updateElementVisiblityOnScroll = function(){
              angular.element(scope.parent).bind('scroll', scope.updateElementVisiblity);
            }
			/*----------------------------------------------------------------------------------------------------*/
            //enables elements to be shown if they're already on viewport when page's loaded
            scope.updateElementOnPageLoad = function (){
              setTimeout(scope.updateElementVisiblity, 0);
            }
			/*----------------------------------------------------------------------------------------------------*/
            scope.updateElementVisiblity = function (){
              scope.show = scope.elementIsOnViewport(scope, element, $window)
              scope.$apply();
            }
			/*----------------------------------------------------------------------------------------------------*/
            scope.elementIsOnViewport = function (scope, element, $window){
              var position = scope.getPosition(element);
              var offset = scope.getScrollOffsets(scope.parent);
              if (offset == null) return true;
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
			/*----------------------------------------------------------------------------------------------------*/
            scope.updateElementVisiblityOnScroll();
            $timeout(function(){
            	scope.updateElementOnPageLoad();
            }, 2000);
        }
    }
}]);
