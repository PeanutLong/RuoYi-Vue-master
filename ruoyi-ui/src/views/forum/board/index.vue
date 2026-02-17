<template>
  <div class="app-container">
    <div class="board-header">
      <div class="header-content">
        <h1>探索社区板块</h1>
        <p>找到你感兴趣的圈子，开始交流与分享</p>
        <div class="search-bar">
          <el-input
            v-model="queryParams.boardName"
            placeholder="搜索你感兴趣的板块..."
            prefix-icon="el-icon-search"
            clearable
            @keyup.enter.native="handleQuery"
            class="search-input"
          >
            <el-button slot="append" type="primary" @click="handleQuery">搜索</el-button>
          </el-input>
        </div>
      </div>
    </div>

    <el-row :gutter="25" v-loading="loading" class="board-grid">
      <el-col
        :xs="24" :sm="12" :md="8" :lg="6"
        v-for="board in boardList"
        :key="board.boardId"
        class="mb20"
      >
        <el-card class="board-user-card" shadow="hover" @click.native="goToBoardDetail(board.boardId)">
          <div class="board-cover" :style="{ background: getRandomGradient(board.boardId) }">
            <i class="el-icon-collection-tag"></i>
          </div>

          <div class="board-body">
            <h3 class="board-name">{{ board.boardName }}</h3>
            <p class="board-desc">{{ board.description || '这个板块暂时还没有介绍哦~' }}</p>

            <div class="board-footer">
              <span class="tag-status" v-if="board.status === '0'">
                <i class="el-icon-circle-check"></i> 活跃中
              </span>
              <el-button type="text" class="enter-btn">进入浏览 <i class="el-icon-arrow-right"></i></el-button>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-empty v-if="!loading && boardList.length === 0" description="没有找到匹配的板块"></el-empty>

    <div class="pagination-container">
      <pagination
        v-show="total > 0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        layout="prev, pager, next"
        @pagination="getList"
      />
    </div>
  </div>
</template>

<script>
import { listBoard } from "@/api/forum/board";

export default {
  name: "ForumBoardUser",
  data() {
    return {
      loading: true,
      total: 0,
      boardList: [],
      queryParams: {
        pageNum: 1,
        pageSize: 12, // 用户页建议一页多显示几个，4的倍数
        boardName: undefined,
        status: "0" // 给用户看只查“正常”状态的
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询板块列表 */
    getList() {
      this.loading = true;
      listBoard(this.queryParams).then(response => {
        this.boardList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 搜索 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 跳转板块详情（展示该板块下的所有帖子） */
    goToBoardDetail(boardId) {
      this.$router.push({
        path: '/forum/post', // 跳转到帖子列表页，带上板块参数
        query: { boardId: boardId }
      });
    },
    /** 生成随机渐变背景，美化卡片 */
    getRandomGradient(id) {
      const colors = [
        ['#4facfe', '#00f2fe'],
        ['#f093fb', '#f5576c'],
        ['#5eeff9', '#48c6ef'],
        ['#f6d365', '#fda085'],
        ['#84fab0', '#8fd3f4']
      ];
      const index = id % colors.length;
      return `linear-gradient(135deg, ${colors[index][0]} 0%, ${colors[index][1]} 100%)`;
    }
  }
};
</script>

<style lang="scss" scoped>
.app-container {
  padding: 0;
  background-color: #f5f7f9;
  min-height: calc(100vh - 84px);
}

// 顶部背景区域
.board-header {
  background: linear-gradient(to right, #243b55, #141e30);
  padding: 60px 20px;
  text-align: center;
  color: #fff;
  margin-bottom: 40px;

  h1 { font-size: 32px; font-weight: 600; margin-bottom: 15px; }
  p { font-size: 16px; opacity: 0.8; margin-bottom: 30px; }

  .search-bar {
    max-width: 600px;
    margin: 0 auto;
    ::v-deep .el-input__inner {
      height: 50px;
      border-radius: 25px 0 0 25px;
    }
    ::v-deep .el-input-group__append {
      border-radius: 0 25px 25px 0;
      background-color: #409EFF;
      color: #fff;
      border: none;
      padding: 0 30px;
    }
  }
}

.board-grid {
  padding: 0 30px;
  max-width: 1400px;
  margin: 0 auto;
}

.board-user-card {
  border: none;
  border-radius: 12px;
  cursor: pointer;
  transition: all 0.3s;
  overflow: hidden;

  &:hover {
    transform: translateY(-8px);
    box-shadow: 0 12px 24px rgba(0,0,0,0.1) !important;
  }

  .board-cover {
    height: 100px;
    display: flex;
    justify-content: center;
    align-items: center;
    i { font-size: 40px; color: rgba(255,255,255,0.8); }
  }

  .board-body {
    padding: 20px;
    .board-name {
      margin: 0 0 10px 0;
      font-size: 18px;
      color: #333;
    }
    .board-desc {
      font-size: 14px;
      color: #777;
      height: 40px;
      line-height: 20px;
      display: -webkit-box;
      -webkit-line-clamp: 2;
      -webkit-box-orient: vertical;
      overflow: hidden;
      margin-bottom: 15px;
    }
  }

  .board-footer {
    display: flex;
    justify-content: space-between;
    align-items: center;
    border-top: 1px solid #f0f0f0;
    padding-top: 15px;

    .tag-status {
      font-size: 12px;
      color: #67C23A;
    }
    .enter-btn {
      font-weight: bold;
      padding: 0;
    }
  }
}

.pagination-container {
  padding: 40px 0;
  display: flex;
  justify-content: center;
}
</style>
