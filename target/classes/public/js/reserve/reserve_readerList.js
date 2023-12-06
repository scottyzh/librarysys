layui.use(['table', 'layer', "form"], function () {
    var layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table;
    //借阅列表展示
    var tableIns = table.render({
        elem: '#userList',
        url: ctx + '/reserve/reserveReaderList',
        cellMinWidth: 95,
        page: true,
        height: "full-125",
        limits: [10, 15, 20, 25],
        limit: 10,
        toolbar: "#toolbarDemo",
        id: "userListTable",
        cols: [[
            {field: "reserveId", title: '预约号', fixed: "true", width: 150},
            {field: 'readerId', title: '读者ID', minWidth: 50, align: "center"},
            {field: 'bookName', title: '图书名字', minWidth: 50, align: "center"},
            {field: 'isbn', title: 'ISBN', minWidth: 50, align: "center"},
            {field: 'readerIdentity', title: '身份', minWidth: 100, align: 'center', templet : function(data) {// 替换数据
                    if(data.readerIdentity==0){
                        return "学生";
                    }else if(data.readerIdentity==1){
                        return "老师";
                    }else if(data.readerIdentity==2){
                        return "图书管理员";
                    }else if(data.readerIdentity==3){
                        return "系统管理员";
                    }
                }},
            {field: 'reserveTime', title: '预约时间', minWidth: 100, align: 'center'},
            {field: 'presentStock', title: '当前库存', align: 'center'},
            {field: 'status', title: '状态', align: 'center', templet : function(data) {// 替换数据
                    if(data.status==0){
                        return "等待取书";
                    }else if(data.status==1){
                        return "已取书";
                    }
                    else if(data.status==2){
                        return "预约失效";
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
                isbn: $("input[name='isbn']").val(),// isbn
                readerId: $("input[name='readerId']").val(),//书名
                reserveId: $("input[name='reserveId']").val(),//库存，
                status: $("select[name='status']").val() , //状态
                readerIdentity: $("select[name='readerIdentity']").val()  //状态
            }
        })
    });


    // 头工具栏事件
    table.on('toolbar(users)', function (obj) {
        alert("点")
        switch (obj.event) {
            case "process":
                openBookStockInfo(table.checkStatus(obj.config.id).data);
                break;
            case "del":
                delUser(table.checkStatus(obj.config.id).data);
                break;
        }
    });




    // 编辑 删除选项
    table.on('tool(users)', function (obj) {
        var layEvent = obj.event;
        if (layEvent === "process") {
            openBookStockInfo(obj.data);
        }else if(layEvent === "cancel"){
            layer.confirm("确认取消预约?",{icon: 3, title: "预约信息"},function (index) {
                $.post(ctx+"/reserve/cancel",{reserveId:obj.data.reserveId},function (data) {
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

    //更新表
    function reloadtable () {
        table.reload("userListTable", {
            page: {
                curr: 1
            },
            where: {
                isbn: $("input[name='isbn']").val(),// isbn
                readerId: $("input[name='readerId']").val(),//书名
                status: $("input[name='status']").val() , //状态
                operator: $("input[name='operator']").val(),//操作员
                fine: $("select[name='fine']").val(), //罚款
            }
        })
    }
    //弹出框--库存信息
    function openBookStockInfo(data) {
        var title = "图书库存记录";
        layui.layer.open({
            title: title,
            type: 2,
            area: ["900px", "600px"],
            maxmin: true,
            content: ctx + "/reserve/toStock?isbn=" + data.isbn +"&readerId="+data.readerId+"&reserveId="+data.reserveId
        })
        reloadtable()
    }
});
