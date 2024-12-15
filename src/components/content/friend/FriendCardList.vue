<template>
  <div class="friendCard">
    <owner-card class="card" />
    <announce-card class="card" />
    <friend-data-card class="card" />
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import { OwnerCard, AnnounceCard } from "@/components/general/card";
import FriendDataCard from "./components/FriendDataCard.vue";

export default defineComponent({
  components: { OwnerCard, FriendDataCard, AnnounceCard },
  setup() {
    return {
    };
  },
});
</script>

<style lang="scss" scoped>
.friendCard {
  .card {
    margin-bottom: 20px;
  }
}

</style>