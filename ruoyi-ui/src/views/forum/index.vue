<template>
  <div class="app-container">
    <div class="page-header">
      <h2>论坛社区</h2>
      <p class="page-subtitle">发现精彩内容，分享您的见解，与数千名开发者共同成长</p>
    </div>

    <el-row :gutter="20" class="forum-main">
      <el-col :span="24">
        <el-card class="board-card" shadow="hover">
          <div class="board-card-content">
            <div class="board-icon">
              <div class="icon-wrapper">
                <i class="el-icon-monitor"></i>
              </div>
            </div>
            <div class="board-info">
              <h3>探索所有板块</h3>
              <p class="board-description">浏览论坛的所有分类板块，找到您感兴趣的技术领域或生活话题</p>
              <div class="board-stats">
                <span><i class="el-icon-folder"></i> <b>{{ boardCount }}</b> 个板块</span>
                <span><i class="el-icon-document"></i> <b>{{ postCount }}</b> 篇帖子</span>
                <span><i class="el-icon-chat-dot-round"></i> <b>{{ replyCount }}</b> 条回复</span>
              </div>
            </div>
            <div class="board-action">
              <el-button
                type="primary"
                size="large"
                @click="goToBoards"
                icon="el-icon-connection"
                :loading="loading"
              >
                立即进入
              </el-button>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20">
      <el-col :sm="24" :md="16">
        <el-card class="hot-card" shadow="never">
          <div slot="header" class="clearfix">
            <span class="card-title">
              <i class="el-icon-medal" style="color: #f56c6c;"></i> 热门排行
            </span>
            <el-button style="float: right; padding: 3px 0" type="text" @click="handleQuickLink({path: '/forum/post/hot'})">查看更多</el-button>
          </div>
          <div v-loading="hotLoading" class="hot-list-container">
            <div
              v-for="(post, index) in hotPostList"
              :key="post.postId"
              class="hot-item"
              @click="handlePostClick(post.postId)"
            >
              <div class="hot-rank" :class="index < 3 ? 'rank-' + (index + 1) : ''">
                {{ index + 1 }}
              </div>
              <div class="hot-content">
                <div class="hot-item-title">{{ post.title }}</div>
                <div class="hot-item-meta">
                  <span class="author"><i class="el-icon-user"></i> {{ post.createBy }}</span>
                  <span><i class="el-icon-view"></i> {{ post.viewCount || 0 }}</span>
                  <span><i class="el-icon-chat-line-square"></i> {{ post.commentCount || 0 }}</span>
                  <span class="time">{{ parseTime(post.createTime, '{m}-{d}') }}</span>
                </div>
              </div>
            </div>
            <el-empty v-if="!hotLoading && hotPostList.length === 0" description="暂无热门动态" :image-size="80"></el-empty>
          </div>
        </el-card>
      </el-col>

      <el-col :sm="24" :md="8">

        <el-card v-if="isLoggedIn" class="user-info-card" shadow="hover">
          <div class="user-profile-wrapper" @click="goToProfile">
            <div class="avatar-box">
              <el-avatar :size="60" :src="avatar">
                <img src="@/assets/images/profile.jpg"/>
              </el-avatar>
            </div>
            <div class="user-info-text">
              <div class="welcome-text">欢迎回来</div>
              <div class="user-name">{{ name || '社区用户' }}</div>
            </div>

            <div class="check-in-status">
              <el-tag v-if="hasCheckedIn" type="success" size="mini" effect="dark">
                <i class="el-icon-check"></i> 已签到
              </el-tag>
            </div>
          </div>

          <div class="user-quick-actions">
            <el-button type="text" icon="el-icon-user" @click="goToProfile">个人中心</el-button>
            <el-divider direction="vertical"></el-divider>

            <el-button
              type="text"
              :icon="hasCheckedIn ? 'el-icon-circle-check' : 'el-icon-date'"
              :class="{ 'signed-in-btn': hasCheckedIn }"
              :disabled="hasCheckedIn"
              @click="handleCheckIn"
              :loading="checkInLoading"
            >
              {{ hasCheckedIn ? '今日已签' : '每日签到' }}
            </el-button>

            <el-divider direction="vertical"></el-divider>
            <el-button type="text" icon="el-icon-coin">我的金币</el-button>
          </div>
        </el-card>

        <el-row :gutter="15">
          <el-col :span="12" v-for="item in quickLinks" :key="item.id">
            <el-card class="quick-card" shadow="hover" @click.native="handleQuickLink(item)">
              <div class="quick-card-content">
                <i :class="item.icon" class="quick-icon"></i>
                <div class="quick-title">{{ item.title }}</div>
              </div>
            </el-card>
          </el-col>
        </el-row>

        <el-card v-if="!isLoggedIn" class="login-promo-card" shadow="never">
          <h4>加入我们的社区</h4>
          <p>登录后即可发帖、评论、点赞，开启你的互动之旅。</p>
          <el-button type="primary" block @click="handleLogin" style="width: 100%">立即登录 / 注册</el-button>
        </el-card>

      </el-col>
    </el-row>

    <el-alert
      v-if="isLoggedIn"
      title="小贴士：文明发帖，共建和谐社区。"
      type="success"
      show-icon
      :closable="false"
      style="margin-top: 20px;"
    />
  </div>
