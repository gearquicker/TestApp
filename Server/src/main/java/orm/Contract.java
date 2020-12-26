package orm;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "contract")
public class Contract {

    @Id
    private String id;
    private long date;
    private long changeDate;
    private String data;

    public Contract() {
    }

    public Contract(String id) {
        this.id = id;
    }

}