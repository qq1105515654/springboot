package com.extend.demo.generator;

import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 代码生成器父类
 */
public class SuperGenerator {


    protected TemplateConfig templateConfig() {
        return new TemplateConfig().setXml(null);
    }

    /**
     * 资源文件的配置
     *
     * @return
     */
    protected InjectionConfig getInjectionConfig() {
        return new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<>();
                this.setMap(map);
            }
        }.setFileOutConfigList(Collections.singletonList(new FileOutConfig("/templates/mapper.xml.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return getResourcePath() + "/mapper/" + tableInfo.getEntityName() + "Mapper.xml";
            }
        }));
    }

    protected PackageConfig getPackageConfig() {
        return new PackageConfig()
                .setParent("com.extend.demo")
                .setController("controller")
                .setEntity("domain.entity")
                .setMapper("dao.mapper")
                .setService("manager")
                .setServiceImpl("manager.impl");
    }

    protected StrategyConfig getStrategyConfig() {
        return new StrategyConfig()
                .setCapitalMode(false)//全局大写命名
                .setNaming(NamingStrategy.underline_to_camel)//表名生成策略
                //自定义实体类的父类
                .setSuperEntityClass("com.extend.demo.common.entity.BaseEntity")
                //公共字段
                .setSuperEntityColumns("id");
    }

    /**
     * 获取资源文件路劲
     *
     * @return
     */
    protected String getResourcePath() {
        String resourcePaht = getResourcePath() + "/src/main/resources";
        System.err.println("Generator Resource PATH：【 " + resourcePaht + " 】");
        return resourcePaht;
    }

    /**
     * 获取项目根路径
     *
     * @return
     */
    protected String getRootPath() {
        String file = Objects.requireNonNull(SuperGenerator.class.getClassLoader().getResource("")).getFile();
        return new File(file).getParentFile().getParentFile().getParent();
    }

    /**
     * 获取项目Java 路径
     *
     * @return
     */
    protected String getJavaPath() {
        String javaPath = getRootPath() + "/src/main/java";
        System.err.println("Generator Resource PATH：【 " + javaPath + " 】");
        return javaPath;
    }

}
