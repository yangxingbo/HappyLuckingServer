<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>欢迎使用Ajax服务器</title>

    <script type="text/javascript">
        function loadXMLDoc()
        {
            var xmlhttp;
            if (window.XMLHttpRequest)
            {// code for IE7+, Firefox, Chrome, Opera, Safari
                xmlhttp=new XMLHttpRequest();
            }
            else
            {// code for IE6, IE5
                xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
            }
            xmlhttp.onreadystatechange=function()
            {
                if (xmlhttp.readyState==4 && xmlhttp.status==200)
                {
                    document.getElementById("showText").innerHTML=xmlhttp.responseText;
                }
            }
            xmlhttp.open("GET","servlet/LoginServlet?method=query&username=bob&password=123456",true);
            xmlhttp.send();
        }
    </script>

</head>
<body>
    <h2 id="showText">点击下面按钮改变我</h2>
    <button type="button" onclick="loadXMLDoc()">通过Ajax从数据库获取数据</button>
</body>
</html>
