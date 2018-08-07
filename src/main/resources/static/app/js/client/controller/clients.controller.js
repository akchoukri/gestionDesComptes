(function () {


var app = angular.module("myApp");
app.controller("listClientContl",clientCtrl);

//controller pour client
function clientCtrl($scope,clientDataService,$state,$rootScope) {
	 //objet pour  tester les directives
	$scope.naomi  = {
			    name: 'Igor',
			    address: '123 Somewhere'
			    
			   
			  };
	
	$scope.pageClient = {};
    $scope.motCle ="" ;
    $scope.currentPage = 0;
    $scope.size=2;
    $scope.totalePages=0;
    $scope.pages =[];
console.log($rootScope.user);
    //fonction qui permet rcuperer les client au demarage de la vue 
	  $scope.init = function(){
		  
		  //faire appel au service client pour recuperer les clients
		  clientDataService.getClients($scope.motCle,$scope.currentPage,$scope.size).then(function (data) {
	          
			  $scope.pageClient=data;
	            $scope.totalePages= data.totalPages;
	            $scope.pages = new Array(data.totalPages);
			  
			 // console.log($scope.pageClient);

			 
	        });
	    }
	  //fonction pour chercher un cllient avec l'evenemnt key up
	  $scope.keyUp = function (event) {
	        //alert(event.target.value);
	        $scope.motcle=event.target.value;
	       // console.log($scope.motcle);
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
	    
	    // mise a jour de client
	    $scope.updateClient= function (client) {
	    	//clientDataService.updateClient( $scope.client);
	    	 $scope.client=client;
	    	//console.log("mode update : "+clientDataService.modeUpdate);
	    	//console.log($scope.client);
	    	
	    }
	    // suppression d'un client
	    $scope.supprimerClient = function (c) {
	    	
	    	   
    	  var item = $scope.pageClient.content[c];
	    //	$scope.pageClient.content.splice(c,1);
	    //	console.log(item);
    	  
	    clientDataService.deleteClient(item.codeClient);
	    $state.reload();
	        //$window.location.reload();


	    }
	    
	    //fonction pour test directives
	    $scope.teestFctDirec = function (c) {
	    	
	    	alert("passer fonction marche good!");
		    }
	    

}

})();