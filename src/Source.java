import java.util.Random;

/**
 * Created by AdamMraz on 9/4/2020.
 */

public class Source {
    private static final Random random = new Random();
    public static void main(String[] args) throws InterruptedException {
        //Test
        Bank bank = new Bank();
        long bankBalance = 0;
        long allAccountsBalance = 0;

        for (int i = 1; i < 200; i++) {
            long money = random.nextInt(300000);
            String accountNum = Integer.toString(i);
            bank.addAccount(accountNum, money);
            bankBalance += money;
        }

        Thread thread = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                String fromAccountNum = " ";
                String toAccountNum = " ";
                while (fromAccountNum.equals(toAccountNum)) {
                    fromAccountNum = Integer.toString(1 + random.nextInt(199));
                    toAccountNum = Integer.toString(1 + random.nextInt(199));
                }
                long amount = random.nextInt(100000);
                bank.transfer(fromAccountNum, toAccountNum, amount);
            }
        });
        thread.start();

        boolean index = true;

        while (thread.isAlive()) {}
        for (int j = 0; j < 100; j++) {
            while (index) {
                index = false;
                for (int i = 1; i < 200; i++) {
                    if (bank.getIsFreeze(Integer.toString(i))) {
                        index = true;
                    }
                }
            }
        }

        for (int i = 1; i < 200; i++) {
            System.out.println("Account number " + i + "::Balance " + bank.getBalance(Integer.toString(i)) + "::Is blocked " + bank.getIsBlocked(Integer.toString(i)));
            allAccountsBalance += bank.getBalance(Integer.toString(i));
        }
        System.out.println("Bank balance = " + bankBalance);
        System.out.println("All accounts balance = " + allAccountsBalance);
    }
}
