"use strict";

var _extends = Object.assign || function(target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i]; for (var key in source) { if (Object.prototype.hasOwnProperty.call(source, key)) { target[key] = source[key]; } } } return target; };

function _defineProperty(obj, key, value) { if (key in obj) { Object.defineProperty(obj, key, { value: value, enumerable: true, configurable: true, writable: true }); } else { obj[key] = value; } return obj; }

// sessionStorage.token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiIxIiwidXNlck5hbWUiOiJhZG1pbiIsInByb3BlcnR5SWQiOiIxIiwiaWF0IjoxNTQwNTE3NDU3LCJleHAiOjE1NDA1NDc0NTd9.s2f0-O0YPOtgV-huiE38T_th__M1BCVTK82ksnLOP1o";
var conIndex = 1;
var linkageindex = 0;
var linkageArg = null;
var commonUrl = "";
var pageUrl = "/";
var $ = layui.$;
var tableIns;
var form = layui.form;
var table = layui.table;
var layer = layui.layer;
var laytpl = layui.laytpl;
var laydate = layui.laydate;
var element = layui.element;
var layedit = layui.layedit;
var cascader = layui.cascader;
var upload = layui.upload;
var carousel = layui.carousel;

function myAjax(_ref, callback) {
    var url = _ref.url,
        async = _ref.async,
        data = _ref.data || {};

    data.token = sessionStorage.token;
    $.ajax(commonUrl + url, {
        data: decodeURIComponent($.param(data)).replace(/\[([^ \[\]]*?[^ \[\]\d]+?[^ \[\]]*?)\]/g, ".$1"),
        type: 'POST',
        async: async,
        success: function(result) {
            if (result.code == 3) {
                window.location.href = "/";
            }
            if (result.code == 0) {
                layer.msg(result.msg, { icon: 5 });
            }

            callback(result);
        }
    });

}

