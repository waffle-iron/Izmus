angular.module('availableStartupsApp').directive('availableStartupsList', 
		['loadAllAvailableStartups',function(loadAllAvailableStartups) {
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
			$scope.pageSize = 10;
			/*----------------------------------------------------------------------------------------------------*/
			$scope.goSearch = function(){
				$scope.sidenavCtrl.progressMode = 'indeterminate';
				$scope.goSearchText = $scope.search;
				$scope.setVirtualRepeat();
			}
			/*----------------------------------------------------------------------------------------------------*/
			$scope.setVirtualRepeat = function(){
				// In this example, we set up our model using a class.
		        // Using a plain object works too. All that matters
		        // is that we implement getItemAtIndex and getLength.
		        var DynamicItems = function() {
		          /**
		           * @type {!Object<?Array>} Data pages, keyed by page number (0-index).
		           */
		          this.loadedPages = {};
		          /** @type {number} Total number of items. */
		          this.numItems = {itemNumber : 0};
		          /** @const {number} Number of items to fetch per request. */
		          this.fetchNumItems_();
		        };
		        // Required.
		        DynamicItems.prototype.getItemAtIndex = function(index) {
		          var pageNumber = Math.floor(index / $scope.pageSize);
		          var page = this.loadedPages[pageNumber];
		          if (page) {
		            return page[index % $scope.pageSize];
		          } else if (page !== null) {
		            this.fetchPage_(pageNumber);
		          }
		        };
		        // Required.
		        DynamicItems.prototype.getLength = function() {
		          return this.numItems.itemNumber;
		        };
		        DynamicItems.prototype.fetchPage_ = function(pageNumber) {
		          // Set the page to null so we know it is already being fetched.
		          var loadedPages = this.loadedPages;
		          loadedPages[pageNumber] = null;
		          loadAllAvailableStartups(pageNumber, $scope.goSearchText, $scope.pageSize).then(function(data){
		        	  	loadedPages[pageNumber] = data[0];
		          }, function(){
		        	  
		          });
		        };
		        DynamicItems.prototype.fetchNumItems_ = function() {
		        	var loadedPages = this.loadedPages;
		        	var numItems = this.numItems;
		        	loadedPages[0] = null;
		        	loadAllAvailableStartups(0, $scope.goSearchText, $scope.pageSize).then(function(data){
		        	  	loadedPages[0] = data[0];
						numItems.itemNumber = data[1];
						$scope.sidenavCtrl.progressMode = '';
		        	}, function(){
		        		
		        	});
		        	return numItems.itemNumber;
		        };
		        $scope.availableStartups = new DynamicItems();
			}
			/*----------------------------------------------------------------------------------------------------*/
			$scope.setVirtualRepeat();
			/*----------------------------------------------------------------------------------------------------*/
		}],
		link : function(scope, elem, attr, parentCtrl) {
			/*----------------------------------------------------------------------------------------------------*/
			scope.selectStartup = function(startup){
				parentCtrl.selectStartup(startup);
			}
		}
	}
} ]);