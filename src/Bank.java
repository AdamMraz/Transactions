import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Bank
{
    private HashMap<String, Account> accounts;
    private final Random random = new Random();
    private AtomicInteger countOfTest = new AtomicInteger();

    public Bank () {
        accounts = new HashMap<>();
    }

    public void addAccount(String accountNum, long money) {
        accounts.put(accountNum, new Account(accountNum, money));
    }

    public synchronized boolean isFraud(String fromAccountNum, String toAccountNum, long amount)
        throws InterruptedException
    {
        Thread.sleep(1000);
        return random.nextBoolean();
    }

    /**
     * TODO: реализовать метод. Метод переводит деньги между счетами.
     * Если сумма транзакции > 50000, то после совершения транзакции,
     * она отправляется на проверку Службе Безопасности – вызывается
     * метод isFraud. Если возвращается true, то делается блокировка
     * счетов (как – на ваше усмотрение)
     */
    public void transfer(String fromAccountNum, String toAccountNum, long amount)
    {
        new Thread(() -> {
            synchronized (fromAccountNum.compareTo(toAccountNum) > 0 ? toAccountNum : fromAccountNum) {
                while (accounts.get(fromAccountNum).getIsFreeze() && accounts.get(toAccountNum).getIsFreeze()) {
                }
                AtomicBoolean result = new AtomicBoolean(false);
                boolean isBlockedFrom = accounts.get(fromAccountNum).getIsBlocked();
                boolean isBlockedTo = accounts.get(toAccountNum).getIsBlocked();
                if ((!isBlockedFrom) && (!isBlockedTo) && (accounts.get(fromAccountNum).getMoney() >= amount)) {
                    accounts.get(fromAccountNum).setMoney(accounts.get(fromAccountNum).getMoney() - amount);
                    accounts.get(toAccountNum).setMoney(accounts.get(toAccountNum).getMoney() + amount);
                    if (amount > 50000) {
                        accounts.get(fromAccountNum).setFreeze(true);
                        accounts.get(toAccountNum).setFreeze(true);
                        new Thread(() -> {
                            try {
                                result.set(isFraud(fromAccountNum, toAccountNum, amount));
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            synchronized (result) {
                                accounts.get(fromAccountNum).setBlocked(result.get());
                                accounts.get(toAccountNum).setBlocked(result.get());
                            }
                            accounts.get(fromAccountNum).setFreeze(false);
                            accounts.get(toAccountNum).setFreeze(false);
                        }).start();
                    }
                }
            }
        }).start();
    }

    /**
     * TODO: реализовать метод. Возвращает остаток на счёте.
     */
    public long getBalance(String accountNum)
    {
        synchronized (accounts.get(accountNum)) {
            return accounts.get(accountNum).getMoney();
        }
    }

    public boolean getIsBlocked(String accountNum) {
        synchronized (accounts.get(accountNum)) {
            return accounts.get(accountNum).getIsBlocked();
        }
    }

    public boolean getIsFreeze(String accountNum) {
        synchronized (accounts.get(accountNum)) {
            return accounts.get(accountNum).getIsFreeze();
        }
    }
}
