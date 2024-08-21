package cn.jiayeli.aop.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * CREATE TABLE exception_info (
 *     id INT AUTO_INCREMENT PRIMARY KEY,
 *     jobName VARCHAR(255) NOT NULL,
 *     className VARCHAR(255) NOT NULL,
 *     methodName VARCHAR(255) NOT NULL,
 *     parameters TEXT,
 *     exceptionType VARCHAR(255) NOT NULL,
 *     exceptionInfo TEXT NOT NULL,
 *     exceptionStackTrace TEXT,
 *     exceptionLocalizedMessage TEXT
 * );
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExceptionInfoModel {

    private long id;
    private String jobName;
    private String className;
    private String methodName;
    private String parameters;
    private String exceptionType;
    private String exceptionInfo;
    private String exceptionStackTrace;
    private String exceptionLocalizedMessage;
    private long time;

}
