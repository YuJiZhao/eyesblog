<template>
  <div class="dialog" v-if="status">
    <component :is="dialogComponents[choice]"/>
  </div>
</template>

<script lang="ts">
import { defineComponent, inject } from "vue";
import { ProcessInterface } from "@/d.ts/modules/process";
import { ContactMe } from "./dialogComponent";

export default defineComponent({
  components: { ContactMe },
  setup() {
    const $process = inject<ProcessInterface>("$process")!;
    const dialogComponents = [ContactMe];
    
    return {
      dialogComponents,
      choice: $process.dialogChoice,
      status: $process.dialogStatus
    };
  },
});
</script>