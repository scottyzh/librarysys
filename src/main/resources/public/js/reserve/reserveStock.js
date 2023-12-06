layui.use(['table', 'layer', "form"], function () {
    var layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table;

    var tableIns = table.render({
        elem: '#userList',
        url: ctx + '/reserve/list',
        cellMinWidth: 95,
        page: true,
        height: "full-125",
        limits: [10, 15, 20, 25],
        limit: 10,
        toolbar: "#toolbarDemo",
        id: "userListTable",
        cols: [[
            {field: 'bookId', title: '图书ID', minWidth: 50, align: "center"},
            {field: 'status', title: '状态', align: 'center', templet : function(data) {// 替换数据
                    if(data.status==0){
                        return "未借";
                    }else if(data.status==1){
                        return "被借";
                    }
                }},

            {field: 'bookLocation', title: '馆藏地址', align: 'center', templet : function(data) {// 替换数据
                    if(data.bookLocation==0){
                        return "三水";
                    }else if(data.bookLocation==1){
                        return "广州";
                    }else if(data.bookLocation==2){
                        return "三水、广州";
                    }else if(data.bookLocation==3){
                        return "无库存";
                    }
                }},
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
                bookId: $("input[name='bookId']").val(),// 图书id
                bookLocation: $("select[name='bookLocation']").val(),// 馆藏地址
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

    // // 编辑 删除选项
    // table.on('tool(users)', function (obj) {
    //     var layEvent = obj.event;
    //     if (layEvent === "select") {
    //         // openAddOrUpdateUserDialog(obj.data.id);
    //
    //         layer.confirm("确认选择该书?", {icon: 3, title: "借阅"}, function (index) {
    //               $.post(ctx+"/reserve/selectBook",{id:obj.data.bookId},function (data) {
    //                         alert(obj.data.bookId)
    //                         if (data.code == 200) {
    //                             layer.msg("选择成功");
    //                             tableIns.reload();
    //                         } else {
    //                             layer.msg(data.msg);
    //                         }
    //                     })
    //                 })
    //     }
    // });
    //
    // 编辑 删除选项
    table.on('tool(users)',function (obj) {
        var layEvent =obj.event;
        if(layEvent === "remind"){
            alert("你点击了此按钮")
        }else if(layEvent === "select"){
            layer.confirm("确认选择该书?",{icon: 3, title: "库存详情"},function (index) {
                $.post(ctx+"/reserve/selectBook",{bookId:obj.data.bookId},function (data) {
                    if(data.code==200){
                        layer.msg("确认成功");
                        parent.location.reload();
                        layer.close(index);
                    }else{
                        layer.msg(data.msg);
                    }
                })
            })

        }
    });
});
