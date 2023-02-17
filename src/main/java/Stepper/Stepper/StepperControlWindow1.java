package Stepper.Stepper;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import arduino.Arduino;

public class StepperControlWindow1 {

	protected Shell Window;
	private Text portText;
	private Text baudRateText;
	private Text rpmText;
	private Text speedText;
	Scale scale;
	
	private Label currentPort;
	private Label currentBaudRate;
	private Label currentRPM;
	private Label currentSpeed;
	
	static String port = "COM5";
	static int baudRate = 115200;
	static String eof = "\n";
	static double rpm = 0;
	static double speed = 0;
	static int maxRpm = 225;
	static int minRpm = -maxRpm;
	static double rpmToSpeed = 0.16755;  //1rpm = 1/60rps = 1/30*r*pi mm/s = 1/30*r*pi mm/s = 0.16755 mm/s
	
	static Arduino arduino = new Arduino(port, baudRate);

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			StepperControlWindow1 window = new StepperControlWindow1();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
			
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		Window.open();
		Window.layout();		
		while (!Window.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	
	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		Window = new Shell();
		Window.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				arduino.serialWrite(0 + eof);
				System.out.print("bye!");
			}
		});
		Window.setSize(474, 242);
		Window.setText("StepperControl");
		Window.setLayout(new GridLayout(7, false));
		
		Label lblPort = new Label(Window, SWT.NONE);
		lblPort.setText("Port:");
		new Label(Window, SWT.NONE);
		new Label(Window, SWT.NONE);
		
		portText = new Text(Window, SWT.BORDER);
		portText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.keyCode == 13) {
					try{
						port = (portText.getText());
						arduino.closeConnection();
						arduino = new Arduino(port, baudRate);
						} catch (Exception exc) {
						// TODO Auto-generated catch block
						exc.printStackTrace();
					}
						updateGUI();
						portText.setText("");
					}
				}
		});
		portText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(Window, SWT.NONE);
		new Label(Window, SWT.NONE);
		
		currentPort = new Label(Window, SWT.NONE);
		currentPort.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		currentPort.setText(port);
		
		Label lblBaudrate = new Label(Window, SWT.NONE);
		lblBaudrate.setText("Baudrate:");
		new Label(Window, SWT.NONE);
		new Label(Window, SWT.NONE);
		
		baudRateText = new Text(Window, SWT.BORDER);
		baudRateText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.keyCode == 13) {
					try{
						baudRate = Integer.valueOf(baudRateText.getText());
						arduino.setBaudRate(baudRate);
						} catch (Exception exc) {
						// TODO Auto-generated catch block
						exc.printStackTrace();
					}
						updateGUI();
						baudRateText.setText("");
					}
				}
		});
		baudRateText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(Window, SWT.NONE);
		new Label(Window, SWT.NONE);
		
		currentBaudRate = new Label(Window, SWT.NONE);
		currentBaudRate.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		currentBaudRate.setText(Integer.toString(baudRate));
		new Label(Window, SWT.NONE);
		new Label(Window, SWT.NONE);
		new Label(Window, SWT.NONE);
		new Label(Window, SWT.NONE);
		new Label(Window, SWT.NONE);
		new Label(Window, SWT.NONE);
		new Label(Window, SWT.NONE);
		
		Label lblRpm = new Label(Window, SWT.NONE);
		lblRpm.setText("RPM:");
		new Label(Window, SWT.NONE);
		new Label(Window, SWT.NONE);
		
		rpmText = new Text(Window, SWT.BORDER);
		rpmText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.keyCode == 13) {
					try{
						rpm = Double.valueOf(rpmText.getText());
						speed = rpm*rpmToSpeed;
						System.out.println(rpm);
						if (rpm < minRpm) {
							rpm = minRpm;
							speed = rpm*rpmToSpeed;
							}
						else if (rpm > maxRpm){
							rpm = maxRpm;
							speed = rpm*rpmToSpeed;
							}
						} catch (Exception exc) {
						// TODO Auto-generated catch block
						exc.printStackTrace();
					}
						
						updateGUI();
						rpmText.setText("");
					}
				}
		});
		rpmText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(Window, SWT.NONE);
		new Label(Window, SWT.NONE);
		
		currentRPM = new Label(Window, SWT.NONE);
		currentRPM.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		currentRPM.setText(Double.toString(rpm));
		
		Label lblSpeed = new Label(Window, SWT.NONE);
		lblSpeed.setText("mm/s:");
		new Label(Window, SWT.NONE);
		new Label(Window, SWT.NONE);
		
		
		speedText = new Text(Window, SWT.BORDER);
		speedText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.keyCode == 13) {
					try{
						speed = Integer.valueOf(speedText.getText());
						rpm = speed/rpmToSpeed;
						System.out.println(speed);
						} catch (Exception exc) {
						// TODO Auto-generated catch block
						exc.printStackTrace();
					}
						updateGUI();
						speedText.setText("");
					}
				}
		});
		speedText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(Window, SWT.NONE);
		new Label(Window, SWT.NONE);
		
		currentSpeed = new Label(Window, SWT.NONE);
		currentSpeed.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		currentSpeed.setText(Double.toString(speed));
		
		Button minusRpm = new Button(Window, SWT.CENTER);
		minusRpm.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				System.out.println(rpm);
				if (rpm > minRpm) {
					rpm = rpm-1;
					speed = rpm*rpmToSpeed;
				}
				updateGUI();
			}
		});
		minusRpm.addSelectionListener(new SelectionAdapter() {
		});
		minusRpm.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		minusRpm.setText("-");
		
		Label minRpmlbl = new Label(Window, SWT.NONE);
		minRpmlbl.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		minRpmlbl.setText(Integer.toString(minRpm));
		new Label(Window, SWT.NONE);
		
		scale = new Scale(Window, SWT.NONE);
		GridData gd_scale = new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1);
		gd_scale.widthHint = 225;
		scale.setLayoutData(gd_scale);
		scale.setMaximum(maxRpm-minRpm);
		scale.setSelection((int)rpm+maxRpm);
		new Label(Window, SWT.NONE);
		scale.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				rpm = scale.getSelection() - maxRpm;
				speed = rpm*rpmToSpeed;
				updateGUI();
			}
		});
		
		Label maxRpmlbl = new Label(Window, SWT.NONE);
		maxRpmlbl.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		maxRpmlbl.setText(Integer.toString(maxRpm));
		
		Button plusRpm = new Button(Window, SWT.CENTER);
		plusRpm.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				System.out.println(rpm);
				if (rpm < maxRpm) {
					rpm = rpm+1;
					speed = rpm*rpmToSpeed;
				}
				updateGUI();
			}
		});
		plusRpm.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		plusRpm.setText("+");
		
		Button btnStart = new Button(Window, SWT.NONE);
		btnStart.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent arg0) {
				try {
					arduino.openConnection();
					Thread.sleep(2000);
					updateGUI();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnStart.setText("Start");
		
		Button btnStop = new Button(Window, SWT.NONE);
		btnStop.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				rpm = 0;
				speed = rpm*rpmToSpeed;
				updateGUI();
			}
		});
		btnStop.setText("Stop");
		new Label(Window, SWT.NONE);
		new Label(Window, SWT.NONE);
		new Label(Window, SWT.NONE);
		new Label(Window, SWT.NONE);
		
		Button btnReverse = new Button(Window, SWT.NONE);
		btnReverse.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent arg0) {
				rpm = -rpm;
				speed = rpm*rpmToSpeed;
				updateGUI();
				System.out.print(rpm);
			}
		});
		btnReverse.setText("Reverse");

	}
	
	public void updateGUI() {
		currentPort.setText(port);
		currentBaudRate.setText(Integer.toString(baudRate));
		currentRPM.setText(Double.toString(rpm));
		currentSpeed.setText(Double.toString(speed));
		scale.setSelection((int)rpm+maxRpm);
		currentPort.pack();
		currentBaudRate.pack();
		currentRPM.pack();
		currentSpeed.pack();
		//scale.pack();
		
		try {
			Thread.sleep(0);
			arduino.serialWrite(rpm + eof);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
