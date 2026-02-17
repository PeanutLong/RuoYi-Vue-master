import request from '@/utils/request'

// 查询论坛用户金币信息
export function getUserCoins() {
  return request({
    url: '/forum/coins/myCoins', // 假设你后端有这个接口
    method: 'get'
  })
}
