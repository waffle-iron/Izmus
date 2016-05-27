var basicPortfolioDirective = angular.module('basicPortfolioDirective', ['drag', 'treeGrid','instrumentDataService', 'clientDataService','angularSpinner','chart.js']);
basicPortfolioDirective.directive('investitBasicportfolio', ['getPortfolioData','getInstrumentData', function(getPortfolioData, getInstrumentData){
	return {
		restrict : 'E',
		scope: {
			header: '@',
			expand: '@',
			collapse: '@',
			instrumentInformation: '@',
			instrument: '@',
			value: '@',
			quantity: '@',
			asset: '@'
			},
		templateUrl : '/views/dashboard/templates/basic-portfolio.html',
		link: function(scope, elem, attr) {
			/*----------------------------------------------------------------------------------------------------*/
			scope.getClickPosition = function(element){
				var doc = document.documentElement;
				var docLeft = (window.pageXOffset || doc.scrollLeft)
						- (doc.clientLeft || 0), docTop = (window.pageYOffset || doc.scrollTop)
						- (doc.clientTop || 0), elementWidth = element.scrollWidth, elementHeight = element.scrollHeight;
				var docWidth = doc.clientWidth
						+ docLeft, docHeight = doc.clientHeight
						+ docTop, totalWidth = elementWidth
						+ event.pageX, totalHeight = elementHeight
						+ event.pageY, left = Math.max(
						event.pageX - docLeft, 0), top = Math
						.max(event.pageY - docTop, 0);
				if (totalWidth > docWidth) {
					left = left
							- (totalWidth - docWidth);
				}
				if (totalHeight > docHeight) {
					top = top
							- (totalHeight - docHeight);
				}
				if (dir == 'rtl'){
					element.style.textAlign = 'right';
					return {x: left - 160, y: top};
				}
				else {
					return {x: left, y: top};
				}
			}
			/*----------------------------------------------------------------------------------------------------*/
			scope.extractPieData = function (result){
				scope.pielabels = [];
				scope.piedata = [];
				for (i = 0;i < result.length; i++){
					scope.pielabels.push(result[i].assetClass);
					scope.piedata.push(result[i].value);
				}
			}
			/*----------------------------------------------------------------------------------------------------*/
			scope.instrumentViewClose = function(){
				var instrumentView = (document.getElementById("instrumentView"));
				instrumentView.className = "dropdown" ;
			}
			/*----------------------------------------------------------------------------------------------------*/
			scope.userTreeClick = function(branch, $event) {
				var dropdown = (document.getElementById("dropdownMenu"));
				var instrumentView = (document.getElementById("instrumentView"));
				instrumentView.className = "dropdown" ;
				if (branch.instrument == '') {
					dropdown.className = "dropdown position-fixed" ;
					return;
				}
				dropdown.className = dropdown.className.indexOf('open') > -1 ? "dropdown position-fixed" : "dropdown position-fixed open";
				var position = scope.getClickPosition(dropdown);
				dropdown.style.left = position.x + 'px';
				dropdown.style.top = position.y+ 'px';
				scope.portfolioCtrl.branch = branch;
			};
			/*----------------------------------------------------------------------------------------------------*/
			scope.portfolioCtrl = {};
			scope.portfolioCtrl.instrumentInformation = function(instrument, $event){
				scope.showInstrumentSpinner = true;
				var dropdown = (document.getElementById("dropdownMenu"));
				dropdown.className = "dropdown position-fixed" ;
				var instrumentView = (document.getElementById("instrumentView"));
				instrumentView.style.opacity = 0.5;
				instrumentView.className = "dropdown open" ;
				var position = scope.getClickPosition(instrumentView);
				instrumentView.style.left = position.x + 'px';
				instrumentView.style.top = position.y+ 'px';
				getInstrumentData(instrument).then(function(result) {
					 scope.linelabels = result.lineLabels;
					  scope.lineseries = result.lineSeries;
					  scope.linedata = result.lineData;
					instrumentView.style.opacity = 1;
			      	scope.showInstrumentSpinner = false;
			    }, function(error) {
			    	instrumentView.style.opacity = 1;
			      	scope.showInstrumentSpinner = false;
			      console.error(error)
			    });
			}
			/*----------------------------------------------------------------------------------------------------*/
			elem[0].style.opacity = 0.5;
			/*----------------------------------------------------------------------------------------------------*/
			scope.showSpinner = true;
			/*----------------------------------------------------------------------------------------------------*/
			scope.expanding_def = {
					field : "assetClass",
					displayName : scope.asset,
					sortable : true,
					filterable : true
				};
			/*----------------------------------------------------------------------------------------------------*/
			scope.col_defs = [ {
					field : "instrument",
					displayName : scope.instrument,
					sortable : true,
					filterable : true
				}, {
					field : "quantity",
					displayName : scope.quantity,
					sortable : true,
					filterable : true
				}, {
					field : "value",
					displayName : scope.value,
					sortable : true,
					filterable : true
				} ];
			/*----------------------------------------------------------------------------------------------------*/
			scope.portfolioData = [{
				assetClass: ""
			},{
				assetClass: ""
			},{
				assetClass: ""
			},{
				assetClass: ""
			}];
			/*----------------------------------------------------------------------------------------------------*/
			try {
				if (dir)
					scope.dir = dir;
			} catch (e) {
				scope.dir = '';
			}
			/*----------------------------------------------------------------------------------------------------*/
			scope.pieoptions = {
					animateScale : true,
					tooltipTemplate: "<%if (label){%><%=label%>: <%}%><%= value %>%",
					percentageInnerCutout : 70,
					responsive: true
			};
			/*----------------------------------------------------------------------------------------------------*/
			scope.piecolors = ['#3E648D', '#3A87AD', '#00BCD4', '#009688', '#3F51B5', '#673AB7'];
			/*----------------------------------------------------------------------------------------------------*/
			getPortfolioData().then(function(result) {
				scope.extractPieData(result);
				scope.portfolioData = result;
		      	elem[0].style.opacity = 1;
		      	scope.showSpinner = false;
		    }, function(error) {
		    	elem[0].style.opacity = 1;
		      	scope.showSpinner = false;
		      console.error(error)
		    });
		}
	}
}]);
/*----------------------------------------------------------------------------------------------------*/
/**
 * treegrid
 */
