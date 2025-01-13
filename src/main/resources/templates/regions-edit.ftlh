<!DOCTYPE html>
<html>
<head>
    <title>Редактировать регион</title>
</head>
<body>
<h1>Редактировать регион</h1>
<form action="/regions/update/${region.id}" method="post">
    <label for="name">Новое имя региона:</label>
    <input type="text" id="name" name="name" value="${region.name}" required/>
    <br><br>
    <button type="submit">Сохранить</button>
</form>
</body>
</html>
