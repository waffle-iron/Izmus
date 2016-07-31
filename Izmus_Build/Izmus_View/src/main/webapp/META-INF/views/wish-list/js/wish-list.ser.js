/*----------------------------------------------------------------------------------------------------*/
angular.module('wishListApp').factory('loadAllWishlist',
		[ '$q', '$http', function($q, $http) {
			return function(pageNumber, pageSize) {
				return $q(function(resolve, reject) {
					$http({
						method : 'GET',
						url : '/api/Cart/PagedWishlist',
						params: {
							pageNumber: pageNumber,
							pageSize: pageSize
							},
					}).then(function successCallback(response) {
						if (response.data) {
							resolve(response.data);
						} else {
							reject();
						}
					}, function errorCallback(response) {
						reject();
					});
				})
			}
		} ]);