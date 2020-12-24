import { Pool } from 'pg'
import env from '../../env'

const databaseConfig = {
  user: env.db_user,
  host: env.db_host,
  database: env.db_name,
  password: env.db_password,
  port: env.db_port
}
const pool = new Pool(databaseConfig)

export default pool