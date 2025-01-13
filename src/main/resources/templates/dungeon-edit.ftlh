<!DOCTYPE html>
<html>
<head>
    <title>Редактировать данж</title>
</head>
<body>
<h1>Редактировать данж</h1>
<form action="/dungeons/update/${dungeon.id}" method="post">
    <label for="name">Новое имя данжа:</label>
    <input type="text" id="name" name="name" value="${dungeon.name}" required/>
    <br><br>
    <label for="region">Регион:</label>
    <select id="region" name="regionId" required>
        <#if regions?? && (regions?size > 0)>
            <#list regions as region>
                <option value="${region.id}" <#if region.id == dungeon.region.id>selected</#if>>
                    ${region.name}
                </option>
            </#list>
        </#if>
    </select>
    <br><br>
    <button type="submit">Сохранить</button>
    <a href="/dungeons">
        <button type="button">Отмена</button>
    </a>
</form>
</body>
</html>
