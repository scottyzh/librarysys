<!DOCTYPE html>
<html>
<head>
    <#include "../common.ftl">
</head>
<body class="childrenBody">
<style>
    .file {
        position: relative;
        display: inline-block;
        background: #D0EEFF;
        border: 1px solid #99D3F5;
        border-radius: 4px;
        padding: 4px 12px;
        overflow: hidden;
        color: #1E88C7;
        text-decoration: none;
        text-indent: 0;
        line-height: 20px;
    }
    .file input {
        position: absolute;
        font-size: 100px;
        right: 0;
        top: 0;
        opacity: 0;
    }
    .file:hover {
        background: #AADFFD;
        border-color: #78C3F3;
        color: #004974;
        text-decoration: none;
    }
</style>
<form class="layui-form" style="width:80%;">
<#--    <input name="id" type="hidden" value="${(bookinfo.isbn)!}"/>-->

    <div class="layui-form-item layui-row">
        <div class="layui-col-xs6">
            <label class="layui-form-label">ISBN</label>
            <div class="layui-input-block">
                <input type="text" class="layui-input"
                       name="isbn" id="isbn"  lay-verify="required"  placeholder="请输入ISBN">
            </div>
        </div>
        <div class="layui-col-xs6">
            <label class="layui-form-label">图书名字</label>
            <div class="layui-input-block">
                <input type="text" class="layui-input"
                       name="bookName" id="legalPerson" lay-verify="required" placeholder="请输入图书名字">
            </div>
        </div>
    </div>

    <div class="layui-form-item layui-row">
        <div class="layui-col-xs6">
            <label class="layui-form-label">类别</label>
            <div class="layui-input-block">
                <select name="category"  id="level">
                    <option value="" >请选择</option>
                    <option value="1">马克思主义</option>
                    <option value="2">哲学、宗教</option>
                    <option value="3">社会科学理论</option>
                    <option value="4">政治、法律</option>
                    <option value="5">军事</option>
                    <option value="6">经济</option>
                    <option value="7">文化、科学、教育、体育</option>
                    <option value="8">语言、文字</option>
                    <option value="9">文学</option>
                    <option value="10">艺术</option>
                    <option value="11">历史、地理</option>
                    <option value="12">自然科学总论</option>
                    <option value="13">数理科学与化学</option>
                    <option value="14">天文学、地球科学</option>
                    <option value="15">生物科学</option>
                    <option value="16">医药、卫生</option>
                    <option value="17">农业科学</option>
                    <option value="18">工业技术</option>
                    <option value="19">交通运输</option>
                    <option value="20">航空、航天</option>
                    <option value="21">环境科学、安全科学</option>
                    <option value="22">综合性图书</option>
                </select>
            </div>
        </div>
        <div class="layui-col-xs6">
            <label class="layui-form-label">作者</label>
            <div class="layui-input-block">
                <input type="text" class="layui-input"
                       name="author" placeholder="请输入作者">
            </div>
        </div>
    </div>

    <div class="layui-form-item layui-row">
        <div class="layui-col-xs6">
            <label class="layui-form-label">出版社</label>
            <div class="layui-input-block">
                <input type="text" class="layui-input"
                       name="publisher" placeholder="请输入出版社">
            </div>
        </div>
    </div>



    <div class="layui-form-item">
        <label class="layui-form-label label_font" style="margin-top: 35px">缩略图</label>
        <div class="layui-input-block">
            <a href="javascript:;" class="file">请选择略缩图文件
            <input style="margin-top: 7px;" type="file" id="file">
                </a>
            <img id="showImg" style="max-height: 300px; height: 8em; min-width:8em;"/>
            <textarea name="bookPic" id="base64" style="display: none; width: 100%;height: 30em;"></textarea>
        </div>
    </div>


    <div class="layui-form-item layui-form-text">
        <label class="layui-form-label">请填写图书描述</label>
        <div class="layui-input-block">
            <textarea  name="bookDescription" placeholder="请输入内容" class="layui-textarea"></textarea>
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
<script type="text/javascript" src="${ctx}/js/book/add.update.js"></script>
</body>
</html>