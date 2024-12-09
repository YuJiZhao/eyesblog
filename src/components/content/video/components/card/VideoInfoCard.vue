<template>
  <standard-card title="视频数据" :icon="icon">
    <Wait :show="show" :fail="isFail" height="100px" @callback="initData">
      <div class="infoCard">
        <ul>
          <li v-for="item in info" :key="item.index">
            <div>{{item.title}}</div>
            <div>{{item.value}}</div>
          </li>
        </ul>
      </div>
    </Wait>
  </standard-card>
</template>

<script lang="ts">
import { defineComponent, ref } from "vue";
import Resource from "@/config/resource";
import StandardCard from "@/components/general/card/components/StandardCard.vue";
import { Wait } from "@/components/general/popup";
import videoProcess from "@/components/content/video/businessTs/videoProcess";
import { videoContext } from "@/components/content/video/businessTs/videoContext";

export default defineComponent({
  components: { StandardCard, Wait },
  setup() {
    let info = ref<Array<infoType>>([]);
    const indexList = ["views", "createTime"];
    const titleList = ["播放量", "发布时间"];
    
    // 构建数据结构
    type infoType = {
      index: string;
      title: string;
      value: string | number;
    }
    function buildInfoStructure(index: Array<string>, title: Array<string>, value: Array<string | number>): Array<infoType> {
      let info: Array<infoType> = [];
      index.forEach((v, i) => {
        info.push({
          index: index[i],
          title: title[i],
          value: value[i]
        });
      })
      return info;
    }

    // 初始化数据
    function initData() {
      info.value = buildInfoStructure(indexList, titleList, [
        videoContext.data.views!,
        videoContext.data.createTime!
      ]);
    }

    return {
      info,
      icon: Resource.data,
      show: videoProcess.cardInitLoad,
      isFail: videoProcess.cardInitFail,
      initData
    };
  },
});
</script>

<style lang="scss" scoped>
@import "@/assets/scss/index.scss";

.infoCard {
  ul > li {
    height: 25px;
    line-height: 25px;
    color: $normal;
    display: flex;
    justify-content: space-between;
  }
}
</style>