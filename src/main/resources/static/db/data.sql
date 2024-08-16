INSERT INTO account(account_id, email, password, nickname, gender, birth_date, role, created_at, updated_at, deleted)
VALUES('1', 'test1@test.com', '$2a$10$.s16a34pwL.M9NksIVSkIOasWPPsBB39blD1lOqinqhzoR7ri84d.', 'testNickname', 'MALE', '1993-12-22', 'USER', '2024-08-13 10:00:01', '2024-08-13 10:00:01', false);

INSERT INTO total_weight(total_weight_id, total_weight)
VALUES ('1', 90000);

INSERT INTO ad_video(ad_video_id, url, advertiser, weight, created_at, updated_at, deleted)
VALUES ('1', 'http://localhost:8080/videoFiles/ad1.mp4', 'advertiser1', '10000', '2024-08-13 10:00:01', '2024-08-13 10:00:01', false);
INSERT INTO ad_video(ad_video_id, url, advertiser, weight, created_at, updated_at, deleted)
VALUES ('2', 'http://localhost:8080/videoFiles/ad2.mp4', 'advertiser2', '10000', '2024-08-13 10:00:01', '2024-08-13 10:00:01', false);
INSERT INTO ad_video(ad_video_id, url, advertiser, weight, created_at, updated_at, deleted)
VALUES ('3', 'http://localhost:8080/videoFiles/ad3.mp4', 'advertiser3', '10000', '2024-08-13 10:00:01', '2024-08-13 10:00:01', false);
INSERT INTO ad_video(ad_video_id, url, advertiser, weight, created_at, updated_at, deleted)
VALUES ('4', 'http://localhost:8080/videoFiles/ad4.mp4', 'advertiser4', '50000', '2024-08-13 10:00:01', '2024-08-13 10:00:01', false);
INSERT INTO ad_video(ad_video_id, url, advertiser, weight, created_at, updated_at, deleted)
VALUES ('5', 'http://localhost:8080/videoFiles/ad5.mp4', 'advertiser5', '10000', '2024-08-13 10:00:01', '2024-08-13 10:00:01', false);

