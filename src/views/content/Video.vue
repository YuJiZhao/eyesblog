<template>
  <div class="video">
    <div class="playBox">
      <div class="title">{{title}}</div>
      <Wait :show="show" :fail="isFail" color="#000" width="100%" height="400px" failColor="#000">
        <video-player />
      </Wait>
      <func-bar />
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, inject, onActivated, onMounted, ref, watch } from 'vue';
import { ProcessInterface, ApiObject, UserInterface } from "@/d.ts/plugin";
import useProcessControl from "@/hooks/useProcessControl";
import { VideoPlayer, FuncBar } from "@/components/content/video";
import { codeConfig } from "@/config/program";
import { videoContext } from "@/components/content/video/businessTs/videoContext";
import videoProcess from "@/components/content/video/businessTs/videoProcess";
import { Wait } from "@/components/general/popup";
import { videoSideCards } from "@/components/content/video/config";
import { userCenterContext, siteContext } from "@/config/site";

export default defineComponent({
  name: "Video",
  components: { VideoPlayer, Wait, FuncBar },
  setup() {
    const $process = inject<ProcessInterface>("$process")!;
    const $api = inject<ApiObject>("$api")!;
    const $user = inject<UserInterface>("$user")!;

    let title = ref("");
    let show = ref(true);
    let isFail = ref(false);

    async function initData() {
      videoProcess.cardInitLoad.value = true;
      show.value = true;
      isFail.value = false;
      await $api.getVideoInfo().then(({code, msg, data}) => {
        if(code == codeConfig.success) {
          title.value = data.title;
          videoContext.init(data);
          videoProcess.cardInitLoad.value = false;
          show.value = false;
          return;
        } else {
          $process.tipShow.error(msg);
        }
        isFail.value = true;
        videoProcess.cardInitFail.value = true;
      })
    }

    watch(
      () => videoProcess.videoEndSentry.value,
      () => initData()
    )

    onMounted(() => {
      if (!$user.isLogin()) {
        location.href = `${userCenterContext.auth}?clientId=${siteContext.clientId}&redirectUrl=${process.env.VITE_SITE_URL + userCenterContext.redirectUrl}`;
      }
      initData();
    })

    onActivated(() => {
      useProcessControl(true, videoSideCards, false);
    })

    return {
      title,
      show,
      isFail,
    };
  },
});
</script>

<style lang="scss" scoped>
@import "@/assets/scss/index.scss";

.video {
  width: 100%;
  padding: 0 !important;
  .playBox {
    width: 100%;
    max-width: 800px;
    margin: 0 auto;
    .title {
      font-size: 20px;
      min-height: 50px;
      line-height: 50px;
      color: $title;
      white-space:normal; 
      word-break:break-all;
      overflow:hidden;
    }
  }
}
</style>