layui.use(['table', 'layer', "form"], function () {
    var layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table;
    //用户列表展示
    var tableIns = table.render({
        elem: '#userList',
        url: ctx + '/user/list',
        cellMinWidth: 95,
        page: true,
        height: "full-125",
        limits: [10, 15, 20, 25],
        limit: 10,
        toolbar: "#toolbarDemo",
        id: "userListTable",
        cols: [[
            // {type: "checkbox", fixed: "left", width: 50},
            {field: "userId", title: '用户ID', fixed: "true", width: 150},
            {field: 'userName', title: '用户名', minWidth: 50, align: "center"},
            {field: 'phone', title: '电话', minWidth: 100, align: 'center'},
            {field: 'identity', title: '身份', minWidth: 100, align: 'center', templet : function(data) {// 替换数据
                    if(data.identity==0){
                        return "学生";
                    }else if(data.identity==1){
                        return "老师";
                    }else if(data.identity==2){
                        return "图书管理员";
                    }else if(data.identity==3){
                        return "系统管理员";
                    }
                }},
            {field: 'status', title: '状态', align: 'center', templet : function(data) {// 替换数据
                    if(data.status==0){
                        return "正常";
                    }else if(data.status==1){
                        return "挂失";
                    }else if(data.status==2){
                        return "注销";
                    }else if(data.status==3){
                        return "暂停借阅";
                    }
                }},
            {field: 'createTime', title: '创建时间', align: 'center'},
            {title: '操作', minWidth: 150, templet: '#userListBar', fixed: "right", align: "center"}
        ]]
    });


    // 多条件搜索
    $(".search_btn").on("click", function () {
        table.reload("userListTable", {
            page: {
                curr: 1
            },
            where: {
                userId: $("input[name='userId']").val(),// 用户ID
                userName: $("input[name='userName']").val(),// 用户名
                status: $("select[name='status']").val(),//用户状态
            }
        })
    });


    // 头工具栏事件
    table.on('toolbar(users)', function (obj) {
        switch (obj.event) {
            case "add":
                openAddOrUpdateUserDialog();
                break;
            case "stockInfo":
                openAddOrUpdateBookStock(table.checkStatus(obj.config.id).data);
                break;
            case "del":
                delUser(table.checkStatus(obj.config.id).data);
                break;

        }
    });


    function delUser(datas) {
        /**
         * 批量删除
         *   datas:选择的待删除记录数组
         */
        if (datas.length == 0) {
            layer.msg("请选择待删除记录!");
            return;
        }
        layer.confirm("确定删除选中的记录", {
            btn: ['确定', '取消']
        }, function (index) {
            layer.close(index);
            // ids=10&ids=20&ids=30
            var ids = "ids=";
            for (var i = 0; i < datas.length; i++) {
                if (i < datas.length - 1) {
                    ids = ids + datas[i].id + "&ids=";
                } else {
                    ids = ids + datas[i].id;
                }
            }
            $.ajax({
                type: "post",
                url: ctx + "/user/delete",
                data: ids,
                dataType: "json",
                success: function (data) {
                    if (data.code == 200) {
                        tableIns.reload();
                    } else {
                        layer.msg(data.msg);
                    }
                }
            })


        })
    }

    // 编辑 删除选项
    table.on('tool(users)', function (obj) {
        var layEvent = obj.event;
        if (layEvent === "edit") {
            UpdateUserDialog(obj.data.userId);
        } else if (layEvent === "status") {
            UpdateUserStatusDialog(obj.data.userId)
        }
    });

    //添加用户弹出框
    function openAddOrUpdateUserDialog(id) {
        var title = "用户管理-用户添加";
        var url = ctx + "/user/toAddUserPage";
        layui.layer.open({
            title: title,
            type: 2,
            area: ["700px", "500px"],
            maxmin: true,
            content: url
        })
    }

    //更新用户信息弹出框
    function UpdateUserDialog(id) {
        var title = "用户管理-更改信息";
        var url = ctx + "/user/toUpdateUserPage?userId=" + id;
        layui.layer.open({
            title: title,
            type: 2,
            area: ["700px", "500px"],
            maxmin: true,
            content: url
        })
    }
});
