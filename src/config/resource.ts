import { urlConfig } from "@/config/program";

const sitePrefix = "site/";
const avatarPrefix = "avatar/";
const siteResource = urlConfig.resourceUrl + sitePrefix;

const icon = {
    success: siteResource + "success.png",
    warn: siteResource + "warn.png",
    info: siteResource + "info.png",
    error: siteResource + "error.png",
    fail: siteResource + "fail.png",
    home: siteResource + "home.png",
    blog: siteResource + "blog.png",
    shuoshuo: siteResource + "shuoshuo.png",
    entertainment: siteResource + "entertainment.png",
    music: siteResource + "music.png",
    video: siteResource + "video.png",
    joke: siteResource + "joke.png",
    warehouse: siteResource + "warehouse.png",
    anime: siteResource + "anime.png",
    book: siteResource + "book.png",
    other: siteResource + "other.png",
    friend: siteResource + "friend.png",
    version: siteResource + "version.png",
    about: siteResource + "about.png",
    menu: siteResource + "menu.png",
    before: siteResource + "before.png",
    announce: siteResource + "announce.png",
    data: siteResource + "data.png",
    comment: siteResource + "comment.png",
    refresh: siteResource + "refresh.png",
    email: siteResource + "email.png",
    copy: siteResource + "copy.png",
    bilibili: siteResource + "bilibili.png",
    copyright: siteResource + "copyright.png",
    hide: siteResource + "hide.png",
    fullscreen: siteResource + "fullscreen.png",
    switch: siteResource + "switch.png",
    category: siteResource + "category.png",
    label: siteResource + "label.png",
    catalogue: siteResource + "catalogue.png",
}

const img = {
    errorPage: siteResource + "errorPage.png",
    defaultAvatar: urlConfig.userResourceUrl + avatarPrefix + "space.jpg",
}

const gif = {
    loading: siteResource + "loading.png",
}

const resource = {
    ...icon,
    ...img,
    ...gif,
}

export default resource;