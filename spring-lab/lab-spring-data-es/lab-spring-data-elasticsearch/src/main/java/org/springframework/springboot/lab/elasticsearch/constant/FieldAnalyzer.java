package org.springframework.springboot.lab.elasticsearch.constant;

/**
 * ES 字段分析器的枚举类
 *
 * @author K
 */
public class FieldAnalyzer {

    /**
     * IK 最大化分词 会将文本做最细粒度的拆分
     */
    public static final String IK_MAX_WORD = "ik_max_word";

    /**
     * IK 智能分词 会做最粗力度的拆分
     */
    public static final String IK_SMART = "ik_smart";
}
