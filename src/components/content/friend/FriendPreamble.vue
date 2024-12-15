<template>
  <div class="friendPreamble">
      <md-editor 
        v-model="content"
        :editor-id="id"
        preview-theme="vuepress" 
        previewOnly 
        showCodeRowNumber
      />
  </div>
</template>

<script lang="ts">
import { defineComponent, onBeforeMount, ref, inject } from "vue";
import { ProcessInterface, ApiObject } from "@/d.ts/plugin";
import { codeConfig } from "@/config/program";
import MdEditor from 'md-editor-v3';
import 'md-editor-v3/lib/style.css';
import { contextConfig } from "@/config/program";

export default defineComponent({
  components: { MdEditor },
  setup() {
    const $api = inject<ApiObject>("$api")!;
    const $process = inject<ProcessInterface>("$process")!;

    let content = ref("");

    function getFriendPreamble() {
        $api.getContextItem([contextConfig.friendPreamble]).then(({code, msg, data}) => {
            if (code == codeConfig.success) {
                content.value = data.content;
            } else {
                $process.tipShow.error(msg);
            }
        });
    }
    
    onBeforeMount(() => {
      getFriendPreamble();
    })

    return {
      content,
      id: "friend"
    };
  },
});
</script>

<style lang="scss" scoped>
</style>