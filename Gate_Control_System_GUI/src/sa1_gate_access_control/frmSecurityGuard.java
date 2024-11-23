/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa1_gate_access_control;

import java.awt.Color;
import java.awt.HeadlessException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author USER
 */
public class frmSecurityGuard extends javax.swing.JFrame {

    /**
     * Creates new form frmSecurityGuard
     */
    public frmSecurityGuard() {
        initComponents();
        mRemoveErrorMessages();
        mVeiwRecords();
        
    }
    
    
    
    //Declaration 
    
    private String strDate ,strName, strTime ,strFromCompany , strVehicleRegNo ,strNumber
            ,strReasonForVisit ,strPersonToSee ;
    
   private boolean boolInvalideData;
   
    private  static int RecordID;
    
    
    // GUI Methods 
    
    
     public void mRemoveErrorMessages(){  // Removes red labels below text inputs
    
        lblDateError.setVisible(false);
        lblTimeInError.setVisible(false);
        lblNameError.setVisible(false);
        lblFromCompanyError.setVisible(false);
        lblRegError.setVisible(false);
        lblNumberError.setVisible(false);
        lblReasonForVisitError.setVisible(false);
        lblPersonToSeeError.setVisible(false);
      
    }
    
    public void mClearInputs(){
    
      txtDate.setText("");
      txtName.setText("");
      txtTimeIn.setText("");
      txtFromCompany.setText("");
      txtVehicleRegNo.setText("");
      txtNumber.setText("");
      cboReasonForVisit.setSelectedIndex(4);
      txtPersonToSee.setText("");
    
    }
     
     
   
    
    public void mValidateInputs(){  //  makes Sure That No empty inputs
    
    
      if (txtDate.getText().isEmpty()){
      
      
           lblDateError.setVisible(true);
           txtDate.requestFocus();
           boolInvalideData= true;
     
      } else if (txtTimeIn.getText().isEmpty()){
      
      
           lblTimeInError.setVisible(true);
           txtTimeIn.requestFocus();
           boolInvalideData= true;
     
      }else if (txtName.getText().isEmpty()){
      
      
           lblNameError.setVisible(true);
           txtName.requestFocus();
           boolInvalideData= true;
     
      } else if (txtFromCompany.getText().isEmpty()){
      
      
           lblFromCompanyError.setVisible(true);
           txtFromCompany.requestFocus();
           boolInvalideData= true;
     
      }else if (txtVehicleRegNo.getText().isEmpty()){
      
      
           lblRegError.setVisible(true);
           txtVehicleRegNo.requestFocus();
           boolInvalideData= true;
     
      }else if (txtNumber.getText().isEmpty()){
      
      
           lblNumberError.setVisible(true);
           txtNumber.requestFocus();
           boolInvalideData= true;
     
      } else if (cboReasonForVisit.getSelectedItem().toString().isEmpty()){
      
      
           lblReasonForVisitError.setVisible(true);
           cboReasonForVisit.requestFocus();
           boolInvalideData= true;
     
      }else if (txtPersonToSee.getText().isEmpty()){
      
      
           lblPersonToSeeError.setVisible(true);
           txtPersonToSee.requestFocus();
           boolInvalideData= true;
     
      }else {
          
           boolInvalideData=  false;
           strDate =txtDate.getText();
           strTime  =txtTimeIn.getText();
           strName=txtName.getText();
           strFromCompany = txtFromCompany.getText();
           strVehicleRegNo =txtVehicleRegNo.getText();
           strNumber = txtNumber.getText();
           strReasonForVisit = cboReasonForVisit.getSelectedItem().toString();
           strPersonToSee =txtPersonToSee.getText();
           mRemoveErrorMessages();
    
      }
    
    
    
    }
    
    
    

       // Database connection and methods
    
    
    
       
      private int intID;
      final  private String strDBConnectionString= "jdbc:mysql://localhost:3306/gate_access_control?" + "useSSL=false";
      final  private String strDBUser="root";
      final  private String strDBPassword="Password";
      private java.sql.Connection conMySQLConnectionString;
      private Statement stStatement= null;
      private ResultSet rs= null;
      private PreparedStatement pst =null;
   
      private boolean boolRecordExists;
      
    
    
      // method to check record i a database
      
