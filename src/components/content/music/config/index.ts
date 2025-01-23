const musicContext = {
    lrcApiUrl: `${process.env.VITE_API_DOMAIN}/music/getMusicLrc?id=`,
}

const aplayerConfig = {
    fixed: false,
    mini: false,
    autoplay: false,
    loop: "none",
    order: "list",
    preload: "auto",
    volume: 0.7,
    lrcType: 3,
    listFolded: false,
    listMaxHeight: '60vh'
}

export { musicContext, aplayerConfig };