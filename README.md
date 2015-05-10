# StreamRec

This is a Griffon-based rework of my old StreamRecorder application, written in 2002 to play with downloading web-based 
media streams. Converting this application seemed like a good opportunity to learn Griffon.

> Note: somewhere along the line this stopped working (even the old version); maybe stream servers have changed over the past 13 years. At this point
this application does nothing more than demonstrate the UI development with Griffon then simply logs the interaction with the backend. I really don't 
want to spend the time fixing something that I don't even need. Sorry.

The original project code consisted of one Java file with no external dependencies:

```java
package com.stehno.mp3.recorder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

public class StreamRecorder extends JFrame implements ActionListener {

    private static final long serialVersionUID = -4275001129805888659L;
    private static final int WIDTH = 400;
    private static final int HEIGHT = 165;
    private JTextField fileTxt, streamTxt;
    private JCheckBox limitCb;
    private JSpinner limitSpinner;
    private JLabel bytesValue;
    private File saveFile;
    private URL streamUrl;
    private Recorder recorder;
    private Timer timer;
    private FileSizeUpdater fileSizeUpdater;

    private StreamRecorder() {
        super("StreamRecorder");

        final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((int) Math.round((screen.getWidth() - (double) WIDTH) / 2.0d), (int) Math.round((screen.getHeight() - (double) HEIGHT) / 2.0d), WIDTH, HEIGHT);

        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        final Container content = getContentPane();
        content.setLayout(null);

        setupStreamGui(content);
        setupFileGui(content);
        setupLimiterGui(content);
        setupBytesGui(content);
        setupControlButtonsGui(content);

        setVisible(true);

        this.timer = new Timer(false);
    }

    public void actionPerformed(ActionEvent evt) {
        String cmd = evt.getActionCommand();
        if (cmd.equals("SelectFile")) {
            doSelectFile();
        } else if (cmd.equals("SelectStream")) {
            doSelectStream();
        } else if (cmd.equals("Start")) {
            doStart();
        } else if (cmd.equals("Stop")) {
            doStop();
        } else if (cmd.equals("Limit")) {
            if (limitSpinner.isEnabled()) {
                limitSpinner.setEnabled(false);
            } else {
                limitSpinner.setEnabled(true);
            }
        }
    }

    public static void main(String[] args) {
        new StreamRecorder();
    }

    private void doStart() {
        if (streamUrl != null && saveFile != null) {
            System.out.println("Recording " + streamUrl.toString() + " into " + saveFile.toString());
            this.recorder = new Recorder(streamUrl, saveFile);
            Thread t = new Thread(recorder);
            t.start();

            if (fileSizeUpdater == null) {
                fileSizeUpdater = new FileSizeUpdater();
            }
            timer.scheduleAtFixedRate(fileSizeUpdater, 0, 100);
        }
    }

    private void doStop() {
        if (recorder != null) {
            recorder.stopRecording();
            recorder = null;

            fileSizeUpdater.cancel();

            System.out.println("Recording stopped.");
        }
    }

    private void doSelectStream() {
        String data = JOptionPane.showInputDialog(this, "Input the URL of the stream to record:");
        if (data != null) {
            try {
                streamUrl = new URL(data);
                streamTxt.setText(data);
            } catch (MalformedURLException mfu) {
                handleException("Invalid Stream URL", mfu.toString());
            }
        }
    }

    private void handleException(String title, String message) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
    }

    private void doSelectFile() {
        JFileChooser fileChooser = new JFileChooser();
        int state = fileChooser.showSaveDialog(this);
        if (state == JFileChooser.APPROVE_OPTION) {
            saveFile = fileChooser.getSelectedFile();
            fileTxt.setText(saveFile.toString());
        }
    }

    private void setupControlButtonsGui(Container content) {
        JButton startBtn = new JButton("Start");
        startBtn.addActionListener(this);
        startBtn.setBounds(95, 105, 100, 20);
        content.add(startBtn);

        JButton stopBtn = new JButton("Stop");
        stopBtn.addActionListener(this);
        stopBtn.setBounds(200, 105, 100, 20);
        content.add(stopBtn);
    }

    private void setupBytesGui(Container content) {
        JLabel bytesLbl = new JLabel("Recorded:");
        bytesLbl.setBounds(5, 80, 75, 20);
        content.add(bytesLbl);

        bytesValue = new JLabel("0.00 MB", JLabel.CENTER);
        bytesValue.setBounds(80, 80, 100, 20);
        content.add(bytesValue);
    }

    private void setupFileGui(Container content) {
        addLabel(content, "File:", 5, 30, 50, 20);

        fileTxt = new JTextField("<none>");
        fileTxt.setEditable(false);
        fileTxt.setBounds(60, 30, 300, 20);
        content.add(fileTxt);

        JButton fileBtn = new JButton("...");
        fileBtn.setActionCommand("SelectFile");
        fileBtn.addActionListener(this);
        fileBtn.setBounds(362, 30, 30, 20);
        content.add(fileBtn);
    }

    private void setupLimiterGui(Container content) {
        limitCb = new JCheckBox("Limit to");
        limitCb.setActionCommand("Limit");
        limitCb.setBounds(5, 55, 75, 20);
        limitCb.addActionListener(this);
        content.add(limitCb);

        final SpinnerNumberModel model = new SpinnerNumberModel();
        model.setMinimum(0);
        model.setMaximum(1000);
        model.setStepSize(100);
        model.setValue(100);

        limitSpinner = new JSpinner(model);
        limitSpinner.setBounds(80, 55, 60, 20);
        limitSpinner.setEnabled(false);
        content.add(limitSpinner);

        addLabel(content, "MB", 145, 55, 50, 20);
    }

    private void addLabel(Container c, String text, int x, int y, int w, int h) {
        final JLabel lbl = new JLabel(text);
        lbl.setBounds(x, y, w, h);
        c.add(lbl);
    }

    private void setupStreamGui(Container content) {
        JLabel streamLbl = new JLabel("Stream:");
        streamLbl.setBounds(5, 5, 60, 20);
        content.add(streamLbl);

        streamTxt = new JTextField("<none>");
        streamTxt.setEditable(false);
        streamTxt.setBounds(70, 5, 290, 20);
        content.add(streamTxt);

        JButton streamBtn = new JButton("...");
        streamBtn.setActionCommand("SelectStream");
        streamBtn.addActionListener(this);
        streamBtn.setBounds(362, 5, 30, 20);
        content.add(streamBtn);
    }

    private class FileSizeUpdater extends TimerTask {

        private final DecimalFormat formatter = new DecimalFormat("0.00 MB");
        private final double conversion = 1000000.0d;
        private long bytesRecorded = 0;

        public void run() {
            if (saveFile != null) {
                bytesRecorded = saveFile.length();
                final double mbRec = ((double) bytesRecorded) / conversion;

                if (limitCb.isSelected() && mbRec >= ((Number) limitSpinner.getValue()).doubleValue()) {
                    doStop();
                }

                bytesValue.setText(formatter.format(mbRec));
            }
        }
    }

    private class Recorder implements Runnable {

        private URL url;
        private File file;
        private BufferedOutputStream out;
        private BufferedInputStream in;
        private boolean run = true;

        Recorder(URL url, File file) {
            this.url = url;
            this.file = file;
        }

        public void run() {
            try {
                in = new BufferedInputStream(url.openStream());
                out = new BufferedOutputStream(new FileOutputStream(file));

                int x = in.read();
                while (run && x != -1) {
                    try {
                        out.write(x);
                        x = in.read();
                    } catch (Exception ex) {
                        continue;
                    }
                }

                stopRecording();

            } catch (IOException ioe) {
                handleException("IO Error", ioe.toString());
            }
        }

        public void stopRecording() {
            try {
                run = false;
                out.close();
                in.close();
            } catch (IOException ioe) {
                handleException("IO Error", ioe.toString());
            }
        }
    }
}
```