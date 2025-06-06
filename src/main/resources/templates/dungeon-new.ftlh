<!DOCTYPE html>
<html>
<head>
    <title>Добавить данж</title>
</head>
<body>
<h1>Добавить новый данж</h1>
<form action="/dungeons/save" method="post">
    <label for="name">Название:</label>
    <input type="text" id="name" name="name" required>
    <br><br>
    <label for="region">Регион:</label>
    <select id="region" name="region.id" required>
        <#if regions?? && (regions?size > 0)>
            <#list regions as region>
                <option value="${region.id}">${region.name}</option>
            </#list>
        <#else>
            <option disabled>Нет доступных регионов</option>
        </#if>
    </select>
    <br><br>
    <button type="submit">Сохранить</button>
    <a href="/">
        <button type="button">Отмена</button>
    </a>
</form>
</body>
</html>
