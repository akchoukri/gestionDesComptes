(function () {


var app = angular.module("myApp");
//service pour gerer compte
app.service("compteDataService",function ($http,$location) {

	this.modeUpdate = 0;
  
    // recuperer la liste des comptes
    this.getPageComptes = function(motCle,currentPage,size){

    var promise1    =   $http({
        method : "GET",
        url : "http://localhost:8080/comptes?mc="+motCle+"&page="+currentPage+"&size="+size
    });


    var promise2 = promise1.then(function mySuccess(response) {

        return response.data;

    }, function myError(response) {

    });

    return promise2;
    };

    // ajouter un nouveau compte 
    this.newCompte = function(compte,type){

if(type === 'compteCourant')
{
	$http.post("http://localhost:8080/comptes/compteCourant",compte)

            .then(function mySuccess(response) {
            }, function myError(response) {
                console.log( response.statusText);    	
            });
}
else
	{
	$http.post("http://localhost:8080/comptes/compteEpargne",compte)

    .then(function mySuccess(response) {
    }, function myError(response) {
        console.log( response.statusText);    	
    });
	}
    };

    // mise a jour d'un compte

    this.updateCompte = function(compte){


        $http.put("http://localhost:8080/comptes/"+compte.idCompte,compte)
            .then(function mySuccess(response) {
               // alert("working");
                
            }, function myError(response) {
                $scope.myWelcome = response.statusText;
               
            });
    };

    // supprimer un compte


    this.deleteCompte = function(id){


        $http.delete("http://localhost:8080/comptes/"+id)
            .then(function mySuccess(response) {
            	//console.log("la suppression working");
            	//console.log(response.data);
            }, function myError(response) {
                $scope.myWelcome = response.statusText;
                alert("not working");
            });
    };

    // recuperer un compte

    this.getCompte = function (id) {
     


        var promise1    =   $http({
            method : "GET",
            url: "http://localhost:8080/comptes/"+id
        });

        
        var promise2 = promise1.then(function mySuccess(response) {

            return response.data;

        });
      
        return promise2;
    }
    // recuperer un compte selon user role

    this.getCompteByUser = function (codeCompte,username) {
     


        var promise1    =   $http({
            method : "GET",
            url: "http://localhost:8080/users/"+username+"/comptes/"+codeCompte
        });

        
        var promise2 = promise1.then(function mySuccess(response) {

            return response.data;

        });
      
        return promise2;
    }
    // recuperer des comptes
    
//    this.getComptes = function (){
//    	var promise1    =   $http({
//            method : "GET",
//            url: "http://localhost:8080/comptes"
//        });
//
//        
//        var promise2 = promise1.then(function mySuccess(response) {
//
//            return response.data;
//
//        }, function myError(response) {
//              
//        });
//      
//        return promise2;
//    }
    
    //ajouter une operation
    this.newOperation = function(operation){


    	var promise1    = $http.post("http://localhost:8080/operation",operation)

    	            .then(function mySuccess(response) {
    	            	 return (response.data);
    	            });
    	return promise1;
    	    };

})
})();