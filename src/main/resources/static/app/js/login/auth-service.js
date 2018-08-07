angular.module('myApp')
// service pour stocker user details
.service('AuthService', function() {
	return {
		user : null
	}
});
