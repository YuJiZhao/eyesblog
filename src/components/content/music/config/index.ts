const musicContext = {
    lrcApiUrl: `${process.env.VITE_API_DOMAIN}/music/getMusicLrc?id=`,
}

const aplayerConfig = {
    fixed: false,
    mini: false,
    autoplay: false,
    theme: "rgba(64,161,193)",
    loop: "none",
    order: "list",
    preload: "auto",
    volume: 0.7,
    lrcType: 3,
    listFolded: true,
}

export { musicContext, aplayerConfig };