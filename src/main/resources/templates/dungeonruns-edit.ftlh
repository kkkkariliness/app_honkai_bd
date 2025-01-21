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
        // Функция для обновления списка данжей
        function updateDungeons() {
            const dungeonSelect = document.getElementById('dungeon'); // Элемент для данжей
            const regionSelect = document.getElementById('region');  // Элемент для регионов
            const saveButton = document.querySelector('button[type="submit"]'); // Кнопка "Сохранить"

            // Проверяем, что элементы найдены
            if (!(regionSelect && dungeonSelect && saveButton)) {
                console.error('Не найден элемент region, dungeon или кнопка "Сохранить"!');
                return;
            }

            if (regionSelect instanceof HTMLSelectElement) {
                const regionId = regionSelect.value; // Получаем значение региона

                // Выполняем запрос к серверу
                fetch(`/dungeons/by-region/` + regionId)
                    .then((response) => {
                        if (!response.ok) {
                            throw new Error('Ошибка при получении списка данжей: ' + response.statusText);
                        }
                        return response.json(); // Парсим JSON
                    })
                    .then((dungeons) => {
                        dungeonSelect.innerHTML = ''; // Очищаем выпадающий список

                        // Если список данжей пуст, отображаем "Данжей не найдено" и скрываем кнопку "Сохранить"
                        if (dungeons.length === 0) {
                            const option = document.createElement('option');
                            option.value = '';
                            option.textContent = 'Данжей не найдено';
                            dungeonSelect.appendChild(option);

                            // Скрываем кнопку "Сохранить"
                            saveButton.style.display = 'none';
                        } else {
                            // Заполняем новый список данжей
                            dungeons.forEach((dungeon) => {
                                const option = document.createElement('option');
                                option.value = dungeon.id;
                                option.textContent = dungeon.name;
                                dungeonSelect.appendChild(option);
                            });

                            // Показываем кнопку "Сохранить", если есть данжи
                            saveButton.style.display = 'inline-block';
                        }
                    })
                    .catch((error) => {
                        console.error('Ошибка при загрузке данжей:', error);
                    });
            }
        }
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
    <p>Случайное время прохождения будет назначено автоматически.</p>

    <button type="submit">Сохранить</button>
    <a href="/dungeon-runs">
        <button type="button">Отмена</button>
    </a>
</form>
</body>
</html>
