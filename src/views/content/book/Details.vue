<template>
  <div class="details">
    <book-item class="bookItem" :data="bookData" :key="bookSentry" />
    <word-card class="wordCard" :word="bookData.word" :key="bookSentry" />
  </div>
</template>

<script lang="ts">
import { defineComponent, onMounted, inject, ref } from "vue";
import { useRouter } from "vue-router";
import useProcessControl from "@/hooks/useProcessControl";
import { ProcessInterface, ApiObject } from "@/d.ts/plugin";
import { CardDirection, CardList, CardType } from "@/constant";
import { codeConfig } from "@/config/program";
import BookItem from "@/components/content/book/components/BookItem.vue";
import { WordCard } from "@/components/general/card"; 

export default defineComponent({
  components: { BookItem, WordCard },
  setup() {
    const router = useRouter();
    const $process = inject<ProcessInterface>("$process")!;
    const $api = inject<ApiObject>("$api")!;

    let bookId = ref(router.currentRoute.value.params.id);
    let bookData = ref({
      word: ""
    });
    let bookSentry = ref(0);

    async function getBookInfo() {
      $api.getBookInfo([bookId.value]).then(({ code, msg, data }) => {
        if (code == codeConfig.success) {
          bookData.value = data;
          bookSentry.value++;
        } else {
          $process.tipShow.error(msg);
        }
      })
    }

    onMounted(() => {
      getBookInfo();
      useProcessControl(true, {
        direction: CardDirection.row,
        cardType: CardType.CardList,
        cardList: CardList.BookCardList
      });
    });

    return {
      bookId,
      bookData,
      bookSentry
    };
  },
});
</script>

<style lang="scss" scoped>
.details {
  .bookItem {
    margin: 3px;
  }

  .wordCard {
    margin: 20px 3px 15px 3px;
  }
}
</style>