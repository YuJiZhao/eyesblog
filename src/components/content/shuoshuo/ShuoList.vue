<template>
  <div class="shuoList">
    <shuo-item
      class="shuoItem"
      v-for="item in shuoDataList" :key="item.id"
      :id="item.id"
      :content="item.content"
      :picList="item.picList"
      :comments="item.comments"
      :createTime="item.createTime"
    />
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import ShuoItem from "@/components/content/shuoshuo/components/ShuoItem.vue"; 

export default defineComponent({
  components: { ShuoItem },
  props: ["shuoListData"],
  setup(props) {
    return {
      shuoDataList: props.shuoListData
    };
  },
});
</script>

<style lang="scss" scoped>
.shuoList {
  width: 100%;
  min-width: 335px;
  margin: 0 auto;
}
</style>