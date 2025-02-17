-- ========================================
-- 🐾 코드 그룹 및 코드 상세 초기화 (PostgreSQL)
-- ========================================

-- 🚨 1️⃣ 기존 데이터 삭제
TRUNCATE TABLE code_detail RESTART IDENTITY CASCADE;
TRUNCATE TABLE code_group RESTART IDENTITY CASCADE;

-- 🚀 2️⃣ 코드 그룹 (code_group) 삽입
INSERT INTO public.code_group (group_id, group_name, description) VALUES
                                                                      ('USER_ROLE', '유저 역할', '일반 사용자, 관리자 구분 코드'),
                                                                      ('CTG_DOG', '강아지', '강아지 품종 구분 코드'),
                                                                      ('CTG_CAT', '고양이', '고양이 품종 구분 코드');

-- 🚀 3️⃣ 유저 역할 코드 (USER_ROLE)
INSERT INTO public.code_detail (code_detail_id, group_id, code_detail_name, code_detail_value, sort_order, is_active)
VALUES
    ('ADMIN', 'USER_ROLE', '관리자', 'A', 1, true),
    ('USER', 'USER_ROLE', '일반 사용자', 'U', 2, true);

-- 🚀 4️⃣ 강아지 품종 코드 (CTG_DOG)
INSERT INTO public.code_detail (code_detail_id, group_id, code_detail_name, code_detail_value, sort_order, is_active)
VALUES
    ('LABRADOR', 'CTG_DOG', '래브라도 리트리버', 'LAB', 1, true),
    ('POODLE', 'CTG_DOG', '푸들', 'POO', 2, true),
    ('BULLDOG', 'CTG_DOG', '불독', 'BUL', 3, true),
    ('SHIBA', 'CTG_DOG', '시바 이누', 'SHI', 4, true),
    ('GOLDEN_RETRIEVER', 'CTG_DOG', '골든 리트리버', 'GRT', 5, true),
    ('POMERANIAN', 'CTG_DOG', '포메라니안', 'POM', 6, true),
    ('BEAGLE', 'CTG_DOG', '비글', 'BEA', 7, true),
    ('JINDO', 'CTG_DOG', '진돗개', 'JIN', 8, true);

-- 🚀 5️⃣ 고양이 품종 코드 (CTG_CAT)
INSERT INTO public.code_detail (code_detail_id, group_id, code_detail_name, code_detail_value, sort_order, is_active)
VALUES
    ('PERSIAN', 'CTG_CAT', '페르시안', 'PER', 1, true),
    ('SIAMESE', 'CTG_CAT', '샴', 'SIA', 2, true),
    ('MAINE_COON', 'CTG_CAT', '메인쿤', 'MCO', 3, true),
    ('BENGAL', 'CTG_CAT', '뱅갈', 'BEN', 4, true),
    ('RAGDOLL', 'CTG_CAT', '래그돌', 'RAG', 5, true),
    ('SCOTTISH_FOLD', 'CTG_CAT', '스코티시 폴드', 'SCF', 6, true),
    ('SPHYNX', 'CTG_CAT', '스핑크스', 'SPH', 7, true),
    ('KOREAN_SHORTHAIR', 'CTG_CAT', '코리안 숏헤어', 'KSH', 8, true);

-- ✅ 완료 메시지
DO $$
BEGIN
    RAISE NOTICE '코드 그룹 및 코드 상세 초기화 완료';
END $$;

-- ========================================
-- 🐾 전체 데이터 초기화 및 펫시터 프로필 생성 (PostgreSQL)
-- ========================================

-- 🚨 기존 데이터 삭제 (순서 중요 - FK 제약 고려)
TRUNCATE TABLE available_pet_type RESTART IDENTITY CASCADE;
TRUNCATE TABLE pet_sitter_profile RESTART IDENTITY CASCADE;
TRUNCATE TABLE address RESTART IDENTITY CASCADE;
TRUNCATE TABLE member RESTART IDENTITY CASCADE;

