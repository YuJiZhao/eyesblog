<template>
  <div class="friendCard">
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
            noticeId: contextConfig.friendNotice,
            noticeTitle: "站长说",
            noticeIcon: resource.announce,
            dataTitle: "友链数据",
            dataApi: "getFriendListData",
            dataCardConfig
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