(function () {


var app = angular.module("myApp");
app.controller("contacDeCtrl",contacDetailCtrl);

//controller pour les detail contact
function contacDetailCtrl($scope,contactDataService,$location,$stateParams) {

    $scope.contact = null;
   
    // faire appel au service contact pour recuperer la liste des contact
    contactDataService.getContact($stateParams.id)
        .then(function (data) {
            $scope.contact = data;
          
        })
  

        //fonction qui permet de supprimer un conatct
    $scope.supC= function () {
        contactDataService.deleteContact($scope.contact.id);
        $location.path("home");
    }
    
 
 

    
}

})();