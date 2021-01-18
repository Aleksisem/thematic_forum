import pool from './pool'
import * as UserController from '../controllers/userController'

const userRoles = [
  {
    id: 0,
    name: 'гость',
    description: 'Гостю предоставляется возможность просматривать посты из раздела "Общее" без возможности комментирования.'
  },
  {
    id: 1,
    name: 'пользователь',
    description: 'Обычному пользователю предоставляется возможность создавать новые посты, просматривать посты из тематических разделов, а также оставлять комментарии.'
  },
  {
    id: 2,
    name: 'модератор',
    description: 'Модератору предоставляется возможность просматривать посты из скрытого раздела, создавать новые разделы, удалять созданные пользователями посты и комментарии.'
  },
  {
    id: 3,
    name: 'студент ртф',
    description: 'Студент радиотехнического факультета'
  },
  {
    id: 4,
    name: 'студент ркф',
    description: 'Студент радиоконструкторского факультета'
  },
  {
    id: 5,
    name: 'студент фвс',
    description: 'Студент факультет вычислительных систем'
  },
  {
    id: 6,
    name: 'студент фсу',
    description: 'Студент факультет систем управления'
  },
  {
    id: 7,
    name: 'студент фэт',
    description: 'Студент факультета электронной техники'
  },
  {
    id: 8,
    name: 'студент фит',
    description: 'Студент факультета инновационных технологий'
  },
  {
    id: 9,
    name: 'студент эф',
    description: 'Студент экономического факультета'
  },
  {
    id: 10,
    name: 'студент гф',
    description: 'Студент гуманитарного факультета'
  },
  {
    id: 11,
    name: 'студент юф',
    description: 'Студент ридического факультета'
  },
  {
    id: 12,
    name: 'студент фб',
    description: 'Студент факультета безопасности'
  },
  {
    id: 13,
    name: 'студент зивф',
    description: 'Студент заочного и вечернего факультета'
  },
  {
    id: 14,
    name: 'студент фдо',
    description: 'Студент факультета дистанционного обучения'
  },
]

const postCategories = [
  {
    id: 0,
    name: 'Общий',
    description: 'Здесь опубликованы информационные посты и объявления.',
    accessRoles: [0, 1, 2]
  },
  {
    id: 1,
    name: 'Администрация',
    description: 'Скрытый раздел. Здесь опубликованы посты для модераторов и администраторов форума.',
    accessRoles: [2]
  },
  {
    id: 2,
    name: 'РТФ',
    description: 'Радиотехнический факультет.',
    accessRoles: [2, 3]
  },
  {
    id: 3,
    name: 'РКФ',
    description: 'Радиоконструкторский факультет.',
    accessRoles: [2, 4]
  },
  {
    id: 4,
    name: 'ФВС',
    description: 'Факультет вычислительных систем.',
    accessRoles: [2, 5]
  },
  {
    id: 5,
    name: 'ФСУ',
    description: 'Факультет систем управления.',
    accessRoles: [2, 6]
  },
  {
    id: 6,
    name: 'ФЭТ',
    description: 'Факультет электронной техники.',
    accessRoles: [2, 7]
  },
  {
    id: 7,
    name: 'ФИТ',
    description: 'Факультет инновационных технологий.',
    accessRoles: [2, 8]
  },
  {
    id: 8,
    name: 'ЭФ',
    description: 'Экономический факультет.',
    accessRoles: [2, 9]
  },
  {
    id: 9,
    name: 'ГФ',
    description: 'Гуманитарный факультет.',
    accessRoles: [2, 10]
  },
  {
    id: 10,
    name: 'ЮФ',
    description: 'Юридический факультет.',
    accessRoles: [2, 11]
  },
  {
    id: 11,
    name: 'ФБ',
    description: 'Факультет безопасности.',
    accessRoles: [2, 12]
  },
  {
    id: 12,
    name: 'ЗИВФ',
    description: 'Заочный и вечерний факультет.',
    accessRoles: [2, 13]
  },
  {
    id: 13,
    name: 'ФДО',
    description: 'Факультет дистанционного обучения.',
    accessRoles: [2, 14]
  },
  {
    id: 14,
    name: 'Программирование',
    description: 'Посты, связанные с тематикой программирования.',
    accessRoles: [1, 2]
  },
  {
    id: 15,
    name: 'Математика',
    description: 'Посты на тему математики.',
    accessRoles: [1, 2]
  },
  {
    id: 16,
    name: 'Электроника и схемотехника',
    description: 'Посты, связанные с миром электроники.',
    accessRoles: [1, 2]
  }
]

