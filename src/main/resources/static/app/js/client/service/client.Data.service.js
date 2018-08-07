(function () {


var app = angular.module("myApp");

// service pour gerer CRUD de cleint
app.service("clientDataService",function ($http,$location) {

	this.modeUpdate = 0;
  
    // recuperer la liste des clients
    this.getClients = function(motCle,currentPage,size){

    var promise1    =   $http({
        method : "GET",
        url : "http://localhost:8080/clients?mc="+motCle+"&page="+currentPage+"&size="+size
    });

    var promise2 = promise1.then(function mySuccess(response) {

        return response.data;

    }, function myError(response) {

    });

    return promise2;
    };

    // ajouter un nouveau Client
    this.newClient = function(client){


        $http.post("http://localhost:8080/clients/new",client)
            .then(function mySuccess(response) {


            }, function myError(response) {
                $scope.myWelcome = response.statusText;

            });
    };

    // mise a jour d'un client

    this.updateClient = function(client){


        $http.put("http://localhost:8080/clients/"+client.codeClient,client)
            .then(function mySuccess(response) {
             
                
            });
    };

    // supprimer un client


    this.deleteClient = function(id){


        $http.delete("http://localhost:8080/clients/"+id)
            .then(function mySuccess(response) {
            	//console.log("la suppression working");
            	//console.log(response.data);
            }, function myError(response) {
                $scope.myWelcome = response.statusText;
                alert("not working");
            });
    };

    // recuperer un client

    this.getClient = function (id) {
     


        var promise1    =   $http({
            method : "GET",
            url: "http://localhost:8080/clients/"+id
        });

        
        var promise2 = promise1.then(function mySuccess(response) {

            return response.data;

        }, function myError(response) {
              
        });
      
        return promise2;
    }
    // recuperer client par name
    this.getClientByName = function (name) {
        


        var promise1    =   $http({
            method : "GET",
            url: "http://localhost:8080/clients/name/"+name
        });

        
        var promise2 = promise1.then(function mySuccess(response) {

            return response.data;

        }, function myError(response) {
              
        });
      
        return promise2;
    }
    // recuperer la liste des clients
    this.allClients = function(){

    var promise1    =   $http({
        method : "GET",
        url : "http://localhost:8080/allclients"
    });


    var promise2 = promise1.then(function mySuccess(response) {

        return response.data;

    }, function myError(response) {

    });

    return promise2;
    };
    
    //******************************************//
    //               GERER USER                //
    //****************************************//
    
    // ajouter un nouveau user
    this.newUser = function(user){


    	var promise1    =  $http.post("http://localhost:8080/users",user);
    	 return promise1;
    };
    //mettre a jour user
    this.updateUser = function(user){


        $http.put("http://localhost:8080/users",user)  ;
   
    };
    //
    // recuperer user par client
    this.getUserByClient = function (id) {
        


        var promise1    =   $http({
            method : "GET",
            url: "http://localhost:8080/users/client/"+id
        });

        
        var promise2 = promise1.then(function mySuccess(response) {

            return response.data;

        }, function myError(response) {
              
        });
      
        return promise2;
    }
})
//********
app.factory("fileReader", function($q, $log) {
	  var onLoad = function(reader, deferred, scope) {
	    return function() {
	      scope.$apply(function() {
	        deferred.resolve(reader.result);
	      });
	    };
	  };

	  var onError = function(reader, deferred, scope) {
	    return function() {
	      scope.$apply(function() {
	        deferred.reject(reader.result);
	      });
	    };
	  };

	  var onProgress = function(reader, scope) {
	    return function(event) {
	      scope.$broadcast("fileProgress", {
	        total: event.total,
	        loaded: event.loaded
	      });
	    };
	  };

	  var getReader = function(deferred, scope) {
	    var reader = new FileReader();
	    reader.onload = onLoad(reader, deferred, scope);
	    reader.onerror = onError(reader, deferred, scope);
	    reader.onprogress = onProgress(reader, scope);
	    return reader;
	  };

	  var readAsDataURL = function(file, scope) {
	    var deferred = $q.defer();

	    var reader = getReader(deferred, scope);
	    reader.readAsDataURL(file);

	    return deferred.promise;
	  };

	  return {
	    readAsDataUrl: readAsDataURL
	  };
	});

//**********
app.service('fileUpload', ['$http', function ($http) {

	
    this.uploadFileToUrl = function(file, uploadUrl){
        var fd = new FormData();
        fd.append('file', file);
       angular.forEach(file, function(file) {
            fd.append('file', file);
        });

        $http.post(uploadUrl, fd, {
            transformRequest: angular.identity,
           headers: { 'Content-Type': undefined } 
        })
        
       ;
    }
}]);
//**********
})();