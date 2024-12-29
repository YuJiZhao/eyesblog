import { RouteRecordRaw } from 'vue-router';

const publicPath = {
    home: "/",
    blog: "/blog",
    shuoshuo: "/shuoshuo",
    music: "/music",
    video: "/video",
    joke: "/joke",
    anime: "/anime",
    book: "/book",
    footprint: "/footprint",
    friend: "/friend",
    version: "version",
    about: "/about",
    errorPath: {
        route: "/error/errorRoute",
        context: "/error/errorContext"
    }
}

const common: Array<RouteRecordRaw> = [
    {
        path: "/auth",
        name: "auth",
        component: () => import("@/views/common/Auth.vue"),
    },
    {
        path: "/error/:type",
        name: "errorPage",
        component: () => import("@/views/common/ErrorPage.vue"),
    },
    {
        path: "/:catchAll(.*)",
        redirect: publicPath.errorPath.route
    }
];

const content: Array<RouteRecordRaw> = [
    ...common,
    {
        path: "",
        name: "home",
        component: () => import("@/views/content/Home.vue"),
    },
    {
        path: publicPath.blog,
        children: [
            {
                path: "",
                name: "blog",
                component: () => import("@/views/content/Blog.vue"),
            },
            {
                path: "details/:id",
                name: "blogDetails",
                component: () => import("@/views/content/blog/Details.vue"),
            },
        ]
    },
    {
        path: publicPath.shuoshuo,
        name: "shuoshuo",
        component: () => import("@/views/content/Shuoshuo.vue"),
    },
    {
        path: publicPath.music,
        name: "music",
        component: () => import("@/views/content/Music.vue"),
    },
    {
        path: publicPath.video,
        name: "video",
        component: () => import("@/views/content/Video.vue"),
    },
    {
        path: publicPath.joke,
        name: "joke",
        component: () => import("@/views/content/Joke.vue"),
    },
    {
        path: publicPath.anime,
        children: [
            {
                path: "",
                name: "anime",
                component: () => import("@/views/content/Anime.vue"),
            },
            {
                path: "details/:id",
                name: "animeDetails",
                component: () => import("@/views/content/anime/Details.vue"),
            }
        ]
    },
    {
        path: publicPath.book,
        children: [
            {
                path: "",
                name: "book",
                component: () => import("@/views/content/Book.vue"),
            },
            {
                path: "details/:id",
                name: "bookDetails",
                component: () => import("@/views/content/book/Details.vue"),
            }
        ]
    },
    {
        path: publicPath.footprint,
        children: [
            {
                path: "",
                name: "footprint",
                component: () => import("@/views/content/Footprint.vue"),
            },
            {
                path: "details/:id",
                name: "footprintDetails",
                component: () => import("@/views/content/footprint/Details.vue"),
            }
        ]
    },
    {
        path: publicPath.friend,
        name: "friend",
        component: () => import("@/views/content/Friend.vue"),
    },
    {
        path: publicPath.version,
        name: "version",
        component: () => import("@/views/content/Version.vue"),
    },
    {
        path: publicPath.about,
        name: "about",
        component: () => import("@/views/content/About.vue"),
    }
];

export { publicPath, content };