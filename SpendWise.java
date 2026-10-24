import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class SpendWise extends JFrame {

    // Colors
    Color background = new Color(18, 18, 18);
    Color darkGreen = new Color(15, 25, 20);
    Color cardColor = new Color(35, 35, 35);
    Color inputColor = new Color(48, 48, 48);
    Color green = new Color(34, 197, 94);
    Color red = new Color(239, 83, 80);
    Color blue = new Color(66, 133, 244);
    Color textColor = Color.WHITE;

    // Store transactions
    ArrayList<Transaction> transactions = new ArrayList<>();

    DefaultTableModel tableModel;
    JTable table;

    JLabel incomeLabel, expenseLabel, balanceLabel;
    JTextField descriptionField, amountField;
    JComboBox<String> typeBox;

    // Transaction class
    class Transaction {
        String date;
        String type;
        String description;
        double amount;

        Transaction(String type, String description, double amount) {
            this.date = LocalDate.now().toString();
            this.type = type;
            this.description = description;
            this.amount = amount;
        }
    }

    // Main window
    public SpendWise() {
        setTitle("SpendWise - Expense Manager");
        setSize(900, 650);
        setMinimumSize(new Dimension(750, 550));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(background);

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(darkGreen);
        header.setBorder(
                BorderFactory.createEmptyBorder(20, 25, 20, 25)
        );

        JLabel title = new JLabel("SpendWise");
        title.setFont(new Font("Arial", Font.BOLD, 30));
        title.setForeground(green);

        JLabel subtitle = new JLabel("YOUR MONEY, UNDER CONTROL");
        subtitle.setFont(new Font("Arial", Font.PLAIN, 12));
        subtitle.setForeground(Color.LIGHT_GRAY);

        JPanel heading = new JPanel(new GridLayout(2, 1, 0, 5));
        heading.setOpaque(false);
        heading.add(title);
        heading.add(subtitle);

        header.add(heading, BorderLayout.WEST);
        add(header, BorderLayout.NORTH);

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(background);
        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(15, 20, 15, 20)
        );

        // Summary cards
        JPanel summaryPanel = new JPanel(new GridLayout(1, 3, 15, 0));
        summaryPanel.setBackground(background);

        incomeLabel = new JLabel("Income: Rs. 0.00");
        expenseLabel = new JLabel("Expenses: Rs. 0.00");
        balanceLabel = new JLabel("Balance: Rs. 0.00");

        summaryPanel.add(createCard(incomeLabel, green));
        summaryPanel.add(createCard(expenseLabel, red));
        summaryPanel.add(createCard(balanceLabel, blue));

        mainPanel.add(summaryPanel, BorderLayout.NORTH);

        // Transaction table
        String[] columns = {
                "Date", "Type", "Description", "Amount (Rs.)"
        };

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setRowHeight(32);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setBackground(cardColor);
        table.setForeground(textColor);
        table.setGridColor(new Color(65, 65, 65));
        table.setShowVerticalLines(false);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setSelectionBackground(new Color(35, 100, 65));
        table.setSelectionForeground(Color.WHITE);

        // Table heading style
        table.getTableHeader().setFont(
                new Font("Arial", Font.BOLD, 14)
        );
        table.getTableHeader().setBackground(darkGreen);
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setPreferredSize(new Dimension(0, 38));

        // Alternate dark table rows
        DefaultTableCellRenderer renderer =
                new DefaultTableCellRenderer() {
                    @Override
                    public Component getTableCellRendererComponent(
                            JTable table, Object value, boolean selected,
                            boolean focused, int row, int column) {

                        Component cell =
                                super.getTableCellRendererComponent(
                                        table, value, selected,
                                        focused, row, column
                                );

                        if (!selected) {
                            if (row % 2 == 0) {
                                cell.setBackground(cardColor);
                            } else {
                                cell.setBackground(inputColor);
                            }
                            cell.setForeground(Color.WHITE);
                        }

                        setBorder(BorderFactory.createEmptyBorder(
                                0, 10, 0, 10
                        ));

                        return cell;
                    }
                };

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i)
                    .setCellRenderer(renderer);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(
                                new Color(65, 65, 65)
                        ),
                        "TRANSACTION HISTORY",
                        0, 0,
                        new Font("Arial", Font.BOLD, 12),
                        green
                )
        );
        scrollPane.getViewport().setBackground(cardColor);
        scrollPane.getViewport().setOpaque(true);
        scrollPane.getVerticalScrollBar().setBackground(cardColor);

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Input form
        JPanel form = new JPanel(new GridLayout(2, 4, 10, 10));
        form.setBackground(cardColor);
        form.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder(
                                BorderFactory.createLineBorder(
                                        new Color(65, 65, 65)
                                ),
                                "ADD TRANSACTION",
                                0, 0,
                                new Font("Arial", Font.BOLD, 12),
                                green
                        ),
                        BorderFactory.createEmptyBorder(10, 10, 10, 10)
                )
        );

        typeBox = new JComboBox<>(
                new String[]{"Income", "Expense"}
        );
        descriptionField = new JTextField();
        amountField = new JTextField();

        // Style input fields
        descriptionField.setBackground(inputColor);
        descriptionField.setForeground(textColor);
        descriptionField.setCaretColor(Color.WHITE);

        amountField.setBackground(inputColor);
        amountField.setForeground(textColor);
        amountField.setCaretColor(Color.WHITE);

        typeBox.setBackground(inputColor);
        typeBox.setForeground(textColor);

        form.add(createLabel("Type"));
        form.add(createLabel("Description"));
        form.add(createLabel("Amount"));
        form.add(createLabel("Action"));

        form.add(typeBox);
        form.add(descriptionField);
        form.add(amountField);

        JButton addButton = new JButton("+ Add Transaction");
        styleButton(addButton, green);
        form.add(addButton);

        // Delete button
        JButton deleteButton = new JButton("Delete Selected");
        styleButton(deleteButton, red);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(background);
        buttonPanel.add(deleteButton);

        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
        bottomPanel.setBackground(background);
        bottomPanel.add(form, BorderLayout.CENTER);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);

        // Button actions
        addButton.addActionListener(e -> addTransaction());
        deleteButton.addActionListener(e -> deleteTransaction());

        setVisible(true);
    }

    // Create labels
    JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.LIGHT_GRAY);
        return label;
    }

    // Create summary cards
    JPanel createCard(JLabel label, Color color) {
        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(cardColor);
        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(
                                0, 4, 0, 0, color
                        ),
                        BorderFactory.createEmptyBorder(15, 8, 15, 8)
                )
        );

        label.setFont(new Font("Arial", Font.BOLD, 16));
        label.setForeground(color);

        card.add(label);
        return card;
    }

    // Style buttons
    void styleButton(JButton button, Color color) {
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 13));
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    // Add transaction
    void addTransaction() {
        String type = (String) typeBox.getSelectedItem();
        String description = descriptionField.getText().trim();

        if (description.isEmpty() ||
                amountField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(
                    this, "Please fill in all fields!"
            );
            return;
        }

        double amount;

        try {
            amount = Double.parseDouble(amountField.getText().trim());

            if (amount <= 0 || !Double.isFinite(amount)) {
                JOptionPane.showMessageDialog(
                        this, "Enter an amount greater than zero!"
                );
                return;
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    this, "Please enter a valid number!"
            );
            return;
        }

        Transaction t = new Transaction(type, description, amount);
        transactions.add(t);

        tableModel.addRow(new Object[]{
                t.date, t.type, t.description,
                String.format("%.2f", t.amount)
        });

        updateBalance();

        descriptionField.setText("");
        amountField.setText("");
    }

    // Calculate totals and balance
    void updateBalance() {
        double income = 0;
        double expenses = 0;

        for (Transaction t : transactions) {
            if (t.type.equals("Income")) {
                income += t.amount;
            } else {
                expenses += t.amount;
            }
        }

        double balance = income - expenses;

        incomeLabel.setText(
                String.format("Income: Rs. %.2f", income)
        );
        expenseLabel.setText(
                String.format("Expenses: Rs. %.2f", expenses)
        );
        balanceLabel.setText(
                String.format("Balance: Rs. %.2f", balance)
        );
    }

    // Delete selected transaction
    void deleteTransaction() {
        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(
                    this, "Please select a transaction first!"
            );
            return;
        }

        transactions.remove(row);
        tableModel.removeRow(row);

        updateBalance();
    }

    // Start application
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SpendWise());
    }
}