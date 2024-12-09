<template>
  <div class="music">
    <Wait :show="show" :fail="isFail" height="400px">
      <music-player />
    </Wait>
  </div>
</template>

<script lang="ts">
import { defineComponent, inject, onActivated, onMounted, ref, watch } from 'vue';
import { ProcessInterface, ApiObject, UserInterface } from "@/d.ts/plugin";
import { MusicPlayer } from "@/components/content/music";
import useProcessControl from "@/hooks/useProcessControl";
import { musicContext } from "@/components/content/music/businessTs/musicContext";
import { codeConfig } from "@/config/program";
import { Wait } from "@/components/general/popup";
import musicProcess from "@/components/content/music/businessTs/musicProcess";
import { userCenterContext, siteContext } from "@/config/site";

export default defineComponent({
  name: "Music",
  components: { MusicPlayer, Wait },
  setup() {
    const $process = inject<ProcessInterface>("$process")!;
    const $api = inject<ApiObject>("$api")!;
    const $user = inject<UserInterface>("$user")!;

    let show = ref(true);
    let isFail = ref(false);

    async function initData() {
      show.value = true;
      isFail.value = false;
      await $api.getMusicInfo().then(({code, msg, data}) => {
        if(code == codeConfig.success) {
          musicContext.init(data);
          musicProcess.initNotice();
          show.value = false;
        } else {
          $process.tipShow.error(msg);
          isFail.value = true;
        }
      })
    }

    watch(
      () => musicProcess.musicEndSentry.value,
      () => initData()
    )

    onMounted(() => {
      if (!$user.isLogin()) {
        location.href = `${userCenterContext.auth}?clientId=${siteContext.clientId}&redirectUrl=${process.env.VITE_SITE_URL + userCenterContext.redirectUrl}`;
      }
      initData();
    })

    onActivated(() => {
      useProcessControl(true, false, false);
    })

    return {
      show,
      isFail,
    };
  },
});
</script>

<style lang="scss" scoped>
.music {
  width: 100%;
}
</style>