<!DOCTYPE html>
<html>
<head>
    <title>Персонаж - ${character.name}</title>
</head>
<body>
<h1>Персонаж: ${character.name}</h1>
<hr>
<b>Имя: </b>${character.name}<br>
<b>Редкость: </b>${character.rarity}<br>
<b>Путь: </b>${character.way}<br>
<b>Тип боя: </b>${character.typeOfBattle}<br>
<b>Дата встречи: </b>${character.dateOfMeet}<br>
<b>Жив по сюжету игры: </b>
<#if character.isAlive?? && character.isAlive()>
    Да
<#else>
    Нет
</#if>
<br>
<b>Задоначено денег для получения: </b>${character.moneyDonat}<br>
<hr>
<form action="/characters/delete/${character.id}" method="post">
    <button type="submit">Удалить персонажа</button>
</form>
</body>
</html>
