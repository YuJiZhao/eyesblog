import { publicPath } from "@/router/path";

const urlConfig = {
    siteUrl: process.env.VITE_SITE_URL!,
    resourceUrl: "http://oss.eyescode.top/eyesspace/",
    userResourceUrl: "http://oss.eyescode.top/eyesuser/",
    warehouseUrl: "https://github.com/eyesyeager/eyesspace",
};

const siteConfig = {
    tokenHeader: {
        sToken: "sAuth",
        lToken: "lAuth"
    },
    tokenExpireTime: 30,
    mpThreshold: 800,
    enterURL: "enterURL",
    keepAliveRoute: ["Home", "Blog", "Shuoshuo", "Footprint", "Music", "Video", "Joke", "Anime", "Book", "Product", "Friend", "Version", "About"],
}

const codeConfig = {
    success: 200,
    fail: 400,
    authentication_error: 20000,
    authentication_error_illegal_jwt: 20001,
    authentication_expired: 20002,
    account_freezing: 20003,
    account_not_found: 20004,
    no_time: 2001,
}

const contextConfig = {
    announce: 1,
    about: 8,
    animeNotice: 9,
    friendPreamble: 10,
    jokeNotice: 11,
    bookNotice: 12,
    footprintInit: 13,
    footprintNotice: 14,
    footContext: [16, 17, 19],
    productNotice: 20,
    friendNotice: 21
}

const pathConfig = publicPath;

export { urlConfig, siteConfig, codeConfig, pathConfig, contextConfig }