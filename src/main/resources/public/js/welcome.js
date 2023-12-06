layui.use(['table', 'layer', "form"], function () {
    var layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table;
    //消息框显示
    var tableIns = table.render({
        elem: '#userList',
        url: ctx + '/userMsg/getMsg',
        cellMinWidth: 95,
        page: true,
        height: "full-125",
        limits: [10, 15, 20, 25],
        limit: 10,
        // toolbar: "#toolbarDemo",
        id: "userListTable",
        cols: [[
            {field: "createTime", title: '时间', width:350, fixed: "true", align: "center"},
            {field: 'msg', title: '消息', minWidth: 50, align: "center"},
        ]]
    });




})
