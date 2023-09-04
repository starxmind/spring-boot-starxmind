package com.starxmind.boot.email;

import com.starxmind.boot.email.request.EmailAttachment;
import com.starxmind.boot.email.request.EmailRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * Email sender
 *
 * @author pizzalord
 * @since 1.0
 */
@RequiredArgsConstructor
public class EmailSender {
    /**
     * Java mail sender by spring
     */
    private final JavaMailSender mailSender;

    /**
     * Email sender
     */
    private final String from;

    public void send(EmailRequest request) throws MessagingException {
        // 是否有附件,决定是否要创建一个multipart消息
        boolean hasAttachments = CollectionUtils.isNotEmpty(request.getEmailAttachments());
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, hasAttachments);
        // set mail subject
        helper.setSubject(request.getSubject());
        // set mail text
        helper.setText(request.getContent() == null ? StringUtils.EMPTY : request.getContent(), true);
        // set sender mail address
        helper.setFrom(from);
        // set receiver mail address
        helper.setTo(request.getTo());
        // set carbon copy
        if (ArrayUtils.isNotEmpty(request.getCc())) {
            helper.setCc(request.getCc());
        }
        // set blind carbon copy
        if (ArrayUtils.isNotEmpty(request.getBcc())) {
            helper.setBcc(request.getBcc());
        }
        // set attachments
        if (hasAttachments) {
            for (EmailAttachment emailAttachment : request.getEmailAttachments()) {
                helper.addAttachment(emailAttachment.getName(), emailAttachment.getFile());
            }
        }
        // send mail message
        mailSender.send(mimeMessage);
    }
}
