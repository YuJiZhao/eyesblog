<template>
    <div class="bookList">
        <template v-for="item in bookListData" :key="item">
            <book-item class="bookItem" :data="item" @click="jumpDetail(item.id)"/>
        </template>
    </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import BookItem from "./components/BookItem.vue";
import { useRouter } from "vue-router";

export default defineComponent({
    components: { BookItem },
    props: ["bookListData"],
    setup(props) {
        let router = useRouter();

        function jumpDetail(id: number) {
            window.open(router.resolve(`/book/details/${id}`).href, "_blank");
        }

        return {
            bookListData: props.bookListData,
            jumpDetail
        };
    },
});
</script>

<style lang="scss" scoped>
.bookList {
    width: 100%;
    min-width: 340px;
    margin: 0 auto;
    display: flex;
    flex-wrap: wrap;
    justify-content: space-between;

    .bookItem {
        width: calc(50% - 20px);
        cursor: pointer;
        margin: 3px 5px 10px 5px;
    }
}

@media screen and (max-width: 1040px) {
  .bookItem {
    width: calc(100% - 10px) !important;
  }
}

</style>