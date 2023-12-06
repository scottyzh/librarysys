<!DOCTYPE html>
<html>
<head>
    <#include "../common.ftl">
</head>
<body class="childrenBody">
<form class="layui-form" style="width:80%;">

    <div class="layui-form-item layui-row">
        <div class="layui-col-xs6">
            <label class="layui-form-label">用户ID</label>
            <div class="layui-input-block">
                <input type="text" class="layui-input"
                       name="userId" id="name"  lay-verify="required" placeholder="请输入用户ID">
            </div>
        </div>
        <div class="layui-col-xs6">
            <label class="layui-form-label">用户名</label>
            <div class="layui-input-block">
                <input type="text" class="layui-input"
                       name="userName" id="legalPerson" lay-verify="required"  placeholder="请输入用户名">
            </div>
        </div>
    </div>

    <div class="layui-form-item layui-row">
        <div class="layui-col-xs6">
            <label class="layui-form-label">用户密码</label>
            <div class="layui-input-block">
                <input type="text" class="layui-input"
                       name="userPassword"  placeholder="请输入用户密码">
            </div>
        </div>
        <div class="layui-col-xs6">
            <label class="layui-form-label">电话</label>
            <div class="layui-input-block">
                <input type="text" class="layui-input"
                       name="phone" placeholder="请输入用户电话">
            </div>
        </div>
    </div>

    <div class="layui-form-item layui-row">
        <div class="layui-col-xs6">
            <label class="layui-form-label">身份</label>
            <div class="layui-input-block">
                <select name="identity"  id="level">
                    <option value="">请选择</option>
                    <option value="0"> 学生</option>
                    <option value="1"> 老师</option>
                    <option value="2"> 图书管理员</option>
                    <option value="3"> 系统管理员</option>
                </select>
            </div>
        </div>
    </div>
    <br/>
    <div class="layui-form-item layui-row layui-col-xs12">
        <div class="layui-input-block">
            <button class="layui-btn layui-btn-lg" lay-submit="addOrUpdateUser"
                    lay-filter="addOrUpdateUser">确认
            </button>
            <button class="layui-btn layui-btn-lg layui-btn-normal" onclick="">取消</button>
        </div>
    </div>
</form>
<script type="text/javascript" src="${ctx}/js/user/add_user.js"></script>
</body>
</html>