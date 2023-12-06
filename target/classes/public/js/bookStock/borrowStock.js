layui.use(['table', 'layer', "form"], function () {
    var layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table;

    var tableIns = table.render({
        elem: '#userList',
        url: ctx + '/bookStock/list',
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
                        return "可借";
                    }else if(data.status==1){
                        return "借出";
                    }
                    else if(data.status==2){
                        return "出库";
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
        ]]
    });


    // 多条件搜索
    $(".search_btn").on("click", function () {
        table.reload("userListTable", {
            page: {
                curr: 1
            },
            where: {
                bookId: $("input[name='bookId']").val(),
                status: $("select[name='status']").val(),
                bookLocation: $("select[name='bookLocation']").val()
            }
        })
    });


    // 头工具栏事件
    table.on('toolbar(users)', function (obj) {
        switch (obj.event) {
            case "add":
                openAddOrUpdateUserDialog();
                break;
            case "reduce":
                openReduceDialog();
                break;
            case "stockInfo":
                openAddOrUpdateBookStock(table.checkStatus(obj.config.id).data);
                break;
            case "transferBookToSS":
                transferBookToSS(table.checkStatus(obj.config.id).data);
                break;
            case "tansferToGZ":
                transferToGZ(table.checkStatus(obj.config.id).data);
                break;

        }
    });


    function transferBookToSS(datas) {
        /**
         * 批量删除
         *   datas:选择的待删除记录数组
         */
        if (datas.length == 0) {
            layer.msg("请选择待转移图书!");
            return;
        }
        layer.confirm("确定选中图书？",{
            btn:['确定','取消']
        },function (index) {
            layer.close(index);
            // var ids = datas
            var ids="ids=";
            for(var i=0;i<datas.length;i++){
                if(i<datas.length-1){
                    ids=ids+datas[i].bookId+"&ids=";
                }else{
                    ids=ids+datas[i].bookId;
                }
            }
            $.ajax({
                type: "post",
                url: ctx + "/bookStock/transferToSS",
                data: ids,
                dataType: "json",
                success: function (data) {
                    if (data.code == 200) {
                        layer.msg("转移馆藏成功");
                        tableIns.reload();
                    } else {
                        layer.msg(data.msg);
                    }
                }
            })
        })
    }

    function transferToGZ(datas) {
        /**
         * 批量删除
         *   datas:选择的待删除记录数组
         */
        if (datas.length == 0) {
            layer.msg("请选择待转移图书!");
            return;
        }
        layer.confirm("确定选中图书？",{
            btn:['确定','取消']
        },function (index) {
            layer.close(index);
            // var ids = datas
            var ids="ids=";
            for(var i=0;i<datas.length;i++){
                if(i<datas.length-1){
                    ids=ids+datas[i].bookId+"&ids=";
                }else{
                    ids=ids+datas[i].bookId;
                }
            }
            $.ajax({
                type: "post",
                url: ctx + "/bookStock/transferToGZ",
                data: ids,
                dataType: "json",
                success: function (data) {
                    if (data.code == 200) {
                        layer.msg("转移馆藏成功");
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
            openAddOrUpdateUserDialog(obj.data.id);
        } else if (layEvent === "del") {
            layer.confirm("确认出库该书?", {icon: 3, title: "图书管理"}, function (index) {
                $.post(ctx + "/bookStock/deleteStock", {bookId: obj.data.bookId}, function (data) {
                    if (data.code == 200) {
                        layer.msg("出库成功");
                        tableIns.reload();
                    } else {
                        layer.msg(data.msg);
                    }
                })
            })
        }
    });


    //弹出框
    function openAddOrUpdateUserDialog(id) {
        var title = "增加库存";
        var url = ctx + "/bookStock/toAddStock";
        if (id) {
            title = "增加库存";
            url = url + "?id=" + id;
        }
        layui.layer.open({
            title: title,
            type: 2,
            area: ["650px", "300px"],
            maxmin: true,
            content: url
        })
    }

    function openReduceDialog(id) {
        var title = "减少库存";
        var url = ctx + "/bookStock/toReduceStock";
        if (id) {
            title = "减少库存";
            url = url + "?id=" + id;
        }
        layui.layer.open({
            title: title,
            type: 2,
            area: ["650px", "300px"],
            maxmin: true,
            content: url
        })
    }



});
