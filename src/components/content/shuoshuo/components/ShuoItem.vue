<template>
  <div class="shuoItem">
    <div class="content">
      <shuo-md :content="props.content" :mdId="props.id" />
    </div>
    <div class="imgList" v-if="props.picList?.length">
      <div class="shuoImg" v-for="item in props.picList" :key="item">
        <Image :imgArray="props.picList" :url="item" size="97px" />
      </div>
    </div>
    <div class="foot">
      <div class="time">{{props.createTime}}</div>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import Image from "@/components/general/image/Image.vue";
import ShuoMd from "./ShuoMd.vue";

export default defineComponent({
  components: { Image, ShuoMd },
  props: {
    id: Number,
    content: String,
    picList: Array<string>,
    createTime: String
  },
  setup(props) {
    return {
      props,
    };
  },
});
</script>

<style lang="scss" scoped>
@import "@/assets/scss/index.scss";

.shuoItem {
  width: calc(100% - 5px);
  margin: 0 auto;
  margin-top: 2px;
  margin-bottom: 20px;
  padding: 10px;
  box-shadow: 0 0 3px rgba($color: $black, $alpha: 0.8);
  -webkit-box-shadow: 0 0 3px rgba($color: $black, $alpha: 0.8);
  -moz-box-shadow: 0 0 3px rgba($color: $black, $alpha: 0.8);
  color: $normal;
  .content {
    margin-bottom: 10px;
  }
  .imgList {
    display: flex;
    justify-content: start;
    flex-wrap: wrap;
    .shuoImg {
      margin-right: 5px;
      margin-bottom: 5px;
    }
  }
  .foot {
    font-size: 15px;
    height: 40px;
    margin-top: 10px;
    padding-top: 5px;
    line-height: 40px;
    text-align: right;
    border-top: 1px dashed rgba($color: $black, $alpha: 0.5);
  }
}
</style>