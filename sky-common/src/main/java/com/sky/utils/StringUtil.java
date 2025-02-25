package com.sky.utils;

import java.util.List;

/**
 * @author uncle_yumo
 * @fileName StringUtil
 * @createDate 2025/2/25 February
 * @school 无锡学院
 * @studentID 22344131
 * @description
 */

public class StringUtil {
    /**
     * 将id列表转换为字符串，并用括号包裹起来
     * @return 如：“(1,2,3,4)”
     */
    public static String idsToStringWithBracket(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            throw new IllegalArgumentException("ids is empty");
        }
        StringBuilder idsStr = new StringBuilder("(");
        for (Long id : ids) {
            idsStr.append(id).append(",");
        }
        idsStr.deleteCharAt(idsStr.length() - 1);  // 将最后一个逗号去掉
        idsStr.append(")");
        return idsStr.toString();
    }

    /**
     * 将id列表转换为字符串，不用括号包裹
     * @return 如：“1,2,3,4”
     */
    public static String idsToStringWithoutBracket(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            throw new IllegalArgumentException("ids is empty");
        }
        StringBuilder idsStr = new StringBuilder();
        for (Long id : ids) {
            idsStr.append(id).append(",");
        }
        idsStr.deleteCharAt(idsStr.length() - 1);  // 将最后一个逗号去掉
        return idsStr.toString();
    }
}
