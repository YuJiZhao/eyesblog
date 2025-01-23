<template>
  <div class="musicPlayer">
    <div id="musicContainer"></div>
  </div>
</template>

<script lang="ts">
import { defineComponent, reactive, onBeforeUnmount, onMounted } from "vue";
import APlayer from 'APlayer';
import 'APlayer/dist/APlayer.min.css';
import { musicContext, aplayerConfig } from "./config";

export default defineComponent({
  props: ["data"],
  setup(props) {
    const state = reactive<any>({
      instance: null
    });

    function initPlayer() {
      // 音乐数据格式转换
      let audioList = props.data.map((v: any) => {
        return {
          name: v.title,
          artist: v.author,
          url: v.musicUrl,
          cover: v.coverUrl,
          lrc: musicContext.lrcApiUrl + v.id
        }
      });
      // 实例化播放器
      state.instance = new APlayer({
        container: document.getElementById("musicContainer"),
        ...aplayerConfig,
        audio: audioList
      });
    }

    onMounted(() => {
      initPlayer();
    });

    // 销毁
    onBeforeUnmount(() => {
      state.instance.destroy()
    });
  },
});
</script>

<style lang="scss" scoped>
.musicPlayer {
  #musicContainer {
    width: 80vw;
    position: fixed;
    top: 15%;
    left: calc(50% - 40vw);
  }
}
</style>