<!DOCTYPE html>
<html>
<head>
    <title>Добавить персонажа</title>
</head>
<body>
<h1>Добавить нового персонажа</h1>
<form action="/characters/save" method="post">
    <label for="name">Имя персонажа:</label>
    <input type="text" id="name" name="name" required>
    <br><br>

    <label for="rarity">Редкость (4-5):</label>
    <input type="number" id="rarity" name="rarity" min="4" max="5" required>
    <br><br>

    <label for="way">Путь:</label>
    <input type="text" id="way" name="way" required>
    <br><br>

    <label for="typeOfBattle">Тип боя:</label>
    <input type="text" id="typeOfBattle" name="typeOfBattle" required>
    <br><br>

    <label for="dateOfMeet">Дата встречи:</label>
    <input type="date" id="dateOfMeet" name="dateOfMeet" required>
    <br><br>

    <label for="isAlive">Жив по сюжету игры:</label>
    <input type="checkbox" id="isAlive" name="isAlive">
    <br><br>

    <label for="moneyDonat">Задоначено денег для получения:</label>
    <input type="number" id="moneyDonat" name="moneyDonat" step="0.01" required>
    <br><br>

    <button type="submit">Сохранить</button>
    <a href="/characters">
        <button type="button">Отмена</button>
    </a>
</form>
</body>
</html>
