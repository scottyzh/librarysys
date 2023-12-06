<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>挂失申请</title>
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
                <label class="layui-form-label required">请输入密码确认</label>
                <div class="layui-input-block">
                    <input type="text" name="password" lay-verify="required" lay-reqtext="密码不能为空" placeholder='用户密码' value=""} class="layui-input">
                </div>
            </div>

            <div class="layui-form-item">
                <div class="layui-input-block">
                    <button class="layui-btn" lay-submit lay-filter="saveBtn">确认挂失</button>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="${ctx}/js/user/loss_page.js"></script>

</body>
</html>