<template>
  <div class="versionCardList">
    <owner-card class="card" />
    <announce-card class="card" />
    <data-card class="card" :title="dataTitle" :api="dataApi" :cardConfig="dataCardConfig" />
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import { OwnerCard, AnnounceCard, DataCard } from "@/components/general/card";
import { dataCardConfig } from "./config";

export default defineComponent({
  components: { OwnerCard, AnnounceCard, DataCard },
  setup() {
    return {
      dataTitle: "版本数据",
      dataApi: "getVersionInfo",
      dataCardConfig
    };
  },
});
</script>

<style lang="scss" scoped>
.versionCardList {
  .card {
    margin-bottom: 20px;
  }
}
</style>