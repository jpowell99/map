// Copyright 2013 ESRI
//
// All rights reserved under the copyright laws of the United States
// and applicable international laws, treaties, and conventions.
//
// You may freely redistribute and use this sample code, with or
// without modification, provided you include the original copyright
// notice and use restrictions.
//
// See the use restrictions.
//

package com.esri.client.samples.map;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

import com.esri.core.geometry.Envelope;
import com.esri.map.ArcGISTiledMapServiceLayer;
import com.esri.map.JMap;
import com.esri.map.MapEvent;
import com.esri.map.MapEventListener;
import com.esri.map.MapEventListenerAdapter;

/**
 * <p>
 * This application displays the corner values the current extent. As the extent of the map
 * changes by zooming in/out of the map, the extent changes.
 * </p>
 *
 * <p>
 * The change in the extent is monitored by providing a {@link MapEventListener} or
 * {@link MapEventListenerAdapter} to the JMap.
 * </p>
 */
public class MapExtentApp {

    private JMap map;
    private static DecimalFormat decimalFormat = new DecimalFormat("##.##");
    private static String NEWLINE = System.getProperty("line.separator");
    private final Color BG_COLOR = new Color(0, 0, 0, 255);
    private final String URL_WORLD_TOPO =
            "http://services.arcgisonline.com/ArcGIS/rest/services/World_Topo_Map/MapServer";
    // ------------------------------------------------------------------------
// Constructors
// ------------------------------------------------------------------------
    public MapExtentApp() {

    }

// ------------------------------------------------------------------------
// Core functionality
// ------------------------------------------------------------------------
    /**
     * Creates and displays the UI, including the map, for this application.
     */
    public JComponent createUI() throws Exception {

        // application content
        JComponent contentPane = createContentPane();

        // map
        map = createMap();

        // title
        JTextField txtTitle = new JTextField();
        txtTitle.setText("Current Extent");
        txtTitle.setHorizontalAlignment(SwingConstants.CENTER);
        txtTitle.setFont(new Font(txtTitle.getFont().getName(), Font.PLAIN, 16));
        txtTitle.setMaximumSize(new Dimension(300, 30));
        txtTitle.setBackground(new Color(0, 0, 0, 100));
        txtTitle.setForeground(Color.WHITE);
        txtTitle.setBorder(BorderFactory.createEmptyBorder(5,0,5,0));

        // UI for extent info
        final JTextArea txtStatus = new JTextArea();
        txtStatus.setLineWrap(true);
        txtStatus.setWrapStyleWord(true);
        txtStatus.setFont(new Font(txtStatus.getFont().getName(), txtStatus.getFont().getStyle(), 12));
        txtStatus.setMaximumSize(new Dimension(300, 100));
        txtStatus.setBackground(BG_COLOR);
        txtStatus.setForeground(Color.WHITE);
        txtStatus.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));

        final JTextArea txtSpatial = new JTextArea();
        txtSpatial.setLineWrap(true);
        txtSpatial.setWrapStyleWord(true);
        txtSpatial.setFont(new Font(txtStatus.getFont().getName(), txtStatus.getFont().getStyle(), 12));
        txtSpatial.setMaximumSize(new Dimension(300, 100));
        txtSpatial.setBackground(BG_COLOR);
        txtStatus.setForeground(Color.WHITE);
        txtSpatial.setBorder(BorderFactory.createEmptyBorder(45,15,15,15));

        // zoom to Pacific NorthWest at start
        map.setExtent(new Envelope(-14384172, 4578761, -11982215, 6180881));

        // map event listeners
        map.addMapEventListener(new MapEventListenerAdapter() {

            @Override
            public void mapExtentChanged(MapEvent arg0) {
                if (map.isReady()) {
                    StringBuilder str = new StringBuilder();
                    str.append("MinX: " + decimalFormat.format(map.getExtent().getXMin()) + NEWLINE);
                    str.append("MinY: " + decimalFormat.format(map.getExtent().getYMin()) + NEWLINE);
                    str.append("MaxX: " + decimalFormat.format(map.getExtent().getXMax()) + NEWLINE);
                    str.append("MaxY: " + decimalFormat.format(map.getExtent().getYMax()) + NEWLINE);
                    txtStatus.setText(str.toString());
                }
            }
        });

        map.addMapEventListener(new MapEventListenerAdapter(){

            @Override
            public void mapReady(MapEvent arcg0){
                if(map.isReady()){
                    StringBuilder str = new StringBuilder();
                    str.append("Map Units: " + map.getMapUnits());
                    txtSpatial.setText(str.toString());

                }
            }
        });

        // group the above UI items into a panel
        final JPanel controlPanel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(controlPanel, BoxLayout.Y_AXIS);
        controlPanel.setLayout(boxLayout);
        controlPanel.setLocation(10, 10);
        controlPanel.setSize(170, 135);
        controlPanel.setBackground(new Color(0, 0, 0, 100));
        controlPanel.setBorder(new LineBorder(Color.BLACK, 3));
        controlPanel.add(txtTitle);
        controlPanel.add(txtStatus);
        controlPanel.add(txtSpatial);

        // add the panel to the main window
        contentPane.add(controlPanel);

        contentPane.add(map);

        return contentPane;
    }

// ------------------------------------------------------------------------
// Static methods
// ------------------------------------------------------------------------
    /**
     * Starting point of this application.
     * @param args arguments to this application.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    // instance of this application
                    MapExtentApp mapExtentApp = new MapExtentApp();

                    // create the UI, including the map, for the application.
                    JFrame appWindow = mapExtentApp.createWindow();
                    appWindow.add(mapExtentApp.createUI());
                    appWindow.setVisible(true);
                } catch (Exception e) {
                    // on any error, display the stack trace.
                    e.printStackTrace();
                }
            }
        });
    }

// ------------------------------------------------------------------------
// Private methods
// ------------------------------------------------------------------------
    /**
     * Creates a map.
     * @return a map.
     */
    private JMap createMap() {
        final JMap jMap = new JMap();

        // add base layer
        final ArcGISTiledMapServiceLayer baseLayer = new ArcGISTiledMapServiceLayer(URL_WORLD_TOPO);
        jMap.getLayers().add(baseLayer);

        return jMap;
    }

    /**
     * Creates a window.
     * @return a window.
     */
    private JFrame createWindow() {
        JFrame window = new JFrame("Map Extent Application");
        window.setBounds(100, 100, 1000, 700);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.getContentPane().setLayout(new BorderLayout(0, 0));
        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                super.windowClosing(windowEvent);
                map.dispose();
            }
        });
        return window;
    }

    /**
     * Creates a content pane.
     * @return a content pane.
     */
    private JLayeredPane createContentPane() {
        JLayeredPane contentPane = new JLayeredPane();
        contentPane.setBounds(100, 100, 1000, 700);
        contentPane.setLayout(new BorderLayout(0, 0));
        contentPane.setVisible(true);
        return contentPane;
    }
}

