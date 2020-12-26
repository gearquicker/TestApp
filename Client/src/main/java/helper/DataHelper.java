package helper;

import model.Contract;

import java.util.List;
import java.util.UUID;

public class DataHelper {

    public static void checkActuality(List<Contract> contracts) {
        for (Contract contract : contracts) {
            contract.setActual(System.currentTimeMillis() - contract.getChangeDate() < 60 * 86400000L);
        }
    }

    public static Contract createRandomContract() {
        long date = (long) (System.currentTimeMillis() - Math.random() * 1000 * 86400000);
        long changeDate = (long) (System.currentTimeMillis() - Math.random() * 100 * 86400000);
        changeDate = Math.max(changeDate, date);
        String data = UUID.randomUUID().toString();
        return new Contract(date, changeDate, data);
    }

}
