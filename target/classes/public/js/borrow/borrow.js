layui.use(['table', 'layer', "form"], function () {
    var layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table;
    //图书列表展示
    var tableIns = table.render({
        elem: '#userList',
        url: ctx + '/book/list',
        cellMinWidth: 95,
        page: true,
        height: "full-125",
        limits: [10, 15, 20, 25],
        limit: 10,
        toolbar: "#toolbarDemo",
        id: "userListTable",
        cols: [[
            // {type: "checkbox", fixed: "left", width: 50},
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
            {field: 'enterTime', title: '入库时间', align: 'center', width: 120},
            {field: 'totalStock', title: '总库存', align: 'center'},
            {field: 'presentStock', title: '当前库存', align: 'center'},
            {field: 'status', title: '状态', align: 'center', templet : function(data) {// 替换数据
                    if(data.status==0){
                        return "正常";
                    }else if(data.status==1){
                        return "暂停借阅";
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
                isbn: $("input[name='isbn']").val(),
                bookName: $("input[name='bookName']").val(),
                author: $("input[name='author']").val()
            }
        })
    });

    // 编辑 删除选项
    table.on('tool(users)', function (obj) {
        openBookStockInfo(obj.data);
    });

    //弹出框--库存信息
    function openBookStockInfo(data) {
        var readerId = prompt("请输入读者ID：");
        var title = "图书库存记录";
        layui.layer.open({
            title: title,
            type: 2,
            area: ["900px", "600px"],
            maxmin: true,
            content: ctx + "/borrow/toStock?isbn=" + data.isbn + "&readerId=" + readerId
        })
    }
})
