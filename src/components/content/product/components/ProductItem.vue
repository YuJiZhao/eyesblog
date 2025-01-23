<template>
    <div class="productItem">
        <div class="name">{{ data.name }}</div>
        <div class="meta">
            <span>{{ data.type }}</span>
        </div>
        <div class="introduce"><common-md :mdId="data.title" :content="data.introduce" /></div>
        <div class="footer">
            <div class="time">{{ data.createTime }}</div>
            <div class="url">
                <div class="target codeUrl" @click="jumpPage(data.codeUrl)" v-if="data.codeUrl">项目</div>
                <div class="target viewUrl" @click="jumpPage(data.viewUrl)" v-if="data.viewUrl">体验</div>
            </div>
        </div>
    </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import CommonMd from "@/components/general/md/CommonMd.vue";

export default defineComponent({
    components: { CommonMd },
    props: ["data"],
    setup(props) {
        function jumpPage(url: string) {
            window.open(url, "_blank");
        }

        return {
            data: props.data,
            jumpPage
        };
    },
});
</script>

<style lang="scss" scoped>
@import "@/assets/scss/index.scss";

.productItem {
    box-shadow: 0 0 3px rgba($color: $black, $alpha: 0.8);
    -webkit-box-shadow: 0 0 3px rgba($color: $black, $alpha: 0.8);
    -moz-box-shadow: 0 0 3px rgba($color: $black, $alpha: 0.8);
    color: $normal;
    padding: 10px;
    display: flex;
    flex-direction: column;

    .name {
        font-size: 20px;
        font-weight: bold;
        margin: 5px 0;
    }

    .meta {
        text-align: right;
    }

    .introduce {
        line-height: 18px;
        flex: 1;
    }

    .footer {
        height: 20px;
        line-height: 20px;
        display: flex;
        justify-content: space-between;

        .url {
            display: flex;
            justify-content: space-between;

            .target {
                margin-left: 10px;
                cursor: pointer;
            }
        }
    }
}
</style>