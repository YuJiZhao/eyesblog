<template>
  <div class="shuoCard">
    <owner-card class="card" />
    <announce-card class="card" />
    <shuo-data-card class="card" />
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import ShuoDataCard from "@/components/content/shuoshuo/components/cards/ShuoDataCard.vue";
import { OwnerCard, AnnounceCard } from "@/components/general/card";

export default defineComponent({
  components: { OwnerCard, ShuoDataCard, AnnounceCard },
  setup() {
    return {
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