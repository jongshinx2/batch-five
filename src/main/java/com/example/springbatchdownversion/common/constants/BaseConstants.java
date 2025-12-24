package com.example.springbatchdownversion.common.constants;

public class BaseConstants {

    // custom objectmapper
    public static final String OBJECT_MAPPER = "objectMapper";
    public static final String CUSTOM_OBJECT_MAPPER = "customObjectMapper";

    // datasource
    public static final String BASE_DATASOURCE = "baseDataSource";
    public static final String DATASOURCE = "dataSource";

    // mybatis beans
    public static final String MYBATIS_BASE_PACKAGE = "com.example.springbatchdownversion.infrastructure.mappers";
    public static final String MYBATIS_ALIAS_PACKAGE = "com.example.springbatchdownversion.domain.model.views";
    public static final String MYBATIS_MAPPER_LOCATION = "classpath*:mappers/**/*.xml";
    public static final String SESSION_FACTORY = "sqlSessionFactory";
    public static final String SESSION_TEMPLATE = "sqlSessionTemplate";

    // jpa beans
    public static final String JPA_BASE_PACKAGE = "com.example.springbatchdownversion.infrastructure.repositories";
    public static final String JPA_ENTITY_PACKAGE = "com.example.springbatchdownversion.domain";
    public static final String ENTITY_MANAGER_FACTORY = "entityManagerFactory";
    public static final String JPA_PERSISTENCE_UNIT = "primaryPersistenceUnit";

    // tx manager
    public static final String MYBATIS_TX_MANAGER = "mybatisTransactionManager";
    public static final String JPA_TX_MANAGER = "jpaTransactionManager";

    public static class CommonURL {
        // jobs
        public static final String JOB_BASE_URL = "/job";
        public static final String JOB_EXECUTION = "/execute";

        // aws s3
        public static final String S3_BASE_URL = "/api/s3";
        public static final String S3_PUT_OBJ = "/create";
        public static final String S3_COPY_OBJ = "/copy";

        // Excel
        public static final String EXCEL_BASE_URL = "/excel";
        public static final String EXCEL_DOWNLOAD = "/download";
        public static final String EXCEL_UPLOAD_SYNC = "/sync/upload";
        public static final String EXCEL_UPLOAD_ASYNC = "/async/upload";
    }

    // aws sdk
    public static final String S3_CLIENT = "s3Client";

    // aws s3 buckets
    public static final String TEST_BUCKET = "tyk-bundle-storage";
}
