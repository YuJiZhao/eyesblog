<template>
  <div class="jokeList">
    <div class="jokeItem" v-for="item in jokeListData" :key="item" @click.stop="handlePreview(item)">
      <p>{{item.category}}</p>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, inject } from "vue";
import { ApiObject } from "@/d.ts/plugin";
import { v3ImgPreviewFn } from "v3-img-preview";
import { siteConfig } from "@/config/program";

export default defineComponent({
  props: ["jokeListData"],
  setup(props) {
    const $api = inject<ApiObject>("$api")!;

    function handlePreview(item: any) {
      v3ImgPreviewFn({
        images: <string[]>item.urlList,
        index: 0
      });

      // 添加埋点
      $api.addTrackPoint({
        browserId: localStorage.getItem(siteConfig.browserId),
        sessionId: localStorage.getItem(siteConfig.sessionId),
        path: location.href,
        title: "clickJoke",
        content: `id-${item.id};category-${item.category}`,
      });
    }

    return {
      jokeListData: props.jokeListData,
      handlePreview
    };
  },
});
</script>

<style lang="scss" scoped>
@import "@/assets/scss/index.scss";

.jokeList {
  width: 100%;
  min-width: 335px;
  margin: 0 auto;
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
  .jokeItem {
    width: 130px;
    height: 130px;
    background-image: radial-gradient(#333, #888);
    margin: 0 10px 20px 10px;
    cursor: pointer;
    p {
      color: #fff;
      text-align: center;
      line-height: 130px;
    }
  }
}
</style>