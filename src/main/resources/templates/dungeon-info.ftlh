<!DOCTYPE html>
<html>
<head>
    <title>Данж - ${dungeon.name}</title>
</head>
<body>
<h1>Данж: ${dungeon.name}</h1>
<hr>
<b>Название: </b>${dungeon.name}<br>
<b>Регион: </b>${dungeon.region.name}<br>
<hr>
<form action="/dungeons/delete/${dungeon.id}" method="post">
    <button type="submit">Удалить данж</button>
</form>
</body>
</html>
