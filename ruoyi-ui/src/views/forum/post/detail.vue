<template>
  <div>
    <forum-banner/>
    <div class="app-container">
      <el-card v-loading="loading" class="post-detail-card">
        <div class="post-header">
          <h1 class="post-title">{{ form.title }}</h1>

          <div v-if="form.postType === '1'" class="bounty-banner" :style="form.acceptedAnswerId ? 'border-color: #67C23A; background-color: #f0f9eb;' : ''">
            <div class="bounty-status">
              <i class="el-icon-coin gold-icon" :style="form.acceptedAnswerId ? 'color: #67C23A;' : ''"></i>
              <span class="bounty-text" :style="form.acceptedAnswerId ? 'color: #67C23A;' : ''">
              {{ form.acceptedAnswerId ? '已解决' : '悬赏中' }}
            </span>
              <span class="bounty-amount" :style="form.acceptedAnswerId ? 'color: #67C23A;' : ''">
              {{ form.bountyCoins || 0 }}
            </span>
              <span class="bounty-unit" :style="form.acceptedAnswerId ? 'color: #67C23A;' : ''">金币</span>
            </div>
            <div class="bounty-tip">
              {{ form.acceptedAnswerId ? '该悬赏已结束，赏金已发放给最佳答案提供者' : '采纳最佳答案后，悬赏金币将发放给回答者' }}
            </div>
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
          <el-button type="primary" icon="el-icon-back" @click="handleBack">返回</el-button>
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

          <div
            v-for="(reply, index) in replyList"
            :key="reply.replyId"
            class="reply-item"
            :class="{ 'reply-item-accepted': reply.replyId === form.acceptedAnswerId }"
          >
            <div v-if="reply.replyId === form.acceptedAnswerId" class="accepted-badge">
              <i class="el-icon-success"></i> 最佳答案
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
                v-if="form.postType == '1' && !form.acceptedAnswerId && reply.isAuthor !== '1' && (form.userId == $store.state.user.id || form.userId == $store.state.user.userId || $store.state.user.name === 'admin')"
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
  </div>

</template>

<script>
import { getPost } from "@/api/forum/post";
import { listReply, addReply } from "@/api/forum/reply";
import { toggleLike } from "@/api/forum/like";
import Editor from "@/components/Editor";
import { acceptBountyAnswer } from "@/api/forum/bounty";
import ForumBanner from '@/components/Banner';

export default {
  name: "ForumPostDetail",
  components: { Editor, ForumBanner },
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
    handleAcceptAnswer(reply) {
      this.$confirm('确定要将该回复采纳为最佳答案吗？采纳后悬赏金币将自动发放给该用户，且操作不可撤销。', '系统提示', {
        confirmButtonText: '确定采纳',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const data = {
          postId: this.postId,
          replyId: reply.replyId,
          // 注意：这里需要确保你的 reply 对象里带有回复者的 userId 字段
          // 若你的字段名是别的（比如 authorId），请对应修改
          replyUserId: reply.userId
        };
        return acceptBountyAnswer(data);
      }).then(() => {
        this.$modal.msgSuccess("已成功采纳最佳答案！");
        // 刷新帖子数据（为了更新悬赏状态和采纳标签）
        this.getDetail();
        // 刷新回复列表（为了显示最佳答案的高亮标识）
        this.getReplyList();
      }).catch(() => {});
    },
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
      // 如果帖子数据存在且有板块ID，则跳转到对应板块页
      if (this.form && this.form.boardId) {
        this.$router.push({
          path: '/forum/post',
          query: { boardId: this.form.boardId }
        });
      } else {
        // 容错处理：如果没有板块ID，则返回论坛首页
        this.$router.push('/forum/index');
      }
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
/* 最佳答案勋章样式 */
.accepted-badge {
  display: inline-flex;
  align-items: center;
  background-color: #f0f9eb; /* 浅绿色背景 */
  border: 1px solid #67C23A; /* 成功绿边框 */
  color: #67C23A;            /* 字体颜色 */
  padding: 4px 12px;
  border-radius: 4px;
  font-size: 13px;
  font-weight: bold;
  margin-bottom: 15px;
  box-shadow: 0 2px 4px rgba(103, 194, 58, 0.1);

  i {
    margin-right: 5px;
    font-size: 16px;
    font-weight: bold;
  }
}

/* 进阶：当某条回复是最佳答案时，给整个回复项加一个淡绿色外框和背景 */
.reply-item-accepted {
  border: 2px solid #67C23A !important;
  background-color: #fafdf8;
  border-radius: 8px;
  padding: 20px !important;
  position: relative;
  transition: all 0.3s ease;
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
