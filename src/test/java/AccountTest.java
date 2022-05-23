import org.junit.Before;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AccountTest {
    private Bank bank;
    private List<Client> clients;
    int countThread = 30;
    HashMap<String, Account> accounts;


    @Before
    public void setUp() {
        bank = new Bank(accounts);
        clients = new ArrayList<>();
        accounts = new HashMap<>();

        for (int i = 0; i < 10; i++) {
            Client client = new Client(bank.addAccount(10_000_000));
            clients.add(client);
        }
    }
    public void newAccountNotBeBlocked() {


    }
}
