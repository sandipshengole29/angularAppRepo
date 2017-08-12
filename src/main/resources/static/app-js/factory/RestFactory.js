/**
 * 
 */
app.factory("restFactory", function($http, $q) {
    var factory = {};

    factory.getRestRequest = function(url,callback){
        //alert("URL: " + url);
        return $http.get(url).success(callback);
    }
    
    factory.getAjaxRequest = function(url,callback){
    	return $http.get(url).success(callback);
    }
    
    factory.getAjaxRequestWithData = function(url, data, callback){
    	return $http.get(url, data).success(callback);
    }
    
    factory.postDataRequestForSearch = function(url, data, config, callback){
    	return $http.post(url, data, config).success(callback);
    }
    
    factory.postDataRequestForSave = function(url, data, config, callback){
    	return $http.post(url, data, config).success(callback);
    }
    
    return factory;
});