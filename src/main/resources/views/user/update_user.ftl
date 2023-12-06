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
                       name="userId" id="name"  lay-verify="required" value="${(user.userId)!}" placeholder="请输入用户ID">
            </div>
        </div>
        <div class="layui-col-xs6">
            <label class="layui-form-label">用户名</label>
            <div class="layui-input-block">
                <input type="text" class="layui-input"
                       name="userName" id="legalPerson" lay-verify="required" value="${(user.userName)!}" placeholder="请输入用户名">
            </div>
        </div>
    </div>
    <div class="layui-form-item layui-row">

        <div class="layui-col-xs6">
            <label class="layui-form-label">电话</label>
            <div class="layui-input-block">
                <input type="text" class="layui-input"
                       name="phone" value="${(user.phone)!}" placeholder="请输入用户电话">
            </div>
        </div>
        <div class="layui-col-xs6">
            <label class="layui-form-label">状态</label>
            <div class="layui-input-block">
                <select name="status">
                    <option value="${(user.status)!}" >请选择</option>
                    <option value="0" <#if user.status==0>selected="selected"</#if>>正常</option>
                    <option value="1" <#if user.status==1>selected="selected"</#if>>挂失</option>
                    <option value="2" <#if user.status==2>selected="selected"</#if>>注销</option>
                    <option value="3" <#if user.status==3>selected="selected"</#if>>暂停借阅</option>
                </select>
            </div>
        </div>
    </div>

    <div class="layui-form-item layui-row">
        <div class="layui-col-xs6">
            <label class="layui-form-label">身份</label>
            <div class="layui-input-block">
                <select name="identity"  id="level">
                    <option value="${(user.identity)!}" >请选择</option>
                    <option value="0" <#if user.identity==0>selected="selected"</#if>>学生</option>
                    <option value="1" <#if user.identity==1>selected="selected"</#if>>老师</option>
                    <option value="2" <#if user.identity==2>selected="selected"</#if>>图书管理员</option>
                    <option value="3" <#if user.identity==3>selected="selected"</#if>>系统管理员</option>
                </select>
            </div>
        </div>

        <div class="layui-col-xs6">
            <label class="layui-form-label">用户密码</label>
            <div class="layui-input-block">
                <input type="text" class="layui-input"
                       name="userPassword" value="" placeholder="请输入用户密码">
            </div>
        </div>
    </div>
    <br/>
    <div class="layui-form-item layui-row layui-col-xs12">
        <div class="layui-input-block">
            <button class="layui-btn layui-btn-lg" lay-submit=""
                    lay-filter="addOrUpdateUser">确认
            </button>
        </div>
    </div>
</form>
<script type="text/javascript" src="${ctx}/js/user/update_user.js"></script>
</body>
</html>