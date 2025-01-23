<template>
    <div class="videoPlayer">
      <div id="videoContainer"></div>
    </div>
</template>

<script lang="ts">
import { defineComponent, onMounted } from "vue";
import config from "./config";
import Player from "xgplayer/dist/core_player";
import fullscreen from "xgplayer/dist/controls/fullscreen";
import loading from "xgplayer/dist/controls/loading";
import play from "xgplayer/dist/controls/play";
import poster from "xgplayer/dist/controls/poster";
import usePageHidden from "@/hooks/usePageHidden";

export default defineComponent({
  props: ["video", "cover"],
  setup(props) {
    let player: any;

    function initPlayer() {
      player = new Player({
        id: "videoContainer",
        url: props.video,
        poster: props.cover,
        fluid: true,
        controls: false,
        controlPlugins: [ fullscreen, loading, play, poster ],
        errorTips: "视频加载失败"
      });

      // 若不在当前页，则暂停播放
      usePageHidden.addEvent(
        () => {/** empty */}, () => {
          player.pause();
        }
      );

      // 若视频播放完毕，则清除缓存
      player.on('ended', () => {
        localStorage.removeItem(config.videoStorageKey);
      });
    }

    onMounted(() => {
      initPlayer();
    });

    return {
    };
  },
});
</script>

<style lang="scss" scoped>
.videoPlayer {
  width: 100%;
  #videoContainer {
    width: 100%;
  }
}
</style>