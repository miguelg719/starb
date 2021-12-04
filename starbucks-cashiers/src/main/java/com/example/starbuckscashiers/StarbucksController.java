package com.example.starbuckscashiers;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.example.springcybersource.*;


@Controller
@Configuration
@RequestMapping("/")
public class StarbucksController implements WebMvcConfigurer{

    @Autowired
    private UserRepository userRepository;


    private static boolean DEBUG = true;

    @Value("${cybersource.apihost}")
    private String apiHost;
    @Value("${cybersource.merchantkeyid}")
    private String merchantKeyId;
    @Value("${cybersource.merchantsecretkey}")
    private String merchantsecretKey;
    @Value("${cybersource.merchantid}")
    private String merchantId;

    private CyberSourceAPI api = new CyberSourceAPI();
    
    static List<String> drinksNameList = null;
    static {
        drinksNameList = new ArrayList<>();
        drinksNameList.add("Caffe Latte");
        drinksNameList.add("Caffe Americano");
        drinksNameList.add("Caffe Mocha");
        drinksNameList.add("Espresso");
        drinksNameList.add("Cappuccino");
        drinksNameList.add("Iced Tea");
    }

    static List<String> milkList = null;
    static {
        milkList = new ArrayList<>();
        milkList.add("Whole");
        milkList.add("Soy");
        milkList.add("Almond");
        milkList.add("2%");
        milkList.add("Coconut");
    }

    static List<String> sizeList = null;
    static {
        sizeList = new ArrayList<>();
        sizeList.add("Tall 12 oz.");
        sizeList.add("Grande 16 oz.");
        sizeList.add("Venti 20 oz.");
    }

    private static Map<String, String> months = new HashMap<>();
    private static Map<String, String> states = new HashMap<>();

    static {
        months.put("January", "01");
        months.put("February", "02");
        months.put("March", "03");
        months.put("April", "04");
        months.put("May", "05");
        months.put("June", "06");
        months.put("July", "07");
        months.put("August", "08");
        months.put("September", "09");
        months.put("October", "10");
        months.put("November", "11");
        months.put("December", "12");
    }

    static {
        states.put("AL", "Alabama");
        states.put("AK", "Alaska");
        states.put("AB", "Alberta");
        states.put("AZ", "Arizona");
        states.put("AR", "Arkansas");
        states.put("BC", "British Columbia");
        states.put("CA", "California");
        states.put("CO", "Colorado");
        states.put("CT", "Connecticut");
        states.put("DE", "Delaware");
        states.put("DC", "District Of Columbia");
        states.put("FL", "Florida");
        states.put("GA", "Georgia");
        states.put("GU", "Guam");
        states.put("HI", "Hawaii");
        states.put("ID", "Idaho");
        states.put("IL", "Illinois");
        states.put("IN", "Indiana");
        states.put("IA", "Iowa");
        states.put("KS", "Kansas");
        states.put("KY", "Kentucky");
        states.put("LA", "Louisiana");
        states.put("ME", "Maine");
        states.put("MB", "Manitoba");
        states.put("MD", "Maryland");
        states.put("MA", "Massachusetts");
        states.put("MI", "Michigan");
        states.put("MN", "Minnesota");
        states.put("MS", "Mississippi");
        states.put("MO", "Missouri");
        states.put("MT", "Montana");
        states.put("NE", "Nebraska");
        states.put("NV", "Nevada");
        states.put("NB", "New Brunswick");
        states.put("NH", "New Hampshire");
        states.put("NJ", "New Jersey");
        states.put("NM", "New Mexico");
        states.put("NY", "New York");
        states.put("NF", "Newfoundland");
        states.put("NC", "North Carolina");
        states.put("ND", "North Dakota");
        states.put("NT", "Northwest Territories");
        states.put("NS", "Nova Scotia");
        states.put("NU", "Nunavut");
        states.put("OH", "Ohio");
        states.put("OK", "Oklahoma");
        states.put("ON", "Ontario");
        states.put("OR", "Oregon");
        states.put("PA", "Pennsylvania");
        states.put("PE", "Prince Edward Island");
        states.put("PR", "Puerto Rico");
        states.put("QC", "Quebec");
        states.put("RI", "Rhode Island");
        states.put("SK", "Saskatchewan");
        states.put("SC", "South Carolina");
        states.put("SD", "South Dakota");
        states.put("TN", "Tennessee");
        states.put("TX", "Texas");
        states.put("UT", "Utah");
        states.put("VT", "Vermont");
        states.put("VI", "Virgin Islands");
        states.put("VA", "Virginia");
        states.put("WA", "Washington");
        states.put("WV", "West Virginia");
        states.put("WI", "Wisconsin");
        states.put("WY", "Wyoming");
        states.put("YT", "Yukon Territory");
    }


