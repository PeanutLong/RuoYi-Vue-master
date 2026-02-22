import request from '@/utils/request'

// 采纳最佳答案
export function acceptBountyAnswer(data) {
  return request({
    url: '/forum/bounty/accept',
    method: 'post',
    data: data
  })
}
