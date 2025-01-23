<template>
  <div class="animeCard">
    <owner-card class="card" />
    <notice-card class="card" :id="noticeId" :title="noticeTitle" :icon="noticeIcon"/>
    <data-card class="card" :title="dataTitle" :api="dataApi" :cardConfig="dataCardConfig" />
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import { OwnerCard, NoticeCard, DataCard } from "@/components/general/card";
import { contextConfig } from "@/config/program";
import resource from "@/config/resource";
import { dataCardConfig } from "./config";

export default defineComponent({
  components: { OwnerCard, NoticeCard, DataCard },
  setup() {
    return {
      noticeId: contextConfig.animeNotice,
      noticeTitle: "站长说",
      noticeIcon: resource.announce,
      dataTitle: "动漫数据",
      dataApi: "getAnimeListInfo",
      dataCardConfig
    };
  },
});
</script>

<style lang="scss" scoped>
.animeCard {
  .card {
    margin-bottom: 20px;
  }
}
</style>