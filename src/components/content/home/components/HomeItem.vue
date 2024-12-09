<template>
    <blog-item
      class="item blogItem"
      v-if="data.type == HomeType.blog"
      :id="data.homeList.id"
      :title="data.homeList.title"
      :category="data.homeList.category"
      :words="data.homeList.words"
      :summary="data.homeList.summary"
      :date="data.homeList.createTime"
      :view="data.homeList.views"
      :like="data.homeList.likes"
      :collection="data.homeList.collections"
    />
    <shuo-item
      class="item shuoshuoItem"
      v-if="data.type == HomeType.shuoshuo"
      :id="data.homeList.id"
      :content="data.homeList.content"
      :picList="data.homeList.picList"
      :createTime="data.homeList.createTime"
    />
    <version-item
      class="item versionItem"
      v-if="data.type == HomeType.version"
      :data="data.homeList"
    />
    <anime-item
      class="item animeItem"
      v-if="data.type == HomeType.anime"
      :data="data.homeList"
      @click="jumpAnime(data.homeList.id)"
    />
</template>

<script lang="ts">
import { defineComponent } from "vue";
import { useRouter } from "vue-router";
import { HomeType } from "@/constant";
import BlogItem from "@/components/content/blog/components/BlogItem.vue";
import ShuoItem from "@/components/content/shuoshuo/components/ShuoItem.vue";
import VersionItem from "@/components/content/version/components/VersionItem.vue";
import { AnimeItem } from "@/components/content/anime";

export default defineComponent({
  components: { BlogItem, ShuoItem, VersionItem, AnimeItem },
  props: ["data"],
  setup(props) {
    let router = useRouter();
    function jumpAnime(id: number) {
      window.open(router.resolve(`/anime/details/${id}`).href, "_blank");
    }

    return {
      HomeType,
      data: props.data,
      jumpAnime
    };
  },
});
</script>

<style lang="scss" scoped>
.animeItem {
  cursor: pointer;
}
</style>