课程设计
==========

#校园社团论坛

##1.1 编写目的
* 本文档是校园论坛需求调研报告，供需求分析人员进行项目需求分析时使用；
* 本文档可以作为项目验收标准之一；
* 本文档可以作为软件维护的参考资料；

##1.2 背景说明
开发软件名称:校园社团论坛
项目任务提出者:由小组所有成员共同讨论而提出
目标用户:大学生

##1.3 系统总体目标
本项目主要目标是搭建一个基于web的校园bbs论坛，为高校社团之间的交流提供一个平台，通过这个平台，大学生可以轻松找到自己喜欢的社团组织，不同学校之间的社团也可以通过这个bbs促进交流。
    同时，本项目结束时，所有开发小组成员都应该能收获web开发经验，了解项目开发的流程。

##1.4 数据库逻辑结构设计
###1.4.1 逻辑结构设计
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

###1.4.2 物理结构设计

bbs_user表<br>
字段	数据类型	说明<br>
userName	varchar(32)	用户名(主键)<br>
passwd	varchar(64)	密码<br>

user_info表<br>
字段	数据类型	说明<br>
userName	varchar(32)	用户名(主键) （bbs_user外键）<br>
nickName	varchar(32)	昵称<br>
head	varchar(64)	头像路径<br>
emailAccount	varchar(64)	邮箱<br>
emailServerId	Int(11)	邮箱所属的邮件服务器，外键<br>

club表<br>
字段	数据类型	说明<br>
clubName	varchar(32)	板块名（主键）<br>
clubIcon	varchar(64)	图标<br>
clubDescribe	varchar(256)	版块描述<br>
clubTypeId	int(11)	帖子类型（club_type外键）<br>

club_admin表<br>
字段	数据类型	说明<br>
userName	varchar(32)	用户名(主键) （bbs_user外键）<br>
clubName	varchar(32)	版块名(主键) （club外键）<br>

post表<br>
字段	数据类型	说明<br>
postId	int(11)	帖子ID(主键)<br>
postTitle	varchar(80)	帖子标题<br>
postContent	varchar(2048)	帖子内容<br>
postTime	timestamp	发帖时间<br>
lastTime	timestamp	最后回复的时间<br>
userName	varchar(32)	用户名（bbs_user外键）<br>
clubName	varchar(32)	版块名（club外键）<br>
postTypeId	int(11)	帖子类型id（post_type外键)<br>

reply表<br>
字段	数据类型	说明<br>
replyId	int(11)	回复id(主键)<br>
replyContent	varchar(512)	回复内容<br>
replyTime	timestamp	回复时间<br>
postId	int(11)	帖子id（post外键）<br>
userName	varchar(32)	用户名（bbs_user外键）<br>

club_type表<br>
字段	数据类型	说明<br>
clubTypeId	int(11)	板块类型id（主键）<br>
clubType	varchar(64)	板块类型<br>

post_type表<br>
字段	数据类型	说明<br>
postTypeId	int(11)	帖子类型id（主键）<br>
postType	varchar(64)	帖子类型名<br>
color	varchar(16)	标题颜色<br>

system_admin表<br>
字段	数据类型	说明<br>
postTypeId	int(11)	帖子类型id（主键）<br>
postType	varchar(64)	帖子类型名<br>
color	varchar(16)	标题颜色<br>


##最终效果请参考http://www.esaulu.cn/bbs
