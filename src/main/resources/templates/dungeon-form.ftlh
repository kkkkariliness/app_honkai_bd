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
    <select id="region" name="region.id">
        <#list regions as region>
            <option value="${region.id}">${region.name}</option>
        </#list>
    </select>
    <br><br>
    <button type="submit">Сохранить</button>
</form>
</body>
</html>
