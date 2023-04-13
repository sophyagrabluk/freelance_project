<#import "userInfo.ftl" as ui>

<#macro common titleValue>

    <html>
    <head>
        <title> ${titleValue} </title>
    </head>
    <body>
    <#nested/>
    </body>
    </html>

</#macro>