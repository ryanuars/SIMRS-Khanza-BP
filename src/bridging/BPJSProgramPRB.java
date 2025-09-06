/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgResepObat.java
 *
 * Created on 31 Mei 10, 11:27:40
 */

package bridging;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fungsi.WarnaTable;
import fungsi.WarnaTable2;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.URI;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRGraphics2DExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.*;


/**
 *
 * @author perpustakaan
 */
public final class BPJSProgramPRB extends javax.swing.JDialog {
    private final DefaultTableModel tabMode,tabMode2;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps,ps2;
    private ResultSet rs,rs2;
    private BPJSCekReferensiDokterDPJP dokter=new BPJSCekReferensiDokterDPJP(null,false);
    private BPJSCekReferensiDiagnosaPRB diagnosa=new BPJSCekReferensiDiagnosaPRB(null,false);
    private int i=0,z=0;
    private WarnaTable2 warna=new WarnaTable2();
    private double[] jumlah;
    private String[] kodeobat,namaobat,signa1,signa2;
    private HttpHeaders headers;
    private HttpEntity requestEntity;
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root;
    private JsonNode nameNode;
    private JsonNode response;
    private ApiBPJS api=new ApiBPJS();
    private String URL="",link="",utc="",requestJson="",obat="";

    /** Creates new form DlgResepObat 
     *@param parent
     *@param modal*/
    public BPJSProgramPRB(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(628,674);        
        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.RM","Nama Pasien","Alamat","Email","No.Kartu","No.SEP","No.SRB",
            "Tgl.PRB","Kode DPJP","Dokter DPJP","Kode PRB","Program PRB","Keterangan","Saran"}){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbProgramPRB.setModel(tabMode);

        tbProgramPRB.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbProgramPRB.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 15; i++) {
            TableColumn column = tbProgramPRB.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(105);
            }else if(i==1){
                column.setPreferredWidth(70);
            }else if(i==2){
                column.setPreferredWidth(150);
            }else if(i==3){
                column.setPreferredWidth(180);
            }else if(i==4){
                column.setPreferredWidth(150);
            }else if(i==5){
                column.setPreferredWidth(85);
            }else if(i==6){
                column.setPreferredWidth(122);
            }else if(i==7){
                column.setPreferredWidth(80);
            }else if(i==8){
                column.setPreferredWidth(65);
            }else if(i==9){
                column.setPreferredWidth(75);
            }else if(i==10){
                column.setPreferredWidth(150);
            }else if(i==11){
                column.setPreferredWidth(60);
            }else if(i==12){
                column.setPreferredWidth(150);
            }else if(i==13){
                column.setPreferredWidth(150);
            }else if(i==14){
                column.setPreferredWidth(150);
            }
        }
        tbProgramPRB.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabMode2=new DefaultTableModel(null,new Object[]{
            "Jumlah","Kode Obat","Nama Obat","Signa 1","Signa 2"}){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if ((colIndex==0)||(colIndex==3)||(colIndex==4)) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbObat.setModel(tabMode2);

        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 5; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(45);
            }else if(i==1){
                column.setPreferredWidth(80);
            }else if(i==2){
                column.setPreferredWidth(325);
            }else if(i==3){
                column.setPreferredWidth(90);
            }else if(i==4){
                column.setPreferredWidth(90);
            }
        }
        warna.kolom=0;
        tbObat.setDefaultRenderer(Object.class,warna);

        NoRawat.setDocument(new batasInput((byte)17).getKata(NoRawat));
        Alamat.setDocument(new batasInput((int)200).getKata(Alamat));
        Email.setDocument(new batasInput((byte)40).getKata(Email));
        Saran.setDocument(new batasInput((byte)100).getKata(Saran));
        Keterangan.setDocument(new batasInput((byte)100).getKata(Keterangan));
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        ObatPRB.setDocument(new batasInput((byte)100).getKata(ObatPRB));
        
        if(koneksiDB.CARICEPAT().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil();
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil();
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil();
                    }
                }
            });
        }  
        ChkInput.setSelected(false);
        isForm();
        
        dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(dokter.getTable().getSelectedRow()!= -1){  
                    KdDPJP.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                    NmDPJP.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),2).toString());
                    KdDPJP.requestFocus();             
                }  
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
        
        dokter.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    dokter.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        diagnosa.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(diagnosa.getTable().getSelectedRow()!= -1){  
                    KdProgram.setText(diagnosa.getTable().getValueAt(diagnosa.getTable().getSelectedRow(),1).toString());
                    NmProgram.setText(diagnosa.getTable().getValueAt(diagnosa.getTable().getSelectedRow(),2).toString());
                    KdProgram.requestFocus();             
                }  
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
        
        diagnosa.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    diagnosa.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        try {
            link=koneksiDB.URLAPIBPJS();
        } catch (Exception e) {
            System.out.println("E : "+e);
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnSurat = new javax.swing.JMenuItem();
        MnTampilkanObatPRB = new javax.swing.JMenuItem();
        MnKirimWA = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbProgramPRB = new widget.Table();
        jPanel3 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnPrint = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        BtnKeluar = new widget.Button();
        panelGlass9 = new widget.panelisi();
        jLabel19 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel21 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        PanelInput = new javax.swing.JPanel();
        FormInput = new widget.PanelBiasa();
        NoRawat = new widget.TextBox();
        NoRM = new widget.TextBox();
        KdDPJP = new widget.TextBox();
        NmDPJP = new widget.TextBox();
        jLabel3 = new widget.Label();
        jLabel13 = new widget.Label();
        btnDokterDPJP = new widget.Button();
        NmPasien = new widget.TextBox();
        jLabel4 = new widget.Label();
        NoKartu = new widget.TextBox();
        jLabel5 = new widget.Label();
        Alamat = new widget.TextBox();
        Email = new widget.TextBox();
        jLabel9 = new widget.Label();
        jLabel10 = new widget.Label();
        NoSEP = new widget.TextBox();
        jLabel14 = new widget.Label();
        KdProgram = new widget.TextBox();
        NmProgram = new widget.TextBox();
        btnProgramPRB = new widget.Button();
        jLabel8 = new widget.Label();
        Keterangan = new widget.TextBox();
        jLabel11 = new widget.Label();
        Saran = new widget.TextBox();
        jLabel15 = new widget.Label();
        ObatPRB = new widget.TextBox();
        BtnCari2 = new widget.Button();
        Scroll1 = new widget.ScrollPane();
        tbObat = new widget.Table();
        jLabel20 = new widget.Label();
        Tanggal = new widget.Tanggal();
        ChkInput = new widget.CekBox();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnSurat.setBackground(new java.awt.Color(255, 255, 254));
        MnSurat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSurat.setForeground(new java.awt.Color(50, 50, 50));
        MnSurat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnSurat.setText("Surat Rujuk Balik");
        MnSurat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnSurat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnSurat.setName("MnSurat"); // NOI18N
        MnSurat.setPreferredSize(new java.awt.Dimension(160, 26));
        MnSurat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSuratActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnSurat);

        MnTampilkanObatPRB.setBackground(new java.awt.Color(255, 255, 254));
        MnTampilkanObatPRB.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnTampilkanObatPRB.setForeground(new java.awt.Color(50, 50, 50));
        MnTampilkanObatPRB.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnTampilkanObatPRB.setText("Tampilkan Obat PRB");
        MnTampilkanObatPRB.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnTampilkanObatPRB.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnTampilkanObatPRB.setName("MnTampilkanObatPRB"); // NOI18N
        MnTampilkanObatPRB.setPreferredSize(new java.awt.Dimension(160, 26));
        MnTampilkanObatPRB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnTampilkanObatPRBActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnTampilkanObatPRB);

        MnKirimWA.setBackground(new java.awt.Color(255, 255, 254));
        MnKirimWA.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnKirimWA.setForeground(new java.awt.Color(50, 50, 50));
        MnKirimWA.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnKirimWA.setText("Kirim via WA");
        MnKirimWA.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnKirimWA.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnKirimWA.setName("MnKirimWA"); // NOI18N
        MnKirimWA.setPreferredSize(new java.awt.Dimension(160, 26));
        MnKirimWA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnKirimWAActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnKirimWA);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Program PRB di VClaim ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setComponentPopupMenu(jPopupMenu1);
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbProgramPRB.setAutoCreateRowSorter(true);
        tbProgramPRB.setComponentPopupMenu(jPopupMenu1);
        tbProgramPRB.setName("tbProgramPRB"); // NOI18N
        tbProgramPRB.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbProgramPRBMouseClicked(evt);
            }
        });
        tbProgramPRB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbProgramPRBKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbProgramPRB);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(44, 100));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan.setMnemonic('S');
        BtnSimpan.setText("Simpan");
        BtnSimpan.setToolTipText("Alt+S");
        BtnSimpan.setName("BtnSimpan"); // NOI18N
        BtnSimpan.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpanActionPerformed(evt);
            }
        });
        BtnSimpan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSimpanKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnSimpan);

        BtnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Cancel-2-16x16.png"))); // NOI18N
        BtnBatal.setMnemonic('B');
        BtnBatal.setText("Baru");
        BtnBatal.setToolTipText("Alt+B");
        BtnBatal.setName("BtnBatal"); // NOI18N
        BtnBatal.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBatalActionPerformed(evt);
            }
        });
        BtnBatal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnBatalKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnBatal);

        BtnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        BtnHapus.setMnemonic('H');
        BtnHapus.setText("Hapus");
        BtnHapus.setToolTipText("Alt+H");
        BtnHapus.setName("BtnHapus"); // NOI18N
        BtnHapus.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnHapusActionPerformed(evt);
            }
        });
        BtnHapus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnHapusKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnHapus);

        BtnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/inventaris.png"))); // NOI18N
        BtnEdit.setMnemonic('G');
        BtnEdit.setText("Ganti");
        BtnEdit.setToolTipText("Alt+G");
        BtnEdit.setName("BtnEdit"); // NOI18N
        BtnEdit.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEditActionPerformed(evt);
            }
        });
        BtnEdit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnEditKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnEdit);

        BtnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png"))); // NOI18N
        BtnPrint.setMnemonic('T');
        BtnPrint.setText("Cetak");
        BtnPrint.setToolTipText("Alt+T");
        BtnPrint.setName("BtnPrint"); // NOI18N
        BtnPrint.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrintActionPerformed(evt);
            }
        });
        BtnPrint.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPrintKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnPrint);

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(55, 30));
        panelGlass8.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(52, 30));
        panelGlass8.add(LCount);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar.setMnemonic('K');
        BtnKeluar.setText("Keluar");
        BtnKeluar.setToolTipText("Alt+K");
        BtnKeluar.setName("BtnKeluar"); // NOI18N
        BtnKeluar.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluarActionPerformed(evt);
            }
        });
        BtnKeluar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnKeluarKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnKeluar);

        jPanel3.add(panelGlass8, java.awt.BorderLayout.CENTER);

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel19.setText("Tanggal :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(60, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "18-03-2024" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass9.add(DTPCari1);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("s.d.");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(23, 23));
        panelGlass9.add(jLabel21);

        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "18-03-2024" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass9.add(DTPCari2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(290, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass9.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('4');
        BtnCari.setToolTipText("Alt+4");
        BtnCari.setName("BtnCari"); // NOI18N
        BtnCari.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariActionPerformed(evt);
            }
        });
        BtnCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCariKeyPressed(evt);
            }
        });
        panelGlass9.add(BtnCari);

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('M');
        BtnAll.setToolTipText("Alt+M");
        BtnAll.setName("BtnAll"); // NOI18N
        BtnAll.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAllActionPerformed(evt);
            }
        });
        BtnAll.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnAllKeyPressed(evt);
            }
        });
        panelGlass9.add(BtnAll);

        jPanel3.add(panelGlass9, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setPreferredSize(new java.awt.Dimension(400, 266));
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(440, 107));
        FormInput.setLayout(null);

        NoRawat.setEditable(false);
        NoRawat.setHighlighter(null);
        NoRawat.setName("NoRawat"); // NOI18N
        FormInput.add(NoRawat);
        NoRawat.setBounds(83, 10, 125, 23);

        NoRM.setEditable(false);
        NoRM.setHighlighter(null);
        NoRM.setName("NoRM"); // NOI18N
        FormInput.add(NoRM);
        NoRM.setBounds(210, 10, 95, 23);

        KdDPJP.setEditable(false);
        KdDPJP.setHighlighter(null);
        KdDPJP.setName("KdDPJP"); // NOI18N
        FormInput.add(KdDPJP);
        KdDPJP.setBounds(83, 70, 75, 23);

        NmDPJP.setEditable(false);
        NmDPJP.setHighlighter(null);
        NmDPJP.setName("NmDPJP"); // NOI18N
        FormInput.add(NmDPJP);
        NmDPJP.setBounds(160, 70, 170, 23);

        jLabel3.setText("No.Rawat :");
        jLabel3.setName("jLabel3"); // NOI18N
        FormInput.add(jLabel3);
        jLabel3.setBounds(0, 10, 80, 23);

        jLabel13.setText("Obat PRB :");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(0, 130, 80, 23);

        btnDokterDPJP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnDokterDPJP.setMnemonic('3');
        btnDokterDPJP.setToolTipText("Alt+3");
        btnDokterDPJP.setName("btnDokterDPJP"); // NOI18N
        btnDokterDPJP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDokterDPJPActionPerformed(evt);
            }
        });
        btnDokterDPJP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnDokterDPJPKeyPressed(evt);
            }
        });
        FormInput.add(btnDokterDPJP);
        btnDokterDPJP.setBounds(332, 70, 28, 23);

        NmPasien.setEditable(false);
        NmPasien.setHighlighter(null);
        NmPasien.setName("NmPasien"); // NOI18N
        FormInput.add(NmPasien);
        NmPasien.setBounds(307, 10, 255, 23);

        jLabel4.setText("No.Kartu :");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(555, 10, 70, 23);

        NoKartu.setEditable(false);
        NoKartu.setHighlighter(null);
        NoKartu.setName("NoKartu"); // NOI18N
        FormInput.add(NoKartu);
        NoKartu.setBounds(628, 10, 110, 23);

        jLabel5.setText("Alamat :");
        jLabel5.setName("jLabel5"); // NOI18N
        FormInput.add(jLabel5);
        jLabel5.setBounds(0, 40, 80, 23);

        Alamat.setHighlighter(null);
        Alamat.setName("Alamat"); // NOI18N
        Alamat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlamatKeyPressed(evt);
            }
        });
        FormInput.add(Alamat);
        Alamat.setBounds(83, 40, 265, 23);

        Email.setHighlighter(null);
        Email.setName("Email"); // NOI18N
        Email.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EmailKeyPressed(evt);
            }
        });
        FormInput.add(Email);
        Email.setBounds(398, 40, 145, 23);

        jLabel9.setText("Email :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(345, 40, 50, 23);

        jLabel10.setText("No.SEP :");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(545, 40, 50, 23);

        NoSEP.setEditable(false);
        NoSEP.setHighlighter(null);
        NoSEP.setName("NoSEP"); // NOI18N
        FormInput.add(NoSEP);
        NoSEP.setBounds(598, 40, 140, 23);

        jLabel14.setText("Program PRB :");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(368, 70, 90, 23);

        KdProgram.setEditable(false);
        KdProgram.setHighlighter(null);
        KdProgram.setName("KdProgram"); // NOI18N
        FormInput.add(KdProgram);
        KdProgram.setBounds(461, 70, 75, 23);

        NmProgram.setEditable(false);
        NmProgram.setHighlighter(null);
        NmProgram.setName("NmProgram"); // NOI18N
        FormInput.add(NmProgram);
        NmProgram.setBounds(538, 70, 170, 23);

        btnProgramPRB.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnProgramPRB.setMnemonic('3');
        btnProgramPRB.setToolTipText("Alt+3");
        btnProgramPRB.setName("btnProgramPRB"); // NOI18N
        btnProgramPRB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProgramPRBActionPerformed(evt);
            }
        });
        btnProgramPRB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnProgramPRBKeyPressed(evt);
            }
        });
        FormInput.add(btnProgramPRB);
        btnProgramPRB.setBounds(710, 70, 28, 23);

        jLabel8.setText("Keterangan :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(0, 100, 80, 23);

        Keterangan.setHighlighter(null);
        Keterangan.setName("Keterangan"); // NOI18N
        Keterangan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKeyPressed(evt);
            }
        });
        FormInput.add(Keterangan);
        Keterangan.setBounds(83, 100, 277, 23);

        jLabel11.setText("Saran :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(368, 100, 60, 23);

        Saran.setHighlighter(null);
        Saran.setName("Saran"); // NOI18N
        Saran.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SaranKeyPressed(evt);
            }
        });
        FormInput.add(Saran);
        Saran.setBounds(431, 100, 307, 23);

        jLabel15.setText("Dokter DPJP :");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(0, 70, 80, 23);

        ObatPRB.setName("ObatPRB"); // NOI18N
        ObatPRB.setPreferredSize(new java.awt.Dimension(578, 23));
        ObatPRB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ObatPRBKeyPressed(evt);
            }
        });
        FormInput.add(ObatPRB);
        ObatPRB.setBounds(83, 130, 425, 23);

        BtnCari2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari2.setMnemonic('4');
        BtnCari2.setToolTipText("Alt+4");
        BtnCari2.setName("BtnCari2"); // NOI18N
        BtnCari2.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCari2ActionPerformed(evt);
            }
        });
        BtnCari2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCari2KeyPressed(evt);
            }
        });
        FormInput.add(BtnCari2);
        BtnCari2.setBounds(510, 130, 28, 23);

        Scroll1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)));
        Scroll1.setName("Scroll1"); // NOI18N

        tbObat.setName("tbObat"); // NOI18N
        tbObat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbObatMouseClicked(evt);
            }
        });
        Scroll1.setViewportView(tbObat);

        FormInput.add(Scroll1);
        Scroll1.setBounds(83, 156, 655, 80);

        jLabel20.setText("Tanggal PRB :");
        jLabel20.setName("jLabel20"); // NOI18N
        jLabel20.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel20);
        jLabel20.setBounds(565, 130, 80, 23);

        Tanggal.setForeground(new java.awt.Color(50, 70, 50));
        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "18-03-2024" }));
        Tanggal.setDisplayFormat("dd-MM-yyyy");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.setOpaque(false);
        Tanggal.setPreferredSize(new java.awt.Dimension(95, 23));
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        FormInput.add(Tanggal);
        Tanggal.setBounds(648, 130, 90, 23);

        PanelInput.add(FormInput, java.awt.BorderLayout.CENTER);

        ChkInput.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput.setMnemonic('I');
        ChkInput.setText(".: Input Data");
        ChkInput.setToolTipText("Alt+I");
        ChkInput.setBorderPainted(true);
        ChkInput.setBorderPaintedFlat(true);
        ChkInput.setFocusable(false);
        ChkInput.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkInput.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkInput.setName("ChkInput"); // NOI18N
        ChkInput.setPreferredSize(new java.awt.Dimension(192, 20));
        ChkInput.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkInputActionPerformed(evt);
            }
        });
        PanelInput.add(ChkInput, java.awt.BorderLayout.PAGE_END);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if(tbProgramPRB.getSelectedRow()!= -1){
            try {
                bodyWithDeleteRequest();
            }catch (Exception ex) {
                System.out.println("Notifikasi Bridging : "+ex);
            }
        }else{
            JOptionPane.showMessageDialog(null,"Silahkan pilih dulu data yang mau dihapus..!!");
        }

}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnCari,BtnPrint);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnPrint,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        BtnCariActionPerformed(evt);
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            TCari.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            Map<String, Object> param = new HashMap<>();  
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("emailrs",akses.getemailrs());   
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptDataSRBBPJS.jasper","report","::[ Data Resep Pulang ]::",
                    "select bridging_sep.no_rawat,bridging_sep.nomr,bridging_sep.nama_pasien,bridging_srb_bpjs.alamat,"+
                    "bridging_srb_bpjs.email,bridging_sep.no_kartu,bridging_sep.no_sep,bridging_srb_bpjs.no_srb,bridging_srb_bpjs.tgl_srb,"+
                    "bridging_srb_bpjs.kodedpjp,bridging_srb_bpjs.nmdpjp,bridging_srb_bpjs.kodeprogram,bridging_srb_bpjs.namaprogram,"+
                    "bridging_srb_bpjs.keterangan,bridging_srb_bpjs.saran from bridging_sep inner join bridging_srb_bpjs "+
                    "on bridging_srb_bpjs.no_sep=bridging_sep.no_sep where bridging_srb_bpjs.tgl_srb between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' "+
                    (TCari.getText().trim().equals("")?"":" and (bridging_sep.no_rawat like '%"+TCari.getText().trim()+"%' or bridging_sep.nomr like '%"+TCari.getText().trim()+"%' or "+
                    "bridging_sep.nama_pasien like '%"+TCari.getText().trim()+"%' or bridging_sep.no_kartu like '%"+TCari.getText().trim()+"%' or bridging_sep.no_sep like '%"+TCari.getText().trim()+"%' or "+
                    "bridging_srb_bpjs.no_srb like '%"+TCari.getText().trim()+"%' or bridging_srb_bpjs.kodedpjp like '%"+TCari.getText().trim()+"%' or bridging_srb_bpjs.nmdpjp like '%"+TCari.getText().trim()+"%' or "+
                    "bridging_srb_bpjs.kodeprogram like '%"+TCari.getText().trim()+"%' or bridging_srb_bpjs.namaprogram like '%"+TCari.getText().trim()+"%')")+"order by bridging_srb_bpjs.tgl_srb",param);
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnKeluar);
        }
}//GEN-LAST:event_BtnPrintKeyPressed

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
         if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }
}//GEN-LAST:event_TCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        tampil();
}//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari, BtnAll);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        tampil();        
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            tampil();
            TCari.setText("");
        }else{
            Valid.pindah(evt, BtnCari, NmDPJP);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void tbProgramPRBMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbProgramPRBMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbProgramPRBMouseClicked

    private void tbProgramPRBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbProgramPRBKeyPressed
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
}//GEN-LAST:event_tbProgramPRBKeyPressed

