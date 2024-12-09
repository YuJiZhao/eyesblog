import { ref } from "vue";

const ext = {
    initSentry: ref(false),
    initNotice: () => {
        ext.initSentry.value = !ext.initSentry.value;
    },

    musicEndSentry: ref(false),
    doChangeMusic: () => {
        ext.musicEndSentry.value = !ext.musicEndSentry.value;
    },

    musicRangeSentry: ref(false),
    rangeNotice: () => {
        ext.musicRangeSentry.value = !ext.musicRangeSentry.value;
    }
}

const musicProcess = {
    ...ext
}

export default musicProcess;