/**
 * 
 */
app.controller('showAndHideDemoController', function($scope, $location, $http, $state, $timeout) {
	
	$scope.initPage = function(){
		console.log('loading page..');
	}
	
	$scope.showEditPage = function(){
		console.log('loading Edit page..');

		var viewPageDiv = angular.element(document.querySelector("#viewPageDiv"));
		viewPageDiv.attr("class","col-md-6");

		var showEditDiv = angular.element(document.querySelector("#showEditDiv"));
		showEditDiv.attr("class","col-md-6");
		showEditDiv.attr("style","display: block;");
		
		var sideMenuDiv = angular.element(document.querySelector("#sideMenuDiv"));
		sideMenuDiv.attr("style","display: none;");
		
		var bodyDiv = angular.element(document.querySelector("#bodyDiv"));
		bodyDiv.attr("style","width: 92%; margin-left: 3%;");

	}
	
	$scope.hideEditPage = function(){
		console.log('hiding Edit page..');
		var viewPageDiv = angular.element(document.querySelector("#viewPageDiv"));
		viewPageDiv.attr("class","col-md-12");

		var showEditDiv = angular.element(document.querySelector("#showEditDiv"));
		showEditDiv.attr("class","col-md-6");
		showEditDiv.attr("style","display: none;");
		
		var sideMenuDiv = angular.element(document.querySelector("#sideMenuDiv"));
		sideMenuDiv.attr("style"," width: 13%; height: 450px; display: block;");
		
		var bodyDiv = angular.element(document.querySelector("#bodyDiv"));
		bodyDiv.attr("style","width: 82%; margin-left: 3%;");

	}
	
	$scope.updateData = function(){
		console.log('update Edit page data..');
	}
});