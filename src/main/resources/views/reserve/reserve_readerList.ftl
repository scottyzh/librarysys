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
                    <input type="text" name="isbn" class="layui-input searchVal" placeholder="isbn"/>
                </div>
                <div class="layui-input-inline">
                    <input type="text" name="reserveId" class="layui-input searchVal" placeholder="预约号"/>
                </div>
                <div class="layui-input-inline">
                    <select name="status" class="layui-input searchVal">
                        <option value="">状态</option>
                        <option value="0">等待取书</option>
                        <option value="1">已取书</option>
                        <option value="2">预约失效</option>
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
        <a class="layui-btn layui-btn-xs" id="edit" lay-event="cancel">取消预约</a>
    </script>
</form>
<script type="text/javascript" src="${ctx}/js/reserve/reserve_readerList.js"></script>

</body>
</html>