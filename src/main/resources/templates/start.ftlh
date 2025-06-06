<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/html">
<head>
    <title>Лучшая сборка в данж</title>
</head>
<body>
<h1>Лучшая сборка в данж</h1>
<hr>

<!-- Выбор региона -->
<form action="/dungeon-runs" method="post">

    <script>
        // Функция для обновления списка данжей
        function updateDungeons() {
            const dungeonSelect = document.getElementById('dungeon'); // Элемент для данжей
            const regionSelect = document.getElementById('region');  // Элемент для регионов
            const editButton = document.querySelector("button[onclick*='dungeons/edit/']"); // Кнопка "Изменить данж"
            const deleteButton = document.querySelector("button[onclick*='dungeons/delete/']"); // Кнопка "Удалить данж"

            // Проверяем, что оба элемента найдены
            if (!(regionSelect && dungeonSelect && editButton && deleteButton)) {
                console.error('Не найден элемент region, dungeon, или кнопки!');
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

                        // Если список данжей пуст, отображаем "Данжей не найдено" и скрываем кнопки
                        if (dungeons.length === 0) {
                            const option = document.createElement('option');
                            option.value = '';
                            option.textContent = 'Данжей не найдено';
                            dungeonSelect.appendChild(option);

                            // Скрываем кнопки "Изменить данж" и "Удалить данж"
                            editButton.style.display = 'none';
                            deleteButton.style.display = 'none';
                        } else {
                            // Заполняем новый список данжей
                            dungeons.forEach((dungeon) => {
                                const option = document.createElement('option');
                                option.value = dungeon.id;
                                option.textContent = dungeon.name;
                                dungeonSelect.appendChild(option);
                            });

                            // Показываем кнопки, если есть данжи
                            editButton.style.display = 'inline-block';
                            deleteButton.style.display = 'inline-block';
                        }
                    })
                    .catch((error) => {
                        console.error('Ошибка при загрузке данжей:', error);
                    });


            }
        }
    </script>


    <p>
        <label for="region">Выберите регион:</label>
        <select name="region" id="region" onchange="updateDungeons()">
            <#list regions as region>
                <option value="${region.id}">${region.name}</option>
            </#list>
        </select>
        <button type="button" onclick="window.location='/regions/new'">Добавить регион</button>
        <button type="button" onclick="window.location='/regions/edit/' + getElementById('region').value">Изменить регион</button>
        <button type="button" onclick="window.location='/regions/delete/' + getElementById('region').value">Удалить регион</button>
    </p>

    <br><br>

    <!-- Выбор данжа -->
    <p>
        <label for="dungeon">Выберите данж:</label>
        <select name="dungeon" id="dungeon">
            <!-- Список данжей обновляется динамически -->
        </select>
        <button type="button" onclick="window.location='/dungeons/new'">Добавить данж</button>
        <button type="button" onclick="window.location='/dungeons/edit/' + getElementById('dungeon').value">Изменить данж</button>
        <button type="button" onclick="window.location='/dungeons/delete/' + getElementById('dungeon').value">Удалить данж</button>
    </p>

    <br><br>

    <!-- Форма выбора персонажа -->
    <p>
        <label for="character">Выберите персонажа:</label>
        <select name="character" id="character">
            <#list characters as character>
                <option value="${character.id}">${character.name}</option>
            </#list>
        </select>
        <button type="button" onclick="window.location='/characters/new'">Добавить персонажа</button>
        <button type="button" onclick="window.location='/characters/edit/' + getElementById('character').value">Изменить персонажа</button>
        <button type="button" onclick="window.location='/characters/delete/' + getElementById('character').value">Удалить персонажа</button>
    </p>

    <br><br>

    <!-- Кнопка "В данж" -->

    <p>
        <input type="submit" value="В данж!">
        <a href="/dungeon-runs">Подробнее...</a>

       <!-- Кнопка "Оживить персонажей" -->
        <button type="button" onclick="reviveCharacters()">Оживить персонажей!</button>

        <script>
            function reviveCharacters() {
                fetch('/reviveCharacters', {
                    method: 'POST',
                })
                    .then(response => {
                        if (!response.ok) {
                            throw new Error('Ошибка при оживлении персонажей: ' + response.statusText);
                        }
                        return response.text();
                    })
                    .then(message => {
                        alert(message); // Сообщение об успешной операции
                        window.location.reload(); // Перезагрузка страницы
                    })
                    .catch(error => {
                        alert('Ошибка: ' + error.message);
                    });
            }
        </script>


        <script>
             // Проверка заполненности формы перед отправкой
             document.addEventListener('DOMContentLoaded', function () {
                 //Обновляем список данжей
                 updateDungeons();
                 // Добавляем обработчик на событие отправки формы
                 document.querySelector('form[action="/dungeon-runs"]')
                     .addEventListener('submit', function (event) {
                     // Проверяем, что полученные элементы являются HTMLSelectElement
                     const region = document.getElementById('region');
                     const dungeon = document.getElementById('dungeon');
                     const character = document.getElementById('character');

                     // Убедимся, что элементы существуют и являются правильным типом
                     if (
                         !(region instanceof HTMLSelectElement) ||
                         !(dungeon instanceof HTMLSelectElement) ||
                         !(character instanceof HTMLSelectElement)
                     ) {
                         alert('Не удалось определить элементы формы. Попробуйте обновить страницу.');
                         event.preventDefault(); // Отменяем отправку формы
                         return;
                     }

                     // Проверяем, что значения элементов выбраны
                     if (!region.value || !dungeon.value || !character.value) {
                         alert('Пожалуйста, выберите все параметры перед отправкой!');
                         event.preventDefault(); // Отменяем отправку формы
                     }
                 });
             });
        </script>
    </p>
</form>

</body>
</html>
