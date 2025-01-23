<template>
  <div class="bookItem">
    <div class="title">{{ data.title }}</div>
    <div class="meta">
      <span>作者: {{ data.author }}</span>
      <span> | </span>
      <span>类型: {{ data.type }}</span>
      <span> | </span>
      <span>时期: {{ data.period }}</span>
    </div>
    <div class="introduce">{{ data.introduce }}</div>
    <div class="footer">
      <div class="time">{{ data.createTime }}</div>
      <div class="view">点击:{{ view }}</div>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import utils from "@/utils/helper";

export default defineComponent({
  props: ["data"],
  setup(props) {
    return {
      data: props.data,
      view: utils.simplifyNum(props.data.view)
    };
  },
});
</script>

<style lang="scss" scoped>
@import "@/assets/scss/index.scss";

.bookItem {
  box-shadow: 0 0 3px rgba($color: $black, $alpha: 0.8);
  -webkit-box-shadow: 0 0 3px rgba($color: $black, $alpha: 0.8);
  -moz-box-shadow: 0 0 3px rgba($color: $black, $alpha: 0.8);
  color: $normal;
  padding: 10px;
  display: flex;
  flex-direction: column;

  .title {
    font-size: 20px;
    font-weight: bold;
  }

  .meta {
    text-align: right;
    margin: 10px 0;
  }

  .introduce {
    line-height: 18px;
    flex: 1;
  }

  .footer {
    height: 20px;
    line-height: 20px;
    display: flex;
    justify-content: space-between;
    margin-top: 10px;
  }
}
</style>