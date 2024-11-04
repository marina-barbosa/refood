

-- Inserindo dados na tabela tb_users
INSERT INTO tb_users (date_creation, email, last_login, name, password, phone)
VALUES -- -- as senhas são 123456Bb*
    (NOW(), 'hortencia@email.com', NOW(), 'Hortencia Flores', '$2a$10$neEHRrvLQ5COJvr8rgWxiubCTD19fGjKto88fvlDSO.r2AFkqUp9q', '1399998888' ),
    (NOW(), 'isaaclovehortencia@email.com', NOW(), 'Isaac Bezerra', '$2a$10$neEHRrvLQ5COJvr8rgWxiubCTD19fGjKto88fvlDSO.r2AFkqUp9q', '1399997777' );

-- Inserindo dados na tabela tb_restaurants
INSERT INTO tb_restaurants (
    average_rating, category, cnpj, date_creation, email, fantasy, last_login,
    password, quantity_evaluations, total_evaluations, phone,
    url_banner, url_logo
)VALUES -- -- as senhas são 123456Bb*
    (4.5, 'PADARIA', '42342342300195', NOW(), 'bakeryshop@email.com', 'Bakery Shop', NOW(), 
    '$2a$10$neEHRrvLQ5COJvr8rgWxiubCTD19fGjKto88fvlDSO.r2AFkqUp9q', 1327, 1328, '1308006666',
    'https://firebasestorage.googleapis.com/v0/b/refood-storage.appspot.com/o/banner12.jpg?alt=media&token=e4dd1628-a01d-4e81-8d91-09cb2886217a', 
    'https://firebasestorage.googleapis.com/v0/b/refood-storage.appspot.com/o/logo12.png?alt=media&token=bacd3db9-56d8-46ca-8af1-1e6292854bf6'),
       (4.2, 'RESTAURANTE', '32142342300195', NOW(), 'Bomsabor@email.com', 'Bom Sabor', NOW(),
    '$2a$10$neEHRrvLQ5COJvr8rgWxiubCTD19fGjKto88fvlDSO.r2AFkqUp9q', 1327, 1328, '1308006776',
    'https://firebasestorage.googleapis.com/v0/b/refood-storage.appspot.com/o/banner12.jpg?alt=media&token=e4dd1628-a01d-4e81-8d91-09cb2886217a',
    'https://firebasestorage.googleapis.com/v0/b/refood-storage.appspot.com/o/logo12.png?alt=media&token=bacd3db9-56d8-46ca-8af1-1e6292854bf6'),
    (4.0, 'SUPERMERCADO', '32142342300000', NOW(), 'bh@email.com', 'BH', NOW(),
        '$2a$10$neEHRrvLQ5COJvr8rgWxiubCTD19fGjKto88fvlDSO.r2AFkqUp9q', 1327, 1328, '1309006776',
        'https://firebasestorage.googleapis.com/v0/b/refood-storage.appspot.com/o/banner12.jpg?alt=media&token=e4dd1628-a01d-4e81-8d91-09cb2886217a',
        'https://firebasestorage.googleapis.com/v0/b/refood-storage.appspot.com/o/logo12.png?alt=media&token=bacd3db9-56d8-46ca-8af1-1e6292854bf6');


-- -- Inserindo dados na tabela tb_addresses
INSERT INTO tb_addresses (address_type, cep, complement, district, is_standard, number, state, street, city, type, restaurant_id, user_id)
VALUES
    ('USER', '11045123', 'Apto 101', 'Jardins', TRUE, '12', 'SP', 'Rua Dom', 'Santos', 'Casa', NULL, 1 ),
    ('USER', '11045321', 'Apto 202', 'Centro', FALSE, '45', 'SP', 'Av Pedro Pessoa', 'Santos', 'Trabalho', NULL, 1),
    ('USER', '11045456', 'Apto 103', 'Jardins', TRUE, '56', 'SP', 'Rua Pinheiros', 'Sao Vicente', 'Casa', NULL, 2),
    ('USER', '11045654', 'Apto 203', 'Jardins', FALSE, '03', 'SP', 'Rua Boeto', 'Sao Vicente', 'Casa da Mamae', NULL, 2),
    ('RESTAURANT', '11060470', 'Rua Gastronômica', 'Gonzaga', TRUE, '55', 'SP', 'Rua Tolentino Filgueiras', 'Santos', 'Filial Santos', 1, NULL);

