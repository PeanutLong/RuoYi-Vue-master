<template>
  <div>
    <forum-banner/>
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
            <el-select v-model="form.boardId" placeholder="请选择所属板块" style="width: 100%" @change="handleBoardChange">
              <el-option
                v-for="item in boardOptions"
                :key="item.boardId"
                :label="item.boardName"
                :value="item.boardId">
              </el-option>
            </el-select>
          </el-form-item>

          <!-- 自动根据板块显示悬赏输入框，无需手动选择类型 -->
          <el-form-item v-if="form.postType === '1'" label="悬赏金币" prop="bountyCoins">
            <el-input-number
              v-model="form.bountyCoins"
              :min="1"
              :max="1000"
              label="输入悬赏金额"
            ></el-input-number>
            <span class="tip-text">您当前拥有金币: {{ userCoins }}</span>
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
  </div>

</template>

<script>
import { addPost } from "@/api/forum/post";
import { listBoard } from "@/api/forum/board";
import { getMyCoins } from "@/api/forum/coins";
import Editor from '@/components/Editor';
import ForumBanner from '@/components/Banner';

export default {
  name: "ForumPostCreate",
  components: { Editor,ForumBanner },
  data() {
    return {
      submitLoading: false,
      boardOptions: [],
      userCoins: 0,
      // 求助板块ID列表（根据实际数据修改）
      helpBoardIds: [2],  // 示例中 ID=2 为求助区，如有多个可添加
      form: {
        title: undefined,
        boardId: undefined,
        postType: "0",     // 默认为普通讨论
        bountyCoins: 10,
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
        ]
      }
    };
  },
  created() {
    const boardId = this.$route.query.boardId;
    if (boardId) {
      this.form.boardId = parseInt(boardId);
    }
    this.fetchBoardList().then(() => {
      // 如果初始有板块ID，立即触发类型设置
      if (this.form.boardId) {
        this.handleBoardChange(this.form.boardId);
      }
    });
    this.fetchUserCoins();
  },
  methods: {
    fetchBoardList() {
      return listBoard().then(response => {
        // 根据后端实际返回结构调整（若依框架通常返回 { rows: [...] }）
        this.boardOptions = response.rows || [];
      });
    },
    fetchUserCoins() {
      getMyCoins().then(response => {
        if (response.code === 200) {
          // 根据您提供的接口数据，使用 availableCoins 字段
          this.userCoins = response.data.availableCoins || 0;
        }
      });
    },
    // 板块切换时自动设置帖子类型
    handleBoardChange(boardId) {
      // 判断当前选择的板块是否在求助板块列表中
      const isHelp = this.helpBoardIds.includes(boardId);
      this.form.postType = isHelp ? '1' : '0';
      // 如果是求助区且未设置悬赏金额，给个默认值
      if (isHelp && !this.form.bountyCoins) {
        this.form.bountyCoins = 10;
      }
    },
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          // 悬赏帖额外校验
          if (this.form.postType === '1') {
            if (!this.form.bountyCoins) {
              this.$modal.msgError("悬赏金额不能为空");
              return;
            }
            if (this.form.bountyCoins > this.userCoins) {
              this.$modal.msgError("您的金币余额不足！");
              return;
            }
          }

          this.submitLoading = true;
          addPost(this.form).then(response => {
            if (response.code === 200) {
              this.$modal.msgSuccess("发布成功");
              const postId = response.data;  // 确认这是帖子ID
              this.$router.push(`/forum/post/detail/${postId}`);
            }
          }).finally(() => {
            this.submitLoading = false;
          });
        }
      });
    },
    handleBack() {
      if (this.form.boardId) {
        this.$router.push({path: '/forum/post/list', query: {boardId: this.form.boardId}});
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
