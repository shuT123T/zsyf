/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.134.1_MySQL
 Source Server Type    : MySQL
 Source Server Version : 80023
 Source Host           : localhost:3306
 Source Schema         : zsyf

 Target Server Type    : MySQL
 Target Server Version : 80023
 File Encoding         : 65001

 Date: 14/05/2023 14:43:12
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for address_book
-- ----------------------------
DROP TABLE IF EXISTS `address_book`;
CREATE TABLE `address_book`  (
  `id` bigint(0) NOT NULL COMMENT '主键',
  `user_id` bigint(0) NOT NULL COMMENT '用户id',
  `consignee` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '收货人',
  `sex` tinyint(0) NOT NULL COMMENT '性别 0 女 1 男',
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '手机号',
  `province_code` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '省级区划编号',
  `province_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '省级名称',
  `city_code` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '市级区划编号',
  `city_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '市级名称',
  `district_code` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '区级区划编号',
  `district_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '区级名称',
  `detail` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '详细地址',
  `label` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '标签',
  `is_default` tinyint(1) NOT NULL DEFAULT 0 COMMENT '默认 0 否 1是',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL COMMENT '更新时间',
  `create_user` bigint(0) NOT NULL COMMENT '创建人',
  `update_user` bigint(0) NOT NULL COMMENT '修改人',
  `is_deleted` int(0) NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '地址管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of address_book
-- ----------------------------
INSERT INTO `address_book` VALUES (1592358798415138818, 1592358647202091009, '尹先生', 1, '18142079681', NULL, NULL, NULL, NULL, NULL, NULL, '9栋二单元305', '公司', 1, '2022-11-15 11:28:17', '2022-11-15 11:28:19', 1592358647202091009, 1592358647202091009, 0);
INSERT INTO `address_book` VALUES (1637800970815660034, 1592351848277245953, '舒乐钧', 1, '19958215293', NULL, NULL, NULL, NULL, NULL, NULL, '9栋二单元', '学校', 0, '2023-03-20 20:59:15', '2023-03-28 11:35:28', 1592351848277245953, 1592351848277245953, 0);
INSERT INTO `address_book` VALUES (1638339053344575489, 1592351848277245953, '张先生', 1, '13017190127', NULL, NULL, NULL, NULL, NULL, NULL, '9栋二单元306', '公司', 1, '2023-03-22 08:37:24', '2023-03-29 11:23:32', 1592351848277245953, 1592351848277245953, 0);
INSERT INTO `address_book` VALUES (1641358139427147777, 1592424257193775105, '舒乐钧', 1, '19958215293', NULL, NULL, NULL, NULL, NULL, NULL, '9栋四单元', '公司', 1, '2023-03-30 16:34:10', '2023-03-30 16:34:12', 1592424257193775105, 1592424257193775105, 0);

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `id` bigint(0) NOT NULL COMMENT '主键',
  `type` int(0) NULL DEFAULT NULL COMMENT '类型   1 菜品分类 2 套餐分类',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '分类名称',
  `sort` int(0) NOT NULL DEFAULT 0 COMMENT '排序',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL COMMENT '更新时间',
  `create_user` bigint(0) NOT NULL COMMENT '创建人',
  `update_user` bigint(0) NOT NULL COMMENT '修改人',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_category_name`(`name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '菜品及套餐分类' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES (1397844263642378242, 1, '大荤❀', 0, '2021-05-27 09:16:58', '2023-03-22 20:32:27', 1, 1);
INSERT INTO `category` VALUES (1397844303408574465, 1, '小荤', 2, '2021-05-27 09:17:07', '2023-03-21 14:18:00', 1, 1);
INSERT INTO `category` VALUES (1397844391040167938, 1, '烤鱼系列', 3, '2021-05-27 09:17:28', '2023-03-21 14:38:53', 1, 1);
INSERT INTO `category` VALUES (1413341197421846529, 1, '饮品区', 11, '2021-07-09 11:36:15', '2023-03-21 14:33:37', 1, 1);
INSERT INTO `category` VALUES (1413342269393674242, 2, '超划算套餐', 5, '2021-07-09 11:40:30', '2023-03-21 15:06:28', 1, 1);
INSERT INTO `category` VALUES (1413384954989060097, 1, '主食', 12, '2021-07-09 14:30:07', '2021-07-09 14:39:19', 1, 1);
INSERT INTO `category` VALUES (1638069656994549762, 1, '田园时蔬', 2, '2023-03-21 14:46:55', '2023-03-21 14:46:55', 1, 1);
INSERT INTO `category` VALUES (1638073047040757762, 1, '营养炖品', 11, '2023-03-21 15:00:23', '2023-03-21 15:01:21', 1, 1);

-- ----------------------------
-- Table structure for dish
-- ----------------------------
DROP TABLE IF EXISTS `dish`;
CREATE TABLE `dish`  (
  `id` bigint(0) NOT NULL COMMENT '主键',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '菜品名称',
  `category_id` bigint(0) NOT NULL COMMENT '菜品分类id',
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '菜品价格',
  `code` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '商品码',
  `image` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '图片',
  `description` varchar(400) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '描述信息',
  `status` int(0) NOT NULL DEFAULT 1 COMMENT '0 停售 1 起售',
  `sort` int(0) NOT NULL DEFAULT 0 COMMENT '顺序',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL COMMENT '更新时间',
  `create_user` bigint(0) NOT NULL COMMENT '创建人',
  `update_user` bigint(0) NOT NULL COMMENT '修改人',
  `is_deleted` int(0) NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_dish_name`(`name`) USING BTREE,
  INDEX `dish_ibfk_1`(`category_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '菜品管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dish
-- ----------------------------
INSERT INTO `dish` VALUES (1397860242057375745, '麻辣烤鱼', 1397844391040167938, 12800.00, '123456786543213456', 'b3e74c8e-3dde-4c8b-9d8c-fcd43eb62272.jpg', '', 1, 0, '2021-05-27 10:20:27', '2023-03-21 14:42:50', 1, 1, 0);
INSERT INTO `dish` VALUES (1397860578738352129, '特色蒜香烤鱼', 1397844391040167938, 12800.00, '12345678654', 'b6cd3e86-2ad0-49a1-9ba8-04ecb6cb3515.jpg', '', 1, 0, '2021-05-27 10:21:48', '2023-03-21 14:42:02', 1, 1, 0);
INSERT INTO `dish` VALUES (1397860792492666881, '泡椒味烤鱼', 1397844391040167938, 13800.00, '213456432123456', '256d0f76-2852-4775-9c85-ef089b3bec82.jpg', '泡椒烤鱼 酸辣 开胃', 1, 0, '2021-05-27 10:22:39', '2023-03-21 14:40:18', 1, 1, 0);
INSERT INTO `dish` VALUES (1397860963880316929, '扬州风味狮子头', 1397844303408574465, 500.00, '1234563212345', '0dd5b672-3e23-438c-854d-14e463f5dbe1.jpg', '淮扬大厨秘制，选用肥四瘦六五花肉，入口软香绵滑，历经多道工序并摔打20余次，方才成就这颗地道“扬州狮子头”', 1, 0, '2021-05-27 10:23:19', '2023-03-21 14:31:39', 1, 1, 0);
INSERT INTO `dish` VALUES (1397861683434139649, '招牌粉蒸肉', 1397844263642378242, 15800.00, '1234567876543213456', 'cd4f0716-a3c7-44e4-94d4-3352f4a7a7cf.jpg', '可口的粉蒸肉', 1, 0, '2021-05-27 10:26:11', '2023-03-21 15:50:55', 1, 1, 0);
INSERT INTO `dish` VALUES (1397862198033297410, '爱心煎蛋', 1397844303408574465, 300.00, '123456786532455', 'a552dbde-e120-4de7-934e-e3c846b599ac.jpg', '连煎蛋都是爱你的样子，亲，不点主餐不配送哦', 1, 0, '2021-05-27 10:28:14', '2023-03-21 14:33:05', 1, 1, 0);
INSERT INTO `dish` VALUES (1397862477831122945, '梅菜扣肉', 1397844263642378242, 13800.00, '1234567865432', '9f31b1e4-6069-4393-8073-160e575e3e77.jpg', '肉香梅菜也很好吃', 1, 0, '2021-05-27 10:29:20', '2023-03-21 14:25:58', 1, 1, 0);
INSERT INTO `dish` VALUES (1413342036832100354, '红豆藕粉小圆子', 1638073047040757762, 500.00, '', '8446d6dd-1e82-44f3-a4ca-2696a372b2c7.jpg', '一碗有颜又有料的红豆小圆子！', 1, 0, '2021-07-09 11:39:35', '2023-03-21 15:00:59', 1, 1, 0);
INSERT INTO `dish` VALUES (1413384757047271425, '紫菜花蛋汤', 1638073047040757762, 2100.00, '', '5fed03cf-c231-4a5e-8a2d-4b2f413c2165.jpg', '紫菜与鸡蛋的完美结合', 1, 0, '2021-07-09 14:29:20', '2023-03-21 16:32:27', 1, 1, 0);
INSERT INTO `dish` VALUES (1413385247889891330, '米饭', 1413384954989060097, 200.00, '', 'f35a2250-2ea1-40a9-9002-1c9ec7daff30.jpg', '不点不送哦~~', 1, 0, '2021-07-09 14:31:17', '2023-03-21 16:40:13', 1, 1, 0);
INSERT INTO `dish` VALUES (1593846416722214913, '辣孑鸡', 1397844263642378242, 7800.00, '', 'f361a4b8-f671-43d2-a03b-a4e8a0ffaf47.jpg', '辣子鸡丁 ，特色传统菜肴，属川菜系， 一道家常菜，较辣，是重庆一道著名的江湖风味菜，起源于歌乐山。 干辣椒不是主料胜似主料，充分体现了江湖厨师“下手重”的特点。 成菜色泽棕红油亮，质地酥软，麻辣味浓。 咸鲜醇香，略带回甜，是一款食者啖之难忘的美味佳肴。', 1, 0, '2022-11-19 13:59:33', '2023-03-21 14:55:36', 1, 1, 0);
INSERT INTO `dish` VALUES (1605039326092103682, '紫苏牛蛙', 1397844263642378242, 12300.00, '', '4e6addb5-7902-4b64-81b6-d98ddc7953d8.jpg', '大只牛蛙，嘎嘎好吃', 1, 0, '2022-12-20 11:16:10', '2023-03-21 16:16:30', 1, 1, 0);
INSERT INTO `dish` VALUES (1638067037785919490, '可口可乐', 1413341197421846529, 700.00, '', 'd9c09fed-f54f-419d-bad3-6d968b9604a3.jpg', '亲，单点不配送哦', 1, 0, '2023-03-21 14:36:30', '2023-03-21 14:36:30', 1, 1, 0);
INSERT INTO `dish` VALUES (1638069061139140610, '香辣烤鱼', 1397844391040167938, 12800.00, '', 'e2e33dc3-d7df-4f53-b8e5-6f2987251d68.jpg', '', 1, 0, '2023-03-21 14:44:33', '2023-03-21 14:45:02', 1, 1, 0);
INSERT INTO `dish` VALUES (1638069827002273793, '炒花菜', 1638069656994549762, 1500.00, '', '23e624ab-6dae-42da-9e1b-4ac2f3b47f39.jpg', '', 1, 0, '2023-03-21 14:47:35', '2023-03-21 14:47:42', 1, 1, 0);
INSERT INTO `dish` VALUES (1638070031407484930, '酸辣土豆丝', 1638069656994549762, 1200.00, '', '692b34ee-606e-42ee-9bda-3d6669ee062d.jpg', '', 1, 0, '2023-03-21 14:48:24', '2023-03-21 14:48:24', 1, 1, 0);
INSERT INTO `dish` VALUES (1638070309242376194, '油焖茄子', 1638069656994549762, 1600.00, '', '53acaf16-4ece-43c3-9f6b-ffff26c203a4.jpg', '好吃的茄子', 1, 0, '2023-03-21 14:49:30', '2023-03-21 15:49:53', 1, 1, 0);
INSERT INTO `dish` VALUES (1638070421867827202, '山药木耳', 1638069656994549762, 1600.00, '', 'b96231e5-d308-4404-a046-713a43ece303.jpg', '', 1, 0, '2023-03-21 14:49:57', '2023-03-21 14:49:57', 1, 1, 0);
INSERT INTO `dish` VALUES (1638070620350681090, '清炒苦瓜', 1638069656994549762, 1680.00, '', '87be0cce-be95-40ef-9824-a5a4318f319b.jpg', '清凉降火，低卡', 1, 0, '2023-03-21 14:50:44', '2023-03-21 16:17:08', 1, 1, 0);
INSERT INTO `dish` VALUES (1638072735370416130, '每日C葡萄汁', 1413341197421846529, 600.00, '', 'e422f54c-ecd3-473b-a378-1568ced98e58.jpg', '美味的葡萄汁', 1, 0, '2023-03-21 14:59:09', '2023-03-21 14:59:09', 1, 1, 0);
INSERT INTO `dish` VALUES (1638090710622179329, '孜然烤肉', 1397844263642378242, 8800.00, '', '419e13a4-ce63-44af-9ec9-7813080898e5.jpg', '新疆孜然烤肉味，好吃不柴', 1, 0, '2023-03-21 16:10:34', '2023-03-21 16:10:34', 1, 1, 0);
INSERT INTO `dish` VALUES (1638091132086816769, '土豆红烧肉', 1397844263642378242, 4800.00, '', '92dda1cd-d444-4e31-9df8-56ad9d643da4.jpg', '超大快的红烧肉，一饱口福', 1, 0, '2023-03-21 16:12:15', '2023-03-21 16:12:15', 1, 1, 0);
INSERT INTO `dish` VALUES (1638091325314207745, '西红柿炒蛋', 1397844303408574465, 1800.00, '', 'bfd956a8-7185-4493-9e37-fcce1685261b.jpg', '经典永不过时', 1, 0, '2023-03-21 16:13:01', '2023-03-21 16:13:01', 1, 1, 0);
INSERT INTO `dish` VALUES (1638096704890769409, '手撕包菜', 1638069656994549762, 600.00, '', '08de9b9e-f518-4ff7-ab81-d3d21f5df287.jpg', '香辣爽脆', 1, 0, '2023-03-21 16:34:23', '2023-03-21 16:34:23', 1, 1, 0);
INSERT INTO `dish` VALUES (1638097404496482306, '农家一碗香', 1397844303408574465, 1600.00, '', 'c6bd9890-35e4-40ae-bb85-4818cf274655.jpg', '多了一个蛋', 1, 0, '2023-03-21 16:37:10', '2023-03-21 16:37:10', 1, 1, 0);
INSERT INTO `dish` VALUES (1638097668095905793, '油麦菜', 1638069656994549762, 690.00, '', 'a14808d3-2d25-462a-9852-77ea22b56a12.jpg', '低卡减脂', 1, 0, '2023-03-21 16:38:13', '2023-04-10 12:24:57', 1, 1, 0);
INSERT INTO `dish` VALUES (1638097967145586689, '藕丁炒肉', 1397844303408574465, 1680.00, '', 'beb96703-1636-490c-937d-aa82da088c76.jpg', '藕丁与猪肉的完美结合', 1, 0, '2023-03-21 16:39:24', '2023-03-21 16:39:24', 1, 1, 0);
INSERT INTO `dish` VALUES (1638098693456433153, '柠檬酸菜鱼', 1397844263642378242, 5600.00, '', 'eb8dc86c-b10b-457a-9335-4488f7d5c5e6.jpg', '酸得刚刚好，打开你的味蕾', 1, 0, '2023-03-21 16:42:18', '2023-03-21 16:42:18', 1, 1, 0);

-- ----------------------------
-- Table structure for dish_flavor
-- ----------------------------
DROP TABLE IF EXISTS `dish_flavor`;
CREATE TABLE `dish_flavor`  (
  `id` bigint(0) NOT NULL COMMENT '主键',
  `dish_id` bigint(0) NOT NULL COMMENT '菜品',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '口味名称',
  `value` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '口味数据list',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL COMMENT '更新时间',
  `create_user` bigint(0) NOT NULL COMMENT '创建人',
  `update_user` bigint(0) NOT NULL COMMENT '修改人',
  `is_deleted` int(0) NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '菜品口味关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dish_flavor
-- ----------------------------
INSERT INTO `dish_flavor` VALUES (1397849417888346113, 1397849417854791681, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]', '2021-05-27 09:37:27', '2021-05-27 09:37:27', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1397849936421761025, 1397849936404983809, '忌口', '[\"不要葱\",\"不要蒜\",\"不要香菜\",\"不要辣\"]', '2021-05-27 09:39:30', '2021-05-27 09:39:30', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1397849936438538241, 1397849936404983809, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]', '2021-05-27 09:39:30', '2021-05-27 09:39:30', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1397850630734262274, 1397850630700707841, '忌口', '[\"不要葱\",\"不要蒜\",\"不要香菜\",\"不要辣\"]', '2021-05-27 09:42:16', '2021-05-27 09:42:16', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1397850630755233794, 1397850630700707841, '辣度', '[\"微辣\",\"中辣\",\"重辣\"]', '2021-05-27 09:42:16', '2021-05-27 09:42:16', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1397853423486414850, 1397853423461249026, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]', '2021-05-27 09:53:22', '2021-05-27 09:53:22', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1397854133632413697, 1397854133603053569, '温度', '[\"热饮\",\"常温\",\"去冰\",\"少冰\",\"多冰\"]', '2021-05-27 09:56:11', '2021-05-27 09:56:11', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1397855742303186946, 1397855742273826817, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]', '2021-05-27 10:02:35', '2021-05-27 10:02:35', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1397855906497605633, 1397855906468245506, '忌口', '[\"不要葱\",\"不要蒜\",\"不要香菜\",\"不要辣\"]', '2021-05-27 10:03:14', '2021-05-27 10:03:14', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1397856190573621250, 1397856190540066818, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]', '2021-05-27 10:04:21', '2021-05-27 10:04:21', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1397859056709316609, 1397859056684150785, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]', '2021-05-27 10:15:45', '2021-05-27 10:15:45', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1397859277837217794, 1397859277812051969, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]', '2021-05-27 10:16:37', '2021-05-27 10:16:37', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1397859487502086146, 1397859487476920321, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]', '2021-05-27 10:17:27', '2021-05-27 10:17:27', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1397859757061615618, 1397859757036449794, '甜味', '[\"无糖\",\"少糖\",\"半躺\",\"多糖\",\"全糖\"]', '2021-05-27 10:18:32', '2021-05-27 10:18:32', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1397860242086735874, 1397860242057375745, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]', '2023-03-21 14:42:50', '2023-03-21 14:42:50', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1397861135754506242, 1397861135733534722, '甜味', '[\"无糖\",\"少糖\",\"半躺\",\"多糖\",\"全糖\"]', '2021-05-27 10:24:00', '2021-05-27 10:24:00', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1397861370035744769, 1397861370010578945, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]', '2021-05-27 10:24:56', '2021-05-27 10:24:56', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1397861683459305474, 1397861683434139649, '忌口', '[\"不要葱\",\"不要蒜\",\"不要辣\"]', '2023-03-21 15:50:55', '2023-03-21 15:50:55', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1397861898467717121, 1397861898438356993, '忌口', '[\"不要葱\",\"不要蒜\",\"不要香菜\",\"不要辣\"]', '2021-05-27 10:27:02', '2021-05-27 10:27:02', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1397862477835317250, 1397862477831122945, '辣度', '[\"不辣\",\"微辣\",\"中辣\"]', '2023-03-21 14:25:58', '2023-03-21 14:25:58', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1398089545865015297, 1398089545676271617, '温度', '[\"热饮\",\"常温\",\"去冰\",\"少冰\",\"多冰\"]', '2021-05-28 01:31:38', '2021-05-28 01:31:38', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1398089782323097601, 1398089782285348866, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]', '2021-05-28 01:32:34', '2021-05-28 01:32:34', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1398090003262255106, 1398090003228700673, '忌口', '[\"不要葱\",\"不要蒜\",\"不要香菜\",\"不要辣\"]', '2021-05-28 01:33:27', '2021-05-28 01:33:27', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1398090264554811394, 1398090264517062657, '忌口', '[\"不要葱\",\"不要蒜\",\"不要香菜\",\"不要辣\"]', '2021-05-28 01:34:29', '2021-05-28 01:34:29', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1398090455399837698, 1398090455324340225, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]', '2021-05-28 01:35:14', '2021-05-28 01:35:14', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1398090685449023490, 1398090685419663362, '温度', '[\"热饮\",\"常温\",\"去冰\",\"少冰\",\"多冰\"]', '2021-05-28 01:36:09', '2021-05-28 01:36:09', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1398090825358422017, 1398090825329061889, '忌口', '[\"不要葱\",\"不要蒜\",\"不要香菜\",\"不要辣\"]', '2021-05-28 01:36:43', '2021-05-28 01:36:43', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1398091007051476993, 1398091007017922561, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]', '2021-05-28 01:37:26', '2021-05-28 01:37:26', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1398091296164851713, 1398091296131297281, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]', '2021-05-28 01:38:35', '2021-05-28 01:38:35', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1398091546531246081, 1398091546480914433, '忌口', '[\"不要葱\",\"不要蒜\",\"不要香菜\",\"不要辣\"]', '2021-05-28 01:39:35', '2021-05-28 01:39:35', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1398091729809747969, 1398091729788776450, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]', '2021-05-28 01:40:18', '2021-05-28 01:40:18', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1398091889499484161, 1398091889449152513, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]', '2021-05-28 01:40:56', '2021-05-28 01:40:56', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1398092095179763713, 1398092095142014978, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]', '2021-05-28 01:41:45', '2021-05-28 01:41:45', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1398092283877306370, 1398092283847946241, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]', '2021-05-28 01:42:30', '2021-05-28 01:42:30', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1398094018939236354, 1398094018893099009, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]', '2021-05-28 01:49:24', '2021-05-28 01:49:24', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1398094391494094850, 1398094391456346113, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]', '2021-05-28 01:50:53', '2021-05-28 01:50:53', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1399574026165727233, 1399305325713600514, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]', '2021-06-01 03:50:25', '2021-06-01 03:50:25', 1399309715396669441, 1399309715396669441, 0);
INSERT INTO `dish_flavor` VALUES (1413389684020682754, 1413342036832100354, '温度', '[\"常温\",\"冷藏\"]', '2023-03-21 15:00:59', '2023-03-21 15:00:59', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1586328192052936706, 1586328191721586689, '甜味', '[\"无糖\",\"少糖\",\"半糖\",\"多糖\",\"全糖\"]', '2022-11-17 09:58:11', '2022-11-17 09:58:11', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1586328192052936707, 1586328191721586689, '温度', '[\"热饮\",\"常温\",\"去冰\",\"少冰\",\"多冰\"]', '2022-11-17 09:58:11', '2022-11-17 09:58:11', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1586328699102347266, 1586328699035238402, '温度', '[\"热饮\",\"常温\",\"去冰\",\"少冰\",\"多冰\"]', '2022-10-29 20:06:49', '2022-10-29 20:06:49', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1586328699102347267, 1586328699035238402, '甜味', '[\"无糖\",\"少糖\",\"半糖\",\"多糖\",\"全糖\"]', '2022-10-29 20:06:49', '2022-10-29 20:06:49', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1586328699102347268, 1586328699035238402, '忌口', '[\"不要葱\",\"不要蒜\",\"不要香菜\",\"不要辣\"]', '2022-10-29 20:06:49', '2022-10-29 20:06:49', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1586553086044872705, 1586328191721586689, '科技', '[\"嘎嘎多\"]', '2022-11-17 09:58:11', '2022-11-17 09:58:11', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1593772245497470977, 1593772245434556418, '随便', '[\"一个\"]', '2022-11-19 09:52:54', '2022-11-19 09:52:54', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1605039326150823938, 1605039326092103682, '辣度', '[\"爆辣\",\"一般辣\",\"不加辣\"]', '2023-03-21 16:16:30', '2023-03-21 16:16:30', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1638063231119495169, 1593846416722214913, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]', '2023-03-21 14:55:36', '2023-03-21 14:55:36', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1638067037794308098, 1638067037785919490, '温度', '[\"常温\",\"冷藏\"]', '2023-03-21 14:36:30', '2023-03-21 14:36:30', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1638067992258514945, 1397860792492666881, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]', '2023-03-21 14:40:18', '2023-03-21 14:40:18', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1638068381900967938, 1397860578738352129, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]', '2023-03-21 14:42:02', '2023-03-21 14:42:02', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1638069061147529218, 1638069061139140610, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]', '2023-03-21 14:45:02', '2023-03-21 14:45:02', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1638090710655733761, 1638090710622179329, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]', '2023-03-21 16:10:34', '2023-03-21 16:10:34', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1638096704899158017, 1638096704890769409, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]', '2023-03-21 16:34:23', '2023-03-21 16:34:23', 1, 1, 0);

-- ----------------------------
-- Table structure for employee
-- ----------------------------
DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee`  (
  `id` bigint(0) NOT NULL COMMENT '主键',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '姓名',
  `username` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用户名',
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '密码',
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '手机号',
  `sex` varchar(2) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '性别',
  `id_number` varchar(18) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '身份证号',
  `status` int(0) NOT NULL DEFAULT 1 COMMENT '状态 0:禁用，1:正常',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL COMMENT '更新时间',
  `create_user` bigint(0) NOT NULL COMMENT '创建人',
  `update_user` bigint(0) NOT NULL COMMENT '修改人',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_username`(`username`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '员工信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of employee
-- ----------------------------
INSERT INTO `employee` VALUES (1585916274108588034, '张三', 'zhangsan1234', 'e10adc3949ba59abbe56e057f20f883e', '13017190127', '1', '121221211212455191', 1, '2022-10-28 16:47:59', '2022-10-28 16:47:59', 1, 1);
INSERT INTO `employee` VALUES (1585954083313868802, '尹大力', 'yin4310', 'e10adc3949ba59abbe56e057f20f883e', '15518209748', '1', '430102199003078399', 1, '2022-10-28 19:18:14', '2023-04-07 14:46:31', 1, 1);
INSERT INTO `employee` VALUES (1644230475650187265, '叶珍丽', 'yezhenli', 'e10adc3949ba59abbe56e057f20f883e', '17138109517', '0', '430102199003074259', 1, '2023-04-07 14:47:48', '2023-04-07 14:47:54', 1, 1);
INSERT INTO `employee` VALUES (1644230625185513473, '袁靖之', 'yuanjingzhi', 'e10adc3949ba59abbe56e057f20f883e', '15613938495', '1', '430102199003078399', 1, '2023-04-07 14:48:24', '2023-04-07 14:48:24', 1, 1);
INSERT INTO `employee` VALUES (1644230790101352450, '何清心', 'heqingxin', 'e10adc3949ba59abbe56e057f20f883e', '18601574221', '0', '43010219900307489X', 1, '2023-04-07 14:49:03', '2023-04-10 12:33:32', 1, 1);
INSERT INTO `employee` VALUES (1645733799306326018, '管理员', 'admin', 'e10adc3949ba59abbe56e057f20f883e', '13812312312', '1', '110101199001010047', 1, '2021-05-06 17:20:07', '2021-05-10 02:24:09', 1, 1);

-- ----------------------------
-- Table structure for order_detail
-- ----------------------------
DROP TABLE IF EXISTS `order_detail`;
CREATE TABLE `order_detail`  (
  `id` bigint(0) NOT NULL COMMENT '主键',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '名字',
  `image` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '图片',
  `order_id` bigint(0) NOT NULL COMMENT '订单id',
  `dish_id` bigint(0) NULL DEFAULT NULL COMMENT '菜品id',
  `setmeal_id` bigint(0) NULL DEFAULT NULL COMMENT '套餐id',
  `dish_flavor` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '口味',
  `number` int(0) NOT NULL DEFAULT 1 COMMENT '数量',
  `amount` decimal(10, 2) NOT NULL COMMENT '金额',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '订单明细表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_detail
-- ----------------------------
INSERT INTO `order_detail` VALUES (1638078612534005761, '米饭', 'ee04a05a-1230-46b6-8ad5-1a95b140fff3.png', 1638078612504645634, 1413385247889891330, NULL, NULL, 1, 2.00);
INSERT INTO `order_detail` VALUES (1638078612534005762, '紫菜花蛋汤', '5fed03cf-c231-4a5e-8a2d-4b2f413c2165.jpg', 1638078612504645634, 1413384757047271425, NULL, NULL, 1, 21.00);
INSERT INTO `order_detail` VALUES (1638078612534005763, '爱心煎蛋', 'a552dbde-e120-4de7-934e-e3c846b599ac.jpg', 1638078612504645634, 1397862198033297410, NULL, NULL, 1, 3.00);
INSERT INTO `order_detail` VALUES (1638078612542394369, '紫苏牛蛙', '4e6addb5-7902-4b64-81b6-d98ddc7953d8.jpg', 1638078612504645634, 1605039326092103682, NULL, '一般辣', 1, 123.00);
INSERT INTO `order_detail` VALUES (1638079145781039105, '紫苏牛蛙', '4e6addb5-7902-4b64-81b6-d98ddc7953d8.jpg', 1638079145772650498, 1605039326092103682, NULL, '不加辣', 1, 123.00);
INSERT INTO `order_detail` VALUES (1638339225659166721, '超优惠①号套餐', 'f7e727ca-2776-42f8-a871-153f7ddb12a4.jpg', 1638339225558503425, NULL, 1638142898081988610, NULL, 1, 199.00);
INSERT INTO `order_detail` VALUES (1638362327755608065, '紫苏牛蛙', '4e6addb5-7902-4b64-81b6-d98ddc7953d8.jpg', 1638362327688499201, 1605039326092103682, NULL, '不加辣', 1, 123.00);
INSERT INTO `order_detail` VALUES (1638511092734955522, '紫苏牛蛙', '4e6addb5-7902-4b64-81b6-d98ddc7953d8.jpg', 1638511092734955521, 1605039326092103682, NULL, '爆辣', 1, 123.00);
INSERT INTO `order_detail` VALUES (1640558235050106882, '紫苏牛蛙', '4e6addb5-7902-4b64-81b6-d98ddc7953d8.jpg', 1640558235033329665, 1605039326092103682, NULL, '不加辣', 1, 123.00);
INSERT INTO `order_detail` VALUES (1640558235050106883, '柠檬酸菜鱼', 'eb8dc86c-b10b-457a-9335-4488f7d5c5e6.jpg', 1640558235033329665, 1638098693456433153, NULL, NULL, 1, 56.00);
INSERT INTO `order_detail` VALUES (1640919854515052545, '紫苏牛蛙', '4e6addb5-7902-4b64-81b6-d98ddc7953d8.jpg', 1640919854447943682, 1605039326092103682, NULL, '一般辣', 1, 123.00);
INSERT INTO `order_detail` VALUES (1641358227700469761, '紫苏牛蛙', '4e6addb5-7902-4b64-81b6-d98ddc7953d8.jpg', 1641358227675303937, 1605039326092103682, NULL, '一般辣', 1, 123.00);
INSERT INTO `order_detail` VALUES (1641358473675427842, '紫苏牛蛙', '4e6addb5-7902-4b64-81b6-d98ddc7953d8.jpg', 1641358473667039234, 1605039326092103682, NULL, '不加辣', 1, 123.00);
INSERT INTO `order_detail` VALUES (1641358619108724738, '紫苏牛蛙', '4e6addb5-7902-4b64-81b6-d98ddc7953d8.jpg', 1641358619091947521, 1605039326092103682, NULL, '不加辣', 1, 123.00);
INSERT INTO `order_detail` VALUES (1647033852494884865, '紫苏牛蛙', '4e6addb5-7902-4b64-81b6-d98ddc7953d8.jpg', 1647033852427776001, 1605039326092103682, NULL, '不加辣', 1, 123.00);
INSERT INTO `order_detail` VALUES (1647108083421319170, '柠檬酸菜鱼', 'eb8dc86c-b10b-457a-9335-4488f7d5c5e6.jpg', 1647108083421319169, 1638098693456433153, NULL, NULL, 3, 56.00);

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
  `id` bigint(0) NOT NULL COMMENT '主键',
  `number` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '订单号',
  `status` int(0) NOT NULL DEFAULT 1 COMMENT '订单状态 1待付款，2待派送，3已派送，4已完成，5已取消',
  `user_id` bigint(0) NOT NULL COMMENT '下单用户',
  `address_book_id` bigint(0) NOT NULL COMMENT '地址id',
  `order_time` datetime(0) NOT NULL COMMENT '下单时间',
  `checkout_time` datetime(0) NOT NULL COMMENT '结账时间',
  `pay_method` int(0) NOT NULL DEFAULT 1 COMMENT '支付方式 1微信,2支付宝',
  `amount` decimal(10, 2) NOT NULL COMMENT '实收金额',
  `remark` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '备注',
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `consignee` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '订单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES (1638078612504645634, '1638078612504645634', 4, 1592351848277245953, 1637800970815660034, '2023-01-01 15:22:30', '2023-01-01 15:22:30', 1, 149.00, '有多辣就多辣', '19958215293', '9栋二单元', NULL, '舒乐钧');
INSERT INTO `orders` VALUES (1638079145772650498, '1638079145772650498', 4, 1592351848277245953, 1637800970815660034, '2023-01-01 15:24:37', '2023-01-01 15:24:37', 1, 123.00, '', '19958215293', '9栋二单元', NULL, '舒乐钧');
INSERT INTO `orders` VALUES (1638339225558503425, '1638339225558503425', 4, 1592351848277245953, 1638339053344575489, '2023-02-01 08:38:05', '2023-02-01 08:38:05', 1, 199.00, '', '13017190127', '9栋二单元305', NULL, '勒布朗');
INSERT INTO `orders` VALUES (1638362327688499201, '1638362327688499201', 3, 1592351848277245953, 1638339053344575489, '2023-03-22 10:09:53', '2023-03-22 10:09:53', 1, 123.00, '没得说', '13017190127', '9栋二单元305', NULL, '勒布朗');
INSERT INTO `orders` VALUES (1638511092734955521, '1638511092734955521', 2, 1592351848277245953, 1637800970815660034, '2023-03-22 20:01:01', '2023-03-22 20:01:01', 1, 123.00, '', '19958215293', '9栋二单元', NULL, '舒乐钧');
INSERT INTO `orders` VALUES (1640558235033329665, '1640558235033329665', 3, 1592351848277245953, 1637800970815660034, '2023-03-28 11:35:38', '2023-03-28 11:35:38', 1, 179.00, '', '19958215293', '9栋二单元', NULL, '舒乐钧');
INSERT INTO `orders` VALUES (1640919854447943682, '1640919854447943682', 3, 1592351848277245953, 1638339053344575489, '2023-03-29 11:32:35', '2023-03-29 11:32:35', 1, 123.00, '', '13017190127', '9栋二单元306', NULL, '勒布朗');
INSERT INTO `orders` VALUES (1641358227675303937, '1641358227675303937', 2, 1592424257193775105, 1641358139427147777, '2023-03-30 16:34:31', '2023-03-30 16:34:31', 1, 123.00, '', '19958215293', '9栋二单元306', NULL, '舒乐钧');
INSERT INTO `orders` VALUES (1641358473667039234, '1641358473667039234', 2, 1592424257193775105, 1641358139427147777, '2023-03-30 16:35:30', '2023-03-30 16:35:30', 1, 123.00, '', '19958215293', '湖南省长沙市天心区暮云街道中意三路122号湖南科技职院', NULL, '舒乐钧');
INSERT INTO `orders` VALUES (1641358619091947521, '1641358619091947521', 2, 1592424257193775105, 1641358139427147777, '2023-03-30 16:36:04', '2023-03-30 16:36:04', 1, 123.00, '', '19958215293', '湖南省长沙市天心区暮云街道中意三路122号湖南科技职院', NULL, '舒乐钧');
INSERT INTO `orders` VALUES (1647033852427776001, '1647033852427776001', 2, 1592351848277245953, 1638339053344575489, '2023-04-15 08:27:26', '2023-04-15 08:27:26', 1, 123.00, '', '13017190127', '9栋二单元306', NULL, '张先生');
INSERT INTO `orders` VALUES (1647108083421319169, '1647108083421319169', 2, 1592351848277245953, 1638339053344575489, '2023-04-15 13:22:24', '2023-04-15 13:22:24', 1, 168.00, '', '13017190127', '9栋二单元306', NULL, '张先生');

-- ----------------------------
-- Table structure for setmeal
-- ----------------------------
DROP TABLE IF EXISTS `setmeal`;
CREATE TABLE `setmeal`  (
  `id` bigint(0) NOT NULL COMMENT '主键',
  `category_id` bigint(0) NOT NULL COMMENT '菜品分类id',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '套餐名称',
  `price` decimal(10, 2) NOT NULL COMMENT '套餐价格',
  `status` int(0) NULL DEFAULT NULL COMMENT '状态 0:停用 1:启用',
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '编码',
  `description` varchar(512) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '描述信息',
  `image` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '图片',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL COMMENT '更新时间',
  `create_user` bigint(0) NOT NULL COMMENT '创建人',
  `update_user` bigint(0) NOT NULL COMMENT '修改人',
  `is_deleted` int(0) NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_setmeal_name`(`name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '套餐' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of setmeal
-- ----------------------------
INSERT INTO `setmeal` VALUES (1638142898081988610, 1413342269393674242, '超优惠①号套餐', 19900.00, 1, '', '套餐内含有紫苏牛蛙、辣孑鸡、米饭、可口可乐', 'f7e727ca-2776-42f8-a871-153f7ddb12a4.jpg', '2023-03-21 19:42:41', '2023-03-21 19:42:41', 1, 1, 0);
INSERT INTO `setmeal` VALUES (1646762548122365953, 1413342269393674242, '烤鱼优惠套餐', 29900.00, 1, '', '超值的烤鱼套餐', '37410826-08f7-4d24-880e-5efd37e19a1c.jpg', '2023-04-14 14:29:39', '2023-04-14 14:29:39', 1645733799306326018, 1645733799306326018, 0);

-- ----------------------------
-- Table structure for setmeal_dish
-- ----------------------------
DROP TABLE IF EXISTS `setmeal_dish`;
CREATE TABLE `setmeal_dish`  (
  `id` bigint(0) NOT NULL COMMENT '主键',
  `setmeal_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '套餐id ',
  `dish_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '菜品id',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '菜品名称 （冗余字段）',
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '菜品原价（冗余字段）',
  `copies` int(0) NOT NULL COMMENT '份数',
  `sort` int(0) NOT NULL DEFAULT 0 COMMENT '排序',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL COMMENT '更新时间',
  `create_user` bigint(0) NOT NULL COMMENT '创建人',
  `update_user` bigint(0) NOT NULL COMMENT '修改人',
  `is_deleted` int(0) NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '套餐菜品关系' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of setmeal_dish
-- ----------------------------
INSERT INTO `setmeal_dish` VALUES (1638144091504021505, '1638142898081988610', '1605039326092103682', '紫苏牛蛙', 12300.00, 1, 0, '2023-03-21 19:42:41', '2023-03-21 19:42:41', 1, 1, 0);
INSERT INTO `setmeal_dish` VALUES (1638144091508215809, '1638142898081988610', '1593846416722214913', '辣孑鸡', 7800.00, 1, 0, '2023-03-21 19:42:41', '2023-03-21 19:42:41', 1, 1, 0);
INSERT INTO `setmeal_dish` VALUES (1638144091508215810, '1638142898081988610', '1413385247889891330', '米饭', 200.00, 1, 0, '2023-03-21 19:42:41', '2023-03-21 19:42:41', 1, 1, 0);
INSERT INTO `setmeal_dish` VALUES (1638144091508215811, '1638142898081988610', '1638067037785919490', '可口可乐', 700.00, 3, 0, '2023-03-21 19:42:41', '2023-03-21 19:42:41', 1, 1, 0);
INSERT INTO `setmeal_dish` VALUES (1646762620356669442, '1646762548122365953', '1413385247889891330', '米饭', 200.00, 3, 0, '2023-04-14 14:29:39', '2023-04-14 14:29:39', 1645733799306326018, 1645733799306326018, 0);
INSERT INTO `setmeal_dish` VALUES (1646762620356669443, '1646762548122365953', '1397860242057375745', '麻辣烤鱼', 12800.00, 1, 0, '2023-04-14 14:29:39', '2023-04-14 14:29:39', 1645733799306326018, 1645733799306326018, 0);
INSERT INTO `setmeal_dish` VALUES (1646762620356669444, '1646762548122365953', '1397860578738352129', '特色蒜香烤鱼', 12800.00, 1, 0, '2023-04-14 14:29:39', '2023-04-14 14:29:39', 1645733799306326018, 1645733799306326018, 0);

-- ----------------------------
-- Table structure for shopping_cart
-- ----------------------------
DROP TABLE IF EXISTS `shopping_cart`;
CREATE TABLE `shopping_cart`  (
  `id` bigint(0) NOT NULL COMMENT '主键',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '名称',
  `image` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '图片',
  `user_id` bigint(0) NOT NULL COMMENT '主键',
  `dish_id` bigint(0) NULL DEFAULT NULL COMMENT '菜品id',
  `setmeal_id` bigint(0) NULL DEFAULT NULL COMMENT '套餐id',
  `dish_flavor` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '口味',
  `number` int(0) NOT NULL DEFAULT 1 COMMENT '数量',
  `amount` decimal(10, 2) NOT NULL COMMENT '金额',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '购物车' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of shopping_cart
-- ----------------------------
INSERT INTO `shopping_cart` VALUES (1641361769798615041, '紫苏牛蛙', '4e6addb5-7902-4b64-81b6-d98ddc7953d8.jpg', 1641361748164395009, 1605039326092103682, NULL, '不加辣', 1, 123.00, '2023-03-30 16:48:36');
INSERT INTO `shopping_cart` VALUES (1645277547698688001, '土豆红烧肉', '92dda1cd-d444-4e31-9df8-56ad9d643da4.jpg', 1592424257193775105, 1638091132086816769, NULL, NULL, 7, 48.00, '2023-04-10 12:08:30');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint(0) NOT NULL COMMENT '主键',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '姓名',
  `phone` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '手机号',
  `sex` varchar(2) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '性别',
  `id_number` varchar(18) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '身份证号',
  `avatar` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '头像',
  `status` int(0) NULL DEFAULT 0 COMMENT '状态 0:禁用，1:正常',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '用户信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1592351848277245953, NULL, '19958215293', NULL, NULL, NULL, 1);
INSERT INTO `user` VALUES (1592358647202091009, NULL, '18142079681', NULL, NULL, NULL, 1);
INSERT INTO `user` VALUES (1592424257193775105, NULL, '13017190127', NULL, NULL, NULL, 1);
INSERT INTO `user` VALUES (1594567490367668226, NULL, '18142079684', NULL, NULL, NULL, 1);
INSERT INTO `user` VALUES (1604315552568889346, NULL, '19958215291', NULL, NULL, NULL, 1);
INSERT INTO `user` VALUES (1641361748164395009, NULL, '18152726372', NULL, NULL, NULL, 1);

SET FOREIGN_KEY_CHECKS = 1;
