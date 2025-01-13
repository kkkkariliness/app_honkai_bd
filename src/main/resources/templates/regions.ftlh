<!DOCTYPE html>
<html>
<head>
    <title>Регионы</title>
</head>
<body>
<h1>Регионы</h1>
<hr>
<h3>Все регионы:</h3>
<#if regions?? && (regions?size > 0) >
<#list regions as region>
        <div>
            <p>${region.name} | <a href="/regions/${region.id}"> Подробнее о регионе</a> </p>
        </div>
    </#list>
<#else>
    <p>Регионы отсутствуют</p>
</#if>
</body>
</html>
