(function () {


var app = angular.module("myApp");
app.controller("clientNewCtrl",newclientCtrl);
//controller pour ajouter un cleint
function newclientCtrl($scope,$http,clientDataService,$location,$state,fileUpload) {

    $scope.imageSrc ;

    $scope.client = null;
    $scope.user =null;
    clientDataService.modeCr=1;
    $scope.mode=clientDataService.modeCr;


    //fonction pour ajouter un client
    $scope.save= function () {
    	$scope.message='';
    	$scope.user.client = $scope.client;
    	$scope.client.telClient=Number($scope.client.telClient);
    	console.log(" imageSrc")
    	var str = $scope.imageSrc.name;
    	var res = str.slice(str.indexOf("."));
    	$scope.client.imageClient=$scope.client.nomClient+res
    	console.log($scope.client)
    	console.log("**************")
    	console.dir( $scope.imageSrc.name);
    	$scope.uploadfile()
    	clientDataService.newUser( $scope.user) .then(function mySuccess(response) {
    		clientDataService.newClient( $scope.client);
       	 $scope.reset();
     	// redirection a la vue client et refresh la vue
     	 $state.go("clients", {}, {reload: true});
     	 //$location.path("clients");
        }, function myError(response) {
        	$scope.message = response.data.message;
        });


    };

    //reset de la formulaire
    $scope.reset = function () {
        $scope.client = null;
        $scope.user =null;
    }

  //upload file
    $scope.uploadfile = function() {
            var file = $scope.imageSrc;
             console.dir( $scope.imageSrc);
             var uploadUrl = '/upload';
             fileUpload.uploadFileToUrl(file,uploadUrl); 
    }; 
}

})();