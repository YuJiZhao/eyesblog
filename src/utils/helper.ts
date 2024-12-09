import { siteConfig } from "@/config/program";
import CryptoJS from 'crypto-js';
import { SimplifyNumType } from "@/constant";

const utils = {
    // 检测Email是否规范
    checkEmail: (email: string) => {
        return /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(email);
    },

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

    // 加密字符串
    encryption: (str: string) => {
        return CryptoJS.AES.encrypt(
            CryptoJS.enc.Utf8.parse(str), 
            CryptoJS.enc.Utf8.parse(siteConfig.aesKey), 
            {
                mode: CryptoJS.mode.CBC,
                padding: CryptoJS.pad.Pkcs7,
                iv: CryptoJS.enc.Utf8.parse(siteConfig.aesIV)
            }
        ).toString();
    },

    // 获取时间差
    getTimeDisff: (t1: Date, t2: Date) => {
        let millisecond = t1.valueOf() - t2.valueOf();
        let miscod2d = 1000 * 60 * 60 * 24;
        // let miscod2h = 1000 * 60 * 60;
        let day = Math.floor(millisecond / miscod2d);
        // let hour = Math.floor((millisecond - day * miscod2d) / miscod2h);
        // return day + "天" + hour + "小时";
        return day + "天";
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

    // 文本框聚焦
    cursorMove: (el: any, spos: number) => {
        setTimeout(function() {
            el.setSelectionRange(spos, spos);
            el.focus();
        }, 0);
    },

    // 字节转Mb
    byte2MB: (size: number) => {
        return size / (1024 * 1024);
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

    // 获取uuid
    getUUid: () => {
        return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
            var r = Math.random()*16|0, v = c == 'x' ? r : (r&0x3|0x8);
            return v.toString(16);
        });
    }
};

export default utils;