(function () {
	  angular
	    .module('template/treeGrid/treeGrid.html', [])
	    .run([
	      '$templateCache',
	      function ($templateCache) {
	        $templateCache.put('template/treeGrid/treeGrid.html',
	          "<div class=\"table-responsive\">\n" +
	          " <table class=\"table tree-grid\">\n" +
	          "   <thead>\n" +
	          "     <tr>\n" +
	          "       <th><a ng-if=\"expandingProperty.sortable\" onMouseOver='' style=\"cursor: pointer;\" ng-click=\"sortBy(expandingProperty)\">{{expandingProperty.displayName || expandingProperty.field || expandingProperty}}</a><span ng-if=\"!expandingProperty.sortable\">{{expandingProperty.displayName || expandingProperty.field || expandingProperty}}</span><i ng-if=\"expandingProperty.sorted\" class=\"{{expandingProperty.sortingIcon}} pull-right\"></i></th>\n" +
	          "       <th ng-repeat=\"col in colDefinitions\"><a ng-if=\"col.sortable\" onMouseOver='' style=\"cursor: pointer;\" ng-click=\"sortBy(col)\">{{col.displayName || col.field}}</a><span ng-if=\"!col.sortable\">{{col.displayName || col.field}}</span><i ng-if=\"col.sorted\" class=\"{{col.sortingIcon}} pull-right\"></i></th>\n" +
	          "     </tr>\n" +
	          "   </thead>\n" +
	          "   <tbody>\n" +
	          "     <tr ng-repeat=\"row in tree_rows | searchFor:$parent.filterString:expandingProperty:colDefinitions track by row.branch.uid\"\n" +
	          "       ng-class=\"'level-' + {{ row.level }} + (row.branch.selected ? ' active':'')\" class=\"tree-grid-row\">\n" +
	          "       <td><a ng-click=\"user_clicks_branch(row.branch)\"><i ng-class=\"row.tree_icon\"\n" +
	          "              ng-click=\"row.branch.expanded = !row.branch.expanded\"\n" +
	          "              class=\"indented tree-icon\"></i>\n" +
	          "           </a><span class=\"indented tree-label\" ng-click=\"on_user_click(row.branch, $event)\">\n" +
	          "             {{row.branch[expandingProperty.field] || row.branch[expandingProperty]}}</span>\n" +
	          "       </td>\n" +
	          "       <td ng-click=\"on_user_click(row.branch, $event)\" ng-repeat=\"col in colDefinitions\" onMouseOver='' style=\"cursor: pointer;\">\n" +
	          "         <div ng-if=\"col.cellTemplate\" compile=\"col.cellTemplate\" cell-template-scope=\"col.cellTemplateScope\"></div>\n" +
	          "         <div ng-if=\"!col.cellTemplate\">{{row.branch[col.field]}}</div>\n" +
	          "       </td>\n" +
	          "     </tr>\n" +
	          "   </tbody>\n" +
	          " </table>\n" +
	          "</div>\n" +
	          "");
	        $templateCache.put('template/treeGrid/treeGridHe.html',
	                "<div class=\"table-responsive\" dir=\"rtl\">\n" +
	                " <table class=\"table tree-grid\">\n" +
	                "   <thead>\n" +
	                "     <tr>\n" +
	                "       <th style='text-align: right;'><a ng-if=\"expandingProperty.sortable\" onMouseOver='' style=\"cursor: pointer;\" ng-click=\"sortBy(expandingProperty)\">{{expandingProperty.displayName || expandingProperty.field || expandingProperty}}</a><span ng-if=\"!expandingProperty.sortable\">{{expandingProperty.displayName || expandingProperty.field || expandingProperty}}</span><i ng-if=\"expandingProperty.sorted\" class=\"{{expandingProperty.sortingIcon}} pull-right\"></i></th>\n" +
	                "       <th style='text-align: right;' ng-repeat=\"col in colDefinitions\"><a ng-if=\"col.sortable\" onMouseOver='' style=\"cursor: pointer;\" ng-click=\"sortBy(col)\">{{col.displayName || col.field}}</a><span ng-if=\"!col.sortable\">{{col.displayName || col.field}}</span><i ng-if=\"col.sorted\" class=\"{{col.sortingIcon}} pull-left\"></i></th>\n" +
	                "     </tr>\n" +
	                "   </thead>\n" +
	                "   <tbody>\n" +
	                "     <tr ng-repeat=\"row in tree_rows | searchFor:$parent.filterString:expandingProperty:colDefinitions track by row.branch.uid\"\n" +
	                "       ng-class=\"'level-' + {{ row.level }} + (row.branch.selected ? ' active':'')\" class=\"tree-grid-row\">\n" +
	                "       <td><a ng-click=\"user_clicks_branch(row.branch)\"><i ng-class=\"row.tree_icon\"\n" +
	                "              ng-click=\"row.branch.expanded = !row.branch.expanded\"\n" +
	                "              class=\"indentedRight tree-icon\"></i>\n" +
	                "           </a><span class=\"indentedRight tree-label\" ng-click=\"on_user_click(row.branch, $event)\">\n" +
	                "             {{row.branch[expandingProperty.field] || row.branch[expandingProperty]}}</span>\n" +
	                "       </td>\n" +
	                "       <td ng-click=\"on_user_click(row.branch, $event)\" ng-repeat=\"col in colDefinitions\" onMouseOver='' style=\"cursor: pointer;\">\n" +
	                "         <div ng-if=\"col.cellTemplate\" compile=\"col.cellTemplate\" cell-template-scope=\"col.cellTemplateScope\"></div>\n" +
	                "         <div ng-if=\"!col.cellTemplate\">{{row.branch[col.field]}}</div>\n" +
	                "       </td>\n" +
	                "     </tr>\n" +
	                "   </tbody>\n" +
	                " </table>\n" +
	                "</div>\n" +
	                "");
	      }]);

	  angular
	    .module('treeGrid', [
	      'template/treeGrid/treeGrid.html'
	    ])

	    .directive('compile', [
	      '$compile',
	      function ($compile) {
	        return {
	          restrict: 'A',
	          link    : function (scope, element, attrs) {
	            scope.cellTemplateScope = scope.$eval(attrs.cellTemplateScope);

	            // Watch for changes to expression.
	            scope.$watch(attrs.compile, function (new_val) {
	              /*
					 * Compile creates a linking function that can be used with
					 * any scope.
					 */
	              var link = $compile(new_val);

	              /*
					 * Executing the linking function creates a new element.
					 */
	              var new_elem = link(scope);

	              // Which we can then append to our DOM element.
	              element.append(new_elem);
	            });
	          }
	        };
	      }])

	    .directive('treeGrid', [
	      '$timeout',
	      'treegridTemplate',
	      function ($timeout,
	                treegridTemplate) {

	        return {
	          restrict   : 'E',
	          templateUrl: function (tElement, tAttrs) {
	            return tAttrs.templateUrl || treegridTemplate.getPath();
	          },
	          replace    : true,
	          scope      : {
	            treeData        : '=',
	            colDefs         : '=',
	            expandOn        : '=',
	            onSelect        : '&',
	            onClick         : '&',
	            initialSelection: '@',
	            treeControl     : '='
	          },
	          link       : function (scope, element, attrs) {
	            var error, expandingProperty, expand_all_parents, expand_level, for_all_ancestors, for_each_branch, get_parent, n, on_treeData_change, select_branch, selected_branch, tree;

	            error = function (s) {
	              console.log('ERROR:' + s);
	              debugger;
	              return void 0;
	            };

	            attrs.iconExpand = attrs.iconExpand ? attrs.iconExpand : 'icon-plus  glyphicon glyphicon-plus  fa fa-plus';
	            attrs.iconCollapse = attrs.iconCollapse ? attrs.iconCollapse : 'icon-minus glyphicon glyphicon-minus fa fa-minus';
	            attrs.iconLeaf = attrs.iconLeaf ? attrs.iconLeaf : 'icon-file  glyphicon glyphicon-file  fa fa-file';
	            attrs.sortedAsc = attrs.sortedAsc ? attrs.sortedAsc : 'icon-file  glyphicon glyphicon-chevron-up  fa angle-up';
	            attrs.sortedDesc = attrs.sortedDesc ? attrs.sortedDesc : 'icon-file  glyphicon glyphicon-chevron-down  fa angle-down';
	            attrs.expandLevel = attrs.expandLevel ? attrs.expandLevel : '3';
	            expand_level = parseInt(attrs.expandLevel, 10);

	            if (!scope.treeData) {
	              alert('No data was defined for the tree, please define treeData!');
	              return;
	            }

	            var getExpandingProperty = function getExpandingProperty() {
	              if (attrs.expandOn) {
	                expandingProperty = scope.expandOn;
	                scope.expandingProperty = scope.expandOn;
	              } else {
	                if (scope.treeData.length) {
	                  var _firstRow = scope.treeData[0],
	                    _keys = Object.keys(_firstRow);
	                  for (var i = 0, len = _keys.length; i < len; i++) {
	                    if (typeof (_firstRow[_keys[i]]) === 'string') {
	                      expandingProperty = _keys[i];
	                      break;
	                    }
	                  }
	                  if (!expandingProperty) expandingProperty = _keys[0];
	                  scope.expandingProperty = expandingProperty;
	                }
	              }
	            };

	            getExpandingProperty();

	            if (!attrs.colDefs) {
	              if (scope.treeData.length) {
	                var _col_defs = [],
	                  _firstRow = scope.treeData[0],
	                  _unwantedColumn = ['children', 'level', 'expanded', expandingProperty];
	                for (var idx in _firstRow) {
	                  if (_unwantedColumn.indexOf(idx) === -1) {
	                    _col_defs.push({
	                      field: idx
	                    });
	                  }
	                }
	                scope.colDefinitions = _col_defs;
	              }
	            } else {
	              scope.colDefinitions = scope.colDefs;
	            }

	            for_each_branch = function (f) {
	              var do_f, root_branch, _i, _len, _ref, _results;
	              do_f = function (branch, level) {
	                var child, _i, _len, _ref, _results;
	                f(branch, level);
	                if (branch.children != null) {
	                  _ref = branch.children;
	                  _results = [];
	                  for (_i = 0, _len = _ref.length; _i < _len; _i++) {
	                    child = _ref[_i];
	                    _results.push(do_f(child, level + 1));
	                  }
	                  return _results;
	                }
	              };
	              _ref = scope.treeData;
	              _results = [];
	              for (_i = 0, _len = _ref.length; _i < _len; _i++) {
	                root_branch = _ref[_i];
	                _results.push(do_f(root_branch, 1));
	              }
	              return _results;
	            };
	            selected_branch = null;
	            select_branch = function (branch) {
	              if (!branch) {
	                if (selected_branch != null) {
	                  selected_branch.selected = false;
	                }
	                selected_branch = null;
	                return;
	              }
	              if (branch !== selected_branch) {
	                if (selected_branch != null) {
	                  selected_branch.selected = false;
	                }
	                branch.selected = true;
	                selected_branch = branch;
	                expand_all_parents(branch);
	                if (branch.onSelect != null) {
	                  return $timeout(function () {
	                    return branch.onSelect(branch);
	                  });
	                } else {
	                  if (scope.onSelect != null) {
	                    return $timeout(function () {
	                      return scope.onSelect({
	                        branch: branch
	                      });
	                    });
	                  }
	                }
	              }
	            };
	            scope.on_user_click = function (branch, $event) {
	              if (scope.onClick) {
	                scope.onClick({
	                  branch: branch,
	                  $event: $event
	                });
	              }
	            };
	            scope.user_clicks_branch = function (branch) {
	              if (branch !== selected_branch) {
	                return select_branch(branch);
	              }
	            };
	            
	            /* sorting methods */
	            scope.sortBy = function (col) {
	            	if (col.sortDirection === "asc") {
	                 sort_recursive(scope.treeData, col, true);
	            	   col.sortDirection = "desc";
	       	           col.sortingIcon = attrs.sortedDesc;
	            	} else {
	            	   sort_recursive(scope.treeData, col, false);
	             	   col.sortDirection = "asc";
	        	       col.sortingIcon = attrs.sortedAsc;
	            	}
	          	    col.sorted = true;
	                resetSorting(col);
	              };

	            var sort_recursive = function(elements, col, descending) {
	              elements.sort(sort_by(col, descending));
	              for (var i = 0; i < elements.length; i++) {
	                  sort_recursive(elements[i].children, col, descending);
	              }
	            };

	            var sort_by = function(col, descending) {

	              var direction = !descending ? 1 : -1;

	              if (col.sortingType === "custom" && typeof col.sortingFunc === "function") {
	                return function (a, b) {
	                  return col.sortingFunc(a, b) * direction;
	                };
	              }

	              var key = function(x) {
	                return (x[col.field] === null ? "" : x[col.field].toLowerCase());
	              };

	              switch(col.sortingType) {
	                case "number":
	                  key = function(x) { return parseFloat(x[col.field]); };
	                  break;
	                case "date":
	                  key = function (x) { return new Date(x[col.field]); };
	                  break;
	              }

	              return function (a, b) {
	                return a = key(a), b = key(b), direction * ((a > b) - (b > a));
	              };
	            }

	            var resetSorting = function(sortedCol) {
	            	var arraySize = scope.colDefinitions.length;
	            	for (var i= 0;i<arraySize;i++) {
	            		var col = scope.colDefinitions[i];
	            		if (col.field != sortedCol.field) {
	            			col.sorted = false;
	                		col.sortDirection = "none";	
	            		}
	            	}
	            }
	              
	            /* end of sorting methods */
	            
	            get_parent = function (child) {
	              var parent;
	              parent = void 0;
	              if (child.parent_uid) {
	                for_each_branch(function (b) {
	                  if (b.uid === child.parent_uid) {
	                    return parent = b;
	                  }
	                });
	              }
	              return parent;
	            };
	            for_all_ancestors = function (child, fn) {
	              var parent;
	              parent = get_parent(child);
	              if (parent != null) {
	                fn(parent);
	                return for_all_ancestors(parent, fn);
	              }
	            };
	            expand_all_parents = function (child) {
	              return for_all_ancestors(child, function (b) {
	                return b.expanded = true;
	              });
	            };

	            scope.tree_rows = [];

	            on_treeData_change = function () {
	              getExpandingProperty();

	              var add_branch_to_list, root_branch, _i, _len, _ref, _results;
	              for_each_branch(function (b, level) {
	                if (!b.uid) {
	                  return b.uid = "" + Math.random();
	                }
	              });
	              for_each_branch(function (b) {
	                var child, _i, _len, _ref, _results;
	                if (angular.isArray(b.children)) {
	                  _ref = b.children;
	                  _results = [];
	                  for (_i = 0, _len = _ref.length; _i < _len; _i++) {
	                    child = _ref[_i];
	                    _results.push(child.parent_uid = b.uid);
	                  }
	                  return _results;
	                }
	              });
	              scope.tree_rows = [];
	              for_each_branch(function (branch) {
	                var child, f;
	                if (branch.children) {
	                  if (branch.children.length > 0) {
	                    f = function (e) {
	                      if (typeof e === 'string') {
	                        return {
	                          label   : e,
	                          children: []
	                        };
	                      } else {
	                        return e;
	                      }
	                    };
	                    return branch.children = (function () {
	                      var _i, _len, _ref, _results;
	                      _ref = branch.children;
	                      _results = [];
	                      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
	                        child = _ref[_i];
	                        _results.push(f(child));
	                      }
	                      return _results;
	                    })();
	                  }
	                } else {
	                  return branch.children = [];
	                }
	              });
	              add_branch_to_list = function (level, branch, visible) {
	                var child, child_visible, tree_icon, _i, _len, _ref, _results;
	                if (branch.expanded == null) {
	                  branch.expanded = false;
	                }
	                if (!branch.children || branch.children.length === 0) {
	                  tree_icon = attrs.iconLeaf;
	                } else {
	                  if (branch.expanded) {
	                    tree_icon = attrs.iconCollapse;
	                  } else {
	                    tree_icon = attrs.iconExpand;
	                  }
	                }
	                branch.level = level;
	                scope.tree_rows.push({
	                  level    : level,
	                  branch   : branch,
	                  label    : branch[expandingProperty],
	                  tree_icon: tree_icon,
	                  visible  : visible
	                });
	                if (branch.children != null) {
	                  _ref = branch.children;
	                  _results = [];
	                  for (_i = 0, _len = _ref.length; _i < _len; _i++) {
	                    child = _ref[_i];
	                    child_visible = visible && branch.expanded;
	                    _results.push(add_branch_to_list(level + 1, child, child_visible));
	                  }
	                  return _results;
	                }
	              };
	              _ref = scope.treeData;
	              _results = [];
	              for (_i = 0, _len = _ref.length; _i < _len; _i++) {
	                root_branch = _ref[_i];
	                _results.push(add_branch_to_list(1, root_branch, true));
	              }
	              return _results;
	            };

	            scope.$watch('treeData', on_treeData_change, true);

	            if (attrs.initialSelection != null) {
	              for_each_branch(function (b) {
	                if (b.label === attrs.initialSelection) {
	                  return $timeout(function () {
	                    return select_branch(b);
	                  });
	                }
	              });
	            }
	            n = scope.treeData.length;
	            for_each_branch(function (b, level) {
	              b.level = level;
	              return b.expanded = b.level < expand_level;
	            });
	            if (scope.treeControl != null) {
	              if (angular.isObject(scope.treeControl)) {
	                tree = scope.treeControl;
	                tree.expand_all = function () {
	                  return for_each_branch(function (b, level) {
	                    return b.expanded = true;
	                  });
	                };
	                tree.collapse_all = function () {
	                  return for_each_branch(function (b, level) {
	                    return b.expanded = false;
	                  });
	                };
	                tree.get_first_branch = function () {
	                  n = scope.treeData.length;
	                  if (n > 0) {
	                    return scope.treeData[0];
	                  }
	                };
	                tree.select_first_branch = function () {
	                  var b;
	                  b = tree.get_first_branch();
	                  return tree.select_branch(b);
	                };
	                tree.get_selected_branch = function () {
	                  return selected_branch;
	                };
	                tree.get_parent_branch = function (b) {
	                  return get_parent(b);
	                };
	                tree.select_branch = function (b) {
	                  select_branch(b);
	                  return b;
	                };
	                tree.get_children = function (b) {
	                  return b.children;
	                };
	                tree.select_parent_branch = function (b) {
	                  var p;
	                  if (b == null) {
	                    b = tree.get_selected_branch();
	                  }
	                  if (b != null) {
	                    p = tree.get_parent_branch(b);
	                    if (p != null) {
	                      tree.select_branch(p);
	                      return p;
	                    }
	                  }
	                };
	                tree.add_branch = function (parent, new_branch) {
	                  if (parent != null) {
	                    parent.children.push(new_branch);
	                    parent.expanded = true;
	                  } else {
	                    scope.treeData.push(new_branch);
	                  }
	                  return new_branch;
	                };
	                tree.add_root_branch = function (new_branch) {
	                  tree.add_branch(null, new_branch);
	                  return new_branch;
	                };
	                tree.expand_branch = function (b) {
	                  if (b == null) {
	                    b = tree.get_selected_branch();
	                  }
	                  if (b != null) {
	                    b.expanded = true;
	                    return b;
	                  }
	                };
	                tree.collapse_branch = function (b) {
	                  if (b == null) {
	                    b = selected_branch;
	                  }
	                  if (b != null) {
	                    b.expanded = false;
	                    return b;
	                  }
	                };
	                tree.get_siblings = function (b) {
	                  var p, siblings;
	                  if (b == null) {
	                    b = selected_branch;
	                  }
	                  if (b != null) {
	                    p = tree.get_parent_branch(b);
	                    if (p) {
	                      siblings = p.children;
	                    } else {
	                      siblings = scope.treeData;
	                    }
	                    return siblings;
	                  }
	                };
	                tree.get_next_sibling = function (b) {
	                  var i, siblings;
	                  if (b == null) {
	                    b = selected_branch;
	                  }
	                  if (b != null) {
	                    siblings = tree.get_siblings(b);
	                    n = siblings.length;
	                    i = siblings.indexOf(b);
	                    if (i < n) {
	                      return siblings[i + 1];
	                    }
	                  }
	                };
	                tree.get_prev_sibling = function (b) {
	                  var i, siblings;
	                  if (b == null) {
	                    b = selected_branch;
	                  }
	                  siblings = tree.get_siblings(b);
	                  n = siblings.length;
	                  i = siblings.indexOf(b);
	                  if (i > 0) {
	                    return siblings[i - 1];
	                  }
	                };
	                tree.select_next_sibling = function (b) {
	                  var next;
	                  if (b == null) {
	                    b = selected_branch;
	                  }
	                  if (b != null) {
	                    next = tree.get_next_sibling(b);
	                    if (next != null) {
	                      return tree.select_branch(next);
	                    }
	                  }
	                };
	                tree.select_prev_sibling = function (b) {
	                  var prev;
	                  if (b == null) {
	                    b = selected_branch;
	                  }
	                  if (b != null) {
	                    prev = tree.get_prev_sibling(b);
	                    if (prev != null) {
	                      return tree.select_branch(prev);
	                    }
	                  }
	                };
	                tree.get_first_child = function (b) {
	                  var _ref;
	                  if (b == null) {
	                    b = selected_branch;
	                  }
	                  if (b != null) {
	                    if (((_ref = b.children) != null ? _ref.length : void 0) > 0) {
	                      return b.children[0];
	                    }
	                  }
	                };
	                tree.get_closest_ancestor_next_sibling = function (b) {
	                  var next, parent;
	                  next = tree.get_next_sibling(b);
	                  if (next != null) {
	                    return next;
	                  } else {
	                    parent = tree.get_parent_branch(b);
	                    return tree.get_closest_ancestor_next_sibling(parent);
	                  }
	                };
	                tree.get_next_branch = function (b) {
	                  var next;
	                  if (b == null) {
	                    b = selected_branch;
	                  }
	                  if (b != null) {
	                    next = tree.get_first_child(b);
	                    if (next != null) {
	                      return next;
	                    } else {
	                      next = tree.get_closest_ancestor_next_sibling(b);
	                      return next;
	                    }
	                  }
	                };
	                tree.select_next_branch = function (b) {
	                  var next;
	                  if (b == null) {
	                    b = selected_branch;
	                  }
	                  if (b != null) {
	                    next = tree.get_next_branch(b);
	                    if (next != null) {
	                      tree.select_branch(next);
	                      return next;
	                    }
	                  }
	                };
	                tree.last_descendant = function (b) {
	                  var last_child;
	                  if (b == null) {
	                    debugger;
	                  }
	                  n = b.children.length;
	                  if (n === 0) {
	                    return b;
	                  } else {
	                    last_child = b.children[n - 1];
	                    return tree.last_descendant(last_child);
	                  }
	                };
	                tree.get_prev_branch = function (b) {
	                  var parent, prev_sibling;
	                  if (b == null) {
	                    b = selected_branch;
	                  }
	                  if (b != null) {
	                    prev_sibling = tree.get_prev_sibling(b);
	                    if (prev_sibling != null) {
	                      return tree.last_descendant(prev_sibling);
	                    } else {
	                      parent = tree.get_parent_branch(b);
	                      return parent;
	                    }
	                  }
	                };
	                return tree.select_prev_branch = function (b) {
	                  var prev;
	                  if (b == null) {
	                    b = selected_branch;
	                  }
	                  if (b != null) {
	                    prev = tree.get_prev_branch(b);
	                    if (prev != null) {
	                      tree.select_branch(prev);
	                      return prev;
	                    }
	                  }
	                };
	              }
	            }
	          }
	        };
	      }
	    ])

	    .provider('treegridTemplate', function () {
	      this.$get = function () {
	        return {
	          getPath: function () {
	        	var templatePath;
	          	try {
	      			if (dir == 'rtl') {
	      				templatePath = 'template/treeGrid/treeGridHe.html';
	      			} else {
	      				templatePath = 'template/treeGrid/treeGrid.html';
	      			}
	      		} catch (e) {
	      			templatePath = 'template/treeGrid/treeGrid.html';
	      		}
	            return templatePath;
	          }
	        };
	      };
	    })

	  .filter('searchFor', function() {
			return function(arr, filterString, expandingProperty, colDefinitions) {
				var filtered = [];
				// only apply filter for strings 3 characters long or more
			   if (!filterString || filterString.length < 3) {		     
				   for (var i = 0; i < arr.length; i++) {
			              var item = arr[i];
			              if (item.visible) {
			                 filtered.push(item);
			           }
			      }
			   } else {
				  var ancestorStack = [];
				  var currentLevel = 0;
	              for (var i = 0; i < arr.length; i++) {
	                 var item = arr[i];
	                 while (currentLevel >= item.level) {
	                	 throwAway = ancestorStack.pop();
	                	 currentLevel--;
	                 }
	                 ancestorStack.push(item);
	                 currentLevel = item.level;
	                 if (include(item, filterString, expandingProperty, colDefinitions)) {
	                	for(var ancestorIndex = 0; ancestorIndex < ancestorStack.length; ancestorIndex++) {
	                		ancestor = ancestorStack[ancestorIndex];
	                		if(ancestor.visible){
	                			filtered.push(ancestor);
	                		}
	                	} 
	                    ancestorStack = [];
	                 }
	              }
			   }
	           return filtered;
			};
			
			function include(item, filterString, expandingProperty, colDefinitions){
				var includeItem = false;
				var filterApplied = false;
				// first check the expandingProperty
				if (expandingProperty.filterable) {
					filterApplied = true;
				    if(checkItem(item, filterString, expandingProperty)) {
				    	includeItem = true;
				    }
				}
				// then check each of the other columns
				var arraySize = colDefinitions.length;
	        	for (var i= 0;i<arraySize;i++) {
	        		var col = colDefinitions[i];
	        		if (col.filterable) {
	    				filterApplied = true;
	    			    if(checkItem(item, filterString, col)) {
	    			    	includeItem = true;
	    			    }
	    			}        		
	        	}
				if (filterApplied) {
				    return includeItem;
				} else {
					return true;
				}			
			}
			
			function checkItem(item, filterString, col) {
				if (col.sortingType === "number") {
					if (item.branch[col.field] != null
							  && parseFloat(item.branch[col.field]) === parseFloat(filterString)) {
						return true;
				    }
				} else {
				   if (item.branch[col.field] != null
					  && item.branch[col.field].toLowerCase().indexOf(filterString.toLowerCase()) !== -1) {
					   return true;
				   }
				}
			}
	  });
	}).call(window);
