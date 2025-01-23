<template>
    <div class="bookCard">
        <owner-card class="card" />
        <notice-card class="card" :id="noticeId" :title="noticeTitle" :icon="noticeIcon"/>
        <data-card class="card" :title="dataTitle" :api="dataApi" :cardConfig="dataCardConfig" />
    </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import { OwnerCard, NoticeCard, DataCard } from "@/components/general/card";
import { contextConfig } from "@/config/program";
import resource from "@/config/resource";
import { dataCardConfig } from "./config";

export default defineComponent({
    components: { OwnerCard, NoticeCard, DataCard },
    setup() {
        return {
            noticeId: contextConfig.bookNotice,
            noticeTitle: "站长说",
            noticeIcon: resource.announce,
            dataTitle: "书单数据",
            dataApi: "getBookListInfo",
            dataCardConfig
        };
    },
});
</script>

<style lang="scss" scoped>
.bookCard {
    .card {
        margin-bottom: 20px;
    }
}
</style>