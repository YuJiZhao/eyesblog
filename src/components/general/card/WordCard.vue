<template>
    <div class="wordCard">
        <div class="title">站长说</div>
        <md-editor class="md" v-model="word" editor-id="id" preview-theme="vuepress" previewOnly showCodeRowNumber />
    </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import MdEditor from 'md-editor-v3';
import 'md-editor-v3/lib/style.css';

export default defineComponent({
    components: { MdEditor },
    props: ["word"],
    setup(props) {
        return {
            word: props.word,
        };
    },
});
</script>

<style lang="scss" scoped>
@import "@/assets/scss/index.scss";

.wordCard {
    min-height: 100px;
    padding: 10px;
    box-shadow: 0 0 3px rgba($color: $black, $alpha: 0.8);
    -webkit-box-shadow: 0 0 3px rgba($color: $black, $alpha: 0.8);
    -moz-box-shadow: 0 0 3px rgba($color: $black, $alpha: 0.8);
    color: $normal;

    .title {
        height: 30px;
        line-height: 30px;
        text-align: center;
        font-size: 20px;
    }
}

:deep.md {
    .md-editor-content .md-editor-preview-wrapper {
        padding: 0;
    }
}
</style>