    @Getter
    @Setter
    class Message {
        private String msg;

        public Message(String m) {
            msg = m;
        }
    }

    class ErrorMessages {
        private ArrayList<Message> messages = new ArrayList<>();

        public void add(String m) {
            messages.add(new Message(m));
        }

        public ArrayList<Message> getMessages() {
            return messages;
        }

        public void print() {
            for (Message m : messages) {
                System.out.println(m);
            }
        }
    }



    @GetMapping
    public String getOrder(@ModelAttribute("command") StarbucksOrder command, Model model) {
        model.addAttribute("drinksNameList", drinksNameList);
        model.addAttribute("milkList", milkList);
        model.addAttribute("sizeList", sizeList);
        // model.addAttribute("firstname", orderForm.getFirstname());
        return "cashier";
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<StarbucksOrder> getAllUsers() {
      // This returns a JSON or XML with the users
      return userRepository.findAll();
    }

    @RequestMapping
    public String submitOrder(@Valid @ModelAttribute("command") StarbucksOrder command,
            @RequestParam(value = "action", required = true) String action, Errors errors, Model model,
            HttpServletRequest request) {

        // log.info("Action: " + action);
        // log.info("Command: " + command);

        CyberSourceAPI.setHost(apiHost);
        CyberSourceAPI.setKey(merchantKeyId);
        CyberSourceAPI.setSecret(merchantsecretKey);
        CyberSourceAPI.setMerchant(merchantId);

        CyberSourceAPI.debugConfig();


        ErrorMessages msgs = new ErrorMessages();

        boolean hasErrors = false;

        /* Checking for form errors whether any of the field is empty or not */
        if (command.getFirstname().equals("")) {
            hasErrors = true;
            msgs.add("First Name required");
        }
        if (command.getLastname().equals("")) {
            hasErrors = true;
            msgs.add("Last Name required");
        }
        if (command.getAddress().equals("")) {
            hasErrors = true;
            msgs.add("Address required");
        }
        if (command.getCity().equals("")) {
            hasErrors = true;
            msgs.add("City required");
        }
        if (command.getState().equals("")) {
            hasErrors = true;
            msgs.add("State required");
        }
        if (command.getZip().equals("")) {
            hasErrors = true;
            msgs.add("Zip Code required");
        }
        if (command.getPhonenumber().equals("")) {
            hasErrors = true;
            msgs.add("Phone Number required");
        }
        if (command.getCardnumber().equals("")) {
            hasErrors = true;
            msgs.add("Card Number required");
        }
        if (command.getExpmonth().equals("")) {
            hasErrors = true;
            msgs.add("Card Exp Month required");
        }
        if (command.getExpyear().equals("")) {
            hasErrors = true;
            msgs.add("Card Exp Year required");
        }
        if (command.getCvv().equals("")) {
            hasErrors = true;
            msgs.add("Card CVV required");
        }
        if (command.getEmail().equals("")) {
            hasErrors = true;
            msgs.add("Email required");
        }

        // regex validation for proper formatting
        if (!command.getZip().matches("\\d{5}")) {
            hasErrors = true;
            msgs.add("Invalid Zip Code: " + command.getZip());
        }
        if (!command.getPhonenumber().matches("[(]\\d{3}[)] \\d{3}-\\d{4}")) {
            hasErrors = true;
            msgs.add("Invalid Phone Number: " + command.getPhonenumber());
        }
        if (!command.getCvv().matches("\\d{3}")) {
            hasErrors = true;
            msgs.add("Invalid CVV: " + command.getCvv());
        }
        if (!command.getCardnumber().matches("\\d{4}-?\\d{4}-?\\d{4}-?\\d{4}")) {
            hasErrors = true;
            msgs.add("Invalid Card Number: " + command.getCardnumber());
        }
        if (!command.getExpyear().matches("\\d{4}")) {
            hasErrors = true;
            msgs.add("Invalid Card Expiration Year: " + command.getExpyear());
        }

        // Validating months input
        if (months.get(command.getExpmonth()) == null) {
            hasErrors = true;
            msgs.add("Invalid Card Expiration Month: " + command.getExpmonth());
        }

        // Validating states input
        if (states.get(command.getState()) == null) {
            hasErrors = true;
            msgs.add("Invalid State: " + command.getState());
        }

        

        /* Render View for Errors in Form Input */
        if (hasErrors) {
            msgs.print();
            model.addAttribute("messages", msgs.getMessages());
            return "cashier";
        }



        int min = 1239871;
        int max = 9999999;
        int random_int = (int) Math.floor(Math.random() * (max - min + 1) + min);
        String order_num = String.valueOf(random_int);

        AuthRequest auth = new AuthRequest() ;
        auth.reference = order_num ;
        auth.billToFirstName = command.getFirstname();
        auth.billToLastName = command.getLastname();
        auth.billToAddress = command.getAddress();
        auth.billToCity = command.getCity();
        auth.billToState = command.getState();
        auth.billToZipCode = command.getZip();
        auth.billToPhone = command.getPhonenumber();
        auth.billToEmail = command.getEmail();
        auth.transactionAmount = "30.00";
        auth.transactionCurrency = "USD";
        auth.cardNumnber = command.getCardnumber();
        auth.cardExpMonth = months.get(command.getExpmonth());
        auth.cardExpYear = command.getExpyear();
        auth.cardCVV = command.getCvv();
        auth.cardType = CyberSourceAPI.getCardType(auth.cardNumnber);
        if (auth.cardType.equals("ERROR")) {
            System.out.println("Unsupported Credit Card Type.");
            model.addAttribute("message", "Unsupported Credit Card Type.");
            return "cashier";
        }
        boolean authValid = true;
        AuthResponse authResponse = new AuthResponse();
        System.out.println("\n\nAuth Request: " + auth.toJson());
        authResponse = api.authorize(auth);
        System.out.println("\n\nAuth Request: " + authResponse.toJson());
        if (!authResponse.status.equals("AUTHORIZED")) {
            authValid = false;
            System.out.println(authResponse.message);
            model.addAttribute("message", authResponse.message);
            return "cashier";
        }


        boolean captureValid = true;
        CaptureRequest capture = new CaptureRequest();
        CaptureResponse captureResponse = new CaptureResponse();
        if (authValid) {
            capture.reference = order_num;
            capture.paymentId = authResponse.id;
            capture.transactionAmount = "30.00";
            capture.transactionCurrency = "USD";
            System.out.println("\n\nCapture Request: " + capture.toJson());
            captureResponse = api.capture(capture);
            System.out.println("\n\nCapture Response: " + captureResponse.toJson());
            if (!captureResponse.status.equals("PENDING")) {
                captureValid = false;
                System.out.println(captureResponse.message);
                model.addAttribute("message", captureResponse.message);
                return "cashier";
            }
        }

        /* Render View */
        if (authValid && captureValid) {
            command.setOrderNumber(order_num);
            command.setTransactionAmount("30.00");
            command.setTransactionCurrency("USD");
            command.setAuthId(authResponse.id);
            command.setAuthStatus(authResponse.status);
            command.setCaptureId(captureResponse.id);
            command.setCaptureStatus(captureResponse.status);

            userRepository.save(command);
            model.addAttribute("data", command.toString());
            model.addAttribute("message", "Thank You for Your Payment! Your Order Number is: " + order_num);
        }
        return "cashier";
    }
}