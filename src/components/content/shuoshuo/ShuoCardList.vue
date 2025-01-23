<template>
  <div class="shuoCard">
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
  components: { OwnerCard, DataCard, AnnounceCard },
  setup() {
    return {
      dataTitle: "说说数据",
      dataApi: "getShuoshuoListInfo",
      dataCardConfig
    };
  },
});
</script>

<style lang="scss" scoped>
.shuoCard {
  .card {
    margin-bottom: 20px;
  }
}
</style>