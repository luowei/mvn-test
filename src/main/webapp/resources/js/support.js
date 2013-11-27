//删除确认对话框
function del() {
    if (confirm("确实要删除吗？")) {
        return true;
    }
    return false;
}

//后退
function back(){
    window.history.go(-1);
}

//消息div一定时间后隐藏
$(function(){
    $("div.messagePlace").fadeOut(5000);
});


//日历控件
$(function () {
    var accordionOpts = {
        navigation:true,
        autoHeight:false
    };

    var pickerOpts = {
        changeMonth:true,
        changeYear:true,
        yearRange:"1960:2022"
    };

    $(".datepicker").datepicker(pickerOpts);
    $.datepicker.setDefaults($.datepicker.regional['zh_CN']);

});


//字段排序
function sort(obj){
    var $obj = $(obj);
    var property = $obj.attr("id");
    var currentUrl = $obj.attr("class");
    var orders = currentUrl.substr(currentUrl.indexOf("orders=")).replace("orders=", "");
    var arr = orders.split(",");
    var url = currentUrl.substring(0, currentUrl.indexOf("orders="))+"orders=";

    for(var i=0; i<arr.length; i++){
        if(orders.search(property) != -1){
            if(arr[i] == property+":asc"){
                url = currentUrl.replace(arr[i], property+":desc");
            }else if(arr[i] == property+":desc"){
                url = currentUrl.replace(arr[i], "")
            }
        }else{
            url = url.replace("orders=", "orders="+property+":asc,");
        }
    }

    if(url.lastIndexOf(",") == url.length -1){
        url = url.substring(0, url.length-1);
    }

    window.location.href = url;
}

//显示隐藏排序指示标记
function direction(id){
    var $obj = $("#"+id);
    var url = $obj.attr("class");

    if(url.search(id+":asc")!=-1){
        $obj.children("i").removeClass().addClass("icon-arrow-up");
    }else if(url.search(id+":desc") != -1){
        $obj.children("i").removeClass().addClass("icon-arrow-down");
    }
}


//展开折叠菜单
function addMenuSub(obj){
    jQuery(obj).toggleClass("dt_undisplay");
    jQuery(obj).nextAll().toggle();
}


//展开折叠按钮
jQuery(function(){
    jQuery(".btn_all_show").click(function(){
        jQuery(".menu_sub dd").show();
        jQuery(".menu_sub dt").removeClass().addClass("dt_display");
    });
    jQuery(".btn_all_hide").click(function(){
        jQuery(".menu_sub dd").hide();
        jQuery(".menu_sub dt").removeClass().addClass("dt_undisplay");
    });
});


//显示隐藏菜单
jQuery(function(){
    var $btn_show_hide = jQuery(".btn_show_hide");
    $btn_show_hide.toggle(function(){
        jQuery(this).text("显示菜单");
        jQuery(".sidebar").hide();
        jQuery(".content").css({left:'0'});
    },function(){
        jQuery(this).text("隐藏菜单");
        jQuery(".sidebar").show();
        jQuery(".content").css({left:'200px'});
    });
});


/**
 * 全选功能
 * @param controlCheckboxId 控制全选checkbox的id
 * @param name checkbox名称
 */
function checkAll(controlCheckboxId, name){
    var $controlCheckbox = $("#" + controlCheckboxId);
    var $element = $("input[name="+name+"]");

    var checkedName = "input[name=" + name + "]:checked";

    $controlCheckbox.click(function () {
        if ($(this).attr("checked") == "checked") {
            $element.attr("checked", "checked");
        } else {
            $element.removeAttr("checked");
        }
    });

    var size = $element.length;

    $element.click(function () {
        var $checkedElement = $(checkedName);

        if (size == $checkedElement.length) {
            $controlCheckbox.attr("checked", "checked");
        }

        if (size > $checkedElement.length) {
            $controlCheckbox.removeAttr("checked");
        }
    });
}


/**
 *
 * @param buttonId
 * @param formId
 * @param formAction
 * @param checkboxName
 */
function checkBeforeBatchDelete(buttonId, formId, formAction, checkboxName){
    $("#" + buttonId).click(function () {
        if ($("input[name=" + checkboxName + "]:checked").length > 0) {
            if (del()) {
                $("#" + formId).attr("action", formAction).submit();
            }
        } else {
            alert("至少选择一个删除项");
        }
    });

}