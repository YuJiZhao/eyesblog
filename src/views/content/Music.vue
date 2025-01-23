<template>
  <div class="music">
    <Wait :show="show" :fail="isFail" height="400px">
      <music-player :data="musicData" :key="musicSlave"/>
    </Wait>
  </div>
</template>

<script lang="ts">
import { defineComponent, inject, onActivated, onMounted, ref } from 'vue';
import { ProcessInterface, ContextInterface, ApiObject, UserInterface } from "@/d.ts/plugin";
import { MusicPlayer } from "@/components/content/music";
import useProcessControl from "@/hooks/useProcessControl";
import { codeConfig } from "@/config/program";
import { Wait } from "@/components/general/popup";
import { userCenterContext } from "@/config/site";

export default defineComponent({
  name: "Music",
  components: { MusicPlayer, Wait },
  setup() {
    const $process = inject<ProcessInterface>("$process")!;
    const $context = inject<ContextInterface>("$context")!;
    const $api = inject<ApiObject>("$api")!;
    const $user = inject<UserInterface>("$user")!;

    let show = ref(true);
    let isFail = ref(false);
    let musicData = ref([]);
    let musicSlave = ref(0);

    async function initData() {
      show.value = true;
      isFail.value = false;
      await $api.getMusicList().then(({code, msg, data}) => {
        if(code == codeConfig.success) {
          show.value = false;
          musicData.value = data;
          musicSlave.value++;
        } else {
          $process.tipShow.error(msg);
          isFail.value = true;
        }
      })
    }

    onMounted(() => {
      if (!$user.isLogin()) {
        location.href = `${userCenterContext.auth}?clientId=${$context.data.spaceClientId}&redirectUrl=${process.env.VITE_SITE_URL + userCenterContext.redirectUrl}`;
      }
      initData();
    })

    onActivated(() => {
      useProcessControl(true, false, false);
    })

    return {
      show,
      isFail,
      musicData,
      musicSlave
    };
  },
});
</script>

<style lang="scss" scoped>
.music {
  width: 100%;
}
</style>