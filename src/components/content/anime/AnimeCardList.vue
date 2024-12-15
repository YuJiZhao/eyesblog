<template>
  <div class="animeCard">
    <owner-card class="card" />
    <notice-card class="card" :id="id" :title="title" :icon="icon"/>
    <anime-data-card class="card" />
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import { OwnerCard, NoticeCard } from "@/components/general/card";
import AnimeDataCard from "./components/cards/AnimeDataCard.vue";
import { contextConfig } from "@/config/program";
import resource from "@/config/resource";

export default defineComponent({
  components: { OwnerCard, NoticeCard, AnimeDataCard },
  setup() {
    return {
      id: contextConfig.animeNotice,
      title: "站长说",
      icon: resource.announce
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