private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
  isForm();                
}//GEN-LAST:event_ChkInputActionPerformed

    private void btnDokterDPJPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnDokterDPJPKeyPressed
        Valid.pindah(evt,Email,btnProgramPRB);
    }//GEN-LAST:event_btnDokterDPJPKeyPressed

    private void btnDokterDPJPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDokterDPJPActionPerformed
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);        
        dokter.setVisible(true);
    }//GEN-LAST:event_btnDokterDPJPActionPerformed

    private void AlamatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlamatKeyPressed
        Valid.pindah(evt, TCari,Email);
    }//GEN-LAST:event_AlamatKeyPressed

    private void EmailKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EmailKeyPressed
        Valid.pindah(evt,Alamat,btnDokterDPJP);
    }//GEN-LAST:event_EmailKeyPressed

    private void btnProgramPRBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProgramPRBActionPerformed
        diagnosa.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        diagnosa.setLocationRelativeTo(internalFrame1);        
        diagnosa.setVisible(true);
    }//GEN-LAST:event_btnProgramPRBActionPerformed

    private void btnProgramPRBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnProgramPRBKeyPressed
        Valid.pindah(evt,btnDokterDPJP,Keterangan);
    }//GEN-LAST:event_btnProgramPRBKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(NoRawat.getText().trim().equals("")){
            Valid.textKosong(NoRawat,"Pasien");
        }else if(NoKartu.getText().trim().equals("")){
            Valid.textKosong(NoKartu,"No.Kartu");
        }else if(KdDPJP.getText().trim().equals("")||NmDPJP.getText().trim().equals("")){
            Valid.textKosong(btnDokterDPJP,"Dokter DPJP");
        }else if(KdProgram.getText().trim().equals("")||NmProgram.getText().trim().equals("")){
            Valid.textKosong(btnDokterDPJP,"Dokter DPJP");
        }else if(Keterangan.getText().trim().equals("")){
            Valid.textKosong(Keterangan,"Keterangan");
        }else if(Saran.getText().trim().equals("")){
            Valid.textKosong(Saran,"Saran");
        }else{
            try{
                obat="";
                z=0;        
                for(i=0;i<tbObat.getRowCount();i++){
                    if(Valid.SetAngka(tbObat.getValueAt(i,0).toString())>0){
                        obat=obat+"{\"kdObat\":\""+tbObat.getValueAt(i,1).toString()+"\",\"signa1\":\""+tbObat.getValueAt(i,3).toString().trim()+"\",\"signa2\":\""+tbObat.getValueAt(i,4).toString().trim()+"\",\"jmlObat\":\""+Valid.SetAngka2(Double.parseDouble(tbObat.getValueAt(i,0).toString()))+"\"},";
                        z++;
                    }
                }
                if(z>0){
                    obat="["+obat.substring(0,obat.length()-1)+"]";
                }else{
                    obat="[]";
                }
                    
                
                headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                headers.add("X-Cons-ID",koneksiDB.CONSIDAPIBPJS());
                utc=String.valueOf(api.GetUTCdatetimeAsString());
                headers.add("X-Timestamp",utc);
                headers.add("X-Signature",api.getHmac(utc));
                headers.add("user_key",koneksiDB.USERKEYAPIBPJS());
                requestEntity = new HttpEntity(headers);
                URL = link+"/PRB/insert";
                //System.out.println("URL : "+URL);
                requestJson ="{" +
                                "\"request\":{" +
                                    "\"t_prb\":{" +
                                        "\"noSep\":\""+NoSEP.getText()+"\"," +
                                        "\"noKartu\":\""+NoKartu.getText()+"\"," +
                                        "\"alamat\":\""+Alamat.getText()+"\"," +
                                        "\"email\":\""+Email.getText()+"\"," +
                                        "\"programPRB\":\""+KdProgram.getText().trim()+"\"," +
                                        "\"kodeDPJP\":\""+KdDPJP.getText()+"\"," +
                                        "\"keterangan\":\""+Keterangan.getText()+"\"," +
                                        "\"saran\":\""+Saran.getText()+"\"," +
                                        "\"user\":\""+KdDPJP.getText()+"\"," +
                                        "\"obat\":" +obat+
                                    "}" +
                                "}" +
                              "}";
                System.out.println("JSON : "+requestJson);
                requestEntity = new HttpEntity(requestJson,headers);
                root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
                nameNode = root.path("metaData");
                JOptionPane.showMessageDialog(null,nameNode.path("message").asText()); 
                if(nameNode.path("code").asText().equals("200")){
                    response = mapper.readTree(api.Decrypt(root.path("response").asText(),utc)).path("noSRB");
                    if(Sequel.menyimpantf("bridging_srb_bpjs","?,?,?,?,?,?,?,?,?,?,?,?","No.SEP SRB",12,new String[]{
                            NoSEP.getText(),response.asText(),Valid.SetTgl(Tanggal.getSelectedItem()+""),Alamat.getText(),Email.getText(),KdProgram.getText(),NmProgram.getText(),KdDPJP.getText(),NmDPJP.getText(),KdDPJP.getText(),Keterangan.getText(),Saran.getText()
                        })==true){
                        if(z>0){
                            for(i=0;i<tbObat.getRowCount();i++){
                                if(Valid.SetAngka(tbObat.getValueAt(i,0).toString())>0){
                                    Sequel.menyimpan("bridging_srb_bpjs_obat","?,?,?,?,?,?,?",7,new String[]{
                                        NoSEP.getText(),response.asText(),tbObat.getValueAt(i,1).toString(),tbObat.getValueAt(i,2).toString(),tbObat.getValueAt(i,0).toString(),tbObat.getValueAt(i,3).toString(),tbObat.getValueAt(i,4).toString()
                                    });  
                                }
                            }
                        }
                        emptTeks();
                        tampil();
                    }
                }  
            } catch (Exception ex) {
                System.out.println("Notifikasi : "+ex);
                if(ex.toString().contains("UnknownHostException")){
                    JOptionPane.showMessageDialog(rootPane,"Koneksi ke server BPJS terputus...!");
                }
            }
        }

    }//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,ObatPRB,BtnBatal);
        }
    }//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        emptTeks();
        ChkInput.setSelected(true);
        isForm();
    }//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnBatalActionPerformed(null);
        }else{Valid.pindah(evt, BtnSimpan, BtnHapus);}
    }//GEN-LAST:event_BtnBatalKeyPressed

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        if(NoRawat.getText().trim().equals("")){
            Valid.textKosong(NoRawat,"Pasien");
        }else if(NoKartu.getText().trim().equals("")){
            Valid.textKosong(NoKartu,"No.Kartu");
        }else if(KdDPJP.getText().trim().equals("")||NmDPJP.getText().trim().equals("")){
            Valid.textKosong(btnDokterDPJP,"Dokter DPJP");
        }else if(KdProgram.getText().trim().equals("")||NmProgram.getText().trim().equals("")){
            Valid.textKosong(btnDokterDPJP,"Dokter DPJP");
        }else if(Keterangan.getText().trim().equals("")){
            Valid.textKosong(Keterangan,"Keterangan");
        }else if(Saran.getText().trim().equals("")){
            Valid.textKosong(Saran,"Saran");
        }else{
            if(tbProgramPRB.getSelectedRow()!= -1){
                try{
                    obat="";
                    z=0;        
                    for(i=0;i<tbObat.getRowCount();i++){
                        if(Valid.SetAngka(tbObat.getValueAt(i,0).toString())>0){
                            obat=obat+"{\"kdObat\":\""+tbObat.getValueAt(i,1).toString()+"\",\"signa1\":\""+tbObat.getValueAt(i,3).toString().trim()+"\",\"signa2\":\""+tbObat.getValueAt(i,4).toString().trim()+"\",\"jmlObat\":\""+tbObat.getValueAt(i,0).toString()+"\"},";
                            z++;
                        }
                    }
                    if(z>0){
                        obat="["+obat.substring(0,obat.length()-1)+"]";
                    }else{
                        obat="[]";
                    }


                    headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                    headers.add("X-Cons-ID",koneksiDB.CONSIDAPIBPJS());
                    utc=String.valueOf(api.GetUTCdatetimeAsString());
                    headers.add("X-Timestamp",utc);
                    headers.add("X-Signature",api.getHmac(utc));
                    headers.add("user_key",koneksiDB.USERKEYAPIBPJS());
                    requestEntity = new HttpEntity(headers);
                    URL = link+"/PRB/Update";
                    //System.out.println("URL : "+URL);
                    requestJson ="{" +
                                    "\"request\":{" +
                                        "\"t_prb\":{" +
                                            "\"noSrb\":\""+tbProgramPRB.getValueAt(tbProgramPRB.getSelectedRow(),7).toString()+"\"," +
                                            "\"noSep\":\""+NoSEP.getText()+"\"," +
                                            "\"alamat\":\""+Alamat.getText()+"\"," +
                                            "\"email\":\""+Email.getText()+"\"," +
                                            "\"kodeDPJP\":\""+KdDPJP.getText()+"\"," +
                                            "\"keterangan\":\""+Keterangan.getText()+"\"," +
                                            "\"saran\":\""+Saran.getText()+"\"," +
                                            "\"user\":\""+KdDPJP.getText()+"\"," +
                                            "\"obat\":" +obat+
                                        "}" +
                                    "}" +
                                  "}";
                    System.out.println("JSON : "+requestJson);
                    requestEntity = new HttpEntity(requestJson,headers);
                    root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.PUT, requestEntity, String.class).getBody());
                    nameNode = root.path("metaData");
                    JOptionPane.showMessageDialog(null,nameNode.path("message").asText()); 
                    if(nameNode.path("code").asText().equals("200")){
                        response = mapper.readTree(api.Decrypt(root.path("response").asText(),utc)).path("noSRB");
                        if(Sequel.queryu2tf("update bridging_srb_bpjs set no_sep=?,no_srb=?,tgl_srb=?,alamat=?,email=?,kodeprogram=?,namaprogram=?,kodedpjp=?,nmdpjp=?,user=?,keterangan=?,saran=? where no_sep=? and no_srb=?",14,new String[]{
                                NoSEP.getText(),response.asText(),Valid.SetTgl(Tanggal.getSelectedItem()+""),Alamat.getText(),Email.getText(),KdProgram.getText(),NmProgram.getText(),KdDPJP.getText(),NmDPJP.getText(),KdDPJP.getText(),Keterangan.getText(),Saran.getText(),
                                tbProgramPRB.getValueAt(tbProgramPRB.getSelectedRow(),6).toString(),tbProgramPRB.getValueAt(tbProgramPRB.getSelectedRow(),7).toString()
                            })==true){
                            if(z>0){
                                Sequel.meghapus("bridging_srb_bpjs_obat","no_sep","no_srb",tbProgramPRB.getValueAt(tbProgramPRB.getSelectedRow(),6).toString(),tbProgramPRB.getValueAt(tbProgramPRB.getSelectedRow(),7).toString());
                                for(i=0;i<tbObat.getRowCount();i++){
                                    if(Valid.SetAngka(tbObat.getValueAt(i,0).toString())>0){
                                        Sequel.menyimpan("bridging_srb_bpjs_obat","?,?,?,?,?,?,?",7,new String[]{
                                            NoSEP.getText(),response.asText(),tbObat.getValueAt(i,1).toString(),tbObat.getValueAt(i,2).toString(),tbObat.getValueAt(i,0).toString(),tbObat.getValueAt(i,3).toString(),tbObat.getValueAt(i,4).toString()
                                        });  
                                    }
                                }
                            }
                            emptTeks();
                            tampil();
                        }
                    }  
                } catch (Exception ex) {
                    System.out.println("Notifikasi : "+ex);
                    if(ex.toString().contains("UnknownHostException")){
                        JOptionPane.showMessageDialog(rootPane,"Koneksi ke server BPJS terputus...!");
                    }
                }
            }
        }
    }//GEN-LAST:event_BtnEditActionPerformed

    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnEditActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnPrint);
        }
    }//GEN-LAST:event_BtnEditKeyPressed

    private void KeteranganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKeyPressed
        Valid.pindah(evt,btnProgramPRB,Saran);
    }//GEN-LAST:event_KeteranganKeyPressed

    private void SaranKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SaranKeyPressed
        Valid.pindah(evt, Keterangan,Tanggal);
    }//GEN-LAST:event_SaranKeyPressed

    private void ObatPRBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ObatPRBKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCari2ActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari2.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Saran.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_TAB){
            BtnSimpan.requestFocus();
        }
    }//GEN-LAST:event_ObatPRBKeyPressed

    private void BtnCari2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCari2ActionPerformed
        tampilObat();
    }//GEN-LAST:event_BtnCari2ActionPerformed

    private void BtnCari2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCari2KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            //Valid.pindah(evt, TCari,Pemeriksaan);
        }
    }//GEN-LAST:event_BtnCari2KeyPressed

    private void tbObatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbObatMouseClicked
        /*if(tabMode2.getRowCount()!=0){
            try {
                Valid.tabelKosong(tabMode);
                tampil();
            } catch (java.lang.NullPointerException e) {
            }
        }*/
    }//GEN-LAST:event_tbObatMouseClicked

    private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeyPressed
        Valid.pindah(evt, Saran,BtnSimpan);
    }//GEN-LAST:event_TanggalKeyPressed

    private void MnSuratActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnSuratActionPerformed
        if(tbProgramPRB.getSelectedRow()!= -1){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("logo",Sequel.cariGambar("select gambar.bpjs from gambar"));
            param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbProgramPRB.getValueAt(tbProgramPRB.getSelectedRow(),10).toString()+"\nID "+tbProgramPRB.getValueAt(tbProgramPRB.getSelectedRow(),9).toString()+"\n"+Valid.SetTgl3(tbProgramPRB.getValueAt(tbProgramPRB.getSelectedRow(),8).toString()));  
            obat="";
            z=1;
            for(i=0;i<tbObat.getRowCount();i++){
                if(Valid.SetAngka(tbObat.getValueAt(i,0).toString())>0){
                    obat=obat+z+". "+tbObat.getValueAt(i,3).toString().trim()+"x"+tbObat.getValueAt(i,4).toString().trim()+"  "+tbObat.getValueAt(i,2).toString()+"  "+tbObat.getValueAt(i,0).toString()+"\n";
                    z++;
                }
            }
            param.put("obatpasien",obat);
            Valid.MyReportqry("rptBridgingSuratSRB.jasper","report","::[ Data Surat SRB BPJS ]::",
                "select bridging_sep.no_rawat,bridging_sep.nomr,bridging_sep.nama_pasien,bridging_srb_bpjs.alamat,bridging_sep.tanggal_lahir,"+
                "bridging_sep.diagawal,bridging_sep.nmdiagnosaawal,bridging_srb_bpjs.email,bridging_sep.no_kartu,bridging_sep.no_sep,bridging_srb_bpjs.no_srb,bridging_srb_bpjs.tgl_srb,"+
                "bridging_srb_bpjs.kodedpjp,bridging_srb_bpjs.nmdpjp,bridging_srb_bpjs.kodeprogram,bridging_srb_bpjs.namaprogram,"+
                "bridging_srb_bpjs.keterangan,bridging_srb_bpjs.saran from bridging_sep inner join bridging_srb_bpjs "+
                "on bridging_srb_bpjs.no_sep=bridging_sep.no_sep where bridging_sep.no_sep='"+tbProgramPRB.getValueAt(tbProgramPRB.getSelectedRow(),6).toString()+"' "+
                "and bridging_srb_bpjs.no_srb='"+tbProgramPRB.getValueAt(tbProgramPRB.getSelectedRow(),7).toString()+"'",param);
            this.setCursor(Cursor.getDefaultCursor());
        }else{
            JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data Program PRB yang mau dicetak...!!!!");
            BtnBatal.requestFocus();
        }
    }//GEN-LAST:event_MnSuratActionPerformed

    private void MnTampilkanObatPRBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnTampilkanObatPRBActionPerformed
        Valid.tabelKosong(tabMode);
        try{     
            ps=koneksi.prepareStatement("select bridging_sep.no_rawat,bridging_sep.nomr,bridging_sep.nama_pasien,bridging_srb_bpjs.alamat,"+
                    "bridging_srb_bpjs.email,bridging_sep.no_kartu,bridging_sep.no_sep,bridging_srb_bpjs.no_srb,bridging_srb_bpjs.tgl_srb,"+
                    "bridging_srb_bpjs.kodedpjp,bridging_srb_bpjs.nmdpjp,bridging_srb_bpjs.kodeprogram,bridging_srb_bpjs.namaprogram,"+
                    "bridging_srb_bpjs.keterangan,bridging_srb_bpjs.saran from bridging_sep inner join bridging_srb_bpjs "+
                    "on bridging_srb_bpjs.no_sep=bridging_sep.no_sep where bridging_srb_bpjs.tgl_srb between ? and ? "+
                    (TCari.getText().trim().equals("")?"":" and (bridging_sep.no_rawat like ? or bridging_sep.nomr like ? or "+
                    "bridging_sep.nama_pasien like ? or bridging_sep.no_kartu like ? or bridging_sep.no_sep like ? or "+
                    "bridging_srb_bpjs.no_srb like ? or bridging_srb_bpjs.kodedpjp like ? or bridging_srb_bpjs.nmdpjp like ? or "+
                    "bridging_srb_bpjs.kodeprogram like ? or bridging_srb_bpjs.namaprogram like ?)")+"order by bridging_srb_bpjs.tgl_srb");
            try {
                ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                if(!TCari.getText().equals("")){
                    ps.setString(3,"%"+TCari.getText().trim()+"%");
                    ps.setString(4,"%"+TCari.getText().trim()+"%");
                    ps.setString(5,"%"+TCari.getText().trim()+"%");
                    ps.setString(6,"%"+TCari.getText().trim()+"%");
                    ps.setString(7,"%"+TCari.getText().trim()+"%");
                    ps.setString(8,"%"+TCari.getText().trim()+"%");
                    ps.setString(9,"%"+TCari.getText().trim()+"%");
                    ps.setString(10,"%"+TCari.getText().trim()+"%");
                    ps.setString(11,"%"+TCari.getText().trim()+"%");
                    ps.setString(12,"%"+TCari.getText().trim()+"%");
                }
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new Object[]{
                        rs.getString("no_rawat"),rs.getString("nomr"),rs.getString("nama_pasien"),rs.getString("alamat"),
                        rs.getString("email"),rs.getString("no_kartu"),rs.getString("no_sep"),rs.getString("no_srb"),rs.getString("tgl_srb"),
                        rs.getString("kodedpjp"),rs.getString("nmdpjp"),rs.getString("kodeprogram"),rs.getString("namaprogram"),
                        rs.getString("keterangan"),rs.getString("saran")
                    });
                    try{     
                        ps2=koneksi.prepareStatement("select * from bridging_srb_bpjs_obat where no_sep=? and no_srb=?");
                        try {
                            ps2.setString(1,rs.getString("no_sep"));
                            ps2.setString(2,rs.getString("no_srb"));
                            rs2=ps2.executeQuery();
                            if(rs2.next()){
                                tabMode.addRow(new Object[]{
                                    "","Jumlah","Kode Barang","Nama Barag","Signa 1","Signa 2","","","","","","","","",""
                                });
                                rs2.beforeFirst();
                                while(rs2.next()){
                                    tabMode.addRow(new Object[]{
                                        "",rs2.getDouble("jumlah"),rs2.getString("kd_obat"),rs2.getString("nm_obat"),rs2.getString("signa1"),rs2.getString("signa2"),"","","","","","","","",""
                                    });
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("Notif : "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(ps2!=null){
                                ps2.close();
                            }
                        }
                    }catch(Exception e){
                        System.out.println("Notifikasi : "+e);
                    } 
                }
                rs.last();
                LCount.setText(""+rs.getRow());
            } catch (Exception e) {
                System.out.println("Notif : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }     
    }//GEN-LAST:event_MnTampilkanObatPRBActionPerformed

    private void MnKirimWAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnKirimWAActionPerformed
    boolean wasAlwaysOnTop = this.isAlwaysOnTop();
    this.setAlwaysOnTop(true);
    
    try {
        if (!cekKonfigurasiWA()) {
            return;
        }
        
        int selectedRow = tbProgramPRB.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data Program PRB terlebih dahulu!");
            return;
        }
        
        // Ambil data dari table PRB yang dipilih
        String noRawat = tbProgramPRB.getValueAt(selectedRow, 0).toString();    // No.Rawat
        String noRM = tbProgramPRB.getValueAt(selectedRow, 1).toString();       // No.RM  
        String namaPasien = tbProgramPRB.getValueAt(selectedRow, 2).toString(); // Nama Pasien
        String alamat = tbProgramPRB.getValueAt(selectedRow, 3).toString();     // Alamat
        String email = tbProgramPRB.getValueAt(selectedRow, 4).toString();      // Email
        String noKartu = tbProgramPRB.getValueAt(selectedRow, 5).toString();    // No.Kartu
        String noSEP = tbProgramPRB.getValueAt(selectedRow, 6).toString();      // No.SEP
        String noSRB = tbProgramPRB.getValueAt(selectedRow, 7).toString();      // No.SRB
        String tglPRB = tbProgramPRB.getValueAt(selectedRow, 8).toString();     // Tgl.PRB
        String kodeDPJP = tbProgramPRB.getValueAt(selectedRow, 9).toString();   // Kode DPJP
        String nameDPJP = tbProgramPRB.getValueAt(selectedRow, 10).toString();  // Dokter DPJP
        String kodePRB = tbProgramPRB.getValueAt(selectedRow, 11).toString();   // Kode PRB
        String programPRB = tbProgramPRB.getValueAt(selectedRow, 12).toString(); // Program PRB
        
        String noTelp = "";
        
        // Ambil nomor telepon dari tabel pasien dan auto format ke 628
        try {
            PreparedStatement ps = koneksi.prepareStatement(
                "SELECT no_tlp FROM pasien WHERE no_rkm_medis = ?"
            );
            ps.setString(1, noRM);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                String rawNoTelp = rs.getString("no_tlp");
                if (rawNoTelp != null && !rawNoTelp.trim().isEmpty()) {
                    noTelp = autoFormatToWA(rawNoTelp.trim());
                } else {
                    noTelp = "";
                }
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error mengambil nomor telepon: " + e.getMessage());
        }
        
        // Buat screenshot PRB
        String fileName = buatScreenshotProgramPRB(noRM, namaPasien, noSRB);
        if (fileName == null) {
            return;
        }
        
        // Dialog input nomor WhatsApp dengan retry
        String nomorWA = inputNomorWhatsApp(noTelp, namaPasien);
        if (nomorWA == null) {
            hapusScreenshot(fileName);
            JOptionPane.showMessageDialog(this, "Pengiriman WhatsApp dibatalkan.");
            return;
        }
        
        // Buat pesan WA dengan nama yang disensor
        String namaSensor = sensorNamaPasien(namaPasien);
        String pesanWA = "Berikut adalah pesan WhatsApp resmi dari RS Umum Banjar Patroman, kami sampaikan untuk Surat Rujuk Balik (PRB):\n\n" +
                        "Nama: " + namaPasien + "\n" +
                        "No. SRB: " + noSRB + "\n" +
                        "Program PRB: " + programPRB + "\n\n" +
                        "Harap tunjukan surat rujuk balik ini bila diperlukan oleh FKTP.\n\n" +
                        "Mohon maaf jika terjadi kesalahan pengiriman. Harap gunakan dokumen ini dengan bijak dan jangan disalahgunakan.";
        
        // Simpan ke database WA
        simpanKeWADatabase(nomorWA, pesanWA, fileName);
        
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
    } finally {
        this.setAlwaysOnTop(wasAlwaysOnTop);
    }
    }//GEN-LAST:event_MnKirimWAActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            BPJSProgramPRB dialog = new BPJSProgramPRB(new javax.swing.JFrame(), true);
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private widget.TextBox Alamat;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnCari2;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkInput;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.TextBox Email;
    private widget.PanelBiasa FormInput;
    private widget.TextBox KdDPJP;
    private widget.TextBox KdProgram;
    private widget.TextBox Keterangan;
    private widget.Label LCount;
    private javax.swing.JMenuItem MnKirimWA;
    private javax.swing.JMenuItem MnSurat;
    private javax.swing.JMenuItem MnTampilkanObatPRB;
    private widget.TextBox NmDPJP;
    private widget.TextBox NmPasien;
    private widget.TextBox NmProgram;
    private widget.TextBox NoKartu;
    private widget.TextBox NoRM;
    private widget.TextBox NoRawat;
    private widget.TextBox NoSEP;
    private widget.TextBox ObatPRB;
    private javax.swing.JPanel PanelInput;
    private widget.TextBox Saran;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll1;
    private widget.TextBox TCari;
    private widget.Tanggal Tanggal;
    private widget.Button btnDokterDPJP;
    private widget.Button btnProgramPRB;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel13;
    private widget.Label jLabel14;
    private widget.Label jLabel15;
    private widget.Label jLabel19;
    private widget.Label jLabel20;
    private widget.Label jLabel21;
    private widget.Label jLabel3;
    private widget.Label jLabel4;
    private widget.Label jLabel5;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.Table tbObat;
    private widget.Table tbProgramPRB;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try{     
            ps=koneksi.prepareStatement("select bridging_sep.no_rawat,bridging_sep.nomr,bridging_sep.nama_pasien,bridging_srb_bpjs.alamat,"+
                    "bridging_srb_bpjs.email,bridging_sep.no_kartu,bridging_sep.no_sep,bridging_srb_bpjs.no_srb,bridging_srb_bpjs.tgl_srb,"+
                    "bridging_srb_bpjs.kodedpjp,bridging_srb_bpjs.nmdpjp,bridging_srb_bpjs.kodeprogram,bridging_srb_bpjs.namaprogram,"+
                    "bridging_srb_bpjs.keterangan,bridging_srb_bpjs.saran from bridging_sep inner join bridging_srb_bpjs "+
                    "on bridging_srb_bpjs.no_sep=bridging_sep.no_sep where bridging_srb_bpjs.tgl_srb between ? and ? "+
                    (TCari.getText().trim().equals("")?"":" and (bridging_sep.no_rawat like ? or bridging_sep.nomr like ? or "+
                    "bridging_sep.nama_pasien like ? or bridging_sep.no_kartu like ? or bridging_sep.no_sep like ? or "+
                    "bridging_srb_bpjs.no_srb like ? or bridging_srb_bpjs.kodedpjp like ? or bridging_srb_bpjs.nmdpjp like ? or "+
                    "bridging_srb_bpjs.kodeprogram like ? or bridging_srb_bpjs.namaprogram like ?)")+"order by bridging_srb_bpjs.tgl_srb");
            try {
                ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                if(!TCari.getText().equals("")){
                    ps.setString(3,"%"+TCari.getText().trim()+"%");
                    ps.setString(4,"%"+TCari.getText().trim()+"%");
                    ps.setString(5,"%"+TCari.getText().trim()+"%");
                    ps.setString(6,"%"+TCari.getText().trim()+"%");
                    ps.setString(7,"%"+TCari.getText().trim()+"%");
                    ps.setString(8,"%"+TCari.getText().trim()+"%");
                    ps.setString(9,"%"+TCari.getText().trim()+"%");
                    ps.setString(10,"%"+TCari.getText().trim()+"%");
                    ps.setString(11,"%"+TCari.getText().trim()+"%");
                    ps.setString(12,"%"+TCari.getText().trim()+"%");
                }
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new Object[]{
                        rs.getString("no_rawat"),rs.getString("nomr"),rs.getString("nama_pasien"),rs.getString("alamat"),
                        rs.getString("email"),rs.getString("no_kartu"),rs.getString("no_sep"),rs.getString("no_srb"),rs.getString("tgl_srb"),
                        rs.getString("kodedpjp"),rs.getString("nmdpjp"),rs.getString("kodeprogram"),rs.getString("namaprogram"),
                        rs.getString("keterangan"),rs.getString("saran")
                    });
                }
            } catch (Exception e) {
                System.out.println("Notif : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }
            LCount.setText(""+tabMode.getRowCount());
        }catch(SQLException e){
            System.out.println("Notifikasi : "+e);
        }        
    }

    private void getData() {
        if(tbProgramPRB.getSelectedRow()!= -1){
            NoRawat.setText(tbProgramPRB.getValueAt(tbProgramPRB.getSelectedRow(),0).toString()); 
            NoRM.setText(tbProgramPRB.getValueAt(tbProgramPRB.getSelectedRow(),1).toString()); 
            NmPasien.setText(tbProgramPRB.getValueAt(tbProgramPRB.getSelectedRow(),2).toString()); 
            Alamat.setText(tbProgramPRB.getValueAt(tbProgramPRB.getSelectedRow(),3).toString());  
            Email.setText(tbProgramPRB.getValueAt(tbProgramPRB.getSelectedRow(),4).toString());  
            NoKartu.setText(tbProgramPRB.getValueAt(tbProgramPRB.getSelectedRow(),5).toString());  
            NoSEP.setText(tbProgramPRB.getValueAt(tbProgramPRB.getSelectedRow(),6).toString());    
            KdDPJP.setText(tbProgramPRB.getValueAt(tbProgramPRB.getSelectedRow(),9).toString());   
            NmDPJP.setText(tbProgramPRB.getValueAt(tbProgramPRB.getSelectedRow(),10).toString());  
            KdProgram.setText(tbProgramPRB.getValueAt(tbProgramPRB.getSelectedRow(),11).toString());  
            NmProgram.setText(tbProgramPRB.getValueAt(tbProgramPRB.getSelectedRow(),12).toString());  
            Keterangan.setText(tbProgramPRB.getValueAt(tbProgramPRB.getSelectedRow(),13).toString());  
            Saran.setText(tbProgramPRB.getValueAt(tbProgramPRB.getSelectedRow(),14).toString());  
            Valid.tabelKosong(tabMode2);
            try{     
                ps=koneksi.prepareStatement("select * from bridging_srb_bpjs_obat where no_sep=? and no_srb=?");
                try {
                    ps.setString(1,tbProgramPRB.getValueAt(tbProgramPRB.getSelectedRow(),6).toString());
                    ps.setString(2,tbProgramPRB.getValueAt(tbProgramPRB.getSelectedRow(),7).toString());
                    rs=ps.executeQuery();
                    while(rs.next()){
                        tabMode2.addRow(new Object[]{
                            rs.getDouble("jumlah"),rs.getString("kd_obat"),rs.getString("nm_obat"),rs.getString("signa1"),rs.getString("signa2")
                        });
                    }
                } catch (Exception e) {
                    System.out.println("Notif : "+e);
                } finally{
                    if(rs!=null){
                        rs.close();
                    }
                    if(ps!=null){
                        ps.close();
                    }
                }
            }catch(Exception e){
                System.out.println("Notifikasi : "+e);
            } 
            Valid.SetTgl(Tanggal,tbProgramPRB.getValueAt(tbProgramPRB.getSelectedRow(),8).toString());
        }
    }
   
    public void setNoRm(String norawat,String nosep,String nokartu,String norm,String namapasien,String alamat,String email,String kodedpjp,String namadpjp) {
        NoRawat.setText(norawat);
        NoSEP.setText(nosep);
        NoKartu.setText(nokartu);
        NoRM.setText(norm);
        NmPasien.setText(namapasien);
        Alamat.setText(alamat);
        Email.setText(email);
        KdDPJP.setText(kodedpjp);
        NmDPJP.setText(namadpjp);
        TCari.setText(nosep);
        ChkInput.setSelected(true);
        isForm();
        tampil();
    }
    
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,266));
            FormInput.setVisible(true);      
            ChkInput.setVisible(true);
        }else if(ChkInput.isSelected()==false){           
            ChkInput.setVisible(false);            
            PanelInput.setPreferredSize(new Dimension(WIDTH,20));
            FormInput.setVisible(false);      
            ChkInput.setVisible(true);
        }
    }
    
    public void isCek(){
        btnDokterDPJP.setEnabled(akses.getbpjs_program_prb());
        BtnHapus.setEnabled(akses.getbpjs_program_prb());
        BtnPrint.setEnabled(akses.getbpjs_program_prb());
    }

    private void tampilObat() {
        try {
            z=0;
            for(i=0;i<tbObat.getRowCount();i++){
                if(Valid.SetAngka(tbObat.getValueAt(i,0).toString())>0){
                    z++;
                }
            }   
            jumlah=null;
            jumlah=new double[z];
            kodeobat=null;
            kodeobat=new String[z];
            namaobat=null;
            namaobat=new String[z];
            signa1=null;
            signa1=new String[z];
            signa2=null;
            signa2=new String[z];
            z=0;        
            for(i=0;i<tbObat.getRowCount();i++){
                if(Valid.SetAngka(tbObat.getValueAt(i,0).toString())>0){
                    try {
                        jumlah[z]=Double.parseDouble(tbObat.getValueAt(i,0).toString());
                    } catch (Exception e) {
                        jumlah[z]=0;
                    } 
                    kodeobat[z]=tbObat.getValueAt(i,1).toString();
                    namaobat[z]=tbObat.getValueAt(i,2).toString();
                    signa1[z]=tbObat.getValueAt(i,3).toString();
                    signa2[z]=tbObat.getValueAt(i,4).toString();
                    z++;
                }
            }
            
            Valid.tabelKosong(tabMode2); 
            
            for(i=0;i<z;i++){
                tabMode2.addRow(new Object[] {
                    jumlah[i],kodeobat[i],namaobat[i],signa1[i],signa2[i]
                });
            }
            
            headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
	    headers.add("X-Cons-ID",koneksiDB.CONSIDAPIBPJS());
	    utc=String.valueOf(api.GetUTCdatetimeAsString());
	    headers.add("X-Timestamp",utc);
	    headers.add("X-Signature",api.getHmac(utc));
            headers.add("user_key",koneksiDB.USERKEYAPIBPJS());
            requestEntity = new HttpEntity(headers);
            URL = link+"/referensi/obatprb/"+ObatPRB.getText();	
            //System.out.println(rest.exchange(URL, HttpMethod.GET, requestEntity, String.class).getBody());
            root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.GET, requestEntity, String.class).getBody());
            nameNode = root.path("metaData");
            if(nameNode.path("code").asText().equals("200")){
                response = mapper.readTree(api.Decrypt(root.path("response").asText(),utc));
                //response = root.path("response");
                if(response.path("list").isArray()){
                    for(JsonNode list:response.path("list")){
                        tabMode2.addRow(new Object[]{
                            "",list.path("kode").asText(),list.path("nama").asText(),"",""
                        });
                    }
                }
            }else {
                JOptionPane.showMessageDialog(null,nameNode.path("message").asText());                
            }   
        } catch (Exception ex) {
            System.out.println("Notifikasi : "+ex);
            if(ex.toString().contains("UnknownHostException")){
                JOptionPane.showMessageDialog(rootPane,"Koneksi ke server BPJS terputus...!");
            }
        }
    }

    private void emptTeks() {
        KdProgram.setText("");
        NmProgram.setText("");
        Keterangan.setText("");
        Saran.setText("");
        ObatPRB.setText("");
        Valid.tabelKosong(tabMode2);
        Alamat.requestFocus();
    }
    
    @Test
    public void bodyWithDeleteRequest() throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        SSLContext sslContext = SSLContext.getInstance("SSL");
        javax.net.ssl.TrustManager[] trustManagers= {
            new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {return null;}
                public void checkServerTrusted(X509Certificate[] arg0, String arg1)throws CertificateException {}
                public void checkClientTrusted(X509Certificate[] arg0, String arg1)throws CertificateException {}
            }
        };
        sslContext.init(null,trustManagers , new SecureRandom());
        SSLSocketFactory sslFactory=new SSLSocketFactory(sslContext,SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        Scheme scheme=new Scheme("https",443,sslFactory);
    
        HttpComponentsClientHttpRequestFactory factory=new HttpComponentsClientHttpRequestFactory(){
            @Override
            protected HttpUriRequest createHttpUriRequest(HttpMethod httpMethod, URI uri) {
                if (HttpMethod.DELETE == httpMethod) {
                    return new BPJSSuratKontrol.HttpEntityEnclosingDeleteRequest(uri);
                }
                return super.createHttpUriRequest(httpMethod, uri);
            }
        };
        factory.getHttpClient().getConnectionManager().getSchemeRegistry().register(scheme);
        restTemplate.setRequestFactory(factory);
        
        try {
            headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.add("X-Cons-ID",koneksiDB.CONSIDAPIBPJS());
            utc=String.valueOf(api.GetUTCdatetimeAsString());
            headers.add("X-Timestamp",utc);
            headers.add("X-Signature",api.getHmac(utc));
            headers.add("user_key",koneksiDB.USERKEYAPIBPJS());
            requestEntity = new HttpEntity(headers);
            URL = link+"/PRB/Delete";
            requestJson ="{\"request\":{\"t_prb\":{\"noSrb\":\""+tbProgramPRB.getValueAt(tbProgramPRB.getSelectedRow(),7).toString()+"\",\"noSep\":\""+tbProgramPRB.getValueAt(tbProgramPRB.getSelectedRow(),6).toString()+"\",\"user\": \""+tbProgramPRB.getValueAt(tbProgramPRB.getSelectedRow(),9).toString()+"\"}}}";            
            requestEntity = new HttpEntity(requestJson,headers);
            root = mapper.readTree(restTemplate.exchange(URL, HttpMethod.DELETE,requestEntity, String.class).getBody());
            nameNode = root.path("metaData");
            System.out.println("code : "+nameNode.path("code").asText());
            System.out.println("message : "+nameNode.path("message").asText());
            JOptionPane.showMessageDialog(null,nameNode.path("message").asText());
            if(nameNode.path("code").asText().equals("200")){
                Sequel.meghapus("bridging_srb_bpjs","no_sep","no_srb",tbProgramPRB.getValueAt(tbProgramPRB.getSelectedRow(),6).toString(),tbProgramPRB.getValueAt(tbProgramPRB.getSelectedRow(),7).toString());
                Sequel.meghapus("bridging_srb_bpjs_obat","no_sep","no_srb",tbProgramPRB.getValueAt(tbProgramPRB.getSelectedRow(),6).toString(),tbProgramPRB.getValueAt(tbProgramPRB.getSelectedRow(),7).toString());
                tampil();
            }
        } catch (Exception e) {   
            System.out.println("Notif : "+e);
            if(e.toString().contains("UnknownHostException")){
                JOptionPane.showMessageDialog(null,"Koneksi ke server BPJS terputus...!");
            }
        }
    }