-- -- Inserindo dados na tabela tb_cards
INSERT INTO tb_cards (cpf, cvv, number, validity, holder_name, user_id) VALUES 
('42302050000', '123', '3333111122223333', '12/25', 'Hortencia Flores', 1), 
('42302050000', '244', '3000111122223000', '10/26', 'Hortencia Flores', 1), 
('42402050110', '456', '5173000033335173', '01/26', 'Isaac Bezerra', 2);

-- -- Inserindo dados na tabela tb_contacts
INSERT INTO tb_contacts (description, phone, restaurant_id)
VALUES 
    ('Contato Principal', '1308006633', 1),
    ('Contato Alternativo', '1308006645', 1);

-- -- Inserindo dados na tabela tb_products
INSERT INTO tb_products (active, addition_date, description_product, sell_price, name_product, category, url_img_product, original_price, expiration_date, quantity, restaurant_id)
VALUES 
    (TRUE, NOW(), 'Bolo de cenoura com cobertura de chocolate, delicioso e fresquinho', 15, 'Bolo de Cenoura com Chocolate', 'DOCE', 
    'https://firebasestorage.googleapis.com/v0/b/refood-storage.appspot.com/o/Default_Product_Images.png?alt=media&token=7b5f5565-c02b-4b7b-9cde-9f5170ce8e14', 
    18.0, '2024-11-17', 50, 1),
    (TRUE, NOW(), 'Pão francês crocante por fora e macio por dentro', 1, 'Pão Francês', 'SALGADO', 
    'https://firebasestorage.googleapis.com/v0/b/refood-storage.appspot.com/o/Default_Product_Images.png?alt=media&token=7b5f5565-c02b-4b7b-9cde-9f5170ce8e14', 
    1.5, '2024-11-17', 300, 1),
    (TRUE, NOW(), 'Pastel assado de frango com catupiry', 6, 'Pastel de Frango com Catupiry', 'SALGADO', 
    'https://firebasestorage.googleapis.com/v0/b/refood-storage.appspot.com/o/Default_Product_Images.png?alt=media&token=7b5f5565-c02b-4b7b-9cde-9f5170ce8e14', 
    5.0, '2024-11-17', 150, 1),
    (TRUE, NOW(), 'Biscoito caseiro amanteigado, derrete na boca', 7, 'Biscoito Amanteigado', 'DOCE', 
    'https://firebasestorage.googleapis.com/v0/b/refood-storage.appspot.com/o/Default_Product_Images.png?alt=media&token=7b5f5565-c02b-4b7b-9cde-9f5170ce8e14', 
    8.0, '2024-11-17', 80, 1),
    (TRUE, NOW(), 'Pão doce recheado com creme de baunilha', 3.5, 'Pão Doce com Creme', 'DOCE', 
    'https://firebasestorage.googleapis.com/v0/b/refood-storage.appspot.com/o/Default_Product_Images.png?alt=media&token=7b5f5565-c02b-4b7b-9cde-9f5170ce8e14', 
    4.0, '2024-11-17', 100, 1),
    (TRUE, NOW(), 'Croissant folhado e amanteigado, perfeito para o café da manhã', 6.0, 'Croissant', 'SALGADO', 
    'https://firebasestorage.googleapis.com/v0/b/refood-storage.appspot.com/o/Default_Product_Images.png?alt=media&token=7b5f5565-c02b-4b7b-9cde-9f5170ce8e14', 
    6.0, '2024-11-17', 70, 1),
    (TRUE, NOW(), 'Sonho com recheio de doce de leite', 4.2, 'Sonho de Doce de Leite', 'DOCE', 
    'https://firebasestorage.googleapis.com/v0/b/refood-storage.appspot.com/o/Default_Product_Images.png?alt=media&token=7b5f5565-c02b-4b7b-9cde-9f5170ce8e14', 
    4.5, '2024-11-17', 60, 1),
    (TRUE, NOW(), 'Torta de maçã com massa crocante e recheio cremoso', 12, 'Torta de Maçã', 'DOCE', 
    'https://firebasestorage.googleapis.com/v0/b/refood-storage.appspot.com/o/Default_Product_Images.png?alt=media&token=7b5f5565-c02b-4b7b-9cde-9f5170ce8e14', 
    12.0, '2024-11-17', 30, 1),
    (TRUE, NOW(), 'Empadão de palmito, delicioso e bem temperado', 20, 'Empadão de Palmito', 'SALGADO', 
    'https://firebasestorage.googleapis.com/v0/b/refood-storage.appspot.com/o/Default_Product_Images.png?alt=media&token=7b5f5565-c02b-4b7b-9cde-9f5170ce8e14', 
    20.0, '2024-11-17', 20, 1),
    (TRUE, NOW(), 'Enroladinho de salsicha, ideal para lanches rápidos', 3, 'Enroladinho de Salsicha', 'SALGADO', 
    'https://firebasestorage.googleapis.com/v0/b/refood-storage.appspot.com/o/Default_Product_Images.png?alt=media&token=7b5f5565-c02b-4b7b-9cde-9f5170ce8e14', 
    3.0, '2024-11-17', 200, 1),
    (TRUE, NOW(), 'Rosquinha de coco com açúcar por cima', 3, 'Rosquinha de Coco', 'DOCE', 
    'https://firebasestorage.googleapis.com/v0/b/refood-storage.appspot.com/o/Default_Product_Images.png?alt=media&token=7b5f5565-c02b-4b7b-9cde-9f5170ce8e14', 
    3.5, '2024-11-17', 90, 1),
    (TRUE, NOW(), 'Quiche de queijo com espinafre', 12.9, 'Quiche de Queijo e Espinafre', 'SALGADO', 
    'https://firebasestorage.googleapis.com/v0/b/refood-storage.appspot.com/o/Default_Product_Images.png?alt=media&token=7b5f5565-c02b-4b7b-9cde-9f5170ce8e14', 
    15.0, '2024-11-17', 40, 1),
    --
       (TRUE, NOW(), 'Pão de queijo mineiro, ideal para o café da manhã', 5.0, 'Pão de Queijo', 'SALGADO',
        'https://firebasestorage.googleapis.com/v0/b/refood-storage.appspot.com/o/Default_Product_Images.png?alt=media&token=7b5f5565-c02b-4b7b-9cde-9f5170ce8e14',
        6.0, '2024-11-17', 30, 1),

        (TRUE, NOW(), 'Bolo de fubá com um toque de erva-doce', 12.0, 'Bolo de Fubá', 'DOCE',
        'https://firebasestorage.googleapis.com/v0/b/refood-storage.appspot.com/o/Default_Product_Images.png?alt=media&token=7b5f5565-c02b-4b7b-9cde-9f5170ce8e14',
        15.0, '2024-11-18', 20, 2),

        (TRUE, NOW(), 'Salgado de presunto e queijo com massa crocante', 7.0, 'Enrolado de Presunto e Queijo', 'SALGADO',
        'https://firebasestorage.googleapis.com/v0/b/refood-storage.appspot.com/o/Default_Product_Images.png?alt=media&token=7b5f5565-c02b-4b7b-9cde-9f5170ce8e14',
        8.0, '2024-11-19', 50, 3),

        (TRUE, NOW(), 'Biscoito caseiro de polvilho crocante', 4.0, 'Biscoito de Polvilho', 'MISTO',
        'https://firebasestorage.googleapis.com/v0/b/refood-storage.appspot.com/o/Default_Product_Images.png?alt=media&token=7b5f5565-c02b-4b7b-9cde-9f5170ce8e14',
        5.0, '2024-11-20', 40, 1),

        (TRUE, NOW(), 'Bolo de chocolate com recheio cremoso', 18.0, 'Bolo de Chocolate', 'DOCE',
        'https://firebasestorage.googleapis.com/v0/b/refood-storage.appspot.com/o/Default_Product_Images.png?alt=media&token=7b5f5565-c02b-4b7b-9cde-9f5170ce8e14',
        22.0, '2024-11-21', 15, 2),

        (TRUE, NOW(), 'Sanduíche natural de frango com alface e tomate', 9.0, 'Sanduíche Natural', 'SALGADO',
        'https://firebasestorage.googleapis.com/v0/b/refood-storage.appspot.com/o/Default_Product_Images.png?alt=media&token=7b5f5565-c02b-4b7b-9cde-9f5170ce8e14',
        10.0, '2024-11-22', 35, 3),

        (TRUE, NOW(), 'Torta de morango com chantilly, perfeita para sobremesa', 20.0, 'Torta de Morango', 'DOCE',
        'https://firebasestorage.googleapis.com/v0/b/refood-storage.appspot.com/o/Default_Product_Images.png?alt=media&token=7b5f5565-c02b-4b7b-9cde-9f5170ce8e14',
        25.0, '2024-11-30', 25, 1),

        (TRUE, NOW(), 'Empada de frango com tempero especial', 6.5, 'Empada de Frango', 'SALGADO',
        'https://firebasestorage.googleapis.com/v0/b/refood-storage.appspot.com/o/Default_Product_Images.png?alt=media&token=7b5f5565-c02b-4b7b-9cde-9f5170ce8e14',
        7.5, '2024-11-28', 45, 2),

        (TRUE, NOW(), 'Salgadinho de batata recheado com queijo', 5.0, 'Coxinha de Batata com Queijo', 'SALGADO',
        'https://firebasestorage.googleapis.com/v0/b/refood-storage.appspot.com/o/Default_Product_Images.png?alt=media&token=7b5f5565-c02b-4b7b-9cde-9f5170ce8e14',
        6.0, '2024-11-27', 50, 3),

        (TRUE, NOW(), 'Pão francês fresquinho, ideal para o café da manhã', 0.5, 'Pão Francês', 'SALGADO',
            'https://firebasestorage.googleapis.com/v0/b/refood-storage.appspot.com/o/Default_Product_Images.png?alt=media&token=7b5f5565-c02b-4b7b-9cde-9f5170ce8e14',
            0.8, '2024-11-23', 100, 1),

            (TRUE, NOW(), 'Bolo de laranja com pedaços da fruta, ótimo para o lanche', 10.0, 'Bolo de Laranja', 'DOCE',
            'https://firebasestorage.googleapis.com/v0/b/refood-storage.appspot.com/o/Default_Product_Images.png?alt=media&token=7b5f5565-c02b-4b7b-9cde-9f5170ce8e14',
            12.0, '2024-11-25', 20, 2),

            (TRUE, NOW(), 'Coxinha de frango crocante e saborosa', 5.0, 'Coxinha de Frango', 'SALGADO',
            'https://firebasestorage.googleapis.com/v0/b/refood-storage.appspot.com/o/Default_Product_Images.png?alt=media&token=7b5f5565-c02b-4b7b-9cde-9f5170ce8e14',
            6.0, '2024-11-26', 40, 3),

            (TRUE, NOW(), 'Torta salgada de legumes com queijo', 8.0, 'Torta de Legumes', 'MISTO',
            'https://firebasestorage.googleapis.com/v0/b/refood-storage.appspot.com/o/Default_Product_Images.png?alt=media&token=7b5f5565-c02b-4b7b-9cde-9f5170ce8e14',
            10.0, '2024-11-27', 15, 1),

            (TRUE, NOW(), 'Queijo minas frescal, ideal para acompanhar pães', 20.0, 'Queijo Minas', 'MISTO',
            'https://firebasestorage.googleapis.com/v0/b/refood-storage.appspot.com/o/Default_Product_Images.png?alt=media&token=7b5f5565-c02b-4b7b-9cde-9f5170ce8e14',
            25.0, '2024-11-28', 10, 2),

            (TRUE, NOW(), 'Biscoito amanteigado caseiro', 3.5, 'Biscoito Amanteigado', 'DOCE',
            'https://firebasestorage.googleapis.com/v0/b/refood-storage.appspot.com/o/Default_Product_Images.png?alt=media&token=7b5f5565-c02b-4b7b-9cde-9f5170ce8e14',
            4.5, '2024-11-29', 50, 3),

            (TRUE, NOW(), 'Sanduíche de atum com salada', 12.0, 'Sanduíche de Atum', 'SALGADO',
            'https://firebasestorage.googleapis.com/v0/b/refood-storage.appspot.com/o/Default_Product_Images.png?alt=media&token=7b5f5565-c02b-4b7b-9cde-9f5170ce8e14',
            13.0, '2024-11-30', 30, 1),

            (TRUE, NOW(), 'Bolo de milho cremoso, feito com milho fresco', 8.0, 'Bolo de Milho', 'DOCE',
            'https://firebasestorage.googleapis.com/v0/b/refood-storage.appspot.com/o/Default_Product_Images.png?alt=media&token=7b5f5565-c02b-4b7b-9cde-9f5170ce8e14',
            9.0, '2024-12-01', 25, 2),

            (TRUE, NOW(), 'Empadão de palmito com azeitonas', 15.0, 'Empadão de Palmito', 'MISTO',
            'https://firebasestorage.googleapis.com/v0/b/refood-storage.appspot.com/o/Default_Product_Images.png?alt=media&token=7b5f5565-c02b-4b7b-9cde-9f5170ce8e14',
            18.0, '2024-12-02', 12, 3),

            (TRUE, NOW(), 'Pastel de carne crocante e recheado', 4.5, 'Pastel de Carne', 'SALGADO',
            'https://firebasestorage.googleapis.com/v0/b/refood-storage.appspot.com/o/Default_Product_Images.png?alt=media&token=7b5f5565-c02b-4b7b-9cde-9f5170ce8e14',
            5.5, '2024-12-03', 45, 1),

            (TRUE, NOW(), 'Bolo de coco gelado, ideal para sobremesa', 12.0, 'Bolo de Coco Gelado', 'DOCE',
            'https://firebasestorage.googleapis.com/v0/b/refood-storage.appspot.com/o/Default_Product_Images.png?alt=media&token=7b5f5565-c02b-4b7b-9cde-9f5170ce8e14',
            14.0, '2024-12-04', 20, 2),

            (TRUE, NOW(), 'Croissant de presunto e queijo', 7.0, 'Croissant de Presunto e Queijo', 'SALGADO',
            'https://firebasestorage.googleapis.com/v0/b/refood-storage.appspot.com/o/Default_Product_Images.png?alt=media&token=7b5f5565-c02b-4b7b-9cde-9f5170ce8e14',
            8.5, '2024-12-05', 35, 3),

            (TRUE, NOW(), 'Torrada com manteiga e alho', 2.0, 'Torrada com Manteiga e Alho', 'MISTO',
            'https://firebasestorage.googleapis.com/v0/b/refood-storage.appspot.com/o/Default_Product_Images.png?alt=media&token=7b5f5565-c02b-4b7b-9cde-9f5170ce8e14',
            3.0, '2024-12-06', 60, 1),

            (TRUE, NOW(), 'Torta de frango com requeijão', 16.0, 'Torta de Frango', 'MISTO',
            'https://firebasestorage.googleapis.com/v0/b/refood-storage.appspot.com/o/Default_Product_Images.png?alt=media&token=7b5f5565-c02b-4b7b-9cde-9f5170ce8e14',
            18.0, '2024-12-07', 10, 2),

            (TRUE, NOW(), 'Cupcake de chocolate com cobertura de morango', 6.5, 'Cupcake de Chocolate', 'DOCE',
            'https://firebasestorage.googleapis.com/v0/b/refood-storage.appspot.com/o/Default_Product_Images.png?alt=media&token=7b5f5565-c02b-4b7b-9cde-9f5170ce8e14',
            7.5, '2024-12-08', 40, 3);

