<template>
  <base-card>
    <div class="ownerCard">
      <div class="avatar" :style="{ backgroundImage: 'url(' + context.avatar + ')' }"></div>
      <div class="words">
        <p class="name">{{ context.name }}</p>
        <p class="motto">{{ context.motto }}</p>
      </div>
    </div>
  </base-card>
</template>

<script lang="ts">
import { defineComponent, inject } from "vue";
import BaseCard from "./components/BaseCard.vue";
import { ContextInterface } from "@/d.ts/plugin";

export default defineComponent({
  components: { BaseCard },
  setup() {
    const $context = inject<ContextInterface>("$context")!;

    let context: any = {
      avatar: $context.data.ownerAvatar,
      name: $context.data.ownerName,
      motto: $context.data.ownerMotto,
    };

    return {
      context
    };
  },
});
</script>

<style lang="scss" scoped>
@import "@/assets/scss/index.scss";

.ownerCard {
  padding-top: 10px;
  .avatar {
    width: 110px;
    height: 110px;
    background-size: 100% 100%;
    border-radius: 50%;
    margin: 0px auto;
    margin-bottom: 20px;
  }
  .words {
    .name {
      font-size: 20px;
      color: $title;
      text-align: center;
      margin-bottom: 10px;
    }
    .motto {
      color: $normal;
      text-align: center;
    }
  }
}
</style>