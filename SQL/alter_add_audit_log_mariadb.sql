SET NAMES utf8mb4;

USE `quan_ly_nha_thuoc`;

CREATE TABLE IF NOT EXISTS `audit_log` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `audit_code` VARCHAR(20) NOT NULL,
    `user_id` VARCHAR(50) NULL,
    `username` VARCHAR(50) NULL,
    `employee_id` VARCHAR(10) NULL,
    `full_name` VARCHAR(100) NULL,
    `action` VARCHAR(30) NOT NULL,
    `entity_name` VARCHAR(100) NOT NULL,
    `entity_id` VARCHAR(50) NULL,
    `description` TEXT NULL,
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uq_audit_log_code` (`audit_code`),
    KEY `idx_audit_log_created_at` (`created_at`),
    KEY `idx_audit_log_employee_id` (`employee_id`),
    KEY `idx_audit_log_entity` (`entity_name`, `entity_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Bang nay dung de thay trigger audit phu thuoc CONTEXT_INFO.
-- User thao tac se duoc server service truyen ro rang qua UserContext/request.
