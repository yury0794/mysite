package kr.ac.sungkyul.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.ac.sungkyul.mysite.vo.BoardVo;

public class BoardDao {
	private Connection getConnection() throws SQLException {
		Connection conn = null;

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			conn = DriverManager.getConnection(url, "webdb", "webdb");

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return conn;
	}

	public int delete(Long no, Long userNo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int count = 0;

		try {
			conn = getConnection();

			String sql = "delete from board where no=? and user_no=?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, no);
			pstmt.setLong(2, userNo);

			count = pstmt.executeUpdate(); // 완벽한 쿼리문이 아니기 때문에 괄호 안에 sql을 넣지 않는다

		} catch (SQLException e) {
			System.out.println("에러 : " + e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return count;
	}

	public void replyInsert(BoardVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();

			String sql = "insert into board values(seq_board.nextval, ?, ?, "
					+ "sysdate, 0, ?, nvl((select max(order_no) "
					+ "from board where group_no=? and depth=?),0)+1, ?, ?)";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setLong(3, vo.getGroupNo());
			pstmt.setLong(4, vo.getGroupNo());
			pstmt.setLong(5, vo.getDepth());
			pstmt.setLong(6, vo.getDepth());
			pstmt.setLong(7, vo.getUserNo());

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void insert(BoardVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();

			String sql = "insert into board " + "values(seq_board.nextval, ?, ?, sysdate, 0, "
					+ "nvl((select max(group_no) from board),0)+1, 1, 1, ?)";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setLong(3, vo.getUserNo());

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void updateCount(Long no) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();

			String sql = null;
			sql = "update board set view_count=view_count+1 where no=?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, no);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) {
					pstmt.cancel();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void update(BoardVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();

			Long no = vo.getNo();
			String title = vo.getTitle();
			String content = vo.getContent();

			String sql = null;
			sql = "update board set title=?, content=? where no=?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, title);
			pstmt.setString(2, content);
			pstmt.setLong(3, no);

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) {
					pstmt.cancel();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public BoardVo get(Long boardNo) {
		BoardVo vo = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();

			String sql = "select no, title, content, group_no, order_no, " + "depth, user_no from board where no = ?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, boardNo);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				Long no = rs.getLong(1);
				String title = rs.getString(2);
				String content = rs.getString(3);
				Long groupNo = rs.getLong(4);
				Long orderNo = rs.getLong(5);
				Long depth = rs.getLong(6);
				Long userNo = rs.getLong(7);

				vo = new BoardVo();
				vo.setNo(no);
				vo.setTitle(title);
				vo.setContent(content);
				vo.setGroupNo(groupNo);
				vo.setOrderNo(orderNo);
				vo.setDepth(depth);
				vo.setUserNo(userNo);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return vo;
	}

	public List<BoardVo> getList(Integer page, Integer row, String keyword) {
		List<BoardVo> list = new ArrayList<BoardVo>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();
			String sql = null;

			if (keyword == null || "".equals(keyword)) {
				sql = "select no, title, name, view_count, " + "to_char(reg_date, 'yyyy-mm-dd pm hh:mi:ss'), depth, "
						+ "user_no, rn from (select c.*, rownum as rn "
						+ "from (select a.*, b.name from board a, users b "
						+ "where a.user_no=b.no order by group_no desc, depth, "
						+ "order_no desc) c) where (?-1)*?+1 <= rn and rn <= ?*?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, page);
				pstmt.setInt(2, row);
				pstmt.setInt(3, page);
				pstmt.setInt(4, row);
				
			} else {
				sql = "select no, title, name, view_count, " + "to_char(reg_date, 'yyyy-mm-dd pm hh:mi:ss'), depth, "
						+ "user_no, rn from (select c.*, rownum as rn "
						+ "from (select a.*, b.name from board a, users b "
						+ "where a.user_no=b.no and (a.title like ? " + "or a.content like ?) order by group_no desc, "
						+ "depth, order_no desc) c) where (?-1)*?+1 <= rn " + "and rn <= ?*?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, "%" + keyword + "%");
				pstmt.setString(2, "%" + keyword + "%");
				pstmt.setInt(3, page);
				pstmt.setInt(4, row);
				pstmt.setInt(5, page);
				pstmt.setInt(6, row);
			}
			
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Long no = rs.getLong(1);
				String title = rs.getString(2);
				String name = rs.getString(3);
				Long viewCount = rs.getLong(4);
				String regDate = rs.getString(5);
				Long depth = rs.getLong(6);
				Long userNo = rs.getLong(7);
				Long rownum = rs.getLong(8);

				BoardVo vo = new BoardVo();
				vo.setNo(no);
				vo.setTitle(title);
				vo.setName(name);
				vo.setViewCount(viewCount);
				vo.setRegDate(regDate);
				vo.setDepth(depth);
				vo.setUserNo(userNo);
				vo.setRownum(rownum);

				list.add(vo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	public int getList(String keyword) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count = 0;

		try {
			conn = getConnection();

			String sql = null;

			if (keyword == null || "".equals(keyword)) {
				sql = "select count(*) from board";
				pstmt = conn.prepareStatement(sql);
			} else {
				sql = "select count(*) from board where title like ? " + "or content like ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, "%" + keyword + "%");
				pstmt.setString(2, "%" + keyword + "%");
			}
			rs = pstmt.executeQuery();

			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return count;
	}

}
