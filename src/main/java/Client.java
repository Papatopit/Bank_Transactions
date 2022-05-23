public class Client implements Runnable {

    private final Account account;
    private Bank bank;
    private String accountNumber;

    public Client(Account account) {
        this.account = account;
    }

    @Override
    public void run() {
        while (!account.isBlock()) {
            try {
                transfer(getAmountOfMoney());
                checkBalance();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void transfer(long money) throws InterruptedException {
        if (account.getMoney() > money && !account.isBlock()) {
            String number = bank.getRandomNumber(accountNumber);
            if (number == null) {
                account.unblock(true);
            } else {
                bank.transfer(accountNumber, number, money);
            }
        } else if (account.getMoney() < 0) {
            account.unblock(true);
        }
    }

    private long getAmountOfMoney() {
        return (long) (Math.random() * (52650));
    }

    public void checkBalance() {
        bank.getBalance(account.getAccNumber());
    }
}
