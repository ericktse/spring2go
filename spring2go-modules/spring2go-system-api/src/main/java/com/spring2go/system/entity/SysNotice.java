package com.spring2go.system.entity;

import com.spring2go.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * 系统通知
 *
 * @author xiaobin
 */
@Data
public class SysNotice extends BaseEntity {
    /**
     * 公告ID
     */
    private Long noticeId;

    /**
     * 公告标题
     */
    private String noticeTitle;

    /**
     * 公告类型（1通知 2公告）
     */
    private String noticeType;

    /**
     * 公告内容
     */
    private String noticeContent;

    /**
     * 公告状态（0正常 1关闭）
     */
    private String status;
}
