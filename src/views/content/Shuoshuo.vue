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

        let page = ref(1);
        let pageSize = ref(6);
        let total = ref(0);
        let shuoListData = ref([]);
        let show = ref(true);
        let isFail = ref(false);
        let loadShow = ref(false);
        let shuoSentry = ref(0);

        async function getShuoshuoList() {
            await $api.getShuoshuoList({page: page.value}).then(({ code, msg, data }) => {
                if (code == codeConfig.success) {
                    shuoListData.value = data.data;
                    total.value = data.total;
                    show.value = false;
                    shuoSentry.value++;
                    goBoth(GoBothType.TopSpeed);
                } else {
                    $process.tipShow.error("获取数据失败");
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
            loadShow,
            pageChange,
        };
    },
});
</script>

<style lang="scss" scoped>
@import "@/assets/scss/index.scss";

.shuoshuo {
    width: 100%;
    .loadBtnWait {
        width: 100px;
        height: 40px;
        box-shadow: 0 0 3px rgba($color: $black, $alpha: 0.8);
        -webkit-box-shadow: 0 0 3px rgba($color: $black, $alpha: 0.8);
        -moz-box-shadow: 0 0 3px rgba($color: $black, $alpha: 0.8);
        margin: 10px auto;
        .loadBtn {
            color: $normal;
            height: 100%;
            display: flex;
            align-items: center;
            justify-content: center;
            cursor: pointer;
            img {
                display: block;
                width: 20px;
                height: 20px;
            }
        }
    }
}
</style>