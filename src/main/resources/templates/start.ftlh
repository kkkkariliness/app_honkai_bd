<!DOCTYPE html>
<html>
<head>
    <title>Лучшая сборка в данж</title>
</head>
<body>
<h1>Лучшая сборка в данж</h1>
<hr>

<!-- Выбор региона -->
<form action="/dungeon-runs" method="post">
    <p>
        <label for="region">Выберите регион:</label>
        <select name="region" id="region">
            <#list regions as region>
                <option value="${region.id}">${region.name}</option>
            </#list>
        </select>
        <button type="button" onclick="window.location='/regions/new'">Добавить регион</button>
        <button type="button" onclick="window.location='/regions/edit/' + getElementById('region').value">Изменить регион</button>
        <button type="button" onclick="window.location='/regions/delete/' + getElementById('region').value">Удалить регион</button>
        <!--<a href="/regions">Подробнее...</a> -->

    </p>
    <br><br>

    <!-- Выбор данжа -->
    <p>
        <label for="dungeon">Выберите данж:</label>
        <select name="dungeon" id="dungeon">
            <#list dungeons as dungeon>
                <option value="${dungeon.id}">${dungeon.name}</option>
            </#list>
        </select>
        <button type="button" onclick="window.location='/dungeons/new'">Добавить данж</button>
        <button type="button" onclick="window.location='/dungeons/edit/' + getElementById('dungeon').value">
            Изменить данж
        </button>
    </p>

<form action="/dungeons/${dungeon.id}" method="post">
    <input type="submit" value="Удалить данж" />
</form>

<a href="/dungeons">Подробнее...</a>

<br><br>

<!-- Форма выбора персонажа -->
<form action="/dungeon-runs" method="post">
    <p>
        <label for="character">Выберите персонажа:</label>
        <select name="character" id="character">
            <#list characters as character>
                <option value="${character.id}">${character.name}</option>
            </#list>
        </select>
        <button type="button" onclick="window.location='/characters/new'">Добавить персонажа</button>
        <button type="button" onclick="window.location='/characters/edit/' + document.getElementById('character').value">
            Изменить персонажа
        </button>

    </p>
</form>



<br><br>

<!-- Кнопка "В данж" -->
<form action="/dungeon-runs" method="post">
    <p>
        <input type="submit" value="В данж!">
        <a href="/dungeon-runs">Подробнее...</a>
    </p>
</form>

</body>
</html>
