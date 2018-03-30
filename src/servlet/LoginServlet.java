package servlet;

import utils.DBUtil;
import com.mysql.jdbc.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        String method = request.getParameter("method");
        Connection connect = DBUtil.getConnect();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        try {
            Statement statement = connect.createStatement();
            if (null == method || "".equals(method)) {
                response.getWriter().append("方法调用错误");
            } else if (method.equals("add")) {
                String sqlInsertPass = "INSERT INTO " + DBUtil.TABLE_NAME + " (userId,username,password) VALUES ('" + UUID.randomUUID() + "','" + username + "','" + password + "')";
                // 更新类操作返回一个int类型的值，代表该操作影响到的行数
                int row1 = statement.executeUpdate(sqlInsertPass); // 插入帐号密码
                if (row1 == 1) {
                    response.getWriter().append("数据库添加成功");
                } else if (row1 == 2) {
                    response.getWriter().append("数据库添加失败");
                }
            } else if (method.equals("query")) {
                String query = "select username,password from " + DBUtil.TABLE_NAME;
                ResultSet resultSet = statement.executeQuery(query);
                String dbUsername = null;
                String dbPassword = null;
                while (resultSet.next()) {
                    dbUsername = resultSet.getString("username");
                    dbPassword = resultSet.getString("password");
                    if (username.equals(dbUsername) && password.equals(dbPassword)) {
                        response.getWriter().append("{username:" + dbUsername + ",password:" + dbPassword + "}");
                        break;
                    }
                }
                if (!username.equals(dbUsername) || !password.equals(dbPassword)) {
                    response.getWriter().append("<h1>你查询的用户不存在!<h1>");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
