<template>
  <div class="rewardBtn" @click="openReward">
    <img class="icon" :src="rewordIcon" />
    <div class="reward">打赏</div>
  </div>
</template>

<script lang="ts">
import { defineComponent, inject } from "vue";
import { ProcessInterface, ApiObject } from "@/d.ts/plugin";
import resource from "@/config/resource";
import { siteConfig } from "@/config/program";
import { Dialogs } from "@/constant";

export default defineComponent({
  components: {},
  setup() {
    const $process = inject<ProcessInterface>("$process")!;
    const $api = inject<ApiObject>("$api")!;

    function openReward() {
      // 添加埋点
      $api.addTrackPoint({
        browserId: localStorage.getItem(siteConfig.browserId),
        sessionId: localStorage.getItem(siteConfig.sessionId),
        path: location.href,
        title: "reward",
        content: "",
      });
      $process.dialogShow(Dialogs.Reward);
    }

    return {
      rewordIcon: resource.reward,
      openReward,
    };
  },
});
</script>

<style lang="scss" scoped>
@import "@/assets/scss/index.scss";

.rewardBtn {
  width: 100px;
  height: 40px;
  border-radius: 5px;
  box-shadow: 0 0 5px rgba($color: $black, $alpha: 0.7);
  -webkit-box-shadow: 0 0 5px rgba($color: $black, $alpha: 0.7);
  -moz-box-shadow: 0 0 5px rgba($color: $black, $alpha: 0.7);
  margin: 30px auto;
  color: $normal;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  .icon {
    display: block;
    width: 20px;
    height: 20px;
  }
}
</style>