<template>
    <standard-card title="我曾走过" :icon="icon">
        <Wait :show="show" :fail="isFail" height="100px">
            <div class="card footprintDataCard">
                <ul>
                    <li v-for="item in footprintDataCardConfig" :key="item.name">
                        <div>{{ item.title }} :</div>
                        <div>{{ footprintData[item.name] }}</div>
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
import { footprintDataCardConfig } from "../config";
import { Wait } from "@/components/general/popup";

export default defineComponent({
    components: { StandardCard, Wait },
    setup() {
        const $process = inject<ProcessInterface>("$process")!;
        const $api = inject<ApiObject>("$api")!;

        let show = ref(true);
        let isFail = ref(false);
        let footprintData: any = ref({});

        async function getFootprintListInfo() {
            show.value = true;
            isFail.value = false;
            await $api.getFootprintListInfo().then(({ code, data }) => {
                if (code == codeConfig.success) {
                    footprintData.value = data;
                    show.value = false;
                } else {
                    $process.tipShow.error("足迹数据获取失败");
                    isFail.value = true;
                }
            });
        }

        onMounted(() => {
            getFootprintListInfo();
        })

        return {
            icon: resource.data,
            footprintDataCardConfig,
            footprintData,
            show,
            isFail
        };
    },
});
</script>

<style lang="scss" scoped>
@import "@/assets/scss/index.scss";

.footprintDataCard {
    li {
        display: flex;
        justify-content: space-between;
        margin-bottom: 5px;
        color: $normal;
    }
}
</style>