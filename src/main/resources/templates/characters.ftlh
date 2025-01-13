<!DOCTYPE html>
<html>
<head>
    <title>Персонажи</title>
</head>
<body>
<h1>Персонажи</h1>
<hr>
<h3>Все персонажи:</h3>
<#if characters?? && (characters?size > 0)>
    <#list characters as character>
        <div>
            <p>${character.name} | <a href="/characters/${character.id}">Подробнее о персонаже</a></p>
        </div>
    </#list>
<#else>
    <p>Персонажи отсутствуют</p>
</#if>
</body>
</html>
