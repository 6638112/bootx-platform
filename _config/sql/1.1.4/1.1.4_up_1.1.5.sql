SET FOREIGN_KEY_CHECKS = 0;

UPDATE `iam_perm_menu` SET `client_code` = 'admin', `parent_id` = 1431152689832525824, `title` = '审计日志', `name` = 'auditLog', `perm_code` = '', `effect` = b'0', `icon` = '', `hidden` = b'0', `hide_children_in_menu` = b'0', `component` = 'RouteView', `component_name` = NULL, `path` = '/system/log', `redirect` = '', `sort_no` = 0, `menu_type` = 1, `leaf` = NULL, `keep_alive` = b'1', `target_outside` = b'0', `hidden_header_content` = b'0', `admin` = b'0', `remark` = NULL, `creator` = 1399985191002447872, `create_time` = '2021-10-25 17:29:09', `last_modifier` = 1399985191002447872, `last_modified_time` = '2022-10-07 12:57:05', `version` = 1, `deleted` = 0 WHERE `id` = 1452567897717321728;
UPDATE `iam_perm_menu` SET `client_code` = 'admin', `parent_id` = NULL, `title` = '商品管理', `name` = 'GoodsManager', `perm_code` = NULL, `effect` = b'0', `icon` = 'car', `hidden` = b'1', `hide_children_in_menu` = b'0', `component` = 'RouteView', `component_name` = NULL, `path` = '/goods', `redirect` = '', `sort_no` = 0, `menu_type` = 0, `leaf` = NULL, `keep_alive` = b'1', `target_outside` = b'0', `hidden_header_content` = b'0', `admin` = b'0', `remark` = NULL, `creator` = 1399985191002447872, `create_time` = '2022-05-10 11:01:40', `last_modifier` = 1399985191002447872, `last_modified_time` = '2022-10-07 12:38:50', `version` = 2, `deleted` = 0 WHERE `id` = 1523860792695283712;
INSERT INTO `notice_message_template` VALUES (1580083314274070528, 'BpmTaskBack', '流程任务回退事件', '流程任务驳回事件', 0, '', 1399985191002447872, '2022-10-12 14:29:53', 1399985191002447872, '2022-10-12 14:30:11', 1, 0);

SET FOREIGN_KEY_CHECKS = 1;