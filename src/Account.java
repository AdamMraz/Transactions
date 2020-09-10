import java.util.concurrent.atomic.AtomicLong;

public class Account
{
    private volatile boolean isBlocked;
    private volatile long money;
    private String accNumber;
    private volatile boolean isFreeze;

    public Account(String accNumber, long money) {
        this.isBlocked = false;
        this.money = money;
        this.accNumber = accNumber;
        isFreeze = false;
    }

    public boolean getIsBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
    }

    public String getAccNumber() {
        return accNumber;
    }

    public void setAccNumber(String accNumber) {
        this.accNumber = accNumber;
    }

    public void setFreeze(boolean freeze) {
        isFreeze = freeze;
    }

    public boolean getIsFreeze() {
        return isFreeze;
    }
}
