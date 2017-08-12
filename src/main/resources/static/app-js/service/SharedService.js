/**
 * 
 */

app.service('sharedService', function () {
	var myData = [];

    var addData = function(newObj) {
        myData.push(newObj);
    };

    var getData = function(){
        return myData.pop();
    };

    return {
        addData: addData,
        getData: getData
    };
});
