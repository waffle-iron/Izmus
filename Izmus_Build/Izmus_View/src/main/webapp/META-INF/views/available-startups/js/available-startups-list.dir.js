angular.module('availableStartupsApp').directive('availableStartupsList', 
		['loadAllAvailableStartups','$timeout',function(loadAllAvailableStartups,$timeout) {
	return {
		restrict : 'E',
		require: '^^availableStartups',
		templateUrl : '/views/available-startups/templates/available-startups-list.html',
		scope: {
			sidenavCtrl: '='
		},
		controller : ['$scope',function($scope) {
			$scope.globalAttr = globalAttr;
			$scope.lang = lang;
			/*----------------------------------------------------------------------------------------------------*/
			$scope.loadStartups = function(){
				loadAllAvailableStartups().then(function(data){
					$scope.setVirtualRepeat(data);
					$scope.rawAvailableStartups = data;
					$scope.sidenavCtrl.progressMode = '';
				}, function(){
					$scope.sidenavCtrl.progressMode = '';
				});
			}
			/*----------------------------------------------------------------------------------------------------*/
			$scope.$watch('search', function(){
				$scope.goSearch();
			});
			/*----------------------------------------------------------------------------------------------------*/
			$scope.goSearch = function(){
				if ($scope.search) {
					var found = false;
					for (var i = $scope.searchIndex + 5; i < $scope.rawAvailableStartups.length; i++){
						if ($scope.rawAvailableStartups[i].startupName.toLowerCase().indexOf($scope.search.toLowerCase()) > -1){
							$scope.searchIndex = i - 1;
							found = true;
							break;
						}
					}
					if (!found){
						for (var i = 0; i < $scope.searchIndex + 5; i++){
							if ($scope.rawAvailableStartups[i].startupName.toLowerCase().indexOf($scope.search.toLowerCase()) > -1){
								$scope.searchIndex = i - 1;
								break;
							}
						}
					}
				}
			}
			/*----------------------------------------------------------------------------------------------------*/
			$scope.setVirtualRepeat = function(data){
				// In this example, we set up our model using a class.
		        // Using a plain object works too. All that matters
		        // is that we implement getItemAtIndex and getLength.
		        var DynamicItems = function() {
		          /**
		           * @type {!Object<?Array>} Data pages, keyed by page number (0-index).
		           */
		          this.loadedPages = {};
		          /** @type {number} Total number of items. */
		          this.numItems = 0;
		          /** @const {number} Number of items to fetch per request. */
		          this.PAGE_SIZE = 20;
		          this.fetchNumItems_();
		        };
		        // Required.
		        DynamicItems.prototype.getItemAtIndex = function(index) {
		          var pageNumber = Math.floor(index / this.PAGE_SIZE);
		          var page = this.loadedPages[pageNumber];
		          if (page) {
		            return page[index % this.PAGE_SIZE];
		          } else if (page !== null) {
		            this.fetchPage_(pageNumber);
		          }
		        };
		        // Required.
		        DynamicItems.prototype.getLength = function() {
		          return this.numItems;
		        };
		        DynamicItems.prototype.fetchPage_ = function(pageNumber) {
		          // Set the page to null so we know it is already being fetched.
		          this.loadedPages[pageNumber] = null;
		          // For demo purposes, we simulate loading more items with a timed
		          // promise. In real code, this function would likely contain an
		          // $http request.
		          $timeout(angular.noop, 300).then(angular.bind(this, function() {
		            this.loadedPages[pageNumber] = [];
		            var pageOffset = pageNumber * this.PAGE_SIZE;
		            for (var i = pageOffset; i < pageOffset + this.PAGE_SIZE; i++) {
		              this.loadedPages[pageNumber].push(data[i]);
		            }
		          }));
		        };
		        DynamicItems.prototype.fetchNumItems_ = function() {
		          // For demo purposes, we simulate loading the item count with a timed
		          // promise. In real code, this function would likely contain an
		          // $http request.
		          $timeout(angular.noop, 300).then(angular.bind(this, function() {
		            this.numItems = data.length;
		          }));
		        };
		        $scope.availableStartups = new DynamicItems();
			}
			/*----------------------------------------------------------------------------------------------------*/
			$scope.loadStartups();
		}],
		link : function(scope, elem, attr, parentCtrl) {
			
		}
	}
} ]);