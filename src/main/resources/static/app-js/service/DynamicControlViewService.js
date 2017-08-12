/**
 * 
 */
app.service('dynamicControlViewService', function (restFactory) {
    return {
        getDynamicControlData : function(url, callback){
            //alert("Get Data For: " + url);
        	restFactory.getRestRequest(url, function(response) {
                callback(response);
            });
        },
        
        postDynamicControlData : function(url, data, config, callback){
            //alert("Get Data For: " + url);
        	restFactory.postDataRequestForSave(url, data, config, function(response) {
                callback(response);
            });
        }
    }
});