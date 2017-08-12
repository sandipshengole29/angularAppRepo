/**
 * 
 */
app.controller('xmlFieldMappingController', function($scope, $location, $http, $state, xmlPocPageService, $timeout, sharedService) {
	
	$scope.initData = function() {
		$timeout(function(){
			alert('XML mapping field..');
		});
	};
});