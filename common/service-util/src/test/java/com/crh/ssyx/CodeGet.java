package com.crh.ssyx;


public class CodeGet {

//    public static void main(String[] args) {
//
//        // 1、创建代码生成器
//        AutoGenerator mpg = new AutoGenerator();
//
//
//        // 2、全局配置
//        // 全局配置
//        //D:\IdeaProject\guigu-ssyx-parent
//        GlobalConfig gc = new GlobalConfig();
//        gc.setOutputDir("D:\\IdeaProject\\guigu-ssyx-parent\\service\\service-activity" + "/src/main/java");
//        gc.setServiceName("%sService");    //去掉Service接口的首字母I
//        gc.setAuthor("crh");
//        gc.setOpen(false);
//        mpg.setGlobalConfig(gc);
//
//        // 3、数据源配置
//        DataSourceConfig dsc = new DataSourceConfig();
//        dsc.setUrl("jdbc:mysql://localhost:3306/shequ-activity?serverTimezone=GMT%2B8&useSSL=false");
//        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
//        dsc.setUsername("root");
//        dsc.setPassword("root");
//        dsc.setDbType(DbType.MYSQL);
//        mpg.setDataSource(dsc);
//
//        // 4、包配置
//        PackageConfig pc = new PackageConfig();
//        pc.setParent("com.crh.ssyx");
//        pc.setModuleName("activity"); //模块名
//        pc.setController("controller");
//        pc.setService("service");
//        pc.setMapper("mapper");
//        mpg.setPackageInfo(pc);
//
//        // 5、策略配置
//        StrategyConfig strategy = new StrategyConfig();
//
//        strategy.setInclude("activity_info", "activity_rule", "activity_sku", "coupon_info", "coupon_range", "coupon_use");
//
//        strategy.setNaming(NamingStrategy.underline_to_camel);//数据库表映射到实体的命名策略
//
//        strategy.setColumnNaming(NamingStrategy.underline_to_camel);//数据库表字段映射到实体的命名策略
//        strategy.setEntityLombokModel(true); // lombok 模型 @Accessors(chain = true) setter链式操作
//
//        strategy.setRestControllerStyle(true); //restful api风格控制器
//        strategy.setControllerMappingHyphenStyle(true); //url中驼峰转连字符
//
//        mpg.setStrategy(strategy);
//
//        // 6、执行
//        mpg.execute();
//    }
}
