<template>
    <div class="footprintItem">
        <div class="content">
            <common-md :content="props.content" :mdId="props.id" />
        </div>
        <div class="foot">
            <div class="type">{{ props.type }}</div>
            <div class="time">{{ props.timeFrom }} - {{ props.timeTo }}</div>
        </div>
    </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import CommonMd from "@/components/general/md/CommonMd.vue";

export default defineComponent({
    components: { CommonMd },
    props: {
        id: Number,
        type: String,
        content: String,
        timeFrom: String,
        timeTo: String
    },
    setup(props) {
        return {
            props,
        };
    },
});
</script>

<style lang="scss" scoped>
@import "@/assets/scss/index.scss";

.footprintItem {
    width: calc(100% - 5px);
    margin: 0 auto;
    margin-top: 2px;
    margin-bottom: 20px;
    padding: 10px;
    box-shadow: 0 0 3px rgba($color: $black, $alpha: 0.8);
    -webkit-box-shadow: 0 0 3px rgba($color: $black, $alpha: 0.8);
    -moz-box-shadow: 0 0 3px rgba($color: $black, $alpha: 0.8);
    color: $normal;

    .content {
        margin-bottom: 10px;
    }

    .foot {
        font-size: 15px;
        height: 40px;
        margin-top: 10px;
        padding-top: 5px;
        line-height: 40px;
        border-top: 1px dashed rgba($color: $black, $alpha: 0.5);
        display: flex;
        justify-content: space-between;
    }
}
</style>