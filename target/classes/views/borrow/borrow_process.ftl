<!DOCTYPE html>
<html>
<head>
    <title>用户管理</title>
    <#include "../common.ftl">
</head>
<body class="childrenBody">
<form class="layui-form">
    <blockquote class="layui-elem-quote quoteBox">
        <form class="layui-form">
            <div class="layui-inline">
                <div class="layui-input-inline">
                    <p>ISBN：${(isbn)!"获取错误"}&nbsp;</p>
                    <p>读者ID：${(readerId)!"获取错误"}&nbsp;</p>
                </div>
                <div class="layui-input-inline">
                    <input type="text" name="bookId" class="layui-input searchVal" placeholder="图书ID"/>
                </div>
                <div class="layui-input-inline ">
                    <select name="bookLocation" class="layui-input searchVal">
                        <option value="">馆藏位置</option>
                        <option value="0">三水</option>
                        <option value="1">广州</option>
                    </select>
                </div>
                <a class="layui-btn search_btn" data-type="reload"><i
                            class="layui-icon">&#xe615;</i> 搜索</a>
            </div>
        </form>
    </blockquote>
    <table id="userList" class="layui-table" lay-filter="users"></table>
    <!--操作-->
    <script id="userListBar" type="text/html">
        <a class="layui-btn layui-btn-xs" id="edit" lay-event="select">选择该书</a>
    </script>
</form>
<script type="text/javascript" src="${ctx}/js/borrow/borrow_stock.js"></script>

</body>
</html>