package com.starxmind.boot.email.request;

import lombok.*;

import java.util.List;

/**
 * Email request
 *
 * @author pizzalord
 * @since 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Data
public class EmailRequest {
    private String subject; // 邮件标题
    private String content; // 邮件内容
    private String[] to; // 接收者
    private String[] cc; // 抄送
    private String[] bcc; // 暗抄送
    @Singular
    private List<EmailAttachment> emailAttachments; // 附件
}