private String buatScreenshotProgramPRB(String noRM, String namaPasien, String noSRB) {
    try {
        // 1. Cari file jasper report PRB
        String jasperPath = cariFileJasperPRB();
        if (jasperPath == null) {
            JOptionPane.showMessageDialog(this, "❌ File rptBridgingSuratSRB.jasper tidak ditemukan!");
            return null;
        }
        
        // 2. Parameter report - SAMA PERSIS dengan MnSuratActionPerformed
        Map<String, Object> param = new HashMap<>();
        param.put("namars", akses.getnamars());
        param.put("alamatrs", akses.getalamatrs());
        param.put("kotars", akses.getkabupatenrs());
        param.put("propinsirs", akses.getpropinsirs());
        param.put("kontakrs", akses.getkontakrs());
        param.put("logo", Sequel.cariGambar("select gambar.bpjs from gambar"));
        
        // Parameter finger sama seperti di MnSuratActionPerformed
        String dokterDPJP = tbProgramPRB.getValueAt(tbProgramPRB.getSelectedRow(), 10).toString();
        String kodeDPJP = tbProgramPRB.getValueAt(tbProgramPRB.getSelectedRow(), 9).toString();
        String tglPRB = tbProgramPRB.getValueAt(tbProgramPRB.getSelectedRow(), 8).toString();
        
        param.put("finger", "Dikeluarkan di " + akses.getnamars() + ", Kabupaten/Kota " + akses.getkabupatenrs() + 
                           "\nDitandatangani secara elektronik oleh " + dokterDPJP + 
                           "\nID " + kodeDPJP + 
                           "\n" + Valid.SetTgl3(tglPRB));
        
        // 3. Generate data obat PRB (sama seperti di MnSuratActionPerformed)
        String obat = "";
        int z = 1;
        for (int i = 0; i < tbObat.getRowCount(); i++) {
            if (Valid.SetAngka(tbObat.getValueAt(i, 0).toString()) > 0) {
                obat = obat + z + ". " + tbObat.getValueAt(i, 3).toString().trim() + "x" + 
                       tbObat.getValueAt(i, 4).toString().trim() + "  " + 
                       tbObat.getValueAt(i, 2).toString() + "  " + 
                       tbObat.getValueAt(i, 0).toString() + "\n";
                z++;
            }
        }
        param.put("obatpasien", obat);
        
        // 4. Query SAMA PERSIS dengan MnSuratActionPerformed tapi dengan sensor data
        String noSEP = tbProgramPRB.getValueAt(tbProgramPRB.getSelectedRow(), 6).toString();
        String noSRBSelected = tbProgramPRB.getValueAt(tbProgramPRB.getSelectedRow(), 7).toString();
        
        String query = "SELECT bridging_sep.no_rawat, " +
                      "bridging_sep.nomr, " +
                      "bridging_sep.nama_pasien, " + // Tidak Sensor nama
                      "bridging_srb_bpjs.alamat, " +
                      "bridging_sep.tanggal_lahir, " + // Tidak Sensor tanggal lahir
                      "bridging_sep.diagawal, " +
                      "bridging_sep.nmdiagnosaawal, " + // Tidak Sensor diagnosa
                      "bridging_srb_bpjs.email, " +
                      "bridging_sep.no_kartu, " + // Tidak Sensor no kartu
                      "bridging_sep.no_sep, " +
                      "bridging_srb_bpjs.no_srb, " +
                      "bridging_srb_bpjs.tgl_srb, " +
                      "bridging_srb_bpjs.kodedpjp, " +
                      "bridging_srb_bpjs.nmdpjp, " +
                      "bridging_srb_bpjs.kodeprogram, " +
                      "bridging_srb_bpjs.namaprogram, " +
                      "bridging_srb_bpjs.keterangan, " +
                      "bridging_srb_bpjs.saran " +
                      "FROM bridging_sep " +
                      "INNER JOIN bridging_srb_bpjs ON bridging_srb_bpjs.no_sep = bridging_sep.no_sep " +
                      "WHERE bridging_sep.no_sep = '" + noSEP + "' " +
                      "AND bridging_srb_bpjs.no_srb = '" + noSRBSelected + "'";
        
        // 5. Execute query
        PreparedStatement ps = koneksi.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        
        if (!rs.next()) {
            JOptionPane.showMessageDialog(this, "❌ Data Program PRB tidak ditemukan!");
            rs.close();
            ps.close();
            return null;
        }
        
        rs.beforeFirst();
        
        // 6. Generate jasper report
        net.sf.jasperreports.engine.JasperReport jasperReport = 
            (net.sf.jasperreports.engine.JasperReport) net.sf.jasperreports.engine.util.JRLoader.loadObjectFromFile(jasperPath);
        
        net.sf.jasperreports.engine.JRResultSetDataSource dataSource = 
            new net.sf.jasperreports.engine.JRResultSetDataSource(rs);
        
        net.sf.jasperreports.engine.JasperPrint jasperPrint = 
            net.sf.jasperreports.engine.JasperFillManager.fillReport(jasperReport, param, dataSource);
        
        rs.close();
        ps.close();
        
        // 7. Convert ke Image
        return convertJasperPrintToImagePRB(jasperPrint, noRM, namaPasien, noSRB);
        
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error membuat screenshot PRB: " + e.getMessage());
        return null;
    }
}


