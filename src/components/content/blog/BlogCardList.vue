<template>
  <div class="blogCardList">
    <owner-card class="card" />
    <data-card class="card" :title="dataTitle" :api="dataApi" :cardConfig="dataCardConfig" />
    <blog-category-card class="card" />
    <blog-label-card class="card" />
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import { OwnerCard, DataCard } from "@/components/general/card";
import { BlogCategoryCard, BlogLabelCard } from "@/components/content/blog/components/cards";
import { dataCardConfig } from "./config";

export default defineComponent({
  components: { OwnerCard, DataCard, BlogCategoryCard, BlogLabelCard },
  setup() {
    return {
      dataTitle: "博客数据",
      dataApi: "getBlogListInfo",
      dataCardConfig
    };
  },
});
</script>

<style lang="scss" scoped>
.blogCardList {
  .card {
    margin-bottom: 20px;
  }
}
</style>