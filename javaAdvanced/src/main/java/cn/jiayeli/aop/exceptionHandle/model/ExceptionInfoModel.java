package cn.jiayeli.aop.exceptionHandle.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String jobName = "null";
    private String className = "unknown";;
    private String methodName = "unknown";;
    private String parameters = "null";;
    private String exceptionType = "unknown";;
    private String exceptionInfo = "null";;
    private String exceptionStackTrace = "null";;
    private String exceptionLocalizedMessage = "unknown";;
    private long time;

}
