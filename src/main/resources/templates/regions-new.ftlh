<!DOCTYPE html>
<html>
<head>
    <title>Создать регион</title>
</head>
<body>
<h1>Создать новый регион</h1>
<form action="/regions/new" method="post">
    Название региона: <input type="text" name="name"/><br><br>
    <input type="submit" value="Добавить регион"/>
    <a href="/regions">
        <button type="button">Отмена</button>
    </a>
</form>
</body>
</html>