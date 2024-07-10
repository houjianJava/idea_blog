$(document).ajaxSend(function (e, xhr, opt){
    var token = localStorage.getItem("user_token");
    xhr.setRequestHeader("user_token_header", token);
});

function getUserInfo(url){
    $.ajax({
        type: "get",
        url: url,
        SUCCESS:function (result){
            if (result.code=="SUCCESS" && result.data!=null){
                var userInfo = result.data;
                $(".left.card h3").text(userInfo.username);
                $(".left.card a").text("href", userInfo.githubUrl);
            }else{
                alert(result.errMsg);
            }
        },
        error:function (error) {
            if (error != null && error.status == 401) {
                location.href = "blog_login.html";
            }
        }
    });
}
function logout(){
    //删除token
    localStorage.removeItem("user_token");
    location.href = "blog_login.html";
}