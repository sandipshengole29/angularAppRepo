/**
 * 
 */
app.controller('xmlPocPageController', function($scope, $location, $http, $state, xmlPocPageService, $timeout, sharedService) {
	$scope.showMsg;
	$scope.baseUrl;
	$scope.dataMap;
	$scope.dataList;
	$scope.serachSelectedField="Select Search Field";
	$scope.serachSelectedVal;
	$scope.formData={};
	$scope.updateMap={};
	$scope.showLoader=true;
	$scope.hideTableInSearch=false;
	$scope.fieldList;
	$scope.showButtons=false;
	$scope.currencyData;
	$scope.showComponent='default';
	$scope.autoPopulateData;
	$scope.placeHolderMsg = "Search Input";
	$scope.isInitialised = false;
	
	$scope.headersArray=[];
	$scope.dataJsonArray=[];
	
	$scope.pageNumber=0;
	$scope.updateFormData={
			"exceptionId":null,
			"dataMap":null,
			"exceptionType":null
	};
	
	$scope.autoPopulateForm={
			"field":null,
			"fieldVal":null
	};
	
	$scope.initData = function() {
		$scope.baseUrl = $location.absUrl().substr(0, $location.absUrl().lastIndexOf("#"));
		//alert("Load initial data...");
	};
	
	$scope.getFieldList = function(){
		$scope.baseUrl = $location.absUrl().substr(0, $location.absUrl().lastIndexOf("#"));
		var url = $scope.baseUrl+"xmlPoc/getFieldList";
		xmlPocPageService.getFieldListData(url, function(response) {
			//console.log("fieldData: " + angular.toJson(response));
			$scope.dataList = response;
		});
	}
	
	$scope.readDataFromFlatFile = function(){
		var url = $scope.baseUrl+"xmlPoc/readInputFile";
		xmlPocPageService.getFileData(url, function(result) {
			//alert(result);
			if(result === 'SUCCESS'){
				$scope.showMsg = "Successfully data read and saved from the flat file.";
			}else{
				$scope.showMsg = "Error occured while reading and saving data from file.";
			}
		});
	};
	
	$scope.showDataOfFlatFile = function(event){
		event.preventDefault();
		$state.go("xmlPocView-page");
	};
	
	$scope.getListData = function(newPageNumber){
		if(undefined != newPageNumber || null != newPageNumber){
			$scope.pageNumber=newPageNumber;
		}else{
			$scope.pageNumber=1;
		}
		
		console.log("-- getDataforPage --: " + $scope.pageNumber);
		$scope.baseUrl = $location.absUrl().substr(0, $location.absUrl().lastIndexOf("#"));
		var url = $scope.baseUrl+"xmlPoc/viewFileData/" + $scope.pageNumber;
		$timeout(function(){
			xmlPocPageService.getFileData(url, function(result) {
				$scope.showLoader=false;
				$scope.dataMap = result.EXCEPTION_DATA;
				$scope.headersArray = Object.keys($scope.dataMap[0]);
				console.log("headers: " + angular.toJson(Object.keys($scope.dataMap[0])));
				
				$scope.dataJsonArray = angular.fromJson(result.EXCEPTION_DATA);
				console.log("dataJsonArray: " + $scope.dataJsonArray);
				
				$scope.fieldList = result.FIELD_LIST;
				$scope.totalCount = result.TOTAL_COUNT;
				$scope.pageSize = result.PAGE_SIZE;
				//console.log("complete_data: " + angular.toJson($scope.dataMap));
			});
        });
	};
	
	$scope.sort = function(keyname){
        $scope.sortKey = keyname;   //set the sortKey to the param passed
        $scope.reverse = !$scope.reverse; //if true make it false and vice versa
    }
	
	$scope.setDataForSearch = function(event, searchField, selectedFieldVal, htmlComponent){
		//console.log("serachField: " + selectedFieldVal);
		$scope.serachSelectedField = selectedFieldVal;
		$scope.serachSelectedVal = searchField;
		$scope.showComponent = htmlComponent;
		$scope.placeHolderMsg = "Enter " + selectedFieldVal;
		$scope.isInitialised = true;
		$scope.getDataOfDropDown();
		/*if(htmlComponent == 'autoPopulate'){
			$scope.getDataForAutoComplete();
		}*/
	}
	
	$scope.searchDataForField = function(){
		$scope.hideTableInSearch=true;
		$scope.baseUrl = $location.absUrl().substr(0, $location.absUrl().lastIndexOf("#"));
		var url = $scope.baseUrl+"xmlPoc/searchFieldData";
		$scope.showLoader=true;
		$scope.dataMap;
		var config = {
            headers : {
                'Content-Type': 'application/json	'
            }
        }
		
		if($scope.formData.searchText == null || $scope.formData.searchText.length <= 0){
			$scope.serachSelectedField="Select Search Field";
			$scope.showComponent='default';
			$scope.placeHolderMsg = "Search Input";
		}
		
		$scope.formData.searchField = $scope.serachSelectedVal;
		//console.log("FormData: " + angular.toJson($scope.formData));
		
		xmlPocPageService.getSearchResultForField(url, $scope.formData, config, function(result) {
			$scope.showLoader=false;
			$scope.hideTableInSearch=false;
			$scope.dataMap = result.EXCEPTION_DATA;
			//console.log("fieldData: " + angular.toJson(result));
		});
	}
	
	$scope.editCurrentRow = function(currentRow){
		//console.log("current_row: " + angular.toJson(currentRow));
		sharedService.addData(currentRow.ID);
		$state.go('xmlPocEdit-page');
	}
	
	$scope.loadEditPageData = function(){
		var exceptionID = sharedService.getData();
		//console.log("exception_id_OnEdit: " + exceptionID);
		$scope.baseUrl = $location.absUrl().substr(0, $location.absUrl().lastIndexOf("#"));
		var url = $scope.baseUrl+"xmlPoc/getEditableFieldList";
		
		/*var updateXmlDiv = angular.element("#updateXmlDiv");
		console.log("updateXmlDiv: " + angular.toJson(updateXmlDiv));*/
		
		xmlPocPageService.getFieldListData(url, function(response) {
			$scope.dataList = response;
		});
		
		//console.log("beforeMaking_call: " + exceptionID);
		$scope.baseUrl = $location.absUrl().substr(0, $location.absUrl().lastIndexOf("#"));
		var url = $scope.baseUrl+"xmlPoc/getExceptionForId/"+exceptionID;
		
		xmlPocPageService.getFieldListData(url, function(response) {
			$scope.dataMap = response.EXCEPTION_DATA;
			angular.forEach($scope.dataMap[0], function(value, key) {
				//console.log(key +" ===== "+ value);
				if(key == 'ID'){
					$scope.updateFormData.exceptionId = value;
				}
				
				if(key == 'ExceptionType'){
					$scope.updateFormData.exceptionType = value;
				}
				
				if(key != 'ID'){
					$scope.formData[key] = value;
				}
			});
			$scope.showButtons=true;
			$scope.showLoader=false;
			//console.log("formData: " + angular.toJson($scope.formData));
		});
	}
	
	$scope.updateXMLData = function(){
		$scope.showButtons=true;
		angular.forEach($scope.formData, function(key, value) {
			//console.log(value + " ===== " + angular.toJson(key.label));
			if(null != key){
				if(null != angular.toJson(key.value)){
					$scope.updateMap[value] = (key.value);
				}else{
					$scope.updateMap[value] = key;
				}
			}
		});
		//console.log("updateFormData: " + angular.toJson($scope.updateMap));
		$scope.updateFormData.dataMap = $scope.updateMap;
		//console.log("updateFormData: " + angular.toJson($scope.updateFormData));
		
		if($scope.editForm.$valid){
			$scope.baseUrl = $location.absUrl().substr(0, $location.absUrl().lastIndexOf("#"));
			var url = $scope.baseUrl+"xmlPoc/updateExceptionData";
			$scope.dataMap;
			var config = {
	            headers : {
	                'Content-Type': 'application/json'
	            }
	        }
			
			xmlPocPageService.postDataForUpdate(url, $scope.updateFormData, config, function(result) {
				console.log("fieldData: " + angular.toJson(result));
				if(result == "SUCCESS"){
					$scope.showMsg="Exception data updated successfully."
				}else{
					$scope.showMsg="Error occured in updating exception data."
				}
			});
		}
	}
	
	$scope.viewListPage = function(){
		$state.go('xmlPocView-page');
	}
	
	$scope.getDataOfDropDown = function(){
		$scope.baseUrl = $location.absUrl().substr(0, $location.absUrl().lastIndexOf("#"));
		var url = $scope.baseUrl+"xmlPoc/getDataOfDropDownOnEdit";
		
		if($scope.isInitialised){
			var serachSelectedField = $scope.serachSelectedField;
			var serachSelectedVal = $scope.serachSelectedVal;
			var showComponent = $scope.showComponent;
			var placeHolderMsg = $scope.placeHolderMsg;
			
			//console.log("serachSelectedField: " + serachSelectedField);
			//console.log("serachSelectedVal: " + serachSelectedVal);
			//console.log("showComponent: " + showComponent);
			//console.log("placeHolderMsg: " + placeHolderMsg);
			
			if(showComponent == 'select'){
				$scope.placeHolderMsg = placeHolderMsg.replace('Enter', 'Select');
			}
			
			$scope.autoPopulateForm={
					"field": serachSelectedVal,
					"fieldVal":"",
					"componentType": showComponent
			};
			
			xmlPocPageService.getDataForSelectedField(url, $scope.autoPopulateForm, function(response) {
				//console.log("autoPopulate: " + angular.toJson(response));
				$scope.currencyData = response;
			});
		}
		
		
		/*$scope.baseUrl = $location.absUrl().substr(0, $location.absUrl().lastIndexOf("#"));
		var url = $scope.baseUrl+"xmlPoc/getDataOfDropDown";
		 xmlPocPageService.getCurrencyFieldData(url, function(response) {
			$scope.currencyData = response;
			console.log("fieldData: " + angular.toJson($scope.currencyData));
		});*/
	}
	
	$scope.getDataOfDropDownOnEdit = function(fieldName, componentType){
		$scope.autoPopulateData = null;
		$scope.selectedItem = 0;

		$scope.baseUrl = $location.absUrl().substr(0, $location.absUrl().lastIndexOf("#"));
		var url = $scope.baseUrl+"xmlPoc/getDataOfDropDownOnEdit";
		
		$scope.autoPopulateForm={
				"field":fieldName,
				"fieldVal":"",
				"componentType": componentType
		};
		
		xmlPocPageService.getDataForSelectedField(url, $scope.autoPopulateForm, function(response) {
			//console.log("autoPopulate: " + angular.toJson(response));
			
			var dorpDownVariable = "dropDown_" + fieldName;
			//console.log("dorpDownVariable: " + dorpDownVariable + " :: " + $scope.formData[fieldName]);
			$scope[dorpDownVariable] = response;
			
			/*for (var i=0; i<$scope[dorpDownVariable].length; i++) {
			    if($scope.formData[fieldName] == $scope[dorpDownVariable][i].toString()){
			    	$scope.selectedItem =$scope[dorpDownVariable][i].toString();
			    	console.log(i +" :: "+ $scope[dorpDownVariable][i]);
			    	break;
			    }  
			}*/
			//$scope.currencyData = response;
		});
	}
	
	$scope.getDataForAutoComplete = function(){
		//console.log("serachSelectedVal: " + $scope.serachSelectedVal);
		$scope.autoPopulateData = document.getElementById('searchText').value;
		//console.log("searchText: " + $scope.autoPopulateData);
	}
	
	$scope.getDataForAutoCompleteOnEdit = function(fieldName, mappingName){
		$scope.showComponent = mappingName;
		$scope.serachSelectedVal = fieldName;
		//console.log("currentText: " + document.getElementById(fieldName).value)
		$scope.autoPopulateData = document.getElementById(fieldName).value;
	}

});

app.directive('autocomplete', ['xmlPocPageService','$location', function(xmlPocPageService, $location) {
	return {
		restrict : 'A',
		require : 'ngModel',
		link : function(scope, element, attrs, ngModelCtrl) {
			element.autocomplete({
				source : function(request, response) {
					//console.log("showComponent: " + scope.showComponent);
					//console.log("autoPopulateData: " + scope.autoPopulateData);
					
					scope.autoPopulateForm={
							"field":scope.serachSelectedVal,
							"fieldVal":scope.autoPopulateData
					};
					
					//console.log("autoPopulateForm: " + angular.toJson(scope.autoPopulateForm));
					
					var baseUrl = $location.absUrl().substr(0, $location.absUrl().lastIndexOf("#"));
					var url = baseUrl+"xmlPoc/getDataForAutoComplete";
					//console.log("url: " + url);
					xmlPocPageService.getDataForSelectedField(url, scope.autoPopulateForm, function(data) {
						//console.log("autoPopulate: " + angular.toJson(data));
						response(data);
					});
				},
				select : function(event, ui) {
					//console.log(ui);
					ngModelCtrl.$setViewValue(ui.item);
					scope.$apply();
				}
			});

		}
	}
}]);