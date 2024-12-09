import { Ref } from "vue";

interface VideoContextInterface {
    data: Partial<VideoContextDataInterface>;
    init: (cx: Partial<VideoContextDataInterface>) => void
}

interface VideoContextDataInterface {
    id: string;
    title: Ref<string>;
    originalAuthor: string;
    pictureUrl: string;
    originalUrl: string;
    ownerComment: string;
    videoUrl: string;
    views: number;
    createTime: string;
}

const videoContext: VideoContextInterface = {
    data: {},
    init: (cx: any) => {
        Object.assign(videoContext.data, cx);
    },
}

export { videoContext };