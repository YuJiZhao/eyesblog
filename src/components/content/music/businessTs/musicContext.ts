interface MusicContextInterface {
    data: Partial<MusicContextDataInterface>;
    init: (cx: Partial<MusicContextDataInterface>) => void
}

interface MusicContextDataInterface {
    id: string;
    title: string;
    author: string;
    url: string;
    pic: string;
    views: number;
}

const musicContext: MusicContextInterface = {
    data: {},
    init: (cx) => {
        Object.assign(musicContext.data, cx);
    },
}

export { musicContext };