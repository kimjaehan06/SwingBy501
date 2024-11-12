package boardUI;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import boardDAO.PostDAO;
import boardDAO.kjh0313boardDAO;
import boardDTO.shw1013BoardDTO;

public class kjh0313boardUI extends JPanel {
    private JTable boardTable;
    private DefaultTableModel tableModel;

    public kjh0313boardUI() {
        setLayout(new BorderLayout());

        // 테이블 모델 설정 (컬럼명 지정)
        String[] columnNames = {"번호", "제목", "작성자"};
        tableModel = new DefaultTableModel(columnNames, 0);
        boardTable = new JTable(tableModel);

        // 스크롤 패널에 테이블 추가
        JScrollPane scrollPane = new JScrollPane(boardTable);
        add(scrollPane, BorderLayout.CENTER);
        
        // 버튼 패널 생성
        JPanel buttonPanel = new JPanel();
        JButton dummyButton = new JButton("더미 버튼");
        buttonPanel.add(dummyButton);

        // 버튼 패널을 하단에 추가
        add(buttonPanel, BorderLayout.SOUTH);

        // 테이블에 클릭 이벤트 추가
        boardTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = boardTable.getSelectedRow(); // 클릭된 행의 인덱스를 가져옴
                if (selectedRow != -1) {
                    // 선택된 행의 게시물 번호 가져오기
                    int boardNo = (int) tableModel.getValueAt(selectedRow, 0);

                    // PostDAO를 사용하여 boardNo에 해당하는 게시물 데이터를 조회하고 PostWindow로 표시
                    PostDAO dao = new PostDAO();
                    List<shw1013BoardDTO> boardList = dao.postView(boardNo);

                    if (!boardList.isEmpty()) {
                        shw1013BoardDTO post = boardList.get(0);
                        new PostWindow(post); // PostWindow 생성자에 게시물 데이터 전달
                    } else {
                        JOptionPane.showMessageDialog(null, "해당 게시물을 찾을 수 없습니다.", "오류", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // 데이터 로드 및 테이블에 추가
        loadData();
    }

    private void loadData() {
        kjh0313boardDAO dao = new kjh0313boardDAO();
        List<shw1013BoardDTO> boardList = dao.getBoardList();

        for (shw1013BoardDTO board : boardList) {
            Object[] rowData = {
                board.getBoardNo(),
                board.getTitle(),
                board.getWriter()
            };
            tableModel.addRow(rowData);
        }
    }

    // 메인 메서드 (단독 실행을 위한 테스트)
    public static void main(String[] args) {
        JFrame frame = new JFrame("게시판 목록");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.add(new kjh0313boardUI());
        frame.setVisible(true);
    }
}
