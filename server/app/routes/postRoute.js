import express from 'express'
import * as PostController from '../controllers/postController'

const router = express.Router()

router.get('/sections', PostController.getAllSections)
router.get('/sections/:sectionId/posts', PostController.getAllPostsFromSection)
router.post('/sections/:sectionId/posts', PostController.addPost)
router.get('/posts/:id', PostController.getPost)
router.get('/posts/:id/comments', PostController.getComments)
router.post('/posts/:id', PostController.addComment)
router.delete('/posts/:id', PostController.deletePost)
router.delete('comments/:commentId', PostController.deleteComment)

export default router