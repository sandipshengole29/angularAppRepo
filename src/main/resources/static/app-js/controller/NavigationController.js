/**
 * 
 */
app.controller('navigationController', function($scope, $location, $http, $state) {
	$scope.loadLandingPage = function(event, linkNumber) {
		event.preventDefault();
		$state.go("landing-page");
		$scope.manageActive(linkNumber);
	};
	
	$scope.loadXmlPocPage = function(event, linkNumber) {
		event.preventDefault();
		$state.go("xmlPocStart-page");
		$scope.manageActive(linkNumber);		
	};
	
	$scope.loadDynamicControlPage = function(event, linkNumber) {
		event.preventDefault();
		$state.go("dynamicControlView-page");
		$scope.manageActive(linkNumber);
	};
	
	$scope.loadXmlFieldMappingPage = function(event, linkNumber) {
		event.preventDefault();
		$state.go("xmlMappingView-page");
		$scope.manageActive(linkNumber);
	};
	
	$scope.showAndHideDemo = function(event, linkNumber) {
		event.preventDefault();
		$state.go("showAndHideView-page");
		$scope.manageActive(linkNumber);
	};
	
	$scope.loadServicePage = function(event, linkNumber) {
		event.preventDefault();
		$state.go("landing-page");
		$scope.manageActive(linkNumber);
	};
	
	$scope.loadContactPage = function(event, linkNumber) {
		event.preventDefault();
		$state.go("landing-page");
		$scope.manageActive(linkNumber);
	};
	
	$scope.loadAddressBookPage = function(event, linkNumber) {
		event.preventDefault();
		$state.go("manageContact-page");
		$scope.manageActive(linkNumber);
	};
	
	$scope.manageActive = function(linkNumber){
		for(var i=1;i<=7;i++){
			if(i===linkNumber){
				var eleId = "#li_"+i
				var selectedElement1 = angular.element(document.querySelector(eleId));
				selectedElement1.attr("class","active");
			}else{
				var eleId = "#li_"+i
				var selectedElement1 = angular.element(document.querySelector(eleId));
				selectedElement1.attr("class","noactive");
			}
		}
	}
});