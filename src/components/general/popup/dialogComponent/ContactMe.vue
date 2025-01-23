<template>
  <base-dialog :close="false" title="联系站长">
    <div class="content">{{ email }}</div>
  </base-dialog>
</template>

<script lang="ts">
import { defineComponent, inject } from "vue";
import { ContextInterface } from "@/d.ts/plugin";
import BaseDialog from "./BaseDialog.vue";

export default defineComponent({
  components: { BaseDialog },
  setup() {
    const $context = inject<ContextInterface>("$context")!;
    return {
      email: $context.data.ownerEmail!,
    };
  },
});
</script>

<style lang="scss" scoped>
@import "@/assets/scss/index.scss";

.content {
  text-align: center;
  margin: 50px 0;
}
</style>
