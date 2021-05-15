package cn.tedu;

import com.alibaba.druid.pool.DruidDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

public class DBUtils {

  private static DruidDataSource ds;

    static {
        //读取jdbc.properties里面的数据
        //常见读取配置文件的对象
        Properties p = new Properties();
        //得到文件输入流 通过类加载器获取文件的输入流 这种方式
        //会自动查找resources目录下的资源文件并返回输入流
        InputStream ips = DBUtils.class.getClassLoader().getResourceAsStream("jdbc.properties");
        //让文件和读取配置文件的队形建立关系 异常抛出
        try {
            p.load(ips);//文件和对象关联
        } catch (IOException e) {
            e.printStackTrace();
        }
        //开启读取配置文件中的数据
        String driver =p.getProperty("db.driver");
        //从配置文件中只能读取到字符串类型
        String url = p.getProperty("db.url");
        String username=p.getProperty("db.username");
        String password=p.getProperty("db.password");
        //创建连接池对象
        ds = new DruidDataSource();
        //设置连接池信息
        ds.setDriverClassName(driver);
        ds.setUrl(url);
        ds.setUsername(username);
        ds.setPassword(password);
        //读取配置文件中的最大连接数量和初始连接数量
        String maxSize = p.getProperty("db.maxActive");
        String initSize = p.getProperty("db.initialSize");
        ds.setMaxActive(Integer.parseInt(maxSize));
        ds.setInitialSize(Integer.parseInt(initSize));
    }
    public static Connection getConn()throws Exception{

        //获取连接
        Connection conn=ds.getConnection();
        return conn;
    }
}
