package com.sxdzsoft.easyresource.util;

/**
 * @Author YangXiaoDong
 * @Date 2023/6/17 20:21
 * @PackageName:com.sxdzsoft.easyresource.util
 * @ClassName: ToggleCaseUtil
 * @Description: TODO
 * @Version 1.0
 */
public enum ToggleCaseUtil {
    /**
     * 汉字数字"一"对应阿拉伯数字1
     */
    一(1),
    /**
     * 汉字数字"二"对应阿拉伯数字2
     */
    二(2),
    /**
     * 汉字数字"三"对应阿拉伯数字3
     */
    三(3),
    /**
     * 汉字数字"四"对应阿拉伯数字4
     */
    四(4),
    /**
     * 汉字数字"五"对应阿拉伯数字5
     */
    五(5),
    /**
     * 汉字数字"六"对应阿拉伯数字6
     */
    六(6),
    /**
     * 汉字数字"七"对应阿拉伯数字7
     */
    七(7),
    /**
     * 汉字数字"八"对应阿拉伯数字8
     */
    八(8),
    /**
     * 汉字数字"九"对应阿拉伯数字9
     */
    九(9);

    private final int arabicNumber;

    ToggleCaseUtil(int arabicNumber) {
        this.arabicNumber = arabicNumber;
    }

    public int getArabicNumber() {
        return arabicNumber;
    }

    public static int convertChineseToArabic(String chineseNumber) {
        for (ToggleCaseUtil number : ToggleCaseUtil.values()) {
            if (number.name().equals(chineseNumber)) {
                return number.getArabicNumber();
            }
        }
        // 如果输入的汉字数字不在映射范围内，可以设置一个默认值或者抛出异常
        return -1;
    }
}

