insert into converter (byn, usd, eur, rub, converter_creation_date) values (1, 0.39, 0.32, 35, 1606915346);
insert into converter (byn, usd, eur, rub, converter_creation_date) values (1, 0.36, 0.32, 37, 1606998543);
insert into converter (byn, usd, eur, rub, converter_creation_date) values (1, 0.4, 0.31, 31, 1607021534);
insert into converter (byn, usd, eur, rub, converter_creation_date) values (1, 0.35, 0.30, 31, 1607024000);
insert into converter (byn, usd, eur, rub, converter_creation_date) values (1, 0.32, 0.38, 38, 1607025992);

insert into token (token_id, token_uuid) values (1, "d91as69f-a5c3-4d32-ade3-23afdsaafb12");
insert into token (token_id, token_uuid) values (2, "asfadffd-8adf-4345-8540-cc234aedaf69");
insert into token (token_id, token_uuid) values (3, "d91f2696-67c3-4d32-ade3-23a5a9a0fba4");
insert into token (token_id, token_uuid) values (4, "e81b7947-8296-4ba6-9641-ed15657ec0f6");
insert into token (token_id, token_uuid) values (5, "e92bc9a0-8c82-40a5-8540-cc8821326d69");
insert into token (token_id, token_uuid) values (6, "efa3ee95-28ce-4685-97d0-c0e8a700f88b");
insert into token (token_id, token_uuid) values (7, "cec6576f-988b-450d-b4d8-87ff89462a8b");
insert into token (token_id, token_uuid) values (8, "3e959c5b-8e75-4811-a168-750f2cdb6622");

insert into users (user_email, user_password, user_role, user_status, user_name, user_surname, user_patronymic, user_token_id)
values ("admin@gmail.com", "58b5444cf1b6253a4317fe12daff411a78bda0a95279b1d5768ebf5ca60829e78da944e8a9160a0b6d428cb213e813525a72650dac67b88879394ff624da482f", "ADMIN", "ENABLE", 
"Yan", "Traulko", "Stanislavovich", 1);
insert into users (user_email, user_password, user_role, user_status, user_name, user_surname, user_patronymic, user_token_id)
values ("user@gmail.com", "e531286ad0aca20c9cd144a03979090a9d41955970f3654e231a9a49bce7dfab5275313cea083cc642e9d5519ab2e45ce5fd8cc518ad920d1486a4aa0c5f0f4f", "USER", "ENABLE",
"Olga", "Traulko", "Stanislavovna", 2);

insert into credit_card (credit_card_id, credit_card_number, credit_card_service_end, credit_card_cvv) values (1, 2622899630431793, 1702587600000, 
"06ef5efffa7a98efc9218cc298ee4fe0f5306c60ed8b49660398af832d0f622c4291bc82fa8ca3efcf84586f58e63e8371794bc0c1c5a654c7dd14a222e9240d");
insert into credit_card (credit_card_id, credit_card_number, credit_card_service_end, credit_card_cvv) values (2, 9098401254999781, 1702587600000, 
"227d4d9f49ed790afa1d7c3eb1f884e39a39ed59ea22d7af464a06a271a76d1d1e29fba3c48b2a2ce72d1c2b6242e0e0acf23120e096a7693ec8845c83ce3872");
insert into credit_card (credit_card_id, credit_card_number, credit_card_service_end, credit_card_cvv) values (3, 3955399914952319, 1702587600000, 
"8df740f7584d6cee1d38e4bbb77e8fc2e85fb7294e2fd6820f19d280a6771283bd360ca6296a2307eb65f45a78657c98bef557f36802f1b90fb76ff739bde60f");
insert into credit_card (credit_card_id, credit_card_number, credit_card_service_end, credit_card_cvv) values (4, 3398352213042492, 1702587600000, 
"f82fa92f0b6d2e4b9fe68cc179a5b53de478966f585bbc26e04cea6b936c69323149c9f9694b1620a31df93341efca17444b84f176f460d8011d69abb70e9ee3");
insert into credit_card (credit_card_id, credit_card_number, credit_card_service_end, credit_card_cvv) values (5, 1721638773253642, 1702587600000, 
"b621c14d7802cba525145e0f2abea6cdb178415b230ca23ce27d35e95ecf2afd8b715fd1774f833ab3caba48f38b4acf4600dbc517fd78daf779cb9d66c65acf");
insert into credit_card (credit_card_id, credit_card_number, credit_card_service_end, credit_card_cvv) values (6, 1045894019482999, 1702587600000, 
"14c34e7007372d6c6e05daec4706ed8d4df3ffe0dec73410bb9232d77777890b800bcd826317e98a52c17c7e5dfcb4450caaac4d0a7bebc582eb3366381e391b");

insert into account (account_id, account_creation_date, account_money_amount, account_status, account_user_id, account_token_id, account_credit_card_id)
values (1, 1607979600000, 457, "BLOCKED", 2, 3, 1);
insert into account (account_id, account_creation_date, account_money_amount, account_status, account_user_id, account_token_id, account_credit_card_id)
values (2, 1607979600000, 45000, "CLOSED", 2, 4, 2);
insert into account (account_id, account_creation_date, account_money_amount, account_status, account_user_id, account_token_id, account_credit_card_id)
values (3, 1607979600000, 1234, "CLOSED", 2, 7, 5);
insert into account (account_id, account_creation_date, account_money_amount, account_status, account_user_id, account_token_id, account_credit_card_id)
values (4, 1607979600000, 545, "ENABLE", 2, 5, 3);
insert into account (account_id, account_creation_date, account_money_amount, account_status, account_user_id, account_token_id, account_credit_card_id)
values (5, 1607979600000, 56000, "ENABLE", 2, 6, 4);
insert into account (account_id, account_creation_date, account_money_amount, account_status, account_user_id, account_token_id, account_credit_card_id)
values (6, 1607979600000, 1000, "ENABLE", 2, 8, 6);

insert into transaction_history (transaction_history_id, from_account_id, to_account_id, transaction_history_money_amount, transaction_date) values
(1, 6, 4, 100, 1607979600000);
insert into transaction_history (transaction_history_id, from_account_id, to_account_id, transaction_history_money_amount, transaction_date) values
(2, 4, 6, 10210, 1607979600000);
insert into transaction_history (transaction_history_id, from_account_id, to_account_id, transaction_history_money_amount, transaction_date) values
(3, 4, 6, 120, 1607979600000);
insert into transaction_history (transaction_history_id, from_account_id, to_account_id, transaction_history_money_amount, transaction_date) values
(4, 3, 4, 10011, 1607979600000);