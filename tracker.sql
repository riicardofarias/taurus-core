/*
 Navicat PostgreSQL Data Transfer

 Source Server         : localhost
 Source Server Type    : PostgreSQL
 Source Server Version : 90423
 Source Host           : localhost:5432
 Source Catalog        : tracker
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 90423
 File Encoding         : 65001

 Date: 20/12/2019 18:57:48
*/


-- ----------------------------
-- Sequence structure for devices_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."devices_id_seq";
CREATE SEQUENCE "public"."devices_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for fence_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."fence_id_seq";
CREATE SEQUENCE "public"."fence_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for position_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."position_id_seq";
CREATE SEQUENCE "public"."position_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Table structure for device
-- ----------------------------
DROP TABLE IF EXISTS "public"."device";
CREATE TABLE "public"."device" (
  "id" int8 NOT NULL DEFAULT nextval('devices_id_seq'::regclass),
  "imei" varchar(180) COLLATE "pg_catalog"."default" NOT NULL,
  "position_id" int8,
  "status" varchar(80) COLLATE "pg_catalog"."default" NOT NULL DEFAULT 'offline'::character varying
)
;

-- ----------------------------
-- Records of device
-- ----------------------------
INSERT INTO "public"."device" VALUES (1, '123456789', 79210, 'offline');

-- ----------------------------
-- Table structure for fence
-- ----------------------------
DROP TABLE IF EXISTS "public"."fence";
CREATE TABLE "public"."fence" (
  "id" int8 NOT NULL DEFAULT nextval('fence_id_seq'::regclass),
  "area" text COLLATE "pg_catalog"."default" NOT NULL,
  "created_at" timestamp(6) NOT NULL DEFAULT now(),
  "device_id" int8 NOT NULL
)
;

-- ----------------------------
-- Records of fence
-- ----------------------------
INSERT INTO "public"."fence" VALUES (1, '[{"latitude":-9.966768725625059,"longitude":-67.85163081251083},{"latitude":-9.968350746520173,"longitude":-67.8509311750531},{"latitude":-9.967488348574008,"longitude":-67.8488951269537},{"latitude":-9.965990363739024,"longitude":-67.84960645716637},{"latitude":-9.966768725625059,"longitude":-67.85163081251083}]', '2019-12-19 20:36:37.573', 1);

-- ----------------------------
-- Table structure for position
-- ----------------------------
DROP TABLE IF EXISTS "public"."position";
CREATE TABLE "public"."position" (
  "id" int8 NOT NULL DEFAULT nextval('position_id_seq'::regclass),
  "latitude" float8 NOT NULL DEFAULT 0,
  "longitude" float8 NOT NULL DEFAULT 0,
  "speed" float4 NOT NULL DEFAULT 0,
  "course" float8 NOT NULL DEFAULT 0,
  "odometer" int4 NOT NULL DEFAULT 0,
  "alarm" varchar(50) COLLATE "pg_catalog"."default",
  "key_ignition" int4 NOT NULL DEFAULT 0,
  "power" float8 NOT NULL DEFAULT 0,
  "battery_level" float8 NOT NULL DEFAULT 0,
  "signal_level" int4 NOT NULL DEFAULT 0,
  "date" timestamp(6) NOT NULL,
  "created_at" timestamp(6) NOT NULL DEFAULT now(),
  "device_id" int8 NOT NULL
)
;

-- ----------------------------
-- Records of position
-- ----------------------------
INSERT INTO "public"."position" VALUES (79205, -19.9680468333333, -43.9550175, 68.32, 90, 1234567890, NULL, 0, 12.345, 90, 31, '2019-12-13 11:26:00.655', '2019-12-20 14:46:31.512', 1);
INSERT INTO "public"."position" VALUES (79206, -9.96716666666667, -67.8503166666667, 68.32, 90, 1234567890, NULL, 0, 12.345, 90, 31, '2019-12-13 11:26:00.743', '2019-12-20 17:46:49.28', 1);
INSERT INTO "public"."position" VALUES (79207, -9.97108816666667, -67.8530745, 68.32, 90, 1234567890, NULL, 0, 12.345, 90, 31, '2019-12-13 11:26:00.131', '2019-12-20 18:07:49.639', 1);
INSERT INTO "public"."position" VALUES (79208, -9.973452, -67.8539346666667, 68.32, 90, 1234567890, NULL, 0, 12.345, 90, 31, '2019-12-13 11:26:00.766', '2019-12-20 18:19:05.25', 1);
INSERT INTO "public"."position" VALUES (79209, -9.967487, -67.8510811666667, 68.32, 90, 1234567890, NULL, 0, 12.345, 90, 31, '2019-12-13 11:26:00.942', '2019-12-20 18:38:53.399', 1);
INSERT INTO "public"."position" VALUES (79210, -9.967487, -67.8510811666667, 68.32, 90, 1234567890, NULL, 0, 12.345, 90, 31, '2019-12-13 11:26:00.989', '2019-12-20 18:45:52.425', 1);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."devices_id_seq"
OWNED BY "public"."device"."id";
SELECT setval('"public"."devices_id_seq"', 2, true);
ALTER SEQUENCE "public"."fence_id_seq"
OWNED BY "public"."fence"."id";
SELECT setval('"public"."fence_id_seq"', 2, true);
ALTER SEQUENCE "public"."position_id_seq"
OWNED BY "public"."position"."id";
SELECT setval('"public"."position_id_seq"', 79211, true);

-- ----------------------------
-- Primary Key structure for table device
-- ----------------------------
ALTER TABLE "public"."device" ADD CONSTRAINT "devices_pk" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table fence
-- ----------------------------
ALTER TABLE "public"."fence" ADD CONSTRAINT "fence_pk" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table position
-- ----------------------------
ALTER TABLE "public"."position" ADD CONSTRAINT "position_pk" PRIMARY KEY ("id");

-- ----------------------------
-- Foreign Keys structure for table fence
-- ----------------------------
ALTER TABLE "public"."fence" ADD CONSTRAINT "fence_device_id_fk" FOREIGN KEY ("device_id") REFERENCES "public"."device" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table position
-- ----------------------------
ALTER TABLE "public"."position" ADD CONSTRAINT "position_devices_id_fk" FOREIGN KEY ("device_id") REFERENCES "public"."device" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
