<!DOCTYPE html>
<html class="x-admin-sm">
<head>
    <meta charset="UTF-8">
    <title>欢迎页面</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi"/>
    <link rel="stylesheet" href="${ctx}/xadmin/font.css">
    <link rel="stylesheet" href="${ctx}/xadmin/xadmin.css">
    <#include "./common.ftl">
<#--    <script type="text/javascript" src="${ctx}/xadmin/layui.js"></script>-->
<#--    <script type="text/javascript" src="${ctx}/xadmin/xadmin.js"></script>-->

    <script type="text/javascript" src="${ctx}/js/welcome.js"></script>
</head>
<body>
<div class="layui-fluid">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md12">
            <img src="/images/GdufeLibraryLogo.png">
        </div>
        <div class="layui-col-md12">
            <div class="layui-card">
                <div class="layui-card-body ">
                    <blockquote class="layui-elem-quote">欢迎：
                        <span class="x-red">${(user.userName)!"获取错误"}</span>！ 当前时间：${(datetime)!"获取错误"}
                    </blockquote>
                </div>
            </div>
        </div>
        <div class="layui-col-md12">
            <div class="layui-card">
                <div class="layui-card-header">数据统计</div>
                <div class="layui-card-body ">
                    <ul class="layui-row layui-col-space10 layui-this x-admin-carousel x-admin-backlog">
                        <li class="layui-col-md2 layui-col-xs6">
                            <a href="javascript:" class="x-admin-backlog-body">
                                <h3>本月借阅次数</h3>
                                <p>
                                    <cite>${(borrowtimes)!"获取错误"}&nbsp;次</cite></p>
                            </a>
                        </li>
                        <li class="layui-col-md2 layui-col-xs6">
                            <a href="javascript:" class="x-admin-backlog-body">
                                <h3>本月预约次数</h3>
                                <p>
                                    <cite>${(reservetimes)!"获取错误"}&nbsp;次</cite></p>
                            </a>
                        </li>
                        <li class="layui-col-md2 layui-col-xs6">
                            <a href="javascript:" class="x-admin-backlog-body">
                                <h3>今天登录次数</h3>
                                <p>
                                    <cite>${(logintimes)!"获取错误"}&nbsp;次</cite></p>
                            </a>
                        </li>
                        <li class="layui-col-md2 layui-col-xs6">
                            <a href="javascript:" class="x-admin-backlog-body">
                                <h3>罚款数目</h3>
                                <p>
                                    <cite>${(fine)!"获取错误"}&nbsp;元</cite></p>
                            </a>
                        </li>
                        <li class="layui-col-md2 layui-col-xs6">
                            <a href="javascript:" class="x-admin-backlog-body">
                                <h3>用户身份</h3>
                                <p>
                                    <cite>${(identity)!"获取错误"}</cite></p>
                            </a>
                        </li>
                        <li class="layui-col-md2 layui-col-xs6 ">
                            <a href="javascript:" class="x-admin-backlog-body">
                                <h3>用户状态</h3>
                                <p>
                                    <cite>${(status)!"获取错误"}</cite></p>
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="layui-col-sm6 layui-col-md3">
            <div class="layui-card">
                <div class="layui-card-header">图书馆借阅次数
                    <span class="layui-badge layui-bg-cyan layuiadmin-badge">月</span></div>
                <div class="layui-card-body  ">
                    <p><cite>${(libBorrowTimes)!"获取错误"}&nbsp;次</cite></p>
                </div>
            </div>
        </div>
        <div class="layui-col-sm6 layui-col-md3">
            <div class="layui-card">
                <div class="layui-card-header">图书馆预约次数
                    <span class="layui-badge layui-bg-cyan layuiadmin-badge">月</span></div>
                <div class="layui-card-body ">
                    <p><cite>${(libReverseTimes)!"获取错误"}&nbsp;次</cite></p>
                </div>
            </div>
        </div>
        <div class="layui-col-sm6 layui-col-md3">
            <div class="layui-card">
                <div class="layui-card-header">图书馆访问次数
                    <span class="layui-badge layui-bg-cyan layuiadmin-badge">月</span></div>
                <div class="layui-card-body ">
                    <p>
                        <cite>${(libLoginTimes)!"获取错误"}&nbsp;次</cite>
                    </p>
                </div>
            </div>
        </div>
        <div class="layui-col-sm6 layui-col-md3">
            <div class="layui-card">
                <div class="layui-card-header">图书馆工作天数
                    <span class="layui-badge layui-bg-cyan layuiadmin-badge">月</span></div>
                <div class="layui-card-body ">
                    <p>
                        <cite>${(dayOfMonth)!"获取错误"}&nbsp;天</cite>
                    </p>
                </div>
            </div>
        </div>
        <div class="layui-col-md12">
            <div class="layui-card">
                <div class="layui-card-header">我的消息</div>
                <div class="layui-card-body ">
                    <table id="userList" class="layui-table" lay-filter="users"></table>
                </div>
            </div>
        </div>
        <style id="welcome_style"></style>
        <div class="layui-col-md12">
            <blockquote class="layui-elem-quote layui-quote-nm">广东财经大学图书馆</blockquote>
        </div>
    </div>
</div>
</div>
<script id="userListBar" type="text/html">
    <#--        <a class="layui-btn layui-btn-xs" id="edit" lay-event="edit">预约</a>-->
</script>
</body>
</html>