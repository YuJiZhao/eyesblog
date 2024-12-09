import Resource from "@/config/resource";
import {CardDirection, CardList, CardType } from "@/constant";

const videoSideCards = {
    direction: CardDirection.reverse,
    cardType: CardType.CardList,
    cardList: CardList.VideoCardList
}

const videoFuncBar = [
    {
        name: "changeBtn",
        icon: Resource.switch,
        word: "切换视频",
        clickFunc: "changeVideo"
    },
    {
        name: "screenBtn",
        icon: Resource.fullscreen,
        word: "全屏播放",
        clickFunc: "doFullScreen"
    },
    {
        name: "clearBtn",
        icon: Resource.hide,
        word: ["关闭侧栏", "打开侧栏"],
        clickFunc: "doClear"
    }
];

export { videoSideCards, videoFuncBar };