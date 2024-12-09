<template>
  <div class="funcBar">
      <div v-for="item in videoFuncBar" :key="item.name" @click="funcObject[item.clickFunc]">
        <Wait class="btn" :class="item.name" :show="show" :fail="isFail" size="20px" failSize="30px">
          <img :src="item.icon" />
          <div class="word">{{(typeof item.word == 'string') ? item.word : item.word[isChangeClearWord ? 1 : 0]}}</div>
        </Wait>
      </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref, inject, watch, nextTick, onActivated, onDeactivated, WatchStopHandle } from "vue";
import { WindowInterface } from "@/d.ts/plugin";
import videoProcess from "@/components/content/video/businessTs/videoProcess";
import { videoFuncBar } from "@/components/content/video/config";
import useProcessControl from "@/hooks/useProcessControl";
import { Wait } from "@/components/general/popup";
import { videoSideCards } from "@/components/content/video/config";
import { siteConfig } from "@/config/program";

export default defineComponent({
  components: { Wait },
  setup() {
    const $window = inject<WindowInterface>("$window")!;
    let isShowSideBar = true;
    let isChangeClearWord = ref(false);
    let watchSwatch: WatchStopHandle;
    let changeSwatch: WatchStopHandle;

    let funcObject: any = {
      changeVideo: () => {
        if(!videoProcess.videoVariable.value) return;
        videoProcess.doChangeVideo();
        videoProcess.videoVariable.value = false;
      },
      doFullScreen: () => {
        videoProcess.doFullScreen();
      },
      doClear: () => {
        if(document.querySelector(".clearBtn")!.classList.contains("btn_disabled")) return;
        isShowSideBar = !isShowSideBar;
        isChangeClearWord.value = !isChangeClearWord.value;
        useProcessControl(true, isShowSideBar ? videoSideCards : false, false);
      },
    }

    function btnControl(selector: string, flag: boolean) {
      nextTick(() => {
        if(flag) {
          document.querySelector(selector)!.classList.add("btn_disabled");
        } else {
          document.querySelector(selector)!.classList.remove("btn_disabled")
        }
      })
    }

    onActivated(() => {
      watchSwatch = watch(
        () => $window.width.value,
        (value) => { btnControl(".clearBtn", value < siteConfig.mpThreshold); },
        { immediate: true }
      );

      changeSwatch = watch(
        () => videoProcess.videoVariable.value,
        (value) => { btnControl(".changeBtn", !value); },
        { immediate: true }
      );
    })

    onDeactivated(() => {
      watchSwatch();
      changeSwatch();
    })

    return {
      show: videoProcess.cardInitLoad,
      isFail: videoProcess.cardInitFail,
      funcObject,
      videoFuncBar,
      isChangeClearWord,
    };
  },
});
</script>

<style lang="scss" scoped>
@import "@/assets/scss/index.scss";

.funcBar {
  display: flex;
  flex-wrap: wrap;
  justify-content: start;
  .btn {
    box-shadow: 0 0 3px rgba($color: $black, $alpha: 0.8);
    -webkit-box-shadow: 0 0 3px rgba($color: $black, $alpha: 0.8);
    -moz-box-shadow: 0 0 3px rgba($color: $black, $alpha: 0.8);
    display: flex;
    justify-content: center;
    align-items: center;
    width: 100px;
    height: 40px;
    margin: 20px 20px 10px 0;
    cursor: pointer;
    img {
      display: block;
      width: 25px;
      height: 25px;
      margin-right: 5px;
    }
    .word {
      font-size: 15px;
      text-align: center;
    }
  }
}

.btn_disabled {
  cursor: default !important;
  opacity: 0.6;
}
</style>