-- -- Inserindo dados na tabela tb_cart
INSERT INTO tb_cart (total_value, user_id)
VALUES
    (120.0, 1),
    (75.0, 2);


-- -- Inserindo dados na tabela tb_cart_items
INSERT INTO tb_cart_items (cart_id, product_id, quantity, unit_value, subtotal)
VALUES 
    (1, 1, 2, 20.0, 40.0),
    (1, 2, 1, 30.0, 30.0),
    (2, 1, 1, 20.0, 20.0),
    (2, 2, 2, 30.0, 60.0),
    (1, 4, 1, 20.0, 20.0),
    (1, 3, 3, 5.0, 15.0),
    (2, 5, 2, 4.0, 8.0),
    (2, 4, 1, 8.0, 8.0),
    (1, 5, 2, 1.5, 3.0),
    (1, 6, 4, 6.0, 24.0),
    (1, 9, 3, 3.5, 10.5);

-- -- Inserindo dados na tabela tb_orders
INSERT INTO tb_orders (order_date, order_status, total_value, address_id, restaurant_id, user_id)
VALUES 
    (NOW(), 1, 100.0, 1, 1, 1),
    (NOW(), 2, 50.0, 2, 1, 2),
    (NOW(), 1, 45.0, 3, 1, 1),
    (NOW(), 2, 75.0, 4, 1, 2),
    (NOW(), 1, 30.0, 5, 1, 1);

