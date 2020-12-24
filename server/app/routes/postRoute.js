import express from 'express'
import { getAllPosts, addPost } from '../controllers/postController'

const router = express.Router()

router.get('/posts', getAllPosts)
// router.get('/post/:id', getPost)
router.post('/posts', addPost)

export default router