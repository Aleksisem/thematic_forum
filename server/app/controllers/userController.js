'use strict'

import dbQuery from '../db/dbQuery'
import { genSalt, hashPassword, isEmpty, comparePassword } from '../utils/validations'
import {
  errorMessage, successMessage, status
} from '../utils/status'

const defaultUser = {
  roles: [0, 1]
}

const UserRole = [
  'GUEST',
  'USER',
  'MODERATOR',
  'RTF_STUDENT',
  'RKF_STUDENT',
  'FVS_STUDENT',
  'FSU_STUDENT',
  'FET_STUDENT',
  'FIT_STUDENT',
  'EF_STUDENT',
  'GF_STUDENT',
  'UF_STUDENT',
  'FB_STUDENT',
  'ZIVF_STUDENT',
  'FDO_STUDENT'
]

/**
 * Добавляет запись пользователя в базе данных.
 * @param {*} req Запрос.
 * @param {*} res Ответ.
 * @return Сообщение о результате выполнения запроса.
 */
const addUser = (req, res) => {
  let {
    login,
    password,
    roles
  } = req.body
  if (isEmpty(login)) {
    errorMessage.error = 'Логин не может быть пустым'
    return res.status(status.bad).send(errorMessage)
  }
  
  if (login === 'admin') {
    roles = UserRole
  }
  else if (login === 'guest') {
    roles = [UserRole[0]]
  }
  const salt = genSalt(16)
  const hashedPassword = hashPassword(password, salt)
  const registration_date = new Date()
  const last_online = new Date()
  let query = `INSERT INTO 
    account (login, password, password_salt, registration_date, last_online) 
    VALUES ($1, $2, $3, $4, $5) 
    RETURNING id, login`
  const values = [
    login,
    hashedPassword,
    salt,
    registration_date,
    last_online
  ]
  dbQuery.query(query, values)
    .then(response => {
      successMessage.data = response.rows[0]
      query = `INSERT INTO 
      user_role_to_account (role_id, account_id) 
      VALUES ($1, $2)`
      roles.forEach(role => {
        dbQuery.query(query, [UserRole.indexOf(role), response.rows[0].id])
      })
      successMessage.data.roles = roles
      if (res) {
        return res.status(status.success).send(successMessage.data)
      } else {
        return
      }
    })
    .catch(err => {
      console.error(err.stack)
      errorMessage.error = 'Пользователь с таким логином уже существует'
      return res.status(status.error).send()
    })
}

/**
 * Получает пользователя из базы данных.
 * @param {*} req Запрос.
 * @param {*} res Ответ.
 * @return Запись пользователя.
 */
const getUser = (req, res) => {
  const { login, password } = req.body
  if (isEmpty(login)) {
    errorMessage.error = 'Логин не должен быть пустым'
    return res.status(status.bad).send(errorMessage)
  }
  const query = `SELECT * FROM account WHERE login = $1`
  dbQuery.query(query, [login])
    .then(response => {
      if (response.rows.length === 0) {
        errorMessage.error = 'Неверный логин или пароль'
        return res.status(status.notfound).send(errorMessage)
      }
      const hashedPassword = hashPassword(password, response.rows[0].password_salt)
      if (!comparePassword(hashedPassword, response.rows[0].password)) {
        errorMessage.error = 'Неверный логин или пароль'
        return res.status(status.notfound).send(errorMessage)
      }
      successMessage.data = {
        id: response.rows[0].id,
        login: response.rows[0].login
      }
      const query2 = `select role_id from user_role_to_account where account_id = ${successMessage.data.id}`
      dbQuery.query(query2)
      .then(response2 => {
        successMessage.data.roles = response2.rows.map(role => UserRole[role.role_id])
        return res.status(status.success).send(successMessage.data)
      })
      .catch(err => {
        console.error(err.stack)
        return res.status(status.error).send()
      })
    })
    .catch(err => {
      console.error(err.stack)
      errorMessage.error = 'Операция не была выполнена'
      return res.status(status.error).send(errorMessage)
    })
}

/**
 * Обновляет данные о пользователе в базе данных.
 * @param {*} req Запрос. 
 * @param {*} res Ответ.
 * @return Сообщение о результате выполнения запроса.
 */
const updateUser = (req, res) => {
  dbQuery.query('UPDATE account SET last_online = $1 WHERE id = $2 RETURNING id', [
    new Date(),
    req.body.id
  ])
  .then(response => {
    if (response.rows.length > 0) {
      successMessage.message = 'Пользователь успешно обновлён'
      return res.status(status.success).send(successMessage)
    } else {
      errorMessage.error = 'Данного пользователя не существует'
      return res.status(status.notfound).send(errorMessage)
    }    
    
  })
  .catch(err => {
    console.error(err.stack)
    errorMessage.error = 'Операция не была выполнена'
    return res.status(status.error).send(errorMessage)
  })
}

export {
  addUser,
  getUser,
  updateUser
}