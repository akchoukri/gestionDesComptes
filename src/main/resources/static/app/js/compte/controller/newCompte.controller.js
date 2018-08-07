(function() {

	var app = angular.module("myApp");
	app.controller("compteNewCtrl", newcompteCtrl);
// controller pour ajouter client
	function newcompteCtrl($scope, $http, compteDataService, clientDataService,
			$state) {

		$scope.client = null;
		$scope.compte = null;
		$scope.clients = {};
		$scope.filterClients = {};
		$scope.compteType = 'compteCourant';
		$scope.taux = 0;
		$scope.decouvert = 0;

		clientDataService.allClients().then(function(data) {
			$scope.clients = data;

		});

		// ajouter un compte
		$scope.save = function() {

			if ($scope.compteType === 'compteCourant')
				{
				$scope.compte.decouvert = $scope.decouvert;
				$scope.compte.typeCompte=$scope.compteType;
				}
			else
				{
				$scope.compte.taux = $scope.taux;
				$scope.compte.typeCompte=$scope.compteType;
				}

		$scope.compte.client=$scope.client

		compteDataService.newCompte($scope.compte,$scope.compteType);
		//	console.log($scope.client);
		
		 $state.go("comptes", {}, {reload: true});
		};

		$scope.reset = function() {
			$scope.compte = null;

		}
		
		$scope.getClient = function() {
			$scope.compte = null;
			
		}

		$scope.autoComplete = function(event) {
			$scope.hidethis = false;
			var output = [];
			angular.forEach($scope.clients, function(listObj) {

				if (listObj.nomClient.indexOf(event.target.value) >= 0) {
					output.push(listObj);

				}
				$scope.filterClients = output;
			});
			if (event.target.value == "")
				$scope.hidethis = true;

		}
		$scope.filtertextbox = function(obClient) {
			$scope.client = obClient;
			$scope.nomClient=$scope.client.nomClient;
//			clientDataService.getClientByName($scope.nomClient).then(
//					function(data) {
//						$scope.client = data;
//
//					});
			$scope.hidethis = true;
		}
	}

})();