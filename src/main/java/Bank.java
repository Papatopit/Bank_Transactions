import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public class Bank {

    private final HashMap<String, Account> accounts;
    private final Random random = new Random();


    public Bank(HashMap<String, Account> accounts) {
        this.accounts = new HashMap<>();
    }

    void addAccounts(Account account) {
        account = new Account(getRandomNumber());
        accounts.put(account.getAccNumber(), account);
    }

    public long getbankMoney() {
        return accounts.values().
                stream().
                mapToLong(Account::getMoney).
                sum();
    }

    public synchronized boolean isFraud() {
        try {
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return random.nextBoolean();
    }

    public void transfer(String fromAccountNum, String toAccountNum, long amount) throws InterruptedException {


        if (fromAccountNum.equals(toAccountNum)) {
            return;
        }

        if (fromAccountNum == null || toAccountNum == null) {
            return;
        }

        Account fromAccount = accounts.get(fromAccountNum);
        Account toAccount = accounts.get(toAccountNum);

        Object lowSyncAccount;
        Object topSyncAccount;

        if (fromAccount.getAccNumber().compareTo(toAccount.getAccNumber()) > 0) {
            lowSyncAccount = fromAccount;
            topSyncAccount = toAccount;
        } else {
            lowSyncAccount = toAccount;
            topSyncAccount = fromAccount;
        }

        synchronized (lowSyncAccount) {
            synchronized (topSyncAccount) {

                long money = accounts.get(fromAccountNum).getMoney();

                if (money < amount) {
                    System.out.println("Недостаточно средств!");
                    return;
                }

                accounts.get(fromAccountNum).takeMoney(amount);
                accounts.get(toAccountNum).putMoney(amount);
                System.out.println("Перевод " + fromAccountNum + " на " + toAccountNum + " выполнен успешно!");

                if (amount > 50_000) {
                        fromAccount.block();
                        toAccount.block();
                        System.out.println("Операция заблокирована! " + "Перевод с " + fromAccountNum + " на " + toAccountNum + " заблокирован! ");
                }
            }
        }
    }

    /**
     * TODO: реализовать метод. Возвращает остаток на счёте.
     */

    public long getBalance(String accountNum) {
        Account account = accounts.get(accountNum);
        return account.getMoney();
    }

    public HashMap<String, Account> getAccounts() {
        return accounts;
    }

    public Account addAccount(long money) {
        Account account = new Account(getRandomNumber());
        account.putMoney(money);
        return accounts.put(account.getAccNumber(), account);

    }

    public String getRandomNumber() {
        String[] letters = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
        return (int) (1 + Math.random() * 9) + letters[(int) (Math.random() * letters.length - 1)] + (int) (1000 + Math.random() * 9999);
    }

    public String getRandomAccount() {
        int accountAmount = (int) (Math.random() * accounts.size());
        Iterator<String> it = accounts.keySet().iterator();
        for (int i = 0; i < accountAmount; i++) {
            it.next();
        }
        return it.next();
    }

    public String getRandomNumber(String accNumebr) {
        String newNumber = null;
        for (Map.Entry<String, Account> entry : accounts.entrySet()) {
            if (random.nextBoolean()) {
                newNumber = entry.getKey();
                if (newNumber.equals(accNumebr)) {
                    newNumber = null;
                }
            }
        }
        return newNumber;
    }

}
