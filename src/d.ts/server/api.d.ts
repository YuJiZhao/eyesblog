// 接口返回格式
export declare interface RespInterface {
    code: number;
    msg: string;
    [propName: string]: any;
}

// 统一api管理
export declare interface ApiObject extends 
    SiteInterface, 
    UserInterface, 
    BlogInterface,
    ShuoshuoInterface,
    MusicInterface, 
    VideoInterface,
    JokeInterface,
    AnimeInterface,
    FriendInterface,
    VersionInterface
{}

// 分页
interface PageInterface {
    [page]: number;
    [pageSize]: number;
}

/*
 ***************************************************************************************
 *                                    site
 ***************************************************************************************
 */
export declare interface SiteInterface {
    getContext: () => Promise<RespInterface>;
    getHomeList: (req: GetHomeListReqInterface) => Promise<RespInterface>;
    getAboutContent: () => Promise<RespInterface>;
}

interface GetHomeListReqInterface extends PageInterface {}

/*
 ***************************************************************************************
 *                                    user
 ***************************************************************************************
 */
export declare interface UserInterface {
    getUserInfo: () => Promise<RespInterface>;
}

/*
 ***************************************************************************************
 *                                    blog
 ***************************************************************************************
 */
export declare interface BlogInterface {
    getBlogListInfo: () => Promise<RespInterface>;
    getBlogList:(req: BlogListReqInterface) => Promise<RespInterface>;
    getBlogInfo: (req: Array<any>) => Promise<RespInterface>;
    getBlogCategory: () => Promise<RespInterface>;
    getBlogLabel: () => Promise<RespInterface>;
}

interface BlogListReqInterface extends PageInterface {}

/*
 ***************************************************************************************
 *                                    shuoshuo
 ***************************************************************************************
 */
export declare interface ShuoshuoInterface {
    getShuoshuoList: (req: ShuoshuoListReqInterface) => Promise<RespInterface>;
    getShuoshuoListInfo: () => Promise<RespInterface>;
}

interface ShuoshuoListReqInterface extends PageInterface {}

/*
 ***************************************************************************************
 *                                    music
 ***************************************************************************************
 */
export declare interface MusicInterface {
    getMusicInfo: () => Promise<RespInterface>;
}

/*
 ***************************************************************************************
 *                                    video
 ***************************************************************************************
 */
export declare interface VideoInterface {
    getVideoInfo: () => Promise<RespInterface>;
}

/*
 ***************************************************************************************
 *                                    joke
 ***************************************************************************************
 */
export declare interface JokeInterface {
    getJokeNotice: () => Promise<RespInterface>;
    getJokeList: (req: GetJokeListReqInterface) => Promise<RespInterface>;
}

interface GetJokeListReqInterface extends PageInterface {}

/*
 ***************************************************************************************
 *                                    anime
 ***************************************************************************************
 */

export declare interface AnimeInterface {
    getAnimeNotice: () => Promise<RespInterface>;
    getAnimeListInfo: () => Promise<RespInterface>;
    getAnimeList: (req: GetAnimeListReqInterface) => Promise<RespInterface>;
    getAnimeInfo: (req: Array<any>) => Promise<RespInterface>;
}

interface GetAnimeListReqInterface extends PageInterface {}

/*
 ***************************************************************************************
 *                                    friend
 ***************************************************************************************
 */
export declare interface FriendInterface {
    getFriendListData: () => Promise<RespInterface>;
    getFriendList: () => Promise<RespInterface>;
    getFriendPreamble: () => Promise<RespInterface>;
}

/*
 ***************************************************************************************
 *                                    version
 ***************************************************************************************
 */

export declare interface VersionInterface {
    getVersionInfo: () => Promise<RespInterface>;
    getVersionList: (req: GetVersionListReqInterface) => Promise<RespInterface>;
}

interface GetVersionListReqInterface extends PageInterface {}