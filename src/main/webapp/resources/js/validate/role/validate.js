$(function () {
    $("#roleForm").validate({
        errorElement:"span",
        rules:{
            name:{
                required:true
            },
            description:{
                required:true
            }
        },
        messages:{
            name:"角色名称必须填写",
            description:"角色描述必须填写"
        }
    });
});