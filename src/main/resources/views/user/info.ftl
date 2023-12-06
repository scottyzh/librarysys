<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>修改用户信息</title>
    <#include "../common.ftl">
    <style>
        .layui-form-item {width: auto;padding-right: 10px;line-height: 38px;}
    </style>
</head>
<body>
<div class="layuimini-container">
    <div class="layuimini-main">

        <div class="layui-form layuimini-form">
            <div class="layui-form-item">
                <label class="layui-form-label required">用户名</label>
                <div class="layui-input-block">
                    <input type="text" name="username" lay-verify="required" lay-reqtext="用户名不能为空" placeholder='用户名'   value=${(user.userName)!"获取错误"} class="layui-input">
                </div>
            </div>

            <div class="layui-form-item">
                <label class="layui-form-label required">电话</label>
                <div class="layui-input-block">
                    <input type="text" name="phone" lay-verify="required" lay-reqtext="电话不能为空" placeholder='用户电话'  value=${(user.phone)!"获取错误"} class="layui-input">
                </div>
            </div>

            <div class="layui-form-item">
                <div class="layui-input-block">
                    <button class="layui-btn" lay-submit lay-filter="saveBtn">确认保存</button>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="${ctx}/js/user/info.js"></script>

</body>
</html>