-- -- Inserindo dados na tabela tb_order_items
INSERT INTO tb_order_items (order_id, product_id, quantity, unit_value, subtotal)
VALUES 
    (1, 1, 2, 20.0, 40.0), -- 2 unidades do Product One no pedido 1
    (1, 2, 1, 30.0, 30.0), -- 1 unidade do Product Two no pedido 1
    (2, 1, 1, 20.0, 20.0), -- 1 unidade do Product One no pedido 2
    (2, 2, 2, 30.0, 60.0), -- 2 unidades do Product Two no pedido 2
    (3, 3, 2, 5.0, 10.0),  -- 2 unidades do Product Three no pedido 3
    (3, 5, 1, 4.0, 4.0),   -- 1 unidade do Product Five no pedido 3
    (4, 1, 1, 20.0, 20.0),  -- 1 unidade do Product One no pedido 4
    (4, 4, 2, 8.0, 16.0),   -- 2 unidades do Product Four no pedido 4
    (5, 6, 3, 6.0, 18.0),   -- 3 unidades do Product Six no pedido 5
    (5, 9, 1, 3.5, 3.5);     -- 1 unidade do Product Nine no pedido 5


-- -- Inserindo dados na tabela tb_reviews
INSERT INTO tb_reviews (rating_comment, rating_date, rating_note, restaurant_id, user_id)
VALUES 
    ('Comida maravilhosa!', NOW(), 5, 1, 1),
    ('Ótimo serviço.', NOW(), 4, 1, 2);

-- -- Inserindo dados na tabela tb_favorites
INSERT INTO tb_favorites (restaurant_id, user_id)
VALUES 
    (1, 1),
    (1, 2);

-- -- Inserindo dados na tabela tb_historical_orders
INSERT INTO tb_historical_orders (date_mod, order_status, order_id, restaurant_id, user_id) 
VALUES 
(NOW(), 'EMPRODUCAO', 1, 1, 1), 
(NOW(), 'ENVIADO', 2, 1, 2); 


-- -- Inserindo dados na tabela tb_notifications
INSERT INTO tb_notifications (msg_notification, msg_read, send_date, restaurant_id, user_id)
VALUES 
    ('Seu pedido foi entregue.', FALSE, NOW(), 1, 1),
    ('novos produtos disponíveis!', TRUE, NOW(), 1, 2);

-- -- Inserindo dados na tabela tb_transactions
INSERT INTO tb_transactions (transaction_date, transaction_status, transaction_value, order_id, card_id) 
VALUES 
(NOW(), 'PENDENTE', 100.0, 1, 1), 
(NOW(), 'APROVADA', 50.0, 2, 2); 