pool.on('connect', () => {
  console.log('Соединение с БД')
})

/**
 * Создаёт таблицу для хранения данных о ролях пользователей.
 */
const createUserRoleTable = async () => {
  const query = `CREATE TABLE IF NOT EXISTS user_role 
    (id smallint PRIMARY KEY,
    name varchar(32) NOT NULL,
    description varchar(300))`
  await pool.query(query)
  console.log('Таблица user_role создана')
}

/**
 * Создаёт таблицу для хранения данных о пользователях.
 */
const createUserTable = async () => {
  const query = `CREATE TABLE IF NOT EXISTS account 
    (id SERIAL PRIMARY KEY,
    login VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(256) NOT NULL,
    password_salt VARCHAR(16) NOT NULL,
    registration_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_online TIMESTAMP DEFAULT CURRENT_TIMESTAMP)`
  await pool.query(query)
  console.log('Таблица account создана')
}

const createUserRoleToAccountTable = async () => {
  const query = `CREATE TABLE IF NOT EXISTS user_role_to_account 
    (role_id smallint REFERENCES user_role(id) ON DELETE CASCADE,
    account_id integer REFERENCES account(id) ON DELETE CASCADE)`
  await pool.query(query)
  console.log('Таблица user_role_to_account создана')
}

/**
 * Создаёт таблицу для хранения данных о категориях постов.
 */
const createPostCategoryTable = async () => {
  const query = `CREATE TABLE IF NOT EXISTS post_category
    (id smallint PRIMARY KEY,
    name varchar(32) NOT NULL,
    description varchar(300))`
  await pool.query(query)
  console.log('Таблица post_category создана')
}

/**
 * Создаёт таблицу для хранения данных о постах. 
 */
const createPostTable = async () => {
  const query = `CREATE TABLE IF NOT EXISTS post 
    (id SERIAL PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    content text NOT NULL,
    category integer REFERENCES post_category(id) ON DELETE CASCADE NOT NULL,
    author_id integer REFERENCES account(id) ON DELETE CASCADE NOT NULL,
    published_date timestamp NOT NULL)`
  await pool.query(query)
  console.log('Таблица post создана')
}

/**
 * Создаёт таблицу для хранения связей постов с категориями.
 */
const createUserRoleToPostCategoryTable = async () => {
  const query = `CREATE TABLE IF NOT EXISTS user_role_to_post_category
    (category_id smallint REFERENCES post_category(id) ON DELETE CASCADE,
    role_id integer REFERENCES user_role(id) ON DELETE CASCADE)`
  await pool.query(query)
  console.log('Таблица user_role_to_post_category создана')
}

/**
 * Создаёт таблицу для хранения данных о комментариях к постам.
 */
const createCommentTable = async () => {
  const query = `CREATE TABLE IF NOT EXISTS comment (
    id SERIAL PRIMARY KEY,
    post_id integer REFERENCES post(id) ON DELETE CASCADE,
    author_id integer REFERENCES account(id) ON DELETE CASCADE,
    content VARCHAR(500) NOT NULL,
    published_date timestamp NOT NULL
  )`
  await pool.query(query)
  console.log('Таблица comment создана')
}

