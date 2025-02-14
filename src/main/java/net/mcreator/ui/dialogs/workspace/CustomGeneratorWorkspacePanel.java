package net.mcreator.ui.dialogs.workspace;

import net.mcreator.generator.Generator;
import net.mcreator.generator.GeneratorConfiguration;
import net.mcreator.generator.GeneratorFlavor;
import net.mcreator.ui.component.JEmptyBox;
import net.mcreator.ui.component.util.PanelUtils;
import net.mcreator.ui.init.L10N;
import net.mcreator.ui.init.UIRES;

import javax.swing.*;
import java.awt.*;

import static net.mcreator.generator.GeneratorFlavor.BaseLanguage.JAVA;

public class CustomGeneratorWorkspacePanel extends AbstractWorkspacePanel {

	private final JComboBox<GeneratorConfiguration> generatorSelector = new JComboBox<>();
	private static final JTextField buildfileVersionField = new JTextField(10);
	private static final JCheckBox isJsonGenerator = new JCheckBox("JSON Generator");
	private static final JCheckBox isJavaGenerator = new JCheckBox("Java Generator");

	public CustomGeneratorWorkspacePanel(Window parent) {
		super(parent);

		add(new JEmptyBox(20, 20));

		add(PanelUtils.westAndEastElement(L10N.label("dialog.new_workspace.mod_name"), workspaceDialogPanel.modName));
		add(new JEmptyBox(10, 10));

		add(PanelUtils.westAndEastElement(L10N.label("dialog.new_workspace.mod_id"), workspaceDialogPanel.modID));
		add(new JEmptyBox(10, 10));

		// Generator Selection (Filtered by "custom-")
		add(PanelUtils.westAndEastElement(L10N.label("dialog.new_workspace.generator"), generatorSelector));
		populateCustomGenerators();

		add(new JEmptyBox(10, 10));

		// Buildfile version input
		add(PanelUtils.westAndEastElement(L10N.label("dialog.new_workspace.buildfile_version"), buildfileVersionField));

		add(new JEmptyBox(10, 10));

		// Checkboxes for JSON/Java Generator Type
		add(PanelUtils.westAndEastElement(new JLabel("Generator Type:"),
				PanelUtils.join(FlowLayout.LEFT, isJsonGenerator, isJavaGenerator)));

		add(new JEmptyBox(30, 30));

		add(PanelUtils.westAndEastElement(L10N.label("dialog.new_workspace.package"), workspaceDialogPanel.packageName));
		add(new JEmptyBox(30, 30));

		add(PanelUtils.westAndEastElement(L10N.label("dialog.new_workspace.folder"),
				PanelUtils.centerAndEastElement(workspaceFolder, selectWorkspaceFolder, 0, 0)));

		add(new JEmptyBox(30, 170));

		add(PanelUtils.join(FlowLayout.LEFT, new JLabel(UIRES.get("18px.info")), new JEmptyBox(0, 0),
				L10N.label("dialog.new_workspace.notice")));

		// Add validation elements
		validationGroup.addValidationElement(workspaceDialogPanel.modName);
		validationGroup.addValidationElement(workspaceDialogPanel.modID);
		validationGroup.addValidationElement(workspaceDialogPanel.packageName);
		validationGroup.addValidationElement(workspaceFolder);
	}

	/**
	 * Populates the generator dropdown with generators that start with "custom-".
	 */
	private void populateCustomGenerators() {
		generatorSelector.removeAllItems();
		Generator.GENERATOR_CACHE.values().stream().filter(gc -> gc.getGeneratorFlavor() == GeneratorFlavor.CUSTOM)
				.forEach(generatorSelector::addItem);

		GeneratorConfiguration generatorConfiguration = GeneratorConfiguration.getRecommendedGeneratorForFlavor(
				Generator.GENERATOR_CACHE.values(), GeneratorFlavor.CUSTOM);
		workspaceDialogPanel.generator.setSelectedItem(generatorConfiguration);

		// Set default selection if available
		if (generatorSelector.getItemCount() > 0) {
			generatorSelector.setSelectedIndex(0);
		}
	}

	/**
	 * Gets the selected buildfile version from the input field.
	 */
	public static String getBuildfileVersion() {
		return buildfileVersionField.getText().trim();
	}

	/**
	 * Gets the selected generator.
	 */
	public GeneratorConfiguration getSelectedGenerator() {
		return (GeneratorConfiguration) generatorSelector.getSelectedItem();
	}

	/**
	 * Gets whether the generator is JSON-based.
	 */
	public static String getBaseLanguage() {
		if(isJsonGenerator.isSelected()){
			return "JSON";
	} else if(isJavaGenerator.isSelected()){
			return "JAVA";
		} else{
			return "JAVA";
		}
	}
}
