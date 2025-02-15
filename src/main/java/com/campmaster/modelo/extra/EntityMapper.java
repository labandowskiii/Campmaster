package com.campmaster.modelo.extra;

import java.lang.reflect.Field;
import java.sql.ResultSet;

public class EntityMapper {

    public static <T> T mapRsToClass(ResultSet rs, Class<T> clazz) {
        T obj = null;
        try {
            obj = clazz.getDeclaredConstructor().newInstance();
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                Object value = rs.getObject(field.getName());
                if (value != null) {
                    field.set(obj, value);
                }
            }
            } catch(Exception e){
                e.printStackTrace();
            }
        return obj;
    }
}