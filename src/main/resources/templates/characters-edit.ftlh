<!DOCTYPE html>
<html>
<head>
    <title>Редактировать персонажа</title>
</head>
<body>
<h1>Редактировать персонажа</h1>
<form action="/characters/update/${character.id}" method="post">
    <label for="name">Новое имя персонажа:</label>
    <input type="text" id="name" name="name" value="${character.name}" required/>
    <br><br>

    <label for="rarity">Редкость (4-5):</label>
    <input type="number" id="rarity" name="rarity" value="${character.rarity}" min="4" max="5" required/>
    <br><br>

    <label for="way">Путь:</label>
    <input type="text" id="way" name="way" value="${character.way}" required/>
    <br><br>

    <label for="typeOfBattle">Тип боя:</label>
    <input type="text" id="typeOfBattle" name="typeOfBattle" value="${character.typeOfBattle}" required/>
    <br><br>

    <label for="dateOfMeet">Дата встречи:</label>
    <input type="date" id="dateOfMeet" name="dateOfMeet" value="${character.dateOfMeet}" required/>
    <br><br>

    <label for="isAlive">Жив по сюжету?:</label>
    <input type="checkbox" id="isAlive" name="isAlive" value="true" <#if character.alive>checked</#if>/>
    <br><br>

    <label for="moneyDonat">Задоначено денег для получения:</label>
    <input type="number" id="moneyDonat" name="moneyDonat" step="0.01" value="${character.moneyDonat}" required/>
    <br><br>

    <button type="submit">Сохранить</button>
    <a href="/">
        <button type="button">Отмена</button>
    </a>
</form>
</body>
</html>