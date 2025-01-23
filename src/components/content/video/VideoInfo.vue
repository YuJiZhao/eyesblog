<template>
    <div class="videoInfo">
        <div class="title">{{ title }}</div>
        <div class="info">
            <div class="author">{{ author }}</div>
            <div class="originalUrl"><a :href="originalUrl" target="_blank">原地址</a></div>
            <div class="createTime">{{ createTime }}</div>
        </div>
    </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";

export default defineComponent({
    props: ["data"],
    setup(props) {

        return {
            title: props.data.title,
            author: props.data.author,
            originalUrl: props.data.originalUrl,
            createTime: props.data.createTime
        };
    },
});
</script>

<style lang="scss" scoped>
@import "@/assets/scss/index.scss";

.videoInfo {
    .title {
        font-size: 24px;
        line-height: 30px;
        color: $title;
        margin: 10px 0;
    }

    .info {
        display: flex;
        color: $normal;
        line-height: 25px;
        margin: 10px 0;

        div {
            margin-right: 20px;
        }

        a {
            color: $normal;
        }
    }
}
</style>