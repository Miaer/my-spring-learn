package com.user.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author mrliz
 */
@Data
public class UserPo implements Serializable {

    private static final long  serialVersionUID = -1315321515532153132L;

    private Long id;

    private String userName;

    private int level;

    private String note;
}
