
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.ArrayList;

public class GUI extends JPanel {
	final static int NUM_THREADS = 1;
	FormalSeatingGenetic ga[];
	
	JPanel toolbar;
	JPanel drawPanel;
	
	int bestY = 9999999;
		
	ArrayList<FormalSeatingGenetic.GenerationStatistics> allGens;
		
    private SwingWorker<Void, Integer> worker;

	private void redrawGraph(Graphics g, FormalSeatingGenetic.GenerationStatistics genStats) {
		if (genStats.terminate) return;
		
		super.paintComponent(g);
		//g.setColor(new Color(0xFFFFFF));
		//g.fillRect(0, 0, 1200, 750);
		
		int maxGen = allGens.size();
		int width = 1000;
		int height = 400;
		int maxFit = 6000;
		
		g.setColor(new Color(0x000000));
		g.drawString(String.format("Gen %d", maxGen), width - 20, height + 40);
		g.drawString(String.format("%d", maxFit / 5 * 5), 5, 25 + height / 5 * 0 + 5);
		g.drawString(String.format("%d", maxFit / 5 * 4), 5, 25 + height / 5 * 1 + 5);
		g.drawString(String.format("%d", maxFit / 5 * 3), 5, 25 + height / 5 * 2 + 5);
		g.drawString(String.format("%d", maxFit / 5 * 2), 5, 25 + height / 5 * 3 + 5);
		g.drawString(String.format("%d", maxFit / 5 * 1), 5, 25 + height / 5 * 4 + 5);
		g.drawString(String.format("%d", maxFit / 5 * 0), 5, 25 + height / 5 * 5 + 5);
				
		int incr = 25;
		for (int i = 0; i <= incr; ++i) {
			if (i % 5 == 0) {
				g.drawLine(40, 24 + height / incr * i, 47, 24 + height / incr * i);
				g.drawLine(40, 25 + height / incr * i, 47, 25 + height / incr * i);
				
				g.drawLine(width + 60, 24 + height / incr * i, width + 67, 24 + height / incr * i);
				g.drawLine(width + 60, 25 + height / incr * i, width + 67, 25 + height / incr * i);
			} else {
				g.drawLine(40, 25 + height / incr * i, 45, 25 + height / incr * i);
				
				g.drawLine(width + 60, 25 + height / incr * i, width + 65, 25 + height / incr * i);
			}
			
		}
		
	
		int arrStats[] = ga[0].getArrangementStatistics(allGens.get(maxGen - 1).bestData);
		
		int lone = arrStats[0];
		int lonePercent = arrStats[0] * 100 / arrStats[4];
		int happy = arrStats[2];
		int happyPercent = arrStats[2] * 100 / arrStats[4];
		int all = arrStats[3];
		int allPercent = arrStats[3] * 100 / arrStats[4];
		if (lonePercent == 0 && lone != 0) lonePercent = 1;
		if (happyPercent == 0 && happy != 0) happyPercent = 1;
		if (allPercent == 0 && all != 0) allPercent = 1;
		int splitCouples = allGens.get(maxGen - 1).splitCouples;
		int totalCouples = allGens.get(maxGen - 1).totalCouples;
		
		int overallRank = 100;
		overallRank /= splitCouples + 1;
		overallRank /= lonePercent + 1;
		overallRank *= happyPercent;
		overallRank = (int) Math.sqrt((double) overallRank);

		
		g.drawString(String.format("Best: %d", allGens.get(maxGen - 1).bestFitness), 80, 25);

		g.drawString(String.format("%d%%", overallRank), 400, 25);

		g.drawString(String.format("Lonely people: %d (%d%%)", lone, lonePercent), 80, 45);
		g.drawString(String.format("Happy people: %d (%d%%)", happy, happyPercent), 80, 65);
		//g.drawString(String.format("People with all: %d (%d%%)", all, allPercent), 80, 85);
		g.drawString(String.format("Split couples: %d / %d", splitCouples / 2, totalCouples / 2), 80, 85);

		int skip = maxGen / 175 + 1;
		
		int prevBest = 0;
		int prevAvg = 0;
		int prevWorst = 0;
		int prevX = 0;
		
		

		for (int gen = 0; gen < maxGen; ++gen) {
			if ((gen % skip) != 0) continue;
			
			FormalSeatingGenetic.GenerationStatistics stats = allGens.get(gen);

			int x = width * gen / (maxGen + 1);
	
			int best = height - stats.bestFitness * height / maxFit;	
			int avg = height - stats.avgFitness * height / maxFit;	
			int worst = height - stats.worstFitness * height / maxFit;	
			
			if (best < bestY) {
				bestY = best;
			}
			
			if (gen != 0) {
				g.setColor(new Color(0x000000));
				g.drawLine(x + 50, best + 25, prevX + 50, prevBest + 25);
				g.setColor(new Color(0x0000FF));
				g.drawLine(x + 50, avg + 25, prevX + 50, prevAvg + 25);
				g.setColor(new Color(0xFF0000));
				g.drawLine(x + 50, worst + 25, prevX + 50, prevWorst + 25);

			}
			
			prevBest = best;
			prevAvg = avg;
			prevWorst = worst;
			prevX = x;
		}
		
		g.setColor(new Color(0x00AA00));
		g.drawLine(50, bestY + 24, width + 50, bestY + 24);
	}
	
