import Exceptions.AlreadyExistsException;
import Exceptions.WrongParameterException;

import java.util.HashMap;
import java.util.Scanner;

public class Main {
    HashMap<String, String> funcName = new HashMap<String, String>() {{
        put("add account", "Adds an account with name,account number,currency,amount");
        put("help", "Shows all possible commands");
        put("view account", "Shows all account if the requesting is super user");
        put("delete account", "Deletes account");
        put("add amount", "Adds a certain amount to the account ");
        put("remove amount", "Removes a certain amount from the account ");
        put("transfer", "Transfers a certain amount money to another account");

    }};
    Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws AlreadyExistsException, WrongParameterException {
        new Main().turnTmnl();
    }

    public void greetingDialogue() {
        System.out.println(
                "---------" +
                        "\nversion:1.0" +
                        "\nteacher:Gadir Teacher" +
                        "\nauthor:Ferhad" +
                        "\nlanguage:Java" +
                        "\npurpose in writing this part:to give the feeling of linux terminal" +
                        "\nproject:terminal based banking system" +
                        "\nwrite help to see all commands" +
                        "\n----------");
    }

    public void turnTmnl() throws AlreadyExistsException, WrongParameterException {
        greetingDialogue();
        boolean isTrue = true;
        while (isTrue) {
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter a command:");
            String cmd = sc.nextLine();
            switch (cmd.toLowerCase()) {
                case "exit":
                    isTrue = false;
                    break;
                case "help":
                    help();
                    break;
                case "add account":
                    System.out.println(addAccount());
                    break;
                case "delete account":
                    System.out.println(deleteAccount());
                    break;
                case "view account":
                    System.out.println(viewAccount());
                    break;
                case "add amount":
                    System.out.println(addAmount(1));
                    break;
                case "remove amount":
                    System.out.println(removeAmount());
                    break;
                case "transfer":
                    System.out.println(transfer());
                    break;
                default:
                    System.out.println("Enter another command");
            }

        }

    }


    public String addAccount() throws AlreadyExistsException, WrongParameterException {
        //name
        System.out.print("Name of the account owner:");
        String name = sc.nextLine();
        //account number
        int accNum = 0;
        System.out.print("Account number:");
        String accountNumber = sc.nextLine();
        if (accountNumber.length() == 8)
            accNum = Integer.parseInt(accountNumber);
        else
            throw new WrongParameterException("its length should be 8");

        //currency
        System.out.print("Currency:");
        String currency = sc.nextLine();
        if (!Account.currencyList.contains(currency.toLowerCase())) {
            throw new WrongParameterException("Wrong currency.it should be azn,usd or eur");
        }
        //amount
        System.out.print("Amount:");
        double amount = Double.parseDouble(sc.nextLine());
        Account.accList.add(new Account(name, accNum, currency, amount));

        return String.format("A new account %s account for %s has been created with account number %d", currency, name, accNum);
    }

    public void help() {
        funcName.forEach((key, value) -> System.out.println(String.format("%s : %s", key, value)));
    }

    public String deleteAccount() throws WrongParameterException {
        int indx = 0;
        String report = "";
        System.out.print("Account number:");
        int accNumToDel = Integer.parseInt(sc.nextLine());
        if (!Account.accNumList.contains(accNumToDel)) {
            throw new WrongParameterException("This account number does not  exist,Write another one");
        } else {
            indx = Account.accNumList.indexOf(accNumToDel);
        }
        String accName = Account.accList.get(indx).getName();
        System.out.print("Do you confirm this action?[yes/no]:");
        String answr = sc.nextLine();
        if (answr.equals("yes")) {
            Account.accList.remove(indx);

            Account.accNumList.remove(indx);
            report = "Account for " + accName + " has been deleted";
        } else {
            report = "the operation is aborted";
        }
        return report;


    }

    public String viewAccount() throws WrongParameterException {
        int indx = 0;
        String report = "";
        Account acc = null;
        System.out.print("Account number:");
        int accNum = Integer.parseInt(sc.nextLine());
        if (!Account.accNumList.contains(accNum)) {
            throw new WrongParameterException("This account number does not  exist,Write another one");
        } else {
            indx = Account.accNumList.indexOf(accNum);
            acc = Account.accList.get(indx);
            report = String.format("----------\nName:%s\nAccount number:%d \nAmount:%.2f %s\n----------", acc.getName(), acc.getAccNum(), acc.getAmount(), acc.getCurrency());
        }

        return report;
    }

    public String addAmount(int operator) throws WrongParameterException {
        int indx = 0;
        String report = "";
        System.out.print("Account number:");
        int accNum = Integer.parseInt(sc.nextLine());
        System.out.print("Amount:");
        double amount = Double.parseDouble(sc.nextLine()) * operator;
        System.out.print("Currency:");
        String currency = sc.nextLine();
        if (!Account.accNumList.contains(accNum)) {
            throw new WrongParameterException("This account number does not  exist,Write another one");
        } else {
            indx = Account.accNumList.indexOf(accNum);
            Account acc = Account.accList.get(indx);
            acc.setAmount(amount, currency);
            report = "Amount hass been successfully added";

        }
        return report;
    }

    public String removeAmount() throws WrongParameterException {
        addAmount(-1);
        return "";
    }

    public String transfer() throws WrongParameterException {
        String result = "";
        int indx = 0;
        int indx2 = 0;
        //fromAccNum to be sent from
        System.out.print("From:");
        int fromAccNum = Integer.parseInt(sc.nextLine());
        //acNum to send
        System.out.print("To:");
        int toAccNum = Integer.parseInt(sc.nextLine());
        //amount
        System.out.print("Amount:");
        double amount = Double.parseDouble(sc.nextLine());
        //currency
        System.out.print("Currency:");
        String currency = sc.nextLine();
        if (String.valueOf(fromAccNum).length() != 8 || String.valueOf(toAccNum).length() != 8)
            throw new WrongParameterException("its length should be 8");
        if (!Account.accNumList.contains(fromAccNum) || !Account.accNumList.contains(toAccNum)) {
            throw new WrongParameterException("This account number does not  exist,Write another one");
        } else {
            indx = Account.accNumList.indexOf(fromAccNum);
            indx2 = Account.accNumList.indexOf(toAccNum);
            Account.accList.get(indx).setAmount(-amount, currency);
            Account.accList.get(indx2).setAmount(amount, currency);
            result = String.format("Amount %.2f %s has been transferred from %d to %d", amount, currency, fromAccNum, toAccNum);
        }
        return result;
    }
}