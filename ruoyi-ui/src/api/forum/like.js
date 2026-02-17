import request from '@/utils/request'

export function toggleLike(data) {
  return request({
    url: '/system/like/toggle',
    method: 'post',
    data: data
  })
}
