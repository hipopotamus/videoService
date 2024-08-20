INSERT INTO account(account_id, email, password, nickname, gender, birth_date, role, created_at, updated_at, deleted)
VALUES('10001', 'test1@test.com', '$2a$10$.s16a34pwL.M9NksIVSkIOasWPPsBB39blD1lOqinqhzoR7ri84d.', 'testNickname1', 'MALE', '1993-12-22', 'USER', '2024-08-13 10:00:01', '2024-08-13 10:00:01', false);
INSERT INTO account(account_id, email, password, nickname, gender, birth_date, role, created_at, updated_at, deleted)
VALUES('10002', 'test2@test.com', '$2a$10$.s16a34pwL.M9NksIVSkIOasWPPsBB39blD1lOqinqhzoR7ri84d.', 'testNickname2', 'MALE', '1993-12-22', 'USER', '2024-08-13 10:00:01', '2024-08-13 10:00:01', false);

INSERT INTO total_weight(total_weight_id, total_weight)
VALUES ('10001', 90000);

INSERT INTO video(video_id, url, length, created_at, updated_at, deleted)
VALUES ('10001', 'testVideoUrl1', 901, '2024-08-13 10:00:01', '2024-08-13 10:00:01', false);
INSERT INTO video(video_id, url, length, created_at, updated_at, deleted)
VALUES ('10002', 'testVideoUrl2', 901, '2024-08-13 10:00:01', '2024-08-13 10:00:01', false);
INSERT INTO video(video_id, url, length, created_at, updated_at, deleted)
VALUES ('10003', 'testVideoUrl3', 901, '2024-08-13 10:00:01', '2024-08-13 10:00:01', false);
INSERT INTO video(video_id, url, length, created_at, updated_at, deleted)
VALUES ('10004', 'testVideoUrl4', 901, '2024-08-13 10:00:01', '2024-08-13 10:00:01', false);
INSERT INTO video(video_id, url, length, created_at, updated_at, deleted)
VALUES ('10005', 'testVideoUrl5', 901, '2024-08-13 10:00:01', '2024-08-13 10:00:01', false);

INSERT INTO board(board_id, account_id, video_id, title, content, views, ad_views, total_playtime, adurls, ad_times, created_at, updated_at, deleted)
VALUES ('10001', '10001', '10001', 'boardTitle1', 'boardContent1', 10, 0, 300, '["http://localhost:8080/videoFiles/ad1.mp4","http://localhost:8080/videoFiles/ad4.mp4","http://localhost:8080/videoFiles/ad4.mp4"]', '[300,600,900]', '2024-08-13 10:00:01', '2024-08-13 10:00:01', false);
INSERT INTO board(board_id, account_id, video_id, title, content, views, ad_views, total_playtime, adurls, ad_times, created_at, updated_at, deleted)
VALUES ('10002', '10001', '10002', 'boardTitle2', 'boardContent2', 20, 3, 700, '["http://localhost:8080/videoFiles/ad1.mp4","http://localhost:8080/videoFiles/ad4.mp4","http://localhost:8080/videoFiles/ad4.mp4"]', '[300,600,900]', '2024-08-13 10:00:01', '2024-08-13 10:00:01', false);
INSERT INTO board(board_id, account_id, video_id, title, content, views, ad_views, total_playtime, adurls, ad_times, created_at, updated_at, deleted)
VALUES ('10003', '10001', '10003', 'boardTitle3', 'boardContent3', 30, 4, 900, '["http://localhost:8080/videoFiles/ad1.mp4","http://localhost:8080/videoFiles/ad4.mp4","http://localhost:8080/videoFiles/ad4.mp4"]', '[300,600,900]', '2024-08-13 10:00:01', '2024-08-13 10:00:01', false);
INSERT INTO board(board_id, account_id, video_id, title, content, views, ad_views, total_playtime, adurls, ad_times, created_at, updated_at, deleted)
VALUES ('10004', '10002', '10004', 'boardTitle4', 'boardContent4', 40, 5, 1300, '["http://localhost:8080/videoFiles/ad1.mp4","http://localhost:8080/videoFiles/ad4.mp4","http://localhost:8080/videoFiles/ad4.mp4"]', '[300,600,900]', '2024-08-13 10:00:01', '2024-08-13 10:00:01', false);
INSERT INTO board(board_id, account_id, video_id, title, content, views, ad_views, total_playtime, adurls, ad_times, created_at, updated_at, deleted)
VALUES ('10005', '10002', '10005', 'boardTitle5', 'boardContent5', 50, 6, 1500, '["http://localhost:8080/videoFiles/ad1.mp4","http://localhost:8080/videoFiles/ad4.mp4","http://localhost:8080/videoFiles/ad4.mp4"]', '[300,600,900]', '2024-08-13 10:00:01', '2024-08-13 10:00:01', false);

INSERT INTO ad_video(ad_video_id, url, advertiser, weight, created_at, updated_at, deleted)
VALUES ('10001', 'http://localhost:8080/videoFiles/ad1.mp4', 'advertiser1', '10000', '2024-08-13 10:00:01', '2024-08-13 10:00:01', false);
INSERT INTO ad_video(ad_video_id, url, advertiser, weight, created_at, updated_at, deleted)
VALUES ('10002', 'http://localhost:8080/videoFiles/ad2.mp4', 'advertiser2', '10000', '2024-08-13 10:00:01', '2024-08-13 10:00:01', false);
INSERT INTO ad_video(ad_video_id, url, advertiser, weight, created_at, updated_at, deleted)
VALUES ('10003', 'http://localhost:8080/videoFiles/ad3.mp4', 'advertiser3', '10000', '2024-08-13 10:00:01', '2024-08-13 10:00:01', false);
INSERT INTO ad_video(ad_video_id, url, advertiser, weight, created_at, updated_at, deleted)
VALUES ('10004', 'http://localhost:8080/videoFiles/ad4.mp4', 'advertiser4', '50000', '2024-08-13 10:00:01', '2024-08-13 10:00:01', false);
INSERT INTO ad_video(ad_video_id, url, advertiser, weight, created_at, updated_at, deleted)
VALUES ('10005', 'http://localhost:8080/videoFiles/ad5.mp4', 'advertiser5', '10000', '2024-08-13 10:00:01', '2024-08-13 10:00:01', false);

