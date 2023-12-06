<!DOCTYPE html>
<html>
<head>
    <title>图书管理</title>
    <#include "../common.ftl">
</head>
<body class="childrenBody">
<form class="layui-form">
    <blockquote class="layui-elem-quote quoteBox">
        <form class="layui-form">
            <div class="layui-inline">
                <div class="layui-input-inline">
                    <input type="text" name="isbn"
                           class="layui-input
					searchVal" placeholder="ISBN"/>
                </div>
                <div class="layui-input-inline">
                    <input type="text" name="bookName" class="layui-input
					searchVal" placeholder="书名"/>
                </div>
                <div class="layui-input-inline">
                    <input type="text" name="author" class="layui-input
					searchVal" placeholder="作者"/>
                </div>
                <div class="layui-input-inline">
                    <input type="text" name="publisher" class="layui-input
					searchVal" placeholder="出版社"/>
                </div>
                <a class="layui-btn search_btn" data-type="reload"><i
                            class="layui-icon">&#xe615;</i> 搜索</a>
            </div>
        </form>
    </blockquote>
    <table id="userList" class="layui-table" lay-filter="users"></table>

    <script type="text/html" id="toolbarDemo">
        <div class="layui-btn-container">
            <a class="layui-btn layui-btn-normal addNews_btn" lay-event="add">
                <i class="layui-icon">&#xe608;</i>
                新增图书条目
            </a>
        </div>
    </script>
    <!--操作-->
    <script id="userListBar" type="text/html">
        <a class="layui-btn layui-btn-xs" id="edit" lay-event="edit">编辑</a>
        <a class="layui-btn layui-btn-xs" lay-event="stockManage">库存</a>
        <a class="layui-btn layui-btn-xs" id="stop" lay-event="stop">暂停借阅</a>
        <a class="layui-btn layui-btn-xs" id="stop" lay-event="viewDetail">详情</a>

    </script>
</form>
<script type="text/javascript" src="${ctx}/js/book/bookInfo.js"></script>

</body>
</html>