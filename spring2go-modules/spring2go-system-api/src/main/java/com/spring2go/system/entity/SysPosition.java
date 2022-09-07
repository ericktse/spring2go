package com.spring2go.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.spring2go.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * @Description: 岗位
 * @author: xiaobin
 * @date: 2021-04-08 10:22
 */
@Data
@TableName("sys_post")
public class SysPosition extends BaseEntity {

    /**
     * 岗位序号
     */
    @TableId(type = IdType.AUTO)
    private Long postId;

    /**
     * 岗位编码
     */
    private String postCode;

    /**
     * 岗位名称
     */
    private String postName;

    /**
     * 岗位排序
     */
    private String postSort;

    /**
     * 删除标志（0代表存在 1代表删除）
     */
    @TableLogic
    private String delFlag;
}
