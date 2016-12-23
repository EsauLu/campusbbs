package campusbbs.model.dao.impl;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlProducer;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.xml.sax.InputSource;

import campusbbs.model.dao.UserDAO;
import campusbbs.model.utils.DBUtils;
import campusbbs.model.vo.User;
import campusbbs.model.vo.UserInfo;

@RunWith(Parameterized.class)
public class UserDAOImplTest {

	private UserDAO userDAO;
	private String userName;
	private String passwd;
	private boolean exists;
	private User user;
	
	@Parameters
    public static Collection<Object[]> data()
    {
		User user1=new User();
		UserInfo info1=new UserInfo();
		
		user1.setUserName("user1");
		user1.setPasswd("passwd1");
		
		info1.setUserName("user1");
		info1.setNickname("昵称1");
		info1.setEmailAccount("1111111111");
		info1.setEmailServerId(1);
		info1.setHead("user1.jpg");
		
		user1.setUserInfo(info1);
		
		
		User user2=new User();
		UserInfo info2=new UserInfo();
		
		user2.setUserName("test2");
		user2.setPasswd("123456");
		
		info2.setUserName("test2");
		info2.setNickname("nickname2");
		info2.setEmailAccount("test2");
		info2.setEmailServerId(2);
		info2.setHead("test2.jpg");
		
		user2.setUserInfo(info2);
		
        return Arrays.asList(new Object[][] { 
				{ "test1","123456",true, user1},
				{ "test","123456",false, user2}
			}
		);
    }	

	public UserDAOImplTest(String userName, String passwd,boolean exists,User user) {
		super();
		System.out.println(userName+","+passwd);
		this.userName = userName;
		this.passwd = passwd;
		this.exists = exists;
		this.user = user;
	}

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		
		//备份数据库
//		backupAllTables();
		
		userDAO=new UserDAOImpl();
		
		try {
			// 创建一个 DBunit 的 Connection，须要传入一个 jdbc 的 Connection
			IDatabaseConnection con = new DatabaseConnection(
			        DBUtils.getDBConnection());
			
			//FlatXmlDataSet 用来获取基于属性存储的的属性值 XmlDataSet 用来获取基于节点类型存储的属性值
			IDataSet ds = new FlatXmlDataSet(new FlatXmlProducer( new InputSource(new FileInputStream("bbs_user.xml") )));
			
			// 会将数据库中的数据清空，并且把测试数据插入
			DatabaseOperation.CLEAN_INSERT.execute(con, ds);
		} catch (DatabaseUnitException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}

	@After
	public void tearDown() throws Exception {
		//恢复数据库
		resume();
	}

	@Test
	public void testSaveUser() {

		assertEquals(exists,userDAO.saveUser(user));

        User insert = userDAO.findUserByName(user.getUserName());
        UserInfo userInfo = userDAO.findUserInfoByName(user.getUserName());
        System.out.println(user);
        System.out.println(insert);
        System.out.println(userInfo);
             
        assertEquals(true,insert!=null);
        assertEquals(user.getUserName(), insert.getUserName());            
        assertEquals(user.getPasswd(), insert.getPasswd());

        assertEquals(user.getUserInfo().getNickname(), userInfo.getNickname());    
        assertEquals(user.getUserInfo().getEmailAccount(), userInfo.getEmailAccount());    
        assertEquals(user.getUserInfo().getEmailServerId(), userInfo.getEmailServerId());    
        assertEquals(user.getUserInfo().getHead(), userInfo.getHead());    
        	
	}

	@Test
	public void testDeleteUser() {
		
        assertEquals(exists, userDAO.deleteUser(userName));
        
	}

	@Test
	public void testUpdateUserInfo() {

		UserInfo tem=user.getUserInfo();
		tem.setUserName(userName);
        assertEquals(exists, userDAO.updateUserInfo(tem));
		
	}

	@Test
	public void testFindUserInfoByName() {

        User user = userDAO.findUserByName(userName);
            
        assertEquals(exists,user!=null);
        
        if(exists){
            assertEquals(userName, user.getUserName());
            assertEquals(passwd, user.getPasswd());
        }

	}

    /**
     * 备份所有的数据表
     */
    public void backupAllTables() {
        try {
            // 创建一个 DBunit 的 Connection，须要传入一个 jdbc 的 Connection
            IDatabaseConnection conn = new DatabaseConnection(
                    DBUtils.getDBConnection());
            // 根据 conn 创建相应的 DataSet ，这个 DataSet 包含了所有的数据表
            IDataSet ds = conn.createDataSet();
            // 将 ds 中的数据通过 FlatXmlDataSet 这个格式写到 xml 文件中
            FlatXmlDataSet.write(ds, new FileWriter(new File(
                    "test.xml")));
        } catch (DatabaseUnitException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
	
	/**
	 * 恢复数据库
	 */
    public void resume() {
        try {
            // 创建 dbunit 的 Connnection ，需要传入一个数据库的 connection 作为参数
            IDatabaseConnection conn = new DatabaseConnection(
                    DBUtils.getDBConnection());
            // 根据备份文件创建 dataset
            IDataSet ds = new FlatXmlDataSet(new FlatXmlProducer(
                    new InputSource(new FileInputStream("test.xml"))));
            DatabaseOperation.CLEAN_INSERT.execute(conn, ds);
        } catch (DataSetException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DatabaseUnitException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
