angular.module('wishListApp').directive('startupGridList', 
		[ '$mdMedia', 'startupPreviewDialog', 
		 '$mdToast', 'loadAllWishlist', 'triggerAnalysis',
		 function($mdMedia, startupPreviewDialog,
				 $mdToast, loadAllWishlist, triggerAnalysis) {
	return {
		restrict : 'E',
		templateUrl : '/views/wish-list/templates/startup-grid-list.html',
		controller : ['$scope',function($scope) {
			$scope.globalAttr = globalAttr;
			$scope.lang = lang;
			$scope.progressMode = '';
			$scope.pageSize = 60;
			/*----------------------------------------------------------------------------------------------------*/
			$scope.isSmallerDevice = function(){
				return (!$mdMedia('gt-sm'));
			}
			/*----------------------------------------------------------------------------------------------------*/
			if ($scope.isSmallerDevice()){
				$scope.numberItemsPerRow = 1;
			}
			else {
				$scope.numberItemsPerRow = 3;
			}
			/*----------------------------------------------------------------------------------------------------*/
			$scope.isSmallDevice = function(){
				return (!$mdMedia('gt-md'));
			}
			/*----------------------------------------------------------------------------------------------------*/
			$scope.setVirtualRepeat = function(){
				$scope.progressMode = 'indeterminate';
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
		          loadAllWishlist(pageNumber, 
		        		  $scope.pageSize).then(function(data){
		        	  	loadedPages[pageNumber] = $scope.createPage(data[0]);
		          }, function(){
		        	  
		          });
		        };
		        DynamicItems.prototype.fetchNumItems_ = function() {
		        	var loadedPages = this.loadedPages;
		        	var numItems = this.numItems;
		        	loadedPages[0] = null;
		        	loadAllWishlist(0, 
		        			$scope.pageSize).then(function(data){
		        	  	loadedPages[0] = $scope.createPage(data[0]);
						numItems.itemNumber = Math.ceil(data[1] / $scope.numberItemsPerRow);
						$scope.progressMode = '';
		        	}, function(){
		        		
		        	});
		        	return numItems.itemNumber;
		        };
		        $scope.availableStartups = new DynamicItems();
			}
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
			/*----------------------------------------------------------------------------------------------------*/
			$scope.viewStartup = function(ev, startup){
				startupPreviewDialog(ev, 
						startup, 
						$scope.getSectorIconForStartup(startup), 
						$scope.addToMyRequests);
			}
			/*----------------------------------------------------------------------------------------------------*/
			$scope.addToMyRequests = function(startup){
				$scope.progressMode = 'indeterminate';
				triggerAnalysis(startup.startupId).then(function(data){
					if(data.result == 'success'){
						$scope.showMessage($scope.lang.addedToMyRequests);
						$scope.setVirtualRepeat();
						$scope.progressMode = '';
					}
					else {
						$scope.showMessage($scope.lang.notAddedRequests);
						$scope.progressMode = '';
					}
				}, function(){
					$scope.showMessage($scope.lang.notAddedRequests);
					$scope.progressMode = '';
				});
			}
			/*----------------------------------------------------------------------------------------------------*/
			$scope.getSectorIconForStartup = function(startup){
				switch(startup.sector){
					case 'Social Media and Advertising':
						return '/views/finders-dashboard/images/social-media.svg';
					case 'Industrial Technologies':
						return '/views/finders-dashboard/images/industry.svg';
					case 'Digital Health and Medical Technologies':
						return '/views/finders-dashboard/images/hospital.svg';
					case 'Mobile and Telecom Technologies':
						return '/views/finders-dashboard/images/mobile.svg';
					case 'Education and Knowledge Technologies':
						return '/views/finders-dashboard/images/education.svg';
					case 'Fintech and eCommerce':
						return '/views/finders-dashboard/images/finance.svg';
					case 'Security and Safety Technologies':
						return '/views/finders-dashboard/images/security.svg';
					case 'Enterprise Solutions':
						return '/views/finders-dashboard/images/enterprise.svg';
					case 'Cleantech':
						return '/views/finders-dashboard/images/clean.svg';
					case 'Agro and Food Technologies':
						return '/views/finders-dashboard/images/food.svg';
					case 'Software Applications':
						return '/views/finders-dashboard/images/application.svg';
					case 'Pharmaceuticals':
						return '/views/finders-dashboard/images/pharma.svg';
					case 'Consumer Electronics':
						return '/views/finders-dashboard/images/electronics.svg';
					default:
						return '/views/core/izmus-nav-bar/images/startup.svg';	
				}
			}
			/*----------------------------------------------------------------------------------------------------*/
			$scope.setVirtualRepeat();
		}],
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