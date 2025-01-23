<template>
    <div class="product">
        <Wait :show="show" :fail="isFail" height="400px">
            <product-list :key="productSentry" :dataList="productListData" />
        </Wait>
        <Pagination class="pagination" :key="productSentry" :total="total" :size="pageSize" :initPage="page" @pageChange="pageChange" />
    </div>
</template>

<script lang="ts">
import { defineComponent, inject, onActivated, ref, onBeforeMount } from 'vue';
import { ProcessInterface, ApiObject } from "@/d.ts/plugin";
import useProcessControl from "@/hooks/useProcessControl";
import { CardDirection, CardType, CardList } from "@/constant";
import { codeConfig } from "@/config/program";
import { goBoth, GoBothType } from "@/hooks/useGoBoth";
import { Wait } from "@/components/general/popup";
import Pagination from "@/components/general/Pagination/pagination.vue";
import { ProductList } from "@/components/content/product";

export default defineComponent({
    name: "Product",
    components: { Pagination, Wait, ProductList },
    setup() {
        const $api = inject<ApiObject>("$api")!;
        const $process = inject<ProcessInterface>("$process")!;

        let show = ref(true);
        let isFail = ref(false);
        let page = ref(1);
        let pageSize = ref(6);
        let total = ref(1);
        let productListData = ref([]);
        let productSentry = ref(0);

        async function getProductList() {
            $api.getProductPage({pageNo: page.value}).then(({ code, msg, data }) => {
                if (code == codeConfig.success) {
                    productListData.value = data.records;
                    total.value = data.total;
                    show.value = false;
                    productSentry.value++;
                    goBoth(GoBothType.TopSpeed);
                } else {
                    $process.tipShow.error(msg);
                    isFail.value = true;
                }
            });
            show.value = false;
        }

        function pageChange(target: number) {
            page.value = target;
            getProductList();
        }

        onBeforeMount(() => {
            getProductList();
        })

        onActivated(() => {
            useProcessControl(true, {
                direction: CardDirection.row,
                cardType: CardType.CardList,
                cardList: CardList.ProductCardList
            });
        })

        return {
            show,
            isFail,
            total,
            page,
            pageSize,
            productListData,
            productSentry,
            pageChange
        };
    },
});
</script>

<style lang="scss" scoped>
.product {
    width: 100%;
    .pagination {
        margin: 20px auto;
    }
}
</style>