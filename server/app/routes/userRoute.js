import express from 'express'

import { addUser, getUser, updateUser, deleteUser } from '../controllers/userController'

const router = express.Router()

router.post('/auth/join', addUser)
router.post('/auth/login', getUser)
router.get('/auth/exit', updateUser)
router.post('/users/delete', deleteUser)

export default router