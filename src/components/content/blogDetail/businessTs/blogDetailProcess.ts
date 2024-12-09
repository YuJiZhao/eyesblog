import { ref } from "vue";

const CardList = {
    cardInitLoad: ref(true),
    cardInitFail: ref(false)
};

const blogDetailProcess = {
    ...CardList,
}

export default blogDetailProcess;