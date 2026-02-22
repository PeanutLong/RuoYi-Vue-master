import request from '@/utils/request'

export function getMyCoins() {
  return request({
    url: '/forum/coins/myCoins',
    method: 'get'
  })
}
