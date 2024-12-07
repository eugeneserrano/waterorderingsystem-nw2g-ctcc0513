import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

public class ViewOrders extends JFrame {

    JLabel titleLabel;
    JTable ordersTable;
    JScrollPane scrollPane;
    DefaultTableModel tableModel;
    JButton backButton;
    Font font = new Font("SansSerif", Font.PLAIN, 16);

    public ViewOrders(Stack<Order> sharedOrderStack) {
        setTitle("View Orders");
        setSize(600, 500);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        titleLabel = new JLabel("Order List (LIFO)");
        titleLabel.setBounds(250, 20, 200, 30);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        add(titleLabel);

        String[] columnNames = { "Name", "Address", "Water Type", "Quantity", "Payment Method", "Total Amount" };
        tableModel = new DefaultTableModel(columnNames, 0);
        ordersTable = new JTable(tableModel);
        scrollPane = new JScrollPane(ordersTable);
        scrollPane.setBounds(50, 70, 500, 300);
        add(scrollPane);

        backButton = new JButton("Back");
        backButton.setBounds(250, 400, 100, 40);
        backButton.setFont(font);
        backButton.setBackground(new Color(255, 182, 193));
        add(backButton);

        loadOrders(sharedOrderStack);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                SwingUtilities.invokeLater(() -> new waterOrdering(sharedOrderStack));
            }
        });

        setVisible(true);
    }

    private void loadOrders(Stack<Order> sharedOrderStack) {
        // Create a temporary stack to preserve the original stack order
        Stack<Order> tempStack = (Stack<Order>) sharedOrderStack.clone();

        while (!tempStack.isEmpty()) {
            Order order = tempStack.pop(); // LIFO order
            Object[] row = {
                    order.name,
                    order.address,
                    order.waterType,
                    order.quantity,
                    order.paymentMethod,
                    order.totalAmount
            };
            tableModel.addRow(row);
        }
    }
}
