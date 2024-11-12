package boardDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import boardDTO.shw1013BoardDTO;

public class PostDAO {
    String driver = "oracle.jdbc.driver.OracleDriver";
    String url = "jdbc:oracle:thin:@localhost:1521:xe";
    String userid = "scott";
    String passwd = "tiger";

    public PostDAO() {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<shw1013BoardDTO> postView(int boardNo) {
        ArrayList<shw1013BoardDTO> lsh1208_list = new ArrayList<shw1013BoardDTO>();
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String sql = "SELECT BOARDNO, TITLE, CONTENT, WRITER, EMPNO, REGDATE " +
                     "FROM BOARD " +
                     "WHERE BOARDNO = ?";

        try {
            con = DriverManager.getConnection(url, userid, passwd);
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, boardNo);  // boardNo 매개변수 설정
            rs = pstmt.executeQuery();

            while (rs.next()) {
                shw1013BoardDTO dto = new shw1013BoardDTO();
                dto.setBoardNo(rs.getInt("BOARDNO"));
                dto.setTitle(rs.getString("TITLE"));
                dto.setContent(rs.getString("CONTENT"));
                dto.setWriter(rs.getString("WRITER"));
                dto.setEmpNo(rs.getInt("EMPNO"));
                dto.setRegDate(rs.getDate("REGDATE"));
                
                lsh1208_list.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return lsh1208_list;
    }
}