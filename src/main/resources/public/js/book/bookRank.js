layui.use(['table', 'layer', "form"], function () {
    var layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table;
    //图书排行榜展示
    var tableIns = table.render({
        elem: '#userList',
        url: ctx + '/bookRank/rankList',
        cellMinWidth: 95,
        page: true,
        height: "full-125",
        limits: [10, 15, 20, 25],
        limit: 10,
        toolbar: "#toolbarDemo",
        id: "userListTable",
        cols: [[
            {field:'selfIncreasing', width:50, title: '排名',fixed: 'left',type: 'numbers'},
            {field: "isbn", title: 'ISBN', fixed: "true", width: 150},
            {field: 'bookName', title: '图书名', minWidth: 50, align: "center"},
            {field: 'categoryId', title: '类别', minWidth: 50, align: "center", templet : function(data) {// 替换数据
                    if(data.categoryId==1){
                        return "马克思主义";
                    }else if(data.categoryId==2){
                        return "哲学、宗教";
                    }else if(data.categoryId==3){
                        return "社会科学理论";
                    }else if(data.categoryId==4){
                        return "政治、法律";
                    }else if(data.categoryId==5){
                        return "政治、法律";
                    }else if(data.categoryId==5){
                        return "军事";
                    }else if(data.categoryId==6){
                        return "经济";
                    }else if(data.categoryId==7){
                        return "文化、科学、教育、体育";
                    }else if(data.categoryId==8){
                        return "语言、文字";
                    }else if(data.categoryId==9){
                        return "文学";
                    }else if(data.categoryId==10){
                        return "艺术";
                    }else if(data.categoryId==11){
                        return "历史、地理";
                    }else if(data.categoryId==12){
                        return "自然科学总论";
                    }else if(data.categoryId==13){
                        return "数理科学与化学";
                    }else if(data.categoryId==14){
                        return "天文学、地球科学";
                    }else if(data.categoryId==15){
                        return "生物科学";
                    }else if(data.categoryId==16){
                        return "医药、卫生";
                    }else if(data.categoryId==17){
                        return "农业科学";
                    }else if(data.categoryId==18){
                        return "工业技术";
                    }else if(data.categoryId==19){
                        return "交通运输";
                    }else if(data.categoryId==20){
                        return "航空、航天";
                    }else if(data.categoryId==21){
                        return "环境科学、安全科学";
                    }else if(data.categoryId==22){
                        return "综合性图书";
                    }
                }
            },
            {field: 'author', title: '作者', minWidth: 100, align: 'center'},
            {field: 'publisher', title: '出版社', minWidth: 100, align: 'center'},
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
            {field: 'presentStock', title: '当前库存', align: 'center'},
            {field: 'totalStock', title: '馆藏', align: 'center'},
            {field: 'status', title: '状态', align: 'center', templet : function(data) {// 替换数据
                    if(data.status==0){
                        return "正常";
                    }else if(data.status==1){
                        return "暂停借阅";
                    }
                }},
            {field: 'borrowTimesInThreeMonths', title: '借阅次数', align: 'center'},
        ]]
    });



    // 多条件搜索
    $(".search_btn").on("click", function () {
        table.reload("userListTable", {
            page: {
                curr: 1
            },
            where: {
                author: $("input[name='author']").val(),// 作者
                order: $("select[name='order']").val(),// 排序依据
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
            openAddOrUpdateUserDialog(obj.data.id);
        } else if (layEvent === "del") {
            layer.confirm("确认删除当前记录?", {icon: 3, title: "用户管理"}, function (index) {
                $.post(ctx + "/user/delete", {ids: obj.data.id}, function (data) {
                    if (data.code == 200) {
                        layer.msg("删除成功");
                        tableIns.reload();
                    } else {
                        layer.msg(data.msg);
                    }
                })
            })
        }
    });

    //弹出框
    function openAddOrUpdateBookStock(data) {
        // console.log(data[0].id)
        if (data.length == 0) {
            layer.msg("请选择需要查看的图书!");
            return;
        }
        if (data.length > 1) {
            layer.msg("不支持批量查看!");
            return;
        }
        var title = "图书库存记录";
        // alert(data[0].isbn)
        layui.layer.open({
            title: title,
            type: 2,
            area: ["800px", "600px"],
            maxmin: true,
            content: ctx + "/bookStock/info?isbn=" + data[0].isbn
        })
    }

    //弹出框
    function openAddOrUpdateUserDialog(id) {
        var title = "图书入库";
        var url = ctx + "/book/toAddBook";
        if (id) {
            title = "图书入库";
            url = url + "?id=" + id;
        }
        layui.layer.open({
            title: title,
            type: 2,
            area: ["700px", "500px"],
            maxmin: true,
            content: url
        })
    }
});
