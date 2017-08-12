/**
 * 
 */
app.controller('serachAccessRoleController', function($rootScope, $scope, $http, $q, $uibModalInstance, sharedServiceForDynamicControls) {
	$scope.selectedInfo = [];
	$scope.availableInfo = [];
	
	$scope.mainHeading = null;
	$scope.subHeading1 = null;
	$scope.subHeading1 = null;

	$scope.cancel = function() {
		$uibModalInstance.dismiss('cancel');
	};
	
	// we will store all of our form data in this object
	$scope.initData = function() {
		var dataFor = sharedServiceForDynamicControls.getDataFor();
		//alert("Load initial data For: " + dataFor);
		var url = null;
		if(dataFor === "Role"){
			url="availableRoles.json";
			$scope.mainHeading = "Access role(s) to group";
			$scope.subHeading1 = "Access Role(s)";
			$scope.subHeading2 = "Selected access Role(s)";
		}else{
			url="availableData.json";
			$scope.mainHeading = "Data group faucet(s)";
			$scope.subHeading1 = "Data faucet(s)";
			$scope.subHeading2 = "Selected data faucet(s)";

		}
		$http.get(url	)
		.success(function(data) {
			//alert(data);
			$scope.availableInfo = data;
		})
		.error(function() {
			alert('Error!!');
	      });
	};
	
	$scope.moveItem = function(items, from, to) {
		console.log('Move items: ' + items + ' From: ' + from + ' To: ' + to);
		//Here from is returned as blank and to as undefined
	
		items.forEach(function(item) {
			var idx = from.indexOf(item);
			if (idx != -1) {
				from.splice(idx, 1);
				to.push(item);
			}
		});
	 };
	 
	 $scope.moveAll = function(from, to) {
		 console.log('Move all  From:: '+from+' To:: '+to);
		 //Here from is returned as blank and to as undefined
	
		 angular.forEach(from, function(item) {
			 to.push(item);
		 });
		 from.length = 0;
	 };
	 
	 $scope.getSelectedData=function(){
		 var dataFor = sharedServiceForDynamicControls.getDataFor();
		 $scope.selected=$scope.selectedInfo;
		 if(dataFor === "Role"){
			 $rootScope.getSelectedAccessData=$scope.selected;
			 $rootScope.showSelectRoleError=false;
		 }else{
			 $rootScope.getSelectedDataGrpData=$scope.selected;
			 $rootScope.showSelectDataError=false;
		 }
		 $uibModalInstance.close($scope.selected);
	 }
});