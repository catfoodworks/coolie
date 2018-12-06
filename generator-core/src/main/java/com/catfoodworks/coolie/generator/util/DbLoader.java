package com.catfoodworks.coolie.generator.util;

import com.github.abel533.database.Dialect;
import com.github.abel533.database.SimpleDataSource;
import com.github.abel533.utils.DBMetadataUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class DbLoader {

    public static DBMetadataUtils getDBMetadataUtils(Dialect dialect, Config config) {
        SimpleDataSource dataSource = new SimpleDataSource(
                dialect,
                config.toUrl(),
                config.user,
                config.auth
        );
        return new DBMetadataUtils(dataSource);
    }

    public static class Config {

        public String protocol;

        public String host;

        public String port;

        public String schema;

        public String query;

        public String user;

        public String auth;

        public static Config fromMap(Map<String, Object> map) throws IllegalAccessException {
            Config config = new Config();
            Map<String, Field> fieldMap = Arrays.stream(config.getClass().getDeclaredFields())
                    .collect(Collectors.toMap(Field::getName, f -> f));
            for (Map.Entry<String, Field> entry : fieldMap.entrySet()) {
                entry.getValue().set(config, map.get(entry.getKey()));
            }
            return config;
        }

        public String toUrl() {
            String url = String.format("%s//%s:%s/%s", protocol, host, port, schema);
            if (Objects.nonNull(url) && url.trim().length() > 0) {
                url = String.format("%s?%s", url, query);
            }

            return url;
        }
    }

}
