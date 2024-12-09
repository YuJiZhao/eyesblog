interface BlogDetailContextInterface {
    data: Partial<BlogDetailContextDataInterface>;
    init: (cx: Partial<BlogDetailContextDataInterface>) => void
}

interface BlogDetailContextDataInterface {
    title: string;
    summary: string;
    content: string;
    category: string;
    labels: Array<string>;
    views: number;
    words: number;
    createTime: string;
}

const blogDetailContext: BlogDetailContextInterface = {
    data: {},
    init: (cx) => {
        Object.assign(blogDetailContext.data, cx);
    },
}

export { blogDetailContext };