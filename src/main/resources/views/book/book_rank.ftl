<!DOCTYPE html>
<html>
<head>
    <title>图书排行</title>
    <#include "../common.ftl">
</head>
<body class="childrenBody">
<form class="layui-form">
    <form class="layui-form">
            <div class="layui-inline">
                    <div class="layui-input-inline" lay-size="35">
                    <input type="text" name="author" class="layui-input searchVal" placeholder="作者"/>
                    </div>
                    <label class="layui-form-label">排序方式：</label>
                    <div class="layui-input-inline" lay-size="35">
                        <select name="order"  id="level">
                            <option value=1 >请选择</option>
                            <option value=1>降序</option>
                            <option value=2>升序</option>
                        </select>
                    </div>
            </div>
        <a class="layui-btn search_btn" data-type="reload"><i
                    class="layui-icon">&#xe615;</i> 查询</a>
    </form>
<#--    <a class="layui-btn search_btn" data-type="reload"><i-->
<#--                class="layui-icon">&#xe615;</i> 查询</a>-->

    <table id="userList" class="layui-table" lay-filter="users"></table>

    <script type="text/html" id="toolbarDemo">
        <div class="layui-btn-container">
        </div>
    </script>
    <!--操作-->
    <script id="userListBar" type="text/html">
<#--        <a class="layui-btn layui-btn-xs" id="edit" lay-event="edit">预约</a>-->
    </script>
</form>
<script type="text/javascript" src="${ctx}/js/book/bookRank.js"></script>

</body>
</html>