package com.frogman.boot.constants;

public class SystemConstants {
    /***
     * menuType
     */
    public static final String MENUTYPE_CATEGORY="M";
    public static final String MENUTYPE_MENU="C";
    public static final String MENUTYPE_BUTTON="F";
    /***
     * delFlag
     */
    public static final String DELETED_TRUE="1";
    public static final String DELETED_FALSE_="0";
    /***
     * status
     */
    public static final String STATUS_NORMAL="0";
    public static final String STATUS_DISABLE="1";

    /***
     * 文章状态
     */
    public static final String ARTICLE_STATUS_DRAFT="1";
    public static final String ARTICLE_STATUS_NORMAL="0";

    /***
     * 分类状态
     */
    public static final String CATEGORY_STATUS_NORMAL="0";
    public static final String CATEGORY_STATUS_ABNORMAL="1";

    /***
     * 友链状态
     */
    public static final String LINK_STATUS_PASSED="0";
    public static final String LINK_STATUS_WAITING_VERIFICATION="1";
    public static final String LINK_STATUS_UNPASSED="2";

    /***
     * 评论查询
     */
    public static final String ARTICLE_COMMENT="0";
    public static final String LINK_COMMENT="1";
    /***
     * 浏览量
     */
    public static final String VIEW_COUNT="viewCount:";

    /***
     * roles
     */
    public static final Long SYS_ADMIN=1L;
}