var searchForm = {
    init: function(containerDiv, url) {
        myAjax({
            url: url,
        }, function(result) {
            laytpl(optionTpl.innerHTML).render(result.data, function(html) {
                document.getElementById(containerDiv).innerHTML = html;
            });
            form.render();
        });
    },
    submit: function(name, url) {
        form.on(name, function(data) {
            data.field.token = sessionStorage.token;
            if (data.field.unitorgid) {
                data.field.orgid = sessionStorage.unitOrgid || JSON.parse(sessionStorage.loginData).orgid;
            }
            // else if(data.field.judgeLinkage) {
            //             //     if(data.field.orgid==JSON.parse(sessionStorage.loginData).orgid) data.field.orgid = '';
            //             //     if(data.field.orgid==getUrlParam("orgid")) data.field.orgid = '';
            //             // }
            // else {
            //     data.field.orgid=JSON.parse(sessionStorage.loginData).orgid;
            // }

            console.log(data.field);
            tableIns.reload({
                where: data.field,
                page: {
                    curr: 1
                },
                done: function(result) {
                    try {
                        if (userLevel) {
                            switch (userLevel) {
                                case 1:
                                    $("#addCityBtn").show();
                                    $("#addProvinceBtn").hide();
                                    $("#addSchoolBtn").hide();
                                    break;
                                case 2:
                                    $("#addCityBtn").hide();
                                    $("#addProvinceBtn").show();
                                    $("#addSchoolBtn").hide();
                                    break;
                                default:
                                    $("#addCityBtn").hide();
                                    $("#addProvinceBtn").hide();
                                    $("#addSchoolBtn").show();
                                    break;
                            }
                        }
                    } catch (e) {

                    }
                    if (result.code == 200) {

                        layer.msg('查询成功', { icon: 1 });
                    } else {
                        layer.msg('查询失败：' + result.msg, { icon: 5 });
                    }
                }
            });

            return false;
        });

    },
    linkage: function(psendData) {

        myAjax({
            url: "/selectcount/linkAge",
            data: {
                orgid: psendData
            }
        }, function(relust) {
            // if(linkageArg == psendData) return;
            if (psendData == JSON.parse(sessionStorage.loginData).orgid && linkageindex > 0) return;
            if (psendData == getUrlParam("orgid") && linkageindex > 0) return;
            linkageArg = psendData;
            var selectstar = '<div class="linkageelem" style="width: 150px;display: inline-block;"><input type="text" name="judgeLinkage" value="1" style="display: none">' +
                '<select lay-search="" data-index="' + linkageindex + '" class="linkageSelect" name="orgid" lay-filter="layLinkage">' +
                '<option value="' + psendData + '" disabled selected>请选择</option>';
            var selectend = '</select></div>';
            var Option;
            if (relust.data.length != 0) {
                linkageindex++;
                for (var i = 0; i < relust.data.length; i++) {
                    Option += "<option value=" + relust.data[i].orgid + ">" + relust.data[i].orgname + "</option>";
                }
            } else {
                Option = "<option value='' disabled>无下级机构</option>"
            }
            var linkageSelect = selectstar + Option + selectend;
            $("#linkDiv").append(linkageSelect);
            form.render('select');
            // $(".layui-select-title").on("change",function(){
            //     // console.log(1);
            // })
        });
        form.on('select(layLinkage)', function(data) {
            var targetindex;
            $($(data.elem).find("option")[0]).attr("disabled", "disabled");
            $(".linkageSelect").each(function(index) {
                if (data.elem == this) {
                    targetindex = index;
                }
            });
            var calc = $(".linkageelem").length - 1;
            for (var i = calc; i > targetindex; i--) {
                $($(".linkageelem")[i]).remove();
            }
            searchForm.linkage(data.value);
        });
    }

}
var myTable = {
    list: function list(_ref2) {
        var elem = _ref2.elem,
            data = _ref2.data,
            url = _ref2.url,
            title = _ref2.title,
            toolbar = _ref2.toolbar,
            cols = _ref2.cols,
            totalRow = _ref2.totalRow,
            // async = _ref2.async,
            myParseData = _ref2.myParseData || function() {},
            done = _ref2.done || function() {},
            postData = _ref2.postData;

        tableIns = table.render({
            elem: elem,
            title: title,
            data: data,
            totalRow: totalRow,
            // async:async,
            method: "post",
            where: _extends({
                token: sessionStorage.token
            }, postData),
            text: {
                none: '暂无相关数据' //默认：无数据。
            },
            request: {
                pageName: 'pageNumber' //页码的参数名称，默认：page
                    ,
                limitName: 'pageSize' //每页数据量的参数名，默认：limit
            },
            response: {
                statusCode: 200 //规定成功的状态码，默认：0
            },
            toolbar: toolbar || true,
            url: commonUrl + url //数据接口
                ,
            page: true //开启分页
                ,
            parseData: function(result) {
                if (result.code == 200) {
                    myParseData(result);
                    return {
                        "code": result.code, //解析接口状态
                        "msg": result.msg, //解析提示文本
                        "count": result.data.totalRow, //解析数据长度
                        "data": result.data.list //解析数据列表
                    };
                } else {
                    layer.msg(result.msg, { icon: 5 });
                }
            },
            cols: cols,
            done: function(result) {
                if (result.code == 200) {
                    done(result);
                } else {
                    if (result.msg) {
                        layer.msg(result.msg, { icon: 5 });
                    }
                }
            }
        });
    },
    //编辑单元格
    edit: function edit(url, postData) {
        table.on('edit(mainTable)', function(obj) {
            var value = obj.value //得到修改后的值
                ,
                data = obj.data //得到所在行所有键值
                ,
                field = obj.field; //得到字段
            layer.open({
                title: '操作',
                // type: 1,
                content: '确认更改成：' + value + '吗？',
                btn: ['确认', '取消'],
                btnAlign: 'c',
                yes: function yes(index) {
                    layer.close(index);
                    myAjax({
                        url: url,
                        dataType: 'json',
                        data: {
                            bean: _defineProperty({
                                id: obj.data.orgid
                            }, field, obj.value)
                        }
                    }, function(result) {
                        if (result.code == 200) {
                            layer.msg('操作成功', { icon: 1 });
                            tableIns.reload({
                                page: {
                                    curr: 1
                                },
                                done: function() {
                                    try {
                                        if (userLevel) {
                                            switch (userLevel) {
                                                case 1:
                                                    $("#addCityBtn").show();
                                                    $("#addProvinceBtn").hide();
                                                    $("#addSchoolBtn").hide();
                                                    break;
                                                case 2:
                                                    $("#addCityBtn").hide();
                                                    $("#addProvinceBtn").show();
                                                    $("#addSchoolBtn").hide();
                                                    break;
                                                default:
                                                    $("#addCityBtn").hide();
                                                    $("#addProvinceBtn").hide();
                                                    $("#addSchoolBtn").show();
                                                    break;
                                            }
                                        }
                                    } catch (e) {
                                        // var userLevel;
                                    }
                                }
                            });
                        }
                    });
                }
            });
        });
    },
    //弹窗编辑单元格
    editDialog: function(data) {
        var editdialogIndex = layer.open({
            type: 1,
            title: "增加",
            area: ['500px', 'auto'],
            content: $("#areaoptionView"),
            shade: [0],
            success: function(dialogEle) {
                // console.log(dialogEle);
                form.render();
            }
        });

    },
    //删除单元格
    detele: function(that, url) {
        layer.open({
            title: '操作',
            // type: 1,
            content: '确认删除吗？',
            btn: ['确认', '取消'],
            btnAlign: 'c',
            yes: function yes(index) {
                layer.close(index);
                console.log(that.dataset.id);
                myAjax({
                    url: url,
                    dataType: 'json',
                    data: {
                        id: that.dataset.id
                    }
                }, function(result) {
                    if (result.code == 200) {
                        layer.msg('操作成功', { icon: 1 });
                        tableIns.reload({
                            page: {
                                curr: 1
                            }
                        });
                    } else {
                        layer.msg('操作失败：' + result.msg, { icon: 5 });
                    }
                });
            }
        });
    },
    //删除多行
    deteleMany: function() {
        console.log(arrDetele, 2);
        layer.open({
            title: '操作',
            // type: 1,
            content: '确认删除吗？',
            btn: ['确认', '取消'],
            btnAlign: 'c',
            yes: function yes(index) {
                console.log(arrDetele, 3);
                layer.close(index);

                $.ajax({
                    url: "/notice/delete",
                    type: "POST",
                    data: { id: arrDetele, token: JSON.parse(sessionStorage.loginData).token },
                    traditional: true, //这里设置为true
                    success: function(result) {
                        if (result.code == 200) {
                            layer.msg('操作成功', { icon: 1 });
                            tableIns.reload({
                                page: {
                                    curr: 1
                                }
                            });
                        } else {
                            layer.msg('操作失败：' + result.msg, { icon: 5 });
                        }
                    }
                });


            }
        });
    },
    //重置密码
    resetPassword: function resetPassword(that, url) {
        layer.open({
            title: '操作',
            // type: 1,
            content: '确认重置密码为：“123456” 吗？',
            btn: ['确认', '取消'],
            btnAlign: 'c',
            yes: function yes(index) {
                layer.close(index);
                myAjax({
                    url: url,
                    dataType: 'json',
                    data: {
                        bean: {
                            fid: that.dataset.fid
                        }
                    }
                }, function(result) {
                    if (result.code == 200) {
                        layer.msg('操作成功：密码已重置为 “123456”', { icon: 1 });
                        tableIns.reload({
                            page: {
                                curr: 1
                            }
                        });
                    } else {
                        layer.msg('操作失败：' + result.msg, { icon: 5 });
                    }
                });
            }
        });
    },

    //增加表单
    addCell: function addCell(url, elem) {
        elem = elem ? elem : 'addCell';
        var dialogIndex = layer.open({
            type: 1,
            title: "增加",
            skin: "add-cell",
            area: ['380px', 'auto'],
            content: $('#' + elem)
        });
        form.on('submit(' + elem + ')', function(data) {
            myAjax({
                url: url,
                data: data.field
            }, function(result) {
                if (result.code == 200) {
                    $('#' + elem)[0].reset();
                    layer.close(dialogIndex);
                    layer.msg('操作成功', { icon: 1 });
                    tableIns.reload({
                        page: {
                            curr: 1
                        },
                        done: function() {
                            try {
                                if (userLevel) {
                                    switch (userLevel) {
                                        case 1:
                                            $("#addCityBtn").show();
                                            $("#addProvinceBtn").hide();
                                            $("#addSchoolBtn").hide();
                                            break;
                                        case 2:
                                            $("#addCityBtn").hide();
                                            $("#addProvinceBtn").show();
                                            $("#addSchoolBtn").hide();
                                            break;
                                        default:
                                            $("#addCityBtn").hide();
                                            $("#addProvinceBtn").hide();
                                            $("#addSchoolBtn").show();
                                            break;
                                    }
                                }
                            } catch (e) {

                            }
                        }
                    });
                } else {
                    layer.msg('操作失败：' + result.msg, { icon: 5 });
                }
            });
            return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
        });
    },
    //编辑提示
    editPrompt: function(str) {
        layer.msg(str, { icon: 6 });
    }
};

//获取url参数
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}
//请求级别
function judgeLevel(userOrgid) {

    myAjax({ url: "/org/orgLevel", data: { orgid: userOrgid }, async: false }, function(result) {
        if (result.code == 200) {
            linkuserLevel = result.data.orgtypeid;
        } else {
            layer.msg(result.msg, { icon: 5 });
        }
    });


}