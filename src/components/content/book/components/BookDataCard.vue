<template>
    <standard-card title="书单数据" :icon="icon">
        <Wait :show="show" :fail="isFail" height="100px">
            <div class="card bookDataCard">
                <ul>
                    <li v-for="item in bookDataCardConfig" :key="item.name">
                        <div>{{ item.title }} :</div>
                        <div>{{ bookData[item.name] }}</div>
                    </li>
                </ul>
            </div>
        </Wait>
    </standard-card>
</template>

<script lang="ts">
import { defineComponent, ref, inject, onMounted } from "vue";
import { ProcessInterface, ApiObject } from "@/d.ts/plugin";
import { codeConfig } from "@/config/program";
import StandardCard from "@/components/general/card/components/StandardCard.vue";
import resource from "@/config/resource";
import { bookDataCardConfig } from "@/components/content/book/config";
import { Wait } from "@/components/general/popup";

export default defineComponent({
    components: { StandardCard, Wait },
    setup() {
        const $process = inject<ProcessInterface>("$process")!;
        const $api = inject<ApiObject>("$api")!;

        let show = ref(true);
        let isFail = ref(false);
        let bookData: any = ref({});

        async function getBookListInfo() {
            show.value = true;
            isFail.value = false;
            await $api.getBookListInfo().then(({ code, data }) => {
                if (code == codeConfig.success) {
                    bookData.value = data;
                    show.value = false;
                } else {
                    $process.tipShow.error("动漫数据获取失败");
                    isFail.value = true;
                }
            });
        }

        onMounted(() => {
            getBookListInfo();
        })

        return {
            icon: resource.data,
            bookDataCardConfig,
            bookData,
            show,
            isFail
        };
    },
});
</script>

<style lang="scss" scoped>
@import "@/assets/scss/index.scss";

.bookDataCard {
    li {
        display: flex;
        justify-content: space-between;
        margin-bottom: 5px;
        color: $normal;
    }
}
</style>