import { get, post, put, del } from "./ajax";
import { 
    ApiObject, 
    SiteInterface,
    UserInterface,
    ShuoshuoInterface,
    BlogInterface,
    MusicInterface,
    VideoInterface,
    JokeInterface,
    AnimeInterface,
    FriendInterface,
    VersionInterface
} from "@/d.ts/server/api";
import { UrlReqType } from "@/constant";

const site: SiteInterface = {
    getContext: async () => {
        return await get("/context/getContext");
    },
    getHomeList: async (req) => {
        return await get("/home/getHomeList", req);
    },
    getAboutContent: async () => {
        return await get("/context/getAboutContext");
    }
}

const user: UserInterface = {
    getUserInfo: async () => {
        return await get("/user/getUserInfo");
    }
}

const blog: BlogInterface = {
    getBlogListInfo: async () => {
        return await get("/blog/getBlogListInfo");
    },
    getBlogList: async (req) => {
        return await get("/blog/getBlogList", req);
    },
    getBlogInfo: async (req) => {
        return await get("/blog/getBlogInfo", req, UrlReqType.path);
    },
    getBlogCategory: async () => {
        return await get("/blog/getBlogCategory");
    },
    getBlogLabel: async () => {
        return await get("/blog/getBlogLabel");
    },
}

const shuoshuo: ShuoshuoInterface = {
    getShuoshuoList: async (req) => {
        return await get("/shuo/getShuoList", req);
    },
    getShuoshuoListInfo: async () => {
        return await get("/shuo/getShuoListInfo");
    },
}

const music: MusicInterface = {
    getMusicInfo: async () => {
        return await get("/music/getMusicInfo");
    },
}

const video: VideoInterface = {
    getVideoInfo: async () => {
        return await get("/video/getVideoInfo");
    },
}

const joke: JokeInterface = {
    getJokeNotice: async () => {
        return await get("/joke/getJokeNotice");
    },
    getJokeList: async (req) => {
        return await get("/joke/getJokeList", req);
    },
}

const anime: AnimeInterface = {
    getAnimeNotice: async () => {
        return await get("/anime/getAnimeNotice");
    },
    getAnimeListInfo: async () => {
        return await get("/anime/getAnimeListInfo");
    },
    getAnimeList: async (req) => {
        return await get("/anime/getAnimeList", req);
    },
    getAnimeInfo: async (req) => {
        return await get("/anime/getAnimeInfo", req, UrlReqType.path);
    },
}

const friend: FriendInterface = {
    getFriendListData: async () => {
        return await get("/friend/getFriendListData");
    },
    getFriendList: async () => {
        return await get("/friend/getFriendList");
    },
    getFriendPreamble: async () => {
        return await get("/friend/getFriendPreamble");
    }
}


const version: VersionInterface = {
    getVersionInfo: async () => {
        return await get("/version/getVersionInfo");
    },
    getVersionList: async (req) => {
        return await get("/version/getVersionList", req);
    }
}

const api: ApiObject = {
    ...site,
    ...user,
    ...blog,
    ...shuoshuo,
    ...music,
    ...video,
    ...joke,
    ...anime,
    ...friend,
    ...version
}

export default api;