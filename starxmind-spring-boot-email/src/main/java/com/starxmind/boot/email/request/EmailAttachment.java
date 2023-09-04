package com.starxmind.boot.email.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;

/**
 * Email attachment
 *
 * @author pizzalord
 * @since 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Data
public class EmailAttachment {
    /**
     * 附件名称
     */
    private String name;

    /**
     * 附件文件
     */
    private File file;
}
