package com.slj;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ZsyfTakeOutApplicationTests {

    @Test
    void contextLoads() {
        String[] s = "胡斌凯 张子豪 谢雪瑞 袁艺杰 唐能军 黄岳锋 杨劲涛 钟宇 高哲 何子健 彭鑫 周雅豪 张泽宇 黄富江 唐先享 唐文杰 莫钦辉".split(" ");
        System.out.println("s.length = " + s.length);
    }
}
