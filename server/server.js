import express from 'express'
import 'babel-polyfill'
import cors from 'cors'
import env from './env'
// Routes
import userRoute from './app/routes/userRoute'
import postRoute from './app/routes/postRoute'

const app = express()

// const io = require('socket.io')(server)

// io.on('connection', (socket) => {
//     console.log('User connected')
//     socket.on('join', (userNickname) => {
//         console.log(userNickname + " has joined the chat ")
//         socket.broadcast.emit('userjoinedthechat', userNickname + " : has joined the chat ")
//     })
//     socket.on('messagedetection', (senderNickname, messageContent) => {
//         console.log(senderNickname + " :" + messageContent)
//         let message = {"message": messageContent, "senderNickname": senderNickname}
//         io.emit('message', message)
//     })
//     socket.on('disconnect', (userNickname) => {
//         console.log(userNickname + ' has left')
//         socket.broadcast.emit('userdisconnect', 'user has left')
//     })
// })

app.use(cors())

app.use(express.urlencoded({ extended: false }))
app.use(express.json())

app.use('/api/v1', userRoute)
app.use('/api/v1', postRoute)

app.listen(env.port).on('listening', () => {
  console.log(`Server running on port ${env.port}`)
})
