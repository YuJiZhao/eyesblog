<template>
  <div class="versionCardList">
    <owner-card class="card" />
    <announce-card class="card" />
    <version-data-card class="card" />
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import VersionDataCard from "@/components/content/version/components/cards/VersionDataCard.vue";
import { OwnerCard, AnnounceCard } from "@/components/general/card";

export default defineComponent({
  components: { OwnerCard, AnnounceCard, VersionDataCard },
  setup() {
    return {
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