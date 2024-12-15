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
    BookInterface,
    AnimeInterface,
    FriendInterface,
    VersionInterface
} from "@/d.ts/server/api";
import { UrlReqType } from "@/constant";

const site: SiteInterface = {
    getContext: async () => {
        return await get("/context/getContext");
    },
    getContextItem: async (req) => {
        return await get("/context/getContextItem", req, UrlReqType.path);
    },
    getHomeList: async (req) => {
        return await get("/home/getHomeList", req);
    },
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
    getJokeList: async (req) => {
        return await get("/joke/getJokeList", req);
    },
}

const anime: AnimeInterface = {
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

const book: BookInterface = {
    getBookListInfo: async () => {
        return await get("/book/getBookListInfo");
    },
    getBookList: async (req) => {
        return await get("/book/getBookList", req);
    },
    getBookInfo: async (req) => {
        return await get("/book/getBookInfo", req, UrlReqType.path);
    }
}

const friend: FriendInterface = {
    getFriendListData: async () => {
        return await get("/friend/getFriendListData");
    },
    getFriendList: async () => {
        return await get("/friend/getFriendList");
    },
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
    ...book,
    ...anime,
    ...friend,
    ...version
}

export default api;