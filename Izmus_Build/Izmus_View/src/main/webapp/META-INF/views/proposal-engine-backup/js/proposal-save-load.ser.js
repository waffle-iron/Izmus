angular.module('proposalEngine').factory(
				'saveProposal',
				[
						'$q',
						'$http',
						'$timeout',
						function($q, $http, $timeout) {
							return function(models, proposalName) {
								return $q(function(resolve, reject) {
									$http(
											{
												method : 'POST',
												url : '/api/Proposal/SaveProposal',
												headers: {
											        'Content-Type': "application/x-www-form-urlencoded; charset=UTF-8",
											        'Upgrade-Insecure-Requests': "1",
											        'X-CSRF-TOKEN': $('input[name="_csrf"]').val()
											    },
											    data : $.param({
											    	models: JSON.stringify(models),
											    	proposalName: proposalName
											    })
											}).then(
											function successCallback(response) {
												resolve(response.data);
											},
											function errorCallback(response) {
												reject(response.data);
											});
								})
							}
						} ]);
angular.module('proposalEngine')
.factory(
		'loadProposal',
		[
				'$q',
				'$http',
				'$timeout',
				function($q, $http, $timeout) {
					return function(proposalId) {
						return $q(function(resolve, reject) {
							$http(
									{
										method : 'GET',
										url : '/api/Proposal/LoadProposal?proposalId=' + proposalId,
									}).then(
									function successCallback(response) {
										if (response.data){
											resolve(response.data);
										}
										else {
											reject();
										}
									},
									function errorCallback(response) {
										reject();
									});
						})
					}
				} ]);