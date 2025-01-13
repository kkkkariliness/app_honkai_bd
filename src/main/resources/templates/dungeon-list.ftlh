<!DOCTYPE html>
<html>
<head>
    <title>Список данжей</title>
</head>
<body>
<h1>Список данжей</h1>
<hr>
<table border="1">
    <thead>
    <tr>
        <th>ID</th>
        <th>Название</th>
        <th>Регион</th>
    </tr>
    </thead>
    <tbody>
    <#list dungeons as dungeon>
        <tr>
            <td>${dungeon.id}</td>
            <td>${dungeon.name}</td>
            <td>${dungeon.region.name}</td>
        </tr>
    </#list>
    </tbody>
</table>
<br>
<a href="/dungeons/new">Добавить новый данж</a>
</body>
</html>
