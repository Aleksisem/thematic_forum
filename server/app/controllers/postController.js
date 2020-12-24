import dbQuery from '../db/dbQuery'
import { errorMessage, successMessage, status } from '../utils/status'
import { empty } from '../utils/validations'

const getAllPosts = async (req, res) => {
  const query = 'SELECT * FROM post ORDER BY published_date DESC'
  dbQuery.query(query)
    .then(response => {
      if (response.rows[0] === undefined) {
        errorMessage.error = 'Постов нет'
        return res.status(status.notfound).send(errorMessage)
      }
      successMessage.data = res.rows
      return res.status(status.success).send(successMessage)
    })
    .catch(err => {

    })
}

const addPost = async (req, res) => {
  const {
    title,
    content,
    authorId
  } = req.body

  if (empty(title) || empty(content)) {
    errorMessage.error = 'Все поля должны быть заполнены'
    return res.status(status.bad).send(errorMessage)
  }

  const query = `INSERT INTO 
  post (title, content, author_id, published_date) 
  VALUES ($1, $2, $3, $4) 
  RETURNING *`
  const values = [
    title,
    content,
    authorId,
    new Date()
  ]

  dbQuery.query(query, values)
    .then(response => {
      successMessage.data = response.rows[0]
      return res.status(status.created).send(successMessage)
    })
    .catch(err => {
      console.error(err.stack)
      errorMessage.error = 'Ошибка добавления поста'
      return res.status(status.error).send(errorMessage)
    })
}

const deletePost = async (req, res) => {
  
}

export {
  getAllPosts,
  addPost,
  deletePost
}