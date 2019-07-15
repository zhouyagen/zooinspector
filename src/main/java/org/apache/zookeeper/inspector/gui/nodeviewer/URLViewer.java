package org.apache.zookeeper.inspector.gui.nodeviewer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.net.URLDecoder;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.apache.zookeeper.inspector.manager.ZooInspectorNodeManager;

/**
 * URLViewer
 */
public class URLViewer extends ZooInspectorNodeViewer {
    
    private final JPanel dataPanel;
    private JTextArea textArea;
    private Pattern iPattern = Pattern.compile("((2(5[0-5]|[0-4]\\d))|[0-1]?\\d{1,2})(\\.((2(5[0-5]|[0-4]\\d))|[0-1]?\\d{1,2})){3}:\\d+");

    public URLViewer(){
        this.setLayout(new BorderLayout());
        this.dataPanel = new JPanel();
        this.dataPanel.setBackground(Color.WHITE);
        textArea = new JTextArea(35, 30 );
        textArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        textArea.setLineWrap(true);
        textArea.setText("url data");
        JScrollPane textScroll = new JScrollPane(this.textArea);
        this.add(textScroll, BorderLayout.CENTER);
    }

    @Override
    public String getTitle() {
        return "DUBBO Data";
    }

    @Override
    public void nodeSelectionChanged(List<String> selectedNodes) {
        if(selectedNodes != null & selectedNodes.size() > 0){
            try {
                String url = URLDecoder.decode(selectedNodes.get(0),"utf-8");
                StringBuilder data = new StringBuilder(url);
                data.append("\n\n");
                String[] params = url.split("\\?");
                if(params != null && params.length > 0){
                    String query = params[1]; 
                    params = query.split("&");
                    if(params != null && params.length > 0){
                        for(String p : params){
                            data.append("\n");
                            data.append(p);
                        }
                    }
                }
                Matcher matcher = iPattern.matcher(url);
                if(matcher.find()){
                    StringBuilder ip = new StringBuilder("\nip= ");
                    ip.append(url.substring(matcher.start(), matcher.end()));
                    data.append(ip.toString());
                }
                textArea.setText(data.toString());
            } catch (Exception e) {
                textArea.setText("error");
            }
        }
    }

    @Override
    public void setZooInspectorManager(ZooInspectorNodeManager zooInspectorManager) {

    }

    
}