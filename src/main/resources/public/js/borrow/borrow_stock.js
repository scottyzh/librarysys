layui.use(['table', 'layer', "form"], function () {
    var layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table;

    var tableIns = table.render({
        elem: '#userList',
        url: ctx + '/borrow/book_stock',
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
                bookId: $("input[name='bookId']").val(),
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
            case "stockInfo":
                openAddOrUpdateBookStock(table.checkStatus(obj.config.id).data);
                break;
            case "del":
                delUser(table.checkStatus(obj.config.id).data);
                break;
        }
    });

    table.on('tool(users)',function (obj) {
        var layEvent =obj.event;
        if(layEvent === "select"){
            layer.confirm("确认选择该书?",{icon: 3, title: "库存详情"},function (index) {
                $.post(ctx+"/borrow/borrow_book",{bookId:obj.data.bookId},function (data) {
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
