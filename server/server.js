import express from 'express'
import 'babel-polyfill'
import cors from 'cors'
import env from './env'
// Routes
import userRoute from './app/routes/userRoute'
import postRoute from './app/routes/postRoute'

const app = express()

app.use(cors())

app.use(express.urlencoded({ extended: false }))
app.use(express.json())

app.use('/api/v1', userRoute)
app.use('/api/v1', postRoute)

app.listen(env.port).on('listening', () => {
  console.log(`Server running on port ${env.port}`)
})

export {
  app
}
