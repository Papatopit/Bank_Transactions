import junit.framework.TestCase;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BankTest extends TestCase {

    private Bank bank;
    private List<Client> clients;
    int countThread = 30;
    HashMap<String, Account> accounts;

    public void setUp() {

        bank = new Bank(accounts);
        clients = new ArrayList<>();
        accounts = new HashMap<>();

        for (int i = 0; i < 10; i++) {
            Client client = new Client(bank.addAccount(10_000_000));
            clients.add(client);
        }
    }
        public void testTransfer() {

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

            Map<String, Account> accounts = bank.getAccounts();
            for(Map.Entry<String, Account> entry : accounts.entrySet()) {
                Account account = entry.getValue();

                boolean actual = account.isBlock();
                Assert.assertFalse(actual);
            }
        }

}
