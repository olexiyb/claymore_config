package claymore_config;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.osgi.service.startlevel.StartLevel;
import org.eclipse.swt.widgets.Text;

public class ConfigGenerator {

	private static final String CLAYMORE_CONFIG_TXT = "\\claymore\\config.txt";
	private static final String CLAYMORE_START_BAT = "\\claymore\\start.bat";
	private static final String IPCONFIG_TXT = "ipconfig.txt";
	protected Shell shell;
	private List pool1list;
	private Button btnEnableDebug;
	private List configList;
	Map<String, String> wallets = new HashMap<String, String>();
	Map<String, Map<String, String>> pools = new HashMap<String, Map<String, String>>();
	private Combo coin1combo;
	private Combo coin2combo;
	private List pool2list;
	private Group group;
	private List currentConfig;
	private Label lblFile;
	private String dropboxFolder = "C:\\Dropbox\\";
	private String dropboxFolderBash = "C:\\\\Dropbox\\\\";
	private Composite composite;
	private TabFolder tabFolder;
	private TabItem tbtmConfigs;
	private List coinsList;
	private List rigsList;
	private Button generateStart;
	private List startList;
	private List currentStart;
	private TabItem tbtmEthman;
	private Composite composite_1;
	private List ethmanList;;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			ConfigGenerator window = new ConfigGenerator();
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
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	protected void generateConfig() {
		String coin1 = coin1combo.getText();
		String coin2 = coin2combo.getText();
		{
			configList.removeAll();
			configList.add("#" + coin1combo.getText() + "," + coin2combo.getText());
			configList.add("-mport 3333");
			{// coin1
				if (!coin1.equals("")) {
					configList.add("-ewal " + wallets.get(coin1));
					for (String pool : pool1list.getSelection()) {
						configList.add("-epool " + pools.get(coin1).get(pool));
					}
					configList.add("-eworker <rig>");
					configList.add("-epsw x");
					if (coin1.equals("EXP")) {
						configList.add("-allcoins exp");
					}
					else if (!coin1.equals("ETH")) {
						configList.add("-allcoins 1");
						configList.add("-allpools 1");
					}
				}
			}
			{// coin2
				if (!coin2.equals("")) {
					for (String pool : pool2list.getSelection()) {
						configList.add("-dpool " + pools.get(coin2).get(pool));
						if (pool.equals("coinmine.pl")) {
							configList.add("-dwal olexiyb.<rig>");
						}
						else if (coin2.equals("SIA"))
						{
							configList.add("-dwal " + wallets.get(coin2)+".<rig>");													
						}
						else
						{
							configList.add("-dwal " + wallets.get(coin2));							
						}
					}
					if (coin2.equals("SIA"))
					{
						configList.add("-dcoin sia");
						configList.add("-dcri 14");
					}
					else
					{
						configList.add("-dcri 22");
					}
				}
			}
			configList.add("-retrydelay 1");
			configList.add("-r 1");
			if (btnEnableDebug.getSelection()) {
				configList.add("-dbg 1");
				configList.add("-logfile c:\\Users\\Default\\AppData\\Local\\Temp\\claymore_<rig>.log");
			} else {
				configList.add("-dbg -1");
			}
		}
		{
			startList.removeAll();
			startList.add("timeout /t 10");
			startList.add("move /Y c:\\Users\\Default\\AppData\\Local\\Temp\\claymore_<rig>.log " + dropboxFolder);
			startList.add("c:\\Dropbox\\shell\\bash -c \"ipconfig | grep 'IPv4 Address' > " + dropboxFolderBash + "<rig>\\\\" + IPCONFIG_TXT + "\"");
			startList.add("setx GPU_FORCE_64BIT_PTR 0");
			startList.add("setx GPU_MAX_HEAP_SIZE 100");
			startList.add("setx GPU_USE_SYNC_OBJECTS 1");
			startList.add("setx GPU_MAX_ALLOC_PERCENT 100");
			startList.add("setx GPU_SINGLE_ALLOC_PERCENT 100");
			startList.add("EthDcrMiner64.exe");
			startList.add("");
			startList.add("shutdown /r /t 5 /f");
		}
		{
			ethmanList.removeAll();
			ethmanList.add("[main]");
			ethmanList.add("width=1296");
			ethmanList.add("height=1010");
			ethmanList.add("colwidth0=70");
			ethmanList.add("colwidth1=130");
			ethmanList.add("colwidth2=80");
			ethmanList.add("colwidth3=150");
			ethmanList.add("colwidth4=150");
			ethmanList.add("colwidth5=240");
			ethmanList.add("colwidth6=200");
			ethmanList.add("colwidth7=50");
			ethmanList.add("colwidth8=100");
			ethmanList.add("httpport=8000");
			ethmanList.add("warntitle=0");
			ethmanList.add("tray=0");
			ethmanList.add("detailedhash=0");
			ethmanList.add("sound=beep");
			ethmanList.add("soundpause=30");
			ethmanList.add("enablesound=0");
			ethmanList.add("bat=sample.bat");
			ethmanList.add("batpause=5");
			ethmanList.add("batminimized=0");
			ethmanList.add("warndiff=7");
			ethmanList.add("dualcoin="+coin2);
			ethmanList.add("maincoin="+coin1);
			ethmanList.add("scale1=MH/s");
			ethmanList.add("scale2=MH/s");
			ethmanList.add("fontsize=12");
			ethmanList.add("bgcolor=-16777211");
			ethmanList.add("dec1=1");
			ethmanList.add("dec2=1");
			ethmanList.add("updateinterval=5000");
			final File dropDir = new File(dropboxFolder);
			int index = 0;
			int coin1rate = 27;
			int coin2rate = 630;
			if (coin2.equals("SIA"))
			{
				coin2rate = 380;
			}
			for (final File fileEntry : dropDir.listFiles()) {
				int cardsinrig = 6;
				if (fileEntry.isDirectory()) {
					if (fileEntry.getName().startsWith("rig")) {
						String rig = fileEntry.getName();
						rigsList.add(rig);
						String config = dropboxFolder + rig + "\\" + IPCONFIG_TXT;
						try {
							java.util.List<String> lines = Files.readAllLines(Paths.get(config));
							String ip = lines.get(0).split(":")[1];
							ethmanList.add("[rig_" + index + "]");
							if (rig.length() == 4)
							{
								ethmanList.add("name=" + rig.replace("rig", "rig0"));
							}
							else
							{
								ethmanList.add("name=" + rig);
							}
							
							ethmanList.add("ip=" + ip);
							ethmanList.add("port=3333");
							if (rig.equals("rig10"))
							{
								cardsinrig = 2;
							}
							else if (rig.equals("rig13"))
							{
								cardsinrig = 3;
							}
							else if (rig.equals("rig11") || rig.equals("rig18"))
							{
								cardsinrig = 4;
							}
							else if (rig.equals("rig14"))
							{
								cardsinrig = 5;								
							}
							else if (rig.equals("rig7"))
							{
								cardsinrig = 8;								
							}
							ethmanList.add("ethrate="+(coin1rate*cardsinrig));
							ethmanList.add("dcrrate="+(coin2rate*cardsinrig));
							ethmanList.add("warntemp=80");
							ethmanList.add("comments=");
							ethmanList.add("disabled=0");
							index++;
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							//e1.printStackTrace();
						}

					}
				}
			}

		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
//		wallets.put("ETH", "0x1E5Fcc4DC53C32e1CeD848DC1e83E8125f471152");
		wallets.put("ETH", "0x751137d4a77b703bdb252cce68df3a8bfaf8ceab"); //bittrex
//		wallets.put("ETC", "0x47709b5c52984a9d4aD183453D66a8647aeA61e5"); //my personal
		wallets.put("ETC", "0xafb5b61b7fa12281b351839dbb828ffe508feb93"); //bittrex
//		wallets.put("MC", "0xf640c47c565823273c83f68836ae3ac155ad656f"); //my personal
		wallets.put("MC", "0x2d32b9f5e36e6e3bd2fe7394028a88d03ca00224"); //bittrex
//		wallets.put("EXP", "0x181a8736473da7da66ebd47d00342147cc9f39ee"); //my personal
		wallets.put("EXP", "0x181a8736473da7da66ebd47d00342147cc9f39ee"); //bittrex
		wallets.put("UBQ","0x607e9b48ff62804c6dc1c54c34882ec003bef7e2"); //bittrex
		wallets.put("DCR","DsnWmnuZQCxS2YrXdoFsqyJrFSoQqcosAQC"); //bittrex
		wallets.put("SIA","bb22284ece529947ffa4050a4a73416f850c4260845493ef8ababf112d091371c4f59dd03063"); //personal

		{// ETH
			Map<String, String> p = new HashMap<String, String>();
			p.put("1. ethpool.org","eu1.ethpool.org:3333");
			p.put("2. alpereum", "eu.alpereum.ch:3002");
			p.put("3. ethermine", "us1.ethermine.org:4444");
			pools.put("ETH", p);
		}
		{// ETC
			Map<String, String> p = new HashMap<String, String>();
			p.put("ethermine", "eu1-etc.ethermine.org:4444");
			pools.put("ETC", p);
		}
		{// MC
			Map<String, String> p = new HashMap<String, String>();
			p.put("miningclub.info", "musicoin.miningclub.info:8558");
			pools.put("MC", p);
		}
		{// DCR
			Map<String, String> p = new HashMap<String, String>();
//			p.put("decredpool.org", "stratum+tcp://stratum.decredpool.org:3333");
			p.put("coinmine.pl", "stratum+tcp://dcr-eu.coinmine.pl:2222");
//			p.put("zpool.ca", "stratum+tcp://decred.mine.zpool.ca:5744");
			pools.put("DCR", p);
		}
		{// SIA
			Map<String, String> p = new HashMap<String, String>();
			p.put("siamining.com","stratum+tcp://siamining.com:7777");
			p.put("nanopool.org", "stratum+tcp://sia-eu1.nanopool.org:7777");
//			p.put("zpool.ca", "stratum+tcp://decred.mine.zpool.ca:5744");
			pools.put("SIA", p);
		}
		{//EXP
			Map<String, String> p = new HashMap<String, String>();
			p.put("dwarfpool.com", "exp-eu.dwarfpool.com:8018");
			//p.put("xpool.cu.cc", "stratum+tcp://xpool.cu.cc:7091");
			pools.put("EXP", p);		
		}
		{//UBQ
			Map<String, String> p = new HashMap<String, String>();
			p.put("http://ubiqpool.io", "ubiqpool.io:8008");
			//p.put("xpool.cu.cc", "stratum+tcp://xpool.cu.cc:7091");
			pools.put("UBQ", p);		
		}

		shell = new Shell();
		shell.setSize(918, 910);
		shell.setText("SWT Application");

		tabFolder = new TabFolder(shell, SWT.NONE);
		tabFolder.setBounds(9, 10, 883, 822);

		tbtmConfigs = new TabItem(tabFolder, SWT.NONE);
		tbtmConfigs.setText("Configs");

		composite = new Composite(tabFolder, SWT.NONE);
		tbtmConfigs.setControl(composite);

		configList = new List(composite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		configList.setBounds(418, 0, 457, 221);

		group = new Group(composite, SWT.NONE);
		group.setBounds(192, 0, 213, 419);

		coin1combo = new Combo(group, SWT.NONE);
		coin1combo.setBounds(45, 15, 165, 23);

		coin1combo.setItems(new String[] {"ETH", "ETC", "MC", "EXP", "UBQ"});
		Label lblCoin = new Label(group, SWT.NONE);
		lblCoin.setBounds(3, 18, 31, 15);
		lblCoin.setText("Coin1");

		pool1list = new List(group, SWT.BORDER | SWT.MULTI);
		pool1list.setBounds(45, 44, 165, 68);

		Label lblPools = new Label(group, SWT.NONE);
		lblPools.setBounds(3, 48, 36, 15);
		lblPools.setText("Pools");

		Label lblCoin_1 = new Label(group, SWT.NONE);
		lblCoin_1.setBounds(3, 118, 31, 15);
		lblCoin_1.setText("Coin2");

		coin2combo = new Combo(group, SWT.NONE);
		coin2combo.setBounds(45, 118, 165, 23);
		coin2combo.setItems(new String[] {"SIA", "DCR"});

		pool2list = new List(group, SWT.BORDER);
		pool2list.setBounds(45, 147, 165, 68);

		Label label = new Label(group, SWT.NONE);
		label.setBounds(3, 151, 36, 15);
		label.setText("Pools");

		startList = new List(composite, SWT.BORDER);
		startList.setBounds(418, 471, 447, 152);

		generateStart = new Button(composite, SWT.NONE);
		generateStart.setText("Generate start.bat");
		generateStart.setBounds(285, 471, 120, 25);
		
		currentStart = new List(composite, SWT.BORDER);
		currentStart.setBounds(418, 629, 447, 155);
		
		tbtmEthman = new TabItem(tabFolder, SWT.NONE);
		tbtmEthman.setText("Ethman");
		
		composite_1 = new Composite(tabFolder, SWT.NONE);
		tbtmEthman.setControl(composite_1);
		
		ethmanList = new List(composite_1, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		ethmanList.setBounds(10, 38, 687, 746);

		currentConfig = new List(composite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		currentConfig.setBounds(418, 259, 457, 186);

		lblFile = new Label(composite, SWT.NONE);
		lblFile.setBounds(418, 242, 457, 15);
		lblFile.setText("File");

		coinsList = new List(composite, SWT.BORDER);
		coinsList.setBounds(72, 10, 71, 465);

		rigsList = new List(composite, SWT.BORDER | SWT.MULTI);
		rigsList.setLocation(0, 10);
		rigsList.setSize(71, 465);

		btnEnableDebug = new Button(group, SWT.CHECK);
		btnEnableDebug.setSelection(true);
		btnEnableDebug.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				generateConfig();
			}
		});
		btnEnableDebug.setBounds(45, 221, 93, 16);
		btnEnableDebug.setText("enable debug");

		pool2list.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				generateConfig();
			}
		});

		pool1list.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				generateConfig();
			}
		});

		coin2combo.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				String coin2 = coin2combo.getText();
				if (coin2 != "") {
					pool2list.removeAll();
					Map<String, String> p = pools.get(coin2);
					for (Map.Entry<String, String> entry : p.entrySet()) {
						pool2list.add(entry.getKey());
					}
					pool2list.select(0);
				}
				generateConfig();
			}
		});

		coin1combo.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				String coin1 = coin1combo.getText();
				if (coin1 != "") {
					pool1list.removeAll();
					Map<String, String> p = pools.get(coin1);
					for (Map.Entry<String, String> entry : p.entrySet()) {
						pool1list.add(entry.getKey());
					}
					pool1list.select(0);
				}
				generateConfig();
			}
		});
		coin1combo.select(0);
		coin2combo.select(0);
		
		Button btnGenerate = new Button(composite, SWT.NONE);
		btnGenerate.setBounds(285, 440, 120, 25);
		btnGenerate.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				currentConfig.removeAll();
				for (String rig : rigsList.getSelection()) {
					String config = dropboxFolder + rig + CLAYMORE_CONFIG_TXT;
					try {
						Files.move(Paths.get(config), Paths.get(config + ".bak"), REPLACE_EXISTING);
					} catch (IOException e1) {
						e1.printStackTrace();
						currentConfig.add(e1.getMessage());
					}
					try {
						java.util.List<String> lines = new ArrayList<String>();
						for (String configLine : configList.getItems()) {
							String line = configLine.replaceAll("<rig>", rig);
//							if (line.startsWith("-dcri"))
//							{
//								if (rig.equals("rig1"))
//									line = "-dcri 23";
//								else if (rig.equals("rig3"))
//									line = "-dcri 24";
//								else if (rig.equals("rig4"))
//									line = "-dcri 23";
//								else if (rig.equals("rig6"))
//									line = "-dcri 24";
//							}
							lines.add(line);
						}
						Files.write(Paths.get(config), lines, Charset.forName("UTF-8"));
						currentConfig.add(config + " - generated");
					} catch (IOException e1) {
						e1.printStackTrace();
						currentConfig.add(e1.getMessage());
					}
				}
				rigsList.deselectAll();
				readRigsList();
			}
		});
		btnGenerate.setText("Generate config.txt");
		
		Label startFile = new Label(composite, SWT.NONE);
		startFile.setBounds(418, 450, 447, 15);
		startFile.setText("New Label");
		
		generateStart.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				currentConfig.removeAll();
				for (String rig : rigsList.getSelection()) {
					String config = dropboxFolder + rig + CLAYMORE_START_BAT;
					try {
						Files.move(Paths.get(config), Paths.get(config + ".bak"), REPLACE_EXISTING);
					} catch (IOException e1) {
						e1.printStackTrace();
						currentConfig.add(e1.getMessage());
					}
					try {
						java.util.List<String> lines = new ArrayList<String>();
						for (String configLine : startList.getItems()) {
							lines.add(configLine.replaceAll("<rig>", rig));
						}
						Files.write(Paths.get(config), lines, Charset.forName("UTF-8"));
						currentConfig.add(config + " - generated");
					} catch (IOException e1) {
						e1.printStackTrace();
						currentConfig.add(e1.getMessage());
					}
				}
				rigsList.deselectAll();
				readRigsList();
			}
		});
		
		Label ethmanConfig = new Label(composite_1, SWT.NONE);
		ethmanConfig.setBounds(10, 10, 208, 15);
		ethmanConfig.setText("C:\\Dropbox\\ethman\\Options.ini");
		
		Button btnSave = new Button(composite_1, SWT.NONE);
		btnSave.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				String config = ethmanConfig.getText();
				try {
					Files.move(Paths.get(config), Paths.get(config + ".bak"), REPLACE_EXISTING);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				try {
					java.util.List<String> lines = new ArrayList<String>();
					for (String configLine : ethmanList.getItems()) {
						lines.add(configLine);
					}
					Files.write(Paths.get(config), lines, Charset.forName("UTF-8"));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnSave.setBounds(224, 5, 75, 25);
		btnSave.setText("Save");
		rigsList.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				currentConfig.removeAll();
				currentStart.removeAll();
				lblFile.setText("File not selected");
				if (rigsList.getSelection().length == 1) {
					String rig = rigsList.getSelection()[0];
					if (!rig.equals("")) {
						{
							lblFile.setText(dropboxFolder + rig + CLAYMORE_CONFIG_TXT);
							File file = new File(lblFile.getText());
							BufferedReader reader = null;
							try {
								reader = new BufferedReader(new FileReader(file));
								String text = null;
								while ((text = reader.readLine()) != null) {
									currentConfig.add(text);
								}
							} catch (IOException e1) {
								e1.printStackTrace();
								lblFile.setText(e1.getMessage());
							} finally {
								try {
									if (reader != null) {
										reader.close();
									}
								} catch (IOException e2) {
								}
							}
						}
						{
							startFile.setText(dropboxFolder + rig + CLAYMORE_START_BAT);
							File file = new File(startFile.getText());
							BufferedReader reader = null;
							try {
								reader = new BufferedReader(new FileReader(file));
								String text = null;
								while ((text = reader.readLine()) != null) {
									currentStart.add(text);
								}
							} catch (IOException e1) {
								e1.printStackTrace();
								startFile.setText(e1.getMessage());
							} finally {
								try {
									if (reader != null) {
										reader.close();
									}
								} catch (IOException e2) {
								}
							}
						}

					}
				}
			}
		});
		readRigsList();

	}

	private void readRigsList() {
		coinsList.removeAll();
		rigsList.removeAll();
		final File dropDir = new File(dropboxFolder);
		for (final File fileEntry : dropDir.listFiles()) {
			if (fileEntry.isDirectory()) {
				if (fileEntry.getName().startsWith("rig")) {
					String rig = fileEntry.getName();
					rigsList.add(rig);
					String config = dropboxFolder + rig + CLAYMORE_CONFIG_TXT;
					try {
						java.util.List<String> lines = Files.readAllLines(Paths.get(config));
						coinsList.add(lines.get(0).replace("#", ""));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}
			}
		}
	}

	protected void setPool1list(String[] items) {
		pool1list.setItems(items);
	}

	public List getPool1list() {
		return pool1list;
	}

	protected List getConfigList() {
		return configList;
	}

	protected Combo getCoin1combo() {
		return coin1combo;
	}

	protected Combo getCoin2combo() {
		return coin2combo;
	}

	protected List getPool2list() {
		return pool2list;
	}

	protected Label getLblFile() {
		return lblFile;
	}

	protected List getRigsList() {
		return rigsList;
	}

	protected List getCoinsList() {
		return coinsList;
	}
	protected List getStartList() {
		return startList;
	}
}
