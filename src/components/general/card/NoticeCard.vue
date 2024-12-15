<template>
  <standard-card :title="props.title" :icon="props.icon">
    <div class="noticeCard">
      <div class="notice">{{ content }}</div>
    </div>
  </standard-card>
</template>

<script lang="ts">
import { defineComponent, inject, onMounted, ref } from "vue";
import StandardCard from "./components/StandardCard.vue";
import { ApiObject, ProcessInterface } from "@/d.ts/plugin";
import { codeConfig } from "@/config/program";

export default defineComponent({
  components: { StandardCard },
  props: ["id", "title", "icon"],
  setup(props) {
    const $api = inject<ApiObject>("$api")!;
    const $process = inject<ProcessInterface>("$process")!;
    let content = ref("");

    async function getNotice() {
      await $api.getContextItem([props.id]).then(({ code, msg, data }) => {
        if (code == codeConfig.success) {
          content.value = data.content;
        } else {
          $process.tipShow.error(msg);
        }
      }
      );
    }

    onMounted(() => {
      getNotice();
    });

    return {
      props,
      content
    };
  },
});
</script>

<style lang="scss" scoped>
@import "@/assets/scss/index.scss";

.noticeCard {
  color: $normal;
  line-height: 18px;
}
</style>