private String cariFileJasperPRB() {
    // Cari file jasper PRB
    String[] possiblePaths = {
        "report/rptBridgingSuratSRB.jasper",
        "./report/rptBridgingSuratSRB.jasper", 
        "src/report/rptBridgingSuratSRB.jasper",
        "../report/rptBridgingSuratSRB.jasper"
    };
    
    for (String path : possiblePaths) {
        java.io.File file = new java.io.File(path);
        if (file.exists()) {
            System.out.println("✅ Jasper PRB file found: " + file.getAbsolutePath());
            return file.getAbsolutePath();
        }
    }
    
    // Debug: tampilkan isi direktori report
    java.io.File reportDir = new java.io.File("report");
    if (reportDir.exists() && reportDir.isDirectory()) {
        System.out.println("📁 Contents of report directory:");
        for (java.io.File file : reportDir.listFiles()) {
            System.out.println("  - " + file.getName());
        }
    }
    
    return null;
}

private String convertJasperPrintToImagePRB(net.sf.jasperreports.engine.JasperPrint jasperPrint, 
                                           String noRM, String namaPasien, String noSRB) {
    try {
        // Ukuran page sesuai jasper report
        int pageWidth = jasperPrint.getPageWidth();
        int pageHeight = jasperPrint.getPageHeight();
        
        float zoomFactor = 1.5f;
        int width = Math.round(pageWidth * zoomFactor);
        int height = Math.round(pageHeight * zoomFactor);
        
        java.awt.image.BufferedImage image = new java.awt.image.BufferedImage(width, height, 
                                                                             java.awt.image.BufferedImage.TYPE_INT_RGB);
        java.awt.Graphics2D graphics = image.createGraphics();
        
        // Set rendering hints
        graphics.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, 
                                 java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setRenderingHint(java.awt.RenderingHints.KEY_TEXT_ANTIALIASING, 
                                 java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics.setRenderingHint(java.awt.RenderingHints.KEY_RENDERING, 
                                 java.awt.RenderingHints.VALUE_RENDER_QUALITY);
        
        graphics.setColor(java.awt.Color.WHITE);
        graphics.fillRect(0, 0, width, height);
        graphics.scale(zoomFactor, zoomFactor);
        
        // Export ke Graphics2D
        net.sf.jasperreports.engine.export.JRGraphics2DExporter exporter = 
            new net.sf.jasperreports.engine.export.JRGraphics2DExporter();
        
        exporter.setExporterInput(new net.sf.jasperreports.export.SimpleExporterInput(jasperPrint));
        
        net.sf.jasperreports.export.SimpleGraphics2DExporterOutput exporterOutput = 
            new net.sf.jasperreports.export.SimpleGraphics2DExporterOutput();
        exporterOutput.setGraphics2D(graphics);
        exporter.setExporterOutput(exporterOutput);
        
        net.sf.jasperreports.export.SimpleGraphics2DReportConfiguration configuration = 
            new net.sf.jasperreports.export.SimpleGraphics2DReportConfiguration();
        configuration.setPageIndex(0);
        exporter.setConfiguration(configuration);
        
        exporter.exportReport();
        graphics.dispose();
        
        // Generate nama file PRB
        String timestamp = new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
        String cleanNama = namaPasien.replaceAll("[^a-zA-Z0-9]", "_");
        String cleanNoSRB = noSRB.replaceAll("[^a-zA-Z0-9]", "_");
        
        String fileName = noRM + "_" + cleanNama + "_PRB_" + cleanNoSRB + "_001.png";
        
        // Simpan file
        String uploadPath = koneksiDB.PATHUPLOADWA();
        java.io.File uploadDir = new java.io.File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        
        java.io.File outputFile = new java.io.File(uploadPath + java.io.File.separator + fileName);
        
        // Handle duplicate filenames
        int counter = 1;
        String originalFileName = fileName;
        while (outputFile.exists()) {
            String baseName = originalFileName.substring(0, originalFileName.lastIndexOf("_"));
            String newFileName = baseName + "_" + String.format("%03d", ++counter) + ".png";
            outputFile = new java.io.File(uploadPath + java.io.File.separator + newFileName);
            fileName = newFileName;
        }
        
        javax.imageio.ImageIO.write(image, "PNG", outputFile);
        
        System.out.println("✅ PRB screenshot saved: " + outputFile.getAbsolutePath());
        return fileName;
        
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error converting PRB report to image: " + e.getMessage());
        return null;
    }
}

// Method helper yang sama seperti implementasi sebelumnya
private String autoFormatToWA(String nomor) {
    if (nomor == null || nomor.trim().isEmpty()) {
        return "";
    }
    
    String cleanNomor = nomor.replaceAll("[^0-9]", "");
    
    if (cleanNomor.startsWith("08")) {
        return "62" + cleanNomor.substring(1);
    } else if (cleanNomor.startsWith("62")) {
        return cleanNomor;
    } else if (cleanNomor.startsWith("8")) {
        return "62" + cleanNomor;
    } else if (cleanNomor.startsWith("0")) {
        return "62" + cleanNomor.substring(1);
    } else {
        return "62" + cleanNomor;
    }
}

private String inputNomorWhatsApp(String defaultNomor, String namaPasien) {
    String nomorWA = defaultNomor;
    boolean validInput = false;
    
    this.setAlwaysOnTop(true);
    
    try {
        while (!validInput) {
            String message = "Kirim Screenshot Surat Rujuk Balik (PRB) ke WhatsApp\n" +
                            "Nama Pasien: " + namaPasien + "\n\n" +
                            "Masukkan nomor WhatsApp:\n" +
                            "(Format: 628xxxxxxxxx - dimulai 628, bukan 08)";
            
            String input = (String) JOptionPane.showInputDialog(
                this, message, "Input Nomor WhatsApp", 
                JOptionPane.QUESTION_MESSAGE, null, null, nomorWA);
            
            if (input == null) {
                return null;
            }
            
            if (input.trim().isEmpty()) {
                int choice = JOptionPane.showConfirmDialog(this, 
                    "Nomor WhatsApp tidak boleh kosong!\n\nCoba lagi?", 
                    "Input Nomor WhatsApp", 
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);
                
                if (choice != JOptionPane.YES_OPTION) {
                    return null;
                }
                continue;
            }
            
            String formattedNomor = formatNomorWA(input);
            
            if (!validasiNomorWA(formattedNomor)) {
                int choice = JOptionPane.showConfirmDialog(this, 
                    "❌ Format nomor SALAH!\n\n" +
                    "Nomor yang dimasukkan: " + input + "\n" +
                    "Nomor yang diformat: " + formattedNomor + "\n\n" +
                    "✅ FORMAT YANG BENAR:\n" +
                    "• Dimulai dengan 628 (BUKAN 08)\n" +
                    "• Panjang 12-15 digit\n" +
                    "• Contoh: 628123456789\n\n" +
                    "Coba lagi?", 
                    "Format Nomor Salah", 
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.ERROR_MESSAGE);
                
                if (choice != JOptionPane.YES_OPTION) {
                    return null;
                }
                nomorWA = input;
                continue;
            }
            
            int confirm = JOptionPane.showConfirmDialog(this,
                "✅ Konfirmasi nomor WhatsApp:\n\n" +
                "Nomor yang dimasukkan: " + input + "\n" +
                "Nomor yang akan digunakan: " + formattedNomor + "\n\n" +
                "Apakah nomor sudah benar?",
                "Konfirmasi Nomor WhatsApp",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
            
            if (confirm == JOptionPane.YES_OPTION) {
                validInput = true;
                return formattedNomor;
            } else {
                nomorWA = input;
                continue;
            }
        }
    } finally {
        this.setAlwaysOnTop(false);
    }
    
    return null;
}

private String formatNomorWA(String nomor) {
    if (nomor == null || nomor.trim().isEmpty()) {
        return nomor;
    }
    
    String cleanNomor = nomor.replaceAll("[^0-9]", "");
    
    if (cleanNomor.startsWith("08")) {
        cleanNomor = "62" + cleanNomor.substring(1);
    } else if (cleanNomor.startsWith("628")) {
        return cleanNomor;
    } else if (cleanNomor.startsWith("62")) {
        if (cleanNomor.length() >= 3) {
            String afterCode = cleanNomor.substring(2);
            if (afterCode.startsWith("8")) {
                return cleanNomor;
            } else {
                return "628" + afterCode;
            }
        }
    } else if (cleanNomor.startsWith("8")) {
        cleanNomor = "62" + cleanNomor;
    } else if (cleanNomor.startsWith("0")) {
        cleanNomor = "62" + cleanNomor.substring(1);
    } else {
        cleanNomor = "628" + cleanNomor;
    }
    
    return cleanNomor;
}

private boolean validasiNomorWA(String nomor) {
    if (nomor == null || nomor.trim().isEmpty()) {
        return false;
    }
    
    if (!nomor.startsWith("628")) {
        return false;
    }
    
    if (nomor.length() < 12 || nomor.length() > 15) {
        return false;
    }
    
    if (!nomor.matches("^[0-9]+$")) {
        return false;
    }
    
    return true;
}

private boolean cekKonfigurasiWA() {
    String host = koneksiDB.HOSTWA();
    String database = koneksiDB.DATABASEWA();
    String path = koneksiDB.PATHUPLOADWA();
    
    if (host.isEmpty()) {
        JOptionPane.showMessageDialog(this, "❌ HOSTWA belum dikonfigurasi di database.xml!");
        return false;
    }
    if (database.isEmpty()) {
        JOptionPane.showMessageDialog(this, "❌ DATABASEWA belum dikonfigurasi di database.xml!");
        return false;
    }
    if (path.isEmpty()) {
        JOptionPane.showMessageDialog(this, "❌ PATHUPLOADWA belum dikonfigurasi di database.xml!");
        return false;
    }
    
    return true;
}

private void simpanKeWADatabase(String nomor, String pesan, String fileName) {
    try {
        if (koneksi == null || koneksi.isClosed()) {
            JOptionPane.showMessageDialog(this, "❌ Koneksi database bermasalah!");
            return;
        }
        
        String uploadPath = koneksiDB.PATHUPLOADWA();
        String fullFilePath = uploadPath + java.io.File.separator + fileName;
        
        String sql = "INSERT INTO " + koneksiDB.DATABASEWA() + ".wa_outbox " +
                    "(nomor, pesan, tipe, file_path, status, created_at) " +
                    "VALUES (?, ?, 'IMAGE', ?, 'ANTRIAN', NOW())";
        
        java.sql.PreparedStatement ps = koneksi.prepareStatement(sql);
        ps.setString(1, nomor);
        ps.setString(2, pesan);
        ps.setString(3, fullFilePath);
        
        int result = ps.executeUpdate();
        if (result > 0) {
            JOptionPane.showMessageDialog(this, 
                "✅ Pesan WhatsApp PRB berhasil ditambahkan ke antrian!\n\n" +
                "Nomor: " + nomor + "\n" +
                "File: " + fullFilePath + "\n" +
                "Status: Menunggu dikirim");
        } else {
            JOptionPane.showMessageDialog(this, "❌ Gagal menyimpan ke database WA!");
            hapusScreenshot(fileName);
        }
        
        ps.close();
        
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "❌ Error menyimpan ke database WA: " + e.getMessage());
        hapusScreenshot(fileName);
    }
}

private void hapusScreenshot(String fileName) {
    if (fileName != null && !fileName.isEmpty()) {
        try {
            String uploadPath = koneksiDB.PATHUPLOADWA();
            java.io.File file = new java.io.File(uploadPath + java.io.File.separator + fileName);
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
            // Silent delete
        }
    }
}

private String sensorNamaPasien(String namaPasien) {
    if (namaPasien == null || namaPasien.trim().isEmpty()) {
        return "***";
    }
    
    String[] kataNama = namaPasien.trim().split("\\s+");
    StringBuilder namaSensor = new StringBuilder();
    
    for (int i = 0; i < kataNama.length; i++) {
        if (i > 0) {
            namaSensor.append(" ");
        }
        
        String kata = kataNama[i];
        if (kata.length() > 0) {
            // PERBAIKAN: Gunakan Character.toUpperCase() atau String.valueOf()
            namaSensor.append(Character.toUpperCase(kata.charAt(0)));
            
            // Tambahkan bintang untuk sisa karakter
            if (kata.length() > 1) {
                for (int j = 1; j < kata.length(); j++) {
                    namaSensor.append("*");
                }
            }
        }
    }
    
    return namaSensor.toString();
}


}
