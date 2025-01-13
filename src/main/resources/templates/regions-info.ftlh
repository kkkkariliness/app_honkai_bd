<!DOCTYPE html>
<html>
<head>
    <title>Регионы</title>
</head>
<body>
<h1>Регионы:</h1>
<h4>Подробнее о регионе</h4>
<hr>
<b>Название региона: </b>${region.name}<br>
<b>Данжи в этом регионе: </b>
<#if region.dungeons?? && (region.dungeons?size > 0)>
    <ul>
        <#list region.dungeons as dungeon>
            <li>${dungeon.name}</li>
        </#list>
    </ul>
<#else>
    <p>Нет данжей в этом регионе.</p>
</#if>
<hr>
<!-- Кнопка для удаления региона -->
<form action="/regions/${region.id}" method="post">
    <input type="submit" value="Удалить регион"/>
</form>

<!-- Кнопка для изменения региона -->
<form action="/regions/edit/${region.id}" method="get">
    <button type="submit">Изменить регион</button>
</form>
</body>
</html>
