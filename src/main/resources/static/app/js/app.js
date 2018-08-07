var app = angular.module("myApp", [ "ui.router" ]);


// the following method will run at the time of initializing the module. That
// means it will run only one time.


// la configuration des des route (les chemin des vues)
app.config(function($stateProvider, $urlRouterProvider, $httpProvider) {

	
	$httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
	 
//	
// // chemin par defaut pour vue accueil
	 $urlRouterProvider.otherwise('/login');

	
	// chemin vue pour navigation
	$stateProvider.state('nav', {
		abstract : true,
		url : '',
		views : {
			'nav@' : {
				templateUrl : 'app/views/nav.html',
				controller : 'NavController'
			}
		}
	// chemin pour vue login
	}).state('login', {
		parent : 'nav',
		url : '/login',
		views : {
			'content@' : {
				templateUrl : 'app/views/login.html',
				controller : 'LoginController'
			}
		}
	// chemin pour vue client
	}).state('clients', {
		parent : 'nav',
		url : '/clients',
		views : {
			'content@' : {
				templateUrl : 'app/views/client/listClientt.html',
				controller : "listClientContl",
			}
		}
})
// vue pour creer nouveau client
 .state('clients.newClient', {
 url : '/new',
 templateUrl : 'app/views/client/createClient.html',
 controller : "clientNewCtrl"		

 })
 // vue pour mise a jour le client
 .state('clients.updateClient', {

 url : '/:id',

				
 templateUrl : 'app/views/client/updateClient.html',
 controller : "clientUpdateCtrl"

 })

	// chemin des vues pour gerer les comptes
	.state('comptes', {
		parent : 'nav',
		url : '/comptes',
		views : {
			'content@' : {
		templateUrl : 'app/views/compte/comptes.html',
		controller : "ComptesContl"
			}
		}
		

	})
	// vue pour creer nouveau compte
	.state('comptes.newCompte', {

		
		url : '/new',

		templateUrl : 'app/views/compte/createCompte.html',
		controller : "compteNewCtrl"

		
	})

	// chemin des vues pour gerer les operations
	.state('operations', {
		parent : 'nav',
		url : '/operations',
		views : {
			'content@' : {
		templateUrl : 'app/views/operation/operations.html',
		controller : "ComptesContl",
			}
		}
	});

})
.run(function(AuthService, $rootScope, $state) {
	
	//analyser le changement de state pour chaque ui-route module qu'il va diffuser
	console.log("inside");
	console.log($rootScope);
	$rootScope.$on('$stateChangeStart', function(event, toState, toParams, fromState, fromParams) {
		
//
//		console.log("fromState");
//		console.log(fromState);
//		console.log("toState");
//		console.log(toState);
		
// tester si user est connecter
		if (!AuthService.user) {

			
			// redirection vers login si en refresh navigateur
			if (toState.name != 'login') {
				event.preventDefault();
				$state.go('login');
			}
		} 
	});
});