-- 🚀 멤버 데이터 삽입 (5명, 비밀번호는 1234 암호화 값)
INSERT INTO member (email, password, name, contact, status, role_id, created_at, updated_at) VALUES
                                                                                                 ('admin@example.com', '$2a$10$oTxvSqrHbuxIyqBZVHmtn.yLZ3BpH8rFX8w1kZCVT47tjMdZ7ibz.', '관리자', '010-1111-1111', 'ACTIVE', 'ADMIN', now(), now()),
                                                                                                 ('user1@example.com', '$2a$10$oTxvSqrHbuxIyqBZVHmtn.yLZ3BpH8rFX8w1kZCVT47tjMdZ7ibz.', '홍길동', '010-2222-2222', 'ACTIVE', 'USER', now(), now()),
                                                                                                 ('user2@example.com', '$2a$10$oTxvSqrHbuxIyqBZVHmtn.yLZ3BpH8rFX8w1kZCVT47tjMdZ7ibz.', '이영희', '010-3333-3333', 'ACTIVE', 'USER', now(), now()),
                                                                                                 ('user3@example.com', '$2a$10$oTxvSqrHbuxIyqBZVHmtn.yLZ3BpH8rFX8w1kZCVT47tjMdZ7ibz.', '김철수', '010-4444-4444', 'ACTIVE', 'USER', now(), now()),
                                                                                                 ('user4@example.com', '$2a$10$oTxvSqrHbuxIyqBZVHmtn.yLZ3BpH8rFX8w1kZCVT47tjMdZ7ibz.', '박영수', '010-5555-5555', 'ACTIVE', 'USER', now(), now());

-- 🚀 주소 데이터 삽입
INSERT INTO address (region, district, neighborhood, latitude, longitude, detail_address, created_at, updated_at) VALUES
                                                                                                                      ('서울', '강남구', '삼성동', 37.5145, 127.0489, '테헤란로 123', now(), now()),
                                                                                                                      ('서울', '서초구', '반포동', 37.5085, 127.0116, '반포대로 45', now(), now()),
                                                                                                                      ('부산', '해운대구', '우동', 35.1631, 129.1639, '해운대로 30', now(), now()),
                                                                                                                      ('대구', '수성구', '범어동', 35.8573, 128.6283, '범어로 12', now(), now()),
                                                                                                                      ('인천', '연수구', '송도동', 37.3878, 126.6432, '송도대로 100', now(), now());

-- 🚀 펫시터 프로필 삽입
INSERT INTO pet_sitter_profile (name, contact, available_time, price, introduce, experience, member_id, address_id, created_at, updated_at) VALUES
                                                                                                                                                ('홍길동', '010-2222-2222', '09:00-18:00', 20000, '강아지 및 고양이 전문', '3년 경력', 2, 1, now(), now()),
                                                                                                                                                ('이영희', '010-3333-3333', '10:00-19:00', 22000, '고양이 전문가', '2년 경력', 3, 2, now(), now()),
                                                                                                                                                ('김철수', '010-4444-4444', '08:00-17:00', 18000, '다양한 반려동물 경험', '5년 경력', 4, 3, now(), now()),
                                                                                                                                                ('박영수', '010-5555-5555', '11:00-20:00', 21000, '이색 반려동물 전문가', '4년 경력', 5, 4, now(), now()),
                                                                                                                                                ('관리자', '010-1111-1111', '09:00-18:00', 25000, '종합 반려동물 전문가', '10년 경력', 1, 5, now(), now());

-- 🚀 AvailablePetType 데이터 삽입
INSERT INTO available_pet_type (pet_sitter_id, pet_type, created_at, updated_at) VALUES
                                                                                     (1, 'DOG', now(), now()),
                                                                                     (1, 'CAT', now(), now()),
                                                                                     (2, 'CAT', now(), now()),
                                                                                     (2, 'RABBIT', now(), now()),
                                                                                     (3, 'DOG', now(), now()),
                                                                                     (3, 'BIRD', now(), now()),
                                                                                     (3, 'OTHER', now(), now()),
                                                                                     (4, 'REPTILE', now(), now()),
                                                                                     (4, 'TURTLE', now(), now()),
                                                                                     (5, 'FISH', now(), now()),
                                                                                     (5, 'TURTLE', now(), now());

-- ✅ 각 테이블 시퀀스 초기화
SELECT setval('member_id_seq', COALESCE((SELECT MAX(id) + 1 FROM member), 1), false);
SELECT setval('address_id_seq', COALESCE((SELECT MAX(id) + 1 FROM address), 1), false);
SELECT setval('pet_sitter_profile_id_seq', COALESCE((SELECT MAX(id) + 1 FROM pet_sitter_profile), 1), false);
SELECT setval('available_pet_type_id_seq', COALESCE((SELECT MAX(id) + 1 FROM available_pet_type), 1), false);

-- ✅ 펫시터 프로필 및 관련 데이터 삽입 완료

