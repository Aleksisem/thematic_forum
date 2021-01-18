import express from 'express'

import * as UserController from '../controllers/userController'

const router = express.Router()

router.post('/auth/join', UserController.addUser)
router.post('/auth/login', UserController.getUser)
router.post('/auth/exit', UserController.updateUser)

export default router