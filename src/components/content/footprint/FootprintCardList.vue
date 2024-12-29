<template>
    <div class="footprintCard">
        <owner-card class="card" />
        <notice-card class="card" :id="id" :title="title" :icon="icon"/>
        <footprint-data-card class="card" />
    </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import { OwnerCard, NoticeCard } from "@/components/general/card";
import FootprintDataCard from "./components/FootprintDataCard.vue";
import { contextConfig } from "@/config/program";
import resource from "@/config/resource";

export default defineComponent({
    components: { OwnerCard, NoticeCard, FootprintDataCard },
    setup() {
        return {
            id: contextConfig.footprintNotice,
            title: "站长说",
            icon: resource.announce
        };
    },
});
</script>

<style lang="scss" scoped>
.footprintCard {
    .card {
        margin-bottom: 20px;
    }
}
</style>