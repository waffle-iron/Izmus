angular.module('izmusLandingPageApp').directive('locationMap', [function() {
	return {
		restrict : 'E',
		templateUrl : '/views-public/landing-page/templates/location-map.html',
		scope :{
			
		},
		controller : ['$scope',function($scope) {
			
		}],
		link : function(scope, elem, attr) {
			scope.address = [1.3123115737363895, 103.86287927627563];
			/*----------------------------------------------------------------------------------------------------*/
			var mymap = L.map('mapid').setView(scope.address, 15);
			L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpandmbXliNDBjZWd2M2x6bDk3c2ZtOTkifQ._QA7i5Mpkd_m30IGElHziw', {
				maxZoom: 18,
				attribution: '',
				id: 'mapbox.streets'
			}).addTo(mymap);
			/*----------------------------------------------------------------------------------------------------*/
			var marker = L.marker(scope.address);
			marker.addTo(mymap);
		}
	}
} ]);