package com.jason.cs353_project;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;


public class generatorCode {
    @Test
    public static void main(String[] args) {
        //我们需要构建一个代码生成器对象
        AutoGenerator mpg = new AutoGenerator();
        //怎么样去执行，配置策略
        //1、全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");//获取当前目录
        gc.setOutputDir(projectPath+"/src/main/java");//输出到哪个目录
        gc.setAuthor("Jason");
        gc.setOpen(false);
        gc.setFileOverride(false);//是否覆盖
        gc.setServiceName("%sService");//去Service的I前缀
        gc.setIdType(IdType.AUTO);
        gc.setDateType(DateType.ONLY_DATE);
        gc.setSwagger2(true);
        mpg.setGlobalConfig(gc);
        //2、设置数据源
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUsername("root");
        dsc.setPassword("xxjjss123321");
        dsc.setUrl("jdbc:mysql://localhost:3306/cs353test?useUnicode=true&characterEncoding=utf-8&useSSL=false&allowPublicKeyRetrieval=true");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setDbType(DbType.MYSQL);
        mpg.setDataSource(dsc);
        //3、包的配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("com.jason");
        pc.setEntity("pojo");
        pc.setMapper("mapper");
        pc.setService("service");
        pc.setController("controller");
        mpg.setPackageInfo(pc);
        //4、策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setInclude("sensor_data_test");//设置要映射的表名,只需改这里即可
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);//是否使用lombok开启注解
        strategy.setLogicDeleteFieldName("deleted");
        //自动填充配置
        TableFill gmtCreate = new TableFill("create_time", FieldFill.INSERT);
        TableFill gmtUpdate = new TableFill("update_time", FieldFill.INSERT_UPDATE);
        ArrayList<TableFill> tableFills = new ArrayList<>();
        tableFills.add(gmtCreate);
        tableFills.add(gmtUpdate);
        strategy.setTableFillList(tableFills);
        //乐观锁配置
        strategy.setVersionFieldName("version");
        strategy.setRestControllerStyle(true);//开启驼峰命名
        strategy.setControllerMappingHyphenStyle(true);//localhost:8080/hello_id_2
        mpg.setStrategy(strategy);
        mpg.execute();//执行
    }
}
