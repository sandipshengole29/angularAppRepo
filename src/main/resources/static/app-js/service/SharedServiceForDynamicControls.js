/**
 * 
 */

app.service('sharedServiceForDynamicControls', function () {

    var data = {
        dataFor: ''
    };

    return {
        getDataFor: function () {
            return data.dataFor;
        },
        setDataFor: function (dataFor) {
            data.dataFor = dataFor;
        }
    };
});
