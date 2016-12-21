课程设计
==========

#校园社团论坛

##1.1	编写目的
* 本文档是校园论坛需求调研报告，供需求分析人员进行项目需求分析时使用；
* 本文档可以作为项目验收标准之一；
* 本文档可以作为软件维护的参考资料；

##1.2	背景说明
开发软件名称:校园社团论坛
项目任务提出者:由小组所有成员共同讨论而提出
目标用户:大学生

##1.3	系统总体目标
本项目主要目标是搭建一个基于web的校园bbs论坛，为高校社团之间的交流提供一个平台，通过这个平台，大学生可以轻松找到自己喜欢的社团组织，不同学校之间的社团也可以通过这个bbs促进交流。
    同时，本项目结束时，所有开发小组成员都应该能收获web开发经验，了解项目开发的流程。

##1.4	数据库逻辑结构设计
###1.4.1	逻辑结构设计
E-R图中有实体：用户，用户信息，账号邮箱服务器，版块，版块分类，主题帖，主题帖分类，回复，系统管理员。
以下对实体进行分解：

* 用户(用户名，密码)
* 用户信息(用户名，昵称，头像路径，邮箱账号名，邮箱服务器)
* 邮箱服务器(ID，邮箱域名)
* 版块(版块名，图标路径，版块描述信息, 类型ID)
* 版块分类(类型ID，类型名)
* 主题帖(帖子ID，标题，内容，发表时间，最后回复时间，用户名，版块名，类型ID)
* 主题帖分类(类型ID，标题颜色，类型名)
* 回复(回复ID,内容，回复时间，帖子ID, 用户名)
* 系统管理员(管理员账号名，密码)

bbs_user表
字段	数据类型	说明
userName	varchar(32)	用户名(主键)
passwd	varchar(64)	密码

user_info表
字段	数据类型	说明
userName	varchar(32)	用户名(主键) （bbs_user外键）
nickName	varchar(32)	昵称
head	varchar(64)	头像路径
emailAccount	varchar(64)	邮箱
emailServerId	Int(11)	邮箱所属的邮件服务器，外键

club表
字段	数据类型	说明
clubName	varchar(32)	板块名（主键）
clubIcon	varchar(64)	图标
clubDescribe	varchar(256)	版块描述
clubTypeId	int(11)	帖子类型（club_type外键）

club_admin表
字段	数据类型	说明
userName	varchar(32)	用户名(主键) （bbs_user外键）
clubName	varchar(32)	版块名(主键) （club外键）

post表
字段	数据类型	说明
postId	int(11)	帖子ID(主键)
postTitle	varchar(80)	帖子标题
postContent	varchar(2048)	帖子内容
postTime	timestamp	发帖时间
lastTime	timestamp	最后回复的时间
userName	varchar(32)	用户名（bbs_user外键）
clubName	varchar(32)	版块名（club外键）
postTypeId	int(11)	帖子类型id（post_type外键)

reply表
字段	数据类型	说明
replyId	int(11)	回复id(主键)
replyContent	varchar(512)	回复内容
replyTime	timestamp	回复时间
postId	int(11)	帖子id（post外键）
userName	varchar(32)	用户名（bbs_user外键）

club_type表
字段	数据类型	说明
clubTypeId	int(11)	板块类型id（主键）
clubType	varchar(64)	板块类型

post_type表
字段	数据类型	说明
postTypeId	int(11)	帖子类型id（主键）
postType	varchar(64)	帖子类型名
color	varchar(16)	标题颜色

system_admin表
字段	数据类型	说明
postTypeId	int(11)	帖子类型id（主键）
postType	varchar(64)	帖子类型名
color	varchar(16)	标题颜色


##最终效果请参考http://www.esaulu.cn/bbs
