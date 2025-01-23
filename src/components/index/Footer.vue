<template>
  <div class="footer" :class="{ static: positionSwitch, fixed: !positionSwitch }">
    <div>{{context.copyright}}</div>
    <div><span class="zwfw" @click="jumpPage">{{context.zwfwCode}}</span></div>
  </div>
</template>

<script lang="ts">
import { defineComponent, inject, reactive, onBeforeMount } from 'vue';
import { ProcessInterface, ApiObject } from "@/d.ts/plugin";
import { urlConfig, codeConfig, contextConfig } from "@/config/program";

export default defineComponent({
  setup() {
    const $process = inject<ProcessInterface>("$process")!;
    const $api = inject<ApiObject>("$api")!;

    let context = reactive({
      copyright: "",
      zwfwCode: "",
      zwfwUrl: ""
    });

    function jumpPage() {
      window.open(context.zwfwUrl);
    }

    async function getContextList() {
      $api.getBatchContextItem({
        idList: contextConfig.footContext
      }).then(({code, msg, data}) => {
        if (codeConfig.success == code) {
          context.copyright = data.copyright;
          context.zwfwCode = data.zwfwCode;
          context.zwfwUrl = data.zwfwUrl;
        } else {
          $process.tipShow.error("foot数据获取失败：" + msg);
        }
      });
    }

    onBeforeMount(() => {
      getContextList();
    });

    return {
      context,
      positionSwitch: $process.footerPosition,
      warehouseUrl: urlConfig.warehouseUrl,
      jumpPage
    };
  },
});
</script>

<style lang="scss" scoped>
@import "@/assets/scss/index.scss";

.footer {
  width: 100%;
  height: 100px;
  background: rgba($color: $white, $alpha: 0.7);
  color: $normal;
  display: flex;
  flex-direction: column;
  justify-content: center;
  div {
    height: 25px;
    line-height: 25px;
  }
  .zwfw {
    cursor: pointer;
  }
}

.fixed {
  position: fixed;
  bottom: 0;
  text-align: center;
}

.static {
  align-items: center;
}
</style>