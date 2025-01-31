CREATE TABLE IF NOT EXISTS summarized_pulse (
    id SERIAL PRIMARY KEY,
    tenant VARCHAR(20),
    product_sku VARCHAR(20),
    use_unity VARCHAR(5),
    summarized_amount DECIMAL(20, 5),
    summarized_date DATE DEFAULT CURRENT_DATE
);