</template>

<script>
import { getHotThreads } from "@/api/forum/post";
import { addCheckIn, listCheckIn } from "@/api/forum/in"; // 1. 引入签到API
import { mapGetters } from "vuex";

export default {
  name: 'ForumIndex',
  computed: {
    ...mapGetters([
      'avatar',
      'name',
      'userId' // 2. 获取 userId 用于查询签到记录
    ])
  },
  data() {
    return {
      // 统计数据
      boardCount: 0,
      postCount: 0,
      replyCount: 0,
      loading: false,
      // 热门列表
      hotLoading: false,
      hotPostList: [],
      // 登录状态
      isLoggedIn: false,
      // 签到相关状态
      hasCheckedIn: false,
      checkInLoading: false,
      // 快速导航配置
      quickLinks: [
        { id: 1, title: '最新发布', icon: 'el-icon-time', path: '/forum/post/latest' },
        { id: 2, title: '精华帖子', icon: 'el-icon-trophy', path: '/forum/post/essence' },
        { id: 3, title: '待你回复', icon: 'el-icon-chat-dot-round', path: '/forum/post/unanswered' },
        { id: 4, title: '我要发帖', icon: 'el-icon-edit-outline', path: '/forum/post/create' }
      ]
    }
  },
  created() {
    this.checkLoginStatus();
    this.getForumStats();
    this.getHotList();

    // 如果已登录，检查今日是否已签到
    if (this.isLoggedIn) {
      this.getTodayCheckInStatus();
    }
  },
  methods: {
    /** 检查今日签到状态 */
    getTodayCheckInStatus() {
      if (!this.userId) return;

      // 获取今天的日期字符串 YYYY-MM-DD
      const today = this.parseTime(new Date(), '{y}-{m}-{d}');

      const query = {
        userId: this.userId,
        // 这里假设后端 list 接口支持 checkDate 参数查询
        // 如果后端是用 params[beginTime] 这种范围查询，需要相应调整
        checkDate: today
      };

      listCheckIn(query).then(response => {
        // 如果返回列表中有数据，说明今天签到过
        if (response.rows && response.rows.length > 0) {
          this.hasCheckedIn = true;
        } else {
          this.hasCheckedIn = false;
        }
      });
    },

    /** 处理签到点击 */
    handleCheckIn() {
      this.checkInLoading = true;
      addCheckIn({}).then(response => {
        this.$message.success("签到成功！金币 +5");
        this.hasCheckedIn = true;
        this.checkInLoading = false;
        // 这里可以触发一个事件或调用Vuex action来刷新右上角的金币余额（如果有的话）
      }).catch(() => {
        this.checkInLoading = false;
      });
    },

    /** 查询热门帖子列表 */
    async getHotList() {
      this.hotLoading = true;
      try {
        const response = await getHotThreads();
        this.hotPostList = response.rows || [];
      } catch (error) {
        console.error('获取热门帖子失败:', error);
      } finally {
        this.hotLoading = false;
      }
    },

    /** 模拟获取统计数据 */
    getForumStats() {
      this.loading = true;
      setTimeout(() => {
        this.boardCount = 12;
        this.postCount = 428;
        this.replyCount = 1562;
        this.loading = false;
      }, 600);
    },

    /** 跳转至帖子详情 */
    handlePostClick(postId) {
      this.$router.push('/forum/post/detail/' + postId);
    },

    /** 跳转至所有板块 */
    goToBoards() {
      this.$router.push('/forum/board');
    },

    /** 跳转至个人中心 */
    goToProfile() {
      this.$router.push('/forum/user/profile');
    },

    /** 处理快速链接点击 */
    handleQuickLink(item) {
      if (item.path === '/forum/post/create' && !this.isLoggedIn) {
        this.$message.warning('请先登录后再进行操作');
        this.handleLogin();
        return;
      }
      this.$router.push(item.path);
    },

    /** 跳转登录 */
    handleLogin() {
      this.$router.push('/login');
    },

    /** 检查登录状态 */
    checkLoginStatus() {
      const token = localStorage.getItem('token') || this.$store?.getters?.token;
      this.isLoggedIn = !!token;
    }
  }
}
</script>

<style lang="scss" scoped>
.app-container {
  padding: 30px 20px;
  background-color: #f8f9fa;
  min-height: calc(100vh - 84px);
}

// 头部样式
.page-header {
  text-align: center;
  margin-bottom: 40px;

  h2 {
    font-size: 36px;
    color: #2c3e50;
    margin-bottom: 12px;
    letter-spacing: 1px;
  }

  .page-subtitle {
    font-size: 16px;
    color: #7f8c8d;
  }
}

