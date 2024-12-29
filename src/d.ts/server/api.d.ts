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
    BookInterface,
    FootprintInterface,
    AnimeInterface,
    FriendInterface,
    VersionInterface
{}

/*
 ***************************************************************************************
 *                                    site
 ***************************************************************************************
 */
export declare interface SiteInterface {
    getContext: () => Promise<RespInterface>;
    getContextItem: (req: any) => Promise<RespInterface>;
    getHomeList: (req: any) => Promise<RespInterface>;
}

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
    getBlogList:(req: any) => Promise<RespInterface>;
    getBlogInfo: (req: Array<any>) => Promise<RespInterface>;
    getBlogCategory: () => Promise<RespInterface>;
    getBlogLabel: () => Promise<RespInterface>;
}

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
    getJokeList: (req: any) => Promise<RespInterface>;
}

/*
 ***************************************************************************************
 *                                    anime
 ***************************************************************************************
 */

export declare interface AnimeInterface {
    getAnimeListInfo: () => Promise<RespInterface>;
    getAnimeList: (req: any) => Promise<RespInterface>;
    getAnimeInfo: (req: Array<any>) => Promise<RespInterface>;
}

/*
 ***************************************************************************************
 *                                    book
 ***************************************************************************************
 */

export declare interface BookInterface {
    getBookListInfo: () => Promise<RespInterface>;
    getBookList: (req: any) => Promise<RespInterface>;
    getBookInfo: (req: Array<any>) => Promise<RespInterface>;
}

/*
 ***************************************************************************************
 *                                    footprint
 ***************************************************************************************
 */


export declare interface FootprintInterface {
    getFootprintList: () => Promise<RespInterface>;
}

/*
 ***************************************************************************************
 *                                    friend
 ***************************************************************************************
 */
export declare interface FriendInterface {
    getFriendListData: () => Promise<RespInterface>;
    getFriendList: () => Promise<RespInterface>;
}

/*
 ***************************************************************************************
 *                                    version
 ***************************************************************************************
 */
export declare interface VersionInterface {
    getVersionInfo: () => Promise<RespInterface>;
    getVersionList: (req: any) => Promise<RespInterface>;
}