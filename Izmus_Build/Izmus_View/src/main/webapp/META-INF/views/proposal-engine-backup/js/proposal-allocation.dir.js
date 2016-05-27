angular.module('proposalAllocation', [
		'dndLists','ui.bootstrap','nvd3', 'fcsa-number' ])
.directive('investitProposalAllocation', ['$uibModal', 'getProposalAseetClassData','saveProposal', 'loadProposal',
function($uibModal, getProposalAseetClassData, saveProposal, loadProposal) {
	return {
		restrict : 'E',
		scope : {
			deleteConfirmationTitle : '@',
			deleteButton : '@',
			createProposalButton : '@',
			refreshButton : '@',
			saveButton : '@',
			loadButton : '@',
			cancelButton : '@',
			deleteConfirmationMessage : '@',
			currentAllocationHeader : '@',
			proposedAllocationHeader : '@',
			asset : '@',
			account : '@',
			realEstate : '@',
			portfolio : '@',
			expand : '@',
			collapse : '@',
			editText: '@',
			deleteText: '@',
			copyText: '@'
		},
		templateUrl : '/views/proposal-engine/templates/proposal-allocation.html',
		controller : ['$scope', function($scope){
			/*----------------------------------------------------------------------------------------------------*/
			//Deletes selected asset
			this.deleteAsset = function(deleteAssetFunctin, asset){
				$scope.openDeleteDialog(deleteAssetFunctin, asset.name);
			}
		}],
		link : function(scope, elem, attr) {
			/*----------------------------------------------------------------------------------------------------*/
			//Variables
			scope.tooltipEnable = false;
			scope.isDragStart = false;
			scope.isDroppedInZone = false;
			scope.finalDelete = function() {};
			scope.isDroppedToTrash = false;
			scope.tempProposalName = "Temporary Save";
			scope.assetClassDropdown = {
					isCurrentOpen: false,
					isProposedOpen: false,
					isEditOpen: false
			};
			scope.proposalObjectCounter = 10000;
			scope.name = [scope.currentAllocationHeader, scope.proposedAllocationHeader];
			/*----------------------------------------------------------------------------------------------------*/
			//Drag handlers
			/*----------------------------------------------------------------------------------------------------*/
			/*
			 * Handles the start drag of any one of the object (portfolio, account, asset)
			 */
			scope.handleDragStart = function(item){
				scope.isDragStart = true;
				scope.models.selected = item;
			}
			/*----------------------------------------------------------------------------------------------------*/
			/*
			 * Handles the end of a drag of any one of the object (portfolio, account, asset)
			 */
			scope.handleDragEnd = function(children, index, item){
				if (scope.isDroppedInZone){
					if (scope.isDroppedToTrash){
						scope.openDeleteDialog(function () {
							children.splice(index, 1);
							scope.models.selected = null;
						});
					}
					else {
						children.splice(index, 1);
					}
				}
				scope.isDroppedInZone = false;
				scope.isDragStart = false;
				scope.isDroppedToTrash = false;
				scope.saveProposal(scope.tempProposalName);
			}
			/*----------------------------------------------------------------------------------------------------*/
			/*
			 * Returns the item that was dropped
			 */
			scope.handleDrop = function(item, trash){
				if (scope.isDragStart){
					if (trash){
						scope.isDroppedToTrash = true;
					}
					scope.isDroppedInZone = true;
					scope.isDragStart = false;
				}
				else {
					scope.isDroppedInZoen = false;
					scope.isDragStart = false;
					scope.isDroppedToTrash = false;
				}
				scope.models.selected = item;
				return item;
			}
			/*----------------------------------------------------------------------------------------------------*/
			/*
			 * Handles drop to edit item
			 */
			scope.dropToEditItem = function(item){
				if(scope.isDragStart) {
					scope.editSelectedItem();
				}
			}
			/*----------------------------------------------------------------------------------------------------*/
			/*
			 * Handles drop to copy item
			 */
			scope.dropToCopyItem = function(item){
				if(scope.isDragStart) {
					scope.copySelectedItem();
				}
			}
			/*----------------------------------------------------------------------------------------------------*/
			/*
			 * Handles drag start from button panel
			 */
			scope.buttonDragStart = function(item){
				scope.assetClassDropdown.isCurrentOpen = false;
				scope.assetClassDropdown.isProposedOpen = false;
				scope.assetClassDropdown.isEditOpen = false;
				item.id = item.id + 1;
			}
			/*----------------------------------------------------------------------------------------------------*/
			//Click handlers
			/*----------------------------------------------------------------------------------------------------*/
			/*
			 * Handles delete item
			 */
			scope.deleteSelectedItem = function(){
				scope.tooltipEnable = false;
				if (scope.models.selected) {
					scope.openDeleteDialog(function () {
						scope.deleteItem(scope.models.selected);
					});
				}
			}
			/*----------------------------------------------------------------------------------------------------*/
			/*
			 * Handles edit item
			 */
			scope.editSelectedItem = function(){
				scope.tooltipEnable = false;
				if (scope.models.selected) {
					scope.openEditDialog();
				}
			}
			/*----------------------------------------------------------------------------------------------------*/
			/*
			 * Handles copy item
			 */
			scope.copySelectedItem = function(){
				scope.tooltipEnable = false;
				if (scope.models.selected) {
					var clientTrees = scope.models.dropzones;
					var selectedItem = scope.models.selected;
					loop: for (var key in clientTrees){
						if (clientTrees.hasOwnProperty(key)){
							var portfoliosArray = clientTrees[key]['children'];
							if (portfoliosArray) for (var i = 0; i < portfoliosArray.length; i++){
								var portfolio = portfoliosArray[i];
								if (portfolio === selectedItem){
									portfoliosArray.push(scope.copyProposalObject(selectedItem));
									scope.models.selected = null;
									break loop;
								}
								var accountsArray = portfolio['children'];
								if (accountsArray) for (var j = 0; j < accountsArray.length; j++){
									var account = accountsArray[j];
									if (account === selectedItem){
										accountsArray.push(scope.copyProposalObject(selectedItem));
										scope.models.selected = null;
										break loop;
									}
									var assetsArray = account['children'];
									if (assetsArray) for (var k = 0; k < assetsArray.length; k++){
										var asset = assetsArray[k];
										if (asset === selectedItem){
											assetsArray.push(scope.copyProposalObject(selectedItem));
											scope.models.selected = null;
											break loop;
										}
									}
								}
							}
						}
					}
				}
			}
			/*----------------------------------------------------------------------------------------------------*/
			//Copy the object
			scope.copyProposalObject = function(item){
				var newItem = jQuery.extend(true, {}, item);
				scope.ammendCopiedItem(newItem);
				return newItem;
			}
			/*----------------------------------------------------------------------------------------------------*/
			//Ammend the item with new keys
			scope.ammendCopiedItem = function(item){
				scope.proposalObjectCounter++;
				item.$$hashKey = item.$$hashKey + scope.proposalObjectCounter;
				item.id = item.id + scope.proposalObjectCounter;
				for (var number in item.children){
					var instanceItem = item.children[number];
					if (instanceItem){
						scope.ammendCopiedItem(instanceItem);
					}
				}
			};
			/*----------------------------------------------------------------------------------------------------*/
			//Asset classes
			getProposalAseetClassData().then(
					function(result) {
						scope.assetClasses = result;
				    }, 
				    function(error) {
				    	
				    });
			/*----------------------------------------------------------------------------------------------------*/
			//Utilities
			scope.deleteItem = function(item){
				var clientTrees = scope.models.dropzones;
				loop: for (var key in clientTrees){
					if (clientTrees.hasOwnProperty(key)){
						var portfoliosArray = clientTrees[key]['children'];
						if (portfoliosArray) for (var i = 0; i < portfoliosArray.length; i++){
							var portfolio = portfoliosArray[i];
							if (portfolio === item){
								portfoliosArray.splice(i, 1);
								scope.models.selected = null;
								break loop;
							}
							var accountsArray = portfolio['children'];
							if (accountsArray) for (var j = 0; j < accountsArray.length; j++){
								var account = accountsArray[j];
								if (account === item){
									accountsArray.splice(j, 1);
									scope.models.selected = null;
									break loop;
								}
								var assetsArray = account['children'];
								if (assetsArray) for (var k = 0; k < assetsArray.length; k++){
									var asset = assetsArray[k];
									if (asset === item){
										assetsArray.splice(k, 1);
										scope.models.selected = null;
										break loop;
									}
								}
							}
						}
					}
				}
			}
			/*----------------------------------------------------------------------------------------------------*/
			/*
			 * Delete Dialog open
			 */
			scope.openDeleteDialog = function (executeFunction, assetName) {
				var modalInstance = $uibModal.open({
					animation : true,
					templateUrl : '/views/proposal-engine/templates/recheck-delete-dialog.html',
					controller: 'recheckDeleteCtrl',
					backdrop : 'static',
					resolve : {
						assetName : function(){
							return assetName;
						}
					},
					scope : scope
				});

				modalInstance.result.then(
						function() {
							//delete
							executeFunction();
							scope.saveProposal(scope.tempProposalName);
						}, function() {
							//dismissed
						});
			};
			/*----------------------------------------------------------------------------------------------------*/
			/*
			 * Edit Dialog open
			 */
			scope.openEditDialog = function () {
				var modalInstance = $uibModal.open({
					animation : true,
					templateUrl : '/views/proposal-engine/templates/edit-dialog.html',
					controller: 'editDialogCtrl',
					backdrop : 'static',
					scope : scope,
					size : 'lg'
				});

				modalInstance.result.then(
						function() {
							//close
							scope.saveProposal(scope.tempProposalName);
						}, function() {
							//dismissed
						});
			};
			/*----------------------------------------------------------------------------------------------------*/
			//save proposal
			scope.saveProposal = function(proposalName){
				saveProposal(scope.models, proposalName);
			}
			/*----------------------------------------------------------------------------------------------------*/
			//save proposal
			scope.loadProposal = function(proposalId){
				loadProposal(proposalId).then(function(result) {
					scope.models = result;
			    }, function(error) {
			    	/*----------------------------------------------------------------------------------------------------*/
					//Empty models for drag and drop
					scope.models = {
						selected : null,
						templates : [ {
							type : "asset",
							name : scope.asset,
							isCollapsed : false,
							id : 2,
							value : 100000,
							allowedChildren : '',
							children : []
						}, {
							type : "account",
							color : '#F0AD4E',
							name : scope.account,
							isCollapsed : false,
							id : 2,
							value : 100000,
							allowedChildren : "'asset'",
							children : []
						}, {
							type : "realEstate",
							color : '#5CB85C',
							name : scope.realEstate,
							isCollapsed : false,
							id : 2,
							value : 100000,
							allowedChildren : '',
							children : []
						}, {
							type : "portfolio",
							color : '#3174AE',
							name : scope.portfolio,
							isCollapsed : false,
							id : 2,
							value : 100000,
							allowedChildren : "'account', 'realEstate'",
							children : []
						} ],
						dropzones : {
							"Current" : {
								type : "current",
								name : scope.currentAllocationHeader,
								id : 1,
								value : 100000,
								allowedChildren : "'portfolio'",
								children : []
							},
							"Proposed" : {
								type : "proposed",
								name : scope.proposedAllocationHeader,
								id : 1,
								value : 100000,
								allowedChildren : "'portfolio'",
								children : []
							}
						}
					};
			    });
			}
			/*----------------------------------------------------------------------------------------------------*/
			//Load proposal after page refresh
			scope.loadProposal();
			/*----------------------------------------------------------------------------------------------------*/
			//Hebrew direction
			try {
				if (dir)
					scope.dir = dir;
			} catch (e) {
				scope.dir = '';
			}
			/*----------------------------------------------------------------------------------------------------*/
			//Hebrew direction
			scope.mouseMoved = function($event){
				scope.tooltipEnable = true;
			}
		}
	}
} ]);