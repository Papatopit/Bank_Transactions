public class Account {

    private long money;
    private final String accNumber;
    private boolean block;

    public Account(String accNumber) {
        this.accNumber = accNumber;
        this.block = false;

    }

    public long getMoney() {
        return money;
    }

    public String getAccNumber() {
        return accNumber;
    }

    public boolean isBlock() {
        return block;
    }

    protected void unblock(boolean unblock) {
        this.block = false;
    }

    protected void block() {
        block = true;
    }

    public void putMoney(long money) {
        this.money += money;
    }

    public void takeMoney(long money) {
        this.money -= money;
    }

}