	JSpinner popSpin;
	JSlider mutSlider;
	JSlider matSlider;
	JSlider eliSlider;
	
	public GUI() {			
		allGens = new ArrayList<FormalSeatingGenetic.GenerationStatistics>();
		ga = new FormalSeatingGenetic[NUM_THREADS];
		for (int i = 0; i < NUM_THREADS; ++i) {
			ga[i] = new FormalSeatingGenetic();
		}

		setPreferredSize(new Dimension(1200, 750));

		
		toolbar = new JPanel();
		toolbar.setLayout(new BoxLayout(toolbar, BoxLayout.Y_AXIS));
		add(toolbar);

		//add selection types
		ButtonGroup samplingGroup = new ButtonGroup();
		JRadioButton stoch 		= new JRadioButton("Stochastic Universal Sampling");
		JRadioButton roulette 	= new JRadioButton("Roulette Selection");
		JRadioButton tourney 	= new JRadioButton("Tournament Selection");
		JRadioButton truncate 	= new JRadioButton("Truncation Selection");
		samplingGroup.add(stoch);
		samplingGroup.add(roulette);
		samplingGroup.add(tourney);
		samplingGroup.add(truncate);
		JPanel samplingPanel = new JPanel();
		samplingPanel.add(stoch);
		samplingPanel.add(roulette);
		samplingPanel.add(tourney);
		samplingPanel.add(truncate);
		toolbar.add(samplingPanel);
		stoch.setSelected(true);

		
		//add crossover types
		ButtonGroup crossGroup = new ButtonGroup();
		JRadioButton orderXOver	= new JRadioButton("Order Crossover");
		JRadioButton cycleXOver = new JRadioButton("Cycle Crossover");
		JRadioButton mapXOver 	= new JRadioButton("Partially Mapped Crossover");
		crossGroup.add(orderXOver);
		crossGroup.add(cycleXOver);
		crossGroup.add(mapXOver);
		JPanel crossPanel = new JPanel();
		crossPanel.add(cycleXOver);
		crossPanel.add(orderXOver);
		crossPanel.add(mapXOver);		
		toolbar.add(crossPanel);
		cycleXOver.setSelected(true);
		
		
		//mutation rate slider
		JPanel mutSliderPanel = new JPanel();
		mutSliderPanel.setPreferredSize(new Dimension(500, 50));
		mutSliderPanel.setLayout(new BoxLayout(mutSliderPanel, BoxLayout.X_AXIS));
		JLabel mutSliderLabel = new JLabel("Mutation %:");
		mutSliderLabel.setPreferredSize(new Dimension(75, 50));
		mutSliderPanel.add(mutSliderLabel);
		JSlider mutSlider = new JSlider();
		mutSlider.setMinimum(0);
		mutSlider.setMaximum(50);
		mutSlider.setMajorTickSpacing(5);
		mutSlider.setMinorTickSpacing(1);
		mutSlider.setValue(3);
		mutSlider.setSnapToTicks(true);
		mutSlider.setPaintTicks(true);
		mutSlider.setPaintLabels(true);
		mutSliderPanel.add(mutSlider);
		toolbar.add(mutSliderPanel);
		
		
		//elitism slider
		JPanel eliSliderPanel = new JPanel();
		eliSliderPanel.setPreferredSize(new Dimension(500, 50));
		eliSliderPanel.setLayout(new BoxLayout(eliSliderPanel, BoxLayout.X_AXIS));
		JLabel eliSliderLabel = new JLabel("Elites 0.1%:");
		eliSliderLabel.setPreferredSize(new Dimension(75, 50));
		eliSliderPanel.add(eliSliderLabel);
		JSlider eliSlider = new JSlider();
		eliSlider.setMinimum(0);
		eliSlider.setMaximum(100);
		eliSlider.setMajorTickSpacing(10);
		eliSlider.setMinorTickSpacing(1);
		eliSlider.setValue(4);
		eliSlider.setSnapToTicks(true);
		eliSlider.setPaintTicks(true);
		eliSlider.setPaintLabels(true);
		eliSliderPanel.add(eliSlider);
		toolbar.add(eliSliderPanel);
		
		
		//mating pool size slider
		JPanel matSliderPanel = new JPanel();
		matSliderPanel.setPreferredSize(new Dimension(500, 50));
		matSliderPanel.setLayout(new BoxLayout(matSliderPanel, BoxLayout.X_AXIS));
		JLabel matSliderLabel = new JLabel("Mating %:");
		matSliderLabel.setPreferredSize(new Dimension(75, 50));
		matSliderPanel.add(matSliderLabel);
		JSlider matSlider = new JSlider();
		matSlider.setMinimum(0);
		matSlider.setMaximum(100);
		matSlider.setMajorTickSpacing(10);
		matSlider.setMinorTickSpacing(1);
		matSlider.setValue(6);
		matSlider.setSnapToTicks(true);
		matSlider.setPaintTicks(true);
		matSlider.setPaintLabels(true);
		matSliderPanel.add(matSlider);
		toolbar.add(matSliderPanel);
		
		
		//add population text boxs
		JPanel popPanel = new JPanel();
		popPanel.setLayout(new BoxLayout(popPanel, BoxLayout.X_AXIS));
		JLabel popLabel = new JLabel("             Population size: ");
		popPanel.add(popLabel);
		popSpin = new JSpinner();
		popSpin.setValue(20000);
		popPanel.add(popSpin);
		crossPanel.add(popPanel);
		
		
		//add buttons
		JPanel btnPanel = new JPanel();
		JButton startBtn = new JButton("Start");
		JButton resetBtn = new JButton("Reset to defaults");
		JButton clearBtn = new JButton("Clear");
		JButton stopBtn = new JButton("Stop");
		btnPanel.add(startBtn);
		btnPanel.add(resetBtn);
		btnPanel.add(clearBtn);
		btnPanel.add(stopBtn);
		toolbar.add(btnPanel);
		
		
		
		//create the drawing panel
		JPanel drawPanel = new JPanel();
		drawPanel.setPreferredSize(new Dimension(1100, 450));
		add(drawPanel);	
		
		startBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int i = 0;
		    	FormalSeatingGenetic gg = new FormalSeatingGenetic();
		    	System.out.printf("TEST!\n");
				gg.start((int) popSpin.getValue(), mutSlider.getValue(), matSlider.getValue(), eliSlider.getValue());
		    	System.out.printf("??\n");
				
				FormalSeatingGenetic.GenerationStatistics genStats;
				do {										
					genStats = gg.doGeneration();
					allGens.add(genStats);
					
					Graphics g = drawPanel.getGraphics();
					redrawGraph(g, genStats);
														
				} while (true);		
			}
		});
		
		resetBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				matSlider.setValue(30);
				eliSlider.setValue(30);
				mutSlider.setValue(15);
				popSpin.setValue(10000);
			}
		});	
	}
	
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("Genetic Algorithm GUI");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
		GUI gui = new GUI();
		frame.setContentPane(gui);
		frame.pack();
		frame.setVisible(true);
	}
}
