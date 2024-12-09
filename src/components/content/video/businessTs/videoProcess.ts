import { ref } from "vue";

const CardList = {
    cardInitLoad: ref(true),
    cardInitFail: ref(false)
};

const ext = {
    fullscreenSentry: ref(false),
    doFullScreen: () => {
        ext.fullscreenSentry.value = !ext.fullscreenSentry.value;
    },

    likesNumSentry: ref(false),

    videoEndSentry: ref(false),
    doChangeVideo: () => {
        ext.videoEndSentry.value = !ext.videoEndSentry.value;
    },

    videoVariable: ref(false),
}

const videoProcess = {
    ...CardList,
    ...ext
}

export default videoProcess;