<template>
  <div class="shuoMd">
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
import { defineComponent } from "vue";
import MdEditor from 'md-editor-v3';
import 'md-editor-v3/lib/style.css';

export default defineComponent({
  components: { MdEditor },
  props: ["mdId", "content"],
  setup(props) {
    return {
      content: props.content,
      id: "shuoId" + props.mdId
    };
  },
});
</script>

<style lang="scss" scoped>
:deep.shuoMd {
  .md-editor-content .md-editor-preview-wrapper {
    padding: 0;
  }
}
</style>