import Exceptions.AlreadyExistsException;
import Exceptions.WrongParameterException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Account {
    static final List<String> currencyList = Arrays.asList("azn", "usd", "eur");
    static List<Account> accList = new ArrayList<>();
    static List<Integer> accNumList = new ArrayList<>();
    static double[][][] convArr =
            {
                    {{1}, {1.75}, {2}},
                    {{1.75}, {1}, {1.2}},
                    {{2}, {1.2}, {1}}
            };
    private String name;
    private int accNum;
    private String currency;
    private double amount;

    public Account(String name, int accNum, String currency, double amount) throws WrongParameterException, AlreadyExistsException {
        this.name = name;
        this.currency = currency;
        if (Account.accNumList.contains(accNum)) {
            throw new AlreadyExistsException("This account number already exists,Write another one");
        } else {
            this.accNum = accNum;
            Account.accNumList.add(accNum);

        }
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAccNum() {
        return accNum;
    }

    public void setAccNum(int accNum) {
        this.accNum = accNum;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double newAmount, String otherCurrency) throws WrongParameterException {
        int firstCurIndx = Account.currencyList.indexOf(currency);
        int otherCurInx = Account.currencyList.indexOf(otherCurrency);
        double convertedMny = 0;//converted money
        if (firstCurIndx == otherCurInx)
            this.amount += newAmount;
        else {
            if (firstCurIndx < otherCurInx) {
                convertedMny = newAmount * Account.convArr[firstCurIndx][otherCurInx][0];

            } else if (firstCurIndx > otherCurInx) {
                convertedMny = newAmount / Account.convArr[firstCurIndx][otherCurInx][0];
            }

        }
        this.amount += convertedMny;
    }

}
