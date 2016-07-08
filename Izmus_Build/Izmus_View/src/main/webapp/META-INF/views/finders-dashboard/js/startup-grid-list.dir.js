angular.module('findersDashboardApp').directive('startupGridList', 
		['loadAllAvailableStartups','loadAllSectors','$mdMedia',function(loadAllAvailableStartups,loadAllSectors, $mdMedia) {
	return {
		restrict : 'E',
		templateUrl : '/views/finders-dashboard/templates/startup-grid-list.html',
		controller : ['$scope',function($scope) {
			$scope.globalAttr = globalAttr;
			$scope.lang = lang;
			$scope.searchMinimized = false;
			$scope.progressMode = 'indeterminate';
			$scope.numberItemsPerRow = 3;
			$scope.pageSize = 60;
			/*----------------------------------------------------------------------------------------------------*/
			$scope.toggleSearchBar = function(){
				$scope.searchMinimized = !$scope.searchMinimized;
			}
			/*----------------------------------------------------------------------------------------------------*/
			$scope.isSearchMinimized = function(){
				return $scope.searchMinimized;
			}
			/*----------------------------------------------------------------------------------------------------*/
			$scope.isSmallDevice = function(){
				return (!$mdMedia('gt-md'));
			}
			/*----------------------------------------------------------------------------------------------------*/
			$scope.goSearch = function(){
				$scope.progressMode = 'indeterminate';
				$scope.goSearchText = $scope.search;
				$scope.goFilterSector = $scope.filterSector;
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
		          this.fetchNumItems_();
		        };
		        // Required.
		        DynamicItems.prototype.getItemAtIndex = function(index) {
		          var pageNumber = Math.floor(index / ($scope.pageSize / $scope.numberItemsPerRow));
		          var page = this.loadedPages[pageNumber];
		          if (page) {
		            return page[index % ($scope.pageSize / $scope.numberItemsPerRow)];
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
		          loadAllAvailableStartups(pageNumber, $scope.goSearchText, $scope.goFilterSector, $scope.pageSize).then(function(data){
		        	  	loadedPages[pageNumber] = $scope.createPage(data.content);
		          }, function(){
		        	  
		          });
		        };
		        DynamicItems.prototype.fetchNumItems_ = function() {
		        	var loadedPages = this.loadedPages;
		        	var numItems = this.numItems;
		        	loadedPages[0] = null;
		        	loadAllAvailableStartups(0, $scope.goSearchText, $scope.goFilterSector, $scope.pageSize).then(function(data){
		        	  	loadedPages[0] = $scope.createPage(data.content);
						numItems.itemNumber = Math.ceil(data.totalElements / $scope.numberItemsPerRow);
						$scope.progressMode = '';
		        	}, function(){
		        		
		        	});
		        	return numItems.itemNumber;
		        };
				/*----------------------------------------------------------------------------------------------------*/
		        $scope.createPage = function(data){
		        	var returnArray = [];
		        	for(var i = 0; i < Math.ceil(data.length / $scope.numberItemsPerRow); i++){
		        		var newRow = [];
		        		newRow.push(data[i * $scope.numberItemsPerRow]);
		        		if (data[i * $scope.numberItemsPerRow + 1]){
		        			newRow.push(data[i * $scope.numberItemsPerRow + 1]);
		        		}
		        		if (data[i * $scope.numberItemsPerRow + 2]){
		        			newRow.push(data[i * $scope.numberItemsPerRow + 2]);
		        		}
		        		returnArray.push(newRow);
		        	}
		        	return returnArray;
		        }
		        $scope.availableStartups = new DynamicItems();
			}
			/*----------------------------------------------------------------------------------------------------*/
			$scope.setVirtualRepeat();
		}],
		link : function(scope, elem, attr) {
			
		}
	}
} ]);