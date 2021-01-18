# Тематический форум

Тематический форум представляет собой платформу для публикации пользователями постов на тематики, определяемые разработчиком. Каждый пользователь обладает ограниченным набором ролей, благодаря чему он имеет доступ к определённым разделам форума. Также данная платформа предоставляет возможность пользователям оставлять комментарии к постам, таким образом, взаимодействуя друг с другом.

## Инструкция по настройке сервера

Для настройки сервера необходимо установить следующее ПО:

- Node.js;

- npm;

- PostgreSQL.

Затем проделать следующие этапы:
1.  После установки PostgreSQL необходимо создать новую базу данных, в которой будет храниться информация форума.

2. После установки в корневой директории сервера необходимо ввести команду "npm install". Данная команда установит все зависимости, необходимые для работы сервера.

3. В файле ".env" необходимо указать IP адрес сервера (по умолчанию localhost), пользователя и пароль в PostgreSQL, название БД, порт, на котором будет прослушиваться СУБД, порт, на котором будет прослушиваться сервер.

4. Затем необходимо ввести команду "npm run create-tables", которая создаст все таблицы, необходимые для работы с БД.

5. После выполнения данной команды необходимо ввести команду "npm run fill-tables", которая заполнит таблицы начальными значениями.

6. После выполнения всех команд необходимо ввести команду "npm run start", которая запустит сервер.

## Инструкция по использованию клиента

Для доступа к форуму необходимо пройти авторизация. Если аккаунт был создан ранее, нужно ввести логин и пароль учётной записи в соответствующих полях и нажать на кнопку "Войти". Если учётной записи не существует, то есть возможность создать новую учётную запись, нажав на кнопку "Регистрация". Для регистрации новой учётной записи в открывшейся форме необходимо ввести уникальный логин, пароль и указать факультет, после чего нажать на кнопку "Регистрация". Если данные указаны корректно, откроется форма авторизации, где нужно повторно ввести логин и пароль.

  Для получения списка постов раздела необходимо нажать на определённый раздел в списке. Откроется форма со списком постов выбранного раздела. Для отображения содержания поста и комментариев необходимо нажать на соответствующий пост в списке.

  Для создания нового поста, находясь в разделе необходимо нажать на кнопку со знаком "+", расположенную в верхнем меню. В открывшейся форме необходимо заполнить заголовок поста и его содержание, а затем нажать кнопку "Сохранить".

  Для добавления комментария необходимо в форме поста нажать на кнопку, расположенную справа от количества комментариев под содержанием поста. Снизу появится поле для ввода содержимого комментария. После заполнения поля, для отправки комментария, необходимо нажать на кнопку справа.

  Пользователи с правами модератора обладают возможностью удалять посты и комментарии. Для удаления необходимо удерживать элемент поста или комментария в списке до вызова контекстного меню, затем выбрать опцию "Удалить".