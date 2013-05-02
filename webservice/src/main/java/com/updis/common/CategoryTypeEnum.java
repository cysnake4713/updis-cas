package com.updis.common;

/**
 * Created with IntelliJ IDEA.
 * User: Zhou Guangwen
 * Date: 4/3/13
 * Time: 8:42 PM
 * To change this template use File | Settings | File Templates.
 */
public enum CategoryTypeEnum {
    NOTICE("通知", 1),
    BID("招投标信息", 2),
    FREE_TALK("畅所欲言", 3),
    SPARE_TIME_LIFE("业余生活", 4);
    private String name;
    private Integer categoryTypeId;

    private CategoryTypeEnum(String name, Integer categoryTypeId) {
        this.name = name;
        this.categoryTypeId = categoryTypeId;
    }

    public static CategoryTypeEnum getByCategoryTypeId(Integer categoryTypeId) {
        for (CategoryTypeEnum categoryTypeEnum : values()) {
            if (categoryTypeEnum.getCategoryTypeId().equals(categoryTypeId)) {
                return categoryTypeEnum;
            }
        }
        throw new IllegalArgumentException("Category type not recognized.");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCategoryTypeId() {
        return categoryTypeId;
    }

    public void setCategoryTypeId(Integer categoryTypeId) {
        this.categoryTypeId = categoryTypeId;
    }
}
