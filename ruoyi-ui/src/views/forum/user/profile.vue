<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="6" :xs="24">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>个人信息</span>
          </div>
          <div>
            <div class="text-center">
              <userAvatar :user="user" />
            </div>
            <ul class="list-group list-group-striped">
              <li class="list-group-item">
                <i class="el-icon-user"></i> 用户账号
                <div class="pull-right">{{ user.userName }}</div>
              </li>
              <li class="list-group-item">
                <i class="el-icon-info"></i> 用户昵称
                <div class="pull-right">{{ user.nickName }}</div>
              </li>
              <li class="list-group-item">
                <i class="el-icon-phone"></i> 手机号码
                <div class="pull-right">{{ user.phonenumber }}</div>
              </li>
              <li class="list-group-item">
                <i class="el-icon-message"></i> 用户邮箱
                <div class="pull-right">{{ user.email }}</div>
              </li>
              <li class="list-group-item">
                <i class="el-icon-office-building"></i> 所属部门
                <div class="pull-right" v-if="user.dept">{{ user.dept.deptName }}</div>
              </li>
            </ul>
          </div>
        </el-card>
      </el-col>

      <el-col :span="18" :xs="24">
        <el-row :gutter="10" style="margin-bottom: 20px;">
          <el-col :span="8">
            <el-card shadow="hover" class="coin-card">
              <div class="coin-title">可用金币</div>
              <div class="coin-value">{{ coins.availableCoins || 0 }}</div>
            </el-card>
          </el-col>
          <el-col :span="8">
            <el-card shadow="hover" class="coin-card">
              <div class="coin-title">冻结金币</div>
              <div class="coin-value gray">{{ coins.frozenCoins || 0 }}</div>
            </el-card>
          </el-col>
          <el-col :span="8">
            <el-card shadow="hover" class="coin-card pink">
              <div class="coin-title">总金币数</div>
              <div class="coin-value">{{ coins.totalCoins || 0 }}</div>
            </el-card>
          </el-col>
        </el-row>

        <el-card>
          <el-tabs v-model="activeTab">
            <el-tab-pane label="基本资料" name="userinfo">
              <el-form ref="form" :model="user" label-width="80px">
                <el-form-item label="用户昵称" prop="nickName">
                  <el-input v-model="user.nickName" maxlength="30" />
                </el-form-item>
                <el-form-item label="手机号码" prop="phonenumber">
                  <el-input v-model="user.phonenumber" maxlength="11" />
                </el-form-item>
                <el-form-item label="邮箱" prop="email">
                  <el-input v-model="user.email" maxlength="50" />
                </el-form-item>
                <el-form-item label="性别">
                  <el-radio-group v-model="user.sex">
                    <el-radio label="0">男</el-radio>
                    <el-radio label="1">女</el-radio>
                  </el-radio-group>
                </el-form-item>
                <el-form-item>
                  <el-button type="primary" size="mini">保存修改</el-button>
                </el-form-item>
              </el-form>
            </el-tab-pane>
            <el-tab-pane label="我的帖子" name="myPosts">
              <p style="color: #999; text-align: center; padding: 20px;">暂无发帖记录</p>
            </el-tab-pane>
          </el-tabs>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import userAvatar from "@/views/system/user/profile/userAvatar";
import { getUserProfile } from "@/api/system/user";
import { getUserCoins } from "@/api/forum/user";

export default {
  name: "ForumProfile",
  components: { userAvatar },
  data() {
    return {
      user: {},
      coins: {},
      activeTab: "userinfo",
      loading: false
    };
  },
  created() {
    this.getUser();
    this.getCoins();
  },
  methods: {
    getUser() {
      getUserProfile().then(response => {
        this.user = response.data;
      });
    },
    getCoins() {
      getUserCoins().then(response => {
        this.coins = response.data;
      });
    }
  }
};
</script>

<style lang="scss" scoped>
.list-group-item {
  border-left: none;
  border-right: none;
  border-radius: 0;
  padding-left: 0;
  padding-right: 0;
  display: flex;
  justify-content: space-between;
  align-items: center;
  border-bottom: 1px solid #e7eaec;
  padding: 11px 0;
  font-size: 13px;
}

.coin-card {
  text-align: center;
  border-radius: 8px;
  background: linear-gradient(135deg, #ff9a9e 0%, #fecfef 100%);
  color: white;
  border: none;

  .coin-title { font-size: 14px; opacity: 0.9; }
  .coin-value { font-size: 24px; font-weight: bold; margin-top: 10px; }

  &.pink { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); }
  .gray { color: #f0f0f0; }
}

.text-center {
  text-align: center;
  position: relative;
  margin-bottom: 20px;
}

.pull-right {
  float: right !important;
  color: #666;
}
</style>
