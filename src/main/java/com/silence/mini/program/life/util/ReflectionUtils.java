package com.silence.mini.program.life.util;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.lang.reflect.*;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by 00175 on 2015/8/13.
 */
public class ReflectionUtils {

    private static Logger logger = LoggerFactory.getLogger(ReflectionUtils.class);

    /**
     * 通过反射执行Bean中的方法
     *
     * @param bean
     * @param propertyName
     * @param param
     * @throws Exception
     */
    public static void invokeGetterMethodRs(Object bean, String propertyName, Object param) throws Exception {
        String getterMethodName = "get" + StringUtils.capitalize(propertyName);
        Class<? extends Object> clazz = bean.getClass();
        Method meth = clazz.getDeclaredMethod(getterMethodName, Map.class);
        meth.invoke(bean, param);
    }

    /**
     * 通过反射执行Bean中的方法
     *
     * @param bean
     * @param propertyName
     * @throws Exception
     */
    public static void invokeGetterMethodRs(Object bean, String propertyName) throws Exception {
        String getterMethodName = "get" + StringUtils.capitalize(propertyName);
        Class<? extends Object> clazz = bean.getClass();
        Method meth = clazz.getDeclaredMethod(getterMethodName, (Class[]) null);
        meth.invoke(bean);
    }

    /**
     * 通过反射执行Bean中的方法-带参数及返回值
     *
     * @param bean
     * @param propertyName
     * @param param
     * @return
     * @throws Exception
     */
    public static Object invokeGetterMethod(Object bean, String propertyName, Map<String, Object> param) throws Exception {
        String getterMethodName = "get" + StringUtils.capitalize(propertyName);
        Class<? extends Object> clazz = bean.getClass();
        Method meth = clazz.getDeclaredMethod(getterMethodName, Map.class);
        return meth.invoke(bean, param);
    }

    /**
     * 通过反射执行Bean中的方法-带参数及返回值
     *
     * @param bean
     * @param propertyName
     * @param param
     * @return
     * @throws Exception
     */
    public static Object invokeGetterMethod(Object bean, String propertyName, Object param) throws Exception {
        String getterMethodName = "get" + StringUtils.capitalize(propertyName);
        Class<? extends Object> clazz = bean.getClass();
        Method meth = clazz.getDeclaredMethod(getterMethodName, Object.class);
        return meth.invoke(bean, param);
    }

    /**
     * 通过反射执行Bean中的方法-返回值
     *
     * @param bean
     * @param propertyName
     * @return
     * @throws Exception
     */
    public static Object invokeGetterMethod(Object bean, String propertyName) throws Exception {
        String getterMethodName = "get" + StringUtils.capitalize(propertyName);
        Class<? extends Object> clazz = bean.getClass();
        Method meth = clazz.getDeclaredMethod(getterMethodName, (Class[]) null);
        return meth.invoke(bean);
    }

    /**
     * 调用Setter方法.使用value的Class来查找Setter方法.
     */
    public static void invokeSetterMethod(Object obj, String propertyName, Object value) {
        invokeSetterMethod(obj, propertyName, value, null);
    }

    /**
     * 调用Setter方法.
     *
     * @param propertyType 用于查找Setter方法,为空时使用value的Class替代.
     */
    public static void invokeSetterMethod(Object obj, String propertyName, Object value, Class<?> propertyType) {
        Class<?> type = propertyType != null ? propertyType : value.getClass();
        String setterMethodName = "set" + StringUtils.capitalize(propertyName);
        invokeMethod(obj, setterMethodName, new Class[]{type}, new Object[]{value});
    }

    /**
     * 直接读取对象属性值, 无视private/protected修饰符, 不经过getter函数.
     */
    public static Object getFieldValue(final Object obj, final String fieldName) {
        Field field = getAccessibleField(obj, fieldName);

        if (field == null) {
            throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + obj + "]");
        }

