<template>
    <div class="footprintContentList">
        <footprint-content-item 
            class="footprintContentItem" 
            v-for="item in footprintDataList" :key="item.id" 
            :id="item.id"
            :type="item.type"
            :content="item.content"
            :timeFrom="item.timeFrom" 
            :timeTo="item.timeTo" 
        />
    </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import FootprintContentItem from "./components/FootprintContentItem.vue";

export default defineComponent({
    components: { FootprintContentItem },
    props: ["footprintListData"],
    setup(props) {
        return {
            footprintDataList: props.footprintListData
        };
    },
});
</script>

<style lang="scss" scoped>
.footprintContentList {
    width: 100%;
    min-width: 335px;
    margin: 20px auto;
}
</style>