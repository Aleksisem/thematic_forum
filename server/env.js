import dotenv from 'dotenv'

dotenv.config()

export default {
  db_host: process.env.DB_HOST,
  db_user: process.env.DB_USER,
  db_password: process.env.DB_PASSWORD,
  db_name: process.env.DB_NAME,
  db_port: process.env.DB_PORT,
  port: process.env.PORT || 5000,
  environment: process.env.NODE_ENV
}