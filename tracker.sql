/*
 Navicat Premium Data Transfer

 Source Server         : PostgresSQL
 Source Server Type    : PostgreSQL
 Source Server Version : 130000
 Source Host           : localhost:5432
 Source Catalog        : tracker
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 130000
 File Encoding         : 65001

 Date: 05/04/2021 20:19:59
*/


-- ----------------------------
-- Sequence structure for devices_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."devices_id_seq";
CREATE SEQUENCE "public"."devices_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 2147483647
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for events_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."events_id_seq";
CREATE SEQUENCE "public"."events_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 2147483647
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for groups_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."groups_id_seq";
CREATE SEQUENCE "public"."groups_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 2147483647
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for knex_migrations_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."knex_migrations_id_seq";
CREATE SEQUENCE "public"."knex_migrations_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 2147483647
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for knex_migrations_lock_index_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."knex_migrations_lock_index_seq";
CREATE SEQUENCE "public"."knex_migrations_lock_index_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 2147483647
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for notifications_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."notifications_id_seq";
CREATE SEQUENCE "public"."notifications_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 2147483647
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for positions_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."positions_id_seq";
CREATE SEQUENCE "public"."positions_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 2147483647
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for users_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."users_id_seq";
CREATE SEQUENCE "public"."users_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 2147483647
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for zones_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."zones_id_seq";
CREATE SEQUENCE "public"."zones_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 2147483647
START 1
CACHE 1;