/*----------------------------------------------------------------------------------------------------*/
/*
 * Drag specific for instrument view
 */
angular.module('drag', []).
directive('draggable', ['$document', function($document) {
  return function(scope, element, attr) {
	  var startX = 0, startY = 0, x = 0, y = 0;
	  var instrumentView = (document.getElementById("instrumentView"));
	  instrumentView.addEventListener('touchstart', function(e){
	        var touchobj = e.changedTouches[0] // reference first touch point
												// (ie: first finger)
	        var investmentViewClose = (document.getElementById("instrumentViewClose"));
	        if (touchobj.target == investmentViewClose){
				instrumentView.className = "dropdown" ;
	        }
	        startX = instrumentView.offsetLeft; // get x position of touch point
												// relative to left edge of
												// browser
	        startY = instrumentView.offsetTop;
	        y = touchobj.clientY;
	        x = touchobj.clientX;
	        e.preventDefault()
	    }, false)
	 
	    instrumentView.addEventListener('touchmove', function(e){
	        var touchobj = e.changedTouches[0] // reference first touch point
												// for this event
	        var distx = touchobj.clientX - x;
	        var disty = touchobj.clientY - y;
	        element.css({
	            top: startY + disty + 'px',
	            left:  startX + distx + 'px'
	          });
	        e.preventDefault()
	    }, false)
    
    element.css({
     cursor: 'pointer',
     display: 'block',
    });
    element.on('mousedown', function(event) {
    	var totalOffsetX = 0;
        var totalOffsetY = 0;
    	var currentElement = event.target;
    	 do{
    	        totalOffsetX += currentElement.offsetLeft - currentElement.scrollLeft;
    	        totalOffsetY += currentElement.offsetTop - currentElement.scrollTop;
    	        currentElement = currentElement.offsetParent;
    	    }
    	    while(currentElement != instrumentView)
    	x = event.originalEvent.x - totalOffsetX - event.offsetX;
    	y = event.originalEvent.y - totalOffsetY - event.offsetY;
      // Prevent default dragging of selected content
      event.preventDefault();
      startX = event.screenX - x;
      startY = event.screenY - y;
      $document.on('mousemove', mousemove);
      $document.on('mouseup', mouseup);
    });
    function mousemove(event) {
      y = event.screenY - startY;
      x = event.screenX - startX;
      element.css({
        top: y + 'px',
        left:  x + 'px'
      });
    }

    function mouseup() {
      $document.off('mousemove', mousemove);
      $document.off('mouseup', mouseup);
    }
  };
}]);
/*----------------------------------------------------------------------------------------------------*/
/*
 * Chart.js
 */
