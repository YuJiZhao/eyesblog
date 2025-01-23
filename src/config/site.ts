import resource from "./resource";
import { pathConfig } from "@/config/program";

/*
 ***************************************************************************************
 *                                    UserCenter
 ***************************************************************************************
 */
const userCenterContext = {
    info: "http://user.eyescode.top/",
    auth: "http://user.eyescode.top/OAuth2",
    redirectUrl: "/auth",
}

/*
 ***************************************************************************************
 *                                    popup
 ***************************************************************************************
 */

// 轻提示图标配置
const tipType = [
    resource.success,
    resource.info,
    resource.warn,
    resource.error
];

/*
 ***************************************************************************************
 *                                    header
 ***************************************************************************************
 */
const headerConfig = [
    {
        path: pathConfig.blog,
        icon: resource.blog,
        word: "博客"
    },
    {
        path: pathConfig.shuoshuo,
        icon: resource.shuoshuo,
        word: "说说"
    },
    {
        path: pathConfig.footprint,
        icon: resource.footprint,
        word: "足迹"
    },
    {
        icon: resource.entertainment,
        word: "娱乐",
        children: [
            {
                path: pathConfig.music,
                icon: resource.music,
                word: "音乐"
            },
            {
                path: pathConfig.video,
                icon: resource.video,
                word: "视频"
            },
            {
                path: pathConfig.joke,
                icon: resource.joke,
                word: "趣图"
            }
        ]
    },
    {
        icon: resource.warehouse,
        word: "仓库",
        children: [
            {
                path: pathConfig.anime,
                icon: resource.anime,
                word: "动漫"
            },
            {
                path: pathConfig.book,
                icon: resource.book,
                word: "书单"
            },
            {
                path: pathConfig.product,
                icon: resource.product,
                word: "作品"
            }
        ]
    },
    {
        icon: resource.other,
        word: "其他",
        children: [
            {
                path: pathConfig.friend,
                icon: resource.friend,
                word: "友链"
            },
            {
                path: pathConfig.version,
                icon: resource.version,
                word: "版本"
            },
            {
                path: pathConfig.about,
                icon: resource.about,
                word: "关于",
            }
        ]
    }
];

/*
 ***************************************************************************************
 *                                    preload
 ***************************************************************************************
 */
const preloadList = [
    resource.loading
]

/*
 ***************************************************************************************
 *                                    error
 ***************************************************************************************
 */
const errorPath = pathConfig.errorPath;

const errorConfig = {
    errorRoute: {
        title: "未找到该页面",
        items: [
            {
                icon: resource.before,
                word: "返回前页",
                clickFunc: "goBack"
            },
            {
                icon: resource.home,
                word: "返回首页",
                clickFunc: "goIndex"
            }
        ],
        process: []
    },
    errorContext: {
        title: "服务器好像宕机了 <br> 可刷新页面，或稍后重试",
        items: [],
        process: [false]
    }
}

export { userCenterContext, tipType, headerConfig, preloadList, errorPath, errorConfig };