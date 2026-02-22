<template>
  <div class="forum-banner">
    <div class="banner-container">
      <div class="logo">
        <router-link to="/forum/index">屌茅论坛</router-link>
      </div>
      <div class="nav-links">
        <router-link to="/forum/index">首页</router-link>
        <router-link to="/forum/board">所有板块</router-link>
      </div>
      <div class="user-section">
        <template v-if="isLoggedIn">
          <el-button
            type="text"
            @click="handleCheckIn"
            :disabled="hasCheckedIn"
            :loading="checkInLoading"
            class="checkin-btn"
          >
            <i :class="hasCheckedIn ? 'el-icon-success' : 'el-icon-edit'"></i>
            {{ hasCheckedIn ? '今日已签' : '每日签到' }}
          </el-button>
          <router-link to="/forum/user/profile" class="user-profile">
            <el-avatar :size="32" :src="avatar">
              <img src="@/assets/images/profile.jpg" alt="avatar">
            </el-avatar>
            <span class="username">{{ name }}</span>
          </router-link>
        </template>
        <template v-else>
          <el-button type="text" @click="goToLogin">登录</el-button>
          <el-button type="text" @click="goToRegister">注册</el-button>
        </template>
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex';
import { addCheckIn, listCheckIn } from "@/api/forum/in";

export default {
  name: 'ForumBanner',
  data() {
    return {
      hasCheckedIn: false,
      checkInLoading: false
    };
  },
  computed: {
    ...mapGetters(['avatar', 'name', 'userId']),
    isLoggedIn() {
      return !!this.$store.getters.token;
    }
  },
  created() {
    if (this.isLoggedIn) {
      this.getTodayCheckInStatus();
    }
  },
  methods: {
    // 检查今日是否已签到
    getTodayCheckInStatus() {
      if (!this.userId) return;
      const today = new Date();
      const year = today.getFullYear();
      const month = (today.getMonth() + 1).toString().padStart(2, '0');
      const day = today.getDate().toString().padStart(2, '0');
      const todayStr = `${year}-${month}-${day}`;
      const query = {
        userId: this.userId,
        checkDate: todayStr
      };
      listCheckIn(query).then(response => {
        this.hasCheckedIn = response.rows && response.rows.length > 0;
      }).catch(() => {});
    },
    // 签到
    handleCheckIn() {
      if (!this.isLoggedIn) {
        this.$message.warning('请先登录');
        return;
      }
      this.checkInLoading = true;
      addCheckIn({}).then(response => {
        this.$message.success('签到成功！金币 +5');
        this.hasCheckedIn = true;
        // 可触发全局金币刷新
      }).finally(() => {
        this.checkInLoading = false;
      });
    },
    goToLogin() {
      this.$router.push('/login');
    },
    goToRegister() {
      this.$router.push('/register');
    }
  }
};
</script>

<style lang="scss" scoped>
.forum-banner {
  background-color: #fff;
  border-bottom: 1px solid #eaeef2;
  box-shadow: 0 2px 8px rgba(0,0,0,0.05);
  position: sticky;
  top: 0;
  z-index: 1000;
}
.banner-container {
  max-width: 1400px;
  margin: 0 auto;
  height: 60px;
  display: flex;
  align-items: center;
  padding: 0 20px;
}
.logo {
  font-size: 20px;
  font-weight: bold;
  margin-right: 40px;
  a {
    color: #303133;
    text-decoration: none;
  }
}
.nav-links {
  flex: 1;
  a {
    margin-right: 30px;
    color: #606266;
    text-decoration: none;
    font-size: 16px;
    &:hover {
      color: #409EFF;
    }
    &.router-link-active {
      color: #409EFF;
      font-weight: 500;
    }
  }
}
.user-section {
  display: flex;
  align-items: center;
  .checkin-btn {
    margin-right: 20px;
    color: #e6a23c;
    &:hover {
      color: #f5b84c;
    }
    &:disabled {
      color: #c0c4cc;
    }
  }
  .user-profile {
    display: flex;
    align-items: center;
    text-decoration: none;
    .username {
      margin-left: 8px;
      color: #333;
      font-size: 14px;
    }
    &:hover .username {
      color: #409EFF;
    }
  }
}
</style>
