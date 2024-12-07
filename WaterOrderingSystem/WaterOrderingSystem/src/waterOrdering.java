import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

class Order {
    String name;
    String address;
    String waterType;
    int quantity;
    String paymentMethod;
    double totalAmount;

    public Order(String name, String address, String waterType, int quantity, String paymentMethod,
            double totalAmount) {
        this.name = name;
        this.address = address;
        this.waterType = waterType;
        this.quantity = quantity;
        this.paymentMethod = paymentMethod;
        this.totalAmount = totalAmount;
    }
}

public class waterOrdering extends JFrame {

    JLabel name, address, water, quantity, payment;
    JTextField nametxt, addresstxt, quantitytxt;
    JComboBox<String> watercb, paymentcb;
    JButton orderbtn, viewOrdersBtn;

    Font font = new Font("SansSerif", Font.PLAIN, 16);

    String[] waterOptions = { "Mineral Water", "Purified Water", "Spring Water" };
    double[] waterPrices = { 320, 300, 350 };
    String[] paymentOptions = { "Cash", "E-Wallet" };

    Stack<Order> orderStack;

    public waterOrdering(Stack<Order> sharedOrderStack) {
        this.orderStack = sharedOrderStack;

        setTitle("Water Ordering System");
        setSize(500, 500);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        name = new JLabel("Name:");
        name.setBounds(70, 83, 80, 25);
        name.setFont(font);
        add(name);

        nametxt = new JTextField();
        nametxt.setBounds(145, 75, 280, 40);
        nametxt.setFont(font);
        add(nametxt);

        address = new JLabel("Address:");
        address.setBounds(70, 143, 80, 25);
        address.setFont(font);
        add(address);

        addresstxt = new JTextField();
        addresstxt.setBounds(145, 133, 280, 40);
        addresstxt.setFont(font);
        add(addresstxt);

        water = new JLabel("Water:");
        water.setBounds(70, 203, 80, 25);
        water.setFont(font);
        add(water);

        watercb = new JComboBox<>(waterOptions);
        watercb.setBounds(145, 195, 280, 40);
        watercb.setFont(font);
        add(watercb);

        quantity = new JLabel("Quantity:");
        quantity.setBounds(70, 263, 80, 25);
        quantity.setFont(font);
        add(quantity);

        quantitytxt = new JTextField();
        quantitytxt.setBounds(145, 255, 280, 40);
        quantitytxt.setFont(font);
        add(quantitytxt);

        payment = new JLabel("Payment Method:");
        payment.setBounds(70, 322, 150, 25);
        payment.setFont(font);
        add(payment);

        paymentcb = new JComboBox<>(paymentOptions);
        paymentcb.setBounds(205, 315, 219, 40);
        paymentcb.setFont(font);
        add(paymentcb);

        orderbtn = new JButton("Order");
        orderbtn.setBackground(new Color(144, 238, 144));
        orderbtn.setBounds(70, 380, 120, 45);
        orderbtn.setFont(font);
        add(orderbtn);

        viewOrdersBtn = new JButton("View Orders");
        viewOrdersBtn.setBackground(new Color(173, 216, 230));
        viewOrdersBtn.setBounds(305, 380, 120, 45);
        viewOrdersBtn.setFont(font);
        add(viewOrdersBtn);

        orderbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nme = nametxt.getText();
                String add = addresstxt.getText();
                String waterchoice = (String) watercb.getSelectedItem();
                String quantText = quantitytxt.getText();

                if (quantText.isEmpty() || !quantText.matches("\\d+") || Integer.parseInt(quantText) <= 0) {
                    JOptionPane.showMessageDialog(null, "Kulit mo, number nga e!", "Invalid Input",
                            JOptionPane.WARNING_MESSAGE);
                } else {
                    int quant = Integer.parseInt(quantText);
                    int pyment = paymentcb.getSelectedIndex();

                    double totalAmount = calculateTotalAmount(waterchoice, quant);

                    Order order = new Order(nme, add, waterchoice, quant, paymentOptions[pyment], totalAmount);
                    orderStack.push(order);
                    JOptionPane.showMessageDialog(null, "Order placed successfully. Total Amount: ₱" + totalAmount);
                    clearFields();
                }
            }
        });

        viewOrdersBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                SwingUtilities.invokeLater(() -> new ViewOrders(orderStack));
            }
        });

        setVisible(true);
    }

    private double calculateTotalAmount(String selectedWater, int quantity) {
        double price = 0;
        for (int i = 0; i < waterOptions.length; i++) {
            if (waterOptions[i].equals(selectedWater)) {
                price = waterPrices[i];
                break;
            }
        }
        return price * quantity;
    }

    private void clearFields() {
        nametxt.setText("");
        addresstxt.setText("");
        quantitytxt.setText("");
        watercb.setSelectedIndex(0);
        paymentcb.setSelectedIndex(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new waterOrdering(new Stack<>()));
    }
}
