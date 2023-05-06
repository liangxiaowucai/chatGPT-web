package com.chat.demo.req;

import lombok.Data;

/**
 * @author lsf
 * @date 2023年05月06日 11:47 AM
 */
@Data
public class InvitationUserReq {
    private String id;

    private String username;

    private String password;

    private String invitationCode;
}
