package com.eyes.eyesspace.tool;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;

import java.sql.Types;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CodeGenerator {
    public static void main(String[] args) {
        String url = "";
        String username = "";
        String password = "";
        List<String> tableNames = Arrays.asList("");

        FastAutoGenerator.create(url, username, password)
                .globalConfig(builder -> {
                    builder.author("eyesYeager") // 设置作者
                            .outputDir("./"); // 指定输出目录
                })
                .dataSourceConfig(builder ->
                        builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                            int typeCode = metaInfo.getJdbcType().TYPE_CODE;
                            if (typeCode == Types.SMALLINT) {
                                // 自定义类型转换
                                return DbColumnType.INTEGER;
                            }
                            return typeRegistry.getColumnType(metaInfo);
                        })
                )
                .packageConfig(builder ->
                        builder.parent("com.eyes.eyesspace.model.entity") // 设置父包名
                                .moduleName("system") // 设置父包模块名
                                .pathInfo(Collections.singletonMap(OutputFile.xml, "./")) // 设置mapperXml生成路径
                )
                .strategyConfig(builder ->
                        builder.addInclude(tableNames) // 设置需要生成的表名
                )
                .execute();
    }
}
