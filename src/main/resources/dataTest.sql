

-- Inserindo dados na tabela tb_users
INSERT INTO tb_users (date_creation, email, last_login, name, password, phone)
VALUES -- -- as senhas são 123456Bb*
    (NOW(), 'user1@example.com', NOW(), 'User One', '$2a$10$neEHRrvLQ5COJvr8rgWxiubCTD19fGjKto88fvlDSO.r2AFkqUp9q', '1112223333' ),
    (NOW(), 'user2@example.com', NOW(), 'User Two', '$2a$10$neEHRrvLQ5COJvr8rgWxiubCTD19fGjKto88fvlDSO.r2AFkqUp9q', '4445556666' );

-- Inserindo dados na tabela tb_restaurants
INSERT INTO tb_restaurants (
    average_rating, category, cnpj, date_creation, email, fantasy,
    last_login, password, quantity_evaluations, total_evaluations,
    url_banner, url_logo, phone
)VALUES -- -- as senhas são 123456Bb*
    (4.5, 'RESTAURANTE', '12345678000195', NOW(), 'restaurant1@example.com', 'Restaurant One', NOW(), '$2a$10$neEHRrvLQ5COJvr8rgWxiubCTD19fGjKto88fvlDSO.r2AFkqUp9q', 10, 50, 'banner1.jpg', 'logo1.jpg', '0800556666'),
    (4.0, 'RESTAURANTE', '98765432000100', NOW(), 'restaurant2@example.com', 'Restaurant Two', NOW(), '$2a$10$neEHRrvLQ5COJvr8rgWxiubCTD19fGjKto88fvlDSO.r2AFkqUp9q', 20, 100, 'banner2.jpg', 'logo2.jpg', '4445550800');

-- Inserindo dados na tabela tb_addresses
INSERT INTO tb_addresses (address_type, cep, complement, district, is_standard, number, state, street, city, type, restaurant_id, user_id)
VALUES
    ('RESTAURANT', '12345678', 'Apto 101', 'Centro', TRUE, '123', 'SP', 'Rua A', 'Aurora do Pará', 'Casa', 1, NULL ),
    ('USER', '87654321', 'Apto 202', 'Jardins', TRUE, '456', 'SP', 'Rua B', 'Capanema', 'Trabalho', NULL, 2);

-- Inserindo dados na tabela tb_cards
INSERT INTO tb_cards (cpf, cvv, number, validity, holder_name, user_id) VALUES 
('40002050000', '123', '4111111111111111', '12/25', 'Nome do Titular 1', 1), 
('11102050110', '456', '5500000000000004', '01/24', 'Nome do Titular 2', 2);

-- Inserindo dados na tabela tb_contacts
INSERT INTO tb_contacts (description, phone, restaurant_id)
VALUES 
    ('Contato Principal', '1112223333', 1),
    ('Contato Alternativo', '4445556666', 2);

-- Inserindo dados na tabela tb_products
INSERT INTO tb_products (active, addition_date, description_product, sell_price, name_product, category, url_img_product, original_price, expiration_date, quantity, restaurant_id)
VALUES 
    (TRUE, NOW(), 'Delicious sweet product', 10.0, 'Product One', 'DOCE', 'http://localhost:8080/images/banner.png', 20.0, '2024-11-17', 100, 1),
    (TRUE, NOW(), 'Tasty savory product', 5.0, 'Product Two', 'SALGADO', 'product2.jpg', 30.0, '2024-11-17', 200, 2);