// 主板块卡片
.board-card {
  border-radius: 12px;
  border: none;
  margin-bottom: 30px;

  .board-card-content {
    display: flex;
    align-items: center;
    padding: 10px;
  }

  .icon-wrapper {
    width: 80px;
    height: 80px;
    background: linear-gradient(135deg, #409EFF 0%, #79bbff 100%);
    border-radius: 16px;
    display: flex;
    justify-content: center;
    align-items: center;
    margin-right: 25px;

    i {
      font-size: 40px;
      color: #fff;
    }
  }

  .board-info {
    flex: 1;

    h3 {
      font-size: 22px;
      margin-bottom: 8px;
      color: #303133;
    }

    .board-description {
      color: #909399;
      font-size: 14px;
      margin-bottom: 15px;
    }

    .board-stats {
      span {
        margin-right: 20px;
        color: #606266;
        font-size: 14px;

        b {
          color: #409EFF;
          margin: 0 4px;
        }
      }
    }
  }
}

// 热门榜单
.hot-card {
  border-radius: 12px;
  min-height: 480px;

  .card-title {
    font-size: 18px;
    font-weight: bold;
  }

  .hot-list-container {
    padding: 5px 0;
  }
}

.hot-item {
  display: flex;
  padding: 15px;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.2s;

  &:hover {
    background-color: #f0f7ff;
    transform: translateX(5px);
  }
}

.hot-rank {
  width: 28px;
  height: 28px;
  border-radius: 6px;
  background-color: #f0f2f5;
  color: #909399;
  text-align: center;
  line-height: 28px;
  font-weight: bold;
  margin-right: 15px;
  flex-shrink: 0;

  &.rank-1 {
    background: #ff4d4f;
    color: #fff;
  }

  &.rank-2 {
    background: #ff7a45;
    color: #fff;
  }

  &.rank-3 {
    background: #ffc53d;
    color: #fff;
  }
}

.hot-content {
  flex: 1;
  overflow: hidden;

  .hot-item-title {
    font-size: 15px;
    color: #303133;
    font-weight: 500;
    margin-bottom: 8px;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
  }

  .hot-item-meta {
    font-size: 12px;
    color: #999;
    display: flex;
    gap: 15px;

    i {
      margin-right: 3px;
    }

    .author {
      color: #666;
      max-width: 100px;
      overflow: hidden;
      text-overflow: ellipsis;
    }
  }
}

// 个人中心卡片
.user-info-card {
  margin-bottom: 20px;
  border-radius: 10px;
  border: none;
  background: linear-gradient(to right bottom, #ffffff, #fdfbfb);

  .user-profile-wrapper {
    display: flex;
    align-items: center;
    cursor: pointer;
    padding: 5px;
    transition: all 0.3s;

    &:hover {
      opacity: 0.8;
      transform: translateX(5px);
    }
  }

  .avatar-box {
    margin-right: 15px;
    border: 2px solid #eaebed;
    border-radius: 50%;
    padding: 2px;
  }

  .user-info-text {
    flex: 1;

    .welcome-text {
      font-size: 12px;
      color: #909399;
      margin-bottom: 5px;
    }

    .user-name {
      font-size: 18px;
      font-weight: bold;
      color: #303133;
    }
  }

  .arrow-btn {
    color: #c0c4cc;
  }
  .user-profile-wrapper {
    position: relative; // 为了让 check-in-status 绝对定位
    // ...
  }

  .check-in-status {
    position: absolute;
    right: 5px;
    top: 5px;
  }

  .user-quick-actions {
    // ...

    // 给已签到的按钮一个特殊的颜色，比如绿色
    .signed-in-btn {
      color: #67C23A;
      cursor: default;

      &:hover {
        color: #67C23A; // 悬停也不变色
      }
    }
  }

  .user-quick-actions {
    margin-top: 15px;
    padding-top: 15px;
    border-top: 1px solid #f0f0f0;
    display: flex;
    justify-content: space-around;

    .el-button {
      color: #606266;

      &:hover {
        color: #409EFF;
      }
    }
  }
}

// 快速导航
.quick-card {
  margin-bottom: 15px;
  text-align: center;
  cursor: pointer;
  border: none;
  border-radius: 10px;

  .quick-card-content {
    padding: 10px 0;

    .quick-icon {
      font-size: 28px;
      color: #409EFF;
      margin-bottom: 10px;
    }

    .quick-title {
      font-size: 14px;
      color: #333;
    }
  }

  &:hover {
    background-color: #409EFF;

    .quick-icon, .quick-title {
      color: #fff;
    }
  }
}

.login-promo-card {
  margin-top: 5px;
  background: linear-gradient(to bottom, #ffffff, #f0f7ff);
  border-radius: 12px;
  text-align: center;

  h4 {
    margin-top: 0;
    color: #303133;
  }

  p {
    font-size: 13px;
    color: #606266;
    margin-bottom: 20px;
    line-height: 1.6;
  }
}

@media (max-width: 768px) {
  .board-card-content {
    flex-direction: column;
    text-align: center;
  }
  .icon-wrapper {
    margin-right: 0 !important;
    margin-bottom: 15px;
  }
  .board-action {
    width: 100%;
    margin-top: 20px;

    .el-button {
      width: 100%;
    }
  }
}
</style>
