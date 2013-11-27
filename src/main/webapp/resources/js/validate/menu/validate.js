$(function () {
    $("#menuForm").validate({
        errorElement:"span",
        rules:{
            name:{
                required:true
            },
            sequence:{
                required:true
            }
        },
        messages:{
            name:"菜单名称必须填写",
            sequence:"菜单次序必须填写"
        }
    });
});