<!DOCTYPE html>
<html>
<head>
    <#include "../common.ftl">
</head>
<body class="childrenBody">
<form class="layui-form" style="width:80%;">
<#--    <input name="id" type="hidden" value="${(bookinfo.isbn)!}"/>-->



    <div class="layui-form-item layui-row">
        <div class="layui-col-xs6">
            <label class="layui-form-label">馆藏地址</label>
            <div class="layui-input-block">
                <select name="bookLocation"  id="bookLocation">
                    <option value="" >请选择</option>
                    <option value="0">三水图书馆</option>
                    <option value="1">广州图书馆</option>
                </select>
            </div>
        </div>
        <div class="layui-col-xs6">
            <label class="layui-form-label">入库数量</label>
            <div class="layui-input-block">
                <input type="text" class="layui-input"
                       name="amount" placeholder="请输入数量">
            </div>
        </div>
    </div>







    <br/>
    <div class="layui-form-item layui-row layui-col-xs12">
        <div class="layui-input-block">
            <button class="layui-btn layui-btn-lg" lay-submit="addOrUpdateBook"
                    lay-filter="addOrUpdateBook">确认
            </button>
            <button class="layui-btn layui-btn-lg layui-btn-normal">取消</button>
        </div>
    </div>
</form>
<script type="text/javascript" src="${ctx}/js/bookStock/add_stock.js"></script>
</body>
</html>