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
                <a class="layui-btn search_btn" data-type="reload"><i
                            class="layui-icon">&#xe615;</i> 搜索</a>
            </div>
        </form>
    </blockquote>
    <table id="userList" class="layui-table" lay-filter="users"></table>

    <script type="text/html" id="toolbarDemo">
        <div class="layui-btn-container">
        </div>
    </script>
    <!--操作-->
    <script id="userListBar" type="text/html">
        <a class="layui-btn layui-btn-xs" id="edit" lay-event="borrow">借阅</a>
    </script>
</form>
<script type="text/javascript" src="${ctx}/js/borrow/borrow.js"></script>

</body>
</html>