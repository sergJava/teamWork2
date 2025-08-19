--liquibase formatted sql

--changeset sfibikh:v1.0-create-tables
CREATE TABLE dynamic_recommendation_rule (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    product_name VARCHAR(255) NOT NULL,
    product_id UUID NOT NULL,
    product_text TEXT NOT NULL,
    hit_count INTEGER DEFAULT 0 NOT NULL
);

CREATE TABLE dynamic_recommendation_query (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    dynamic_recommendation_rule_id BIGINT NOT NULL,
    query_type VARCHAR(50) NOT NULL,
    query_arguments TEXT NOT NULL,
    should_negate BOOLEAN DEFAULT false NOT NULL,
    CONSTRAINT fk_query_rule FOREIGN KEY (dynamic_recommendation_rule_id)
        REFERENCES dynamic_recommendation_rule(id)
);