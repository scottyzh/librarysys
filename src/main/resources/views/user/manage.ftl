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
                    <input type="text" name="userId" class="layui-input searchVal" placeholder="用户ID"/>
                </div>

                <div class="layui-input-inline">
                    <input type="text" name="userName" class="layui-input searchVal" placeholder="用户名"/>
                </div>

                <div class="layui-input-inline ">
                    <select name="status" class="layui-input searchVal">
                        <option value="">用户状态</option>
                        <option value="0">正常</option>
                        <option value="1">挂失</option>
                        <option value="2">注销</option>
                        <option value="3">暂停借阅</option>
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
            <a class="layui-btn layui-btn-normal addNews_btn" lay-event="add">
                <i class="layui-icon">&#xe608;</i>
                添加用户
            </a>
        </div>
    </script>
    <!--操作-->
    <script id="userListBar" type="text/html">
        <a class="layui-btn layui-btn-xs" id="edit" lay-event="edit">编辑信息</a>
    </script>
</form>
<script type="text/javascript" src="${ctx}/js/user/manage.js"></script>

</body>
</html>