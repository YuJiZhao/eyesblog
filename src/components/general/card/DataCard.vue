<template>
    <standard-card :title="title" :icon="icon">
        <Wait :show="show" :fail="isFail" height="100px">
            <div class="card dataCard">
                <ul>
                    <li v-for="item in cardConfig" :key="item.name">
                        <div>{{ item.title }} :</div>
                        <div>{{ cardData[item.name] }}</div>
                    </li>
                </ul>
            </div>
        </Wait>
    </standard-card>
</template>

<script lang="ts">
import { defineComponent, ref, inject, onMounted } from "vue";
import { ProcessInterface } from "@/d.ts/plugin";
import { codeConfig } from "@/config/program";
import StandardCard from "@/components/general/card/components/StandardCard.vue";
import resource from "@/config/resource";
import { Wait } from "@/components/general/popup";

export default defineComponent({
    components: { StandardCard, Wait },
    props: ["title", "api", "cardConfig"],
    setup(props) {
        const $process = inject<ProcessInterface>("$process")!;
        const $api = inject<any>("$api")!;

        let show = ref(true);
        let isFail = ref(false);
        let cardData: any = ref({});

        onMounted(() => {
            show.value = true;
            isFail.value = false;
            $api[props.api]().then((data: any) => {
                if (data.code == codeConfig.success) {
                    cardData.value = data.data;
                    show.value = false;
                } else {
                    $process.tipShow.error(data.msg);
                    isFail.value = true;
                }
            });
        })

        return {
            title: props.title,
            cardConfig: props.cardConfig,
            icon: resource.data,
            cardData,
            show,
            isFail
        };
    },
});
</script>

<style lang="scss" scoped>
@import "@/assets/scss/index.scss";

.dataCard {
    li {
        display: flex;
        justify-content: space-between;
        margin-bottom: 5px;
        color: $normal;
    }
}
</style>