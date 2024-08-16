INSERT INTO account(account_id, email, password, nickname, gender, birth_date, role, created_at, updated_at, deleted)
VALUES('10001', 'test1@test.com', '$2a$10$.s16a34pwL.M9NksIVSkIOasWPPsBB39blD1lOqinqhzoR7ri84d.', 'testNickname', 'MALE', '1993-12-22', 'USER', '2024-08-13 10:00:01', '2024-08-13 10:00:01', false);

INSERT INTO total_weight(total_weight_id, total_weight)
VALUES ('1000001', 150000);

INSERT INTO video(video_id, url, length, created_at, updated_at, deleted)
VALUES ('30001', 'testVideoUrl1', 901, '2024-08-13 10:00:01', '2024-08-13 10:00:01', false);

INSERT INTO board(board_id, account_id, video_id, title, content, views, ad_views, total_playtime, adurls, ad_times, created_at, updated_at, deleted)
VALUES ('40001', '10001', '30001', 'boardTitle', 'boardContent', 0, 0, 0, '["http://localhost:8080/videoFiles/ad1.mp4","http://localhost:8080/videoFiles/ad4.mp4","http://localhost:8080/videoFiles/ad4.mp4"]', '[300,600,900]', '2024-08-13 10:00:01', '2024-08-13 10:00:01', false);

INSERT INTO ad_video(ad_video_id, url, advertiser, weight, created_at, updated_at, deleted)
VALUES ('20001', 'testURL1', 'testAdvertiser1', 10000, '2024-08-13 10:00:01', '2024-08-13 10:00:01', false);
INSERT INTO ad_video(ad_video_id, url, advertiser, weight, created_at, updated_at, deleted)
VALUES ('20002', 'testURL2', 'testAdvertiser2', 20000, '2024-08-13 10:00:01', '2024-08-13 10:00:01', false);
INSERT INTO ad_video(ad_video_id, url, advertiser, weight, created_at, updated_at, deleted)
VALUES ('20003', 'testURL3', 'testAdvertiser3', 30000, '2024-08-13 10:00:01', '2024-08-13 10:00:01', false);
INSERT INTO ad_video(ad_video_id, url, advertiser, weight, created_at, updated_at, deleted)
VALUES ('20004', 'testURL4', 'testAdvertiser4', 40000, '2024-08-13 10:00:01', '2024-08-13 10:00:01', false);
INSERT INTO ad_video(ad_video_id, url, advertiser, weight, created_at, updated_at, deleted)
VALUES ('20005', 'testURL5', 'testAdvertiser5', 50000, '2024-08-13 10:00:01', '2024-08-13 10:00:01', false);
