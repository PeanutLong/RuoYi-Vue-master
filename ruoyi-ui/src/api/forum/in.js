import request from '@/utils/request'

// 查询签到列表 (用于检测今日是否已签到)
export function listCheckIn(query) {
  return request({
    url: '/forum/in/list',
    method: 'get',
    params: query
  })
}

// 新增签到 (执行签到动作)
export function addCheckIn(data) {
  return request({
    url: '/forum/in',
    method: 'post',
    data: data
  })
}
