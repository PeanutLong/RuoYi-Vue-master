<template>
  <div class="login">
    <div class="login-container">
      <div class="login-header">
        <h2 class="title">{{ title }}</h2>
        <p class="subtitle">欢迎回来，请登录您的账户</p>
      </div>

      <el-form
        ref="loginForm"
        :model="loginForm"
        :rules="loginRules"
        class="login-form"
      >
        <el-form-item prop="username">
          <el-input
            v-model="loginForm.username"
            type="text"
            auto-complete="off"
            placeholder="账号"
            prefix-icon="el-icon-user"
          />
        </el-form-item>

        <el-form-item prop="password">
          <el-input
            v-model="loginForm.password"
            type="password"
            auto-complete="off"
            placeholder="密码"
            prefix-icon="el-icon-lock"
            @keyup.enter.native="handleLogin"
          />
        </el-form-item>

        <el-form-item prop="code" v-if="captchaEnabled">
          <div class="code-wrap">
            <el-input
              v-model="loginForm.code"
              auto-complete="off"
              placeholder="验证码"
              prefix-icon="el-icon-key"
              @keyup.enter.native="handleLogin"
            />
            <div class="login-code">
              <img :src="codeUrl" @click="getCode" class="login-code-img" />
            </div>
          </div>
        </el-form-item>

        <div class="login-options">
          <el-checkbox v-model="loginForm.rememberMe">记住密码</el-checkbox>
          <router-link v-if="register" to="/register" class="link-type">立即注册</router-link>
        </div>

        <el-form-item>
          <el-button
            :loading="loading"
            type="primary"
            class="login-btn"
            @click.native.prevent="handleLogin"
          >
            {{ loading ? '登录中...' : '登 录' }}
          </el-button>
        </el-form-item>
      </el-form>
    </div>

    <div class="login-footer">
      <span>Copyright © 2018-2025 ruoyi.vip All Rights Reserved.</span>
    </div>
  </div>
</template>

<script>
import { getCodeImg } from "@/api/login";
import Cookies from "js-cookie";
import { encrypt, decrypt } from "@/utils/jsencrypt";

export default {
  name: "Login",
  data() {
    return {
      title: process.env.VUE_APP_TITLE,
      codeUrl: "",
      loginForm: {
        username: "admin",
        password: "admin123",
        rememberMe: false,
        code: "",
        uuid: ""
      },
      loginRules: {
        username: [
          { required: true, trigger: "blur", message: "请输入您的账号" }
        ],
        password: [
          { required: true, trigger: "blur", message: "请输入您的密码" }
        ],
        code: [{ required: true, trigger: "change", message: "请输入验证码" }]
      },
      loading: false,
      captchaEnabled: true,
      register: false,
      redirect: undefined
    };
  },
  watch: {
    $route: {
      handler: function(route) {
        this.redirect = route.query && route.query.redirect;
      },
      immediate: true
    }
  },
  created() {
    this.getCode();
    this.getCookie();
  },
  methods: {
    getCode() {
      getCodeImg().then(res => {
        this.captchaEnabled =
          res.captchaEnabled === undefined ? true : res.captchaEnabled;
        if (this.captchaEnabled) {
          this.codeUrl = "data:image/gif;base64," + res.img;
          this.loginForm.uuid = res.uuid;
        }
      });
    },
    getCookie() {
      const username = Cookies.get("username");
      const password = Cookies.get("password");
      const rememberMe = Cookies.get("rememberMe");
      this.loginForm = {
        username: username === undefined ? this.loginForm.username : username,
        password:
          password === undefined ? this.loginForm.password : decrypt(password),
        rememberMe: rememberMe === undefined ? false : Boolean(rememberMe)
      };
    },
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true;
          if (this.loginForm.rememberMe) {
            Cookies.set("username", this.loginForm.username, { expires: 30 });
            Cookies.set("password", encrypt(this.loginForm.password), {
              expires: 30
            });
            Cookies.set("rememberMe", this.loginForm.rememberMe, {
              expires: 30
            });
          } else {
            Cookies.remove("username");
            Cookies.remove("password");
            Cookies.remove("rememberMe");
          }
          this.$store
            .dispatch("Login", this.loginForm)
            .then(() => {
              this.$router.push({ path: "/forum/index" }).catch(() => {});
            })
            .catch(() => {
              this.loading = false;
              if (this.captchaEnabled) {
                this.getCode();
              }
            });
        }
      });
    }
  }
};
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.login {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  /* 替换为背景图片 */
  background: url("../assets/images/login-background.jpg") no-repeat center center fixed;
  background-size: cover;
  font-family: "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB", "Microsoft YaHei", sans-serif;
}

.login-container {
  width: 420px;
  padding: 40px 35px;
  background: rgba(255, 255, 255, 0);
  backdrop-filter: blur(10px);
  border-radius: 20px;
  box-shadow: 0 15px 35px rgba(0, 0, 0, 0.2);
  transition: all 0.3s ease;
}

.login-header {
  text-align: center;
  margin-bottom: 40px;

  .title {
    margin: 0 0 8px;
    font-size: 28px;
    font-weight: 600;
    color: #333;
    letter-spacing: 1px;
  }

  .subtitle {
    margin: 0;
    font-size: 14px;
    color: #888;
  }
}

.login-form {
  .el-form-item {
    margin-bottom: 25px;
  }

  .el-input {
    .el-input__inner {
      height: 48px;
      line-height: 48px;
      border: 1px solid #e0e0e0;
      border-radius: 30px;
      padding: 0 20px 0 45px;
      background: #f9f9f9;
      transition: all 0.3s;

      &:focus {
        border-color: #667eea;
        background: #fff;
        box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1);
      }
    }

    .el-input__prefix {
      left: 15px;
      color: #999;
    }
  }

  .code-wrap {
    display: flex;
    align-items: center;

    .el-input {
      flex: 1;
      margin-right: 10px;
    }

    .login-code {
      width: 110px;
      height: 48px;
      border-radius: 30px;
      overflow: hidden;
      cursor: pointer;
      box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);

      img {
        width: 100%;
        height: 100%;
        object-fit: cover;
        transition: opacity 0.3s;

        &:hover {
          opacity: 0.8;
        }
      }
    }
  }

  .login-options {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 20px;
    font-size: 14px;

    .el-checkbox {
      color: #666;

      .el-checkbox__label {
        padding-left: 5px;
      }
    }

    .link-type {
      color: #667eea;
      text-decoration: none;
      font-weight: 500;

      &:hover {
        text-decoration: underline;
      }
    }
  }

  .login-btn {
    width: 100%;
    height: 48px;
    border: none;
    border-radius: 30px;
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    font-size: 16px;
    font-weight: 500;
    letter-spacing: 1px;
    box-shadow: 0 8px 15px rgba(102, 126, 234, 0.3);
    transition: all 0.3s;

    &:hover {
      transform: translateY(-2px);
      box-shadow: 0 15px 25px rgba(102, 126, 234, 0.4);
    }

    &:active {
      transform: translateY(0);
    }
  }
}

.login-footer {
  margin-top: 30px;
  color: rgba(255, 255, 255, 0.8);
  font-size: 12px;
  text-align: center;
}
</style>
