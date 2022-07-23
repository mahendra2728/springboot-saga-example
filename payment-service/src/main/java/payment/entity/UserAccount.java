package payment.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users_balance")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAccount {

    @Id
    private Integer userId;

    private double userCredit;

}