        Object result = null;
        try {
            result = field.get(obj);
        } catch (IllegalAccessException e) {
            logger.error("不可能抛出的异常{}", e.getMessage());
        }
        return result;
    }

    /**
     * 直接设置对象属性值, 无视private/protected修饰符, 不经过setter函数.
     */
    public static void setFieldValue(final Object obj, final String fieldName, final Object value) {
        Field field = getAccessibleField(obj, fieldName);

        if (field == null) {
            throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + obj + "]");
        }

        try {
            field.set(obj, value);
        } catch (IllegalAccessException e) {
            logger.error("不可能抛出的异常:{}", e.getMessage());
        }
    }

    /**
     * 获取class中的Field
     *
     * @param clazz
     * @param fieldName
     * @return
     */
    public static Field getAccessibleField(Class<?> clazz, String fieldName) {
        try {
            return clazz.getDeclaredField(fieldName);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 循环向上转型, 获取对象的DeclaredField,   并强制设置为可访问.
     * <p/>
     * 如向上转型到Object仍无法找到, 返回null.
     */
    public static Field getAccessibleField(final Object obj, final String fieldName) {
        Assert.notNull(obj, "object不能为空");
        Assert.hasText(fieldName, "fieldName");
        for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                Field field = superClass.getDeclaredField(fieldName);
                field.setAccessible(true);
                return field;
            } catch (NoSuchFieldException e) {//NOSONAR
                // Field不在当前类定义,继续向上转型
            }
        }
        return null;
    }

    /**
     * 直接调用对象方法, 无视private/protected修饰符.
     * 用于一次性调用的情况.
     */
    public static Object invokeMethod(final Object obj, final String methodName, final Class<?>[] parameterTypes,
                                      final Object[] args) {
        Method method = getAccessibleMethod(obj, methodName, parameterTypes);
        if (method == null) {
            throw new IllegalArgumentException("Could not find method [" + methodName + "] on target [" + obj + "]");
        }

        try {
            return method.invoke(obj, args);
        } catch (Exception e) {
            throw convertReflectionExceptionToUnchecked(e);
        }
    }

    /**
     * 循环向上转型, 获取对象的DeclaredMethod,并强制设置为可访问.
     * 如向上转型到Object仍无法找到, 返回null.
     * <p/>
     * 用于方法需要被多次调用的情况. 先使用本函数先取得Method,然后调用Method.invoke(Object obj, Object... args)
     */
    public static Method getAccessibleMethod(final Object obj, final String methodName,
                                             final Class<?>... parameterTypes) {
        Assert.notNull(obj, "object不能为空");

        for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                Method method = superClass.getDeclaredMethod(methodName, parameterTypes);

                method.setAccessible(true);

                return method;

            } catch (NoSuchMethodException e) {//NOSONAR
                // Method不在当前类定义,继续向上转型
            }
        }
        return null;
    }

    /**
     * 根据FieldName 获取get方法
     *
     * @param clazz
     * @param fieldName
     * @return
     */
    public static Method getAccessibleMethod(Class<?> clazz, String fieldName) {
        try {
            if (clazz.getName().equals("java.lang.Object")) {
                return null;
            }
            String methodname = "get" + StringUtils.capitalize(fieldName);

            Method method = clazz.getDeclaredMethod(methodname);

            return method;
        } catch (NoSuchMethodException e) {
            return getAccessibleMethod(clazz.getSuperclass(), fieldName);
        }
    }

    /**
     * 通过反射, 获得Class定义中声明的父类的泛型参数的类型.
     * 如无法找到, 返回Object.class.
     * eg.
     * public UserDao extends HibernateDao<User>
     *
     * @param clazz The class to introspect
     * @return the first generic declaration, or Object.class if cannot be determined
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <T> Class<T> getSuperClassGenricType(final Class clazz) {
        return getSuperClassGenricType(clazz, 0);
    }

    /**
     * 通过反射, 获得Class定义中声明的父类的泛型参数的类型.
     * 如无法找到, 返回Object.class.
     * <p/>
     * 如public UserDao extends HibernateDao<User,Long>
     *
     * @param clazz clazz The class to introspect
     * @param index the Index of the generic ddeclaration,start from 0.
     * @return the index generic declaration, or Object.class if cannot be determined
     */
    @SuppressWarnings("rawtypes")
    public static Class getSuperClassGenricType(final Class clazz, final int index) {

        Type genType = clazz.getGenericSuperclass();

        if (!(genType instanceof ParameterizedType)) {
            logger.warn(clazz.getSimpleName() + "'s superclass not ParameterizedType");
            return Object.class;
        }

        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

        if (index >= params.length || index < 0) {
            logger.warn("Index: " + index + ", Size of " + clazz.getSimpleName() + "'s Parameterized Type: "
                    + params.length);
            return Object.class;
        }
        if (!(params[index] instanceof Class)) {
            logger.warn(clazz.getSimpleName() + " not set the actual class on superclass generic parameter");
            return Object.class;
        }

        return (Class) params[index];
    }

    /**
     * 将反射时的checked exception转换为unchecked exception.
     */
    public static RuntimeException convertReflectionExceptionToUnchecked(Exception e) {
        if (e instanceof IllegalAccessException || e instanceof IllegalArgumentException
                || e instanceof NoSuchMethodException) {
            return new IllegalArgumentException("Reflection Exception.", e);
        } else if (e instanceof InvocationTargetException) {
            return new RuntimeException("Reflection Exception.", ((InvocationTargetException) e).getTargetException());
        } else if (e instanceof RuntimeException) {
            return (RuntimeException) e;
        }
        return new RuntimeException("Unexpected Checked Exception.", e);
    }

    /**
     * 通过反射，获取实体所有非null的属性，并增加到map中
     *
     * @param modelObj
     * @return
     */
    //public static <T> Map<String, Object> getClassFieldsValues(T entity) {
    //    Map<String, Object> map = new HashMap<String, Object>();
    //    Field[] fields = entity.getClass().getDeclaredFields();
    //    for (Field field : fields) {
    //        Object obj = getFieldValue(entity, field.getName());
    //        if (obj != null && !obj.equals("")) {
    //            if (obj instanceof Date) {
    //                map.put(field.getName(), DateUtils.formatDate((Date) obj, "yyyy/MM/dd"));
    //            } else {
    //                map.put(field.getName(), obj);
    //            }
    //        }
    //    }
    //    return map;
    //}
    public static Map<String, Object> getClassFieldsValues(Object modelObj) {
        Map<String, Object> map = new HashMap<>();
        Field[] fields = modelObj.getClass().getDeclaredFields();
        for (Field field : fields) {
            Object obj = ReflectionUtils.getFieldValue(modelObj, field.getName());
            if (obj != null && !obj.equals("")) {
                String fieldName = field.getName();
                if (isJavaClass(field.getType())) {
                    if (obj instanceof Date) {
                        map.put(fieldName, DateUtils.formatDate((Date) obj, "yyyy-MM-dd HH:mm:ss"));
                    } else {
                        map.put(fieldName, obj);
                    }
                } else {
                    for (Field subField : obj.getClass().getDeclaredFields()) {
                        Object subObj = ReflectionUtils.getFieldValue(obj, subField.getName());
                        if (subObj != null && !subObj.equals("")) {
                            if (subObj instanceof Date) {
                                map.put(fieldName + "." + subField.getName(), DateUtils.formatDate((Date) subObj, "yyyy-MM-dd HH:mm:ss"));
                            } else {
                                map.put(fieldName + "." + subField.getName(), subObj);
                            }
                        }
                    }
                }
            }

        }
        return map;
    }


    /**
     * 获取所有的属性map，包含值为空串的属性
     *
     * @param modelObj
     * @return
     */
    public static Map<String, Object> getClassFields(Object modelObj) {
        Map<String, Object> map = new LinkedHashMap<>();
        Field[] fs = null;
        Field[] fields = getBeanFields(modelObj.getClass(), fs);
        for (Field field : fields) {
            Object obj = ReflectionUtils.getFieldValue(modelObj, field.getName());
            if (obj != null) {
                String fieldName = field.getName();
                if (isJavaClass(field.getType())) {
                    if (obj instanceof Date) {
                        map.put(fieldName, DateUtils.formatDate((Date) obj, "yyyy-MM-dd"));
                    } else {
                        map.put(fieldName, obj);
                    }
                } else {
                    for (Field subField : obj.getClass().getDeclaredFields()) {
                        Object subObj = ReflectionUtils.getFieldValue(obj, subField.getName());
                        if (subObj != null && !subObj.equals("")) {
                            if (subObj instanceof Date) {
                                map.put(fieldName + "." + subField.getName(), DateUtils.formatDate((Date) subObj, "yyyy-MM-dd"));
                            } else {
                                map.put(fieldName + "." + subField.getName(), subObj);
                            }
                        }
                    }
                }
            }

        }
        return map;
    }

    /**
     * 获取类所有属性名及类型
     * @param clazz
     * @return
     */
    public static Map<String, Object> getClassFields(Class<?> clazz) {

        Map<String, Object> map = new LinkedHashMap<>();
        Field[] fs = null;
        Field[] fields = getBeanFields(clazz, fs);
        for (Field field : fields) {
            map.put(field.getName(), field.getType());
        }
        return map;
    }

    /**
     * 获取父类的属性
     *
     * @param cls
     * @param fs
     * @return
     */
    public static Field[] getBeanFields(Class cls, Field[] fs) {
        fs = (Field[]) ArrayUtils.addAll(fs, cls.getDeclaredFields());
        if (cls.getSuperclass() != null) {
            Class clsSup = cls.getSuperclass();
            fs = getBeanFields(clsSup, fs);
        }
        return fs;
    }


    /**
     * 判断当前是否JAVA还是自定义类
     *
     * @param clz
     * @return
     */
    public static boolean isJavaClass(Class<?> clz) {
        return clz != null && clz.getClassLoader() == null;
    }
}
