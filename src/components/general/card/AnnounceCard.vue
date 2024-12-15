<template>
    <notice-card :id="id" :title="title" :icon="icon" />
</template>

<script lang="ts">
import { defineComponent } from "vue";
import NoticeCard from "./NoticeCard.vue";
import { contextConfig } from "@/config/program";
import resource from "@/config/resource";

export default defineComponent({
    components: { NoticeCard },
    setup() {
        return {
            id: contextConfig.announce,
            title: "站长说",
            icon: resource.announce
        };
    },
});
</script>

<style lang="scss" scoped></style>