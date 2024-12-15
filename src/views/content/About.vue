<template>
  <div class="about">
    <md-editor 
      v-model="content" 
      :editor-id="editorId"
      preview-theme="vuepress" 
      previewOnly 
      showCodeRowNumber
      no-katex
    />
  </div>
</template>

<script lang="ts">
import { defineComponent, onActivated, ref, inject } from "vue";
import MdEditor from 'md-editor-v3';
import 'md-editor-v3/lib/style.css';
import useProcessControl from "@/hooks/useProcessControl";
import { ApiObject, ProcessInterface } from "@/d.ts/plugin";
import { codeConfig, contextConfig } from "@/config/program";

export default defineComponent({
  name: "About",
  components: { MdEditor },
  setup() {
    const $api = inject<ApiObject>("$api")!;
    const $process = inject<ProcessInterface>("$process")!;

    let content = ref("");

    async function getAboutContent() {
      $api.getContextItem([contextConfig.about]).then(({code, msg, data}) => {
        if (code == codeConfig.success) {
          content.value = data.content;
        } else {
          $process.tipShow.error(msg);
        }
      });
    }
    
    onActivated(() => {
      useProcessControl(true, false, false);
    });

    // created生命周期执行
    getAboutContent();

    return {
      content,
      editorId: "about",
    };
  },
});
</script>

<style lang="scss" scoped>
.about {
  .aboutComment {
    margin-top: 20px;
  }
}
</style>