-- ----------------------------
-- Table structure for devices
-- ----------------------------
DROP TABLE IF EXISTS "public"."devices";
CREATE TABLE "public"."devices" (
  "id" int4 NOT NULL DEFAULT nextval('devices_id_seq'::regclass),
  "imei" varchar(255) COLLATE "pg_catalog"."default",
  "status" varchar(255) COLLATE "pg_catalog"."default",
  "created_at" timestamptz(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  "updated_at" timestamptz(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  "position_id" int4,
  "user_id" int4 NOT NULL
)
;
COMMENT ON COLUMN "public"."devices"."id" IS 'ID';
COMMENT ON COLUMN "public"."devices"."imei" IS 'IMEI';
COMMENT ON COLUMN "public"."devices"."status" IS 'Status';
COMMENT ON COLUMN "public"."devices"."position_id" IS 'Última posição do dispositivo';
COMMENT ON COLUMN "public"."devices"."user_id" IS 'Usuário';

-- ----------------------------
-- Records of devices
-- ----------------------------
INSERT INTO "public"."devices" VALUES (3, '235749', 'online', '2021-04-05 15:09:59.806229+00', '2021-04-05 15:09:59.806229+00', 51, 1);

-- ----------------------------
-- Table structure for devices_groups
-- ----------------------------
DROP TABLE IF EXISTS "public"."devices_groups";
CREATE TABLE "public"."devices_groups" (
  "device_id" int4 NOT NULL,
  "group_id" int4 NOT NULL
)
;
COMMENT ON COLUMN "public"."devices_groups"."device_id" IS 'Dispositivo';
COMMENT ON COLUMN "public"."devices_groups"."group_id" IS 'Grupo';

-- ----------------------------
-- Records of devices_groups
-- ----------------------------

-- ----------------------------
-- Table structure for events
-- ----------------------------
DROP TABLE IF EXISTS "public"."events";
CREATE TABLE "public"."events" (
  "id" int4 NOT NULL DEFAULT nextval('events_id_seq'::regclass),
  "speed_limit" int4,
  "event_type" varchar(255) COLLATE "pg_catalog"."default",
  "created_at" timestamptz(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  "updated_at" timestamptz(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  "user_id" int4 NOT NULL,
  "device_id" int4 NOT NULL
)
;
COMMENT ON COLUMN "public"."events"."id" IS 'ID';
COMMENT ON COLUMN "public"."events"."speed_limit" IS 'Limite de velocidade';
COMMENT ON COLUMN "public"."events"."event_type" IS 'Tipo de event';
COMMENT ON COLUMN "public"."events"."user_id" IS 'Usuário';
COMMENT ON COLUMN "public"."events"."device_id" IS 'Dispositivo';

-- ----------------------------
-- Records of events
-- ----------------------------
INSERT INTO "public"."events" VALUES (3, 80, 'OVER_SPEED', '2021-04-06 00:37:16.786601+00', '2021-04-06 00:37:16.786601+00', 1, 3);

-- ----------------------------
-- Table structure for events_zones
-- ----------------------------
DROP TABLE IF EXISTS "public"."events_zones";
CREATE TABLE "public"."events_zones" (
  "event_type" varchar(255) COLLATE "pg_catalog"."default",
  "zone_id" int4 NOT NULL,
  "device_id" int4 NOT NULL
)
;
COMMENT ON COLUMN "public"."events_zones"."event_type" IS 'Tipo de event';
COMMENT ON COLUMN "public"."events_zones"."zone_id" IS 'Zona';
COMMENT ON COLUMN "public"."events_zones"."device_id" IS 'Dispositivo';

-- ----------------------------
-- Records of events_zones
-- ----------------------------
INSERT INTO "public"."events_zones" VALUES ('GEOFENCE_ENTER', 1, 3);

-- ----------------------------
-- Table structure for groups
-- ----------------------------
DROP TABLE IF EXISTS "public"."groups";
CREATE TABLE "public"."groups" (
  "id" int4 NOT NULL DEFAULT nextval('groups_id_seq'::regclass),
  "name" varchar(255) COLLATE "pg_catalog"."default",
  "user_id" int4 NOT NULL
)
;
COMMENT ON COLUMN "public"."groups"."id" IS 'ID';
COMMENT ON COLUMN "public"."groups"."name" IS 'Nome do grupo';
COMMENT ON COLUMN "public"."groups"."user_id" IS 'Usuário';

-- ----------------------------
-- Records of groups
-- ----------------------------

-- ----------------------------
-- Table structure for knex_migrations
-- ----------------------------
DROP TABLE IF EXISTS "public"."knex_migrations";
CREATE TABLE "public"."knex_migrations" (
  "id" int4 NOT NULL DEFAULT nextval('knex_migrations_id_seq'::regclass),
  "name" varchar(255) COLLATE "pg_catalog"."default",
  "batch" int4,
  "migration_time" timestamptz(6)
)
;

-- ----------------------------
-- Records of knex_migrations
-- ----------------------------
INSERT INTO "public"."knex_migrations" VALUES (1, '20210122111730_users.js', 1, '2021-04-05 15:08:09.519+00');
INSERT INTO "public"."knex_migrations" VALUES (2, '20210122111731_groups.js', 1, '2021-04-05 15:08:09.538+00');
INSERT INTO "public"."knex_migrations" VALUES (3, '20210122111732_users_groups.js', 1, '2021-04-05 15:08:09.549+00');
INSERT INTO "public"."knex_migrations" VALUES (4, '20210122111740_zones.js', 1, '2021-04-05 15:08:09.566+00');
INSERT INTO "public"."knex_migrations" VALUES (5, '20210122111753_devices.js', 1, '2021-04-05 15:08:09.586+00');
INSERT INTO "public"."knex_migrations" VALUES (6, '20210122112159_devices_groups.js', 1, '2021-04-05 15:08:09.597+00');
INSERT INTO "public"."knex_migrations" VALUES (7, '20210122112201_events.js', 1, '2021-04-05 15:08:09.616+00');
INSERT INTO "public"."knex_migrations" VALUES (8, '20210122112202_events_zones.js', 1, '2021-04-05 15:08:09.628+00');
INSERT INTO "public"."knex_migrations" VALUES (9, '20210122112502_positions.js', 1, '2021-04-05 15:08:09.654+00');
INSERT INTO "public"."knex_migrations" VALUES (10, '20210122115205_notifications.js', 1, '2021-04-05 15:08:09.674+00');

-- ----------------------------
-- Table structure for knex_migrations_lock
-- ----------------------------
DROP TABLE IF EXISTS "public"."knex_migrations_lock";
CREATE TABLE "public"."knex_migrations_lock" (
  "index" int4 NOT NULL DEFAULT nextval('knex_migrations_lock_index_seq'::regclass),
  "is_locked" int4
)
;

-- ----------------------------
-- Records of knex_migrations_lock
-- ----------------------------
INSERT INTO "public"."knex_migrations_lock" VALUES (1, 0);

-- ----------------------------
-- Table structure for notifications
-- ----------------------------
DROP TABLE IF EXISTS "public"."notifications";
CREATE TABLE "public"."notifications" (
  "id" int4 NOT NULL DEFAULT nextval('notifications_id_seq'::regclass),
  "created_at" timestamptz(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  "updated_at" timestamptz(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  "device_id" int4 NOT NULL,
  "position_id" int4 NOT NULL,
  "event_type" varchar(255) COLLATE "pg_catalog"."default" NOT NULL
)
;
COMMENT ON COLUMN "public"."notifications"."id" IS 'ID';
COMMENT ON COLUMN "public"."notifications"."device_id" IS 'Dispositivo';
COMMENT ON COLUMN "public"."notifications"."position_id" IS 'Posição';
COMMENT ON COLUMN "public"."notifications"."event_type" IS 'Evento';

-- ----------------------------
-- Records of notifications
-- ----------------------------
INSERT INTO "public"."notifications" VALUES (27, '2021-04-06 00:48:27.253225+00', '2021-04-06 00:48:27.253225+00', 3, 49, 'GEOFENCE_ENTER');
INSERT INTO "public"."notifications" VALUES (28, '2021-04-06 00:48:49.153643+00', '2021-04-06 00:48:49.153643+00', 3, 50, 'OVER_SPEED');
INSERT INTO "public"."notifications" VALUES (29, '2021-04-06 00:49:10.527483+00', '2021-04-06 00:49:10.527483+00', 3, 51, 'OVER_SPEED');
INSERT INTO "public"."notifications" VALUES (30, '2021-04-06 00:49:10.549061+00', '2021-04-06 00:49:10.549061+00', 3, 51, 'GEOFENCE_ENTER');

-- ----------------------------
-- Table structure for positions
-- ----------------------------
DROP TABLE IF EXISTS "public"."positions";
CREATE TABLE "public"."positions" (
  "id" int4 NOT NULL DEFAULT nextval('positions_id_seq'::regclass),
  "latitude" float4,
  "longitude" float4,
  "speed" float4,
  "course" float4,
  "odometer" int4,
  "alarm" varchar(255) COLLATE "pg_catalog"."default",
  "key_ignition" int4,
  "power" float4,
  "battery_level" float4,
  "signal_level" int4,
  "date" timestamp(6),
  "created_at" timestamptz(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  "updated_at" timestamptz(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  "device_id" int4 NOT NULL
)
;
COMMENT ON COLUMN "public"."positions"."id" IS 'ID';
COMMENT ON COLUMN "public"."positions"."latitude" IS 'Latitude';
COMMENT ON COLUMN "public"."positions"."longitude" IS 'Longitude';
COMMENT ON COLUMN "public"."positions"."speed" IS 'Velocidade';
COMMENT ON COLUMN "public"."positions"."course" IS 'Ângulo';
COMMENT ON COLUMN "public"."positions"."odometer" IS 'Hodômetro';
COMMENT ON COLUMN "public"."positions"."alarm" IS 'Alarme';
COMMENT ON COLUMN "public"."positions"."key_ignition" IS 'Ignição ligada';
COMMENT ON COLUMN "public"."positions"."power" IS 'Voltagem';
COMMENT ON COLUMN "public"."positions"."battery_level" IS 'Nível da bateria';
COMMENT ON COLUMN "public"."positions"."signal_level" IS 'Nível do sinal';
COMMENT ON COLUMN "public"."positions"."date" IS 'Data';
COMMENT ON COLUMN "public"."positions"."device_id" IS 'Dispositivo';

-- ----------------------------
-- Records of positions
-- ----------------------------
INSERT INTO "public"."positions" VALUES (40, -10.005702, -67.80137, 18.52, 224.29634, 0, NULL, 1, 0, 0, 0, '2020-01-22 16:39:01', '2021-04-06 00:43:08.561074+00', '2021-04-06 00:43:08.561074+00', 3);
INSERT INTO "public"."positions" VALUES (41, -10.005702, -67.80137, 18.52, 224.29634, 0, NULL, 1, 0, 0, 0, '2020-01-22 16:39:01', '2021-04-06 00:43:45.649005+00', '2021-04-06 00:43:45.649005+00', 3);
INSERT INTO "public"."positions" VALUES (42, -10.005702, -67.80137, 18.52, 224.29634, 0, NULL, 1, 0, 0, 0, '2020-01-22 16:39:01', '2021-04-06 00:43:59.444797+00', '2021-04-06 00:43:59.444797+00', 3);
INSERT INTO "public"."positions" VALUES (43, -10.005702, -67.80137, 18.52, 224.29634, 0, NULL, 1, 0, 0, 0, '2020-01-22 16:39:01', '2021-04-06 00:44:55.705492+00', '2021-04-06 00:44:55.705492+00', 3);
INSERT INTO "public"."positions" VALUES (44, -10.005702, -67.80137, 18.52, 224.29634, 0, NULL, 1, 0, 0, 0, '2020-01-22 16:39:01', '2021-04-06 00:45:52.683413+00', '2021-04-06 00:45:52.683413+00', 3);
INSERT INTO "public"."positions" VALUES (45, -10.005702, -67.80137, 18.52, 224.29634, 0, NULL, 1, 0, 0, 0, '2020-01-22 16:39:01', '2021-04-06 00:46:40.749378+00', '2021-04-06 00:46:40.749378+00', 3);
INSERT INTO "public"."positions" VALUES (46, -10.005702, -67.80137, 18.52, 224.29634, 0, NULL, 1, 0, 0, 0, '2020-01-22 16:39:01', '2021-04-06 00:47:30.3821+00', '2021-04-06 00:47:30.3821+00', 3);
INSERT INTO "public"."positions" VALUES (47, -10.005702, -67.80137, 18.52, 224.29634, 0, NULL, 1, 0, 0, 0, '2020-01-22 16:39:01', '2021-04-06 00:47:36.889982+00', '2021-04-06 00:47:36.889982+00', 3);
INSERT INTO "public"."positions" VALUES (48, -10.004039, -67.8015, 18.52, 224.29634, 0, NULL, 1, 0, 0, 0, '2020-01-22 16:39:01', '2021-04-06 00:48:10.96549+00', '2021-04-06 00:48:10.96549+00', 3);
INSERT INTO "public"."positions" VALUES (49, -10.005702, -67.80137, 18.52, 224.29634, 0, NULL, 1, 0, 0, 0, '2020-01-22 16:39:01', '2021-04-06 00:48:27.205885+00', '2021-04-06 00:48:27.205885+00', 3);
INSERT INTO "public"."positions" VALUES (50, -10.004039, -67.8015, 185.2, 224.29634, 0, NULL, 1, 0, 0, 0, '2020-01-22 16:39:01', '2021-04-06 00:48:49.125323+00', '2021-04-06 00:48:49.125323+00', 3);
INSERT INTO "public"."positions" VALUES (51, -10.005702, -67.80137, 222.24, 224.29634, 0, NULL, 1, 0, 0, 0, '2020-01-22 16:39:01', '2021-04-06 00:49:10.499531+00', '2021-04-06 00:49:10.499531+00', 3);

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS "public"."users";
CREATE TABLE "public"."users" (
  "id" int4 NOT NULL DEFAULT nextval('users_id_seq'::regclass),
  "name" varchar(255) COLLATE "pg_catalog"."default",
  "email" varchar(255) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "public"."users"."id" IS 'ID';
COMMENT ON COLUMN "public"."users"."name" IS 'Nome completo';
COMMENT ON COLUMN "public"."users"."email" IS 'E-mail';

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO "public"."users" VALUES (1, 'Ricardo Farias', 'riicardofarias@gmail.com');

-- ----------------------------
-- Table structure for users_groups
-- ----------------------------
DROP TABLE IF EXISTS "public"."users_groups";
CREATE TABLE "public"."users_groups" (
  "user_id" int4 NOT NULL,
  "group_id" int4 NOT NULL
)
;
COMMENT ON COLUMN "public"."users_groups"."user_id" IS 'Usuário';
COMMENT ON COLUMN "public"."users_groups"."group_id" IS 'Grupo';

-- ----------------------------
-- Records of users_groups
-- ----------------------------

-- ----------------------------
-- Table structure for zones
-- ----------------------------
DROP TABLE IF EXISTS "public"."zones";
CREATE TABLE "public"."zones" (
  "id" int4 NOT NULL DEFAULT nextval('zones_id_seq'::regclass),
  "area" text COLLATE "pg_catalog"."default",
  "created_at" timestamptz(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  "updated_at" timestamptz(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  "user_id" int4 NOT NULL
)
;
COMMENT ON COLUMN "public"."zones"."id" IS 'ID';
COMMENT ON COLUMN "public"."zones"."area" IS 'Area';
COMMENT ON COLUMN "public"."zones"."user_id" IS 'Usuário';

-- ----------------------------
-- Records of zones
-- ----------------------------
INSERT INTO "public"."zones" VALUES (1, '[{"lat": -10.004883, "lng": -67.801351}, {"lat": -10.005735, "lng": -67.801978}, {"lat": -10.006365, "lng": -67.800917}, {"lat": -10.005087, "lng": -67.800992}, {"lat": -10.004883, "lng": -67.801351}]', '2021-04-05 15:25:58.896345+00', '2021-04-05 15:25:58.896345+00', 1);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."devices_id_seq"
OWNED BY "public"."devices"."id";
SELECT setval('"public"."devices_id_seq"', 4, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."events_id_seq"
OWNED BY "public"."events"."id";
SELECT setval('"public"."events_id_seq"', 4, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."groups_id_seq"
OWNED BY "public"."groups"."id";
SELECT setval('"public"."groups_id_seq"', 2, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."knex_migrations_id_seq"
OWNED BY "public"."knex_migrations"."id";
SELECT setval('"public"."knex_migrations_id_seq"', 11, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."knex_migrations_lock_index_seq"
OWNED BY "public"."knex_migrations_lock"."index";
SELECT setval('"public"."knex_migrations_lock_index_seq"', 2, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."notifications_id_seq"
OWNED BY "public"."notifications"."id";
SELECT setval('"public"."notifications_id_seq"', 31, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."positions_id_seq"
OWNED BY "public"."positions"."id";
SELECT setval('"public"."positions_id_seq"', 52, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."users_id_seq"
OWNED BY "public"."users"."id";
SELECT setval('"public"."users_id_seq"', 2, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."zones_id_seq"
OWNED BY "public"."zones"."id";
SELECT setval('"public"."zones_id_seq"', 2, true);

-- ----------------------------
-- Primary Key structure for table devices
-- ----------------------------
ALTER TABLE "public"."devices" ADD CONSTRAINT "devices_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table events
-- ----------------------------
ALTER TABLE "public"."events" ADD CONSTRAINT "events_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table groups
-- ----------------------------
ALTER TABLE "public"."groups" ADD CONSTRAINT "groups_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table knex_migrations
-- ----------------------------
ALTER TABLE "public"."knex_migrations" ADD CONSTRAINT "knex_migrations_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table knex_migrations_lock
-- ----------------------------
ALTER TABLE "public"."knex_migrations_lock" ADD CONSTRAINT "knex_migrations_lock_pkey" PRIMARY KEY ("index");

-- ----------------------------
-- Primary Key structure for table notifications
-- ----------------------------
ALTER TABLE "public"."notifications" ADD CONSTRAINT "notifications_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table positions
-- ----------------------------
ALTER TABLE "public"."positions" ADD CONSTRAINT "positions_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table users
-- ----------------------------
ALTER TABLE "public"."users" ADD CONSTRAINT "users_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table zones
-- ----------------------------
ALTER TABLE "public"."zones" ADD CONSTRAINT "zones_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Foreign Keys structure for table devices
-- ----------------------------
ALTER TABLE "public"."devices" ADD CONSTRAINT "devices_user_id_foreign" FOREIGN KEY ("user_id") REFERENCES "public"."users" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table devices_groups
-- ----------------------------
ALTER TABLE "public"."devices_groups" ADD CONSTRAINT "devices_groups_device_id_foreign" FOREIGN KEY ("device_id") REFERENCES "public"."devices" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."devices_groups" ADD CONSTRAINT "devices_groups_group_id_foreign" FOREIGN KEY ("group_id") REFERENCES "public"."groups" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table events
-- ----------------------------
ALTER TABLE "public"."events" ADD CONSTRAINT "events_device_id_foreign" FOREIGN KEY ("device_id") REFERENCES "public"."devices" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."events" ADD CONSTRAINT "events_user_id_foreign" FOREIGN KEY ("user_id") REFERENCES "public"."users" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table events_zones
-- ----------------------------
ALTER TABLE "public"."events_zones" ADD CONSTRAINT "events_zones_device_id_foreign" FOREIGN KEY ("device_id") REFERENCES "public"."devices" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."events_zones" ADD CONSTRAINT "events_zones_zone_id_foreign" FOREIGN KEY ("zone_id") REFERENCES "public"."zones" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table groups
-- ----------------------------
ALTER TABLE "public"."groups" ADD CONSTRAINT "groups_user_id_foreign" FOREIGN KEY ("user_id") REFERENCES "public"."users" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table notifications
-- ----------------------------
ALTER TABLE "public"."notifications" ADD CONSTRAINT "notifications_device_id_foreign" FOREIGN KEY ("device_id") REFERENCES "public"."devices" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."notifications" ADD CONSTRAINT "notifications_position_id_foreign" FOREIGN KEY ("position_id") REFERENCES "public"."positions" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table positions
-- ----------------------------
ALTER TABLE "public"."positions" ADD CONSTRAINT "positions_device_id_foreign" FOREIGN KEY ("device_id") REFERENCES "public"."devices" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table users_groups
-- ----------------------------
ALTER TABLE "public"."users_groups" ADD CONSTRAINT "users_groups_group_id_foreign" FOREIGN KEY ("group_id") REFERENCES "public"."groups" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."users_groups" ADD CONSTRAINT "users_groups_user_id_foreign" FOREIGN KEY ("user_id") REFERENCES "public"."users" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table zones
-- ----------------------------
ALTER TABLE "public"."zones" ADD CONSTRAINT "zones_user_id_foreign" FOREIGN KEY ("user_id") REFERENCES "public"."users" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
