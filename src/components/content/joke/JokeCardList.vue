<template>
  <div class="jokeCardList">
    <owner-card class="card" />
    <notice-card class="card" :id="id" :title="title" :icon="icon"/>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import { OwnerCard, NoticeCard } from "@/components/general/card";
import { contextConfig } from "@/config/program";
import resource from "@/config/resource";

export default defineComponent({
  components: { OwnerCard, NoticeCard },
  setup() {
    return {
      id: contextConfig.jokeNotice,
      title: "站长说",
      icon: resource.announce
    };
  },
});
</script>

<style lang="scss" scoped>
.jokeCardList {
  .card {
    margin-bottom: 20px;
  }
}
</style>