/**
 * Удаляет таблицу ролей пользователя.
 */
const dropUserRoleTable = async () => {
  const query = 'DROP TABLE IF EXISTS user_role CASCADE'
  await pool.query(query)
  console.log('Таблица user_role удалена')
}

/**
 * Удаляет таблицу категорий поста.
 */
const dropPostCategoryTable = async () => {
  const query = 'DROP TABLE IF EXISTS post_category CASCADE'
  await pool.query(query)
  console.log('Таблица post_category удалена')
}

/**
 * Удаляет таблицу связей категорий поста с постами.
 */
const dropUserRoleToPostCategoryTable = async () => {
  const query = 'DROP TABLE IF EXISTS user_role_to_post_category'
  await pool.query(query)
  console.log('Таблица user_role_to_post_category удалена')
}

/**
 * Удаляет таблицу пользователя.
 */
const dropUserTable = async () => {
  const query = 'DROP TABLE IF EXISTS account CASCADE'
  await pool.query(query)
  console.log('Таблица account удалена')
}

const dropUserRoleToAccountTable = async () => {
  const query = 'DROP TABLE IF EXISTS user_role_to_account CASCADE'
  await pool.query(query)
  console.log('Таблица user_role_to_account удалена')
}

/**
 * Удаляет таблицу поста.
 */
const dropPostTable = async () => {
  const query = 'DROP TABLE IF EXISTS post CASCADE'
  await pool.query(query)
  console.log('Таблица post удалена')
}

/**
 * Удаляет таблицу комментария.
 */
const dropCommentTable = async () => {
  const query = 'DROP TABLE IF EXISTS comment'
  await pool.query(query)
  console.log('Таблица comment удалена')
}

const addUserRoles = () => {
  const query = `INSERT INTO 
    user_role (id, name, description) 
    VALUES ($1, $2, $3)`
  userRoles.forEach((role) => {
    pool.query(query, [role.id, role.name, role.description])
  })
}

const addPostCategories = () => {
  const query = `INSERT INTO 
    post_category (id, name, description) 
    VALUES ($1, $2, $3)`
  postCategories.forEach((category) => {
    pool.query(query, [category.id, category.name, category.description])
  })
}

const connectUserRolesToPostCategories = () => {
  const query = `INSERT INTO 
  user_role_to_post_category (category_id, role_id) 
  VALUES ($1, $2)`
  postCategories.forEach((category) => {
    category.accessRoles.forEach((role) => {
      pool.query(query, [category.id, role])
    })
  })
}

/**
 * Создаёт все таблицы.
 */
const createAllTables = async () => {
  try {
    await createUserRoleTable()
    await createPostCategoryTable()
    await createUserTable()
    await createUserRoleToAccountTable()
    await createPostTable()
    await createUserRoleToPostCategoryTable()
    await createCommentTable()
  } catch (err) {
    console.error(err.stack)
  } finally {
    pool.end()
    console.log('Соединение с БД завершено')
  }
}

const addDefaultUsers = () => {
  defaultUsers.forEach(user => {
    const req = { body: user }
    try {
      UserController.addUser(req)
    } catch (err) {

    }
  })
}

const initializeDatabase = () => {
  addPostCategories()
  addUserRoles()
  connectUserRolesToPostCategories()
}

/**
 * Удаляет все таблицы.
 */
const dropAllTables = async () => {
  try {
    await dropPostTable()
    await dropUserRoleTable()
    await dropPostCategoryTable()
    await dropUserTable()
    await dropUserRoleToPostCategoryTable()
    await dropUserRoleToAccountTable()
    await dropCommentTable()
  } catch (err) {
    console.error(err.stack)
  } finally {
    pool.end()
    console.log('Соединение с БД завершено')
  }
}

export {
  createAllTables,
  dropAllTables,
  initializeDatabase,
  addUserRoles,
  addPostCategories,
  connectUserRolesToPostCategories
}

require('make-runnable')