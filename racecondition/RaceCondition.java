package racecondition;

import java.util.Arrays;
import java.util.Random;

public class RaceCondition {

    public static void main(String[] args) {
        var bank = new Bank();
        Runnable task = () -> {
        int range = 1000;
            while (range-- > 0) {
                var randomFromAccount = new Random().nextInt(5);
                var randomToAccount = new Random().nextInt(5);
                while(randomToAccount == randomFromAccount){
                    randomFromAccount = new Random().nextInt(5);
                }
                var amount = new Random().nextInt(10,100);
                bank.transfer(randomFromAccount, randomToAccount, amount);
            }
        };


        var thread = new Thread(task);
        var thread1 = new Thread(task);
        var thread2 = new Thread(task);

        thread.start();
        thread1.start();
        thread2.start();
    }
}

class Bank {

    private final Account[] accounts = new Account[5];

    {
//        initial worth of bank is 665
        accounts[0] = new Account("Kamran", 100);
        accounts[1] = new Account("Baxtiyar", 30);
        accounts[2] = new Account("Alshan", 10);
        accounts[3] = new Account("Narmin", 25);
        accounts[4] = new Account("Aydin", 500);
    }

    public synchronized void transfer(int fromAccountIndex, int toAccountIndex, double amount) {
        System.out.println("-----------" + Thread.currentThread().getId() + " thread running process ----------");
        var fromAccount = accounts[fromAccountIndex];
        var toAccount = accounts[toAccountIndex];
        fromAccount.setAmount(fromAccount.getAmount() - amount);
        System.out.println(fromAccount.getName() + " sent " + amount + "$ to account " + toAccount.getName() + ", he/she now has " + fromAccount.getAmount() + "$");
        toAccount.setAmount(toAccount.getAmount() + amount);
        System.out.println(toAccount.getName() + " received " + amount + "$ to account " + fromAccount.getName() + ", he/she now has " + toAccount.getAmount() + "$");
        System.out.println("Transfer completed, bank worth = " + this.getBankWorth());
    }

    public double getBankWorth() {
        return Arrays.stream(accounts)
                .mapToDouble(Account::getAmount)
                .sum();


    }
}

class Account {

    private String name;
    private double amount;

    public Account(String name, double amount) {
        this.name = name;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
