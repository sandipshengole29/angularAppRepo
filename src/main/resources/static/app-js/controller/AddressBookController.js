/**
 * 
 */
app.controller('addressBookController', function($rootScope, $scope, $location, $http, $state, addressBookService, $timeout) {
	
	$scope.contact={
		"contactName":null,
		"company":null,
		"phoneNumber":null,
		"email":null
	};
	
	$rootScope.diableSaveButton = false;
	$rootScope.showOutputMsg = false;
	$scope.outputMsg = null;
	
	//$scope.successCallBack = $scope.failureCallBack = function(reponse){console.log(reponse);}
	
	$scope.manageAddressBook = function(){
		console.log("-- Manage address book --");
	}
	
	$scope.showHomePage = function(){
		alert('Show Home Page');
	}
	
	$scope.addNewContact = function(){
		$rootScope.showOutputMsg = false;
		$scope.outputMsg = null;
		$state.go("addContact-page");
	}
	
	$scope.goBackToPrevious = function(){
		$state.go("manageContact-page");
	}
	
	$scope.saveContactInformation = function(){
		console.log('ready to save data');
		$rootScope.diableSaveButton = true;
		addressBookService.saveContactInfo($scope.contact, $scope.successCallBack, $scope.failureCallBack);
	}
	
	$scope.successCallBack = function(reponse){
		console.log('@controller for success');
		$rootScope.showOutputMsg = true;
		$scope.outputMsg = "Contact information saved successfully.";
	}
	
	$scope.failureCallBack = function(reponse){
		console.log('@controller for failure');
		$rootScope.showOutputMsg = true;
		$scope.outputMsg = "Error occured while saving contact information.";

	}
	
	$scope.addAnotherContact = function(){
		console.log('@add another contact');
		$rootScope.showOutputMsg = false;
		$scope.outputMsg = null;
		$rootScope.diableSaveButton = false;
		$scope.contact={
			"contactName":null,
			"company":null,
			"phoneNumber":null,
			"email":null
		};
	}
});
