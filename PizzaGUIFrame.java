import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class PizzaGUIFrame extends JFrame {
    private JRadioButton thinCrust, regularCrust, deepDishCrust;
    private JComboBox<String> sizeComboBox;
    private JCheckBox pepperoni, mushrooms, onions, pineapple, bacon, olives;
    private JTextArea orderSummary;

    public PizzaGUIFrame() {
        setTitle("Pizza Order Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Initialize components
        JPanel crustPanel = createCrustPanel();
        JPanel sizePanel = createSizePanel();
        JPanel toppingsPanel = createToppingsPanel();
        JPanel orderPanel = createOrderPanel();
        JPanel buttonPanel = createButtonPanel();

        // Layout
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(3, 1));
        centerPanel.add(crustPanel);
        centerPanel.add(sizePanel);
        centerPanel.add(toppingsPanel);

        add(centerPanel, BorderLayout.CENTER);
        add(orderPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JPanel createCrustPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder("Choose Crust"));

        thinCrust = new JRadioButton("Thin");
        regularCrust = new JRadioButton("Regular");
        deepDishCrust = new JRadioButton("Deep-dish");

        ButtonGroup crustGroup = new ButtonGroup();
        crustGroup.add(thinCrust);
        crustGroup.add(regularCrust);
        crustGroup.add(deepDishCrust);

        panel.add(thinCrust);
        panel.add(regularCrust);
        panel.add(deepDishCrust);
        return panel;
    }

    private JPanel createSizePanel() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder("Choose Size"));

        String[] sizes = {"Small ($8)", "Medium ($12)", "Large ($16)", "Super ($20)"};
        sizeComboBox = new JComboBox<>(sizes);

        panel.add(sizeComboBox);
        return panel;
    }

    private JPanel createToppingsPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder("Choose Toppings"));

        pepperoni = new JCheckBox("Pepperoni");
        mushrooms = new JCheckBox("Mushrooms");
        onions = new JCheckBox("Onions");
        pineapple = new JCheckBox("Pineapple");
        bacon = new JCheckBox("Bacon");
        olives = new JCheckBox("Olives");

        panel.setLayout(new GridLayout(2, 3));
        panel.add(pepperoni);
        panel.add(mushrooms);
        panel.add(onions);
        panel.add(pineapple);
        panel.add(bacon);
        panel.add(olives);

        return panel;
    }

    private JPanel createOrderPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder("Order Summary"));

        orderSummary = new JTextArea(10, 30);
        orderSummary.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(orderSummary);

        panel.add(scrollPane);
        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();

        JButton orderButton = new JButton("Order");
        JButton clearButton = new JButton("Clear");
        JButton quitButton = new JButton("Quit");

        orderButton.addActionListener(e -> processOrder());
        clearButton.addActionListener(e -> clearForm());
        quitButton.addActionListener(e -> exitApplication());

        panel.add(orderButton);
        panel.add(clearButton);
        panel.add(quitButton);

        return panel;
    }

    private void processOrder() {
        String crust = thinCrust.isSelected() ? "Thin" :
                regularCrust.isSelected() ? "Regular" :
                        deepDishCrust.isSelected() ? "Deep-dish" : "None";

        String sizeText = (String) sizeComboBox.getSelectedItem();
        double sizePrice = switch (sizeComboBox.getSelectedIndex()) {
            case 0 -> 8;
            case 1 -> 12;
            case 2 -> 16;
            default -> 20;
        };

        double toppingsCost = 0;
        StringBuilder toppingsList = new StringBuilder();
        JCheckBox[] toppings = {pepperoni, mushrooms, onions, pineapple, bacon, olives};

        for (JCheckBox topping : toppings) {
            if (topping.isSelected()) {
                toppingsList.append(topping.getText()).append("\n");
                toppingsCost += 1;
            }
        }

        double subtotal = sizePrice + toppingsCost;
        double tax = subtotal * 0.07;
        double total = subtotal + tax;

        String receipt = """
            =========================================
            Type of Crust & Size: %s - %s   $%.2f
            -----------------------------------------
            Toppings:
            %s
            -----------------------------------------
            Sub-total:   $%.2f
            Tax (7%%):   $%.2f
            -----------------------------------------
            Total:       $%.2f
            =========================================
            """.formatted(crust, sizeText, sizePrice, toppingsList.toString(), subtotal, tax, total);

        orderSummary.setText(receipt);
    }

    private void clearForm() {
        ButtonGroup crustGroup = new ButtonGroup();
        crustGroup.clearSelection();
        sizeComboBox.setSelectedIndex(0);
        JCheckBox[] toppings = {pepperoni, mushrooms, onions, pineapple, bacon, olives};
        for (JCheckBox topping : toppings) topping.setSelected(false);
        orderSummary.setText("");
    }

    private void exitApplication() {
        int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to quit?", "Confirm Exit",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (response == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}
