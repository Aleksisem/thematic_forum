import dbQuery from '../db/dbQuery'
import { errorMessage, successMessage, status } from '../utils/status'
import { empty } from '../utils/validations'

/**
 * Получает список всех разделов форума из базы данных.
 * @param {*} req 
 * @param {*} res 
 * @return Список разделов.
 */
const getAllSections = (req, res) => {
  const query = `select distinct pc.id, pc.name from post_category as pc join user_role_to_post_category as urtpc on pc.id = urtpc.category_id join user_role_to_account as urta on urtpc.role_id = urta.role_id where urta.account_id = $1 order by pc.id asc`
  const values = [req.headers.user_id]
  dbQuery.query(query, values)
  .then((response) => {
    successMessage.data = response.rows
    return res.status(status.success).send(successMessage.data)
  })
  .catch((err) => {
    console.error(err.stack)
    errorMessage.error
  })
}

/**
 * Получает все записи постов определённого раздела из базы данных.
 * @param {*} req Запрос.
 * @param {*} res Ответ.
 * @return Список постов.
 */
const getAllPostsFromSection = (req, res) => {
  const query = `select post.id, post.title, post.published_date, count(comment.id) from post left outer join comment on comment.post_id = post.id where post.category = ${req.params.sectionId} group by post.id ORDER BY post.published_date desc;`
  dbQuery.query(query)
    .then(response => {
      successMessage.data = response.rows
      successMessage.data.map((post) => {
        post.published_date = post.published_date.toLocaleString("ru", {
          year: 'numeric',
          month: 'numeric',
          day: 'numeric',
          hour: 'numeric',
          minute: 'numeric'
        })
      })
      return res.status(status.success).send(successMessage.data)
    })
    .catch(err => {
      console.error(err.stack)
      errorMessage.error = 'Ошибка при загрузке постов'
      return res.status(status.error).send(errorMessage)
    })
}

/**
 * Получает запись поста из базы данных.
 * @param {*} req Запрос.
 * @param {*} res Ответ.
 * @return Запись поста.
 */
const getPost = (req, res) => {
  const query = `SELECT post.id, post.title, post.content, post.category, post.author_id, account.login as author_login, post.published_date FROM post join (select id, login from account) account on account.id = post.author_id where post.id = ${req.params.id}`
  dbQuery.query(query)
    .then(response => {
      if (response.rows[0] === undefined) {
        errorMessage.error = 'Такой страницы не существует'
        return res.status(status.notfound).send(errorMessage.data)
      }
      successMessage.data = response.rows[0]
      successMessage.data.published_date = successMessage.data.published_date.toLocaleString("ru", {
        year: 'numeric',
        month: 'numeric',
        day: 'numeric',
        hour: 'numeric',
        minute: 'numeric'
      })
      return res.status(status.success).send(successMessage.data)
    })
}

/**
 * Добавляет запись поста в базу данных.
 * @param {*} req Запрос.
 * @param {*} res Ответ.
 * @return Сообщение о результате выполнения запроса.
 */
const addPost = (req, res) => {
  const {
    id,
    title,
    content,
    category,
    author_id,
    author_login,
    published_date
  } = req.body

  if (empty(title) || empty(content)) {
    errorMessage.error = 'Все поля должны быть заполнены'
    return res.status(status.bad).send(errorMessage)
  }

  const query = `INSERT INTO 
  post (title, content, category, author_id, published_date) 
  VALUES ($1, $2, $3, $4, $5) 
  RETURNING *`
  const values = [
    title,
    content,
    category,
    author_id,
    new Date()
  ]

  dbQuery.query(query, values)
    .then((response) => {
      successMessage.data = response.rows[0]
      return res.status(status.created).send(successMessage.data)
    })
    .catch((err) => {
      console.error(err.stack)
      errorMessage.error = 'Ошибка добавления поста'
      return res.status(status.error).send(errorMessage)
    })
}

/**
 * Удаляет запись поста из базы данных.
 * @param {*} req Запрос.
 * @param {*} res Ответ.
 * @return Сообщение об удалении поста.
 */
const deletePost = (req, res) => {
  const query = `DELETE FROM post WHERE id = ${req.params.id}`
  dbQuery.query(query)
    .then((response) => {
      return res.status(status.success).send(successMessage)
    })
    .catch((err) => {
      console.error(err.stack)
      errorMessage.error = 'Ошибка удаления поста'
      return res.status(status.error).send(errorMessage)
    })
}

/**
 * Получает список комментариев определённого поста.
 * @param {*} req Запрос.
 * @param {*} res Ответ.
 * @return Список комментариев.
 */
const getComments = (req, res) => {
  const query = `SELECT comment.id, account.login as author_name, comment.content, comment.published_date FROM comment left join account ON comment.author_id = account.id WHERE post_id = ${req.params.id} ORDER BY published_date DESC`
  dbQuery.query(query)
    .then((response) => {
      successMessage.data = response.rows
      successMessage.data.map((comment) => {
        comment.published_date = comment.published_date.toLocaleString("ru", {
          year: 'numeric',
          month: 'numeric',
          day: 'numeric',
          hour: 'numeric',
          minute: 'numeric'
        })
      })
      return res.status(status.success).send(successMessage.data)
    })
}

/**
 * Добавляет запись комментария в базу данных.
 * @param {*} req Запрос.
 * @param {*} res Ответ.
 * @return Сообщение о результате выполнения запроса.
 */
const addComment = (req, res) => {
  const {
    post_id,
    author_id,
    content
  } = req.body
  const query = `INSERT INTO
  comment (post_id, author_id, content, published_date)
  VALUES ($1, $2, $3, $4)
  RETURNING id, post_id, author_id, content, published_date`
  const values = [
    post_id,
    author_id,
    content,
    new Date()
  ]
  dbQuery.query(query, values)
    .then( (response) => {
      successMessage.data = response.rows[0]
      successMessage.data.published_date = successMessage.data.published_date.toLocaleString("ru", {
        year: 'numeric',
        month: 'numeric',
        day: 'numeric',
        hour: 'numeric',
        minute: 'numeric'
      })
      return res.status(status.success).send(successMessage.data)
    })
    .catch( (err) => {
      console.error(err.stack)
      errorMessage.error = 'Ошибка добавления комментария'
      return res.status(status.error).send(errorMessage)
    })
}

const deleteComment = (req, res) => {
  const query = `DELETE FROM comment WHERE id = ${req.params.id}`
  dbQuery.query(query)
    .then((response) => {
      return res.status(status.success).send(successMessage)
    })
    .catch((err) => {
      console.error(err.stack)
      errorMessage.error = 'Ошибка удаления комментария'
      return res.status(status.error).send(errorMessage)
    })
}

export {
  getAllSections,
  getAllPostsFromSection,
  getPost,
  addPost,
  deletePost,
  getComments,
  addComment,
  deleteComment
}