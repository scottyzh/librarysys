layui.use(['form','jquery','jquery_cookie'], function () {
    var form = layui.form,
        layer = layui.layer,
        $ = layui.jquery,
        $ = layui.jquery_cookie($);

    // 进行登录操作
    form.on('submit(saveBtn)', function (data) {
        data = data.field;
        //加载
        index = layer.load(1);
        $.ajax({
            type:"post",
            url:ctx+"/user/lossApply",
            data:{
                password:data.password,
            },
            dataType:"json",
            success:function (data) {
                layer.close(index);
                if(data.code == 200){
                    layer.msg("挂失成功",{
                        icon: 1,
                        time: 2000, //2秒关闭（如果不配置，默认是3秒）
                        shade : [0.6 , '#000' , true],
                    })
                    //关闭，刷新页面
                    layer.closeAll("iframe");
                    parent.location.reload();
                }else {
                    layer.msg(data.msg);
                }
            }
        })
    });
});
