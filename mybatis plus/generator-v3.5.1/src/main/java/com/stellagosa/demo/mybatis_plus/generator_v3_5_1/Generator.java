package com.stellagosa.demo.mybatis_plus.generator_v3_5_1;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DateType;

import java.util.Collections;

/**
 * @Author: Stellagosa
 * @Date: 2021/12/9 11:46
 * @Description: v3.5.1 MyBatis Plus 自动生成器
 */
public class Generator {

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/mybatis_generator?characterEncoding=utf8&serverTimezone=GMT%2B8&useSSL=true";
        String projectPath = System.getProperty("user.dir");//获取项目路径
        FastAutoGenerator.create(url, "root", "123456")
                //全局配置
                .globalConfig(builder -> {
                    builder.author("Stellagosa")
                            .outputDir(projectPath + "/src/main/java")//输出路径
                            .enableSwagger()//开启swagger3
                            .fileOverride()//覆盖文件
                            .disableOpenDir()//不打开文件夹
                            .commentDate("yyyy-MM-dd HH:mm:ss")//date格式
                            .dateType(DateType.TIME_PACK);//localtime
                })
                //包名配置
                .packageConfig(builder -> builder.parent("com.stellagosa.mybatis_plus")
                        .moduleName("generator_v3_5_1")
                        .service("service")
                        .serviceImpl("service.impl")
                        .controller("controller")
                        .entity("entity")
                        .mapper("mapper")
                        //自定义输出路径，mapper.xml生成到resources目录下
                        .pathInfo(Collections.singletonMap(OutputFile.mapperXml, projectPath + "/src/main/resources/mapper")))
                //策略配置
                .strategyConfig(builder ->
                        builder.addExclude("role")//忽略的表
                                .serviceBuilder()
                                .controllerBuilder().enableRestStyle()//restful开启
                                .enableHyphenStyle()//url改变 例如：index_id_1

                                .entityBuilder().enableLombok()//开启lombok
                                .superClass(BasePo.class)
                                .logicDeleteColumnName("deleted")
                                .addSuperEntityColumns("id", "created_by", "created_time", "updated_by", "updated_time")
                )
                // 模板
                .templateConfig(builder -> builder.entity("/templates/entity.java")
                        .service("/templates/service.java")
                        .serviceImpl("/templates/serviceImpl.java")
                        .mapper("/templates/mapper.java")
                        .mapperXml("/templates/mapper.xml")
                        .controller("/templates/controller.java")
                        .build())
                //执行
                .execute();
    }
}
