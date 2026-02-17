import request from '@/utils/request'

// 论坛板块API

// 查询论坛板块列表
export function listBoard(query) {
  return request({
    url: '/forum/board/list',
    method: 'get',
    params: query
  })
}

// 获取论坛板块详细信息
export function getBoard(boardId) {
  return request({
    url: `/forum/board/${boardId}`,
    method: 'get'
  })
}

// 新增论坛板块
export function addBoard(data) {
  return request({
    url: '/forum/board',
    method: 'post',
    data: data
  })
}

// 修改论坛板块
export function updateBoard(data) {
  return request({
    url: '/forum/board',
    method: 'put',
    data: data
  })
}

// 删除论坛板块
export function delBoard(boardIds) {
  return request({
    url: `/forum/board/${boardIds}`,
    method: 'delete'
  })
}

// 导出论坛板块
export function exportBoard(query) {
  return request({
    url: '/forum/board/export',
    method: 'post',
    responseType: 'blob',
    params: query
  })
}
