$(function () {
    $("#permissionsForm").validate({
        errorElement:"span",
        rules:{
            permission:{
                required:true
            },
            description:{
                required:true
            }
        },
        messages:{
            permission:"权限名称必须填写",
            description:"权限描述必须填写"
        }
    });
});