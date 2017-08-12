/**
 * 
 */
app.controller('landingPageController', function($rootScope, $scope, $location, $http, $state) {
	$scope.initData = function() {
		//alert("Load initial data...");
		$rootScope.showEditpage = false;
		var bodyDiv = angular.element("#bodyDiv");
		console.log("bodyDiv: " + angular.toJson(bodyDiv));
		bodyDiv.attr("style","width: 82%; margin-left: 3%;");
	};
});