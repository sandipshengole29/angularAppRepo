/**
 * 
 */

app.service('xmlPocPageService', function (restFactory) {
    return {
        getFileData : function(url, callback){
            //alert("Get Data For: " + url);
        	restFactory.getRestRequest(url, function(response) {
                callback(response);
            });
        },
        
        getFieldListData : function(url, callback){
            //alert("Get Data For: " + url);
        	restFactory.getAjaxRequest(url, function(response) {
                callback(response);
            });
        },
        
        getSearchResultForField : function(url, data, config, callback){
            //alert("Get Data For: " + url);
        	restFactory.postDataRequestForSearch(url, data, config, function(response) {
                callback(response);
            });
        },
        
        postDataForUpdate : function(url, data, config, callback){
            //alert("Get Data For: " + url);
        	restFactory.postDataRequestForSearch(url, data, config, function(response) {
                callback(response);
            });
        },
        
        getCurrencyFieldData : function(url, callback){
            //alert("Get Data For: " + url);
        	restFactory.getAjaxRequest(url, function(response) {
                callback(response);
            });
        },
        
        getDataForSelectedField : function(url, data, callback){
            //alert("Get Data For: " + url);
        	var config = {
				params : data,
				headers : {
					'Accept' : 'application/json'
				}
			};
        	restFactory.getAjaxRequestWithData(url, config, function(response) {
                callback(response);
            });
        }
    }
});