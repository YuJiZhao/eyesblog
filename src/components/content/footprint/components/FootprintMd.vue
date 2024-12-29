<template>
    <div class="footprintMd">
        <md-editor v-model="content" :editor-id="id" preview-theme="vuepress" previewOnly showCodeRowNumber />
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
            id: "footprintId" + props.mdId
        };
    },
});
</script>

<style lang="scss" scoped>
:deep.footprintMd {
    .md-editor-content .md-editor-preview-wrapper {
        padding: 0;
    }
}
</style>