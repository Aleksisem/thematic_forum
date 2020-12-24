'user strict'

import crypto from 'crypto'
import dbQuery from '../db/dbQuery'
import { genSalt, hashPassword, isEmpty, comparePassword } from '../utils/validations'
import {
  errorMessage, successMessage, status
} from '../utils/status'

const defaultUser = {
  login: 'guest',
  roleId: 1
}

const addUser = async (req, res) => {
  const {
    login, password
  } = req.body
  console.log(req.body)
  if (isEmpty(login)) {
    errorMessage.error = 'Логин не может быть пустым'
    return res.status(status.bad).send(errorMessage)
  }
  const salt = genSalt(16)
  const hashedPassword = hashPassword(password, salt)
  const registration_date = new Date()
  const last_online = new Date()
  const query = `INSERT INTO 
    user (login, password, password_salt, role_id, registration_date, last_online) 
    VALUES ($1, $2, $3, $4, $5, $6) 
    RETURNING *`
  const values = [
    login,
    hashedPassword,
    salt,
    defaultUser.roleId,
    registration_date,
    last_online
  ]
  dbQuery.query(query, values)
    .then(response => {
      console.log(`New user: ${response.rows[0]}`)
      successMessage.data = response.rows[0]
      return res.status(status.created).send(successMessage)
    })
    .catch(err => {
      console.log(err.stack)
      errorMessage.error = 'Операция не была выполнена'
      return res.status(status.error).send(errorMessage)
    })
}

const getUser = async (req, res) => {
  const { login, password } = req.body
  if (isEmpty(login)) {
    errorMessage.error = 'Логин не должен быть пустым'
    return res.status(status.bad).send(errorMessage)
  }
  const query = 'SELECT * FROM user WHERE login = $1'
  dbQuery.query(query, login)
    .then(response => {
      if (!response) {
        errorMessage.error = 'Неверный логин или пароль'
        return res.status(status.notfound).send(errorMessage)
      }
      const hashedPassword = hashPassword(password, response.rows[0].salt)
      if (!comparePassword(hashedPassword, response.rows[0].password)) {
        errorMessage.error = 'Неверный логин или пароль'
        return res.status(status.notfound).send(errorMessage)
      }
      successMessage.data = response.rows[0]
      return res.status(status.success).send(successMessage)
    })
    .catch(err => {
      console.error(err.stack)
      errorMessage.error = 'Операция не была выполнена'
      return res.status(status.error).send(errorMessage)
    })
}

const updateUser = async (req, res) => {
  dbQuery.query('UPDATE user SET role_id = $1, last_online = $2 WHERE id = $3', [
    req.body.roleId,
    new Date(),
    req.body.id
  ]).then(response => {
        res.json({'message': 'Пользователь успешно обновлён'})
      }).catch(err => {
          console.error(err.stack)
          res.send(err)
        })
}

const deleteUser = async (req, res) => {
  dbQuery.query('DELETE FROM user WHERE login = $1 RETURNING *', [req.body.login])
    .then(response => {
      console.log(response.rows[0])
      res.json({'message': `Пользователь ${req.body.login} успешно удалён`})
    }).catch(err => {
      console.error(err.stack)
      res.send(err)
    })
}

export {
  addUser,
  getUser,
  updateUser,
  deleteUser
}