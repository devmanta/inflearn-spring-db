package hello.jdbc.repository;

import hello.jdbc.connection.DBConnectionUtil;
import hello.jdbc.domain.Member;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import lombok.extern.slf4j.Slf4j;

/**
 * JDBC -DriverManager 사용
 */
@Slf4j
public class MemberRepositoryV0 {

    public Member save(Member member) throws SQLException {
        String sql = "insert into member(member_id, money) values (?, ?)";

        Connection conn = null;
        PreparedStatement pstmt = null;

        try{
            conn = this.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, member.getMemberId());
            pstmt.setInt(2, member.getMoney());
            pstmt.executeUpdate();
            return member;
        }catch(Exception e) {
            log.error("db error", e);
            throw e;
        }finally {
            this.close(conn, pstmt, null);
        }

    }

    private Connection getConnection() {
        return DBConnectionUtil.getConnection();
    }

    private void close(Connection conn, Statement stmt, ResultSet rs) {
        if(rs != null){
            try{
                rs.close();
            }catch(SQLException e){
                log.error("MemberRepositoryV0.close ERROR!!" + e);
            }
        }
        if(stmt != null){
            try{
                stmt.close();
            }catch(SQLException e){
                log.error("MemberRepositoryV0.close ERROR!!" + e);
            }
        }
        if(conn != null){
            try{
                conn.close();
            }catch(SQLException e){
                log.error("MemberRepositoryV0.close ERROR!!" + e);
            }
        }
    }

}
