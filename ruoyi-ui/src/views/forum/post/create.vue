<template>
  <div class="app-container">
    <el-card shadow="never" class="create-card">
      <div slot="header" class="clearfix">
        <span>发布新帖子</span>
        <el-button style="float: right; padding: 3px 0" type="text" @click="handleBack">返回列表</el-button>
      </div>

      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="标题" prop="title">
          <el-input v-model="form.title" placeholder="请输入引人注目的标题" maxlength="100" show-word-limit />
        </el-form-item>

        <el-form-item label="板块" prop="boardId">
          <el-select v-model="form.boardId" placeholder="请选择所属板块" style="width: 100%">
            <el-option label="技术交流" :value="1"></el-option>
            <el-option label="生活闲谈" :value="2"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="类型" prop="postType">
          <el-radio-group v-model="form.postType">
            <el-radio label="0">普通讨论</el-radio>
            <el-radio label="1">求助悬赏</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item v-if="form.postType === '1'" label="悬赏金币" prop="bountyCoins">
          <el-input-number
            v-model="form.bountyCoins"
            :min="1"
            :max="1000"
            label="输入悬赏金额"
          ></el-input-number>
          <span class="tip-text">您当前拥有金币: {{ userCoins }} (这里需对接实际余额)</span>
        </el-form-item>

        <el-form-item label="内容" prop="content">
          <editor v-model="form.content" :min-height="300" />
        </el-form-item>

        <el-form-item>
          <el-button type="primary" :loading="submitLoading" @click="submitForm">立即发布</el-button>
          <el-button @click="handleBack">取消</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
import { addPost } from "@/api/forum/post";
import Editor from '@/components/Editor'; // 引入 RuoYi 自带的富文本组件

export default {
  name: "ForumPostCreate",
  components: { Editor },
  data() {
    return {
      submitLoading: false,
      userCoins: 100, // 模拟用户余额，建议从 Vuex 或 API 获取
      form: {
        title: undefined,
        boardId: undefined,
        postType: "0", // 默认普通帖
        bountyCoins: 10, // 默认悬赏
        content: ""
      },
      rules: {
        title: [
          { required: true, message: "帖子标题不能为空", trigger: "blur" },
          { min: 5, max: 100, message: '长度在 5 到 100 个字符', trigger: 'blur' }
        ],
        boardId: [
          { required: true, message: "请选择所属板块", trigger: "change" }
        ],
        content: [
          { required: true, message: "帖子内容不能为空", trigger: "blur" }
        ],
        bountyCoins: [
          { required: true, message: "悬赏金额不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    // 获取路由参数中的 boardId
    const boardId = this.$route.query.boardId;
    if (boardId) {
      // 转为数字类型，防止 select 组件回显失效
      this.form.boardId = parseInt(boardId);
    }
  },
  methods: {
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          // 额外的逻辑校验
          if (this.form.postType === '1' && this.form.bountyCoins > this.userCoins) {
            this.$modal.msgError("您的金币余额不足！");
            return;
          }

          this.submitLoading = true;
          addPost(this.form).then(response => {
            this.$modal.msgSuccess("发布成功");
            this.handleBack();
          }).finally(() => {
            this.submitLoading = false;
          });
        }
      });
    },
    /** 返回 */
    handleBack() {
      // 返回上一页，或者指定跳转到板块页
      if (this.form.boardId) {
        this.$router.push({ path: '/forum/post/list', query: { boardId: this.form.boardId } });
      } else {
        this.$router.go(-1);
      }
    }
  }
};
</script>

<style lang="scss" scoped>
.create-card {
  max-width: 1000px;
  margin: 0 auto;
}
.tip-text {
  font-size: 12px;
  color: #909399;
  margin-left: 10px;
}
</style>
