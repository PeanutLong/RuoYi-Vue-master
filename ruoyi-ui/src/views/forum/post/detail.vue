<template>
  <div class="app-container">
    <el-card v-loading="loading" class="post-detail-card">
      <div class="post-header">
        <h1 class="post-title">{{ form.title }}</h1>

        <div v-if="form.postType === '1'" class="bounty-banner">
          <div class="bounty-status">
            <i class="el-icon-coin gold-icon"></i>
            <span class="bounty-text">悬赏中</span>
            <span class="bounty-amount">{{ form.bountyCoins || 0 }}</span>
            <span class="bounty-unit">金币</span>
          </div>
          <div class="bounty-tip">采纳最佳答案后，悬赏金币将发放给回答者</div>
        </div>

        <div class="post-meta">
          <el-tag size="mini" :type="form.postType === '1' ? 'danger' : 'info'" effect="plain">
            {{ form.postType === '1' ? '悬赏' : '普通' }}
          </el-tag>
          <span><i class="el-icon-user"></i> {{ form.createBy }}</span>
          <span><i class="el-icon-time"></i> {{ form.createTime }}</span>
          <span><i class="el-icon-view"></i> {{ form.viewCount || 0 }} 浏览</span>

          <span v-if="form.postType === '1'" class="meta-bounty">
            <i class="el-icon-coin"></i> {{ form.bountyCoins || 0 }}
          </span>
        </div>
      </div>

      <el-divider></el-divider>

      <div class="post-content">
        <div v-html="form.content" class="content-body"></div>
      </div>

      <div class="post-footer">
        <el-button type="primary" icon="el-icon-back" @click="handleBack">返回首页</el-button>
        <el-button
          type="success"
          icon="el-icon-star-off"
          @click="handlePostLike"
        >
          点赞 ({{ form.likeCount || 0 }})
        </el-button>
      </div>

      <el-divider></el-divider>

      <div class="reply-section">
        <h3 class="reply-count">全部回复 ({{ replyTotal }})</h3>

        <div v-for="(reply, index) in replyList" :key="reply.replyId" class="reply-item">
          <div v-if="reply.replyId === form.acceptedAnswerId" class="accepted-badge">
            <i class="el-icon-check"></i> 最佳答案
          </div>

          <div class="reply-user-info">
            <span class="reply-author">{{ reply.createBy }}</span>
            <el-tag v-if="reply.isAuthor === '1'" size="mini" type="success" effect="plain">楼主</el-tag>
            <span class="reply-floor">{{ reply.floorNum }}楼</span>
            <span class="reply-time">{{ reply.createTime }}</span>
          </div>

          <div class="reply-content" v-html="reply.content"></div>

          <div class="reply-actions">
            <el-button
              v-if="form.postType === '1' && form.userId === $store.state.user.userId && !form.acceptedAnswerId && reply.isAuthor !== '1'"
              type="text"
              icon="el-icon-trophy"
              style="color: #e6a23c"
              @click="handleAcceptAnswer(reply)"
            >采纳为最佳答案</el-button>

            <el-button type="text" icon="el-icon-chat-dot-round" @click="handleSubReply(reply)">回复</el-button>
            <el-button
              type="text"
              icon="el-icon-caret-top"
              @click="handleReplyLike(reply)"
            >
              赞 ({{ reply.likeCount || 0 }})
            </el-button>
          </div>
          <el-divider></el-divider>
        </div>

        <pagination
          v-show="replyTotal > 0"
          :total="replyTotal"
          :page.sync="replyQuery.pageNum"
          :limit.sync="replyQuery.pageSize"
          @pagination="getReplyList"
        />
      </div>

      <div class="reply-input-container">
        <h3 id="reply-box-title">{{ replyForm.parentReplyId === 0 ? '发表回复' : '回复评论' }}</h3>
        <editor v-model="replyForm.content" :min-height="150" />
        <div class="reply-submit">
          <el-button v-if="replyForm.parentReplyId !== 0" type="text" @click="cancelSubReply">取消回复他人</el-button>
          <el-button type="primary" :loading="replyLoading" @click="submitReply">提交回复</el-button>
        </div>
      </div>
    </el-card>
  </div>
</template>

<script>
import { getPost } from "@/api/forum/post";
import { listReply, addReply } from "@/api/forum/reply";
import { toggleLike } from "@/api/forum/like";
import Editor from "@/components/Editor";

