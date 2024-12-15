import resource from "./resource";
import { pathConfig } from "@/config/program";

/*
 ***************************************************************************************
 *                                    site
 ***************************************************************************************
 */
const siteContext = {
    clientId: 198344,
    siteName: "耶瞳空间",
    siteNameEn: "eyesSpace",
    spaceVersion: "3.6.0",
    ownerEmail: "eyesyeager@163.com",
    siteVideoBV: "BV1fg411b7sF",
    commentMaxLen: 1000
};

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

// 联系站长弹窗配置
const contactMeConfig = [
    {
        title: "可以发邮件",
        content: siteContext.ownerEmail,
        btnIcon: resource.copy,
        btnWord: "点击复制",
        clickFunc: "copy"
    },
    {
        title: "也可以去B站",
        content: siteContext.siteVideoBV,
        btnIcon: resource.bilibili,
        btnWord: "点击前往",
        clickFunc: "goBilibli"
    },
]

/*
 ***************************************************************************************
 *                                    header
 ***************************************************************************************
 */
const headerConfig = [
    {
        path: pathConfig.home,
        icon: resource.home,
        word: "主页"
    },
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
                word: "梗图"
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
 *                                    footer
 ***************************************************************************************
 */
const footerConfig = {
    copyright: "©2022-至今 By 耶瞳",
    zwfwCode: "赣ICP备2022006255号"
}

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
        title: "服务器好像宕机了 <br> 请稍后重试，或者联系站长",
        items: [
            {
                icon: resource.refresh,
                word: "刷新页面",
                clickFunc: "refreshPage"
            },
            {
                icon: resource.email,
                word: "联系站长",
                clickFunc: "contactMe"
            }
        ],
        process: [false]
    }
}

export { siteContext, userCenterContext, tipType, contactMeConfig, headerConfig, footerConfig, preloadList, errorPath, errorConfig };