import java.util.*;

public class Main {

    public static void main(String[] args) {

        HashMap<String, Account> accounts = new HashMap<>();
        Bank bank = new Bank(accounts);
        int countThread = 30;

        List<Client> clients = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Client client = new Client(bank.addAccount(1_000_000));
            clients.add(client);
        }
        System.out.println("Количество средств в Банке: " + bank.getbankMoney());


        List<Thread> threadList = new ArrayList<>(countThread);

        for (int i = 0; i < countThread; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 100_000; j++) {
                    try {
                        bank.transfer(bank.getRandomAccount(), bank.getRandomAccount(),1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
            threadList.add(thread);
        }

        for (Thread tr : threadList) {
            try {
                tr.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        HashMap<String, Account> accountMap = bank.getAccounts();
        for (Map.Entry<String, Account> entry : accountMap.entrySet()) {
            String accNum = entry.getValue().getAccNumber();
            System.out.println("Остаток на счете: " + accNum + " - " + bank.getBalance(accNum));
        }
        System.out.println("Остаток на счете после всех транзакций: " + bank.getbankMoney());
    }
}

