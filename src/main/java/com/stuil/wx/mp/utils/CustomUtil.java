package com.stuil.wx.mp.utils;

import org.nutz.castor.Castors;
import org.nutz.castor.FailToCastObjectException;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.lang.Each;
import org.nutz.lang.Lang;
import org.nutz.lang.Mirror;
import org.nutz.lang.reflect.ReflectTool;

import java.lang.reflect.Field;
import java.util.*;

import static org.nutz.lang.Lang.each;

/**
 * @Description MAP 转  T
 */
public class CustomUtil {

    public static <T> T map2Object(Map<?, ?> src, T t) {
        Mirror<T> mirror = Mirror.me(t);
        for (Field field : mirror.getFields()) {
            Object v = null;
            if (!Lang.isAndroid && field.isAnnotationPresent(Column.class)) {
                String cv = field.getAnnotation(Column.class).value();
                v = src.get(cv);
            }

            if (null == v && src.containsKey(field.getName())) {
                v = src.get(field.getName());
            }

            if (null != v) {
                Class<?> ft = ReflectTool.getGenericFieldType(t.getClass(), field);
                Object vv = null;
                // 集合
                if (v instanceof Collection) {
                    Collection c = (Collection) v;
                    // 集合到数组
                    if (ft.isArray()) {
                        vv = Lang.collection2array(c, ft.getComponentType());
                    }
                    // 集合到集合
                    else {
                        // 创建
                        Collection newCol;
                        //Class eleType = Mirror.getGenericTypes(field, 0);
                        Class<?> eleType = ReflectTool.getParameterRealGenericClass(t.getClass(),
                                field.getGenericType(), 0);
                        if (ft == List.class) {
                            newCol = new ArrayList(c.size());
                        } else if (ft == Set.class) {
                            newCol = new LinkedHashSet();
                        } else {
                            try {
                                newCol = (Collection) ft.newInstance();
                            } catch (Exception e) {
                                throw Lang.wrapThrow(e);
                            }
                        }
                        // 赋值
                        for (Object ele : c) {
                            newCol.add(Castors.me().castTo(ele, eleType));
                        }
                        vv = newCol;
                    }
                }
                // Map
                else if (v instanceof Map && Map.class.isAssignableFrom(ft)) {
                    // 创建
                    final Map map;
                    // Map 接口
                    if (ft == Map.class) {
                        map = new HashMap();
                    }
                    // 自己特殊的 Map
                    else {
                        try {
                            map = (Map) ft.newInstance();
                        } catch (Exception e) {
                            throw new FailToCastObjectException("target type fail to born!", e);
                        }
                    }
                    // 赋值
                    final Class<?> keyType = ReflectTool.getParameterRealGenericClass(t.getClass(),
                            field.getGenericType(), 0);
                    final Class<?> valType = ReflectTool.getParameterRealGenericClass(t.getClass(),
                            field.getGenericType(), 1);
                    each(v, new Each<Map.Entry>() {
                        @Override
                        public void invoke(int i, Map.Entry en, int length) {
                            map.put(Castors.me().castTo(en.getKey(), keyType),
                                    Castors.me().castTo(en.getValue(), valType));
                        }
                    });
                    vv = map;
                }
                // 强制转换
                else {
                    vv = Castors.me().castTo(v, ft);
                }
                mirror.setValue(t, field, vv);
            }
        }
        return t;
    }
}
