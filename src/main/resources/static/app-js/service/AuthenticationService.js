/**
 * 
 */
app.service('authenticationService',['$http','$rootScope', function($http, $rootScope){
	var user = {};
	$rootScope.authenticated = false;
	
	return{
		setUserRole : function(role){
			user.role = role;
		},
		
		getUser : function(){
			return user;
		},
		
		authenticate : function(callback){
	        $rootScope.authenticated = true;
		}
	}
}]);
