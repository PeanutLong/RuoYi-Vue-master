import request from '@/utils/request'

// 查询回复列表
export function listReply(query) {
  return request({
    url: '/forum/reply/list',
    method: 'get',
    params: query
  })
}

// 新增回复
export function addReply(data) {
  return request({
    url: '/forum/reply',
    method: 'post',
    data: data
  })
}
