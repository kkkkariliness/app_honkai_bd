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

    <script>
        // Шаг 1: Получаем из модели ID данжа, который должен быть выбран при загрузке.
        // Это значение устанавливается сервером (FreeMarker).
        const initialDungeonId = "${dungeonRun.dungeon.id}";

        // Шаг 2: Модифицированная функция для обновления списка данжей
        // Она теперь принимает необязательный параметр `dungeonToSelectId`
        function updateDungeons(dungeonToSelectId) {
            const dungeonSelect = document.getElementById('dungeon');
            const regionSelect = document.getElementById('region');
            const saveButton = document.querySelector('button[type="submit"]');

            if (!(regionSelect && dungeonSelect && saveButton)) {
                console.error('Не найден элемент region, dungeon или кнопка "Сохранить"!');
                return;
            }

            const regionId = regionSelect.value;
            if (!regionId) return; // Если регион не выбран, ничего не делаем

            fetch(`/dungeons/by-region/` + regionId)
                .then((response) => {
                    if (!response.ok) {
                        throw new Error('Ошибка при получении списка данжей: ' + response.statusText);
                    }
                    return response.json();
                })
                .then((dungeons) => {
                    dungeonSelect.innerHTML = ''; // Очищаем старый список

                    if (dungeons.length === 0) {
                        const option = document.createElement('option');
                        option.value = '';
                        option.textContent = 'Данжей не найдено';
                        dungeonSelect.appendChild(option);
                        saveButton.style.display = 'none'; // Скрываем кнопку, если нет данжей
                    } else {
                        dungeons.forEach((dungeon) => {
                            const option = document.createElement('option');
                            option.value = dungeon.id;
                            option.textContent = dungeon.name;

                            // ВАЖНО: Выбираем нужный данж в списке
                            // Сравниваем ID как строки, чтобы избежать проблем с типами
                            if (dungeonToSelectId && String(dungeon.id) === String(dungeonToSelectId)) {
                                option.selected = true;
                            }

                            dungeonSelect.appendChild(option);
                        });
                        saveButton.style.display = 'inline-block'; // Показываем кнопку
                    }
                })
                .catch((error) => {
                    console.error('Ошибка при загрузке данжей:', error);
                    dungeonSelect.innerHTML = '';
                    const option = document.createElement('option');
                    option.textContent = 'Ошибка загрузки';
                    dungeonSelect.appendChild(option);
                    saveButton.style.display = 'none';
                });
        }

        // Шаг 3: Вызываем функцию СРАЗУ после загрузки страницы
        document.addEventListener('DOMContentLoaded', function() {
            // Вызываем функцию с ID данжа, который должен быть выбран изначально
            updateDungeons(initialDungeonId);
        });
    </script>

    <label for="region">Регион:</label>
    <select name="region" id="region" onchange="updateDungeons()">
        <#list regions as region>
            <option value="${region.id}" <#if region.id == dungeonRun.dungeon.region.id>selected</#if>>
                ${region.name}
            </option>
        </#list>
    </select>
    <br><br>

    <label for="dungeon">Данж:</label>
    <select name="dungeon" id="dungeon">
        <!-- Список данжей будет обновляться динамически -->
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
    <p>Время прохождения данжа назначается автоматически и будет случайным!</p>

    <button type="submit">Сохранить</button>
    <a href="/dungeon-runs">
        <button type="button">Отмена</button>
    </a>
</form>
</body>
</html>
