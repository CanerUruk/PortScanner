package portScanner;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
    	  SwingUtilities.invokeLater(() -> {
    	        PortScannerGUI app = new PortScannerGUI();
    	        app.setVisible(true);
    	    });
    }
}