export default {
  name: "ForumPostDetail",
  components: { Editor },
  data() {
    return {
      loading: true,
      replyLoading: false,
      postId: null,
      form: {},
      // 回复列表
      replyList: [],
      replyTotal: 0,
      replyQuery: {
        pageNum: 1,
        pageSize: 10,
        postId: null,
        status: "0"
      },
      // 回复提交表单
      replyForm: {
        postId: null,
        content: "",
        parentReplyId: 0
      }
    };
  },
  created() {
    const postId = this.$route.params && this.$route.params.postId;
    if (postId) {
      this.postId = postId;
      this.replyQuery.postId = postId;
      this.replyForm.postId = postId;
      this.getDetail();
      this.getReplyList();
    }
  },
  methods: {
    /** 获取帖子详情 */
    getDetail() {
      this.loading = true;
      getPost(this.postId).then(response => {
        this.form = response.data;
        this.loading = false;
      });
    },
    /** 获取回复列表 */
    getReplyList() {
      listReply(this.replyQuery).then(response => {
        this.replyList = response.rows;
        this.replyTotal = response.total;
      });
    },
    /** 帖子点赞逻辑 */
    handlePostLike() {
      const data = {
        postId: this.postId,
        targetId: this.postId,
        likeType: '0' // 0-帖子
      };
      toggleLike(data).then(res => {
        this.form.likeCount = (this.form.likeCount || 0) + res.data;
        this.$modal.msgSuccess(res.msg);
      });
    },
    /** 回复点赞逻辑 */
    handleReplyLike(reply) {
      const data = {
        postId: this.postId,
        targetId: reply.replyId,
        likeType: '1' // 1-回复
      };
      toggleLike(data).then(res => {
        reply.likeCount = (reply.likeCount || 0) + res.data;
        this.$modal.msgSuccess(res.msg);
      });
    },
    /** 提交回复 */
    submitReply() {
      if (!this.replyForm.content || this.replyForm.content.trim() === "") {
        this.$modal.msgError("请输入回复内容");
        return;
      }
      this.replyLoading = true;
      addReply(this.replyForm).then(response => {
        this.$modal.msgSuccess("回复成功");
        this.replyForm.content = "";
        this.replyForm.parentReplyId = 0;
        this.replyLoading = false;
        this.getReplyList();
        // 更新界面显示的回复总数
        this.form.commentCount++;
        this.replyTotal++;
      }).catch(() => {
        this.replyLoading = false;
      });
    },
    /** 准备回复某人的评论 */
    handleSubReply(reply) {
      this.replyForm.parentReplyId = reply.replyId;
      this.$modal.msgInfo("正在回复 " + reply.createBy + " 的评论");
      document.getElementById("reply-box-title").scrollIntoView({ behavior: "smooth" });
    },
    /** 取消回复他人，转为回复帖子 */
    cancelSubReply() {
      this.replyForm.parentReplyId = 0;
      this.$modal.msgInfo("已切换为回复楼主");
    },
    handleBack() {
      this.$router.back();
    }
  }
};
</script>

<style lang="scss" scoped>
.post-detail-card {
  max-width: 1000px;
  margin: 20px auto;
  padding: 20px;
}
.post-title {
  font-size: 28px;
  color: #303133;
  margin-bottom: 20px;
}
.post-meta {
  color: #909399;
  font-size: 14px;
  span {
    margin-right: 20px;
    i { margin-right: 5px; }
  }
}
.content-body {
  line-height: 1.8;
  font-size: 16px;
  color: #333;
  min-height: 300px;
  ::v-deep img {
    max-width: 100%;
    height: auto;
  }
}
.post-footer {
  text-align: center;
  margin: 30px 0;
  .el-button {
    margin: 0 10px;
    padding: 12px 25px;
  }
}
.reply-section {
  margin-top: 40px;
  .reply-count {
    font-size: 20px;
    border-left: 4px solid #409EFF;
    padding-left: 10px;
    margin-bottom: 30px;
  }
}
.reply-item {
  padding: 15px 0;
  .reply-user-info {
    display: flex;
    align-items: center;
    gap: 10px;
    font-size: 14px;
    margin-bottom: 10px;
    .reply-author { font-weight: bold; color: #303133; }
    .reply-floor { color: #909399; }
    .reply-time { color: #C0C4CC; font-size: 12px; }
  }
  .reply-content {
    margin: 10px 0;
    line-height: 1.6;
    color: #606266;
  }
  .reply-actions {
    font-size: 13px;
    text-align: right;
    .el-button { margin-left: 15px; }
  }
}
.reply-input-container {
  margin-top: 50px;
  background-color: #f9f9f9;
  padding: 25px;
  border-radius: 8px;
  border: 1px solid #eee;
  h3 { margin-bottom: 20px; color: #333; }
  .reply-submit {
    margin-top: 15px;
    text-align: right;
  }
}
.post-header {
  margin-bottom: 25px;
}

/* 悬赏条样式 */
.bounty-banner {
  background-color: #fffaf0;
  border: 1px dashed #e6a23c;
  border-radius: 8px;
  padding: 15px 20px;
  margin-bottom: 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;

  .bounty-status {
    display: flex;
    align-items: center;
    .gold-icon {
      font-size: 24px;
      color: #e6a23c;
      margin-right: 10px;
    }
    .bounty-text {
      font-size: 16px;
      color: #333;
      font-weight: bold;
      margin-right: 10px;
    }
    .bounty-amount {
      font-size: 24px;
      color: #e6a23c;
      font-weight: 800;
      margin-right: 5px;
    }
    .bounty-unit {
      color: #e6a23c;
      font-size: 14px;
    }
  }
  .bounty-tip {
    font-size: 13px;
    color: #909399;
  }
}

.meta-bounty {
  color: #e6a23c;
  font-weight: bold;
  i { margin-right: 3px; }
}
</style>
