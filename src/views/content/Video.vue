<template>
  <div class="video">
    <video-info :data="videoData" :key="videoSlave" />
    <Wait :show="show" :fail="isFail" color="#000" width="100%" height="400px" failColor="#000">
      <video-player :video="videoData.videoUrl" :cover="videoData.coverUrl"/>
    </Wait>
    <func-bar :comment="videoData.comment"/>
  </div>
</template>

<script lang="ts">
import { defineComponent, inject, onActivated, onMounted, ref } from 'vue';
import { ProcessInterface, ApiObject, UserInterface, ContextInterface } from "@/d.ts/plugin";
import useProcessControl from "@/hooks/useProcessControl";
import { Wait } from "@/components/general/popup";
import { VideoInfo, VideoPlayer, FuncBar } from "@/components/content/video";
import { codeConfig } from "@/config/program";
import { userCenterContext } from "@/config/site";
import videoConfig from "@/components/content/video/config";
import utils from "@/utils/helper";

export default defineComponent({
  name: "Video",
  components: { Wait, VideoInfo, VideoPlayer, FuncBar },
  setup() {
    const $process = inject<ProcessInterface>("$process")!;
    const $context = inject<ContextInterface>("$context")!;
    const $api = inject<ApiObject>("$api")!;
    const $user = inject<UserInterface>("$user")!;

    let show = ref(true);
    let isFail = ref(false);
    let videoData = ref<any>({});
    let videoSlave = ref(0);

    function initData() {
      show.value = true;
      isFail.value = false;
      let videoInfo = localStorage.getItem(videoConfig.videoStorageKey);
      if (videoInfo == null) {
        getVideoInfo();
      } else {
        // 从本地解析出未播放完的视频信息
        videoData.value = utils.decryptObj(videoInfo);
        refresh();
      }
    }

    async function getVideoInfo() {
      await $api.getVideoInfo().then(({ code, msg, data }) => {
        if (code == codeConfig.success) {
          videoData.value = data;
          // 将视频信息存入本地
          localStorage.setItem(videoConfig.videoStorageKey, utils.encryptionObj(data));
          refresh();
          return;
        } else {
          $process.tipShow.error(msg);
        }
        isFail.value = true;
      });
    }

    function refresh() {
      videoSlave.value++;
      show.value = false;
    }

    onMounted(() => {
      if (!$user.isLogin()) {
        location.href = `${userCenterContext.auth}?clientId=${$context.data.spaceClientId}&redirectUrl=${process.env.VITE_SITE_URL + userCenterContext.redirectUrl}`;
      }
      initData();
    })

    onActivated(() => {
      useProcessControl(true, false, true);
    })

    return {
      show,
      isFail,
      videoData,
      videoSlave
    };
  },
});
</script>

<style lang="scss" scoped>
@import "@/assets/scss/index.scss";

.video {
  width: 100%;
}
</style>