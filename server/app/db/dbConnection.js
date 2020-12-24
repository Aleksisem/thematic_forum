import pool from './pool'

pool.on('connect', () => {
  console.log('Соединение с БД')
})

/**
 * Create User Role Table
 */
const createUserRoleTable = () => {
  const query = `CREATE TABLE IF NOT EXISTS user_role 
    (id smallint NOT NULL,
    name varchar(32) NOT NULL,
    description varchar(300))`
  pool.query(query)
    .then((res) => {
      console.log(res)
      pool.end()
    })
    .catch((err) => {
      console.log(err)
      pool.end()
    })
}

/**
 * Create User Table
 */
const createUserTable = () => {
  const query = `CREATE TABLE IF NOT EXISTS user 
    (id SERIAL PRIMARY KEY,
    login VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(256) NOT NULL,
    password_salt VARCHAR(16) NOT NULL,
    role_id integer REFERENCES user_role(id) NOT NULL,
    registration_date DATE NOT NULL,
    last_online timestamp without time zone NOT NULL)`
  pool.query(query)
    .then((res) => {
      console.log(res)
      pool.end()
    })
    .catch((err) => {
      console.log(err)
      pool.end()
    })
}

const createPostTable = () => {
  const query = `CREATE TABLE IF NOT EXISTS post 
    (id SERIAL PROMARY KEY,
    title VARCHAR(100) NOT NULL,
    content text NOT NULL,
    author_id integer REFERENCES user(id) NOT NULL,
    published_date timestamp without time zone NOT NULL)`
  pool.query(query)
    .then((res) => {
      console.log(res)
      pool.end()
    })
    .catch((err) => {
      console.log(err)
      pool.end()
    })
}

const createCommentTable = () => {
  const query = `CREATE TABLE IF NOT EXISTS comment (
    id integer NOT NULL,
    post_id integer REFERENCES post(id) NOT NULL,
    parent_id integer,
    author_id integer REFERENCES user(id) NOT NULL,
    content VARCHAR(500) NOT NULL,
    published_date timestamp without time zone NOT NULL
  )`
  pool.query(query)
    .then((res) => {
      console.log(res)
      pool.end()
    })
    .catch((err) => {
      console.log(err)
      pool.end()
    })
}

/**
 * Drop User Role Table
 */
const dropUserRoleTable = () => {
  const query = 'DROP TABLE IF EXISTS user_role'
  pool.query(query)
    .then((res) => {
      console.log(res)
      pool.end()
    })
    .catch((err) => {
      console.log(err)
      pool.end()
    })
}

/**
 * Drop User Table
 */
const dropUserTable = () => {
  const query = 'DROP TABLE IF EXISTS user'
  pool.query(query)
    .then((res) => {
      console.log(res)
      pool.end()
    })
    .catch((err) => {
      console.log(err)
      pool.end()
    })
}

/**
 * Drop Post Table
 */
const dropPostTable = () => {
  const query = 'DROP TABLE IF EXISTS post'
  pool.query(query)
    .then((res) => {
      console.log(res)
      pool.end()
    })
    .catch((err) => {
      console.log(err)
      pool.end()
    })
}

/**
 * Drop Comment Table
 */
const dropCommentTable = () => {
  const query = 'DROP TABLE IF EXISTS comment'
  pool.query(query)
    .then((res) => {
      console.log(res)
      pool.end()
    })
    .catch((err) => {
      console.log(err)
      pool.end()
    })
}

/**
 * Create All Tables
 */
const createAllTables = () => {
  createUserRoleTable()
  createUserTable()
  createPostTable()
  createCommentTable()
}

/**
 * Drop All Tables
 */
const dropAllTables = () => {
  dropUserRoleTable()
  dropUserTable()
  dropPostTable()
  dropCommentTable()
}

pool.on('remove', () => {
  console.log('client removed')
  process.exit(0)
})

export {
  createAllTables,
  dropAllTables
}

require('make-runnable')