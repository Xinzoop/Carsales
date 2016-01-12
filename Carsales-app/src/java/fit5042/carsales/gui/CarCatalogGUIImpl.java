/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fit5042.carsales.gui;

import fit5042.carsales.entities.Car;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author zipv5_000
 */
public class CarCatalogGUIImpl extends JFrame implements CarsalesGUI, ListSelectionListener {
    
    // Store current car list for display
    private List<Car> carList;
    
    private static final String[] TABLE_COLUMNS = {"VIN", "Make", "Model Name", "Price"};
    
    private JTextField tbxMake;
    private JTextField tbxModelName;
    private JTextField tbxModelNo;
    private JComboBox cbType;
    
    private JButton btnSearch;
    private JButton btnClose;
    
    private JTable tblResult;
    
    private JLabel lblVIN;
    private JLabel lblThumbnail;
    private JLabel lblPrice;
    private JLabel lblMake;
    private JLabel lblModelNo;
    private JLabel lblModelName;
    private JLabel lblType;
    private JTextArea txtDesc;
    private JLabel lblURL;
    
    public CarCatalogGUIImpl(ActionListener listner) {
        super("Smallwood Carsales");
        
        JPanel pnlSearch = new JPanel();
        pnlSearch.setLayout(new GridLayout(5, 2));
        JLabel lblSearchMake = new JLabel("Make:");
        pnlSearch.add(lblSearchMake);
        tbxMake = new JTextField();
        pnlSearch.add(tbxMake);
        JLabel lblSearchModelName = new JLabel("Model:");
        pnlSearch.add(lblSearchModelName);
        tbxModelName = new JTextField();
        pnlSearch.add(tbxModelName);
        JLabel lblSearchModelNo = new JLabel("Make No.:");
        pnlSearch.add(lblSearchModelNo);
        tbxModelNo = new JTextField();
        pnlSearch.add(tbxModelNo);
        JLabel lblSearchType = new JLabel("Type:");
        pnlSearch.add(lblSearchType);
        cbType = new JComboBox(Car.CarType.values());
        pnlSearch.add(cbType);
        
        btnSearch = new JButton("Search");
        btnSearch.addActionListener(listner);
        pnlSearch.add(btnSearch);
        btnClose = new JButton("Close");
        btnClose.addActionListener(listner);
        pnlSearch.add(btnClose);
        pnlSearch.setBorder(
        BorderFactory.createCompoundBorder(
                                BorderFactory.createTitledBorder("Search Criteria"),
                                BorderFactory.createEmptyBorder(5,5,5,5))
        );
        
        // create table
        tblResult = new JTable(new DefaultTableModel(TABLE_COLUMNS, 0));
        tblResult.getSelectionModel().addListSelectionListener(this); 
        TableColumnModel tm = tblResult.getColumnModel();       
        tm.getColumn(0).setPreferredWidth(300);
        tm.getColumn(1).setPreferredWidth(300);
        tm.getColumn(2).setPreferredWidth(300);
        tm.getColumn(3).setPreferredWidth(300);
        
        lblVIN = new JLabel();
        lblThumbnail = new JLabel();
        lblPrice = new JLabel();
        lblMake = new JLabel();
        lblModelName = new JLabel();
        lblType = new JLabel();
        
        JPanel pnlBrief = new JPanel();        
        pnlBrief.setLayout(new BoxLayout(pnlBrief, BoxLayout.Y_AXIS));
        pnlBrief.add(lblPrice);
        pnlBrief.add(lblVIN);
        pnlBrief.add(lblMake);
        pnlBrief.add(lblModelName);
        pnlBrief.add(lblType);
        
        JPanel pnlTop = new JPanel();
        pnlTop.setLayout(new BoxLayout(pnlTop, BoxLayout.X_AXIS));
        pnlTop.add(lblThumbnail);
        lblThumbnail.setAlignmentX(Component.LEFT_ALIGNMENT);
        pnlTop.add(pnlBrief);
        
        lblModelNo = new JLabel();
        txtDesc = new JTextArea();
        textAreaProperties(txtDesc);
        lblURL = new JLabel();
        
        JPanel pnlDetails = new JPanel();
        pnlDetails.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder("Details"),
                        BorderFactory.createEmptyBorder(5, 5, 5, 5))
        );
        pnlDetails.setLayout(new BoxLayout(pnlDetails, BoxLayout.Y_AXIS));
        pnlDetails.add(pnlTop);
        pnlTop.setAlignmentX(Component.LEFT_ALIGNMENT);
        pnlDetails.add(lblModelNo);
        lblModelNo.setAlignmentX(Component.LEFT_ALIGNMENT);
        pnlDetails.add(lblURL);
        lblURL.setAlignmentX(Component.LEFT_ALIGNMENT);
        pnlDetails.add(txtDesc);
        txtDesc.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(pnlSearch, BorderLayout.NORTH);        
        panel.add(new JScrollPane(tblResult), BorderLayout.CENTER);
        
        JSplitPane pnlSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panel, pnlDetails);
        pnlSplit.setDividerLocation(400);
        Container container = getContentPane();
        container.add(pnlSplit);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setSize(800, 600);
        this.setVisible(true);
    }
    
    private JTextArea textAreaProperties(JTextArea textArea) {
        textArea.setEditable(false);
        textArea.setCursor(null);
        textArea.setOpaque(false);
        textArea.setFocusable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        return textArea;
    }
    
    // Display car details
    private void showDetails(Car c){
        lblVIN.setText("VIN: " + c.getVIN());
        lblPrice.setText("Price: $" + c.getPrice());
        lblThumbnail.setIcon(readThumbnail(c.getThumbnail()));
        lblMake.setText("Make:" + c.getMake());
        lblModelName.setText("Model: " + c.getModelName());
        lblModelNo.setText("Model No.:" + c.getModelNo());
        lblType.setText("Type: " + c.getType());
        txtDesc.setText("Description: " + c.getDescription());
        lblURL.setText("URL: " + c.getPreviewURL());
    }
    
    private Icon readThumbnail(String path){
        try {
            URL url = new URL(path);
            Image img = ImageIO.read(url).getScaledInstance(240, 160, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        } catch (Exception ex) {
            Logger.getLogger(CarCatalogGUIImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public String getMake() throws Exception {
        return tbxMake.getText();
    }

    @Override
    public String getModelName() throws Exception {
        return tbxModelName.getText();
    }

    @Override
    public String getModelNo() throws Exception {
        return tbxModelNo.getText();
    }

    @Override
    public Car.CarType getCarType() throws Exception {
        return (Car.CarType)cbType.getSelectedItem();
    }

    @Override
    public JButton getSearchButton() {
        return btnSearch;
    }

    @Override
    public JButton getCloseButton() {
        return btnClose;
    }

    @Override
    public void showCars(List<Car> carList) {
        this.carList = carList;
        DefaultTableModel tm = (DefaultTableModel)tblResult.getModel();
        // Clear
        tm.setRowCount(0);
        // Add
        for(Car c : carList){
            tm.addRow(new Object[]{c.getVIN(), c.getMake(), c.getModelName(), c.getPrice()});
        }
    }
    
    @Override
    public void valueChanged(ListSelectionEvent lse) {        
        if ((lse.getSource() == tblResult.getSelectionModel())
            && (! lse.getValueIsAdjusting()))
        {
            int i = tblResult.getSelectedRow();
            if(i >=0 && i< tblResult.getRowCount())
                showDetails(carList.get(tblResult.getSelectedRow()));
        }
    }

    @Override
    public void showMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

}
