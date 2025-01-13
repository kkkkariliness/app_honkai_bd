<!DOCTYPE html>
<html>
<head>
    <title>Данжи</title>
</head>
<body>
<h1>Данжи</h1>
<hr>
<h3>Все данжи:</h3>
<#if dungeons?? && (dungeons?size > 0)>
    <#list dungeons as dungeon>
        <div>
            <p>${dungeon.name} | <a href="/dungeons/${dungeon.id}"> Подробнее о данже</a> </p>
        </div>
    </#list>
<#else>
    <p>Данжи отсутствуют</p>
</#if>
</body>
</html>
