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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.esri.map.JMap;
import com.esri.map.MapOptions;
import com.esri.map.MapOptions.MapType;

/**
 * This application shows how to create a simple map application. The map ({@link JMap})
 * can be creating using a {@link MapOptions} instance, giving you the option to easily
 * set a map type (base layer), latitude and longitude around which to center the map,
 * and zoom level for the map.
 * <p>
 * In addition, simple marker graphics (point) can be added directly to the JMap using
 * {@link JMap#addMarkerGraphic(double, double, String, String)} and
 * {@link JMap#addMarkerGraphic(double, double, String, String, String, String, java.awt.image.BufferedImage)}.
 * Popups are enabled by default on these marker graphics. To disable these popups, use
 * {@link JMap#setMarkerGraphicPopupsEnabled(boolean)}, passing in false to disable.
 */
public class SimpleMap {

    private JMap map;

    // default constructor
    public SimpleMap() { }

    // ------------------------------------------------------------------------
    // Core functionality
    // ------------------------------------------------------------------------
    /**
     * Creates and displays the UI, including the map, for this application.
     */
    public JComponent createUI() throws Exception {

        // application content
        JPanel contentPane = new JPanel(new BorderLayout());

        // map options: topographic map, centered at lat-lon 41.9, 12.5 (Rome), zoom level 12
        MapOptions mapOptions = new MapOptions(MapType.TOPO, 41.9000, 12.5000, 12);

        // create the map using MapOptions
        map = new JMap(mapOptions);
        contentPane.add(map);

        // add marker graphics directly to the map
        map.addMarkerGraphic(41.912402,12.484778, "Villa Borghese", "Lovely park in the center of Rome.");
        map.addMarkerGraphic(41.905000, 12.47777, "My Marker", "Just testing the marker.");
        map.addMarkerGraphic(41.902149, 12.454782, "St Peter's Basilica",
                "Along St Peter's Square (Piazza San Pietro) in the Vatican City.",
                "http://www.neotryte.com/images/roma1_th.jpg",
                "http://en.wikipedia.org/wiki/St._Peter's_Basilica", null);

        // option to use a custom marker image by creating a BufferedImage
        BufferedImage imgMarker = null;
        BufferedImage imgMarker2 = null;
        try {
            // create buffered image from a public URL
            imgMarker = ImageIO.read(new URL("http://www.neotryte.com/images/museum-icon.png"));

        } catch (IOException e) { e.printStackTrace(); }

        // add marker graphic using the custom marker image
        map.addMarkerGraphic(41.898548, 12.476858,
                "Pantheon",
                "Built during the reign of Augustus as a temple to all the gods of ancient Rome.",
                "http://www.neotryte.com/images/roma2_th.jpg",
                "http://en.wikipedia.org/wiki/Pantheon,_Rome", imgMarker);

        return contentPane;
    }

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
                    SimpleMap mapExtentApp = new SimpleMap();
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

    private JFrame createWindow() {
        JFrame window = new JFrame("Basic Map Application");
        window.setSize(1000, 600);
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.getContentPane().setLayout(new BorderLayout());
        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                super.windowClosing(windowEvent);
                map.dispose();
            }
        });
        return window;
    }
}

