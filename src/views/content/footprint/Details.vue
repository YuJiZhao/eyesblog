<template>
  <div class="details">
    <footprint-head class="head" :key="footprintSentry" :country="country" :province="province" :city="city" />
    <Wait :show="show" :fail="isFail" height="400px">
      <footprint-content-list class="footprintList" :key="footprintSentry" :footprintListData="footprintListData" />
    </Wait>
    <Pagination :key="footprintSentry" :total="total" :size="pageSize" :initPage="page" @pageChange="pageChange" />
  </div>
</template>

<script lang="ts">
import { defineComponent, onMounted, onBeforeMount, inject, ref } from "vue";
import { useRouter } from "vue-router";
import useProcessControl from "@/hooks/useProcessControl";
import { ProcessInterface, ApiObject } from "@/d.ts/plugin";
import { CardDirection, CardList, CardType } from "@/constant";
import { codeConfig } from "@/config/program";
import { FootprintHead, FootprintContentList } from "@/components/content/footprint";
import { Wait } from "@/components/general/popup";
import Pagination from "@/components/general/Pagination/pagination.vue";
import { goBoth, GoBothType } from "@/hooks/useGoBoth";

export default defineComponent({
  components: { Wait, FootprintHead, Pagination, FootprintContentList },
  setup() {
    const router = useRouter();
    const $process = inject<ProcessInterface>("$process")!;
    const $api = inject<ApiObject>("$api")!;

    let footprintId = ref(router.currentRoute.value.params.id);
    let footprintSentry = ref(0);
    let footprintListData = ref([]);
    let country = ref("");
    let province = ref("");
    let city = ref("");
    let page = ref(1);
    let pageSize = ref(6);
    let total = ref(0);
    let show = ref(true);
    let isFail = ref(false);

    async function pageChange(target: number) {
      page.value = target;
      getFootprintList();
    }

    async function getFootprintList() {
      await $api.getFootprintContentList({
        id: footprintId.value,
        page: page.value
      }).then(({ code, msg, data }) => {
        if (code == codeConfig.success) {
          country.value = data.country;
          province.value = data.province;
          city.value = data.city;
          total.value = data.data.total;
          footprintListData.value = data.data.data;
          show.value = false;
          footprintSentry.value++;
        } else {
          $process.tipShow.error(msg);
          isFail.value = true;
        }
        goBoth(GoBothType.TopSpeed);
      })
    }

    onMounted(() => {
      useProcessControl(true, {
        direction: CardDirection.row,
        cardType: CardType.CardList,
        cardList: CardList.FootprintCardList
      });
    });

    onBeforeMount(() => {
      getFootprintList();
    });

    return {
      footprintSentry,
      footprintListData,
      country,
      province,
      city,
      page,
      pageSize,
      total,
      show,
      isFail,
      pageChange
    };
  },
});
</script>

<style lang="scss" scoped>
.details {
}
</style>