/**
 * 
 */

app.service('addressBookService', function ($http) {
    
    this.saveContactInfo = function(contactForm, getSuccessResponse, getFailureResponse){
    	console.log("inside service for save");
    	
    	var config = {
            headers : {
                'Content-Type': 'application/json'
            }
        }
    	
    	$http.post('contactInfo/saveContact', contactForm, config)
    	.success(function(data) {
    		console.log('- inside success: -' + angular.toJson(data));
    		return getSuccessResponse(data);
    	})
    	.error(function(errResponse) {
    		console.log('- inside failure: -' + angular.toJson(errResponse));
    		return getFailureResponse(errResponse);
    	});
    }
});
