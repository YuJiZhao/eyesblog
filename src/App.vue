<template>
  <router-view v-slot="{ Component }">
    <template v-if="Component">
      <component :is="Component" v-if="isShow" />
    </template>
  </router-view>

  <!-- 弹出层 -->
  <Tip />
  <Mask />
  <Load />
  <Alert />
  <Dialog />
</template>

<script lang="ts">
import { defineComponent, inject, ref } from "vue";

import {
  ApiObject,
  RespInterface,
  ContextInterface,
  UserInterface,
  ProcessInterface,
  WindowInterface,
} from "@/d.ts/plugin";
import { useRouter } from "vue-router";
import { siteConfig, codeConfig } from "@/config/program";
import { Mask, Load, Tip, Alert, Dialog } from "@/components/general/popup";
import resource from "@/config/resource";
import { preloadList, errorPath } from "@/config/site";
import preload from "@/utils/preLoad";
import utils from "@/utils/helper";

export default defineComponent({
  components: { Mask, Load, Tip, Alert, Dialog },
  setup() {
    const router = useRouter();
    const $api = inject<ApiObject>("$api")!;
    const $context = inject<ContextInterface>("$context")!;
    const $user = inject<UserInterface>("$user")!;
    const $process = inject<ProcessInterface>("$process")!;
    const $window = inject<WindowInterface>("$window")!;

    let isShow = ref(false);

    // 空间初始化
    router.isReady().then(async () => {
      if(router.currentRoute.value.fullPath != errorPath.context) {
        localStorage.setItem(siteConfig.enterURL, router.currentRoute.value.fullPath);
      }
      Promise.all([context(), user()]).then(async (values) => {
        const [contextData, userData] = values;
        initContext(contextData);
        closeLoad();
        initUser(userData);
        doPreload();
      }).catch(() => {
        jumpPath(errorPath.context);
      }).finally(() => {
        listenWindow.initAll();
      });
    });
    async function context() {
      return await $api.getContext();
    }

    async function user() {
      if (utils.getCookie(siteConfig.tokenHeader.sToken) == "" || utils.getCookie(siteConfig.tokenHeader.lToken) == "") return "";
      return await $api.getUserInfo();
    }

    function initContext(contextData: RespInterface) { 
      if (contextData.code == codeConfig.success) {
        $context.init(contextData.data);
      }
      else if(![codeConfig.authentication_error, codeConfig.authentication_error_illegal_jwt].includes(contextData.code)) {
        jumpPath(errorPath.context);
      }
    }

    function initUser(userData: "" | RespInterface) {
      if(userData == "" || userData.code != codeConfig.success) {
        $user.init({avatar: resource.defaultAvatar});
      } else {
        $user.init(userData.data);
        $user.status = 1;
      }
    }

    // 为组件提供浏览器数据
    let listenWindow = {
      initSize: () => $window.initSize(),
      initDistance: () => $window.initDistance(),
      initAll() {
        this.initSize();
        this.initDistance();
      }
    }
    window.addEventListener("resize", listenWindow.initSize);
    window.addEventListener("scroll", listenWindow.initDistance);

    // 资源预加载
    function doPreload() {
      preload({
        data: preloadList,
        success: () => {}
      });
    }

    // 页面跳转
    function jumpPath(path: string) {
      router.push(path);
      closeLoad();
    }

    // 关闭遮罩层
    function closeLoad() {
      isShow.value = true;
      $process.loadHide();
    }

    return {
      isShow,
    };
  },
});
</script>