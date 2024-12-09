<template>
  <div class="details">
    <head-meta />
    <md-editor />
  </div>
</template>

<script lang="ts">
import { defineComponent, onMounted, inject, ref } from "vue";
import { useRouter } from "vue-router";
import useProcessControl from "@/hooks/useProcessControl";
import { ProcessInterface, ApiObject } from "@/d.ts/plugin";
import { CardDirection, CardList, CardType } from "@/constant";
import { codeConfig } from "@/config/program";
import { MdEditor, HeadMeta } from "@/components/content/blogDetail";
import { goBoth, GoBothType } from "@/hooks/useGoBoth";
import { blogDetailContext } from "@/components/content/blogDetail/businessTs/blogDetailContext";
import blogDetailProcess from "@/components/content/blogDetail/businessTs/blogDetailProcess";

export default defineComponent({
  components: { MdEditor, HeadMeta },
  setup() {
    const router = useRouter();
    const $process = inject<ProcessInterface>("$process")!;
    const $api = inject<ApiObject>("$api")!;
    let blogId = ref(router.currentRoute.value.params.id);

    async function getBlogInfo() {
      blogDetailProcess.cardInitLoad.value = true;
      blogDetailProcess.cardInitFail.value = false;
      await $api.getBlogInfo([blogId.value]).then(({code, msg, data}) => {
        if(code == codeConfig.success) {
          blogDetailContext.init(data);
          blogDetailProcess.cardInitLoad.value = false;
        } else {
          $process.tipShow.error(msg);
          blogDetailProcess.cardInitFail.value = true;
        }
      });
    }

    onMounted(() => {
      goBoth(GoBothType.TopSpeed);
      getBlogInfo();
      useProcessControl(true, {
        direction: CardDirection.row,
        cardType: CardType.CardList,
        cardList: CardList.BlogDetailCardList,
        follow: true
      }, true);
    });

    return {
      blogId
    };
  },
});
</script>

<style lang="scss" scoped>
.details {
  width: 100%;
}
</style>