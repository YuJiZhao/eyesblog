import { SimplifyNumType } from "@/constant";

const utils = {
    // 防抖
    debounce: (fn: () => void, delay = 300) => {
        let timer: NodeJS.Timeout | null = null;
        if (timer) clearTimeout(timer);
        timer = setTimeout(() => {
            fn();
        }, delay);
    },

    // 节流
    throttle: (fn: () => void, delay = 300) => {
        let timer: NodeJS.Timeout | null = null;
        if (timer) return;
        timer = setTimeout(() => {
            fn();
            timer = null;
        }, delay);
    },

    // 设置cookie
    setCookie: (name: string, value: string, exdays: number) => {
        let d = new Date();
        d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
        let expires = "expires=" + d.toUTCString();
        document.cookie = name + "=" + value + "; " + expires + ";path=/;";
    },

    // 获取指定cookie
    getCookie: (name: string) => {
        name = name + "=";
        let ca = document.cookie.split(';');
        for(let i = 0; i < ca.length; i++) {
          let c = ca[i].trim();
          if (c.indexOf(name) == 0) return c.substring(name.length, c.length);
        }
        return "";
    },

    // 删除指定cookie
    delCookie: (name: string) => {
        let exp = new Date();
        exp.setTime(exp.getTime() - 1);
        let cval = utils.getCookie(name);
        document.cookie = name + "="+ cval + ";expires=" + exp.toUTCString() + ";path=/;";
    },

    // 复制内容到剪贴板
    doCopy: (message: string) => {
        if (utils.iosAgent()) {
            let inputObj = document.createElement("input");
            inputObj.value = message;
            document.body.appendChild(inputObj);
            inputObj.select();
            inputObj.setSelectionRange(0, inputObj.value.length);
            document.execCommand('Copy');
            document.body.removeChild(inputObj);
        } else {
            let domObj = document.createElement("span");
            domObj.innerHTML = message;
            document.body.appendChild(domObj);
            let selection = window.getSelection();
            let range = document.createRange();
            range.selectNodeContents(domObj);
            selection!.removeAllRanges();
            selection!.addRange(range);
            document.execCommand('Copy');
            document.body.removeChild(domObj);
        }
    },

    // 估算阅读时长
    estimateReadTime: (words: number) => {
        let readTime = Math.round(words / 400);
        if(readTime < 60) return readTime + "min";
        let h = words / 60 / 400;
        return Math.floor(h) + "h" + Math.floor((words - Math.floor(h) * 60 * 400) / 400) + "min";
    },

    // 检测是否iOS端
    iosAgent: () => {
        return navigator.userAgent.match(/(iPhone|iPod|iPad);?/i);
    },

    // 大数字简写
    simplifyNum: (num: number, type = SimplifyNumType.all) => {
        if(num < 1000) return String(num);
        if(type == SimplifyNumType.exclude_M || num / 1000 < 10) return (num / 1000).toFixed(1) + "K";
        return (num / 10000).toFixed(1) + "M";
    },

    // 对象加密
    encryptionObj: (o: object) => {
        let text = JSON.stringify(o);
        return text.split("").reverse().join("");
    },

    // 对象解密
    decryptObj: (text: string) => {
        let objStr = text.split("").reverse().join("");
        return JSON.parse(objStr);
    }
};

export default utils;