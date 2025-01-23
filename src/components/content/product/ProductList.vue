<template>
    <div class="productList">
        <product-item class="productItem" v-for="item in dataList" :key="item.title" :data="item"/>
    </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import ProductItem from "./components/ProductItem.vue";

export default defineComponent({
    components: { ProductItem },
    props: ["dataList"],
    setup(props) {
        return {
            dataList: props.dataList,
        };
    },
});
</script>

<style lang="scss" scoped>
.productList {
    width: 100%;
    margin: 0 auto;
    display: flex;
    flex-wrap: wrap;
    justify-content: space-between;

    .productItem {
        width: calc(50% - 20px);
        margin: 3px 5px 10px 5px;
    }
}

@media screen and (max-width: 1040px) {
    .productItem {
        width: calc(100% - 10px) !important;
    }
}
</style>