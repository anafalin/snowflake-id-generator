--liquibase formatted sql
--changeset lad:2025-12-20 objectQuotingStrategy="QUOTE_ALL_OBJECTS" failOnError: true

-- Создание таблицы app_log
CREATE TABLE app_log (
    guid VARCHAR(50) PRIMARY KEY,
    datacenter_id BIGINT NOT NULL,
    worked_id BIGINT NOT NULL,
    created_at TIMESTAMP
);
