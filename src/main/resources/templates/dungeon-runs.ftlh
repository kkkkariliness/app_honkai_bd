<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Список походов в данж</title>
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        table, th, td {
            border: 1px solid black;
        }
        th, td {
            padding: 10px;
            text-align: left;
        }
        th {
            background-color: #f4f4f4;
        }
        button {
            padding: 5px 10px;
            margin: 0 5px;
        }
    </style>
</head>
<body>
<h1>Список походов в данж</h1>

<table>
    <thead>
    <tr>
        <th>Регион</th>
        <th>Персонаж</th>
        <th>Данж</th>
        <th>Время прохождения</th>
        <th>Действия</th>
    </tr>
    </thead>
    <tbody>
    <#list dungeonRuns as dungeonRun>
        <tr>
            <td>${dungeonRun.dungeon.region.name}</td>
            <td>${dungeonRun.character.name}</td>
            <td>${dungeonRun.dungeon.name}</td>
            <td>${dungeonRun.timeScore}</td>
            <td>
                <a href="/dungeon-runs/edit/${dungeonRun.id}">
                    <button type="button">Редактировать</button>
                </a>
                <form action="/dungeon-runs/delete/${dungeonRun.id}" method="post" style="display:inline;">
                    <button type="submit">Удалить</button>
                </form>
            </td>
        </tr>
    </#list>
    </tbody>
</table>

<a href="/"><button type="button">Сходить куда-нибудь ещё!</button></a>
</body>
</html>
