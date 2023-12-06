<!DOCTYPE html>
<html>
<head>
    <title>借阅管理</title>
    <#include "../common.ftl">
</head>
<body class="childrenBody">
<form class="layui-form">
    <blockquote class="layui-elem-quote quoteBox">
        <form class="layui-form">
            <div class="layui-inline">
                <div class="layui-input-inline">
                    <input type="text" name="bookId" class="layui-input searchVal" placeholder="图书ID"/>
                </div>
                <div class="layui-input-inline">
                    <select name="fineFin" class="layui-input searchVal">
                        <option value="">交纳状态</option>
                        <option value="0">未交纳</option>
                        <option value="1">已交纳</option>
                    </select>
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
        <a class="layui-btn layui-btn-xs" id="edit" lay-event="renew">续借</a>
    </script>
</form>
<script type="text/javascript" src="${ctx}/js/borrow/my_fine_list.js"></script>

</body>
</html>