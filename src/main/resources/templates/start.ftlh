<!DOCTYPE html>
<html>
<head>
    <title>Лучшая сборка в данж</title>
</head>
<body>
<h1>Лучшая сборка в данж</h1>
<hr>
<form action="/start" method="post">
    <!-- Выпадающий список для регионов -->
    <p>
    <label for="region">Выберите регион:</label>
    <select name="region" id="region">
        <#list regions as region>
            <option value="${region.id}">${region.name}</option>
        </#list>
    </select>
        <button type="button" onclick="window.location='/regions/new'">Добавить ещё регион</button>
        <!--<button type="button" onclick="window.location='/regions/edit'">Изменить регион</button>
        <button type="button" onclick="window.location=''">Удалить регион</button> -->
    <a href="/regions"> Подробнее...</a> </p>
    <br><br>

    <!-- Выпадающий список для данжей -->
    <p>
    <label for="dungeon">Выберите данж:</label>
    <select name="dungeon" id="dungeon">
        <#list dungeons as dungeon>
            <option value="${dungeon.id}">${dungeon.name}</option>
        </#list>
    </select>
        <button type="button" onclick="window.location='/dungeons/new'">Добавить ещё данж</button>
        <button type="button" onclick="window.location='/dungeons/edit'">Изменить данж</button>
        <button type="button" onclick="window.location=''">Удалить данж</button>
    <a href="/dungeons"> Подробнее...</a> </p>
    <br><br>

    <!-- Выпадающий список для персонажей -->
    <p>
    <label for="character">Выберите персонажа:</label>
    <select name="character" id="character">
        <#list characters as character>
            <option value="${character.id}">${character.name}</option>
        </#list>
    </select>
        <button type="button" onclick="window.location='/characters/new'">Добавить ещё персонажа</button>
        <button type="button" onclick="window.location='/characters/edit'">Изменить персонажа</button>
        <button type="button" onclick="window.location=''">Удалить персонажа</button>
    <a href="/characters"> Подробнее...</a> </p>
    <br><br>

    <!-- Кнопка "В данж" -->
    <p>
        <button type="submit">В данж!</button>
    <a href="/dungeon-runs"> Подробнее...</a> </p>
</form>
</body>
</html>
