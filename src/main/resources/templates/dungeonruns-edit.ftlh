<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Редактирование похода</title>
</head>
<body>
<h1>Редактирование похода в данж</h1>
<form action="/dungeon-runs/update" method="post">
    <input type="hidden" name="id" value="${dungeonRun.id}">

    <label for="region">Регион:</label>
    <select name="region" id="region">
        <#list regions as region>
            <option value="${region.id}" <#if region.id == dungeonRun.dungeon.region.id>selected</#if>>
                ${region.name}
            </option>
        </#list>
    </select>
    <br><br>

    <label for="dungeon">Данж:</label>
    <select name="dungeon" id="dungeon">
        <#list dungeons as dungeon>
            <option value="${dungeon.id}" <#if dungeon.id == dungeonRun.dungeon.id>selected</#if>>
                ${dungeon.name}
            </option>
        </#list>
    </select>
    <br><br>

    <label for="character">Персонаж:</label>
    <select name="character" id="character">
        <#list characters as character>
            <option value="${character.id}" <#if character.id == dungeonRun.character.id>selected</#if>>
                ${character.name}
            </option>
        </#list>
    </select>
    <br><br>

    <!-- Генерация случайного времени прохождения -->
    <input type="hidden" name="timeScore" value="${dungeonRun.timeScore}">
    <p>Случайное время прохождения будет назначено автоматически.</p>

    <button type="submit">Сохранить</button>
    <a href="/dungeon-runs">
        <button type="button">Отмена</button>
    </a>
</form>
</body>
</html>
