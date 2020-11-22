// project interface using Java Swing

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class GUI extends JFrame
{
	// window width, height and font
	public static final String TITLE = "Chord Intervals";
	public static final int W;
	public static final int H;
	public static final Font FONT = new Font("Segoe UI", Font.PLAIN, 14);

	public Instrument instrument;

	// stores what note (as its index) is selected on each string
	public int[] stringNotes;

	// displays the intervals as a list
	public DefaultListModel<String> intervalsListModel = new DefaultListModel<String>();

	// GUI elements
	JPanel fretboard;
	JLabel[] lblStringNotes;

	// static GUI configuration
	static
	{
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		W = screen.width;
		H = screen.height;
		UIManager.put("InternalFrame.titleFont", FONT);
		UIManager.put("Button.font", FONT);
		UIManager.put("Label.font", FONT);
		UIManager.put("RadioButton.font", FONT);
	}

	public GUI()
	{
		setTitle(TITLE);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JButton presetsButton = new JButton("Presets");
		presetsButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				presets();
			}
		});
		presetsButton.setMnemonic(KeyEvent.VK_P);
		presetsButton.setToolTipText("Presets menu (Alt + P)");
		menuBar.add(presetsButton);

		JButton editButton = new JButton("Edit");
		editButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				edit();
			}
		});
		editButton.setMnemonic(KeyEvent.VK_E);
		editButton.setToolTipText("Edit menu (Alt + E)");
		menuBar.add(editButton);

		JButton helpButton = new JButton("Help");
		helpButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				help();
			}
		});
		helpButton.setMnemonic(KeyEvent.VK_H);
		helpButton.setToolTipText("Help menu (Alt + H)");
		menuBar.add(helpButton);

		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));
		setContentPane(contentPane);
		contentPane.setLayout(new GridBagLayout());

		// adds the fretboard and its label
		fretboard = new JPanel();
		fretboard.setLayout(new GridBagLayout());
		fretboard.setBorder(new EmptyBorder(5, 0, 25, 0));
		contentPane.add(fretboard, gridBagConstraints(0, 0));

		// adds intervalsListModel and its label to the UI
		JList<String> listIntervals = new JList<String>();
		listIntervals.setOpaque(false);
		listIntervals.setModel(intervalsListModel);
		listIntervals.setBorder(BorderFactory.createEmptyBorder(5, 0, 25, 0));
		contentPane.add(new JLabel("Intervals:"), gridBagConstraints(0, 1));
		contentPane.add(listIntervals, gridBagConstraints(0, 2));

		// button pressed to play the selected tones as sine waves
		JButton btnPlay = new JButton("Play chord");
		btnPlay.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				List<Double> freqs = new ArrayList<Double>();

				for (int i : stringNotes)
				{
					if (i != -1)
						freqs.add(Note.frequencyFromIndex(i));
				}

				SoundSandbox.generateTones(freqs);
			}
		});
		contentPane.add(btnPlay, gridBagConstraints(0, 3));
		
		setModel(PresetManager.getDefault());

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocation((W - getSize().width)/2, (H - getSize().height)/2);
		pack();
		setResizable(true);
		setVisible(true);
	}

	private void setModel(Instrument instrument)
	{
		this.instrument = instrument;
		updateFretboard();
	}

	private void updateFretboard()
	{
		stringNotes = new int[instrument.strings];
		System.arraycopy(instrument.tuning, 0, stringNotes, 0, instrument.strings);

		fretboard.removeAll();
		fretboard.revalidate();

		// displays what note is selected on each string
		lblStringNotes = new JLabel[instrument.strings];
		for (int i = 0; i < instrument.strings; i++)
		{
			lblStringNotes[i] = new JLabel("" + instrument.tuning[i]);
			fretboard.add(lblStringNotes[i], gridBagConstraints(0, i + 1));
		}

		// fret marks are shown as bold fret numbers
		for (int j = 1; j < instrument.frets; j++)
		{
			fretboard.add(new JLabel(instrument.marks.contains(j) ? "<html><b>" + j + "</b></html>" : j + ""),
					gridBagConstraints(j + 1, 0));
		}

		// creates a ButtonGroup for each string
		for (int i = 0; i < instrument.strings; i++)
		{
			final int i_ = i;

			ButtonGroup btnGroup = new ButtonGroup()
			{
				private ButtonModel prevModel;
				private boolean isAdjusting = false;

				@Override
				public void setSelected(ButtonModel m, boolean b)
				{
					if (isAdjusting)
						return;

					/*
					 	press the same button twice to mute a string. deselects
					 	the fret Button. updates the corresponding string in
					 	lblString (as "x") and strings (as -1)
					 */
					if (m.equals(prevModel))
					{
						isAdjusting = true;
						clearSelection();
						lblStringNotes[i_].setText("x");
						isAdjusting = false;

						stringNotes[i_] = -1;
					}
					/*
					 	selects a fret Button. calculates the selected fret
					 	note's index. updates the corresponding string in
					 	lblString (as the fret note) and strings (as the index)
					 */
					else
					{
						super.setSelected(m, b);
						int index = Integer.parseInt(m.getActionCommand()) + instrument.tuning[i_];
						lblStringNotes[i_].setText(Note.noteFromIndex(index).toString() + Note.octaveFromIndex(index));
						stringNotes[i_] = index;
					}

					prevModel = getSelection();

					// updates intervalsListModel and refreshes the UI
					String s0 = "";

					for (JLabel lbl : lblStringNotes)
						s0 += lbl.getText() + " ";

					String[] sx = Intervals.analyse(s0);
					intervalsListModel.clear();
					for (String s : sx)
						intervalsListModel.addElement(s);

					repaint();
					pack();
				}
			};

			/*
			 	adds the Button objects to each ButtonGroup. each Button stores
			 	its corresponding fret number
			 */
			for (int j = 0; j < instrument.frets; j++)
			{
				JRadioButton rdBtn = new JRadioButton();
				rdBtn.setActionCommand("" + j);
				btnGroup.add(rdBtn);

				fretboard.add(rdBtn, gridBagConstraints(j + 1, i + 1));
			}

			btnGroup.getElements().nextElement().setSelected(true);
		}
	}

	private void presets()
	{
		JFrame frame = new JFrame("Preset manager");

		JList<Instrument> presetsList = new JList<Instrument>();

		DefaultListModel<Instrument> listModel = new DefaultListModel<Instrument>();
		PresetManager.presets.forEach(i -> listModel.addElement(i));

		presetsList.setOpaque(false);
		presetsList.setModel(listModel);
		presetsList.setSelectedIndex(0);

		JScrollPane scrollPane = new JScrollPane(presetsList);

		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.LINE_AXIS));
		buttonsPanel.setBorder(new EmptyBorder(10, 0, 0, 0));

		buttonsPanel.add(Box.createRigidArea(new Dimension(10, 0)));

		JButton selectButton = new JButton("Select");
		selectButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				Instrument presetSelected = presetsList.getSelectedValue();

				if (presetSelected != null)
					setModel(presetSelected);

				frame.dispose();
			}
		}
				);
		selectButton.setMnemonic(KeyEvent.VK_ENTER);
		buttonsPanel.add(selectButton);

		buttonsPanel.add(Box.createRigidArea(new Dimension(10, 0)));

		JButton deleteButton = new JButton("Delete");
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				Instrument presetSelected = presetsList.getSelectedValue();

				if (presetSelected != null)
				{
					PresetManager.deletePreset(presetSelected.getSpecs())
					.stream().forEach(p -> listModel.removeElement(p));

					presetsList.setSelectedIndex(0);
				}
			}
		}
				);
		deleteButton.setMnemonic(KeyEvent.VK_DELETE);
		buttonsPanel.add(deleteButton);


		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));
		frame.setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		frame.add(scrollPane, BorderLayout.CENTER);
		frame.add(buttonsPanel, BorderLayout.SOUTH);

		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setMinimumSize(new Dimension(300, 350));
		frame.setLocation((W - frame.getSize().width)/2, (H - frame.getSize().height)/2);
		frame.pack();
		frame.setResizable(true);
		frame.setVisible(true);
	}

	private void edit()
	{
		JFrame frame = new JFrame("Edit instrument");

		JLabel stringsLabel = new JLabel("Strings:");

		JPanel tuningPanel = new JPanel();
		tuningPanel.setLayout(new GridBagLayout());
		tuningPanel.setAlignmentX(CENTER_ALIGNMENT);
		setTuningPegs(tuningPanel);

		JSpinner stringsSpinner = new JSpinner(new SpinnerNumberModel(instrument.strings, 1, null, 1));
		((JSpinner.DefaultEditor) stringsSpinner.getEditor()).getTextField().setEditable(false);
		stringsSpinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) 
			{
				instrument.strings = (int) stringsSpinner.getValue();

				int[] tuning_ = new int[instrument.strings];
				System.arraycopy(instrument.tuning, 0,
						tuning_, 0, Math.min(instrument.tuning.length, tuning_.length));
				instrument.tuning = tuning_;

				updateFretboard();
				setTuningPegs(tuningPanel);
				frame.pack();
			}
		}
				);
		((JSpinner.DefaultEditor) stringsSpinner.getEditor()).getTextField().setColumns(3);

		JLabel fretsLabel = new JLabel("Frets:");

		JSpinner fretsSpinner = new JSpinner(new SpinnerNumberModel(instrument.frets - 1, 1, null, 1));
		((JSpinner.DefaultEditor) fretsSpinner.getEditor()).getTextField().setEditable(false);
		fretsSpinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) 
			{
				instrument.frets = (int) fretsSpinner.getValue() + 1;

				instrument.marks.removeIf(m -> m >= instrument.frets);

				updateFretboard();
			}
		}
				);
		((JSpinner.DefaultEditor) fretsSpinner.getEditor()).getTextField().setColumns(3);

		JLabel fretMarksLabel = new JLabel("Fret marks:");
		fretMarksLabel.setAlignmentX(CENTER_ALIGNMENT);

		JTextField fretMarksText = new JTextField(instrument.marks.stream().map(i -> i.toString()).collect(Collectors.joining("-")));
		fretMarksText.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				textChanged();
			}

			public void removeUpdate(DocumentEvent e) {
				textChanged();
			}

			public void insertUpdate(DocumentEvent e) {
				textChanged();
			}

			public void textChanged()
			{
				String s = fretMarksText.getText();

				List<Integer> marks = new ArrayList<Integer>();

				if (!s.equals(""))
				{
					marks = PresetManager.parseMarks(s, instrument.frets);

					if (marks.isEmpty())
						return;
				}

				instrument.marks = marks;

				updateFretboard();
			}
		});
		fretMarksText.setHorizontalAlignment(JTextField.CENTER);
		fretMarksText.setMaximumSize(new Dimension(
				Integer.MAX_VALUE, fretMarksText.getPreferredSize().height + 10));
		fretMarksText.setBorder(new EmptyBorder(5, 0, 0, 0));

		JLabel tuningLabel = new JLabel("Tuning:");
		tuningLabel.setAlignmentX(CENTER_ALIGNMENT);

		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				instrument.toString = (String) JOptionPane.showInputDialog(
						new JFrame(),
						"",
						"Enter preset name",
						JOptionPane.PLAIN_MESSAGE,
						null,
						null,
						instrument.toString);

				if (instrument.toString != null)
					PresetManager.savePreset(instrument.getSpecs());
			}
		}
				);
		saveButton.setMnemonic(KeyEvent.VK_S);
		saveButton.setAlignmentX(LEFT_ALIGNMENT);


		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));
		frame.setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));

		JPanel stringsPanel = new JPanel();
		stringsPanel.add(stringsLabel);
		stringsPanel.add(stringsSpinner);
		frame.add(stringsPanel);
		frame.add(Box.createRigidArea(new Dimension(0, 20)));
		JPanel fretsPanel = new JPanel();
		fretsPanel.add(fretsLabel);
		fretsPanel.add(fretsSpinner);
		frame.add(fretsPanel);
		frame.add(Box.createRigidArea(new Dimension(0, 20)));
		frame.add(fretMarksLabel);
		frame.add(fretMarksText);
		frame.add(Box.createRigidArea(new Dimension(0, 20)));
		frame.add(tuningLabel);
		frame.add(tuningPanel);
		frame.add(saveButton);

		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setMinimumSize(new Dimension(240, 340));
		frame.setLocation((W - frame.getSize().width)/2, (H - frame.getSize().height)/2);
		frame.pack();
		frame.setResizable(true);
		frame.setVisible(true);
	}

	private void setTuningPegs(JPanel tuningPanel)
	{
		tuningPanel.removeAll();

		for (int i = 0; i < instrument.strings; i++)
		{
			final int I = i;

			JSpinner tuningPegSpinner = new JSpinner(new SpinnerNumberModel(instrument.tuning[i], null, null, 1));
			((JSpinner.DefaultEditor) tuningPegSpinner.getEditor()).getTextField().setEditable(false);
			tuningPegSpinner.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e)
				{
					instrument.tuning[I] = (int) tuningPegSpinner.getValue();

					updateFretboard();
				}
			}
					);
			tuningPegSpinner.setBorder(new EmptyBorder(10, 0, 10, 20));
			GridBagConstraints gbc = gridBagConstraints(i % 3, i / 3);
			gbc.insets = new Insets(5, 0, 10, 10);
			tuningPanel.add(tuningPegSpinner, gbc);
		}

		tuningPanel.revalidate();
		tuningPanel.repaint();
	}

	private void help()
	{
		JOptionPane.showMessageDialog(new JFrame(),
				"To mute a string, click on its currently selected fret.\n\n" +
				"Use the Edit (Alt + E) menu to modify instrument\n" +
				"specs and save them as a preset.\n\n" +
				"Use the Presets (Alt + P) menu to select and delete\n" +
				"instrument presets.\n\n" +
				"Deleting the presets.txt file will restore the default presets.",
				"Help",
				JOptionPane.PLAIN_MESSAGE
				);
	}

	private GridBagConstraints gridBagConstraints(int x, int y)
	{
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(1, 1, 1, 1);
		gbc.fill = GridBagConstraints.NONE;
		gbc.gridx = x;
		gbc.gridy = y;

		return gbc;
	}
}