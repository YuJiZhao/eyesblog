<?php
namespace app\controller;

class Error
{
    // 空控制器，系统找不到指定的控制器名称时定位到当前类
    public function __call($method, $args)
    {
        return \redirect('https://www.bilibili.com/video/BV1GJ411x7h7');
    }
}
