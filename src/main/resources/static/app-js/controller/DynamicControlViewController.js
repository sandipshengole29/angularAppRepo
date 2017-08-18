/**
 * 
 */
/*app.controller('dynamicControlViewController', function($scope, $location, $http, $state, $timeout) {
	
	$scope.initPage = function(){
		$timeout(function(){
			alert('Load dynamic controls page..');
		});
	}
	
});*/

app.controller('dynamicControlViewController', function($rootScope, $scope, $timeout, $http, $location, $uibModal, sharedServiceForDynamicControls, dynamicControlViewService) {
	$scope.functionalGroups = [{
		groupName : '',
		activeDirectoryName : '',
		groupDescription : '',
		groupType : '',
		accessData : []
	}];
	
	$scope.dataGroups = [{
		groupName : '',
		activeDirectoryName : '',
		groupDescription : '',
		groupType : '',
		accessData : []
		
	}];
	
	$scope.showMessage;
	$scope.functionalGroupRows = 0;
	$scope.dataGroupRows = 0;
	
	$scope.functionalGroupObj = [];
	$scope.dataGroupObj = [];
	
	$rootScope.getSelectedAccessData=[];
	$rootScope.getSelectedDataGrpData=[];
	
	$rootScope.showSelectRoleError=false;
	$rootScope.showSelectDataError=false;
	
	$scope.functionalFormArray = [];
	$scope.dataFormArray = [];
	
	$scope.submitFunctionalForm = false;
	$scope.submitDataForm = false;
	
	$scope.checkFunctionalGroupObj = [];
	$scope.checkDataGroupObj = [];

	$scope.loadInitialFormData = function(){
		console.log("-- insdie loadInitialFormData --");
		$scope.functionalGroups = [{
			groupName : '',
			activeDirectoryName : '',
			groupDescription : '',
			groupType : '',
			accessData : []
		}];
		
		$scope.dataGroups = [{
			groupName : '',
			activeDirectoryName : '',
			groupDescription : '',
			groupType : '',
			accessData : []
			
		}];
		
		$scope.functionalGroupRows = 1;
		$scope.dataGroupRows = 1;
		
		$scope.groupName = '',
		$scope.activeDirectoryName = '',
		$scope.groupDescription = '',
		$scope.groupType = '',
		$scope.accessRoleData = []
		
		$rootScope.getSelectedAccessData=[];
		$rootScope.getSelectedDataGrpData=[];
	};
	
	$scope.setDeskTab = function(newTab){
      $scope.tab = newTab;
      if(newTab == 1){
    	  var selectedElement = angular.element("#fgDiv");
    	  console.log("selectedElement: " + angular.toJson(selectedElement));
    	  selectedElement.attr("style", "display: block; margin-top: 2%;");
    	  
    	  var selectedElement1 = angular.element("#dgDiv");
    	  console.log("selectedElement: " + angular.toJson(selectedElement1));
    	  selectedElement1.attr("style", "display: none; margin-top: 2%;");
    	  
    	  var selectedElement2 = angular.element("#fgLi");
    	  selectedElement2.attr("class", "active");
    	  
    	  var selectedElement3 = angular.element("#dgLi");
    	  selectedElement3.attr("class", "active1");
      }else if(newTab == 2){
    	  var selectedElement = angular.element("#dgDiv");
    	  console.log("selectedElement: " + angular.toJson(selectedElement));
    	  selectedElement.attr("style", "display: block; margin-top: 2%;"); 
    	  
    	  var selectedElement1 = angular.element("#fgDiv");
    	  console.log("selectedElement: " + angular.toJson(selectedElement1));
    	  selectedElement1.attr("style", "display: none; margin-top: 2%;");
    	  
    	  var selectedElement2 = angular.element("#dgLi");
    	  selectedElement2.attr("class", "active");
    	  
    	  var selectedElement3 = angular.element("#fgLi");
    	  selectedElement3.attr("class", "active1");
      }
    };
	
	$scope.addErrorForRole = function(fromName, index){
		//alert("Select role/s!!");
		var validation = true;
		//console.log("here1");
		if(!fromName.$invalid && !$scope.submitFunctionalForm){
			//console.log("here2");
			$rootScope.showSelectRoleError = true;
			validation = false;
		}else{
			$scope.submitFunctionalForm = false;
			$scope.addNewFunctionalGroup(fromName, index, false);
		}
		return validation;
	};
	
	$scope.addErrorForData = function(fromName, index){
		//alert("Select role/s!!");
		var validation = true;
		if(!fromName.$invalid && !$scope.submitDataForm){
			$rootScope.showSelectDataError = true;
			validation = false;
		}else{
			$scope.submitDataForm = false;
			$scope.addNewDataGroup(fromName, index, false);
		}
		return validation;
	};
	
	
	$scope.addNewFunctionalGroup = function(fromName, index, isLast) {
		//alert("rootscope Data:  "+ $rootScope.getSelectedAccessData);
		//var index = rowNumber -1;
		//alert(fromName.$invalid);
		console.log("here3");
		$scope.isEdit = false;
		
		if(!fromName.$invalid){
			$scope.functionalFormArray.push(fromName);
			console.log("current_form: " + angular.toJson(fromName));
			$rootScope.showSelectRoleError = false;
			
			if($scope.functionalGroups.length > 0){
				$scope.functionalGroupRows = $scope.functionalGroups.length;
			}
			
			console.log("inside addnew row: functional");
			if(!isLast){
				console.log("No Last column");
				angular.forEach($scope.functionalGroups, function(value, key) {
		        	var selectedElement = null;
		            if(key === index){
		            	var divId = "#div_"+ key;
		        		console.log("hide_div_Id: " + divId);
		        		selectedElement = angular.element(divId);
		        		console.log("selectedElement: " + angular.toJson(selectedElement));
		                selectedElement.attr("style", "display: none; margin-top: 0%;");
		            }
				});
				
				$scope.functionalGroupRows = $scope.functionalGroupRows + 1;
				
				$scope.functionalGroups.push({
					groupName : $scope.groupName,
					activeDirectoryName : $scope.activeDirectoryName,
					groupDescription : $scope.groupDescription,
					groupType : 'FG',
					accessData : []
				});
				
				angular.forEach($scope.functionalGroupObj, function(value, key) {
		            if(index == key  && value.accessData.length <= 0){
		            	value.accessData = $rootScope.getSelectedAccessData;
		            }
				});
				$rootScope.getSelectedAccessData = [];
			}
		}
		
		if(isLast){
			console.log("functionalGroups: " + $scope.functionalGroups.length);
			angular.forEach($scope.functionalGroupObj, function(value, key) {
				console.log(index + " : " + key);
				console.log("Arr_Size: " + value.accessData.length);
				
	            if(index == key  && value.accessData.length <= 0){
	            	console.log("rootscope assigned to object");
	            	value.accessData = $rootScope.getSelectedAccessData;
	            }
			});
		}
		
		$scope.groupName = '';
		$scope.activeDirectoryName = '';
		$scope.groupDescription = '';
		$scope.groupType = '',
		$scope.accessData = [];
		$rootScope.getSelectedAccessData = [];
		console.log("is_root_array_empty: " + $rootScope.getSelectedAccessData.length);
	};
	
	$scope.addNewDataGroup = function(fromName, index, isLast) {
		if(!fromName.$invalid){
			$scope.dataFormArray.push(fromName);
			console.log("current_form: " + angular.toJson(fromName));
			$rootScope.showSelectDataError = false;
			
			if($scope.dataGroups.length > 0){
				$scope.dataGroupRows = $scope.dataGroups.length;
			}
			
			if(!isLast){
				angular.forEach($scope.dataGroups, function(value, key) {
		        	var selectedElement = null;
		            if(key === index){
		            	var divId = "#div_data_"+ key;
		        		console.log("hide_div_Id: " + divId);
		        		selectedElement = angular.element(divId);
		        		console.log("selectedElement: " + angular.toJson(selectedElement));
		                selectedElement.attr("style", "display: none; margin-top: 0%;");
		            }
				});
				
				console.log("inside addnew row: functional");
				$scope.dataGroupRows = $scope.dataGroupRows + 1;
				
				$scope.dataGroups.push({
					groupName : $scope.groupName,
					activeDirectoryName : $scope.activeDirectoryName,
					groupDescription : $scope.groupDescription,
					groupType : 'DG',
					accessData : []
				});
				
				angular.forEach($scope.dataGroupObj, function(value, key) {
		            if(index == key  && value.accessData.length <= 0){
		            	value.accessData = $rootScope.getSelectedDataGrpData;
		            }
				});
				$rootScope.getSelectedDataGrpData = [];
			}
		}
		
		if(isLast){
			console.log("dataGroups: " + $scope.dataGroups.length);
			angular.forEach($scope.dataGroupObj, function(value, key) {
				console.log(index + " : " + key);
				console.log("Arr_Size: " + value.accessData.length);
				
	            if(index == key  && value.accessData.length <= 0){
	            	console.log("rootscope assigned to object");
	            	value.accessData = $rootScope.getSelectedDataGrpData;
	            }
			});
		}
		
		$scope.groupName = '';
		$scope.activeDirectoryName = '';
		$scope.groupDescription = '';
		$scope.groupType = '',
		$scope.accessData = [];
		$rootScope.getSelectedDataGrpData = [];
		console.log("is_root_array_empty: " + $rootScope.getSelectedDataGrpData.length);
	};
	
	$scope.deleteFunctionalGropNewRow = function(indexId){
		console.log("Delete current row: " + indexId);
		var indexVal = indexId;
		
		if($scope.functionalGroups.length > 0){
			$scope.functionalGroups.splice((indexVal),1);
			$scope.functionalGroupObj.splice((indexVal),1);
			$scope.functionalGroupRows = $scope.functionalGroupRows - 1;
		}
		
		console.log("total_ele: " + $scope.functionalGroups.length);
		
		var differenceVal  = $scope.functionalGroups.length-indexId;
		console.log("differenceVal: " + differenceVal);
		
		var arrayLength = $scope.functionalGroups.length-1;
		console.log("arrayLength: " + arrayLength);
		
		var showIndexVal = indexVal-1;
		console.log("show_index: " + showIndexVal);
		
		var checkIndexVal = indexVal+differenceVal;
		console.log("check_Index: " + checkIndexVal);
		
		if(checkIndexVal === $scope.functionalGroups.length && showIndexVal >= 0 && $scope.functionalGroups.length != 1 && differenceVal > 0){
			console.log("No need to show buttons");
		}else{
			console.log("show buttons");
			angular.forEach($scope.functionalGroups, function(value, key) {
				var selectedElement = null;
				console.log(key + " : " + showIndexVal);
				if(key === showIndexVal && showIndexVal > 0){
					var divId = "#div_"+ key;
					console.log("show_div_Id: " + divId);
					selectedElement = angular.element(divId);
					console.log("selectedElement: " + angular.toJson(selectedElement));
					selectedElement.attr("style", "display: block; margin-top: 2%;");
				}else if(showIndexVal == 0){
					var divId = "#div_"+ key;
					console.log("show_div_Id: " + divId);
					selectedElement = angular.element(divId);
					console.log("selectedElement: " + angular.toJson(selectedElement));
					selectedElement.attr("style", "display: block; margin-top: 2%;");
				}
			});
		}
	};
	
	$scope.deleteDataGropNewRow = function(indexId){
		console.log("Delete current row: " + indexId);
		var indexVal = indexId;
		
		if($scope.dataGroups.length > 0){
			$scope.dataGroups.splice((indexVal),1);
			$scope.dataGroupObj.splice((indexVal),1);
			$scope.dataGroupRows = $scope.dataGroupRows - 1;
		}
		
		console.log("total_ele: " + $scope.dataGroups.length);
		
		var differenceVal  = $scope.dataGroups.length-indexId;
		console.log("differenceVal: " + differenceVal);
		
		var arrayLength = $scope.dataGroups.length-1;
		console.log("arrayLength: " + arrayLength);
		
		var showIndexVal = indexVal-1;
		console.log("show_index: " + showIndexVal);
		
		var checkIndexVal = indexVal+differenceVal;
		console.log("check_Index: " + checkIndexVal);
		
		if(checkIndexVal === $scope.dataGroups.length && showIndexVal >= 0 && $scope.dataGroups.length != 1 && differenceVal > 0){
			console.log("No need to show buttons");
		}else{
			console.log("show buttons");
			angular.forEach($scope.dataGroups, function(value, key) {
				var selectedElement = null;
				console.log(key + " : " + showIndexVal);
				if(key === showIndexVal && showIndexVal > 0){
					var divId = "#div_data_"+ key;
					console.log("show_div_Id: " + divId);
					selectedElement = angular.element(divId);
					console.log("selectedElement: " + angular.toJson(selectedElement));
					selectedElement.attr("style", "display: block; margin-top: 2%;");
				}else if(showIndexVal == 0){
					var divId = "#div_data_"+ key;
					console.log("show_div_Id: " + divId);
					selectedElement = angular.element(divId);
					console.log("selectedElement: " + angular.toJson(selectedElement));
					selectedElement.attr("style", "display: block; margin-top: 2%;");
				}
			});
		}
	};
	
	$scope.searchAccessRole=function(){
		//alert("Add Search access role in progress");
		sharedServiceForDynamicControls.setDataFor("Role");
		var modalInstance = $uibModal.open({
			templateUrl : 'view-lib/dynamic-control/searchAccessRole-page.html',
			controller : 'serachAccessRoleController'
		});
	};
	
	$scope.mapDataFaucet=function(){
		sharedServiceForDynamicControls.setDataFor("Data");
		//alert("Map Data Faucet in progress");
		var modalInstance = $uibModal.open({
			templateUrl : 'view-lib/dynamic-control/searchAccessRole-page.html',
			controller : 'serachAccessRoleController'
		});
	};
	
	$scope.saveFunctionalGroupMapping=function(fromName, currentRow){
		console.log("-- Save Functional Group Mapping --");
		
		var response = 'Y';
		var isReadyToSubmit = true;
		var keepGoing = true;
		var keepGoing1 = true;
		
		console.log("currentRow: " + currentRow);
		if(currentRow === 1){
			$scope.functionalFormArray.push(fromName);
		}
		
		angular.forEach($scope.functionalFormArray, function(value, key) {
			//console.log("Array_Data_Length["+key+"]"+ $scope.functionalGroupObj[key].accessData.length);
			
			if(value.$invalid && keepGoing && $scope.functionalGroupObj[key].accessData.length <= 0){
				isReadyToSubmit = false;
				keepGoing = false;
				return false;
			}
			$scope.lastForm = value;
		});
		
		console.log("isReadyToSubmit: " + isReadyToSubmit);
		var indexVal = currentRow-1;
		console.log("Submit_indexVal: " + indexVal);
		$scope.submitFunctionalForm = true;
		
		if(isReadyToSubmit){
			$scope.addNewFunctionalGroup($scope.lastForm, indexVal, true);
			angular.forEach($scope.functionalGroupObj, function(value, key) {
				if(value.accessData.length <= 0 && keepGoing1){
					$rootScope.showSelectRoleError=true;
					console.log("Unable to submit the form due to select role error");
					isReadyToSubmit = false;
					keepGoing1 = false;
					return false;
				}
			});
		}
		
		if(isReadyToSubmit){
			$scope.baseUrl = $location.absUrl().substr(0, $location.absUrl().lastIndexOf("#"));
			var url = $scope.baseUrl+"dynaView/saveDynaViewData";
			
			var config = {
	            headers : {
	                'Content-Type': 'application/json'
	            }
	        }
			
			alert("Save Functional Group Mapping in progress");
			console.log("Data: " + angular.toJson($scope.functionalGroupObj));
			
			dynamicControlViewService.postDynamicControlData(url, $scope.functionalGroupObj, config, function(result) {
				console.log("result: " + result);
				if(result == 'SUCCESS'){
					$scope.showMessage = "Functional group data saved successfully.";
					value.accessData=[];
				}else{
					$scope.showMessage = "Error occured while saving functional group data."
				}
			});
			
			/*angular.forEach($scope.functionalGroupObj, function(value, key) {
				console.log("1 : " + value.groupName);
	            console.log("2 : " + value.activeDirectoryName);
	            console.log("3 : " + value.groupDescription);
	            console.log("4 : " + value.groupType);
	            console.log("5 : " + value.accessData);
	            angular.forEach(value.accessData, function(value, key) {
	    			console.log("5 : " + value.id);
	                console.log("6 : " + value.name);
	            });
	            
	            if(response === 'Y'){
	            	value.accessData=[];
	            }
			});*/
		}
	};
	
	$scope.saveDataGroupMapping=function(fromName, currentRow){
		var response = 'Y';
		var isReadyToSubmit = true;
		var keepGoing = true;
		var keepGoing1 = true;
		
		if(currentRow === 1){
			$scope.dataFormArray.push(fromName);
		}
		
		angular.forEach($scope.dataFormArray, function(value, key) {
			if(value.$invalid && keepGoing){
				isReadyToSubmit = false;
				keepGoing = false;
				return false;
			}
			$scope.lastForm = value;
		});
		
		console.log("isReadyToSubmit: " + isReadyToSubmit);
		var indexVal = currentRow-1;
		console.log("Submit_indexVal: " + indexVal);
		$scope.submitDataForm = true;
		
		if(isReadyToSubmit){
			$scope.addNewDataGroup($scope.lastForm, indexVal, true);
			angular.forEach($scope.dataGroupObj, function(value, key) {
				if(value.accessData.length <= 0 && keepGoing1){
					$rootScope.showSelectDataError=true;
					console.log("Unable to submit the form due to select data error");
					isReadyToSubmit = false;
					keepGoing1 = false;
					return false;
				}
			});
		}
		
		if(isReadyToSubmit){
			alert("Save Data Group Mapping in progress");
			console.log("Data: " + angular.toJson($scope.dataGroupObj));
			
			angular.forEach($scope.dataGroupObj, function(value, key) {
				console.log("1 : " + value.groupName);
	            console.log("2 : " + value.activeDirectoryName);
	            console.log("3 : " + value.groupDescription);
	            console.log("4 : " + value.groupType);
	            console.log("5 : " + value.accessData);
	            angular.forEach(value.accessData, function(value, key) {
	    			console.log("5 : " + value.id);
	                console.log("6 : " + value.name);
	            });
	            
	            /*if(response === 'Y'){
	            	value.accessData=[];
	            }*/
			});
		}
	};
	
	$scope.loadDynamicViewDataOnView = function(){
		console.log("-- insdie loadDynamicViewDataOnView --");
		$scope.baseUrl = $location.absUrl().substr(0, $location.absUrl().lastIndexOf("#"));
		var url = $scope.baseUrl+"dynaView/getDynaViewData/fg/1";
		var i = 0;
		$scope.isEdit = false;
		dynamicControlViewService.getDynamicControlData(url, function(response) {
			console.log("response.length: " + response.length);
			if(response.length > 0){
				$scope.isEdit = true;
				$scope.functionalGroups = [];
				/*$scope.functionalGroupObj = response;
				$scope.functionalGroups.push(response);*/
				
				var lastIndex = (response.length)-1;
				console.log("lastIndex: " + lastIndex);
				$scope.functionalGroupRows = response.length;
				
				angular.forEach(response, function(value, key) {
					console.log(key +" :: "+ angular.toJson(value));
					$scope.tempObject1 = {
							groupType: value.groupType,
							groupName: value.groupName,
							activeDirectoryName: value.activeDirectoryName,
							groupDescription: value.groupDescription,
							accessData: []
					}
					
					angular.forEach(value.accessData, function(value, key) {
		    			console.log("5 : " + value.id);
		                console.log("6 : " + value.name);
		                $scope.tempObject2 = {
		                		id: value.id,
		                		name: value.name
		                };
		                $scope.tempObject1.accessData.push($scope.tempObject2);
		            });
					
					$scope.functionalGroupObj[i] = $scope.tempObject1;
					
					if(key == lastIndex){
						$rootScope.getSelectedAccessData = $scope.functionalGroupObj[lastIndex].accessData;
					}
					
					$scope.functionalGroups.push($scope.tempObject1);
					i++;
			    });
				
			}else{
				$scope.loadInitialFormData();
			}
			
		});
	}
});