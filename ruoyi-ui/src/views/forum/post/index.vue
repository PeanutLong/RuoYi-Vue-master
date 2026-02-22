<template>
  <div>
    <forum-banner/>
    <div class="app-container">
      <el-row :gutter="20">
        <el-col :span="18" :xs="24">
          <el-card shadow="never" class="list-container">
            <div slot="header" class="list-header">
              <el-tabs v-model="activeTab" @tab-click="handleTabChange">
                <el-tab-pane label="全部" name="all"></el-tab-pane>
                <el-tab-pane label="精华" name="essence"></el-tab-pane>
              </el-tabs>
              <div class="header-search">
                <el-input
                  v-model="queryParams.title"
                  placeholder="搜索本板块帖子"
                  size="small"
                  prefix-icon="el-icon-search"
                  clearable
                  @keyup.enter.native="handleQuery"
                />
              </div>
            </div>

            <div v-loading="loading">
              <div v-for="item in postList" :key="item.postId" class="post-item">
                <div class="post-main">
                  <div class="post-title-row">
                    <el-tag v-if="item.isTop === '1'" size="mini" type="danger" effect="dark">置顶</el-tag>
                    <el-tag v-if="item.isEssence === '1'" size="mini" type="warning" effect="dark">精华</el-tag>

                    <template v-if="item.postType === '1'">
                    <span class="bounty-wrapper">
                      <i class="el-icon-coin gold-icon"></i>
                      <span class="bounty-num">{{ item.bountyCoins || 0 }}</span>
                    </span>
                    </template>

                    <span v-else class="post-type">[讨论]</span>

                    <router-link :to="'/forum/post/detail/' + item.postId" class="post-title">
                      {{ item.title }}
                    </router-link>
                  </div>
                  <div class="post-info">
                    <span class="author"><i class="el-icon-user"></i> {{ item.createBy }}</span>
                    <span class="time"><i class="el-icon-time"></i> {{ parseTime(item.createTime, '{y}-{m}-{d}') }}</span>
                  </div>
                </div>
                <div class="post-stats">
                  <div class="stat-box">
                    <span class="num">{{ item.commentCount || 0 }}</span>
                    <span class="lab">回复</span>
                  </div>
                  <div class="stat-box">
                    <span class="num">{{ item.viewCount || 0 }}</span>
                    <span class="lab">浏览</span>
                  </div>
                </div>
              </div>

              <el-empty v-if="postList.length === 0" description="该板块暂时没有帖子"></el-empty>
            </div>

            <pagination
              v-show="total > 0"
              :total="total"
              :page.sync="queryParams.pageNum"
              :limit.sync="queryParams.pageSize"
              @pagination="getList"
            />
          </el-card>
        </el-col>

        <el-col :span="6" class="hidden-xs-only">
          <el-card shadow="never" class="side-card">
            <el-button type="primary" icon="el-icon-edit" class="publish-btn" @click="handlePublish">
              发布新帖
            </el-button>
            <div class="board-notice">
              <h4>版规说明</h4>
              <p>1. 请自觉遵守国家法律法规。</p>
              <p>2. 严禁发布任何广告、色情或暴力内容。</p>
              <p>3. 提问前请先搜索，保持社区氛围。</p>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </div>
  </div>

</template>

<script>
import { listPost } from "@/api/forum/post";
import ForumBanner from "@/components/Banner/index.vue";

export default {
  name: "ForumPostList",
  components: {ForumBanner},
  data() {
    return {
      loading: true,
      total: 0,
      postList: [],
      activeTab: "all",
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        boardId: undefined,
        title: undefined,
        postType: undefined,
        isEssence: undefined,
        status: "0"
      }
    };
  },
  created() {
    const boardId = this.$route.query.boardId;
    if (boardId) {
      this.queryParams.boardId = boardId;
    }
    this.getList();
  },
  methods: {
    /** 查询帖子列表 */
    getList() {
      this.loading = true;
      listPost(this.queryParams).then(response => {
        this.postList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 搜索 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 标签切换过滤 */
    handleTabChange(tab) {
      this.queryParams.isEssence = tab.name === "essence" ? "1" : undefined;
      this.queryParams.postType = undefined;
      this.handleQuery();
    },
    /** 发布帖子 */
    handlePublish() {
      this.$router.push({
        path: '/forum/post/create',
        query: { boardId: this.queryParams.boardId }
      });
    }
  }
};
</script>

<style lang="scss" scoped>
.app-container {
  background-color: #f4f7f6;
  min-height: calc(100vh - 84px);
}

.list-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  .header-search { width: 200px; }
}

.post-item {
  display: flex;
  padding: 15px 10px;
  border-bottom: 1px solid #f2f6fc;
  transition: background 0.2s;
  &:hover { background-color: #fafafa; }
}

.post-main {
  flex: 1;
  .post-title-row {
    margin-bottom: 8px;
    display: flex;
    align-items: center; // 确保标签和文字对齐

    .el-tag { margin-right: 8px; }

    .post-type { color: #909399; margin-right: 8px; font-size: 14px; }

    /* 悬赏金额样式 */
    .bounty-wrapper {
      margin-right: 10px;
      padding: 0 6px;
      height: 20px;
      line-height: 18px;
      border: 1px solid #ffe3b3;
      background-color: #fff9f0;
      border-radius: 4px;
      display: inline-flex;
      align-items: center;

      .gold-icon {
        color: #e6a23c;
        font-weight: bold;
        margin-right: 4px;
      }
      .bounty-num {
        color: #e6a23c;
        font-size: 13px;
        font-weight: bold;
      }
    }

    .post-title {
      font-size: 16px;
      color: #303133;
      font-weight: 500;
      text-decoration: none;
      &:hover { color: #409EFF; }
    }
  }
  .post-info {
    font-size: 13px;
    color: #999;
    span { margin-right: 15px; }
  }
}

.post-stats {
  display: flex;
  gap: 20px;
  align-items: center;
  .stat-box {
    text-align: center;
    min-width: 40px;
    .num { display: block; font-size: 16px; color: #666; font-weight: bold; }
    .lab { font-size: 12px; color: #999; }
  }
}

.side-card {
  .publish-btn { width: 100%; margin-bottom: 20px; font-size: 16px; padding: 12px; }
  .board-notice {
    h4 { border-left: 4px solid #409EFF; padding-left: 10px; margin-bottom: 15px; }
    p { font-size: 13px; color: #666; line-height: 2; margin: 0; }
  }
}
</style>
