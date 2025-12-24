package com.ecommerce.ecommerce.cliRunner;

import com.ecommerce.ecommerce.dto.PaymentInitiateRequestDto;
import com.ecommerce.ecommerce.dto.PaymentResponseDto;
import com.ecommerce.ecommerce.dto.PaymentVerifyRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;
import java.util.Scanner;

@Service
public class CliService {

    private final WebClient client = WebClient.create("http://localhost:8081");
    private String jwt = null;
    private Long userId = null;
    private final Scanner scanner = new Scanner(System.in);

    public void start() {
        while (true) {
            System.out.println("\n==== E-COMMERCE CLI ====");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Add Category (Admin)");
            System.out.println("4. Add Product (Admin)");
            System.out.println("5. View All Products");
            System.out.println("6. Add Product to Cart");
            System.out.println("7. View Cart");
            System.out.println("8. Add Address");
            System.out.println("9. Checkout / Place Order");
//            System.out.println("10. Initiate Payment");
//            System.out.println("11. Verify Payment");
            System.out.println("10. Exit");
            System.out.print("Select option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> register(scanner);
                case 2 -> login(scanner);
                case 3 -> addCategory();
                case 4 -> addProduct();
                case 5 -> showProducts();
                case 6 -> addToCart(scanner);
                case 7 -> viewCart();
                case 8 -> addAddress();
                case 9 -> checkout();
//                case 10 -> initiatePayment();
//                case 11 -> verifyPayment();
                case 10 -> {
                    System.out.println("Bye 👋");
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    // ---------------------------
    // 1. REGISTER API
    // ---------------------------
    private void register(Scanner sc) {
        System.out.print("Enter name: ");
        String name = sc.nextLine();

        System.out.print("Enter email: ");
        String email = sc.nextLine();

        System.out.print("Enter password: ");
        String password = sc.nextLine();

        System.out.print("Enter phone: ");
        String phone = sc.nextLine();

        var dto = Map.of(
                "name", name,
                "email", email,
                "password", password,
                "phoneNumber", phone
        );

        var response = client.post()
                .uri("/api/users/register")
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        System.out.println("Registration successful: " + response);
        System.out.println("Please Proceed for the Login");
    }

    // ---------------------------
    // 2. LOGIN API
    // ---------------------------
    private void login(Scanner sc) {
        System.out.print("Enter email: ");
        String email = sc.nextLine();

        System.out.print("Enter password: ");
        String password = sc.nextLine();

        var dto = Map.of(
                "email", email,
                "password", password
        );

        Map response = client.post()
                .uri("/api/users/login")
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        jwt = (String) response.get("token");
        userId = Long.valueOf(response.get("userId").toString());

        System.out.println("Login successful!");
        System.out.println("Your User ID = " + userId);
        System.out.println("Please proceed for product selection");
    }

    private void addCategory() {

        if (jwt == null) {
            System.out.println("❌ Please login first (Admin only).");
            return;
        }

        System.out.print("Enter Category Name: ");
        String name = scanner.nextLine();


        System.out.print("Enter Description: ");
        String description = scanner.nextLine();

        var dto = Map.of(
                "name", name
        );

        try {
            String response = client.post()
                    .uri("/api/categories")
                    .header("Authorization", "Bearer " + jwt)
                    .bodyValue(dto)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            System.out.println("✅ Category added successfully!");
            System.out.println(response);

        } catch (Exception e) {
            System.out.println("❌ Failed to add category");
            System.out.println(e.getMessage());
        }
    }



    private void addProduct() {

        if (jwt == null) {
            System.out.println("❌ Please login first (Admin only).");
            return;
        }

        System.out.print("Enter Product Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Price: ");
        double price = Double.parseDouble(scanner.nextLine());

        System.out.print("Enter Stock Quantity: ");
        int stock = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter Category: ");
        String category = scanner.nextLine();

        var dto = Map.of(
                "name", name,
                "price", price,
                "stock", stock,
                "category", category
        );

        try {
            String response = client.post()
                    .uri("/api/products")
                    .header("Authorization", "Bearer " + jwt)
                    .bodyValue(dto)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            System.out.println("✅ Product added successfully!");
            System.out.println(response);

        } catch (Exception e) {
            System.out.println("❌ Failed to add product");
            System.out.println(e.getMessage());
        }
    }



    // ---------------------------
    // 3. GET ALL PRODUCTS
    // ---------------------------
    private void showProducts() {
        var response = client.get()
                .uri("/api/products")
                .retrieve()
                .bodyToMono(String.class)
                .block();

        System.out.println("PRODUCT LIST:");
        System.out.println(response);
    }

    // ---------------------------
    // 4. ADD TO CART
    // ---------------------------
    private void addToCart(Scanner sc) {
        System.out.print("Enter product ID to add: ");
        Long pid = sc.nextLong();
        sc.nextLine();

        if (userId == null) {
            System.out.println("❌ Please login first.");
            return;
        }

        var dto = Map.of(
                "userId", userId,
                "productId", pid,
                "quantity", 1
        );

        var response = client.post()
                .uri("/api/cart/add")
                .header("Authorization", "Bearer " + jwt)
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        System.out.println("Product added to cart!");
        System.out.println(response);
        System.out.println("Proceed for Checkout");
    }


    // ---------------------------
    // 5. VIEW CART
    // ---------------------------
    private void viewCart() {
        var response = client.get()
                .uri("/api/cart/"+userId)
                .header("Authorization", "Bearer " + jwt)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        System.out.println("Your Cart:");
        System.out.println(response);
    }

    private void addAddress() {

        if (userId == null) {
            System.out.println("❌ Please login first.");
            return;
        }

        System.out.print("Enter House No / Street: ");
        String street = scanner.nextLine();

        System.out.print("Enter City: ");
        String city = scanner.nextLine();

        System.out.print("Enter State: ");
        String state = scanner.nextLine();

        System.out.print("Enter Postal Code: ");
        String pincode = scanner.nextLine();

        System.out.print("Enter Country: ");
        String country = scanner.nextLine();



        var dto = Map.of(
                "userId", userId,
                "street", street,
                "city", city,
                "state", state,
                "zipcode", pincode,
                "country", country
        );

        try {
            String response = client.post()
                    .uri("/api/addresses/"+userId)
                    .header("Authorization", "Bearer " + jwt)
                    .bodyValue(dto)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            System.out.println("🏠 Address added successfully:");
            System.out.println(response);

        } catch (Exception e) {
            System.out.println("❌ Failed to add address");
            System.out.println(e.getMessage());
        }
    }


    // ---------------------------
    // 6. CHECKOUT
    // ---------------------------
    private void checkout() {

        if (jwt == null) {
            System.out.println("❌ Please login first.");
            return;
        }

        if (userId == null) {
            System.out.println("❌ User ID missing. Login response must return userId.");
            return;
        }

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter addressId: ");
        Long addressId = sc.nextLong();
        sc.nextLine();

        System.out.print("Enter payment method (COD / UPI / CARD): ");
        String payment = sc.nextLine();

        var dto = Map.of(
                "userId", userId,
                "addressId", addressId,
                "paymentMethod", payment
        );

        var response = client.post()
                .uri("/api/orders/place")
                .header("Authorization", "Bearer " + jwt)
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        System.out.println("Order placed!");
        System.out.println(response);
        System.out.println("Proceed to initiate payment");

    }


    // ---------------------------
    // 7. MAKE PAYMENT
    // ---------------------------
    private Long lastPaymentId = null;
    private String lastTransactionId = null;

    private void payment(Scanner sc) {
        try {
            System.out.print("Enter Order ID to pay: ");
            Long orderId = sc.nextLong();
            sc.nextLine();

            if (jwt == null) {
                System.out.println("❌ Please login first.");
                return;
            }

            // -----------------------------
            // 1. INITIATE PAYMENT
            // -----------------------------
            var dto = Map.of(
                    "orderId", orderId,
                    "method", "UPI"
            );

            var payResponse = client.post()
                    .uri("/api/payments/initiate")
                    .header("Authorization", "Bearer " + jwt)
                    .bodyValue(dto)
                    .retrieve()
                    .bodyToMono(PaymentResponseDto.class)
                    .block();

            if (payResponse == null) {
                System.out.println("❌ Payment initiation failed.");
                return;
            }

            System.out.println("\n💳 Payment Initiated:");
            System.out.println("Payment ID: " + payResponse.paymentId());
            System.out.println("Order ID: " + payResponse.orderId());
            System.out.println("Amount: " + payResponse.amount());
            System.out.println("Transaction ID: " + payResponse.transactionId());
            System.out.println("Status: " + payResponse.status());

            // store values for verification
            lastPaymentId = payResponse.paymentId();
            lastTransactionId = payResponse.transactionId();

            // -----------------------------
            // 2. VERIFY PAYMENT
            // -----------------------------
            System.out.println("\nSimulating payment success...");

            var verifyDto = Map.of(
                    "paymentId", lastPaymentId,
                    "transactionId", lastTransactionId,
                    "success", true
            );

            var verifyResp = client.post()
                    .uri("/api/payments/verify")
                    .header("Authorization", "Bearer " + jwt)
                    .bodyValue(verifyDto)
                    .retrieve()
                    .bodyToMono(PaymentResponseDto.class)
                    .block();

            if (verifyResp == null) {
                System.out.println("❌ Payment verification failed.");
                return;
            }

            System.out.println("\n✅ PAYMENT VERIFIED SUCCESSFULLY!");
            System.out.println("Payment ID: " + verifyResp.paymentId());
            System.out.println("Status: " + verifyResp.status());

        } catch (Exception ex) {
            System.out.println("\n❌ Payment Error: " + ex.getMessage());
        }
    }

    private void initiatePayment() {

        if (userId == null) {
            System.out.println("❌ Please login first.");
            return;
        }

        System.out.print("Enter Order ID to initiate payment: ");
        Long orderId = Long.parseLong(scanner.nextLine());

        System.out.print("Enter Payment method to initiate payment: ");
        String paymentMethod = scanner.nextLine();



        try {
            PaymentInitiateRequestDto dto = new PaymentInitiateRequestDto(orderId,paymentMethod);

            PaymentResponseDto response = client.post()
                    .uri("/api/payments/initiate")
                    .header("Authorization", "Bearer " + jwt)
                    .bodyValue(dto)
                    .retrieve()
                    .bodyToMono(PaymentResponseDto.class)
                    .block();

            System.out.println("===== Payment Started =====");
            System.out.println("Payment ID: " + response.paymentId());
            System.out.println("Order ID: " + response.orderId());
            System.out.println("Amount: " + response.amount());
            System.out.println("Status: " + response.status());
            System.out.println("Transaction ID: " + response.transactionId());
            System.out.println("===========================");
            System.out.println("Please verify payment");

        } catch (Exception e) {
            System.out.println("❌ Payment initiation failed!");
            System.out.println(e.getMessage());
        }
    }



    private void verifyPayment() {

        if (userId == null) {
            System.out.println("❌ Please login first.");
            return;
        }

        System.out.print("Enter Payment ID: ");
        Long paymentId = Long.parseLong(scanner.nextLine());

        System.out.print("Enter Transaction ID: ");
        String txnId = scanner.nextLine();

        System.out.print("Was the payment successful? (yes/no): ");
        boolean success = scanner.nextLine().equalsIgnoreCase("yes");

        try {
            PaymentVerifyRequestDto dto = new PaymentVerifyRequestDto(paymentId, txnId, success);

            PaymentResponseDto response = client.post()
                    .uri("/api/payments/verify")
                    .header("Authorization", "Bearer " + jwt)
                    .bodyValue(dto)
                    .retrieve()
                    .bodyToMono(PaymentResponseDto.class)
                    .block();

            System.out.println("===== Payment Verification =====");
            System.out.println("Payment Status: " + response.status());
            System.out.println("Transaction ID: " + response.transactionId());
            System.out.println("Order updated successfully!");
            System.out.println("================================");

        } catch (Exception e) {
            System.out.println("❌ Payment verification failed!");
            System.out.println(e.getMessage());
        }
    }



}

