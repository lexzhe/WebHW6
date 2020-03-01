window.notify = function(message) {
    $.notify(message, {position: "bottom right"})
};

ajax = function(options, error) {
    var helper = {
        type : "POST",
        url : "",
        dataType : "json",
        success: function (response) {
            if(response["error"]) {
                error.text(response["error"]);
            } else {
                location.href = response["redirect"];
            }
        }
    };
    helper = jQuery.extend(options, helper);
    return $.ajax(helper);
};