(function(){"use strict";var t=this,i=t.Chart,e=function(t){this.canvas=t.canvas,this.ctx=t;var i=function(t,i){return t["offset"+i]?t["offset"+i]:document.defaultView.getComputedStyle(t).getPropertyValue(i)},e=this.width=i(t.canvas,"Width"),n=this.height=i(t.canvas,"Height");t.canvas.width=e,t.canvas.height=n;var e=this.width=t.canvas.width,n=this.height=t.canvas.height;return this.aspectRatio=this.width/this.height,s.retinaScale(this),this};e.defaults={global:{animation:!0,animationSteps:60,animationEasing:"easeOutQuart",showScale:!0,scaleOverride:!1,scaleSteps:null,scaleStepWidth:null,scaleStartValue:null,scaleLineColor:"rgba(0,0,0,.1)",scaleLineWidth:1,scaleShowLabels:!0,scaleLabel:"<%=value%>",scaleIntegersOnly:!0,scaleBeginAtZero:!1,scaleFontFamily:"'Helvetica Neue', 'Helvetica', 'Arial', sans-serif",scaleFontSize:12,scaleFontStyle:"normal",scaleFontColor:"#666",responsive:!1,maintainAspectRatio:!0,showTooltips:!0,customTooltips:!1,tooltipEvents:["mousemove","touchstart","touchmove","mouseout"],tooltipFillColor:"rgba(0,0,0,0.8)",tooltipFontFamily:"'Helvetica Neue', 'Helvetica', 'Arial', sans-serif",tooltipFontSize:14,tooltipFontStyle:"normal",tooltipFontColor:"#fff",tooltipTitleFontFamily:"'Helvetica Neue', 'Helvetica', 'Arial', sans-serif",tooltipTitleFontSize:14,tooltipTitleFontStyle:"bold",tooltipTitleFontColor:"#fff",tooltipYPadding:6,tooltipXPadding:6,tooltipCaretSize:8,tooltipCornerRadius:6,tooltipXOffset:10,tooltipTemplate:"<%if (label){%><%=label%>: <%}%><%= value %>",multiTooltipTemplate:"<%= value %>",multiTooltipKeyBackground:"#fff",onAnimationProgress:function(){},onAnimationComplete:function(){}}},e.types={};var s=e.helpers={},n=s.each=function(t,i,e){var s=Array.prototype.slice.call(arguments,3);if(t)if(t.length===+t.length){var n;for(n=0;n<t.length;n++)i.apply(e,[t[n],n].concat(s))}else for(var o in t)i.apply(e,[t[o],o].concat(s))},o=s.clone=function(t){var i={};return n(t,function(e,s){t.hasOwnProperty(s)&&(i[s]=e)}),i},a=s.extend=function(t){return n(Array.prototype.slice.call(arguments,1),function(i){n(i,function(e,s){i.hasOwnProperty(s)&&(t[s]=e)})}),t},h=s.merge=function(){var t=Array.prototype.slice.call(arguments,0);return t.unshift({}),a.apply(null,t)},l=s.indexOf=function(t,i){if(Array.prototype.indexOf)return t.indexOf(i);for(var e=0;e<t.length;e++)if(t[e]===i)return e;return-1},r=(s.where=function(t,i){var e=[];return s.each(t,function(t){i(t)&&e.push(t)}),e},s.findNextWhere=function(t,i,e){e||(e=-1);for(var s=e+1;s<t.length;s++){var n=t[s];if(i(n))return n}},s.findPreviousWhere=function(t,i,e){e||(e=t.length);for(var s=e-1;s>=0;s--){var n=t[s];if(i(n))return n}},s.inherits=function(t){var i=this,e=t&&t.hasOwnProperty("constructor")?t.constructor:function(){return i.apply(this,arguments)},s=function(){this.constructor=e};return s.prototype=i.prototype,e.prototype=new s,e.extend=r,t&&a(e.prototype,t),e.__super__=i.prototype,e}),c=s.noop=function(){},u=s.uid=function(){var t=0;return function(){return"chart-"+t++}}(),d=s.warn=function(t){window.console&&"function"==typeof window.console.warn&&console.warn(t)},p=s.amd="function"==typeof define&&define.amd,f=s.isNumber=function(t){return!isNaN(parseFloat(t))&&isFinite(t)},g=s.max=function(t){return Math.max.apply(Math,t)},m=s.min=function(t){return Math.min.apply(Math,t)},v=(s.cap=function(t,i,e){if(f(i)){if(t>i)return i}else if(f(e)&&e>t)return e;return t},s.getDecimalPlaces=function(t){return t%1!==0&&f(t)?t.toString().split(".")[1].length:0}),S=s.radians=function(t){return t*(Math.PI/180)},x=(s.getAngleFromPoint=function(t,i){var e=i.x-t.x,s=i.y-t.y,n=Math.sqrt(e*e+s*s),o=2*Math.PI+Math.atan2(s,e);return 0>e&&0>s&&(o+=2*Math.PI),{angle:o,distance:n}},s.aliasPixel=function(t){return t%2===0?0:.5}),y=(s.splineCurve=function(t,i,e,s){var n=Math.sqrt(Math.pow(i.x-t.x,2)+Math.pow(i.y-t.y,2)),o=Math.sqrt(Math.pow(e.x-i.x,2)+Math.pow(e.y-i.y,2)),a=s*n/(n+o),h=s*o/(n+o);return{inner:{x:i.x-a*(e.x-t.x),y:i.y-a*(e.y-t.y)},outer:{x:i.x+h*(e.x-t.x),y:i.y+h*(e.y-t.y)}}},s.calculateOrderOfMagnitude=function(t){return Math.floor(Math.log(t)/Math.LN10)}),C=(s.calculateScaleRange=function(t,i,e,s,n){var o=2,a=Math.floor(i/(1.5*e)),h=o>=a,l=g(t),r=m(t);l===r&&(l+=.5,r>=.5&&!s?r-=.5:l+=.5);for(var c=Math.abs(l-r),u=y(c),d=Math.ceil(l/(1*Math.pow(10,u)))*Math.pow(10,u),p=s?0:Math.floor(r/(1*Math.pow(10,u)))*Math.pow(10,u),f=d-p,v=Math.pow(10,u),S=Math.round(f/v);(S>a||a>2*S)&&!h;)if(S>a)v*=2,S=Math.round(f/v),S%1!==0&&(h=!0);else if(n&&u>=0){if(v/2%1!==0)break;v/=2,S=Math.round(f/v)}else v/=2,S=Math.round(f/v);return h&&(S=o,v=f/S),{steps:S,stepValue:v,min:p,max:p+S*v}},s.template=function(t,i){function e(t,i){var e=/\W/.test(t)?new Function("obj","var p=[],print=function(){p.push.apply(p,arguments);};with(obj){p.push('"+t.replace(/[\r\t\n]/g," ").split("<%").join("	").replace(/((^|%>)[^\t]*)'/g,"$1\r").replace(/\t=(.*?)%>/g,"',$1,'").split("	").join("');").split("%>").join("p.push('").split("\r").join("\\'")+"');}return p.join('');"):s[t]=s[t];return i?e(i):e}if(t instanceof Function)return t(i);var s={};return e(t,i)}),w=(s.generateLabels=function(t,i,e,s){var o=new Array(i);return labelTemplateString&&n(o,function(i,n){o[n]=C(t,{value:e+s*(n+1)})}),o},s.easingEffects={linear:function(t){return t},easeInQuad:function(t){return t*t},easeOutQuad:function(t){return-1*t*(t-2)},easeInOutQuad:function(t){return(t/=.5)<1?.5*t*t:-0.5*(--t*(t-2)-1)},easeInCubic:function(t){return t*t*t},easeOutCubic:function(t){return 1*((t=t/1-1)*t*t+1)},easeInOutCubic:function(t){return(t/=.5)<1?.5*t*t*t:.5*((t-=2)*t*t+2)},easeInQuart:function(t){return t*t*t*t},easeOutQuart:function(t){return-1*((t=t/1-1)*t*t*t-1)},easeInOutQuart:function(t){return(t/=.5)<1?.5*t*t*t*t:-0.5*((t-=2)*t*t*t-2)},easeInQuint:function(t){return 1*(t/=1)*t*t*t*t},easeOutQuint:function(t){return 1*((t=t/1-1)*t*t*t*t+1)},easeInOutQuint:function(t){return(t/=.5)<1?.5*t*t*t*t*t:.5*((t-=2)*t*t*t*t+2)},easeInSine:function(t){return-1*Math.cos(t/1*(Math.PI/2))+1},easeOutSine:function(t){return 1*Math.sin(t/1*(Math.PI/2))},easeInOutSine:function(t){return-0.5*(Math.cos(Math.PI*t/1)-1)},easeInExpo:function(t){return 0===t?1:1*Math.pow(2,10*(t/1-1))},easeOutExpo:function(t){return 1===t?1:1*(-Math.pow(2,-10*t/1)+1)},easeInOutExpo:function(t){return 0===t?0:1===t?1:(t/=.5)<1?.5*Math.pow(2,10*(t-1)):.5*(-Math.pow(2,-10*--t)+2)},easeInCirc:function(t){return t>=1?t:-1*(Math.sqrt(1-(t/=1)*t)-1)},easeOutCirc:function(t){return 1*Math.sqrt(1-(t=t/1-1)*t)},easeInOutCirc:function(t){return(t/=.5)<1?-0.5*(Math.sqrt(1-t*t)-1):.5*(Math.sqrt(1-(t-=2)*t)+1)},easeInElastic:function(t){var i=1.70158,e=0,s=1;return 0===t?0:1==(t/=1)?1:(e||(e=.3),s<Math.abs(1)?(s=1,i=e/4):i=e/(2*Math.PI)*Math.asin(1/s),-(s*Math.pow(2,10*(t-=1))*Math.sin(2*(1*t-i)*Math.PI/e)))},easeOutElastic:function(t){var i=1.70158,e=0,s=1;return 0===t?0:1==(t/=1)?1:(e||(e=.3),s<Math.abs(1)?(s=1,i=e/4):i=e/(2*Math.PI)*Math.asin(1/s),s*Math.pow(2,-10*t)*Math.sin(2*(1*t-i)*Math.PI/e)+1)},easeInOutElastic:function(t){var i=1.70158,e=0,s=1;return 0===t?0:2==(t/=.5)?1:(e||(e=.3*1.5),s<Math.abs(1)?(s=1,i=e/4):i=e/(2*Math.PI)*Math.asin(1/s),1>t?-.5*s*Math.pow(2,10*(t-=1))*Math.sin(2*(1*t-i)*Math.PI/e):s*Math.pow(2,-10*(t-=1))*Math.sin(2*(1*t-i)*Math.PI/e)*.5+1)},easeInBack:function(t){var i=1.70158;return 1*(t/=1)*t*((i+1)*t-i)},easeOutBack:function(t){var i=1.70158;return 1*((t=t/1-1)*t*((i+1)*t+i)+1)},easeInOutBack:function(t){var i=1.70158;return(t/=.5)<1?.5*t*t*(((i*=1.525)+1)*t-i):.5*((t-=2)*t*(((i*=1.525)+1)*t+i)+2)},easeInBounce:function(t){return 1-w.easeOutBounce(1-t)},easeOutBounce:function(t){return(t/=1)<1/2.75?7.5625*t*t:2/2.75>t?1*(7.5625*(t-=1.5/2.75)*t+.75):2.5/2.75>t?1*(7.5625*(t-=2.25/2.75)*t+.9375):1*(7.5625*(t-=2.625/2.75)*t+.984375)},easeInOutBounce:function(t){return.5>t?.5*w.easeInBounce(2*t):.5*w.easeOutBounce(2*t-1)+.5}}),b=s.requestAnimFrame=function(){return window.requestAnimationFrame||window.webkitRequestAnimationFrame||window.mozRequestAnimationFrame||window.oRequestAnimationFrame||window.msRequestAnimationFrame||function(t){return window.setTimeout(t,1e3/60)}}(),P=s.cancelAnimFrame=function(){return window.cancelAnimationFrame||window.webkitCancelAnimationFrame||window.mozCancelAnimationFrame||window.oCancelAnimationFrame||window.msCancelAnimationFrame||function(t){return window.clearTimeout(t,1e3/60)}}(),L=(s.animationLoop=function(t,i,e,s,n,o){var a=0,h=w[e]||w.linear,l=function(){a++;var e=a/i,r=h(e);t.call(o,r,e,a),s.call(o,r,e),i>a?o.animationFrame=b(l):n.apply(o)};b(l)},s.getRelativePosition=function(t){var i,e,s=t.originalEvent||t,n=t.currentTarget||t.srcElement,o=n.getBoundingClientRect();return s.touches?(i=s.touches[0].clientX-o.left,e=s.touches[0].clientY-o.top):(i=s.clientX-o.left,e=s.clientY-o.top),{x:i,y:e}},s.addEvent=function(t,i,e){t.addEventListener?t.addEventListener(i,e):t.attachEvent?t.attachEvent("on"+i,e):t["on"+i]=e}),k=s.removeEvent=function(t,i,e){t.removeEventListener?t.removeEventListener(i,e,!1):t.detachEvent?t.detachEvent("on"+i,e):t["on"+i]=c},F=(s.bindEvents=function(t,i,e){t.events||(t.events={}),n(i,function(i){t.events[i]=function(){e.apply(t,arguments)},L(t.chart.canvas,i,t.events[i])})},s.unbindEvents=function(t,i){n(i,function(i,e){k(t.chart.canvas,e,i)})}),R=s.getMaximumWidth=function(t){var i=t.parentNode;return i.clientWidth},T=s.getMaximumHeight=function(t){var i=t.parentNode;return i.clientHeight},A=(s.getMaximumSize=s.getMaximumWidth,s.retinaScale=function(t){var i=t.ctx,e=t.canvas.width,s=t.canvas.height;window.devicePixelRatio&&(i.canvas.style.width=e+"px",i.canvas.style.height=s+"px",i.canvas.height=s*window.devicePixelRatio,i.canvas.width=e*window.devicePixelRatio,i.scale(window.devicePixelRatio,window.devicePixelRatio))}),M=s.clear=function(t){t.ctx.clearRect(0,0,t.width,t.height)},W=s.fontString=function(t,i,e){return i+" "+t+"px "+e},z=s.longestText=function(t,i,e){t.font=i;var s=0;return n(e,function(i){var e=t.measureText(i).width;s=e>s?e:s}),s},B=s.drawRoundedRectangle=function(t,i,e,s,n,o){t.beginPath(),t.moveTo(i+o,e),t.lineTo(i+s-o,e),t.quadraticCurveTo(i+s,e,i+s,e+o),t.lineTo(i+s,e+n-o),t.quadraticCurveTo(i+s,e+n,i+s-o,e+n),t.lineTo(i+o,e+n),t.quadraticCurveTo(i,e+n,i,e+n-o),t.lineTo(i,e+o),t.quadraticCurveTo(i,e,i+o,e),t.closePath()};e.instances={},e.Type=function(t,i,s){this.options=i,this.chart=s,this.id=u(),e.instances[this.id]=this,i.responsive&&this.resize(),this.initialize.call(this,t)},a(e.Type.prototype,{initialize:function(){return this},clear:function(){return M(this.chart),this},stop:function(){return P(this.animationFrame),this},resize:function(t){this.stop();var i=this.chart.canvas,e=R(this.chart.canvas),s=this.options.maintainAspectRatio?e/this.chart.aspectRatio:T(this.chart.canvas);return i.width=this.chart.width=e,i.height=this.chart.height=s,A(this.chart),"function"==typeof t&&t.apply(this,Array.prototype.slice.call(arguments,1)),this},reflow:c,render:function(t){return t&&this.reflow(),this.options.animation&&!t?s.animationLoop(this.draw,this.options.animationSteps,this.options.animationEasing,this.options.onAnimationProgress,this.options.onAnimationComplete,this):(this.draw(),this.options.onAnimationComplete.call(this)),this},generateLegend:function(){return C(this.options.legendTemplate,this)},destroy:function(){this.clear(),F(this,this.events);var t=this.chart.canvas;t.width=this.chart.width,t.height=this.chart.height,t.style.removeProperty?(t.style.removeProperty("width"),t.style.removeProperty("height")):(t.style.removeAttribute("width"),t.style.removeAttribute("height")),delete e.instances[this.id]},showTooltip:function(t,i){"undefined"==typeof this.activeElements&&(this.activeElements=[]);var o=function(t){var i=!1;return t.length!==this.activeElements.length?i=!0:(n(t,function(t,e){t!==this.activeElements[e]&&(i=!0)},this),i)}.call(this,t);if(o||i){if(this.activeElements=t,this.draw(),this.options.customTooltips&&this.options.customTooltips(!1),t.length>0)if(this.datasets&&this.datasets.length>1){for(var a,h,r=this.datasets.length-1;r>=0&&(a=this.datasets[r].points||this.datasets[r].bars||this.datasets[r].segments,h=l(a,t[0]),-1===h);r--);var c=[],u=[],d=function(){var t,i,e,n,o,a=[],l=[],r=[];return s.each(this.datasets,function(i){t=i.points||i.bars||i.segments,t[h]&&t[h].hasValue()&&a.push(t[h])}),s.each(a,function(t){l.push(t.x),r.push(t.y),c.push(s.template(this.options.multiTooltipTemplate,t)),u.push({fill:t._saved.fillColor||t.fillColor,stroke:t._saved.strokeColor||t.strokeColor})},this),o=m(r),e=g(r),n=m(l),i=g(l),{x:n>this.chart.width/2?n:i,y:(o+e)/2}}.call(this,h);new e.MultiTooltip({x:d.x,y:d.y,xPadding:this.options.tooltipXPadding,yPadding:this.options.tooltipYPadding,xOffset:this.options.tooltipXOffset,fillColor:this.options.tooltipFillColor,textColor:this.options.tooltipFontColor,fontFamily:this.options.tooltipFontFamily,fontStyle:this.options.tooltipFontStyle,fontSize:this.options.tooltipFontSize,titleTextColor:this.options.tooltipTitleFontColor,titleFontFamily:this.options.tooltipTitleFontFamily,titleFontStyle:this.options.tooltipTitleFontStyle,titleFontSize:this.options.tooltipTitleFontSize,cornerRadius:this.options.tooltipCornerRadius,labels:c,legendColors:u,legendColorBackground:this.options.multiTooltipKeyBackground,title:t[0].label,chart:this.chart,ctx:this.chart.ctx,custom:this.options.customTooltips}).draw()}else n(t,function(t){var i=t.tooltipPosition();new e.Tooltip({x:Math.round(i.x),y:Math.round(i.y),xPadding:this.options.tooltipXPadding,yPadding:this.options.tooltipYPadding,fillColor:this.options.tooltipFillColor,textColor:this.options.tooltipFontColor,fontFamily:this.options.tooltipFontFamily,fontStyle:this.options.tooltipFontStyle,fontSize:this.options.tooltipFontSize,caretHeight:this.options.tooltipCaretSize,cornerRadius:this.options.tooltipCornerRadius,text:C(this.options.tooltipTemplate,t),chart:this.chart,custom:this.options.customTooltips}).draw()},this);return this}},toBase64Image:function(){return this.chart.canvas.toDataURL.apply(this.chart.canvas,arguments)}}),e.Type.extend=function(t){var i=this,s=function(){return i.apply(this,arguments)};if(s.prototype=o(i.prototype),a(s.prototype,t),s.extend=e.Type.extend,t.name||i.prototype.name){var n=t.name||i.prototype.name,l=e.defaults[i.prototype.name]?o(e.defaults[i.prototype.name]):{};e.defaults[n]=a(l,t.defaults),e.types[n]=s,e.prototype[n]=function(t,i){var o=h(e.defaults.global,e.defaults[n],i||{});return new s(t,o,this)}}else d("Name not provided for this chart, so it hasn't been registered");return i},e.Element=function(t){a(this,t),this.initialize.apply(this,arguments),this.save()},a(e.Element.prototype,{initialize:function(){},restore:function(t){return t?n(t,function(t){this[t]=this._saved[t]},this):a(this,this._saved),this},save:function(){return this._saved=o(this),delete this._saved._saved,this},update:function(t){return n(t,function(t,i){this._saved[i]=this[i],this[i]=t},this),this},transition:function(t,i){return n(t,function(t,e){this[e]=(t-this._saved[e])*i+this._saved[e]},this),this},tooltipPosition:function(){return{x:this.x,y:this.y}},hasValue:function(){return f(this.value)}}),e.Element.extend=r,e.Point=e.Element.extend({display:!0,inRange:function(t,i){var e=this.hitDetectionRadius+this.radius;return Math.pow(t-this.x,2)+Math.pow(i-this.y,2)<Math.pow(e,2)},draw:function(){if(this.display){var t=this.ctx;t.beginPath(),t.arc(this.x,this.y,this.radius,0,2*Math.PI),t.closePath(),t.strokeStyle=this.strokeColor,t.lineWidth=this.strokeWidth,t.fillStyle=this.fillColor,t.fill(),t.stroke()}}}),e.Arc=e.Element.extend({inRange:function(t,i){var e=s.getAngleFromPoint(this,{x:t,y:i}),n=e.angle>=this.startAngle&&e.angle<=this.endAngle,o=e.distance>=this.innerRadius&&e.distance<=this.outerRadius;return n&&o},tooltipPosition:function(){var t=this.startAngle+(this.endAngle-this.startAngle)/2,i=(this.outerRadius-this.innerRadius)/2+this.innerRadius;return{x:this.x+Math.cos(t)*i,y:this.y+Math.sin(t)*i}},draw:function(t){var i=this.ctx;i.beginPath(),i.arc(this.x,this.y,this.outerRadius,this.startAngle,this.endAngle),i.arc(this.x,this.y,this.innerRadius,this.endAngle,this.startAngle,!0),i.closePath(),i.strokeStyle=this.strokeColor,i.lineWidth=this.strokeWidth,i.fillStyle=this.fillColor,i.fill(),i.lineJoin="bevel",this.showStroke&&i.stroke()}}),e.Rectangle=e.Element.extend({draw:function(){var t=this.ctx,i=this.width/2,e=this.x-i,s=this.x+i,n=this.base-(this.base-this.y),o=this.strokeWidth/2;this.showStroke&&(e+=o,s-=o,n+=o),t.beginPath(),t.fillStyle=this.fillColor,t.strokeStyle=this.strokeColor,t.lineWidth=this.strokeWidth,t.moveTo(e,this.base),t.lineTo(e,n),t.lineTo(s,n),t.lineTo(s,this.base),t.fill(),this.showStroke&&t.stroke()},height:function(){return this.base-this.y},inRange:function(t,i){return t>=this.x-this.width/2&&t<=this.x+this.width/2&&i>=this.y&&i<=this.base}}),e.Tooltip=e.Element.extend({draw:function(){var t=this.chart.ctx;t.font=W(this.fontSize,this.fontStyle,this.fontFamily),this.xAlign="center",this.yAlign="above";var i=this.caretPadding=2,e=t.measureText(this.text).width+2*this.xPadding,s=this.fontSize+2*this.yPadding,n=s+this.caretHeight+i;this.x+e/2>this.chart.width?this.xAlign="left":this.x-e/2<0&&(this.xAlign="right"),this.y-n<0&&(this.yAlign="below");var o=this.x-e/2,a=this.y-n;if(t.fillStyle=this.fillColor,this.custom)this.custom(this);else{switch(this.yAlign){case"above":t.beginPath(),t.moveTo(this.x,this.y-i),t.lineTo(this.x+this.caretHeight,this.y-(i+this.caretHeight)),t.lineTo(this.x-this.caretHeight,this.y-(i+this.caretHeight)),t.closePath(),t.fill();break;case"below":a=this.y+i+this.caretHeight,t.beginPath(),t.moveTo(this.x,this.y+i),t.lineTo(this.x+this.caretHeight,this.y+i+this.caretHeight),t.lineTo(this.x-this.caretHeight,this.y+i+this.caretHeight),t.closePath(),t.fill()}switch(this.xAlign){case"left":o=this.x-e+(this.cornerRadius+this.caretHeight);break;case"right":o=this.x-(this.cornerRadius+this.caretHeight)}B(t,o,a,e,s,this.cornerRadius),t.fill(),t.fillStyle=this.textColor,t.textAlign="center",t.textBaseline="middle",t.fillText(this.text,o+e/2,a+s/2)}}}),e.MultiTooltip=e.Element.extend({initialize:function(){this.font=W(this.fontSize,this.fontStyle,this.fontFamily),this.titleFont=W(this.titleFontSize,this.titleFontStyle,this.titleFontFamily),this.height=this.labels.length*this.fontSize+(this.labels.length-1)*(this.fontSize/2)+2*this.yPadding+1.5*this.titleFontSize,this.ctx.font=this.titleFont;var t=this.ctx.measureText(this.title).width,i=z(this.ctx,this.font,this.labels)+this.fontSize+3,e=g([i,t]);this.width=e+2*this.xPadding;var s=this.height/2;this.y-s<0?this.y=s:this.y+s>this.chart.height&&(this.y=this.chart.height-s),this.x>this.chart.width/2?this.x-=this.xOffset+this.width:this.x+=this.xOffset},getLineHeight:function(t){var i=this.y-this.height/2+this.yPadding,e=t-1;return 0===t?i+this.titleFontSize/2:i+(1.5*this.fontSize*e+this.fontSize/2)+1.5*this.titleFontSize},draw:function(){if(this.custom)this.custom(this);else{B(this.ctx,this.x,this.y-this.height/2,this.width,this.height,this.cornerRadius);var t=this.ctx;t.fillStyle=this.fillColor,t.fill(),t.closePath(),t.textAlign="left",t.textBaseline="middle",t.fillStyle=this.titleTextColor,t.font=this.titleFont,t.fillText(this.title,this.x+this.xPadding,this.getLineHeight(0)),t.font=this.font,s.each(this.labels,function(i,e){t.fillStyle=this.textColor,t.fillText(i,this.x+this.xPadding+this.fontSize+3,this.getLineHeight(e+1)),t.fillStyle=this.legendColorBackground,t.fillRect(this.x+this.xPadding,this.getLineHeight(e+1)-this.fontSize/2,this.fontSize,this.fontSize),t.fillStyle=this.legendColors[e].fill,t.fillRect(this.x+this.xPadding,this.getLineHeight(e+1)-this.fontSize/2,this.fontSize,this.fontSize)},this)}}}),e.Scale=e.Element.extend({initialize:function(){this.fit()},buildYLabels:function(){this.yLabels=[];for(var t=v(this.stepValue),i=0;i<=this.steps;i++)this.yLabels.push(C(this.templateString,{value:(this.min+i*this.stepValue).toFixed(t)}));this.yLabelWidth=this.display&&this.showLabels?z(this.ctx,this.font,this.yLabels):0},addXLabel:function(t){this.xLabels.push(t),this.valuesCount++,this.fit()},removeXLabel:function(){this.xLabels.shift(),this.valuesCount--,this.fit()},fit:function(){this.startPoint=this.display?this.fontSize:0,this.endPoint=this.display?this.height-1.5*this.fontSize-5:this.height,this.startPoint+=this.padding,this.endPoint-=this.padding;var t,i=this.endPoint-this.startPoint;for(this.calculateYRange(i),this.buildYLabels(),this.calculateXLabelRotation();i>this.endPoint-this.startPoint;)i=this.endPoint-this.startPoint,t=this.yLabelWidth,this.calculateYRange(i),this.buildYLabels(),t<this.yLabelWidth&&this.calculateXLabelRotation()},calculateXLabelRotation:function(){this.ctx.font=this.font;var t,i,e=this.ctx.measureText(this.xLabels[0]).width,s=this.ctx.measureText(this.xLabels[this.xLabels.length-1]).width;if(this.xScalePaddingRight=s/2+3,this.xScalePaddingLeft=e/2>this.yLabelWidth+10?e/2:this.yLabelWidth+10,this.xLabelRotation=0,this.display){var n,o=z(this.ctx,this.font,this.xLabels);this.xLabelWidth=o;for(var a=Math.floor(this.calculateX(1)-this.calculateX(0))-6;this.xLabelWidth>a&&0===this.xLabelRotation||this.xLabelWidth>a&&this.xLabelRotation<=90&&this.xLabelRotation>0;)n=Math.cos(S(this.xLabelRotation)),t=n*e,i=n*s,t+this.fontSize/2>this.yLabelWidth+8&&(this.xScalePaddingLeft=t+this.fontSize/2),this.xScalePaddingRight=this.fontSize/2,this.xLabelRotation++,this.xLabelWidth=n*o;this.xLabelRotation>0&&(this.endPoint-=Math.sin(S(this.xLabelRotation))*o+3)}else this.xLabelWidth=0,this.xScalePaddingRight=this.padding,this.xScalePaddingLeft=this.padding},calculateYRange:c,drawingArea:function(){return this.startPoint-this.endPoint},calculateY:function(t){var i=this.drawingArea()/(this.min-this.max);return this.endPoint-i*(t-this.min)},calculateX:function(t){var i=(this.xLabelRotation>0,this.width-(this.xScalePaddingLeft+this.xScalePaddingRight)),e=i/Math.max(this.valuesCount-(this.offsetGridLines?0:1),1),s=e*t+this.xScalePaddingLeft;return this.offsetGridLines&&(s+=e/2),Math.round(s)},update:function(t){s.extend(this,t),this.fit()},draw:function(){var t=this.ctx,i=(this.endPoint-this.startPoint)/this.steps,e=Math.round(this.xScalePaddingLeft);this.display&&(t.fillStyle=this.textColor,t.font=this.font,n(this.yLabels,function(n,o){var a=this.endPoint-i*o,h=Math.round(a),l=this.showHorizontalLines;t.textAlign="right",t.textBaseline="middle",this.showLabels&&t.fillText(n,e-10,a),0!==o||l||(l=!0),l&&t.beginPath(),o>0?(t.lineWidth=this.gridLineWidth,t.strokeStyle=this.gridLineColor):(t.lineWidth=this.lineWidth,t.strokeStyle=this.lineColor),h+=s.aliasPixel(t.lineWidth),l&&(t.moveTo(e,h),t.lineTo(this.width,h),t.stroke(),t.closePath()),t.lineWidth=this.lineWidth,t.strokeStyle=this.lineColor,t.beginPath(),t.moveTo(e-5,h),t.lineTo(e,h),t.stroke(),t.closePath()},this),n(this.xLabels,function(i,e){var s=this.calculateX(e)+x(this.lineWidth),n=this.calculateX(e-(this.offsetGridLines?.5:0))+x(this.lineWidth),o=this.xLabelRotation>0,a=this.showVerticalLines;0!==e||a||(a=!0),a&&t.beginPath(),e>0?(t.lineWidth=this.gridLineWidth,t.strokeStyle=this.gridLineColor):(t.lineWidth=this.lineWidth,t.strokeStyle=this.lineColor),a&&(t.moveTo(n,this.endPoint),t.lineTo(n,this.startPoint-3),t.stroke(),t.closePath()),t.lineWidth=this.lineWidth,t.strokeStyle=this.lineColor,t.beginPath(),t.moveTo(n,this.endPoint),t.lineTo(n,this.endPoint+5),t.stroke(),t.closePath(),t.save(),t.translate(s,o?this.endPoint+12:this.endPoint+8),t.rotate(-1*S(this.xLabelRotation)),t.font=this.font,t.textAlign=o?"right":"center",t.textBaseline=o?"middle":"top",t.fillText(i,0,0),t.restore()},this))}}),e.RadialScale=e.Element.extend({initialize:function(){this.size=m([this.height,this.width]),this.drawingArea=this.display?this.size/2-(this.fontSize/2+this.backdropPaddingY):this.size/2},calculateCenterOffset:function(t){var i=this.drawingArea/(this.max-this.min);return(t-this.min)*i},update:function(){this.lineArc?this.drawingArea=this.display?this.size/2-(this.fontSize/2+this.backdropPaddingY):this.size/2:this.setScaleSize(),this.buildYLabels()},buildYLabels:function(){this.yLabels=[];for(var t=v(this.stepValue),i=0;i<=this.steps;i++)this.yLabels.push(C(this.templateString,{value:(this.min+i*this.stepValue).toFixed(t)}))},getCircumference:function(){return 2*Math.PI/this.valuesCount},setScaleSize:function(){var t,i,e,s,n,o,a,h,l,r,c,u,d=m([this.height/2-this.pointLabelFontSize-5,this.width/2]),p=this.width,g=0;for(this.ctx.font=W(this.pointLabelFontSize,this.pointLabelFontStyle,this.pointLabelFontFamily),i=0;i<this.valuesCount;i++)t=this.getPointPosition(i,d),e=this.ctx.measureText(C(this.templateString,{value:this.labels[i]})).width+5,0===i||i===this.valuesCount/2?(s=e/2,t.x+s>p&&(p=t.x+s,n=i),t.x-s<g&&(g=t.x-s,a=i)):i<this.valuesCount/2?t.x+e>p&&(p=t.x+e,n=i):i>this.valuesCount/2&&t.x-e<g&&(g=t.x-e,a=i);l=g,r=Math.ceil(p-this.width),o=this.getIndexAngle(n),h=this.getIndexAngle(a),c=r/Math.sin(o+Math.PI/2),u=l/Math.sin(h+Math.PI/2),c=f(c)?c:0,u=f(u)?u:0,this.drawingArea=d-(u+c)/2,this.setCenterPoint(u,c)},setCenterPoint:function(t,i){var e=this.width-i-this.drawingArea,s=t+this.drawingArea;this.xCenter=(s+e)/2,this.yCenter=this.height/2},getIndexAngle:function(t){var i=2*Math.PI/this.valuesCount;return t*i-Math.PI/2},getPointPosition:function(t,i){var e=this.getIndexAngle(t);return{x:Math.cos(e)*i+this.xCenter,y:Math.sin(e)*i+this.yCenter}},draw:function(){if(this.display){var t=this.ctx;if(n(this.yLabels,function(i,e){if(e>0){var s,n=e*(this.drawingArea/this.steps),o=this.yCenter-n;if(this.lineWidth>0)if(t.strokeStyle=this.lineColor,t.lineWidth=this.lineWidth,this.lineArc)t.beginPath(),t.arc(this.xCenter,this.yCenter,n,0,2*Math.PI),t.closePath(),t.stroke();else{t.beginPath();for(var a=0;a<this.valuesCount;a++)s=this.getPointPosition(a,this.calculateCenterOffset(this.min+e*this.stepValue)),0===a?t.moveTo(s.x,s.y):t.lineTo(s.x,s.y);t.closePath(),t.stroke()}if(this.showLabels){if(t.font=W(this.fontSize,this.fontStyle,this.fontFamily),this.showLabelBackdrop){var h=t.measureText(i).width;t.fillStyle=this.backdropColor,t.fillRect(this.xCenter-h/2-this.backdropPaddingX,o-this.fontSize/2-this.backdropPaddingY,h+2*this.backdropPaddingX,this.fontSize+2*this.backdropPaddingY)}t.textAlign="center",t.textBaseline="middle",t.fillStyle=this.fontColor,t.fillText(i,this.xCenter,o)}}},this),!this.lineArc){t.lineWidth=this.angleLineWidth,t.strokeStyle=this.angleLineColor;for(var i=this.valuesCount-1;i>=0;i--){if(this.angleLineWidth>0){var e=this.getPointPosition(i,this.calculateCenterOffset(this.max));t.beginPath(),t.moveTo(this.xCenter,this.yCenter),t.lineTo(e.x,e.y),t.stroke(),t.closePath()}var s=this.getPointPosition(i,this.calculateCenterOffset(this.max)+5);t.font=W(this.pointLabelFontSize,this.pointLabelFontStyle,this.pointLabelFontFamily),t.fillStyle=this.pointLabelFontColor;var o=this.labels.length,a=this.labels.length/2,h=a/2,l=h>i||i>o-h,r=i===h||i===o-h;t.textAlign=0===i?"center":i===a?"center":a>i?"left":"right",t.textBaseline=r?"middle":l?"bottom":"top",t.fillText(this.labels[i],s.x,s.y)}}}}}),s.addEvent(window,"resize",function(){var t;return function(){clearTimeout(t),t=setTimeout(function(){n(e.instances,function(t){t.options.responsive&&t.resize(t.render,!0)})},50)}}()),p?define(function(){return e}):"object"==typeof module&&module.exports&&(module.exports=e),t.Chart=e,e.noConflict=function(){return t.Chart=i,e}}).call(this),function(){"use strict";var t=this,i=t.Chart,e=i.helpers,s={scaleBeginAtZero:!0,scaleShowGridLines:!0,scaleGridLineColor:"rgba(0,0,0,.05)",scaleGridLineWidth:1,scaleShowHorizontalLines:!0,scaleShowVerticalLines:!0,barShowStroke:!0,barStrokeWidth:2,barValueSpacing:5,barDatasetSpacing:1,legendTemplate:'<ul class="<%=name.toLowerCase()%>-legend"><% for (var i=0; i<datasets.length; i++){%><li><span style="background-color:<%=datasets[i].fillColor%>"></span><%if(datasets[i].label){%><%=datasets[i].label%><%}%></li><%}%></ul>'};i.Type.extend({name:"Bar",defaults:s,initialize:function(t){var s=this.options;this.ScaleClass=i.Scale.extend({offsetGridLines:!0,calculateBarX:function(t,i,e){var n=this.calculateBaseWidth(),o=this.calculateX(e)-n/2,a=this.calculateBarWidth(t);return o+a*i+i*s.barDatasetSpacing+a/2},calculateBaseWidth:function(){return this.calculateX(1)-this.calculateX(0)-2*s.barValueSpacing},calculateBarWidth:function(t){var i=this.calculateBaseWidth()-(t-1)*s.barDatasetSpacing;return i/t}}),this.datasets=[],this.options.showTooltips&&e.bindEvents(this,this.options.tooltipEvents,function(t){var i="mouseout"!==t.type?this.getBarsAtEvent(t):[];this.eachBars(function(t){t.restore(["fillColor","strokeColor"])}),e.each(i,function(t){t.fillColor=t.highlightFill,t.strokeColor=t.highlightStroke}),this.showTooltip(i)}),this.BarClass=i.Rectangle.extend({strokeWidth:this.options.barStrokeWidth,showStroke:this.options.barShowStroke,ctx:this.chart.ctx}),e.each(t.datasets,function(i){var s={label:i.label||null,fillColor:i.fillColor,strokeColor:i.strokeColor,bars:[]};this.datasets.push(s),e.each(i.data,function(e,n){s.bars.push(new this.BarClass({value:e,label:t.labels[n],datasetLabel:i.label,strokeColor:i.strokeColor,fillColor:i.fillColor,highlightFill:i.highlightFill||i.fillColor,highlightStroke:i.highlightStroke||i.strokeColor}))},this)},this),this.buildScale(t.labels),this.BarClass.prototype.base=this.scale.endPoint,this.eachBars(function(t,i,s){e.extend(t,{width:this.scale.calculateBarWidth(this.datasets.length),x:this.scale.calculateBarX(this.datasets.length,s,i),y:this.scale.endPoint}),t.save()},this),this.render()},update:function(){this.scale.update(),e.each(this.activeElements,function(t){t.restore(["fillColor","strokeColor"])}),this.eachBars(function(t){t.save()}),this.render()},eachBars:function(t){e.each(this.datasets,function(i,s){e.each(i.bars,t,this,s)},this)},getBarsAtEvent:function(t){for(var i,s=[],n=e.getRelativePosition(t),o=function(t){s.push(t.bars[i])},a=0;a<this.datasets.length;a++)for(i=0;i<this.datasets[a].bars.length;i++)if(this.datasets[a].bars[i].inRange(n.x,n.y))return e.each(this.datasets,o),s;return s},buildScale:function(t){var i=this,s=function(){var t=[];return i.eachBars(function(i){t.push(i.value)}),t},n={templateString:this.options.scaleLabel,height:this.chart.height,width:this.chart.width,ctx:this.chart.ctx,textColor:this.options.scaleFontColor,fontSize:this.options.scaleFontSize,fontStyle:this.options.scaleFontStyle,fontFamily:this.options.scaleFontFamily,valuesCount:t.length,beginAtZero:this.options.scaleBeginAtZero,integersOnly:this.options.scaleIntegersOnly,calculateYRange:function(t){var i=e.calculateScaleRange(s(),t,this.fontSize,this.beginAtZero,this.integersOnly);e.extend(this,i)},xLabels:t,font:e.fontString(this.options.scaleFontSize,this.options.scaleFontStyle,this.options.scaleFontFamily),lineWidth:this.options.scaleLineWidth,lineColor:this.options.scaleLineColor,showHorizontalLines:this.options.scaleShowHorizontalLines,showVerticalLines:this.options.scaleShowVerticalLines,gridLineWidth:this.options.scaleShowGridLines?this.options.scaleGridLineWidth:0,gridLineColor:this.options.scaleShowGridLines?this.options.scaleGridLineColor:"rgba(0,0,0,0)",padding:this.options.showScale?0:this.options.barShowStroke?this.options.barStrokeWidth:0,showLabels:this.options.scaleShowLabels,display:this.options.showScale};this.options.scaleOverride&&e.extend(n,{calculateYRange:e.noop,steps:this.options.scaleSteps,stepValue:this.options.scaleStepWidth,min:this.options.scaleStartValue,max:this.options.scaleStartValue+this.options.scaleSteps*this.options.scaleStepWidth}),this.scale=new this.ScaleClass(n)},addData:function(t,i){e.each(t,function(t,e){this.datasets[e].bars.push(new this.BarClass({value:t,label:i,x:this.scale.calculateBarX(this.datasets.length,e,this.scale.valuesCount+1),y:this.scale.endPoint,width:this.scale.calculateBarWidth(this.datasets.length),base:this.scale.endPoint,strokeColor:this.datasets[e].strokeColor,fillColor:this.datasets[e].fillColor}))
},this),this.scale.addXLabel(i),this.update()},removeData:function(){this.scale.removeXLabel(),e.each(this.datasets,function(t){t.bars.shift()},this),this.update()},reflow:function(){e.extend(this.BarClass.prototype,{y:this.scale.endPoint,base:this.scale.endPoint});var t=e.extend({height:this.chart.height,width:this.chart.width});this.scale.update(t)},draw:function(t){var i=t||1;this.clear();this.chart.ctx;this.scale.draw(i),e.each(this.datasets,function(t,s){e.each(t.bars,function(t,e){t.hasValue()&&(t.base=this.scale.endPoint,t.transition({x:this.scale.calculateBarX(this.datasets.length,s,e),y:this.scale.calculateY(t.value),width:this.scale.calculateBarWidth(this.datasets.length)},i).draw())},this)},this)}})}.call(this),function(){"use strict";var t=this,i=t.Chart,e=i.helpers,s={segmentShowStroke:!0,segmentStrokeColor:"#fff",segmentStrokeWidth:2,percentageInnerCutout:50,animationSteps:100,animationEasing:"easeOutBounce",animateRotate:!0,animateScale:!1,legendTemplate:'<ul class="<%=name.toLowerCase()%>-legend"><% for (var i=0; i<segments.length; i++){%><li><span style="background-color:<%=segments[i].fillColor%>"></span><%if(segments[i].label){%><%=segments[i].label%><%}%></li><%}%></ul>'};i.Type.extend({name:"Doughnut",defaults:s,initialize:function(t){this.segments=[],this.outerRadius=(e.min([this.chart.width,this.chart.height])-this.options.segmentStrokeWidth/2)/2,this.SegmentArc=i.Arc.extend({ctx:this.chart.ctx,x:this.chart.width/2,y:this.chart.height/2}),this.options.showTooltips&&e.bindEvents(this,this.options.tooltipEvents,function(t){var i="mouseout"!==t.type?this.getSegmentsAtEvent(t):[];e.each(this.segments,function(t){t.restore(["fillColor"])}),e.each(i,function(t){t.fillColor=t.highlightColor}),this.showTooltip(i)}),this.calculateTotal(t),e.each(t,function(t,i){this.addData(t,i,!0)},this),this.render()},getSegmentsAtEvent:function(t){var i=[],s=e.getRelativePosition(t);return e.each(this.segments,function(t){t.inRange(s.x,s.y)&&i.push(t)},this),i},addData:function(t,i,e){var s=i||this.segments.length;this.segments.splice(s,0,new this.SegmentArc({value:t.value,outerRadius:this.options.animateScale?0:this.outerRadius,innerRadius:this.options.animateScale?0:this.outerRadius/100*this.options.percentageInnerCutout,fillColor:t.color,highlightColor:t.highlight||t.color,showStroke:this.options.segmentShowStroke,strokeWidth:this.options.segmentStrokeWidth,strokeColor:this.options.segmentStrokeColor,startAngle:1.5*Math.PI,circumference:this.options.animateRotate?0:this.calculateCircumference(t.value),label:t.label})),e||(this.reflow(),this.update())},calculateCircumference:function(t){return 2*Math.PI*(Math.abs(t)/this.total)},calculateTotal:function(t){this.total=0,e.each(t,function(t){this.total+=Math.abs(t.value)},this)},update:function(){this.calculateTotal(this.segments),e.each(this.activeElements,function(t){t.restore(["fillColor"])}),e.each(this.segments,function(t){t.save()}),this.render()},removeData:function(t){var i=e.isNumber(t)?t:this.segments.length-1;this.segments.splice(i,1),this.reflow(),this.update()},reflow:function(){e.extend(this.SegmentArc.prototype,{x:this.chart.width/2,y:this.chart.height/2}),this.outerRadius=(e.min([this.chart.width,this.chart.height])-this.options.segmentStrokeWidth/2)/2,e.each(this.segments,function(t){t.update({outerRadius:this.outerRadius,innerRadius:this.outerRadius/100*this.options.percentageInnerCutout})},this)},draw:function(t){var i=t?t:1;this.clear(),e.each(this.segments,function(t,e){t.transition({circumference:this.calculateCircumference(t.value),outerRadius:this.outerRadius,innerRadius:this.outerRadius/100*this.options.percentageInnerCutout},i),t.endAngle=t.startAngle+t.circumference,t.draw(),0===e&&(t.startAngle=1.5*Math.PI),e<this.segments.length-1&&(this.segments[e+1].startAngle=t.endAngle)},this)}}),i.types.Doughnut.extend({name:"Pie",defaults:e.merge(s,{percentageInnerCutout:0})})}.call(this),function(){"use strict";var t=this,i=t.Chart,e=i.helpers,s={scaleShowGridLines:!0,scaleGridLineColor:"rgba(0,0,0,.05)",scaleGridLineWidth:1,scaleShowHorizontalLines:!0,scaleShowVerticalLines:!0,bezierCurve:!0,bezierCurveTension:.4,pointDot:!0,pointDotRadius:4,pointDotStrokeWidth:1,pointHitDetectionRadius:20,datasetStroke:!0,datasetStrokeWidth:2,datasetFill:!0,legendTemplate:'<ul class="<%=name.toLowerCase()%>-legend"><% for (var i=0; i<datasets.length; i++){%><li><span style="background-color:<%=datasets[i].strokeColor%>"></span><%if(datasets[i].label){%><%=datasets[i].label%><%}%></li><%}%></ul>'};i.Type.extend({name:"Line",defaults:s,initialize:function(t){this.PointClass=i.Point.extend({strokeWidth:this.options.pointDotStrokeWidth,radius:this.options.pointDotRadius,display:this.options.pointDot,hitDetectionRadius:this.options.pointHitDetectionRadius,ctx:this.chart.ctx,inRange:function(t){return Math.pow(t-this.x,2)<Math.pow(this.radius+this.hitDetectionRadius,2)}}),this.datasets=[],this.options.showTooltips&&e.bindEvents(this,this.options.tooltipEvents,function(t){var i="mouseout"!==t.type?this.getPointsAtEvent(t):[];this.eachPoints(function(t){t.restore(["fillColor","strokeColor"])}),e.each(i,function(t){t.fillColor=t.highlightFill,t.strokeColor=t.highlightStroke}),this.showTooltip(i)}),e.each(t.datasets,function(i){var s={label:i.label||null,fillColor:i.fillColor,strokeColor:i.strokeColor,pointColor:i.pointColor,pointStrokeColor:i.pointStrokeColor,points:[]};this.datasets.push(s),e.each(i.data,function(e,n){s.points.push(new this.PointClass({value:e,label:t.labels[n],datasetLabel:i.label,strokeColor:i.pointStrokeColor,fillColor:i.pointColor,highlightFill:i.pointHighlightFill||i.pointColor,highlightStroke:i.pointHighlightStroke||i.pointStrokeColor}))},this),this.buildScale(t.labels),this.eachPoints(function(t,i){e.extend(t,{x:this.scale.calculateX(i),y:this.scale.endPoint}),t.save()},this)},this),this.render()},update:function(){this.scale.update(),e.each(this.activeElements,function(t){t.restore(["fillColor","strokeColor"])}),this.eachPoints(function(t){t.save()}),this.render()},eachPoints:function(t){e.each(this.datasets,function(i){e.each(i.points,t,this)},this)},getPointsAtEvent:function(t){var i=[],s=e.getRelativePosition(t);return e.each(this.datasets,function(t){e.each(t.points,function(t){t.inRange(s.x,s.y)&&i.push(t)})},this),i},buildScale:function(t){var s=this,n=function(){var t=[];return s.eachPoints(function(i){t.push(i.value)}),t},o={templateString:this.options.scaleLabel,height:this.chart.height,width:this.chart.width,ctx:this.chart.ctx,textColor:this.options.scaleFontColor,fontSize:this.options.scaleFontSize,fontStyle:this.options.scaleFontStyle,fontFamily:this.options.scaleFontFamily,valuesCount:t.length,beginAtZero:this.options.scaleBeginAtZero,integersOnly:this.options.scaleIntegersOnly,calculateYRange:function(t){var i=e.calculateScaleRange(n(),t,this.fontSize,this.beginAtZero,this.integersOnly);e.extend(this,i)},xLabels:t,font:e.fontString(this.options.scaleFontSize,this.options.scaleFontStyle,this.options.scaleFontFamily),lineWidth:this.options.scaleLineWidth,lineColor:this.options.scaleLineColor,showHorizontalLines:this.options.scaleShowHorizontalLines,showVerticalLines:this.options.scaleShowVerticalLines,gridLineWidth:this.options.scaleShowGridLines?this.options.scaleGridLineWidth:0,gridLineColor:this.options.scaleShowGridLines?this.options.scaleGridLineColor:"rgba(0,0,0,0)",padding:this.options.showScale?0:this.options.pointDotRadius+this.options.pointDotStrokeWidth,showLabels:this.options.scaleShowLabels,display:this.options.showScale};this.options.scaleOverride&&e.extend(o,{calculateYRange:e.noop,steps:this.options.scaleSteps,stepValue:this.options.scaleStepWidth,min:this.options.scaleStartValue,max:this.options.scaleStartValue+this.options.scaleSteps*this.options.scaleStepWidth}),this.scale=new i.Scale(o)},addData:function(t,i){e.each(t,function(t,e){this.datasets[e].points.push(new this.PointClass({value:t,label:i,x:this.scale.calculateX(this.scale.valuesCount+1),y:this.scale.endPoint,strokeColor:this.datasets[e].pointStrokeColor,fillColor:this.datasets[e].pointColor}))},this),this.scale.addXLabel(i),this.update()},removeData:function(){this.scale.removeXLabel(),e.each(this.datasets,function(t){t.points.shift()},this),this.update()},reflow:function(){var t=e.extend({height:this.chart.height,width:this.chart.width});this.scale.update(t)},draw:function(t){var i=t||1;this.clear();var s=this.chart.ctx,n=function(t){return null!==t.value},o=function(t,i,s){return e.findNextWhere(i,n,s)||t},a=function(t,i,s){return e.findPreviousWhere(i,n,s)||t};this.scale.draw(i),e.each(this.datasets,function(t){var h=e.where(t.points,n);e.each(t.points,function(t,e){t.hasValue()&&t.transition({y:this.scale.calculateY(t.value),x:this.scale.calculateX(e)},i)},this),this.options.bezierCurve&&e.each(h,function(t,i){var s=i>0&&i<h.length-1?this.options.bezierCurveTension:0;t.controlPoints=e.splineCurve(a(t,h,i),t,o(t,h,i),s),t.controlPoints.outer.y>this.scale.endPoint?t.controlPoints.outer.y=this.scale.endPoint:t.controlPoints.outer.y<this.scale.startPoint&&(t.controlPoints.outer.y=this.scale.startPoint),t.controlPoints.inner.y>this.scale.endPoint?t.controlPoints.inner.y=this.scale.endPoint:t.controlPoints.inner.y<this.scale.startPoint&&(t.controlPoints.inner.y=this.scale.startPoint)},this),s.lineWidth=this.options.datasetStrokeWidth,s.strokeStyle=t.strokeColor,s.beginPath(),e.each(h,function(t,i){if(0===i)s.moveTo(t.x,t.y);else if(this.options.bezierCurve){var e=a(t,h,i);s.bezierCurveTo(e.controlPoints.outer.x,e.controlPoints.outer.y,t.controlPoints.inner.x,t.controlPoints.inner.y,t.x,t.y)}else s.lineTo(t.x,t.y)},this),s.stroke(),this.options.datasetFill&&h.length>0&&(s.lineTo(h[h.length-1].x,this.scale.endPoint),s.lineTo(h[0].x,this.scale.endPoint),s.fillStyle=t.fillColor,s.closePath(),s.fill()),e.each(h,function(t){t.draw()})},this)}})}.call(this),function(){"use strict";var t=this,i=t.Chart,e=i.helpers,s={scaleShowLabelBackdrop:!0,scaleBackdropColor:"rgba(255,255,255,0.75)",scaleBeginAtZero:!0,scaleBackdropPaddingY:2,scaleBackdropPaddingX:2,scaleShowLine:!0,segmentShowStroke:!0,segmentStrokeColor:"#fff",segmentStrokeWidth:2,animationSteps:100,animationEasing:"easeOutBounce",animateRotate:!0,animateScale:!1,legendTemplate:'<ul class="<%=name.toLowerCase()%>-legend"><% for (var i=0; i<segments.length; i++){%><li><span style="background-color:<%=segments[i].fillColor%>"></span><%if(segments[i].label){%><%=segments[i].label%><%}%></li><%}%></ul>'};i.Type.extend({name:"PolarArea",defaults:s,initialize:function(t){this.segments=[],this.SegmentArc=i.Arc.extend({showStroke:this.options.segmentShowStroke,strokeWidth:this.options.segmentStrokeWidth,strokeColor:this.options.segmentStrokeColor,ctx:this.chart.ctx,innerRadius:0,x:this.chart.width/2,y:this.chart.height/2}),this.scale=new i.RadialScale({display:this.options.showScale,fontStyle:this.options.scaleFontStyle,fontSize:this.options.scaleFontSize,fontFamily:this.options.scaleFontFamily,fontColor:this.options.scaleFontColor,showLabels:this.options.scaleShowLabels,showLabelBackdrop:this.options.scaleShowLabelBackdrop,backdropColor:this.options.scaleBackdropColor,backdropPaddingY:this.options.scaleBackdropPaddingY,backdropPaddingX:this.options.scaleBackdropPaddingX,lineWidth:this.options.scaleShowLine?this.options.scaleLineWidth:0,lineColor:this.options.scaleLineColor,lineArc:!0,width:this.chart.width,height:this.chart.height,xCenter:this.chart.width/2,yCenter:this.chart.height/2,ctx:this.chart.ctx,templateString:this.options.scaleLabel,valuesCount:t.length}),this.updateScaleRange(t),this.scale.update(),e.each(t,function(t,i){this.addData(t,i,!0)},this),this.options.showTooltips&&e.bindEvents(this,this.options.tooltipEvents,function(t){var i="mouseout"!==t.type?this.getSegmentsAtEvent(t):[];e.each(this.segments,function(t){t.restore(["fillColor"])}),e.each(i,function(t){t.fillColor=t.highlightColor}),this.showTooltip(i)}),this.render()},getSegmentsAtEvent:function(t){var i=[],s=e.getRelativePosition(t);return e.each(this.segments,function(t){t.inRange(s.x,s.y)&&i.push(t)},this),i},addData:function(t,i,e){var s=i||this.segments.length;this.segments.splice(s,0,new this.SegmentArc({fillColor:t.color,highlightColor:t.highlight||t.color,label:t.label,value:t.value,outerRadius:this.options.animateScale?0:this.scale.calculateCenterOffset(t.value),circumference:this.options.animateRotate?0:this.scale.getCircumference(),startAngle:1.5*Math.PI})),e||(this.reflow(),this.update())},removeData:function(t){var i=e.isNumber(t)?t:this.segments.length-1;this.segments.splice(i,1),this.reflow(),this.update()},calculateTotal:function(t){this.total=0,e.each(t,function(t){this.total+=t.value},this),this.scale.valuesCount=this.segments.length},updateScaleRange:function(t){var i=[];e.each(t,function(t){i.push(t.value)});var s=this.options.scaleOverride?{steps:this.options.scaleSteps,stepValue:this.options.scaleStepWidth,min:this.options.scaleStartValue,max:this.options.scaleStartValue+this.options.scaleSteps*this.options.scaleStepWidth}:e.calculateScaleRange(i,e.min([this.chart.width,this.chart.height])/2,this.options.scaleFontSize,this.options.scaleBeginAtZero,this.options.scaleIntegersOnly);e.extend(this.scale,s,{size:e.min([this.chart.width,this.chart.height]),xCenter:this.chart.width/2,yCenter:this.chart.height/2})},update:function(){this.calculateTotal(this.segments),e.each(this.segments,function(t){t.save()}),this.reflow(),this.render()},reflow:function(){e.extend(this.SegmentArc.prototype,{x:this.chart.width/2,y:this.chart.height/2}),this.updateScaleRange(this.segments),this.scale.update(),e.extend(this.scale,{xCenter:this.chart.width/2,yCenter:this.chart.height/2}),e.each(this.segments,function(t){t.update({outerRadius:this.scale.calculateCenterOffset(t.value)})},this)},draw:function(t){var i=t||1;this.clear(),e.each(this.segments,function(t,e){t.transition({circumference:this.scale.getCircumference(),outerRadius:this.scale.calculateCenterOffset(t.value)},i),t.endAngle=t.startAngle+t.circumference,0===e&&(t.startAngle=1.5*Math.PI),e<this.segments.length-1&&(this.segments[e+1].startAngle=t.endAngle),t.draw()},this),this.scale.draw()}})}.call(this),function(){"use strict";var t=this,i=t.Chart,e=i.helpers;i.Type.extend({name:"Radar",defaults:{scaleShowLine:!0,angleShowLineOut:!0,scaleShowLabels:!1,scaleBeginAtZero:!0,angleLineColor:"rgba(0,0,0,.1)",angleLineWidth:1,pointLabelFontFamily:"'Arial'",pointLabelFontStyle:"normal",pointLabelFontSize:10,pointLabelFontColor:"#666",pointDot:!0,pointDotRadius:3,pointDotStrokeWidth:1,pointHitDetectionRadius:20,datasetStroke:!0,datasetStrokeWidth:2,datasetFill:!0,legendTemplate:'<ul class="<%=name.toLowerCase()%>-legend"><% for (var i=0; i<datasets.length; i++){%><li><span style="background-color:<%=datasets[i].strokeColor%>"></span><%if(datasets[i].label){%><%=datasets[i].label%><%}%></li><%}%></ul>'},initialize:function(t){this.PointClass=i.Point.extend({strokeWidth:this.options.pointDotStrokeWidth,radius:this.options.pointDotRadius,display:this.options.pointDot,hitDetectionRadius:this.options.pointHitDetectionRadius,ctx:this.chart.ctx}),this.datasets=[],this.buildScale(t),this.options.showTooltips&&e.bindEvents(this,this.options.tooltipEvents,function(t){var i="mouseout"!==t.type?this.getPointsAtEvent(t):[];this.eachPoints(function(t){t.restore(["fillColor","strokeColor"])}),e.each(i,function(t){t.fillColor=t.highlightFill,t.strokeColor=t.highlightStroke}),this.showTooltip(i)}),e.each(t.datasets,function(i){var s={label:i.label||null,fillColor:i.fillColor,strokeColor:i.strokeColor,pointColor:i.pointColor,pointStrokeColor:i.pointStrokeColor,points:[]};this.datasets.push(s),e.each(i.data,function(e,n){var o;this.scale.animation||(o=this.scale.getPointPosition(n,this.scale.calculateCenterOffset(e))),s.points.push(new this.PointClass({value:e,label:t.labels[n],datasetLabel:i.label,x:this.options.animation?this.scale.xCenter:o.x,y:this.options.animation?this.scale.yCenter:o.y,strokeColor:i.pointStrokeColor,fillColor:i.pointColor,highlightFill:i.pointHighlightFill||i.pointColor,highlightStroke:i.pointHighlightStroke||i.pointStrokeColor}))},this)},this),this.render()},eachPoints:function(t){e.each(this.datasets,function(i){e.each(i.points,t,this)},this)},getPointsAtEvent:function(t){var i=e.getRelativePosition(t),s=e.getAngleFromPoint({x:this.scale.xCenter,y:this.scale.yCenter},i),n=2*Math.PI/this.scale.valuesCount,o=Math.round((s.angle-1.5*Math.PI)/n),a=[];return(o>=this.scale.valuesCount||0>o)&&(o=0),s.distance<=this.scale.drawingArea&&e.each(this.datasets,function(t){a.push(t.points[o])}),a},buildScale:function(t){this.scale=new i.RadialScale({display:this.options.showScale,fontStyle:this.options.scaleFontStyle,fontSize:this.options.scaleFontSize,fontFamily:this.options.scaleFontFamily,fontColor:this.options.scaleFontColor,showLabels:this.options.scaleShowLabels,showLabelBackdrop:this.options.scaleShowLabelBackdrop,backdropColor:this.options.scaleBackdropColor,backdropPaddingY:this.options.scaleBackdropPaddingY,backdropPaddingX:this.options.scaleBackdropPaddingX,lineWidth:this.options.scaleShowLine?this.options.scaleLineWidth:0,lineColor:this.options.scaleLineColor,angleLineColor:this.options.angleLineColor,angleLineWidth:this.options.angleShowLineOut?this.options.angleLineWidth:0,pointLabelFontColor:this.options.pointLabelFontColor,pointLabelFontSize:this.options.pointLabelFontSize,pointLabelFontFamily:this.options.pointLabelFontFamily,pointLabelFontStyle:this.options.pointLabelFontStyle,height:this.chart.height,width:this.chart.width,xCenter:this.chart.width/2,yCenter:this.chart.height/2,ctx:this.chart.ctx,templateString:this.options.scaleLabel,labels:t.labels,valuesCount:t.datasets[0].data.length}),this.scale.setScaleSize(),this.updateScaleRange(t.datasets),this.scale.buildYLabels()},updateScaleRange:function(t){var i=function(){var i=[];return e.each(t,function(t){t.data?i=i.concat(t.data):e.each(t.points,function(t){i.push(t.value)})}),i}(),s=this.options.scaleOverride?{steps:this.options.scaleSteps,stepValue:this.options.scaleStepWidth,min:this.options.scaleStartValue,max:this.options.scaleStartValue+this.options.scaleSteps*this.options.scaleStepWidth}:e.calculateScaleRange(i,e.min([this.chart.width,this.chart.height])/2,this.options.scaleFontSize,this.options.scaleBeginAtZero,this.options.scaleIntegersOnly);e.extend(this.scale,s)},addData:function(t,i){this.scale.valuesCount++,e.each(t,function(t,e){var s=this.scale.getPointPosition(this.scale.valuesCount,this.scale.calculateCenterOffset(t));this.datasets[e].points.push(new this.PointClass({value:t,label:i,x:s.x,y:s.y,strokeColor:this.datasets[e].pointStrokeColor,fillColor:this.datasets[e].pointColor}))},this),this.scale.labels.push(i),this.reflow(),this.update()},removeData:function(){this.scale.valuesCount--,this.scale.labels.shift(),e.each(this.datasets,function(t){t.points.shift()},this),this.reflow(),this.update()},update:function(){this.eachPoints(function(t){t.save()}),this.reflow(),this.render()},reflow:function(){e.extend(this.scale,{width:this.chart.width,height:this.chart.height,size:e.min([this.chart.width,this.chart.height]),xCenter:this.chart.width/2,yCenter:this.chart.height/2}),this.updateScaleRange(this.datasets),this.scale.setScaleSize(),this.scale.buildYLabels()},draw:function(t){var i=t||1,s=this.chart.ctx;this.clear(),this.scale.draw(),e.each(this.datasets,function(t){e.each(t.points,function(t,e){t.hasValue()&&t.transition(this.scale.getPointPosition(e,this.scale.calculateCenterOffset(t.value)),i)},this),s.lineWidth=this.options.datasetStrokeWidth,s.strokeStyle=t.strokeColor,s.beginPath(),e.each(t.points,function(t,i){0===i?s.moveTo(t.x,t.y):s.lineTo(t.x,t.y)},this),s.closePath(),s.stroke(),s.fillStyle=t.fillColor,s.fill(),e.each(t.points,function(t){t.hasValue()&&t.draw()})},this)}})}.call(this);
/*----------------------------------------------------------------------------------------------------*/
/*
 * Angular-chart
 */
!function(t){"use strict";"object"==typeof exports?module.exports=t(require("angular"),require("Chart.js")):"function"==typeof define&&define.amd?define(["angular","chart"],t):t(angular,Chart)}(function(t,e){"use strict";function n(){var n={},r={Chart:e,getOptions:function(e){var r=e&&n[e]||{};return t.extend({},n,r)}};this.setOptions=function(e,r){return r?(n[e]=t.extend(n[e]||{},r),void 0):(r=e,n=t.extend(n,r),void 0)},this.$get=function(){return r}}function r(n,r){function o(t,e){return t&&e&&t.length&&e.length?Array.isArray(t[0])?t.length===e.length&&t.every(function(t,n){return t.length===e[n].length}):e.reduce(i,0)>0?t.length===e.length:!1:!1}function i(t,e){return t+e}function c(e,n,r,a){var o=null;return function(i){var c=n.getPointsAtEvent||n.getBarsAtEvent||n.getSegmentsAtEvent;if(c){var l=c.call(n,i);(a===!1||t.equals(o,l)===!1)&&(o=l,e[r](l,i),e.$apply())}}}function l(r,a){for(var o=t.copy(a.colours||n.getOptions(r).colours||e.defaults.global.colours);o.length<a.data.length;)o.push(a.getColour());return o.map(u)}function u(t){return"object"==typeof t&&null!==t?t:"string"==typeof t&&"#"===t[0]?f(g(t.substr(1))):s()}function s(){var t=[h(0,255),h(0,255),h(0,255)];return f(t)}function f(t){return{fillColor:d(t,.2),strokeColor:d(t,1),pointColor:d(t,1),pointStrokeColor:"#fff",pointHighlightFill:"#fff",pointHighlightStroke:d(t,.8)}}function h(t,e){return Math.floor(Math.random()*(e-t+1))+t}function d(t,e){return a?"rgb("+t.join(",")+")":"rgba("+t.concat(e).join(",")+")"}function g(t){var e=parseInt(t,16),n=e>>16&255,r=e>>8&255,a=255&e;return[n,r,a]}function p(e,n,r,a){return{labels:e,datasets:n.map(function(e,n){return t.extend({},a[n],{label:r[n],data:e})})}}function v(e,n,r){return e.map(function(e,a){return t.extend({},r[a],{label:e,value:n[a],color:r[a].strokeColor,highlight:r[a].pointHighlightStroke})})}function y(t,e){var n=t.parent(),r=n.find("chart-legend"),a="<chart-legend>"+e.generateLegend()+"</chart-legend>";r.length?r.replaceWith(a):n.append(a)}function C(t,e,n,r){Array.isArray(n.data[0])?t.datasets.forEach(function(t,n){(t.points||t.bars).forEach(function(t,r){t.value=e[n][r]})}):t.segments.forEach(function(t,n){t.value=e[n]}),t.update(),n.$emit("update",t),n.legend&&"false"!==n.legend&&y(r,t)}function b(t){return!t||Array.isArray(t)&&!t.length||"object"==typeof t&&!Object.keys(t).length}function m(r,a){var o=t.extend({},e.defaults.global,n.getOptions(r),a.options);return o.responsive}return function(e){return{restrict:"CA",scope:{data:"=?",labels:"=?",options:"=?",series:"=?",colours:"=?",getColour:"=?",chartType:"=",legend:"@",click:"=?",hover:"=?",chartData:"=?",chartLabels:"=?",chartOptions:"=?",chartSeries:"=?",chartColours:"=?",chartLegend:"@",chartClick:"=?",chartHover:"=?"},link:function(i,u){function f(t,e){i.$watch(t,function(t){"undefined"!=typeof t&&(i[e]=t)})}function h(n,r){if(!b(n)&&!t.equals(n,r)){var a=e||i.chartType;a&&(w&&w.destroy(),d(a))}}function d(e){if(m(e,i)&&0===u[0].clientHeight&&0===A.clientHeight)return r(function(){d(e)},50,!1);if(i.data&&i.data.length){i.getColour="function"==typeof i.getColour?i.getColour:s,i.colours=l(e,i);var a=u[0],o=a.getContext("2d"),f=Array.isArray(i.data[0])?p(i.labels,i.data,i.series||[],i.colours):v(i.labels,i.data,i.colours),h=t.extend({},n.getOptions(e),i.options);w=new n.Chart(o)[e](f,h),i.$emit("create",w),a.onclick=i.click?c(i,w,"click",!1):t.noop,a.onmousemove=i.hover?c(i,w,"hover",!0):t.noop,i.legend&&"false"!==i.legend&&y(u,w)}}function g(t){if("undefined"!=typeof console&&"test"!==n.getOptions().env){var e="function"==typeof console.warn?console.warn:console.log;i[t]&&e.call(console,'"%s" is deprecated and will be removed in a future version. Please use "chart-%s" instead.',t,t)}}var w,A=document.createElement("div");A.className="chart-container",u.replaceWith(A),A.appendChild(u[0]),a&&window.G_vmlCanvasManager.initElement(u[0]),["data","labels","options","series","colours","legend","click","hover"].forEach(g),f("chartData","data"),f("chartLabels","labels"),f("chartOptions","options"),f("chartSeries","series"),f("chartColours","colours"),f("chartLegend","legend"),f("chartClick","click"),f("chartHover","hover"),i.$watch("data",function(t,n){if(t&&t.length&&(!Array.isArray(t[0])||t[0].length)){var r=e||i.chartType;if(r){if(w){if(o(t,n))return C(w,t,i,u);w.destroy()}d(r)}}},!0),i.$watch("series",h,!0),i.$watch("labels",h,!0),i.$watch("options",h,!0),i.$watch("colours",h,!0),i.$watch("chartType",function(e,n){b(e)||t.equals(e,n)||(w&&w.destroy(),d(e))}),i.$on("$destroy",function(){w&&w.destroy()})}}}}e.defaults.global.responsive=!0,e.defaults.global.multiTooltipTemplate="<%if (datasetLabel){%><%=datasetLabel%>: <%}%><%= value %>",e.defaults.global.colours=["#97BBCD","#DCDCDC","#F7464A","#46BFBD","#FDB45C","#949FB1","#4D5360"];var a="object"==typeof window.G_vmlCanvasManager&&null!==window.G_vmlCanvasManager&&"function"==typeof window.G_vmlCanvasManager.initElement;return a&&(e.defaults.global.animation=!1),t.module("chart.js",[]).provider("ChartJs",n).factory("ChartJsFactory",["ChartJs","$timeout",r]).directive("chartBase",["ChartJsFactory",function(t){return new t}]).directive("chartLine",["ChartJsFactory",function(t){return new t("Line")}]).directive("chartBar",["ChartJsFactory",function(t){return new t("Bar")}]).directive("chartRadar",["ChartJsFactory",function(t){return new t("Radar")}]).directive("chartDoughnut",["ChartJsFactory",function(t){return new t("Doughnut")}]).directive("chartPie",["ChartJsFactory",function(t){return new t("Pie")}]).directive("chartPolarArea",["ChartJsFactory",function(t){return new t("PolarArea")}])});
/*----------------------------------------------------------------------------------------------------*/
/*
 * spinner
 */
!function(a,b){"object"==typeof module&&module.exports?module.exports=b():"function"==typeof define&&define.amd?define(b):a.Spinner=b()}(this,function(){"use strict";function a(a,b){var c,d=document.createElement(a||"div");for(c in b)d[c]=b[c];return d}function b(a){for(var b=1,c=arguments.length;c>b;b++)a.appendChild(arguments[b]);return a}function c(a,b,c,d){var e=["opacity",b,~~(100*a),c,d].join("-"),f=.01+c/d*100,g=Math.max(1-(1-a)/b*(100-f),a),h=j.substring(0,j.indexOf("Animation")).toLowerCase(),i=h&&"-"+h+"-"||"";return m[e]||(k.insertRule("@"+i+"keyframes "+e+"{0%{opacity:"+g+"}"+f+"%{opacity:"+a+"}"+(f+.01)+"%{opacity:1}"+(f+b)%100+"%{opacity:"+a+"}100%{opacity:"+g+"}}",k.cssRules.length),m[e]=1),e}function d(a,b){var c,d,e=a.style;if(b=b.charAt(0).toUpperCase()+b.slice(1),void 0!==e[b])return b;for(d=0;d<l.length;d++)if(c=l[d]+b,void 0!==e[c])return c}function e(a,b){for(var c in b)a.style[d(a,c)||c]=b[c];return a}function f(a){for(var b=1;b<arguments.length;b++){var c=arguments[b];for(var d in c)void 0===a[d]&&(a[d]=c[d])}return a}function g(a,b){return"string"==typeof a?a:a[b%a.length]}function h(a){this.opts=f(a||{},h.defaults,n)}function i(){function c(b,c){return a("<"+b+' xmlns="urn:schemas-microsoft.com:vml" class="spin-vml">',c)}k.addRule(".spin-vml","behavior:url(#default#VML)"),h.prototype.lines=function(a,d){function f(){return e(c("group",{coordsize:k+" "+k,coordorigin:-j+" "+-j}),{width:k,height:k})}function h(a,h,i){b(m,b(e(f(),{rotation:360/d.lines*a+"deg",left:~~h}),b(e(c("roundrect",{arcsize:d.corners}),{width:j,height:d.scale*d.width,left:d.scale*d.radius,top:-d.scale*d.width>>1,filter:i}),c("fill",{color:g(d.color,a),opacity:d.opacity}),c("stroke",{opacity:0}))))}var i,j=d.scale*(d.length+d.width),k=2*d.scale*j,l=-(d.width+d.length)*d.scale*2+"px",m=e(f(),{position:"absolute",top:l,left:l});if(d.shadow)for(i=1;i<=d.lines;i++)h(i,-2,"progid:DXImageTransform.Microsoft.Blur(pixelradius=2,makeshadow=1,shadowopacity=.3)");for(i=1;i<=d.lines;i++)h(i);return b(a,m)},h.prototype.opacity=function(a,b,c,d){var e=a.firstChild;d=d.shadow&&d.lines||0,e&&b+d<e.childNodes.length&&(e=e.childNodes[b+d],e=e&&e.firstChild,e=e&&e.firstChild,e&&(e.opacity=c))}}var j,k,l=["webkit","Moz","ms","O"],m={},n={lines:12,length:7,width:5,radius:10,scale:1,corners:1,color:"#000",opacity:.25,rotate:0,direction:1,speed:1,trail:100,fps:20,zIndex:2e9,className:"spinner",top:"50%",left:"50%",shadow:!1,hwaccel:!1,position:"absolute"};if(h.defaults={},f(h.prototype,{spin:function(b){this.stop();var c=this,d=c.opts,f=c.el=a(null,{className:d.className});if(e(f,{position:d.position,width:0,zIndex:d.zIndex,left:d.left,top:d.top}),b&&b.insertBefore(f,b.firstChild||null),f.setAttribute("role","progressbar"),c.lines(f,c.opts),!j){var g,h=0,i=(d.lines-1)*(1-d.direction)/2,k=d.fps,l=k/d.speed,m=(1-d.opacity)/(l*d.trail/100),n=l/d.lines;!function o(){h++;for(var a=0;a<d.lines;a++)g=Math.max(1-(h+(d.lines-a)*n)%l*m,d.opacity),c.opacity(f,a*d.direction+i,g,d);c.timeout=c.el&&setTimeout(o,~~(1e3/k))}()}return c},stop:function(){var a=this.el;return a&&(clearTimeout(this.timeout),a.parentNode&&a.parentNode.removeChild(a),this.el=void 0),this},lines:function(d,f){function h(b,c){return e(a(),{position:"absolute",width:f.scale*(f.length+f.width)+"px",height:f.scale*f.width+"px",background:b,boxShadow:c,transformOrigin:"left",transform:"rotate("+~~(360/f.lines*k+f.rotate)+"deg) translate("+f.scale*f.radius+"px,0)",borderRadius:(f.corners*f.scale*f.width>>1)+"px"})}for(var i,k=0,l=(f.lines-1)*(1-f.direction)/2;k<f.lines;k++)i=e(a(),{position:"absolute",top:1+~(f.scale*f.width/2)+"px",transform:f.hwaccel?"translate3d(0,0,0)":"",opacity:f.opacity,animation:j&&c(f.opacity,f.trail,l+k*f.direction,f.lines)+" "+1/f.speed+"s linear infinite"}),f.shadow&&b(i,e(h("#000","0 0 4px #000"),{top:"2px"})),b(d,b(i,h(g(f.color,k),"0 0 1px rgba(0,0,0,.1)")));return d},opacity:function(a,b,c){b<a.childNodes.length&&(a.childNodes[b].style.opacity=c)}}),"undefined"!=typeof document){k=function(){var c=a("style",{type:"text/css"});return b(document.getElementsByTagName("head")[0],c),c.sheet||c.styleSheet}();var o=e(a("group"),{behavior:"url(#default#VML)"});!d(o,"transform")&&o.adj?i():j=d(o,"animation")}return h});
"format amd";!function(a){"use strict";function b(a,b){return a.module("angularSpinner",[]).constant("SpinJSSpinner",b).provider("usSpinnerConfig",function(){var a={},b={};return{setDefaults:function(b){a=b||a},setTheme:function(a,c){b[a]=c},$get:function(){return{config:a,themes:b}}}}).factory("usSpinnerService",["$rootScope",function(a){var b={};return b.spin=function(b){a.$broadcast("us-spinner:spin",b)},b.stop=function(b){a.$broadcast("us-spinner:stop",b)},b}]).directive("usSpinner",["SpinJSSpinner","usSpinnerConfig",function(b,c){return{scope:!0,link:function(d,e,f){function g(){d.spinner&&d.spinner.stop()}d.spinner=null,d.key=a.isDefined(f.spinnerKey)?f.spinnerKey:!1,d.startActive=a.isDefined(f.spinnerStartActive)?d.$eval(f.spinnerStartActive):d.key?!1:!0,d.spin=function(){d.spinner&&d.spinner.spin(e[0])},d.stop=function(){d.startActive=!1,g()},d.$watch(f.usSpinner,function(h){g(),h=a.extend(c.config,c.themes[f.spinnerTheme],h),d.spinner=new b(h),d.key&&!d.startActive||f.spinnerOn||d.spinner.spin(e[0])},!0),f.spinnerOn&&d.$watch(f.spinnerOn,function(a){a?d.spin():d.stop()}),d.$on("us-spinner:spin",function(a,b){b===d.key&&d.spin()}),d.$on("us-spinner:stop",function(a,b){b===d.key&&d.stop()}),d.$on("$destroy",function(){d.stop(),d.spinner=null})}}}])}"object"==typeof module&&module.exports?module.exports=b(require("angular"),require("spin.js")):"function"==typeof define&&define.amd?define(["angular","spin"],b):b(a.angular,a.Spinner)}(this);
