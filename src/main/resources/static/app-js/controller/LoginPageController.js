/**
 * 
 */
app.controller('loginPageController', function($rootScope, $scope, $location, $http, $state, authenticationService) {
	$rootScope.authenticated = false;
	
	$scope.initData = function() {
		//alert("Load initial data...");
	};
	
	$scope.getLandingPage = function() {
		//alert("Load initial data...");
		authenticationService.authenticate(function(result) {
			alert(result);
		});
		$state.go("landing-page");
	};
});