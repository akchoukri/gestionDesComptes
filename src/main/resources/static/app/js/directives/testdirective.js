(function () {
// apprendre les directives 
	var app = angular.module("myApp");
	app.directive('myCustomer', function() {
		  return {
			  // isolate scope
			  //(si on a pas fé isolation il va chercher dans dans scope parent)
			  scope: {
			      customer: '=info',//info:'=' et faire info.name(une autre façon de faire info :'=' est remplacer customer par info)
			      // Utiliser le data-binding afin de lier la propriété à la propriété du parent.
			      // customer. deviendra info. dans l'attribut on remplace info par le champs affecté dans  controller 
			    	  test:'@',
			    //  Passer l'attribut sous forme de chaîne de caractères.
			    	select:'&'
			    //Passer une fonction depuis le parent qui sera exécutée plus tard.'	  
			    },
			  restrict: 'E',
			  templateUrl: 'views/partials/my-customer.html'
			  };
			});
	
	
	// direcive pour afficher le temps
	app.directive('time', function(dateFilter,$interval) {
		  return {
			  
			  scope: {
			      
			    },
			  restrict: 'E',
			// templateUrl: 'views/partials/my-customer.html'
				  template :'{{time}}',
				  link: function(scope, element, attrs) { 
					  scope.time=dateFilter(new Date(),'hh:mm:ss');
					  
					  element.on('$destroy',function(){
						  $interval.cancel(interval);
					  })
					  
					  interval = $interval(function(){
						  scope.time=dateFilter(new Date(),'hh:mm:ss');
						  console.log("time changé");
					  },1000)
				  },
			  };
			});
	
	
	// directive pour afficher un msg utilisataion transclude
	app.directive('ngTest', function() {
		  return {
			  
			  scope: {
			      username:'='
			    },
			  restrict: 'A',	
			  transclude:true,//transclude makes the contents of a directive with this option have access to the scope outside of the directive rather than inside.
			  // si je met pas tranclude et ng-transclude il ne va pas afficher le contenu  seulment 
			  //il va afficher la template  ce qu'il ya dedans il va disparitre 
			  // pour remedier on utilise la transclude  il va injecter ce qu'il y avait a l'interieur (il utilise le scope de parent)
			  
		   template :'<div>hello {{username}} <div ng-transclude></div></div>'
		  };
			});
	app.directive('myDraggable', ['$document', function($document) {
		  return {
			    link: function(scope, element, attr) {
			      var startX = 0, startY = 0, x = 0, y = 0;

			      element.css({
			       position: 'relative',
			       border: '1px solid red',
			       backgroundColor: 'lightgrey',
			       cursor: 'pointer'
			      });

			      element.on('mousedown', function(event) {
			        // Prevent default dragging of selected content
			        event.preventDefault();
			        startX = event.pageX - x;
			        startY = event.pageY - y;
			        $document.on('mousemove', mousemove);
			        $document.on('mouseup', mouseup);
			      });

			      function mousemove(event) {
			        y = event.pageY - startY;
			        x = event.pageX - startX;
			        element.css({
			          top: y + 'px',
			          left:  x + 'px'
			        });
			      }

			      function mouseup() {
			        $document.off('mousemove', mousemove);
			        $document.off('mouseup', mouseup);
			      }
			    }
			  };
			}]);
	
	
	app.directive('myTabs', function() {
		  return {
			    restrict: 'E',
			    transclude: true,
			    scope: {},
			    controller: ['$scope', function MyTabsController($scope) {
			      var panes = $scope.panes = [];

			      $scope.select = function(pane) {
			        angular.forEach(panes, function(pane) {
			          pane.selected = false;
			        });
			        pane.selected = true;
			      };

			      this.addPane = function(pane) {
			        if (panes.length === 0) {
			          $scope.select(pane);
			        }
			        panes.push(pane);
			      };
			    }],
			    templateUrl: 'views/partials/my-tabs.html'
			  };
			})
			app.directive('myPane', function() {
			  return {
			    require: '^^myTabs',//inclure la directive de controleur parent
			    restrict: 'E',
			    transclude: true,
			    scope: {
			      title: '@'
			    },
			    link: function(scope, element, attrs, tabsCtrl) {
			      tabsCtrl.addPane(scope);
			      console.log(attrs);
			  
			    },
			    templateUrl: 'views/partials/my-pane.html'
			  };
			});

	//uploadfile
	  app.directive("ngFileSelect", function(fileReader, $timeout) {
	    return {
	      scope: {
	        ngModel: '='
	      },
	      link: function($scope, el) {
	        function getFile(file) {
	          fileReader.readAsDataUrl(file, $scope)
	            .then(function(result) {
	              $timeout(function() {
	                $scope.ngModel = result;
	              });
	            });
	        }

	        el.bind("change", function(e) {
	          var file = (e.srcElement || e.target).files[0];
	          getFile(file);
	        });
	      }
	    };
	  });
	  
	  
	  app.directive('fileModel', ['$parse', function ($parse) {
         return {
            restrict: 'A',
            link: function(scope, element, attrs) {
               var model = $parse(attrs.fileModel);
               var modelSetter = model.assign;
               
               element.bind('change', function(){
                  scope.$apply(function(){
                     modelSetter(scope, element[0].files[0]);
                  });
               });
            }
         };
      }]);

})();