<template>
    <div class="shuoshuo">
        <Wait :show="show" :fail="isFail" height="400px">
            <shuo-list :key="shuoSentry" class="shuoList" :shuoListData="shuoListData"/>
        </Wait>
        <Pagination
            :key="shuoSentry"
            :total="total"
            :size="pageSize"
            :initPage="page"
            @pageChange="pageChange"
        />
    </div>
</template>

<script lang="ts">
import { defineComponent, inject, onActivated, onBeforeMount, ref } from "vue";
import { ProcessInterface, ApiObject } from "@/d.ts/plugin";
import useProcessControl from "@/hooks/useProcessControl";
import { CardDirection, CardType, CardList } from "@/constant";
import { ShuoList } from "@/components/content/shuoshuo";
import { codeConfig } from "@/config/program";
import { Wait } from "@/components/general/popup";
import Pagination from "@/components/general/Pagination/pagination.vue";
import { goBoth, GoBothType } from "@/hooks/useGoBoth";

export default defineComponent({
    name: "Shuoshuo",
    components: { ShuoList, Wait, Pagination },
    setup() {
        const $process = inject<ProcessInterface>("$process")!;
        const $api = inject<ApiObject>("$api")!;

        let shuoSentry = ref(0);
        let shuoListData = ref([]);
        let page = ref(1);
        let pageSize = ref(6);
        let total = ref(0);
        let show = ref(true);
        let isFail = ref(false);

        async function getShuoshuoList() {
            await $api.getShuoshuoList({page: page.value}).then(({ code, msg, data }) => {
                if (code == codeConfig.success) {
                    shuoListData.value = data.data;
                    total.value = data.total;
                    show.value = false;
                    shuoSentry.value++;
                    goBoth(GoBothType.TopSpeed);
                } else {
                    $process.tipShow.error(msg);
                    isFail.value = true;
                }
            });
        }

        async function pageChange(target: number) {
            page.value = target;
            getShuoshuoList();
        }

        onBeforeMount(() => {
            getShuoshuoList();
        });

        onActivated(() => {
            useProcessControl(
                true,
                {
                    direction: CardDirection.row,
                    cardType: CardType.CardList,
                    cardList: CardList.ShuoCardList,
                    follow: false,
                },
                true
            );
        });

        return {
            page,
            pageSize,
            total,
            shuoListData,
            show,
            isFail,
            shuoSentry,
            pageChange,
        };
    },
});
</script>

<style lang="scss" scoped>
.shuoshuo {
    width: 100%;
}
</style>