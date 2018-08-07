angular
		.module('myApp')
		.controller(
				'LoginController',
				function($http, $scope, $state, AuthService, $rootScope) {

					// method  login
					$scope.login = function() {

						// crypter user name et password avec base64 encode
						var base64Credential = btoa($scope.username + ':'
								+ $scope.password);

						
						// faire appeler la requete pour recuperer les informations user
						$http
								.get(
										'user',
										{
											headers : {
												//ajout de l'autorisation dans header
												'Authorization' : 'Basic '
														+ base64Credential
											}
										})
								.then(
										function(res) {
											$scope.password = null;
											console.log(res.data.authenticated)
											if (res.data.authenticated) {

												$scope.message = '';
												// mettre le meme header pour ts
												//les requetes appeler de l'appliation
												$http.defaults.headers.common['Authorization'] = 'Basic '
														+ base64Credential;
												AuthService.user = res;
												
												console.log(AuthService.user);
												$rootScope
														.$broadcast('LoginSuccessful');
												$rootScope.user=AuthService.user;
											//	$scope.message = 'Login successful';
												$state.go('operations');
											} else {
												$scope.message = 'error Authetication';

											}
										}).catch(function (err) {
											$scope.message = 'votre username ou votre mot passe est incorrecte';        
						                });
					};
				});
