package com.w.reggie;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

/**
 * @author xin
 * @date 2022-07-10-16:44
 */
public class FastAutoGeneratorTest {
    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/reggie?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&useSSL=false&allowPublicKeyRetrieval=true",
                        "root", "123456")
                .globalConfig(builder -> {
                    builder.author("xin") // 设置作者
//                            .enableSwagger() // 开启 swagger 模式
//                            .fileOverride() // 覆盖已生成文件
                            .outputDir("D://java001//reggie//reggie_take_out"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.w") // 设置父包名
                            .moduleName("reggie") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, "D://java001//reggie//reggie_take_out")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("address_book");// 设置需要生成的表名
                    builder.addInclude("category");// 设置需要生成的表名
                    builder.addInclude("dish");// 设置需要生成的表名
                    builder.addInclude("dish_flavor");// 设置需要生成的表名
                    builder.addInclude("employee");// 设置需要生成的表名
                    builder.addInclude("order_detail");// 设置需要生成的表名
                    builder.addInclude("orders");// 设置需要生成的表名
                    builder.addInclude("setmeal");// 设置需要生成的表名
                    builder.addInclude("setmeal_dish");// 设置需要生成的表名
                    builder.addInclude("shopping_cart");// 设置需要生成的表名
                    builder.addInclude("user");// 设置需要生成的表名
//                            .addTablePrefix("t_", "c_"); // 设置过滤表前缀
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();

    }
}
