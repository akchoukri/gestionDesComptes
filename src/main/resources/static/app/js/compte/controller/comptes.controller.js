(function () {


	var app = angular.module("myApp");
app.controller("ComptesContl",comptesCtrl);

//controller pour compte
function comptesCtrl($scope,compteDataService,$state,AuthService,$interval) {
	console.log(AuthService)
	 $scope.customer = {
			    name: 'Naomi',
			    address: '1600 Amphitheatre'
			  };
	 
	$scope.pageCompte = {};
    $scope.codeCompte ="" ;
    $scope.currentPage = 0;
    $scope.size=2;
    $scope.totalePages=0;
    $scope.pages =[];
    $scope.compte =null;
    $scope.operation =null;
    $scope.codeCompte ="";
    $scope.typeCompte ="";
    $scope.operations ={};
    $scope.displayForm =false;
    $scope.typeOperation="versement";
    $scope.role=AuthService.user.data.principal.role
    $scope.messageOp ='';
    //initialisation de la liste des comptes au demarage de la vue
	 $scope.init = function(){
		  
		  compteDataService.getPageComptes($scope.codeCompte,$scope.currentPage,$scope.size).then(function (data) {
	            $scope.pageCompte=data;
	            $scope.totalePages= data.totalPages;
	            $scope.pages = new Array(data.totalPages);
			 
	        });
		  
//    compteDataService.getComptes().then(function (data){
//    	$scope.pageCompte=data;
    	
  //  });
		  
		  
	    }
	 // chercher un compte par son code avec event keyup
	  $scope.keyUp = function (event) {
	        //alert(event.target.value);
	        $scope.codeCompte=event.target.value;
	        //console.log($scope.codeCompte);
	        $scope.compte=compteDataService.getCompte($scope.codeCompte);
	        //console.log($scope.compte);
	        $scope.init();
	        
	    }
	  //fonction permet d'incrementer les numero de la page
	  $scope.gotonext= function(){

	        if($scope.currentPage == $scope.totalePages-1){

	            var d=document.getElementById("linknext");
	            d.className="disabled";

	        }
	        else
	            $scope.currentPage=$scope.currentPage+1;
	        $scope.init();
	    }
	//désincrémenter  les numero de la page
	    $scope.gotoprevious=function(){



	        if ($scope.currentPage!=0)
	            $scope.currentPage=$scope.currentPage-1;

	        $scope.init();
	    }
	  //acceder a une page
	    $scope.gotopage=function (p) {

	        $scope.currentPage=p;
	        $scope.init();
	    }
	    
	    // recuperer un compte
	    $scope.getCompte= function () {
	    	//getCompteByUser
	    	console.log(AuthService.user.data.principal.username)
	    	if($scope.codeCompte!="")
	    	 compteDataService.getCompteByUser($scope.codeCompte,AuthService.user.data.principal.username).then(function (data){
	    		 $scope.messageOp='';
	    		 $scope.compte=data;
	    		 $scope.operations = data.operations
	    		 $scope.displayForm=true;
//	    		 console.log($scope.operations);
	    	    }).catch(function (err) {
	    	    	$scope.messageOp = err.data.message;  
	    	    	 $scope.compte=null;
	    	    	 $scope.displayForm=false;
	            });
	    	else
	    		 $scope.displayForm=false;
	    	 
	    }
	    
	    // ajouter une operation
         $scope.saveOperation = function () {
			$scope.operation.cp1=$scope.compte.codeCompte;
			$scope.operation.cp2=$scope.cp2;
			$scope.operation.typeOperation=$scope.typeOperation;
			$scope.messageOp ='';
			compteDataService.newOperation($scope.operation).then(function (data){
				 
				$scope.compte=data;
				$scope.resetOperation();
//				console.log(data);
				 
		    }, function myError(response) {
		    	console.log( response.data.message);
		    	$scope.messageOp = response.data.message;
		    	$scope.resetOperation();
                    	
            });
        	 
			
		}
	    
         //reset de formulaire operation
         $scope.resetOperation = function (){
        	 $scope.operation.montant=null;
        	 $scope.cp2=null;
        	 $scope.typeOperation="versement";
         }
     $scope.confOperation = function (){
		if(AuthService.user.data.principal.role=="USER")
    	 {
			$scope.typeOperation="virement";
    	 }
	}
}



})();