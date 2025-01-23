<template>
    <div class="home">
        <div class="content">
            <div class="avatar" :style="{ backgroundImage: 'url(' + context.avatar + ')' }"></div>
            <div class="words">
                <p class="name">{{ context.name }}</p>
                <p class="motto">{{ context.motto }}</p>
            </div>
            <div class="link">
                <template v-for="item in headerConfig" :key="item.word">
                    <div class="option" v-if="!item.children">
                        <span @click="pageJump(item.path)">{{ item.word }}</span>
                    </div>
                    <div class="option" v-for="child in item.children" :key="child.word" v-else>
                        <span @click="pageJump(child.path)">{{ child.word }}</span>
                    </div>
                </template>
            </div>
        </div>
    </div>
</template>

<script lang="ts">
import {
    defineComponent,
    inject,
    onActivated,
} from "vue";
import { ContextInterface } from "@/d.ts/plugin";
import useProcessControl from "@/hooks/useProcessControl";
import { useRouter } from "vue-router";
import { headerConfig } from "@/config/site";

export default defineComponent({
    name: "Home",
    components: {},
    setup() {
        const router = useRouter();
        const $context = inject<ContextInterface>("$context")!;

        let context = {
            avatar: $context.data.ownerAvatar,
            name: $context.data.ownerName,
            motto: $context.data.ownerMotto,
        };

        function pageJump(path: string) {
            router.push(path);
        }


        onActivated(() => {
            useProcessControl(false, false, false);
        });

        return {
            context,
            headerConfig,
            pageJump
        };
    },
});
</script>

<style lang="scss" scoped>
@import "@/assets/scss/index.scss";

.home {
    width: 100%;

    .content {
        width: 370px;
        position: fixed;
        top: 15%;
        left: calc(50% - 185px);

        .avatar {
            width: 110px;
            height: 110px;
            background-size: 100% 100%;
            border-radius: 50%;
            margin: 0px auto;
            margin-bottom: 20px;
        }

        .words {
            margin-bottom: 20px;

            .name {
                font-size: 20px;
                color: $title;
                text-align: center;
                margin-bottom: 10px;
            }

            .motto {
                color: $normal;
                text-align: center;
            }
        }

        .link {
            color: $normal;
            display: flex;
            flex-wrap: wrap;
            justify-content: space-around;
            margin: 0 50px;
            margin-top: 30px;

            .option {
                width: 55px;
                height: 30px;
                text-align: center;
                line-height: 30px;

                span {
                    cursor: pointer;

                    &:hover {
                        color: rgb(127, 200, 248);
                        transition: 0.3s;
                    }
                }
            }
        }
    }
}
</style>