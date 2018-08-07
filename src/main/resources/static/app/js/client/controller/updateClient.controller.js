(function () {


var app = angular.module("myApp");
app.controller("clientUpdateCtrl",updateClientCtrl);

//controller pour mis a jour client
function updateClientCtrl($scope,clientDataService,$stateParams,$location,$state) {


	$scope.client=null;
	
	$scope.user=null;
	clientDataService.modeUpdate=1;
	
	// faire appel service pour recuperer un client en precisant id
	clientDataService.getUserByClient($stateParams.id)
    .then(function (data) {
    	$scope.user = data;
    	$scope.client = $scope.user.client;
     console.log(data)
    })
   

    //mettre a jour client
    $scope.updateClient= function () {
		console.log($scope.client)
    clientDataService.updateClient($scope.client);
    clientDataService.updateUser($scope.user);
    
    	$state.go("clients", {}, {reload: true});
    };
    
    //annuler la mise a jour et redirection a la vue clients
    $scope.annulerUpdate=function () {
    	$location.path("clients");
    }



    
}

})();