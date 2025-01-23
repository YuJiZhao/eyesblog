<template>
  <div class="funcBar">
    <icon-btn class="btn" 
      v-for="item in btnList" :key="item.word" 
      :icon="item.icon" :word="item.word" 
      @click="item.clickFunc"
    />
  </div>
  <base-dialog title="站长说" :close="false" @close="close" v-if="showComment">
  {{ props.comment }}
  </base-dialog>
</template>

<script lang="ts">
import { defineComponent, inject, ref } from "vue";
import { ProcessInterface } from "@/d.ts/plugin";
import IconBtn from "@/components/general/button/IconBtn.vue";
import resource from "@/config/resource";
import config from "./config";
import { BaseDialog } from "@/components/general/popup/dialogComponent";

export default defineComponent({
  components: { IconBtn, BaseDialog },
  props: ["comment"],
  setup(props) {
    const $process = inject<ProcessInterface>("$process")!;

    let showComment = ref(false);
    let funcObject: any = {
      switchVideo: () => {
        let info = localStorage.getItem(config.videoStorageKey);
        if (info == null) {
          location.reload();
        } else {
          $process.tipShow.warn("视频未完播，无法切换");
        }
      },
      viewComment: () => {
        showComment.value = true;
      }
    }

    let btnList = [
      {
        icon: resource.switch,
        word: "切换视频",
        clickFunc: funcObject.switchVideo
      },
      {
        icon: resource.comment,
        word: "站长说",
        clickFunc: funcObject.viewComment
      }
    ];

    function close() {
      showComment.value = false;
    }

    return {
      props,
      showComment,
      btnList,
      close
    };
  },
});
</script>

<style lang="scss" scoped>
@import "@/assets/scss/index.scss";

.funcBar {
  margin: 20px 0;
  display: flex;
  justify-content: right;

  .btn {
    margin: 0 3px 0 15px;
  }
}
</style>