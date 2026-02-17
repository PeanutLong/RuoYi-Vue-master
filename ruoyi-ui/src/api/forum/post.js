import request from '@/utils/request'

// 查询热门帖子列表
export function getHotThreads() {
  return request({
    url: '/forum/post/hotThread',
    method: 'get'
  })
}
export function getPost(postId) {
  return request({
    url: '/forum/post/' + postId,
    method: 'get'
  })
}
// 查询帖子列表
export function listPost(query) {
  return request({
    url: '/forum/post/list',
    method: 'get',
    params: query
  })
}
export function addPost(data) {
  return request({
    url: '/forum/post',
    method: 'post',
    data: data
  })
}