    public void mCheckRecordInDataBase(){
       
  try{
      
          conMySQLConnectionString = DriverManager.getConnection
            (strDBConnectionString, strDBUser, strDBPassword);
            
             conMySQLConnectionString.createStatement();
            stStatement = (Statement) conMySQLConnectionString.createStatement();

            rs= null;
           
            String  strQuery = "Select * from  visitor_records  where name ='"
                    +strName + "' and  time_in  = '" +strTime+"'";
            stStatement.execute(strQuery);
            rs=stStatement.getResultSet();
            boolRecordExists=rs.next();

                       
          } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null,e); 
           }finally{

                            try{

                            stStatement.close();

                            }catch(SQLException e  ){

                            JOptionPane.showMessageDialog(null,"Connection String not closed "+""+e);
                            }
                   }
        }
    
    
     // method Create Record in a data base
    
public void mCreate(){
       
 try{
     conMySQLConnectionString = DriverManager.getConnection
     (strDBConnectionString, strDBUser, strDBPassword);
            
      conMySQLConnectionString.createStatement();
      stStatement = (Statement) conMySQLConnectionString.createStatement();  
           
           
         
      pst=conMySQLConnectionString.prepareStatement("insert into visitor_records"
       + " ( date , time_in , name ,from_company , vehicle_reg_no,tel_no , reason_for_visit , person_to_see) values (?,?,?,?,?,?,?,?)");
      
       pst.setString(1, strDate);
       
       pst.setString(1, strDate);
        pst.setString(2, strTime);
         pst.setString(3, strName);
          pst.setString(4, strFromCompany);
          pst.setString(5, strVehicleRegNo);
           pst.setString(6, strNumber);
            pst.setString(7, strReasonForVisit);
             pst.setString(8, strPersonToSee);
             
             pst.execute();
       
   
      JOptionPane.showMessageDialog(null, "Successfuly registered ");
                       
    } catch (HeadlessException | SQLException e) {
                       JOptionPane.showMessageDialog(null,e); 
    }finally{

             try{

                     stStatement.close();

               }catch(SQLException e  ){

                   JOptionPane.showMessageDialog(null,"Connection String not closed "+""+e);
               }
             }
    }
  
    

   
    
    
    
      public void mUpdateRecord(){
        
         try{
                   
     pst=conMySQLConnectionString.prepareStatement("UPDATE gate_access_control.visitor_records SET"
       + "  date =?, time_in =?, name=? ,from_company=? , vehicle_reg_no=?,tel_no =?, reason_for_visit=? , person_to_see=? WHERE visitorID=?");
      
       pst.setString(1, txtDate.getText());
       
       pst.setString(2, txtTimeIn.getText());
        pst.setString(3, txtName.getText());
         pst.setString(4, txtFromCompany.getText());
          pst.setString(5, txtVehicleRegNo.getText());
          pst.setString(6, txtNumber.getText());
           pst.setString(7, cboReasonForVisit.getSelectedItem().toString());
            pst.setString(8, txtPersonToSee.getText());
             pst.setString(9, String.valueOf(RecordID));
             
             pst.executeUpdate();
            
          
           
            JOptionPane.showMessageDialog(null,"Record Updated Successfully");
                       
          } catch (HeadlessException | SQLException e) {
                        JOptionPane.showMessageDialog(null,e); 
           }finally{

                            try{

                            stStatement.close();

                            }catch(SQLException e  ){

                            JOptionPane.showMessageDialog(null,"Connection String not closed "+""+e);
                            }
                   }
     }
      
      
      

       
       // =========================== Eding Data to a Table============
    
    
     private void mVeiwRecords(){
         
               
                try{

            conMySQLConnectionString = DriverManager.getConnection
            (strDBConnectionString, strDBUser, strDBPassword);
            
             conMySQLConnectionString.createStatement();
            stStatement = (Statement) conMySQLConnectionString.createStatement();
            String  strQuery = "Select * from visitor_records";
            stStatement.execute(strQuery);
            rs=stStatement.getResultSet();
           
            ResultSetMetaData rsmt = rs.getMetaData();
            
            int intColumnCount = rsmt.getColumnCount();
            
            Vector vColumn = new Vector(intColumnCount);
            for(int i = 1;i < intColumnCount; i++){
            
            vColumn .add(rsmt.getCatalogName(i));
            }
            
            Vector vData = new Vector();
            Vector vRow = new Vector();
            DefaultTableModel RecordTable = (DefaultTableModel) tblViewRecord.getModel();
            while(rs.next()){
            
            vRow = new Vector(intColumnCount);
            for (int i =1 ; i <=intColumnCount; i++){
            
               vRow.add(rs.getString(i));
            
            
                 }
            
            vData.add(vRow);
             RecordTable.addRow(vRow);
            }
          
            
                       
          } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null,e); 
           }finally{

                            try{

                            stStatement.close();

                            }catch(Exception e  ){

                            JOptionPane.showMessageDialog(null,"Connection String not closed "+""+e);
                            }
                   }          
    
    }
      
      
       
    
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlDataInputContainer = new javax.swing.JPanel();
        spnTable = new javax.swing.JScrollPane();
        tblViewRecord = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtDate = new javax.swing.JTextField();
        txtTimeIn = new javax.swing.JTextField();
        txtFromCompany = new javax.swing.JTextField();
        txtVehicleRegNo = new javax.swing.JTextField();
        txtNumber = new javax.swing.JTextField();
        txtPersonToSee = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        cboReasonForVisit = new javax.swing.JComboBox<>();
        lblDateError = new javax.swing.JLabel();
        lblTimeInError = new javax.swing.JLabel();
        lblFromCompanyError = new javax.swing.JLabel();
        lblRegError = new javax.swing.JLabel();
        lblNumberError = new javax.swing.JLabel();
        lblReasonForVisitError = new javax.swing.JLabel();
        lblPersonToSeeError = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        lblNameError = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        lblTittle = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btnSubmit = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        btnLogOut = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlDataInputContainer.setBackground(new java.awt.Color(0, 0, 0));
        pnlDataInputContainer.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        pnlDataInputContainer.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        spnTable.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        spnTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                spnTableMouseClicked(evt);
            }
        });

        tblViewRecord.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        tblViewRecord.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Date", "Time in", "Name", "From Company", "Vehicle Reg. No.", "Tel. No", "Reason for Visit", "Person to see"
            }
        ));
        tblViewRecord.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblViewRecordMouseClicked(evt);
            }
        });
        spnTable.setViewportView(tblViewRecord);

        pnlDataInputContainer.add(spnTable, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 10, 770, 520));

        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel6.setText("Person to see");
        jPanel3.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 450, -1, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel8.setText("Reason for Visit:");
        jPanel3.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 390, -1, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel5.setText("Tel. No.");
        jPanel3.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 330, -1, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel4.setText("Vehicle Reg. No.");
        jPanel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, -1, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel3.setText("From Company");
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 210, -1, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel2.setText("Time in");
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, -1, -1));

        txtDate.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jPanel3.add(txtDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 30, 210, 30));

        txtTimeIn.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jPanel3.add(txtTimeIn, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 90, 210, 30));

        txtFromCompany.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jPanel3.add(txtFromCompany, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 210, 210, 30));

        txtVehicleRegNo.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jPanel3.add(txtVehicleRegNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 270, 210, 30));

        txtNumber.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jPanel3.add(txtNumber, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 330, 210, 30));

        txtPersonToSee.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jPanel3.add(txtPersonToSee, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 450, 210, 30));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel1.setText("Date");
        jPanel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, -1));

        cboReasonForVisit.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cboReasonForVisit.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Come to Work", "Visiting a person", "Courier", " ", " ", " " }));
        jPanel3.add(cboReasonForVisit, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 390, 210, 30));

        lblDateError.setFont(new java.awt.Font("Arial", 2, 11)); // NOI18N
        lblDateError.setForeground(new java.awt.Color(255, 0, 51));
        lblDateError.setText("this can not be empty");
        jPanel3.add(lblDateError, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 60, 200, -1));

        lblTimeInError.setFont(new java.awt.Font("Arial", 2, 11)); // NOI18N
        lblTimeInError.setForeground(new java.awt.Color(255, 0, 51));
        lblTimeInError.setText("this can not be empty");
        jPanel3.add(lblTimeInError, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 120, 200, -1));

        lblFromCompanyError.setFont(new java.awt.Font("Arial", 2, 11)); // NOI18N
        lblFromCompanyError.setForeground(new java.awt.Color(255, 0, 51));
        lblFromCompanyError.setText("this can not be empty");
        jPanel3.add(lblFromCompanyError, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 240, 200, -1));

        lblRegError.setFont(new java.awt.Font("Arial", 2, 11)); // NOI18N
        lblRegError.setForeground(new java.awt.Color(255, 0, 51));
        lblRegError.setText("this can not be empty");
        jPanel3.add(lblRegError, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 300, 200, -1));

        lblNumberError.setFont(new java.awt.Font("Arial", 2, 11)); // NOI18N
        lblNumberError.setForeground(new java.awt.Color(255, 0, 51));
        lblNumberError.setText("this can not be empty");
        jPanel3.add(lblNumberError, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 360, 200, -1));

        lblReasonForVisitError.setFont(new java.awt.Font("Arial", 2, 11)); // NOI18N
        lblReasonForVisitError.setForeground(new java.awt.Color(255, 0, 51));
        lblReasonForVisitError.setText("this can not  be empty");
        jPanel3.add(lblReasonForVisitError, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 420, 200, -1));

        lblPersonToSeeError.setFont(new java.awt.Font("Arial", 2, 11)); // NOI18N
        lblPersonToSeeError.setForeground(new java.awt.Color(255, 0, 51));
        lblPersonToSeeError.setText("this can not be empty");
        jPanel3.add(lblPersonToSeeError, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 480, 200, -1));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel9.setText("Name");
        jPanel3.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, -1, -1));

        txtName.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jPanel3.add(txtName, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 150, 210, 30));

        lblNameError.setFont(new java.awt.Font("Arial", 2, 11)); // NOI18N
        lblNameError.setForeground(new java.awt.Color(255, 0, 51));
        lblNameError.setText("this can not be empty");
        jPanel3.add(lblNameError, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 180, 200, -1));

        pnlDataInputContainer.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 470, 520));

        getContentPane().add(pnlDataInputContainer, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 100, 1290, 540));

        jPanel2.setBackground(new java.awt.Color(0, 51, 51));
        jPanel2.setForeground(new java.awt.Color(255, 255, 255));

        lblTittle.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        lblTittle.setForeground(new java.awt.Color(255, 255, 255));
        lblTittle.setText("Gate Access Control ");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(369, Short.MAX_VALUE)
                .addComponent(lblTittle, javax.swing.GroupLayout.PREFERRED_SIZE, 534, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(387, 387, 387))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(lblTittle, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1290, 100));

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));
        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED, null, java.awt.Color.white, null, new java.awt.Color(102, 102, 102)));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnSubmit.setBackground(java.awt.Color.orange);
        btnSubmit.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btnSubmit.setText("Submit");
        btnSubmit.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnSubmit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnSubmitMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnSubmitMouseExited(evt);
            }
        });
        btnSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubmitActionPerformed(evt);
            }
        });
        jPanel1.add(btnSubmit, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 130, 50));

        btnUpdate.setBackground(java.awt.Color.orange);
        btnUpdate.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btnUpdate.setText("Update Record");
        btnUpdate.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnUpdate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnUpdateMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnUpdateMouseExited(evt);
            }
        });
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });
        jPanel1.add(btnUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 10, 230, 50));

        btnClear.setBackground(java.awt.Color.orange);
        btnClear.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btnClear.setText("Clear");
        btnClear.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnClear.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnClearMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnClearMouseExited(evt);
            }
        });
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });
        jPanel1.add(btnClear, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 10, 230, 50));

        btnLogOut.setBackground(java.awt.Color.orange);
        btnLogOut.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btnLogOut.setText("Exit");
        btnLogOut.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnLogOut.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLogOutMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnLogOutMouseExited(evt);
            }
        });
        btnLogOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogOutActionPerformed(evt);
            }
        });
        jPanel1.add(btnLogOut, new org.netbeans.lib.awtextra.AbsoluteConstraints(1040, 10, 220, 50));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 650, 1290, 70));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void spnTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_spnTableMouseClicked
        // TODO add your handling code here:
        
      
         
       
       
        
        
        
       
        
    }//GEN-LAST:event_spnTableMouseClicked

    @SuppressWarnings("deprecation")
    private void btnLogOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogOutActionPerformed
        // TODO add your handling code here:
        
        frmMain frmMain = new frmMain ();
        frmMain.show();
        this.dispose();
    }//GEN-LAST:event_btnLogOutActionPerformed

    private void tblViewRecordMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblViewRecordMouseClicked
        // TODO add your handling code here:
          DefaultTableModel RecordTable = (DefaultTableModel)tblViewRecord.getModel();
         int SelectedRows = tblViewRecord.getSelectedRow(); 
         tblViewRecord.setSelectionBackground(Color.blue);
         RecordID =Integer.parseInt(RecordTable.getValueAt(SelectedRows, 0).toString());
         txtDate.setText(RecordTable.getValueAt(SelectedRows, 1).toString());
         txtTimeIn.setText(RecordTable.getValueAt(SelectedRows, 2).toString());
         txtName.setText(RecordTable.getValueAt(SelectedRows, 3).toString());
         txtFromCompany.setText(RecordTable.getValueAt(SelectedRows, 4).toString());
         txtVehicleRegNo.setText(RecordTable.getValueAt(SelectedRows, 5).toString());
         txtNumber.setText(RecordTable.getValueAt(SelectedRows, 6).toString());
         cboReasonForVisit.setSelectedItem(RecordTable.getValueAt(SelectedRows, 7).toString());
         txtPersonToSee.setText(RecordTable.getValueAt(SelectedRows, 8).toString());
    }//GEN-LAST:event_tblViewRecordMouseClicked

    private void btnSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmitActionPerformed
        // TODO add your handling code here:
         mValidateInputs();
        DefaultTableModel model = (DefaultTableModel) tblViewRecord.getModel();
       
         
         if (boolInvalideData==false){
         
           mCheckRecordInDataBase();
              if (boolRecordExists==false){       
              mCreate();
              mClearInputs();
              DefaultTableModel RecordTable = (DefaultTableModel)tblViewRecord.getModel();
              model.setRowCount(0);
              mVeiwRecords();
              
           }else {
              
             JOptionPane.showMessageDialog(rootPane, "Record Exist");
              
              
      }        } 
    }//GEN-LAST:event_btnSubmitActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        // TODO add your handling code here:
        mClearInputs();
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnSubmitMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSubmitMouseEntered
        // TODO add your handling code here:
       btnSubmit.setBackground(Color.ORANGE);
    }//GEN-LAST:event_btnSubmitMouseEntered

    private void btnSubmitMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSubmitMouseExited
        // TODO add your handling code here:
         btnSubmit.setBackground(Color.YELLOW);
    }//GEN-LAST:event_btnSubmitMouseExited

    private void btnUpdateMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUpdateMouseExited
        // TODO add your handling code here:
        btnUpdate.setBackground(Color.YELLOW);
    }//GEN-LAST:event_btnUpdateMouseExited

    private void btnClearMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnClearMouseExited
        // TODO add your handling code here:
        btnClear.setBackground(Color.YELLOW);
    }//GEN-LAST:event_btnClearMouseExited

    private void btnLogOutMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLogOutMouseExited
        // TODO add your handling code here:
        btnLogOut.setBackground(Color.yellow);
    }//GEN-LAST:event_btnLogOutMouseExited

    private void btnLogOutMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLogOutMouseEntered
        // TODO add your handling code here:
         btnLogOut.setBackground(Color.red);
    }//GEN-LAST:event_btnLogOutMouseEntered

    private void btnClearMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnClearMouseEntered
        // TODO add your handling code here:
         btnClear.setBackground(Color.ORANGE);
    }//GEN-LAST:event_btnClearMouseEntered

    private void btnUpdateMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUpdateMouseEntered
        // TODO add your handling code here:
          btnUpdate.setBackground(Color.ORANGE);
    }//GEN-LAST:event_btnUpdateMouseEntered

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel) tblViewRecord.getModel();  
      mValidateInputs();
        
         
         if (boolInvalideData==false){
         

              mUpdateRecord();
              mClearInputs();
              model.setRowCount(0);
              mVeiwRecords();
          
         
         }else {
         
            JOptionPane.showMessageDialog(rootPane, "Make sure you select Data you want to update from table");
         
         }
    }//GEN-LAST:event_btnUpdateActionPerformed

    
    
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(frmSecurityGuard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmSecurityGuard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmSecurityGuard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmSecurityGuard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmSecurityGuard().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnLogOut;
    private javax.swing.JButton btnSubmit;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<String> cboReasonForVisit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lblDateError;
    private javax.swing.JLabel lblFromCompanyError;
    private javax.swing.JLabel lblNameError;
    private javax.swing.JLabel lblNumberError;
    private javax.swing.JLabel lblPersonToSeeError;
    private javax.swing.JLabel lblReasonForVisitError;
    private javax.swing.JLabel lblRegError;
    private javax.swing.JLabel lblTimeInError;
    private javax.swing.JLabel lblTittle;
    private javax.swing.JPanel pnlDataInputContainer;
    private javax.swing.JScrollPane spnTable;
    private javax.swing.JTable tblViewRecord;
    private javax.swing.JTextField txtDate;
    private javax.swing.JTextField txtFromCompany;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtNumber;
    private javax.swing.JTextField txtPersonToSee;
    private javax.swing.JTextField txtTimeIn;
    private javax.swing.JTextField txtVehicleRegNo;
    // End of variables declaration//GEN-END:variables
}
